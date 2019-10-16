package persistence.services.note

import persistence.services.Service
import persistence.tables.note.Note

import scala.concurrent.Future

object NoteService extends Service[Note] {
  override def create(t: Note): Future[Int] = ???

  override def update(t: Note): Future[Boolean] = ???

  override def delete(id: Int): Future[Boolean] = ???

  override def get(id: Int): Future[Option[Note]] = ???

  override def getAll: Future[List[Note]] = ???
}
