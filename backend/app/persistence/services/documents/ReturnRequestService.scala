package persistence.services.documents

import persistence.services.Service
import persistence.tables.documents.ReturnRequest

import scala.concurrent.Future

object ReturnRequestService extends Service[ReturnRequest] {
  override def create(t: ReturnRequest): Future[Int] = ???

  override def update(t: ReturnRequest): Future[Boolean] = ???

  override def delete(id: Int): Future[Boolean] = ???

  override def get(id: Int): Future[Option[ReturnRequest]] = ???

  override def getAll: Future[List[ReturnRequest]] = ???
}
