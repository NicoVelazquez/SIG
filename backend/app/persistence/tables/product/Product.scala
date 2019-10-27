package persistence.tables.product

import persistence.tables.DAOModel

case class Product(override val id: Int,
                   name: String,
                   description: String,
                   weight: Double,
                   lotId: Int) extends DAOModel

object Product {
  import play.api.libs.json._
  implicit val product: OFormat[Product] = Json.format[Product]
}
