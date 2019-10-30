package persistence.services.user

import java.util.Date

import persistence.services.Service
import persistence.tables.address.Address
import persistence.tables.product.{Lot, Product}
import persistence.tables.relations.ClientProduct
import persistence.tables.user.Client
import presentation.dto.ProductDTO

import scala.concurrent.Future

class ClientService extends Service[Client] {

  import settings.db.SqlController.ctx._
  import settings.db.SqlController.{ctx, exec}

  override def create(t: Client): Future[Int] =
    ctx.run(quote(querySchema[Client]("client"))
      .insert(
        _.name -> lift(t.name),
        _.password -> lift(t.password),
        _.addressId -> lift(t.addressId),
        _.email -> lift(t.email),
        _.phone -> lift(t.phone)
      ).onConflictIgnore.returning(_.id))

  override def update(t: Client): Future[Boolean] =
    ctx.run(quote(querySchema[Client]("client"))
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
    ctx.run(quote(querySchema[Client]("client")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[Client]] =
    ctx.run(quote(querySchema[Client]("client")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[Client]] =
    ctx.run(quote(querySchema[Client]("client")))

  def getByEmail(email: String): Future[Option[Client]] =
    ctx.run(quote(querySchema[Client]("client")).filter(_.email == lift(email))).map(_.headOption)

  def getClientWithAddress(clientId: Int): Future[Option[(Client, Address)]] = {
    val q = quote {
      for {
        a <- querySchema[Address]("address")
        c <- querySchema[Client]("client") if a.id == c.addressId
      } yield (c, a)
    }
    ctx.run(q.filter(_._1.id == lift(clientId))).map(_.headOption)
  }

  def getClientProducts(clientId: Int): Future[List[ProductDTO]] = {
    val q = quote {
      for {
        a <- querySchema[ClientProduct]("client_product")
        p <- querySchema[Product]("product") if p.id == a.productId
        l <- querySchema[Lot]("lot") if l.id == p.lotId
      } yield (a, p, l)
    }
    ctx.run(q.filter(_._1.clientId == lift(clientId)))
      .map(_.map(cp => ProductDTO(cp._2.id, cp._2.name, cp._3.expirationDate, cp._3.name, cp._1.quantity)).filter(_.quantity > 0))
  }

}
