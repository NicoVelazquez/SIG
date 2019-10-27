package persistence.services.product

import persistence.services.Service
import persistence.tables.product.{Lot, Product}

import scala.concurrent.Future

class ProductService extends Service[Product] {
  import settings.db.SqlController.ctx._
  import settings.db.SqlController.{ctx, exec}

  override def create(t: Product): Future[Int] =
    ctx.run(quote(querySchema[Product]("product"))
      .insert(
        _.name -> lift(t.name),
        _.description -> lift(t.description),
        _.weight -> lift(t.weight),
        _.lotId -> lift(t.lotId)
      ).onConflictIgnore.returning(_.id))

  override def update(t: Product): Future[Boolean] =
    ctx.run(quote(querySchema[Product]("product"))
      .filter(_.id == lift(t.id))
      .update(
        _.name -> lift(t.name),
        _.description -> lift(t.description),
        _.weight -> lift(t.weight),
        _.lotId -> lift(t.lotId)
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[Product]("product")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[Product]] =
    ctx.run(quote(querySchema[Product]("product")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[Product]] =
    ctx.run(quote(querySchema[Product]("product")))

  def getProductWithLot(productId: Int): Future[Option[(Product, Lot)]] = {
    val q = quote {
      for {
        l <- querySchema[Lot]("lot")
        p <- querySchema[Product]("product") if l.id == p.lotId
      } yield (p, l)
    }
    ctx.run(q.filter(_._1.id == lift(productId))).map(_.headOption)
  }
}
