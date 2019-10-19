package persistence.services.user

import persistence.services.Service
import persistence.tables.user.LogisticOperator

import scala.concurrent.Future

class LogisticOperatorService extends Service[LogisticOperator] {

  import persistence.db.SqlController.ctx._
  import persistence.db.SqlController.{ctx, exec}

  override def create(t: LogisticOperator): Future[Int] =
    ctx.run(quote(querySchema[LogisticOperator]("logistic_operator_table"))
      .insert(
        _.name -> t.name,
        _.addressId -> t.addressId,
        _.email -> t.email,
        _.phone -> t.phone,
      ).onConflictIgnore.returning(_.id))

  override def update(t: LogisticOperator): Future[Boolean] =
    ctx.run(quote(querySchema[LogisticOperator]("logistic_operator_table"))
      .filter(_.id == lift(t.id))
      .update(
        _.name -> t.name,
        _.addressId -> t.addressId,
        _.email -> t.email,
        _.phone -> t.phone
      )
    ).map {
      case 1 => true
      case _ => false
    }

  override def delete(id: Int): Future[Boolean] =
    ctx.run(quote(querySchema[LogisticOperator]("logistic_operator_table")).filter(_.id == lift(id)).delete).map {
      case 1 => true
      case _ => false
    }

  override def get(id: Int): Future[Option[LogisticOperator]] =
    ctx.run(quote(querySchema[LogisticOperator]("logistic_operator_table")).filter(_.id == lift(id))).map(_.headOption)

  override def getAll: Future[List[LogisticOperator]] =
    ctx.run(quote(querySchema[LogisticOperator]("logistic_operator_table")))
}
