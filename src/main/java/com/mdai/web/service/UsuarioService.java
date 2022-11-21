package com.mdai.web.service;

import java.util.Optional;

import com.mdai.web.model.Usuario;

public interface UsuarioService {
	
	public Iterable <Usuario> crearUsuario(Usuario usuario);

	public Optional<Usuario> findUsuarioById (Long usuarioId);

	public Iterable<Usuario> deleteUsuarioById(Long id);
	
	public Iterable<Usuario> updateUsuario(Usuario usuario);
	
	public Iterable<Usuario> updateNameAndEmailUsuario(Long id, String name, String email);
	
	public Iterable <Usuario> findAllUsers();

}
