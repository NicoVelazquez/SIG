package persistence.services.user

import persistence.services.Service
import persistence.tables.user.LogisticOperator

import scala.concurrent.Future

object LogisticOperatorService extends Service[LogisticOperator] {
  override def create(t: LogisticOperator): Future[Int] = ???

  override def update(t: LogisticOperator): Future[Boolean] = ???

  override def delete(id: Int): Future[Boolean] = ???

  override def get(id: Int): Future[Option[LogisticOperator]] = ???

  override def getAll: Future[List[LogisticOperator]] = ???
}
