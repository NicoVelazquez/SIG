package persistence.tables.note

import java.util.Date

import persistence.tables.DAOModel

case class Note(override val id: Int,
                money: Long,
                date: Date,
                description: String,
                noteType: String) extends DAOModel

object Note {
  import play.api.libs.json._
  implicit val student: OFormat[Note] = Json.format[Note]
}
