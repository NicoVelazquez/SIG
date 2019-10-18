package presentation.product

import persistence.services.product.LotService
import persistence.tables.product.Lot
import play.api.mvc.ControllerComponents
import presentation.ABMController

import scala.concurrent.ExecutionContext.Implicits.global

class LotController(cc: ControllerComponents) extends ABMController[Lot](cc) {
  service = new LotService()
}
