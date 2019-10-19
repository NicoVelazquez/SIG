package persistence.services.user

import persistence.services.Service
import persistence.tables.user.Client

import scala.concurrent.Future

class ClientService extends Service[Client] {

  import persistence.db.SqlController.ctx._
  import persistence.db.SqlController.{ctx, exec}

  override def create(t: Client): Future[Int] =
    ctx.run(quote(querySchema[Client]("client_table"))
      .insert(
        _.name -> t.name,
        _.password -> t.password,
        _.addressId -> t.addressId,
        _.email -> t.email,
        _.phone -> t.phone
      ).onConflictIgnore.returning(_.id))

  override def update(t: Client): Future[Boolean] =
    ctx.run(quote(querySchema[Client]("client_table"))
      .filter(_.id == lift(t.id))
      .update(
        _.name -> t.name,
        _.password -> t.password,
        _.addressId -> t.addressId,
        _.email -> t.email,
        _.phone -> t.phone
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[Client]("client_table")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[Client]] =
    ctx.run(quote(querySchema[Client]("client_table")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[Client]] =
    ctx.run(quote(querySchema[Client]("client_table")))
}
