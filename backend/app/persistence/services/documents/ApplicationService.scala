package persistence.services.documents

import java.util.Date

import persistence.services.Service
import persistence.tables.documents.Application
import persistence.tables.product.{Lot, Product}
import persistence.tables.relations.ProductApplication
import persistence.tables.user.Client
import presentation.dto.{ApplicationResponse, Applications, ProductApplications, ProductDTO}

import scala.concurrent.Future

class ApplicationService extends Service[Application] {

  import settings.db.SqlController.ctx._
  import settings.db.SqlController.{ctx, exec}

  override def create(t: Application): Future[Int] =
    ctx.run(quote(querySchema[Application]("application"))
      .insert(
        _.date -> lift(t.date),
        _.clientId -> lift(t.clientId),
        _.client -> lift(t.client),
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
        _.client -> lift(t.client),
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

  def getClientApplications(clientId: Int): Future[List[ApplicationResponse]] = {
    val q = quote {
      for {
        a <- querySchema[Application]("application") if a.clientId == lift(clientId)
        p <- querySchema[ProductApplication]("product_application") if a.id == p.applicationId
        c <- querySchema[Product]("product") if c.id == p.productId
      } yield (a, p, c)
    }
    ctx.run(q)
      .map(_.map((cp: (Application, ProductApplication, Product)) =>
        applicationToApplicationsResponse(cp._1, List(ProductDTO(cp._3.id, cp._3.name, new Date(), cp._3.lotId, cp._2.quantity)), cp._1.client)))
      .map(groupById)
  }

  def getAllApplications: Future[List[Applications]] = {
    val q = quote {
      for {
        a <- querySchema[Application]("application")
        p <- querySchema[ProductApplication]("product_application") if a.id == p.applicationId
        c <- querySchema[Product]("product") if c.id == p.productId
        l <- querySchema[Lot]("lot") if l.id == c.lotId
      } yield (a, p, c, l)
    }
    ctx.run(q)
      .map(_.map((cp: (Application, ProductApplication, Product, Lot)) =>
        applicationToApplications(cp._1,
          List(ProductApplications(cp._2.id, cp._3.id, cp._3.name, cp._4.expirationDate, cp._4.name, cp._2.quantity, cp._3.weight,
            cp._4.price, cp._2.accepted, cp._2.good)), cp._1.client)))
      .map(groupByIdApplications)
  }

  private def applicationToApplicationsResponse(application: Application, products: List[ProductDTO], client: String): ApplicationResponse = {
    ApplicationResponse(application.id, client, application.date, application.cost, application.state, application.description,
      application.observation, application.operator_acceptance_date, application.collectionDate, products)
  }

  private def applicationToApplications(application: Application, products: List[ProductApplications], client: String): Applications = {
    Applications(application.id, client, application.date, application.cost, application.state, application.description,
      application.observation, application.operator_acceptance_date, application.collectionDate, products)
  }

  private def groupById(list: List[ApplicationResponse]): List[ApplicationResponse] = {
    list.groupBy(_.id).map((e: (Int, List[ApplicationResponse])) => {
      val application = e._2.head
      val products: List[ProductDTO] = e._2.flatMap(_.products)
      ApplicationResponse(application.id, application.client, application.date, application.cost, application.state, application.description,
        application.observation, application.operator_acceptance_date, application.collectionDate, products)
    }).toList
  }

  private def groupByIdApplications(list: List[Applications]): List[Applications] = {
    list.groupBy(_.id).map((e: (Index, List[Applications])) => {
      val application = e._2.head
      val products: List[ProductApplications] = e._2.flatMap(_.products)
      Applications(application.id, application.client, application.date, application.cost, application.state, application.description,
        application.observation, application.operator_acceptance_date, application.collectionDate, products)
    }).toList
  }

}

case class EverythingJoin(application: Application, productApplication: ProductApplication, product: Product, lot: Lot, client: Client)
