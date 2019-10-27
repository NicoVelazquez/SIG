package presentation.http

import javax.inject.Inject
import persistence.services.Service
import persistence.tables.DAOModel
import play.api.libs.json.{JsValue, Json, OFormat, Reads, Writes}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

abstract class ABMController[T <: DAOModel] @Inject()(cc: ControllerComponents)(implicit ex: ExecutionContext)
  extends AbstractController(cc) {

  var service: Service[T]

  def create()(implicit r: Reads[T]): Action[JsValue] = Action.async(parse.json) { request =>
    Json.fromJson[T](request.body).fold(
    _ => Future.successful(BadRequest),
    model => {
      service.create(model) map { id =>
        Created(id.toString)
      }
    }) recover {
      case _: Exception => InternalServerError
    }
  }

  def update()(implicit r: Reads[T]): Action[JsValue] = Action.async(parse.json) { request =>
    Json.fromJson[T](request.body).fold(
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

  def get(id: Int)(implicit r: Writes[T]): Action[AnyContent] = Action.async { _ =>
    service.get(id) map {
      case Some(requests: T) => Ok(Json.toJson(requests))
      case _ => NotFound
    } recover {
      case _: Exception => InternalServerError
    }
  }

  def getAll(implicit r: Writes[T]): Action[AnyContent] = Action.async { _ =>
    service.getAll map {
      case requests: List[T] => Ok(Json.toJson(requests))
      case _ => NotFound
    } recover {
      case _: Exception => InternalServerError
    }
  }

}
