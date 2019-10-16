package persistence.tables.user

case class Client(override val id: Int,
                  override val name: String,
                  override val phone: Int,
                  override val email: String,
                  password: String,
                  addressId: Int) extends User

object Client {
  import play.api.libs.json._
  implicit val student: OFormat[Client] = Json.format[Client]
}
