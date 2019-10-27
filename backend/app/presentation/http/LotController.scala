package presentation.http

import javax.inject.Inject
import persistence.services.ServiceFactory
import persistence.services.product.LotService
import persistence.tables.product.Lot
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.ExecutionContext

class LotController  @Inject()(cc: ControllerComponents)(implicit ex: ExecutionContext)
  extends AbstractController(cc) {

  var service: LotService = ServiceFactory.lotService

  def getAll: Action[AnyContent] = Action.async { _ =>
    service.getAll map {
      case requests: List[Lot] => Ok(Json.toJson(requests))
      case _ => NotFound
    } recover {
      case _: Exception => InternalServerError
    }
  }

}
