package persistence.tables

case class Address(override val id: Int,
                   country: String,
                   city: String,
                   street: String,
                   streetHeight: String,
                   description: String) extends DAOModel

object Address {
  import play.api.libs.json._
  implicit val student: OFormat[Address] = Json.format[Address]
}
