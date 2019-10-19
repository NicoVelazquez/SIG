package presentation.relations

import persistence.services.relations.ProductRequestService
import persistence.tables.relations.ProductRequest
import play.api.mvc.ControllerComponents
import presentation.ABMController

import scala.concurrent.ExecutionContext.Implicits.global

class ProductRequestController(cc: ControllerComponents) extends ABMController[ProductRequest](cc) {
  service = new ProductRequestService()
}
