package presentation.dto

import java.util.Date

case class ProductDTO(id: Int, name: String, date: Date, lot: Int, quantity: Int)
object ProductDTO {
  import play.api.libs.json._
  implicit val prod: OFormat[ProductDTO] = Json.format[ProductDTO]
}

case class ProductApplications(id: Int, name: String, date: Date, lot: Int, quantity: Int, weight: Double,
                               accepted: Option[Int], good: Option[Int])
object ProductApplication {
  import play.api.libs.json._
  implicit val prod: OFormat[ProductApplications] = Json.format[ProductApplications]
}
