package presentation.documents

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

class LogisticRequestController @Inject()(cc: ControllerComponents)(implicit ex: ExecutionContext)
  extends AbstractController(cc) {

}
