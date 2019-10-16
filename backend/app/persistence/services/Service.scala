package persistence.services

import persistence.tables.DAOModel

import scala.concurrent.Future

trait Service[T <: DAOModel] {

  def create(t: T): Future[Int]

  def update(t: T): Future[Boolean]

  def delete(id: Int): Future[Boolean]

  def get(id: Int): Future[Option[T]]

  def getAll: Future[List[T]]
}
