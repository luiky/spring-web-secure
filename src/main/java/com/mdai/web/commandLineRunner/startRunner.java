package com.mdai.web.commandLineRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mdai.web.model.Direccion;
import com.mdai.web.model.Usuario;
import com.mdai.web.repository.UsuarioRepository;

@Component
public class startRunner implements CommandLineRunner{
	
	@Autowired
	private UsuarioRepository usuarioRepository;	
		
//	public startRunner(UsuarioRepository usuarioRepository) {		
//		this.usuarioRepository = usuarioRepository;		
//		// TODO Auto-generated constructor stub
//	}
	
	public startRunner() {						
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run (String... args) throws Exception {
		System.out.println("\t startRunner execution");
		iniciarUsuarios();
		
	}
	
	private void iniciarUsuarios() {
		System.out.println("\t startRunner: creando usuarios para la BD");
		//Crear tres de usuarios y le añadimos direcciones. Inicializacion de la BD "poco elegante".
		Usuario usuario01 = new Usuario("Luiky","luiky@unex.es");
		Usuario usuario02 = new Usuario("Lidia","lidia@gmail.com");
		Usuario usuario03 = new Usuario("Pedro","pedro@gmail.com");
		
		usuario01.addDireccion(new Direccion("Plaza", "Caceres"));
		usuario01.addDireccion(new Direccion("Calle", "Coria"));
		
		usuario02.addDireccion(new Direccion("Carrer", "Sabadell"));
		
		usuario03.addDireccion(new Direccion("Calleja", "Coria"));
		
		usuario01 = usuarioRepository.save(usuario01);
		usuario02 = usuarioRepository.save(usuario02);
		usuario03 = usuarioRepository.save(usuario03);
		
	}

}
