package settings.db

import io.getquill.{PostgresAsyncContext, SnakeCase}

import scala.concurrent.ExecutionContext

object SqlController {

  lazy val ctx = new PostgresAsyncContext(SnakeCase, "db.default")

  implicit val exec: ExecutionContext = ExecutionContext.Implicits.global

}
