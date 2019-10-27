package presentation.http.note

import javax.inject.Inject
import persistence.services.ServiceFactory
import persistence.services.note.NoteService
import persistence.tables.note.Note
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class NoteController @Inject()(cc: ControllerComponents)(implicit ex: ExecutionContext)
  extends AbstractController(cc) {

  private val service: NoteService = ServiceFactory.noteService

  def create(): Action[JsValue] = Action.async(parse.json) { request =>
    Json.fromJson[Note](request.body).fold(
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
    Json.fromJson[Note](request.body).fold(
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
      case Some(requests: Note) => Ok(Json.toJson(requests))
      case _ => NotFound
    } recover {
      case _: Exception => InternalServerError
    }
  }

  def getAll: Action[AnyContent] = Action.async { _ =>
    service.getAll map {
      case requests: List[Note] => Ok(Json.toJson(requests))
      case _ => NotFound
    } recover {
      case _: Exception => InternalServerError
    }
  }

  def getNoteForApplication(applicationId: Int): Action[AnyContent] = Action.async { _ =>
    service.getApplicationNote(applicationId) map {
      case Some(note) => Ok(Json.toJson(note))
      case None => NotFound
    } recover {
      case _: Exception => InternalServerError
    }
  }

}
