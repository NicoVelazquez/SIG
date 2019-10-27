package presentation.http.user

import javax.inject.Inject
import persistence.services.ServiceFactory
import persistence.services.user.ManagerService
import persistence.tables.user.Manager
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class ManagerController @Inject()(cc: ControllerComponents)(implicit ex: ExecutionContext)
  extends AbstractController(cc) {

  private val service: ManagerService = ServiceFactory.managerService

  def create(): Action[JsValue] = Action.async(parse.json) { request =>
    Json.fromJson[Manager](request.body).fold(
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
    Json.fromJson[Manager](request.body).fold(
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
      case Some(requests: Manager) => Ok(Json.toJson(requests))
      case _ => NotFound
    } recover {
      case _: Exception => InternalServerError
    }
  }

  def getAll: Action[AnyContent] = Action.async { _ =>
    service.getAll map {
      case requests: List[Manager] => Ok(Json.toJson(requests))
      case _ => NotFound
    } recover {
      case _: Exception => InternalServerError
    }
  }

//  def controlApplication(appId: Int) = ???
//
//  def acceptApplication(appId: Int) = ???
//
//  def denyApplication(appId: Int) = ???
//
//  def createNote(appId: Int) = ???
}
