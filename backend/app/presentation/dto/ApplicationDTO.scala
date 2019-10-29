package presentation.dto

import java.util.Date

import persistence.tables.documents.Application

case class ApplicationDTO(date: Date, description: String, products: List[ApplicationProductDTO])
object ApplicationDTO {
  import play.api.libs.json._
  implicit val app: OFormat[ApplicationDTO] = Json.format[ApplicationDTO]
}

case class ApplicationProductDTO(productId: Int, quantity: Int)
object ApplicationProductDTO {
  import play.api.libs.json._
  implicit val apppro: OFormat[ApplicationProductDTO] = Json.format[ApplicationProductDTO]
}

case class ApplicationResponse(id: Int,
                               client: String,
                               date: Date,
                               cost: Int,
                               state: String,
                               description: String,
                               observation: Option[String],
                               operator_acceptance_date: Option[Date],
                               collectionDate: Option[Date],
                               products: List[ProductDTO])
object ApplicationResponse {
  import play.api.libs.json._
  implicit val appcli: OFormat[ApplicationResponse] = Json.format[ApplicationResponse]
}

case class Applications(id: Int,
                           client: String,
                           date: Date,
                           cost: Int,
                           state: String,
                           description: String,
                           observation: Option[String],
                           operator_acceptance_date: Option[Date],
                           collectionDate: Option[Date],
                           products: List[ProductApplications])
object Applications {
  import play.api.libs.json._
  implicit val apps: OFormat[Applications] = Json.format[Applications]
}
