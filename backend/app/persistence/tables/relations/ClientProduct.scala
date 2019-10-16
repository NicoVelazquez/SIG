package persistence.tables.relations

import java.util.Date

import persistence.tables.DAOModel

case class ClientProduct(override val id: Int,
                         date: Date,
                         quantity: Int,
                         clientId: Int,
                         productId: Int,
                         lotId: Int) extends DAOModel

object ClientProduct {
  import play.api.libs.json._
  implicit val student: OFormat[ClientProduct] = Json.format[ClientProduct]
}
