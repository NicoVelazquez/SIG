package presentation.http.documents

import javax.inject.Inject
import persistence.services.ServiceFactory
import persistence.services.documents.ApplicationService
import persistence.services.user.ClientService
import persistence.tables.documents.Application
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class ApplicationController @Inject()(cc: ControllerComponents)(implicit ex: ExecutionContext)
  extends AbstractController(cc) {

  private val service: ApplicationService = ServiceFactory.applicationService

  def create(): Action[JsValue] = Action.async(parse.json) { request =>
    Json.fromJson[Application](request.body).fold(
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
    Json.fromJson[Application](request.body).fold(
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

  def getAll: Action[AnyContent] = Action.async { _ =>
    service.getAllApplications map {
      case requests => Ok(Json.toJson(requests))
      case _ => NotFound
    } recover {
      case e: Exception => InternalServerError
    }
  }

  def get(id: Int): Action[AnyContent] = Action.async { _ =>
    service.getAllApplications map {
      case requests => Ok(Json.toJson(requests.filter(_.id == id).head))
      case _ => NotFound
    } recover {
      case e: Exception => InternalServerError
    }
  }
}
