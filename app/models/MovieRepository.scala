package models

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{AbstractController, ControllerComponents}
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

/**
 * Clase repository, donde iran las consultas
 */
class MovieRepository @Inject()(
                                 protected val dbConfigProvider: DatabaseConfigProvider,
                                 cc: ControllerComponents
                               )
                               (implicit ec: ExecutionContext)
  extends AbstractController (cc)
    with HasDatabaseConfigProvider[JdbcProfile] {
  private lazy val movieQuery = TableQuery[MovieTable]

  // Esto va dentro de la clase MovieRepository

  /**
   * Función de ayuda para crear la tabla si ésta
   * aún no existe en la base de datos.
   * @return
   */
  def dbInit: Future[Unit] = {
    // Definición de la sentencia SQL de creación del schema
    val createSchema = movieQuery.schema.createIfNotExists
    // db.run Ejecuta una sentencia SQL, devolviendo un Future
    db.run(createSchema)
  }



}



