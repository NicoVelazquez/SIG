package persistence.services.user

import persistence.services.Service
import persistence.tables.user.Client

import scala.concurrent.Future

object ClientService extends Service[Client] {
  override def create(t: Client): Future[Int] = ???

  override def update(t: Client): Future[Boolean] = ???

  override def delete(id: Int): Future[Boolean] = ???

  override def get(id: Int): Future[Option[Client]] = ???

  override def getAll: Future[List[Client]] = ???
}
