package presentation.http.documents

import javax.inject.Inject
import persistence.services.ServiceFactory
import persistence.services.documents.ApplicationService
import persistence.services.relations.{ClientProductService, ProductApplicationService}
import persistence.services.user.ClientService
import persistence.tables.documents.{Application, ApplicationUpdate}
import persistence.tables.relations.ProductApplication
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class ApplicationController @Inject()(cc: ControllerComponents)(implicit ex: ExecutionContext)
  extends AbstractController(cc) {

  private val service: ApplicationService = ServiceFactory.applicationService
  var productApplicationService: ProductApplicationService = ServiceFactory.productApplicationService
  var clientProductService: ClientProductService = ServiceFactory.clientProductService

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
    Json.fromJson[ApplicationUpdate](request.body).fold(
      _ => Future.successful(BadRequest),
      model => {
        val update = Application(model.id, model.clientId, model.date, model.cost, model.state, model.description,
          model.observation, model.operator_acceptance_date, model.collectionDate)
        service.update(update) map {
          case true =>
            // Update accepted & good
            model.products.map(p => {
              if(model.state == "Rechazada") {
                clientProductService.getProductsFromClient(model.clientId, p.id) map { cp =>
                  clientProductService.update(cp.copy(quantity = cp.quantity+p.quantity))
                }
              }
              // Reason is not updated.
              productApplicationService.update(ProductApplication(p.paId, p.quantity, model.id, p.id, "", None, p.accepted, p.good, p.received))
            })
            Ok
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
