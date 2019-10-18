package persistence.services.relations

import persistence.services.Service
import persistence.tables.relations.ProductRequest

import scala.concurrent.Future

class ProductRequestService extends Service[ProductRequest] {
  import persistence.db.SqlController.ctx._
  import persistence.db.SqlController.{ctx, exec}

  override def create(t: ProductRequest): Future[Int] =
    ctx.run(quote(querySchema[ProductRequest]("product_request_table"))
      .insert(
        _.productId -> t.productId,
        _.productState -> t.productState,
        _.quantity -> t.quantity,
        _.returnRequestId -> t.returnRequestId
      ).onConflictIgnore.returning(_.id))

  override def update(t: ProductRequest): Future[Boolean] =
    ctx.run(quote(querySchema[ProductRequest]("product_request_table"))
      .filter(_.id == lift(t.id))
      .update(
        _.productId -> t.productId,
        _.productState -> t.productState,
        _.quantity -> t.quantity,
        _.returnRequestId -> t.returnRequestId
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[ProductRequest]("product_request_table")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[ProductRequest]] =
    ctx.run(quote(querySchema[ProductRequest]("product_request_table")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[ProductRequest]] =
    ctx.run(quote(querySchema[ProductRequest]("product_request_table")))
}
