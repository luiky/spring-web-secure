package com.mdai.web.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Direccion {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private long id;
	
	private String dir;
	private String ciudad;
	
	//Esta es la entidad propietaria de la relación. Contiene la Clave ajena (usuario_id)
	@ManyToOne   
	private Usuario usuario;

	protected Direccion() {
		// TODO Auto-generated constructor stub
	}

		
	public Direccion(String dir, String ciudad) {
		super();
		this.dir = dir;
		this.ciudad = ciudad;		
	}



	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Direccion [id=" + id + ", dir=" + dir + ", ciudad=" + ciudad + "]";
	}

}
