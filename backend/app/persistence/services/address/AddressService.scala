package persistence.services.address

import persistence.services.Service
import persistence.tables.address.Address

import scala.concurrent.Future

class AddressService extends Service[Address]{
  import settings.db.SqlController.ctx._
  import settings.db.SqlController.{ctx, exec}

  override def create(t: Address): Future[Int] =
    ctx.run(quote(querySchema[Address]("address"))
      .insert(
        _.city -> lift(t.city),
        _.country -> lift(t.country),
        _.description -> lift(t.description),
        _.street -> lift(t.street),
        _.streetHeight -> lift(t.streetHeight),
        _.cost -> lift(t.cost)
      ).onConflictIgnore.returning(_.id))

  override def update(t: Address): Future[Boolean] =
    ctx.run(quote(querySchema[Address]("address"))
      .filter(_.id == lift(t.id))
      .update(
        _.city -> lift(t.city),
        _.country -> lift(t.country),
        _.description -> lift(t.description),
        _.street -> lift(t.street),
        _.streetHeight -> lift(t.streetHeight),
        _.cost -> lift(t.cost)
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[Address]("address")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[Address]] =
    ctx.run(quote(querySchema[Address]("address")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[Address]] =
    ctx.run(quote(querySchema[Address]("address")))
}
