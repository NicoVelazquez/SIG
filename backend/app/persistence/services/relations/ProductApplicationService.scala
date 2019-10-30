package persistence.services.relations

import persistence.services.Service
import persistence.tables.relations.ProductApplication

import scala.concurrent.Future

class ProductApplicationService extends Service[ProductApplication] {

  import settings.db.SqlController.ctx._
  import settings.db.SqlController.{ctx, exec}

  override def create(t: ProductApplication): Future[Int] =
    ctx.run(quote(querySchema[ProductApplication]("product_application"))
      .insert(
        _.productId -> lift(t.productId),
        _.productState -> lift(t.productState),
        _.quantity -> lift(t.quantity),
        _.applicationId -> lift(t.applicationId),
        _.accepted -> lift(t.accepted),
        _.good -> lift(t.good),
        _.reason -> lift(t.reason),
        _.received -> lift(t.received)
      ).onConflictIgnore.returning(_.id))

  override def update(t: ProductApplication): Future[Boolean] =
    ctx.run(quote(querySchema[ProductApplication]("product_application"))
      .filter(_.id == lift(t.id))
      .update(
        _.productId -> lift(t.productId),
        _.productState -> lift(t.productState),
        _.quantity -> lift(t.quantity),
        _.applicationId -> lift(t.applicationId),
        _.accepted -> lift(t.accepted),
        _.good -> lift(t.good),
        _.received -> lift(t.received)
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[ProductApplication]("product_application")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[ProductApplication]] =
    ctx.run(quote(querySchema[ProductApplication]("product_application")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[ProductApplication]] =
    ctx.run(quote(querySchema[ProductApplication]("product_application")))

  def getByApplicationId(applicationId: Int): Future[Option[List[ProductApplication]]] = {
    ctx.run(quote(querySchema[ProductApplication]("product_application"))
      .filter(_.applicationId == lift(applicationId)))
      .map {
        case list: Seq[ProductApplication] => Some(list)
        case _ => None
      }
  }

  def deleteWithApplicationId(appId: Int): Future[Boolean] = {
    ctx.run(quote(querySchema[ProductApplication]("product_application"))
      .filter(_.applicationId == lift(appId)).delete)
      .map {
        case 1 => true
        case _ => false
      }
  }
}
