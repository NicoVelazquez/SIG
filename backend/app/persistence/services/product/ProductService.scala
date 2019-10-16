package persistence.services.product

import persistence.services.Service
import persistence.tables.product.Product

import scala.concurrent.Future

object ProductService extends Service[Product] {
  override def create(t: Product): Future[Int] = ???

  override def update(t: Product): Future[Boolean] = ???

  override def delete(id: Int): Future[Boolean] = ???

  override def get(id: Int): Future[Option[Product]] = ???

  override def getAll: Future[List[Product]] = ???
}
