package persistence.services.relations

import persistence.services.Service
import persistence.tables.relations.ClientProduct

import scala.concurrent.Future

object ClientProductService extends Service[ClientProduct] {
  override def create(t: ClientProduct): Future[Int] = ???

  override def update(t: ClientProduct): Future[Boolean] = ???

  override def delete(id: Int): Future[Boolean] = ???

  override def get(id: Int): Future[Option[ClientProduct]] = ???

  override def getAll: Future[List[ClientProduct]] = ???
}
