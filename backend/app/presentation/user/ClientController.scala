package presentation.user

import persistence.services.user.ClientService
import persistence.tables.user.Client
import play.api.mvc.ControllerComponents
import presentation.ABMController

import scala.concurrent.ExecutionContext.Implicits.global

class ClientController(cc: ControllerComponents) extends ABMController[Client](cc) {
  service = new ClientService()
}
