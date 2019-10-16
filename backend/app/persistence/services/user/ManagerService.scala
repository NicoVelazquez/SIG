package persistence.services.user

import persistence.services.Service
import persistence.tables.user.Manager

import scala.concurrent.Future

object ManagerService extends Service[Manager] {
  override def create(t: Manager): Future[Int] = ???

  override def update(t: Manager): Future[Boolean] = ???

  override def delete(id: Int): Future[Boolean] = ???

  override def get(id: Int): Future[Option[Manager]] = ???

  override def getAll: Future[List[Manager]] = ???
}
