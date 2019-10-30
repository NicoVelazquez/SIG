package persistence.tables.relations

import persistence.tables.DAOModel

case class ProductApplication(override val id: Int,
                              quantity: Int,
                              applicationId: Int,
                              productId: Int,
                              reason: String,
                              productState: Option[String],
                              accepted: Option[Int],
                              good: Option[Int],
                              received: Option[Int]) extends DAOModel

object ProductApplication {
  import play.api.libs.json._
  implicit val productapplication: OFormat[ProductApplication] = Json.format[ProductApplication]
}
