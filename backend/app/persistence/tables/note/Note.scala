package persistence.tables.note

import java.util.Date

import persistence.tables.DAOModel

case class Note(override val id: Int,
                date: Date,
                priceCredit: Long,
                descriptionCredit: String,
                descriptionDebit: String,
                priceDebit: Long,
                applicationId: Int) extends DAOModel

object Note {
  import play.api.libs.json._
  implicit val note: OFormat[Note] = Json.format[Note]
}
