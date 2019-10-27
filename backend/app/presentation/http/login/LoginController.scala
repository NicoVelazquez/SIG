package presentation.http.login

import javax.inject.Inject
import persistence.services.ServiceFactory
import persistence.services.user.{ClientService, ControllerService, ManagerService}
import persistence.tables.user.{Client, Controller, Manager}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, ControllerComponents, Result}
import presentation.dto.{LoginDTO, LoginResponse}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

class LoginController @Inject()(cc: ControllerComponents)(implicit ex: ExecutionContext) extends AbstractController(cc) {

  val clientService: ClientService = ServiceFactory.clientService
  val managerService: ManagerService = ServiceFactory.managerService
  val controllerService: ControllerService = ServiceFactory.controllerService

  def login(): Action[JsValue] = Action.async(parse.json) { request =>
    Json.fromJson[LoginDTO](request.body).fold(
      _ => Future.successful(BadRequest),
      loginDTO => {
        clientService.getByEmail(loginDTO.email) map {
          case Some(client: Client) =>
            if (client.password == loginDTO.password) Ok(Json.toJson(LoginResponse(client.id, "client")))
            else Unauthorized
          case None =>
            Await.result(getManagerDni(loginDTO), 500 milliseconds)
        }
      }
    ) recover {
      case _: Exception => InternalServerError
    }
  }

  private def getManagerDni(loginDTO: LoginDTO): Future[Result] = {
    managerService.getByEmail(loginDTO.email) map {
      case Some(manager: Manager) =>
        if (manager.password == loginDTO.password) Ok(Json.toJson(LoginResponse(manager.id, "manager")))
        else Unauthorized
      case None => Await.result(getControllerDni(loginDTO), 500 milliseconds)
    }
  }

  private def getControllerDni(loginDTO: LoginDTO): Future[Result] = {
    controllerService.getByEmail(loginDTO.email) map {
      case Some(manager: Controller) =>
        if (manager.password == loginDTO.password) Ok(Json.toJson(LoginResponse(manager.id, "manager")))
        else Unauthorized
      case None => NotFound
    }
  }
}
