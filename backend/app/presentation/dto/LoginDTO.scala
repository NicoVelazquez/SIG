package presentation.dto

case class LoginDTO(email: String, password: String)
object LoginDTO {
  import play.api.libs.json._
  implicit val login: OFormat[LoginDTO] = Json.format[LoginDTO]
}

case class LoginResponse(id: Int, role: String)
object LoginResponse {
  import play.api.libs.json._
  implicit val loginre: OFormat[LoginResponse] = Json.format[LoginResponse]
}
