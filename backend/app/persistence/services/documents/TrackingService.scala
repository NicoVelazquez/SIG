package persistence.services.documents

import persistence.services.Service
import persistence.tables.documents.Tracking

import scala.concurrent.Future

class TrackingService extends Service[Tracking] {

  import persistence.db.SqlController.ctx._
  import persistence.db.SqlController.{ctx, exec}

  override def create(t: Tracking): Future[Int] =
    ctx.run(quote(querySchema[Tracking]("tracking_table"))
      .insert(
        _.addressId -> t.addressId,
        _.trackingNumber -> t.trackingNumber,
        _.trackingState -> t.trackingState
      ).onConflictIgnore.returning(_.id))

  override def update(t: Tracking): Future[Boolean] =
    ctx.run(quote(querySchema[Tracking]("tracking_table"))
      .filter(_.id == lift(t.id))
      .update(
        _.addressId -> t.addressId,
        _.trackingNumber -> t.trackingNumber,
        _.trackingState -> t.trackingState
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[Tracking]("tracking_table")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[Tracking]] =
    ctx.run(quote(querySchema[Tracking]("tracking_table")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[Tracking]] =
    ctx.run(quote(querySchema[Tracking]("tracking_table")))
}
