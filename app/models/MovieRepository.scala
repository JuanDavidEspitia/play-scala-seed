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

  /**
   * Programacion Asincrona: --> Futuros
   */

  def getAll = {
    val q = movieQuery.sortBy(x => x.id) // Devuelve la tabla organizada por el ID
    db.run(q.result) // Permite debolver la tabla, consulta sobre todos los datos de la tabla pelicula

  }

  /**
   * Metodo para encontrar un solo valor que necesitamos conusltar
   * @param id: Que es que le enviamos para que haga la busqueda
   * @return
   */
  def getOne(id: String) = {
    val q = movieQuery.filter(_.id === id) // Compara la columna ID con el valor ID que recibe por parametro
    db.run(q.result.headOption) // Si encuentra el ID lo devuelve dentro de un Option con el valor o un Non si no lo encutra

  }


  
  def create = ???
  def update = ???
  def delete = ???


}



