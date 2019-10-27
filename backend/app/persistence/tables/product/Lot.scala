package persistence.tables.product

import java.util.Date

import persistence.tables.DAOModel

case class Lot(override val id: Int,
               name: String,
               creationDate: Date,
               price: Double,
               expirationDate: Date) extends DAOModel

object Lot {
  import play.api.libs.json._
  implicit val lot: OFormat[Lot] = Json.format[Lot]
}
