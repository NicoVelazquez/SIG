package persistence.services.documents

import persistence.services.Service
import persistence.tables.documents.LogisticRequest

import scala.concurrent.Future

class LogisticRequestService extends Service[LogisticRequest] {

  import persistence.db.SqlController.ctx._
  import persistence.db.SqlController.{ctx, exec}

  override def create(t: LogisticRequest): Future[Int] =
    ctx.run(quote(querySchema[LogisticRequest]("logistic_request_table"))
      .insert(
        _.date -> t.date,
        _.logisticOperatorId -> t.logisticOperatorId,
        _.returnRequestId -> t.returnRequestId,
        _.trackingId -> t.trackingId
      ).onConflictIgnore.returning(_.id))

  override def update(t: LogisticRequest): Future[Boolean] =
    ctx.run(quote(querySchema[LogisticRequest]("logistic_request_table"))
      .filter(_.id == lift(t.id))
      .update(
        _.date -> t.date,
        _.logisticOperatorId -> t.logisticOperatorId,
        _.returnRequestId -> t.returnRequestId,
        _.trackingId -> t.trackingId
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[LogisticRequest]("logistic_request_table")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[LogisticRequest]] =
    ctx.run(quote(querySchema[LogisticRequest]("logistic_request_table")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[LogisticRequest]] =
    ctx.run(quote(querySchema[LogisticRequest]("logistic_request_table")))
}
