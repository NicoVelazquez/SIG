package presentation.user

import persistence.services.user.ManagerService
import persistence.tables.user.Manager
import play.api.mvc.ControllerComponents
import presentation.ABMController

import scala.concurrent.ExecutionContext.Implicits.global

class ManagerController(cc: ControllerComponents) extends ABMController[Manager](cc) {
  service = new ManagerService()
}
