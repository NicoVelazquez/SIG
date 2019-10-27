package persistence.services.user

import persistence.services.Service
import persistence.tables.user.Controller

import scala.concurrent.Future

class ControllerService extends Service[Controller] {

  import settings.db.SqlController.ctx._
  import settings.db.SqlController.{ctx, exec}

  override def create(t: Controller): Future[Int] =
    ctx.run(quote(querySchema[Controller]("controller"))
      .insert(
        _.name -> lift(t.name),
        _.password -> lift(t.password),
        _.addressId -> lift(t.addressId),
        _.email -> lift(t.email),
        _.phone -> lift(t.phone)
      ).onConflictIgnore.returning(_.id))

  override def update(t: Controller): Future[Boolean] =
    ctx.run(quote(querySchema[Controller]("controller"))
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
    ctx.run(quote(querySchema[Controller]("controller")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[Controller]] =
    ctx.run(quote(querySchema[Controller]("controller")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[Controller]] =
    ctx.run(quote(querySchema[Controller]("controller")))

  def getByEmail(email: String): Future[Option[Controller]] =
    ctx.run(quote(querySchema[Controller]("controller")).filter(_.email == lift(email))).map(_.headOption)

}
