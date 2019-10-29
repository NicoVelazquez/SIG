package persistence.tables.documents

import java.util.Date

import persistence.tables.DAOModel
import presentation.dto.ProductApplications

case class Application(override val id: Int,
                       clientId: Int,
                       date: Date,
                       cost: Int,
                       state: String,
                       description: String,
                       observation: Option[String],
                       operator_acceptance_date: Option[Date],
                       collectionDate: Option[Date]) extends DAOModel

object Application {
  import play.api.libs.json._
  implicit val application: OFormat[Application] = Json.format[Application]
}

case class ApplicationUpdate(override val id: Int,
                             clientId: Int,
                             client: String,
                             date: Date,
                             cost: Int,
                             state: String,
                             description: String,
                             observation: Option[String],
                             operator_acceptance_date: Option[Date],
                             collectionDate: Option[Date],
                             product: List[ProductApplications]) extends DAOModel
object ApplicationUpdate {
  import play.api.libs.json._
  implicit val applicationUpdate: OFormat[ApplicationUpdate] = Json.format[ApplicationUpdate]
}
