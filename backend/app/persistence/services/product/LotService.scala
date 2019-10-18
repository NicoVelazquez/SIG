package persistence.services.product

import persistence.services.Service
import persistence.tables.product.Lot

import scala.concurrent.Future

class LotService extends Service[Lot] {
  import persistence.db.SqlController.ctx._
  import persistence.db.SqlController.{ctx, exec}

  override def create(t: Lot): Future[Int] =
    ctx.run(quote(querySchema[Lot]("lot_table"))
      .insert(
        _.date -> t.date,
        _.name -> t.name
      ).onConflictIgnore.returning(_.id))

  override def update(t: Lot): Future[Boolean] =
    ctx.run(quote(querySchema[Lot]("lot_table"))
      .filter(_.id == lift(t.id))
      .update(
        _.date -> t.date,
        _.name -> t.name
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[Lot]("lot_table")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[Lot]] =
    ctx.run(quote(querySchema[Lot]("lot_table")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[Lot]] =
    ctx.run(quote(querySchema[Lot]("lot_table")))
}
