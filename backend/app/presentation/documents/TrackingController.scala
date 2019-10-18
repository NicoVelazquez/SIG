package presentation.documents

import persistence.services.documents.TrackingService
import persistence.tables.documents.Tracking
import play.api.mvc.ControllerComponents
import presentation.ABMController

import scala.concurrent.ExecutionContext.Implicits.global

class TrackingController(cc: ControllerComponents) extends ABMController[Tracking](cc) {
  service = new TrackingService()
}
