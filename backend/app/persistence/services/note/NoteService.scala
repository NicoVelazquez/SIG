package persistence.services.note

import persistence.services.Service
import persistence.tables.note.Note

import scala.concurrent.Future

class NoteService extends Service[Note] {
  import persistence.db.SqlController.ctx._
  import persistence.db.SqlController.{ctx, exec}

  override def create(t: Note): Future[Int] =
    ctx.run(quote(querySchema[Note]("note_table"))
      .insert(
        _.date -> t.date,
        _.description -> t.description,
        _.money -> t.money,
        _.noteType -> t.noteType
      ).onConflictIgnore.returning(_.id))

  override def update(t: Note): Future[Boolean] =
    ctx.run(quote(querySchema[Note]("note_table"))
      .filter(_.id == lift(t.id))
      .update(
        _.date -> t.date,
        _.description -> t.description,
        _.money -> t.money,
        _.noteType -> t.noteType
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[Note]("note_table")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[Note]] =
    ctx.run(quote(querySchema[Note]("note_table")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[Note]] =
    ctx.run(quote(querySchema[Note]("note_table")))
}
