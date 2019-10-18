package presentation.user

import persistence.services.user.LogisticOperatorService
import persistence.tables.user.LogisticOperator
import play.api.mvc.ControllerComponents
import presentation.ABMController

import scala.concurrent.ExecutionContext.Implicits.global

class LogisticOperatorController(cc: ControllerComponents) extends ABMController[LogisticOperator](cc) {
  service = new LogisticOperatorService()
}
