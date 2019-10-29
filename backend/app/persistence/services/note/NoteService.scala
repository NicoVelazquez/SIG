package persistence.services.note

import persistence.services.Service
import persistence.tables.note.Note

import scala.concurrent.Future

class NoteService extends Service[Note] {


  import settings.db.SqlController.ctx._
  import settings.db.SqlController.{ctx, exec}

  override def create(t: Note): Future[Int] =
  ctx.run(quote(querySchema[Note]("note"))
    .insert(
  _.date -> lift(t.date),
  _.descriptionCredit -> lift(t.descriptionCredit),
  _.priceCredit -> lift(t.priceCredit),
  _.descriptionDebit -> lift(t.descriptionDebit),
  _.priceDebit -> lift(t.priceDebit),
  _.applicationId -> lift(t.applicationId)
  ).onConflictIgnore.returning(_.id))

  override def update(t: Note): Future[Boolean] =
  ctx.run(quote(querySchema[Note]("note"))
    .filter(_.id == lift(t.id))
    .update(
  _.date -> lift(t.date),
  _.descriptionCredit -> lift(t.descriptionCredit),
  _.priceCredit -> lift(t.priceCredit),
  _.descriptionDebit -> lift(t.descriptionDebit),
  _.priceDebit -> lift(t.priceDebit),
  _.applicationId -> lift(t.applicationId)
  )
  ).map {
    case 1 => true
    case _ => false
  }

  override def delete(id: Int): Future[Boolean] =
  ctx.run(quote(querySchema[Note]("note")).filter(_.id == lift(id)).delete).map {
    case 1 => true
    case _ => false
  }

  override def get(id: Int): Future[Option[Note]] =
  ctx.run(quote(querySchema[Note]("note")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[Note]] =
  ctx.run(quote(querySchema[Note]("note")))

  def getApplicationNote(applicationId: Int): Future[Option[Note]] = {
    ctx.run(quote(querySchema[Note]("note")).filter(_.applicationId == lift(applicationId))).map(_.headOption)
  }

  def deleteByApplicationId(applicationId: Int) =
    ctx.run(quote(querySchema[Note]("note")).filter(_.applicationId == lift(applicationId)).delete).map {
      case 1 => true
      case _ => false
    }

}
