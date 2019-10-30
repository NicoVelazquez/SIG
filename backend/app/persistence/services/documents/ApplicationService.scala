package persistence.services.documents

import persistence.services.Service
import persistence.tables.documents.Application
import persistence.tables.product.{Lot, Product}
import persistence.tables.relations.ProductApplication
import persistence.tables.user.Client
import presentation.dto.{Applications, ProductApplications}

import scala.concurrent.Future

class ApplicationService extends Service[Application] {

  import settings.db.SqlController.ctx._
  import settings.db.SqlController.{ctx, exec}

  override def create(t: Application): Future[Int] =
    ctx.run(quote(querySchema[Application]("application"))
      .insert(
        _.date -> lift(t.date),
        _.clientId -> lift(t.clientId),
        _.description -> lift(t.description),
        _.cost -> lift(t.cost),
        _.state -> lift(t.state),
        _.observation -> lift(t.observation),
        _.collectionDate -> lift(t.collectionDate),
        _.operator_acceptance_date -> lift(t.operator_acceptance_date)
      ).onConflictIgnore.returning(_.id))

  override def update(t: Application): Future[Boolean] =
    ctx.run(quote(querySchema[Application]("application"))
      .filter(_.id == lift(t.id))
      .update(
        _.date -> lift(t.date),
        _.clientId -> lift(t.clientId),
        _.description -> lift(t.description),
        _.cost -> lift(t.cost),
        _.state -> lift(t.state),
        _.observation -> lift(t.observation),
        _.collectionDate -> lift(t.collectionDate),
        _.operator_acceptance_date -> lift(t.operator_acceptance_date)
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[Application]("application")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[Application]] =
    ctx.run(quote(querySchema[Application]("application")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[Application]] = ctx.run(quote(querySchema[Application]("application")))

  def getAllApplications: Future[List[Applications]] = {
    val q = quote {
      for {
        a <- querySchema[Application]("application")
        p <- querySchema[ProductApplication]("product_application") if a.id == p.applicationId
        c <- querySchema[Product]("product") if c.id == p.productId
        l <- querySchema[Lot]("lot") if l.id == c.lotId
        d <- querySchema[Client]("client") if d.id == a.clientId
      } yield (a, p, c, l, d)
    }
    ctx.run(q)
      .map(_.map((cp: (Application, ProductApplication, Product, Lot, Client)) =>
        applicationToApplications(cp._1,
          List(ProductApplications(cp._2.id, cp._3.id, cp._3.name, cp._4.expirationDate, cp._4.name, cp._2.quantity, cp._3.weight,
            cp._4.price, cp._2.accepted, cp._2.good, cp._2.received)), cp._5.name)))
      .map(groupByIdApplications)
  }

  private def applicationToApplications(application: Application, products: List[ProductApplications], client: String): Applications = {
    Applications(application.id, application.clientId, client, application.date, application.cost, application.state, application.description,
      application.observation, application.operator_acceptance_date, application.collectionDate, products)
  }

  private def groupByIdApplications(list: List[Applications]): List[Applications] = {
    list.groupBy(_.id).map((e: (Index, List[Applications])) => {
      val application = e._2.head
      val products: List[ProductApplications] = e._2.flatMap(_.products)
      Applications(application.id, application.clientId, application.client, application.date, application.cost, application.state, application.description,
        application.observation, application.operator_acceptance_date, application.collectionDate, products)
    }).toList
  }

}
