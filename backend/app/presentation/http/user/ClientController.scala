package presentation.http.user

import javax.inject.Inject
import persistence.services.ServiceFactory
import persistence.services.documents.ApplicationService
import persistence.services.note.NoteService
import persistence.services.relations.{ClientProductService, ProductApplicationService}
import persistence.services.user.ClientService
import persistence.tables.address.Address
import persistence.tables.documents.{Application, ApplicationUpdate}
import persistence.tables.relations.ProductApplication
import persistence.tables.user.Client
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import presentation.dto.{ApplicationDTO, ProductDTO}

import scala.concurrent.{ExecutionContext, Future}

class ClientController @Inject()(cc: ControllerComponents)(implicit ex: ExecutionContext)
  extends AbstractController(cc) {

  var service: ClientService = ServiceFactory.clientService
  var applicationService: ApplicationService = ServiceFactory.applicationService
  var clientProductService: ClientProductService = ServiceFactory.clientProductService
  var productApplicationService: ProductApplicationService = ServiceFactory.productApplicationService
  val noteService: NoteService = ServiceFactory.noteService

  def create(): Action[JsValue] = Action.async(parse.json) { request =>
    Json.fromJson[Client](request.body).fold(
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
    Json.fromJson[Client](request.body).fold(
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
      case Some(requests: Client) => Ok(Json.toJson(requests))
      case _ => NotFound
    } recover {
      case _: Exception => InternalServerError
    }
  }

  def getAll: Action[AnyContent] = Action.async { _ =>
    service.getAll map {
      case requests: List[Client] => Ok(Json.toJson(requests))
      case _ => NotFound
    } recover {
      case _: Exception => InternalServerError
    }
  }

  def createClientApplication(clientId: Int): Action[JsValue] = Action.async(parse.json) { request =>
    Json.fromJson[ApplicationDTO](request.body).fold(
      _ => Future.successful(BadRequest),
      applicationDTO => {
        service.getClientWithAddress(clientId) flatMap {
          case Some((_: Client, address: Address)) =>
            val application = Application(0, clientId, applicationDTO.date, address.cost,
              "Nueva", applicationDTO.description, None, None, None)
            applicationService.create(application) map { applicationId =>
              // TODO: Delete products from client/product relation. Fix quantity amount
              applicationDTO.products.foreach(productDTO => {
                clientProductService.getProductsFromClient(clientId, productDTO.productId).map { cp =>
                  clientProductService.restoreProductsFromClient(clientId, productDTO.productId, cp.quantity - productDTO.quantity)
                }
                val productApplication = ProductApplication(0, productDTO.quantity, applicationId, productDTO.productId,
                  None, None, None)
                // TODO: Add products to product/application relation
                productApplicationService.create(productApplication)
              })
              Created(applicationId.toString)
            }
          case None => Future.successful(NotFound(s"Client not found for id: $clientId"))
        }
      }
    ) recover {
      case _: Exception => InternalServerError
    }
  }

  def getClientApplications(clientId: Int): Action[AnyContent] = Action.async { _ =>
    applicationService.getAllApplications map {
      case list => Ok(Json.toJson(list.filter(_.clientId == clientId)))
      case _ => NotFound
    } recover {
      case e: Exception => InternalServerError
    }
  }

  def getClientProducts(clientId: Int): Action[AnyContent] = Action.async { _ =>
    service.getClientProducts(clientId) map {
      case list: List[ProductDTO] => Ok(Json.toJson(list))
      case _ => NotFound
    } recover {
      case _: Exception => InternalServerError
    }
  }

  def deleteClientApplication(clientId: Int, applicationId: Int): Action[JsValue] = Action.async(parse.json) { request =>
    Json.fromJson[ApplicationUpdate](request.body).fold(
      _ => Future.successful(BadRequest),
      model => {
        productApplicationService.deleteWithApplicationId(applicationId) map {
          case true =>
            // Update accepted & good
            applicationService.delete(applicationId)
            model.products.map(p => {
              clientProductService.getProductsFromClient(model.clientId, p.id) map { cp =>
                clientProductService.update(cp.copy(quantity = cp.quantity+p.quantity))
              }
            })
            Ok
          case false => NotFound
        }
      }
    ) recover {
      case _: Exception => InternalServerError
    }
  }
}
