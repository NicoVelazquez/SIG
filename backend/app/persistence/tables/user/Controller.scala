package persistence.tables.user

import persistence.tables.DAOModel

case class Controller(override val id: Int,
                      override val name: String,
                      override val phone: Int,
                      override val email: String,
                      override val password: String,
                      override val addressId: Int) extends User

object Controller {
  import play.api.libs.json._
  implicit val controller: OFormat[Controller] = Json.format[Controller]
}
