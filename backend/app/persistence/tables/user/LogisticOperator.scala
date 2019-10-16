package persistence.tables.user

case class LogisticOperator(override val id: Int,
                            override val name: String,
                            override val phone: Int,
                            override val email: String,
                            addressId: Int) extends User

object LogisticOperator {
  import play.api.libs.json._
  implicit val student: OFormat[LogisticOperator] = Json.format[LogisticOperator]
}
