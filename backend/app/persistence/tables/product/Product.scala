package persistence.tables.product

import persistence.tables.DAOModel

case class Product(override val id: Int,
                   name: String,
                   description: String,
                   weight: String) extends DAOModel

object Product {
  import play.api.libs.json._
  implicit val student: OFormat[Product] = Json.format[Product]
}
