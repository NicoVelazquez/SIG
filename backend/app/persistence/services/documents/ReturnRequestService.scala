package persistence.services.documents

import persistence.services.Service
import persistence.tables.documents.ReturnRequest

import scala.concurrent.Future

class ReturnRequestService extends Service[ReturnRequest] {

  import persistence.db.SqlController.ctx._
  import persistence.db.SqlController.{ctx, exec}

  override def create(t: ReturnRequest): Future[Int] =
    ctx.run(quote(querySchema[ReturnRequest]("return_request_table"))
      .insert(
        _.date -> t.date,
        _.clientId -> t.clientId,
        _.description -> t.date,
        _.packages -> t.packages,
      ).onConflictIgnore.returning(_.id))

  override def update(t: ReturnRequest): Future[Boolean] =
    ctx.run(quote(querySchema[ReturnRequest]("return_request_table"))
      .filter(_.id == lift(t.id))
      .update(
        _.date -> t.date,
        _.clientId -> t.clientId,
        _.description -> t.date,
        _.packages -> t.packages,
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[ReturnRequest]("return_request_table")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[ReturnRequest]] =
    ctx.run(quote(querySchema[ReturnRequest]("return_request_table")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[ReturnRequest]] =
    ctx.run(quote(querySchema[ReturnRequest]("return_request_table")))
}
