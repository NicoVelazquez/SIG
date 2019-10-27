package persistence.tables.relations

import persistence.tables.DAOModel

case class ClientProduct(override val id: Int,
                         quantity: Int,
                         clientId: Int,
                         productId: Int) extends DAOModel

object ClientProduct {
  import play.api.libs.json._
  implicit val clientproduct: OFormat[ClientProduct] = Json.format[ClientProduct]
}
