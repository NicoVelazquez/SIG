package persistence.tables.product

import java.util.Date

import persistence.tables.DAOModel

case class Lot(override val id: Int,
               name: String,
               date: Date) extends DAOModel

object Lot {
  import play.api.libs.json._
  implicit val student: OFormat[Lot] = Json.format[Lot]
}
