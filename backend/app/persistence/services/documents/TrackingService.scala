package persistence.services.documents

import persistence.services.Service
import persistence.tables.documents.Tracking

import scala.concurrent.Future

object TrackingService extends Service[Tracking] {
  override def create(t: Tracking): Future[Int] = ???

  override def update(t: Tracking): Future[Boolean] = ???

  override def delete(id: Int): Future[Boolean] = ???

  override def get(id: Int): Future[Option[Tracking]] = ???

  override def getAll: Future[List[Tracking]] = ???
}
