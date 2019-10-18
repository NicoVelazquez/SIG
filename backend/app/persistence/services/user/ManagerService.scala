package persistence.services.user

import persistence.services.Service
import persistence.tables.user.Manager

import scala.concurrent.Future

class ManagerService extends Service[Manager] {
  import persistence.db.SqlController.ctx._
  import persistence.db.SqlController.{ctx, exec}

  override def create(t: Manager): Future[Int] =
    ctx.run(quote(querySchema[Manager]("logistic_operator_table"))
      .insert(
        _.name -> t.name,
        _.email -> t.email,
        _.phone -> t.phone,
        _.password -> t.password
      ).onConflictIgnore.returning(_.id))

  override def update(t: Manager): Future[Boolean] =
    ctx.run(quote(querySchema[Manager]("logistic_operator_table"))
      .filter(_.id == lift(t.id))
      .update(
        _.name -> t.name,
        _.email -> t.email,
        _.phone -> t.phone,
        _.password -> t.password
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[Manager]("logistic_operator_table")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[Manager]] =
    ctx.run(quote(querySchema[Manager]("logistic_operator_table")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[Manager]] =
    ctx.run(quote(querySchema[Manager]("logistic_operator_table")))
}
