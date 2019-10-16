package persistence.services.product

import persistence.services.Service
import persistence.tables.product.Lot

import scala.concurrent.Future

object LotService extends Service[Lot] {
  override def create(t: Lot): Future[Int] = ???

  override def update(t: Lot): Future[Boolean] = ???

  override def delete(id: Int): Future[Boolean] = ???

  override def get(id: Int): Future[Option[Lot]] = ???

  override def getAll: Future[List[Lot]] = ???
}
