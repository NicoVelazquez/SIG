package persistence.services.user

import persistence.services.Service
import persistence.tables.user.Manager

import scala.concurrent.Future

class ManagerService extends Service[Manager] {
  import settings.db.SqlController.ctx._
  import settings.db.SqlController.{ctx, exec}

  override def create(t: Manager): Future[Int] =
    ctx.run(quote(querySchema[Manager]("manager"))
      .insert(
        _.name -> lift(t.name),
        _.password -> lift(t.password),
        _.addressId -> lift(t.addressId),
        _.email -> lift(t.email),
        _.phone -> lift(t.phone)
      ).onConflictIgnore.returning(_.id))

  override def update(t: Manager): Future[Boolean] =
    ctx.run(quote(querySchema[Manager]("manager"))
      .filter(_.id == lift(t.id))
      .update(
        _.name -> lift(t.name),
        _.password -> lift(t.password),
        _.addressId -> lift(t.addressId),
        _.email -> lift(t.email),
        _.phone -> lift(t.phone)
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[Manager]("manager")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[Manager]] =
    ctx.run(quote(querySchema[Manager]("manager")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[Manager]] =
    ctx.run(quote(querySchema[Manager]("manager")))

  def getByEmail(email: String): Future[Option[Manager]] =
    ctx.run(quote(querySchema[Manager]("manager")).filter(_.email == lift(email))).map(_.headOption)
}
