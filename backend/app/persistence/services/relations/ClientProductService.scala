package persistence.services.relations

import persistence.services.Service
import persistence.tables.relations.ClientProduct

import scala.concurrent.Future

class ClientProductService extends Service[ClientProduct] {

  import persistence.db.SqlController.ctx._
  import persistence.db.SqlController.{ctx, exec}

  override def create(t: ClientProduct): Future[Int] =
    ctx.run(quote(querySchema[ClientProduct]("client_product_table"))
      .insert(
        _.clientId -> t.clientId,
        _.date -> t.date,
        _.lotId -> t.lotId,
        _.productId -> t.productId,
        _.quantity -> t.quantity
      ).onConflictIgnore.returning(_.id))

  override def update(t: ClientProduct): Future[Boolean] =
    ctx.run(quote(querySchema[ClientProduct]("client_product_table"))
      .filter(_.id == lift(t.id))
      .update(
        _.clientId -> t.clientId,
        _.date -> t.date,
        _.lotId -> t.lotId,
        _.productId -> t.productId,
        _.quantity -> t.quantity
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[ClientProduct]("client_product_table")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[ClientProduct]] =
    ctx.run(quote(querySchema[ClientProduct]("client_product_table")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[ClientProduct]] =
    ctx.run(quote(querySchema[ClientProduct]("client_product_table")))
}
