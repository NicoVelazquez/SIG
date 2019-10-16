package persistence.services.relations

import persistence.services.Service
import persistence.tables.relations.ProductRequest

import scala.concurrent.Future

object ProductRequestService extends Service[ProductRequest] {
  override def create(t: ProductRequest): Future[Int] = ???

  override def update(t: ProductRequest): Future[Boolean] = ???

  override def delete(id: Int): Future[Boolean] = ???

  override def get(id: Int): Future[Option[ProductRequest]] = ???

  override def getAll: Future[List[ProductRequest]] = ???
}
