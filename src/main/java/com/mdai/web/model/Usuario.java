package com.mdai.web.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;




@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	private String email;
	
	//If the relationship is bidirectional, the  mappedBy element must be used to specify  
	//the property (usuario) of the entity that is the owner of the relationship (Direccion). 	
	//Debo proporcionar el atributo:usuario de la entidad propietaria: Direccion, en el mappedBy del OneToMany.
	//
	//fetch= FetchType.EAGER cargamos todas las direcciones que tenga el usuario inmediatamente. (Necesario para estos tests y ejemplo)
	//
	//CascadeType.ALL: Cuando una entidad es persistida, removida, refrescada o actualizada, 
	//su entidad relacionada debe ser persistida, removida, refrescada o actualizada también.
	
	//OrphanRemoval=true: Si el ciclo de vida de la entidad hija (Direccion) está vinculado 
	//a su padre (Usuario) de manera que el hijo no puede existir sin su padre. 
	//Entonces podemos anotar la asociación con el atributo orphanRemoval y la disociación del hijo (padre a null) 
	//desencadenará una sentencia de eliminación en la fila de la tabla hija
	
	@OneToMany (mappedBy ="usuario", cascade = CascadeType.ALL, orphanRemoval = true)//, fetch = FetchType.EAGER,)
	private List<Direccion> direcciones = new ArrayList<>();

	

	protected Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Usuario(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	
	public void addDireccion(Direccion direccion) {
		direcciones.add(direccion);
		direccion.setUsuario(this); //el usuario de esta direccion soy "yo"
	}
	
	public void deleteDireccion(Direccion direccion) {
		direcciones.remove(direccion);
		direccion.setUsuario(null); //Ningún Usuario asociado con esa direccion
        
	}
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getId() {
		return id;
	}	

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Direccion> getDirecciones() {
		return direcciones;
	}

	public void setDirecciones(List<Direccion> direcciones) {
		this.direcciones = direcciones;
	}

	@Override
	public String toString() {
				
		return "Usuario [id=" + id + ", name=" + name + ", email=" + email + ", direcciones=" + direcciones + "]";
	}

}
