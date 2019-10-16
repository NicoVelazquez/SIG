package persistence.tables.user

case class Manager(override val id: Int,
                   override val name: String,
                   override val phone: Int,
                   override val email: String,
                   password: String) extends User

object Manager {
  import play.api.libs.json._
  implicit val student: OFormat[Manager] = Json.format[Manager]
}
