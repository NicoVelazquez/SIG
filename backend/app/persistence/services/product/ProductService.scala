package persistence.services.product

import persistence.services.Service
import persistence.tables.product.Product

import scala.concurrent.Future

class ProductService extends Service[Product] {
  import persistence.db.SqlController.ctx._
  import persistence.db.SqlController.{ctx, exec}

  override def create(t: Product): Future[Int] =
    ctx.run(quote(querySchema[Product]("product_table"))
      .insert(
        _.name -> t.name,
        _.description -> t.description,
        _.weight -> t.weight
      ).onConflictIgnore.returning(_.id))

  override def update(t: Product): Future[Boolean] =
    ctx.run(quote(querySchema[Product]("product_table"))
      .filter(_.id == lift(t.id))
      .update(
        _.name -> t.name,
        _.description -> t.description,
        _.weight -> t.weight
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[Product]("product_table")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[Product]] =
    ctx.run(quote(querySchema[Product]("product_table")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[Product]] =
    ctx.run(quote(querySchema[Product]("product_table")))
}
