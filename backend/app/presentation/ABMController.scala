package presentation

import javax.inject.Inject
import persistence.services.Service
import persistence.tables.DAOModel
import play.api.libs.json.{JsValue, Json, OFormat}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

abstract class ABMController[T <: DAOModel] @Inject()(cc: ControllerComponents)(implicit ex: ExecutionContext)
  extends AbstractController(cc) {

  protected var service: Service[T] = _

  implicit val model: OFormat[T] = Json.format[T]

  def create(): Action[JsValue] = Action.async(parse.json) { request =>
    Json.fromJson[T](request.body).fold(
    _ => Future.successful(BadRequest),
    model => {
      service.create(model) map { id =>
        Created(id.toString)
      }
    }
    ) recover {
      case _: Exception => InternalServerError
    }
  }

  def update(): Action[JsValue] = Action.async(parse.json) { request =>
    Json.fromJson[T](request.body).fold(
      _ => Future.successful(BadRequest),
      model => {
        service.update(model) map {
          case true => Ok(Json.toJson(model))
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
      case requests => Ok(Json.toJson(requests))
      case _ => InternalServerError
    } recover {
      case _: Exception => InternalServerError
    }
  }

  def getAll: Action[AnyContent] = Action.async { _ =>
    service.getAll map {
      case requests => Ok(Json.toJson(requests))
      case _ => InternalServerError
    } recover {
      case _: Exception => InternalServerError
    }
  }

}
