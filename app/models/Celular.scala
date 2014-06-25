package models

import play.api.libs.json.Json
import play.api.data._
import play.api.data.Forms._
import java.io.Serializable;

case class Celular(var id: Int, var nombre: String, var numero: Int, var modeloCelular: ModeloCelular, var recibeResumenCuenta: Boolean) {
  val MAX_NUMERO = 1000

  def this(nombre: String, numero: Int) {
    this(0, nombre, numero, null, false)
  }

  def validar() {
    if (!this.ingresoNumero()) {
      throw new UserException("Debe ingresar número");
    }
    if (this.numero.intValue() <= this.MAX_NUMERO) {
      throw new UserException("El número debe ser mayor a " + this.MAX_NUMERO);
    }
    if (!this.ingresoNombre()) {
      throw new UserException("Debe ingresar nombre");
    }
    if (this.modeloCelular == null) {
      throw new UserException("Debe ingresar un modelo de celular");
    }
  }

  def ingresoNombre() = this.nombre != null && !this.nombre.trim().equals("")

  def ingresoNumero() = this.numero != null;

  override def equals(other: Any) = other match {
    case that: Celular => this.id == that.id
    case _ => false
  }

  override def hashCode(): Int = {
    val prime = 31;
    prime * (1 + id.hashCode)
  }

}

case class ModeloCelular(var descripcion: String, var costo: BigDecimal) {

  def this(des: String, costo: Double) {
    this(des, BigDecimal(costo))
  }
  def descripcionEntera() = descripcion + " ($ " + costo + ")"
}

object ModeloCelular {

  implicit val ModeloCelularFormat = Json.format[ModeloCelular]

  val ModeloCelularForm = Form(
    mapping(
      "descripcion" -> nonEmptyText,
      "costo" -> bigDecimal)(ModeloCelular.apply _)(ModeloCelular.unapply _))
}

object Celular {
  implicit val CelularFormat = Json.format[Celular]

  val CelularForm = Form(
    mapping(
      "id" -> number,
      "nombre" -> nonEmptyText,
      "numero" -> number,
      "modelo" -> ModeloCelular.ModeloCelularForm.mapping,
      "recibeResumenCuenta" -> boolean)(Celular.apply _)(Celular.unapply _))
}
