package persistence.tables.user

case class Manager(override val id: Int,
                   override val name: String,
                   override val phone: Int,
                   override val email: String,
                   override val password: String,
                   override val addressId: Int) extends User

object Manager {
  import play.api.libs.json._
  implicit val manager: OFormat[Manager] = Json.format[Manager]
}
