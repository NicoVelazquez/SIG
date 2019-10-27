package persistence.services.documents

import java.util.Date

import persistence.services.Service
import persistence.tables.documents.Application
import persistence.tables.product.Product
import persistence.tables.relations.ProductApplication
import presentation.dto.{ApplicationsDTO, ProductDTO}

import scala.collection.immutable
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

//  def getClientApplications(clientId: Int) = {
//    ctx.run(quote(querySchema[Application]("application").filter(_.clientId == lift(clientId))
//      .join(querySchema[ProductApplication]("product_application")).on(_.id == _.applicationId)))
//      .map(groupAndListApplicationProducts)
//  }

  def getClientApplications(clientId: Int): Future[List[ApplicationsDTO]] = {
    val q = quote {
      for {
        a <- querySchema[Application]("application") if a.clientId == lift(clientId)
        p <- querySchema[ProductApplication]("product_application") if a.id == p.applicationId
        c <- querySchema[Product]("product") if p.id == p.productId
      } yield (a, p, c)
    }
    ctx.run(q)
      .map(_.map((cp: (Application, ProductApplication, Product)) =>
        ApplicationsDTO(cp._1, List(ProductDTO(cp._3.id, cp._3.name, new Date(), cp._3.lotId, cp._2.quantity)))))
  }

  def getProductsForApplication(applicationId: Int): Future[Iterable[ApplicationsDTO]] = {
    ctx.run(quote(querySchema[ProductApplication]("product_application"))
      .join(querySchema[Product]("product")).on(_.productId == _.id)
      .join(quote(querySchema[Application]("application")).filter(_.id == lift(applicationId))).on(_._1.applicationId == _.id))
      .map(c => c.groupBy(_._2).map(a =>
        ApplicationsDTO(a._1, a._2.map(pp => ProductDTO(pp._1._2.id, pp._1._2.name, new Date(), pp._1._2.lotId, pp._1._1.quantity))))
      )
  }

  def getAllApplicationWithProducts: Future[Iterable[ApplicationsDTO]] = {
    ctx.run(quote(querySchema[ProductApplication]("product_application"))
      .join(querySchema[Product]("product")).on(_.productId == _.id)
      .join(quote(querySchema[Application]("application"))).on(_._1.applicationId == _.id))
      .map(c => c.groupBy(_._2).map((a: (Application, immutable.Seq[((ProductApplication, Product), Application)])) =>
          ApplicationsDTO(a._1, a._2.map(pp => ProductDTO(pp._1._2.id, pp._1._2.name, new Date(), pp._1._2.lotId, pp._1._1.quantity)).toList))
      )
  }

}
