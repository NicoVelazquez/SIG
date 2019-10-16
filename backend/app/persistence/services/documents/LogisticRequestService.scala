package persistence.services.documents

import persistence.services.Service
import persistence.tables.documents.LogisticRequest

import scala.concurrent.Future

object LogisticRequestService extends Service[LogisticRequest] {
  override def create(t: LogisticRequest): Future[Int] = ???

  override def update(t: LogisticRequest): Future[Boolean] = ???

  override def delete(id: Int): Future[Boolean] = ???

  override def get(id: Int): Future[Option[LogisticRequest]] = ???

  override def getAll: Future[List[LogisticRequest]] = ???
}
