package persistence.services.product

import persistence.services.Service
import persistence.tables.product.Lot

import scala.concurrent.Future

class LotService extends Service[Lot] {
  import settings.db.SqlController.ctx._
  import settings.db.SqlController.{ctx, exec}

  override def create(t: Lot): Future[Int] =
    ctx.run(quote(querySchema[Lot]("lot"))
      .insert(
        _.creationDate -> lift(t.creationDate),
        _.name -> lift(t.name),
        _.expirationDate -> lift(t.expirationDate)
      ).onConflictIgnore.returning(_.id))

  override def update(t: Lot): Future[Boolean] =
    ctx.run(quote(querySchema[Lot]("lot"))
      .filter(_.id == lift(t.id))
      .update(
        _.creationDate -> lift(t.creationDate),
        _.name -> lift(t.name),
        _.expirationDate -> lift(t.expirationDate)
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[Lot]("lot")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[Lot]] =
    ctx.run(quote(querySchema[Lot]("lot")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[Lot]] =
    ctx.run(quote(querySchema[Lot]("lot")))
}
