package presentation.product

import persistence.services.product.ProductService
import play.api.mvc.ControllerComponents
import presentation.ABMController
import persistence.tables.product.Product

import scala.concurrent.ExecutionContext.Implicits.global

class ProductController(cc: ControllerComponents) extends ABMController[Product](cc) {
  service = new ProductService()
}

