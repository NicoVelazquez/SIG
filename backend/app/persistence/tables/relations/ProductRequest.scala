package persistence.tables.relations

import persistence.tables.DAOModel

case class ProductRequest(override val id: Int,
                          quantity: Int,
                          returnRequestId: Int,
                          productId: Int,
                          productState: Int) extends DAOModel

object ProductRequest {
  import play.api.libs.json._
  implicit val student: OFormat[ProductRequest] = Json.format[ProductRequest]
}
