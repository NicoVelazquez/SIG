package persistence.tables.documents

import persistence.tables.DAOModel

case class Tracking(override val id: Int,
                    trackingNumber: Int,
                    addressId: Int,
                    trackingState: String) extends DAOModel

object Tracking {
  import play.api.libs.json._
  implicit val student: OFormat[Tracking] = Json.format[Tracking]
}
