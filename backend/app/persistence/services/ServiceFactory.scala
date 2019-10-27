package persistence.services

import persistence.services.address.AddressService
import persistence.services.documents.{ApplicationService, TrackingService}
import persistence.services.note.NoteService
import persistence.services.product.LotService
import persistence.services.relations.{ClientProductService, ProductApplicationService}
import persistence.services.user.{ClientService, ControllerService, ManagerService}

object ServiceFactory {

  def addressService: AddressService = new AddressService()

  def applicationService = new ApplicationService()

  def trackingService = new TrackingService()

  def noteService = new NoteService()

  def lotService = new LotService()

  def productService = new LotService()

  def clientProductService = new ClientProductService()

  def productApplicationService = new ProductApplicationService()

  def clientService = new ClientService()

  def controllerService = new ControllerService()

  def managerService = new ManagerService()

}
