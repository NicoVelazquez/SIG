package presentation.documents

import persistence.services.documents.LogisticRequestService
import persistence.tables.documents.LogisticRequest
import play.api.mvc.ControllerComponents
import presentation.ABMController

import scala.concurrent.ExecutionContext.Implicits.global

class LogisticRequestController(cc: ControllerComponents) extends ABMController[LogisticRequest](cc) {
  service = new LogisticRequestService()
}
