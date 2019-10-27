package persistence.tables.address

import persistence.tables.DAOModel

case class Address(override val id: Int,
                   country: String,
                   city: String,
                   street: String,
                   streetHeight: String,
                   description: String,
                   cost: Int) extends DAOModel

object Address {
  import play.api.libs.json._
  implicit val address: OFormat[Address] = Json.format[Address]
}
