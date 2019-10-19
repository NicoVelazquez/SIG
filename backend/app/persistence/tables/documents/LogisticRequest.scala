package persistence.tables.documents

import java.util.Date

import persistence.tables.DAOModel

case class LogisticRequest(override val id: Int,
                           logisticOperatorId: Int,
                           trackingId: Int,
                           returnRequestId: Int,
                           date: Date) extends DAOModel

object LogisticRequest {
  import play.api.libs.json._
  implicit val student: OFormat[LogisticRequest] = Json.format[LogisticRequest]
}
