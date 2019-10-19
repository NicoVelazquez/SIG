package presentation.relations

import persistence.services.relations.ClientProductService
import persistence.tables.relations.ClientProduct
import play.api.mvc.ControllerComponents
import presentation.ABMController

import scala.concurrent.ExecutionContext.Implicits.global

class ClientProductController(cc: ControllerComponents) extends ABMController[ClientProduct](cc) {
  service = new ClientProductService()
}
