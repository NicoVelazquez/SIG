package presentation.documents

import persistence.services.documents.ReturnRequestService
import persistence.tables.documents.ReturnRequest
import play.api.mvc.ControllerComponents
import presentation.ABMController

import scala.concurrent.ExecutionContext.Implicits.global

class ReturnRequestController(cc: ControllerComponents) extends ABMController[ReturnRequest](cc) {
  service = new ReturnRequestService()
}
