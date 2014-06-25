package controllers

import models.Celular
import models.ModeloCelular
import models.Celular._
import models.ModeloCelular._
import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import models.RepositorioCelulares
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import models.RepositorioModelos

object Application extends Controller {

  val repositorioCelulares = RepositorioCelulares.getInstance()
  val repositorioModelos= RepositorioModelos.getInstance()

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def buscarPorNombre(nombre:String) = Action {
    val celulares = repositorioCelulares.searchByName(nombre)
    Ok(Json.toJson(Map("list" -> celulares.toList)))
  }
  
  def modelosDeCelular() = Action {
    val modelos = repositorioModelos.getModelos()
    Ok(Json.toJson(Map("list" -> modelos.toList)))
  }

  def crearCelular = Action(parse.json) { request =>
  	val celular = request.body.as[Celular]
  	repositorioCelulares.create(celular)
  	Ok(Json.toJson(Map("status" -> "OK")))
  }
  
  def guardarCelular = Action(parse.json) { request =>
  	val celular = request.body.as[Celular]
  	repositorioCelulares.update(celular)
  	Ok(Json.toJson(Map("status" -> "OK")))
  }
  
}