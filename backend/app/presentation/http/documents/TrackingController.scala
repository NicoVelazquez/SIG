package presentation.http.documents

import javax.inject.Inject
import persistence.services.ServiceFactory
import persistence.services.documents.TrackingService
import persistence.tables.documents.Tracking
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class TrackingController @Inject()(cc: ControllerComponents)(implicit ex: ExecutionContext)
  extends AbstractController(cc) {

  private val service: TrackingService = ServiceFactory.trackingService

  def create(): Action[JsValue] = Action.async(parse.json) { request =>
    Json.fromJson[Tracking](request.body).fold(
      _ => Future.successful(BadRequest),
      model => {
        service.create(model) map { id =>
          Created(id.toString)
        }
      }) recover {
      case _: Exception => InternalServerError
    }
  }

  def update(): Action[JsValue] = Action.async(parse.json) { request =>
    Json.fromJson[Tracking](request.body).fold(
      _ => Future.successful(BadRequest),
      model => {
        service.update(model) map {
          case true => Ok
          case false => NotFound
        }
      }
    ) recover {
      case _: Exception => InternalServerError
    }
  }

  def delete(id: Int): Action[AnyContent] = Action.async { _ =>
    service.delete(id) map {
      case true => Ok
      case false => NotFound
    } recover {
      case _: Exception => InternalServerError
    }
  }

  def get(id: Int): Action[AnyContent] = Action.async { _ =>
    service.get(id) map {
      case Some(requests: Tracking) => Ok(Json.toJson(requests))
      case _ => NotFound
    } recover {
      case _: Exception => InternalServerError
    }
  }

  def getAll: Action[AnyContent] = Action.async { _ =>
    service.getAll map {
      case requests: List[Tracking] => Ok(Json.toJson(requests))
      case _ => NotFound
    } recover {
      case _: Exception => InternalServerError
    }
  }
}
