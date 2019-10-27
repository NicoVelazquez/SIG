package persistence.tables.user

import persistence.tables.DAOModel

trait User extends DAOModel {
  val name: String
  val phone: Int
  val email: String
  val password: String
  val addressId: Int
}
