package presentation.note

import persistence.services.note.NoteService
import persistence.tables.note.Note
import play.api.mvc.ControllerComponents
import presentation.ABMController

import scala.concurrent.ExecutionContext.Implicits.global

class NoteController(cc: ControllerComponents) extends ABMController[Note](cc) {
  service = new NoteService()
}
