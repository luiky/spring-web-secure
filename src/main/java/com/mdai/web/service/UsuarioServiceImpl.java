package com.mdai.web.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;


import com.mdai.web.model.Direccion;
import com.mdai.web.model.Usuario;
import com.mdai.web.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
@Service
public class UsuarioServiceImpl implements UsuarioService {
 		
	private final UsuarioRepository usuarioRepository;
		
	@Autowired
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		// TODO Auto-generated constructor stub
		System.out.println("\t Constructor UsuarioServiceImpl ");
		this.usuarioRepository = usuarioRepository;		
	}
		

	
	@Override
	public Iterable<Usuario> crearUsuario(Usuario usuario) {		
		usuarioRepository.save(usuario);
		return findAllUsers();
	} 
	
	@Override
    public Optional<Usuario> findUsuarioById(Long usuarioId) {
        return usuarioRepository.findById(usuarioId);
    }
	
	@Override
	public Iterable<Usuario> deleteUsuarioById(Long id) {		
		usuarioRepository.deleteById(id);
		return findAllUsers();
	}
	
	@Override
	public Iterable<Usuario> updateUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		usuarioRepository.save(usuario);
		return findAllUsers();
	}
		
	
	@Override
	public Iterable<Usuario> updateNameAndEmailUsuario(Long id, String name, String email) {
		// TODO Auto-generated method stub
    	Usuario u = findUsuarioById(id).get();
    	u.setName(name);
    	u.setEmail(email);
    	usuarioRepository.save(u);
		return findAllUsers();
	}

	@Override
	public Iterable<Usuario> findAllUsers() {
		return usuarioRepository.findAll();		
	}

	


}
