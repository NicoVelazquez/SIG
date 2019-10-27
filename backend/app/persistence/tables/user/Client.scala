package persistence.tables.user

case class Client(override val id: Int,
                  override val name: String,
                  override val phone: Int,
                  override val email: String,
                  override val password: String,
                  override val addressId: Int) extends User

object Client {
  import play.api.libs.json._
  implicit val client: OFormat[Client] = Json.format[Client]
}
