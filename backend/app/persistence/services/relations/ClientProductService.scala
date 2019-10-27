package persistence.services.relations

import persistence.services.Service
import persistence.tables.relations.ClientProduct
import persistence.tables.product.Product

import scala.concurrent.Future

class ClientProductService extends Service[ClientProduct] {

  import settings.db.SqlController.ctx._
  import settings.db.SqlController.{ctx, exec}

  override def create(t: ClientProduct): Future[Int] =
    ctx.run(quote(querySchema[ClientProduct]("client_product"))
      .insert(
        _.clientId -> lift(t.clientId),
        _.productId -> lift(t.productId),
        _.quantity -> lift(t.quantity)
      ).onConflictIgnore.returning(_.id))

  override def update(t: ClientProduct): Future[Boolean] =
    ctx.run(quote(querySchema[ClientProduct]("client_product"))
      .filter(_.id == lift(t.id))
      .update(
        _.clientId -> lift(t.clientId),
        _.productId -> lift(t.productId),
        _.quantity -> lift(t.quantity)
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[ClientProduct]("client_product")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[ClientProduct]] =
    ctx.run(quote(querySchema[ClientProduct]("client_product")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[ClientProduct]] =
    ctx.run(quote(querySchema[ClientProduct]("client_product")))

  def getClientProducts(clientId: Int): Future[Option[List[ClientProduct]]] = {
    ctx.run(quote(querySchema[ClientProduct]("client_product")).filter(_.clientId == lift(clientId)))
      .map {
        case list: Seq[ClientProduct] => Some(list)
        case _ => None
      }
  }

  def getProductsFromClient(clientId: Int, productId: Int): Future[ClientProduct] = {
    ctx.run(quote(querySchema[ClientProduct]("client_product"))
      .filter(cp => {
        cp.clientId == lift(clientId) && cp.productId == lift(productId)
      })).map(_.head)
  }

  def restoreProductsFromClient(clientId: Int, productId: Int, quantity: Int): Future[Boolean] = {
    ctx.run(quote(querySchema[ClientProduct]("client_product"))
      .filter(cp => cp.clientId == lift(clientId) && cp.productId == lift(productId))
      .update(
        _.quantity -> lift(quantity)
      )
    ).map {
      case 1 => true
      case _ => false
    }
  }
}
