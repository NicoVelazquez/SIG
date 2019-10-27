package presentation.dto

import java.util.Date

import persistence.tables.documents.Application

case class ApplicationDTO(date: Date, description: String, productDTO: List[ApplicationProductDTO])
object ApplicationDTO {
  import play.api.libs.json._
  implicit val app: OFormat[ApplicationDTO] = Json.format[ApplicationDTO]
}

case class ApplicationProductDTO(productId: Int, quantity: Int)
object ApplicationProductDTO {
  import play.api.libs.json._
  implicit val apppro: OFormat[ApplicationProductDTO] = Json.format[ApplicationProductDTO]
}

case class ApplicationClientResponse(id: Int, date: Date, state: String)
object ApplicationClientResponse {
  import play.api.libs.json._
  implicit val appcli: OFormat[ApplicationClientResponse] = Json.format[ApplicationClientResponse]
}

case class ApplicationsDTO(application: Application, productDTO: List[ProductDTO])
object ApplicationsDTO {
  import play.api.libs.json._
  implicit val apps: OFormat[ApplicationsDTO] = Json.format[ApplicationsDTO]
}
