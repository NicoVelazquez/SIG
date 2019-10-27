package persistence.tables.documents

import java.util.Date

import persistence.tables.DAOModel

case class ManagerApplication(override val id: Int,
                              managerId: Int,
                              trackingId: Int,
                              returnRequestId: Int,
                              date: Date) extends DAOModel

object ManagerApplication {
  import play.api.libs.json._
  implicit val manapp: OFormat[ManagerApplication] = Json.format[ManagerApplication]
}
