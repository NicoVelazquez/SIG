package persistence.tables.documents

import java.util.Date

import persistence.tables.DAOModel

case class ReturnRequest(override val id: Int,
                         date: Date,
                         description: String,
                         packages: Int,
                         clientId: Int) extends DAOModel

object ReturnRequest {
  import play.api.libs.json._
  implicit val student: OFormat[ReturnRequest] = Json.format[ReturnRequest]
}
