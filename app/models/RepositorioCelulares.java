package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RepositorioCelulares implements Serializable {
	private static RepositorioCelulares instance;
	private List<Celular> data = new ArrayList<Celular>();

	public static synchronized RepositorioCelulares getInstance() {
		if (instance == null) {
			instance = new RepositorioCelulares();
		}
		return instance;
	}

	private RepositorioCelulares() {
		ModeloCelular s2 = RepositorioModelos.getInstance().get("Samsung Galaxy SII");
		
		this.create(new Celular(0,"Natalia", 1588022202, RepositorioModelos.getInstance().get("NOKIA 1100"), false));
		this.create(new Celular(0,"Bernardo", 1566378124, RepositorioModelos.getInstance().get("Motorola M90"), true));
		this.create(new Celular(0,"Adalberto", 1569575222, s2, true));
		this.create(new Celular(0,"Juan", 1585249765, s2, true));
		this.create(new Celular(0,"Jorge", 1545789865, s2, true));
		this.create(new Celular(0,"Micaela", 1532653285, s2, true));
		this.create(new Celular(0,"Maria", 1552012030, s2, true));
		this.create(new Celular(0,"Pedro", 1561212111, s2, true));
		
	}

	// ********************************************************
	// ** Altas y bajas
	// ********************************************************

	public void create(Celular celular) {
		celular.validar();
		//this.validarClientesDuplicados(celular);
		celular.id_$eq(this.data.size() + 1);
		this.data.add(celular);
	}

	public void delete(Celular celular) {
		this.data.remove(celular);
	}
	
	public void update(Celular celular) {
		this.delete(celular);
		this.data.add(celular);
	}
	
	protected void validarClientesDuplicados(Celular celular) {
		if (!this.search(celular.numero()).isEmpty()) {
			throw new UserException("Ya existe un celular con el número: " + celular.numero());
		}
	}

	// ********************************************************
	// ** Búsquedas
	// ********************************************************

	public List<Celular> search(Integer numero) {
		return this.search(numero, null);
	}
	
	public List<Celular> searchByName(String name) {
		return this.search(null, name);
	}

	/**
	 * Busca los celulares que coincidan con los datos recibidos. Tanto número como nombre pueden ser nulos,
	 * en ese caso no se filtra por ese atributo.
	 * 
	 * Soporta búsquedas por substring, por ejemplo el celular (12345, "Juan Gonzalez") será contemplado por
	 * la búsqueda (23, "Gonza")
	 */
	public List<Celular> search(Integer numero, String nombre) {
		List<Celular> resultados = new ArrayList<Celular>();

		for (Celular celular : this.data) {
			if (match(numero, celular.numero()) && match(nombre, celular.nombre())) {
				resultados.add(celular);
			}
		}

		return resultados;
	}

	protected boolean match(Object expectedValue, Object realValue) {
		return expectedValue == null
			|| realValue.toString().toLowerCase().contains(expectedValue.toString().toLowerCase());
	}
}
