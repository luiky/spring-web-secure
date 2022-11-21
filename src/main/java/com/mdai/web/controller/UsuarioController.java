package com.mdai.web.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.mdai.web.model.Usuario;
import com.mdai.web.service.UsuarioService;

@Controller
public class UsuarioController {
	
	private final UsuarioService usuarioService;
	
	//Le pido a Spring (IoC container) que me injecte la dependencia mediante el constructor parametrizado.
	@Autowired
	public UsuarioController(UsuarioService usuarioService) {
		super();
		this.usuarioService = usuarioService;
		System.out.println("\t Constructor UsuarioController ");
	}

//	AVISO: Este método no se usa
//	No es necesario si tenemos un index.html en la carpeta resources/static
	//WelcomePageHandlerMapping nos asocia automaticamente el index.html como pag de bienvenida	
//	@GetMapping("/")
    public String index() {		
        return "redirect:/index";
    }

	//
	//La url comienza http:localhost:8080. hola.html está en resources/templates
	//Procesa la peticion a http://localhost:8080/hola
	@GetMapping("/hola")
	public String holaUsuarioControllerThymeleaf(Model model) {
		String texto = "Hola mundo en Spring MVC y Thymeleaf";
		//model es un Map, clave-valor. Clave es un string y valor un Object de Java, por tanto, puede ser un Objeto, una Coleccion...
		model.addAttribute("Bienvenida", texto );
		return "hola"; //devuelve la vista a renderizar
	}
	
	/**
    LISTAR USUARIOS     
   */
	//Procesa la peticion GET a http://localhost:8080/listarUsuarios
    @GetMapping("/listarUsuarios")
    //muestra una tabla con los usuarios del sistema
    public String showUsersTable(Model model) {
    	System.out.println("\t UsuarioController::showUsersTable");    	    	    	
        List<Usuario> usuarioList = (List<Usuario>) usuarioService.findAllUsers(); 
        model.addAttribute("usuarios", usuarioList.isEmpty() ? Collections.EMPTY_LIST : usuarioList);        
        return "listarUsuarios"; //devuelve la vista a renderizar
    }
    
    /**
     ADD USUARIO     
    */
    //Invocado desde el boton + de listarUsuarios.html 
    //muestra el formulario para añadir un usuario
    //el parametro usuario, es "añadido" al modelo automaticamente por Spring 
    //Usa la clase del objeto (Usuario) y lo añadirá como el atributo usuario al Model
    //Sigue la convencion de nombres. Mombre de variable: el de la clase con la primera con minuscula. 
    //Salvo que la clase tenga dos mayusculas seguidas al principio que la variable se llamara como la clase
    //
    //Otra opcion:
    //Podemos pasar el Model como param de entrada, y añadir un atributo con el nombre que queramos:
    //model.addAttribute("usuarioAdd", new Usuario(null, null)); y usarlo en el th:object={usuarioAdd} del html
    //Comprobacion:
    //Podemos pasar el model como param de entrada y el usuario y comprobar que de verdad esta en el modelo...
    @GetMapping("/addUsuario")
    public String showAddUsuarioForm(Usuario usuario) {    	
    	System.out.println("\t UsuarioController::showAddUsuarioForm");    	
        return "addUsuario";
    }

    //Invocado desde el boton del formulario añadir usuario de addUsuario.html
    //La vinculacion entre el formulario y el metodo nos proporciona en usuario los datos introducidos.
    //No añadimos direcciones pq es una vista "admin" y porque es un ejemplo simple...
    @PostMapping("/addUsuario")    
    public String addUsuario(Usuario usuario, Model model){
    	System.out.println("\t UsuarioController::addUsuario");    	    	
    	///logica de validacion de usuarios, email correcto, repetido o no?, nombre usuario repetido, formato....
    	// 
    	//  Esquema común: 
    	//		Si hay errores entonces 		
    	//			devolver la misma vista;
    	//Yo no escribo la logica de control de errores. Sorry :(. 
    	//Pista: Spring y Thymeleaf proporcionan formas de validar datos...

    	//le pedimos al servicio que nos cree un usuario    	
        model.addAttribute("usuarios", usuarioService.crearUsuario(usuario)); //no es estrictamente necesario añadir el atributo al model aquí.  	

        return "redirect:/listarUsuarios"; //redirigimos la URL a la vista listarUsuarios
    }
   
    /**
     Update USUARIO
    */
    
    //Invocado desde el boton editar de listarUsuarios.html 
    //nombre mapeado para spring será: /updateUsuario/id, donde id es el Long del id del usuario
    @GetMapping("/updateUsuario/{id}")
    								  //@PathVariable: El parámetro forma parte de la URL 
    public String showUpdateUsuarioForm(@PathVariable("id") Long usuarioId, Model model) {
    	System.out.println("\t UsuarioController::showUpdateUsuarioForm");    	
    	//le pedimos al servicio que nos busque el objeto usuario clickado en el edit.
    	//lo añadimos a model, su clave será "usuarioUpdate". Podremos acceder a él desde la vista actualizarUsuario
    	//el .get final es por el Optional devuelto.
        model.addAttribute("usuarioUpdate",usuarioService.findUsuarioById(usuarioId).get() );
        //nombre de la vista html, diferente escrito en castellano a idea, para que veais que es posible.
        //devolvera el nombre del .html (actualizarUsuario.html), lo mostrará, pero la url en la barra direcciones sera /updateUsuario/id
        return "actualizarUsuario";
    }

    //Invocado desde el boton de actualizarUsuario.html
    @PutMapping("/updateUsuario/{id}")
    public String updateUsuario(@PathVariable("id") Long id, Usuario usuario, Model model) {   	
    	System.out.println("\n\t UsuarioController::updateUsuario");
 		 	
    	//opcion 1 "logica" en controller y muestra de datos para entender la vinculacion con el formulario
//    	    	
    	//OJO: nuestro usuario recibido desde el form y el modelo solo tiene dos campos correctos. Los que hemos actualizado. 
//    	Si incluimos el input oculto para direcciones, tb las habrá copiado y estarán correctas
//    	Usuario u =  (Usuario) model.getAttribute("usuario");
//    	System.out.println("Usuario en el modelo " + u.toString());
//    	System.out.println("Parametro Usuario " +usuario.toString());  
//    	System.out.println( "id " +id ); //mostramos la id recibida
    	
//    	Usuario u = usuarioService.findUsuarioById(id).get();
//    	u.setEmail(usuario.getEmail());
//    	u.setName(usuario.getName());    	
//      model.addAttribute("usuarios", usuarioService.updateUsuario(u));
        
        //opcion 2 "logica" en services. No es estrictamente necesario incluirlo en model.
    	//usuarioService.updateNameAndEmailUsuario(id, usuario.getName(), usuario.getEmail());
    	model.addAttribute("usuarios", usuarioService.updateNameAndEmailUsuario(id, usuario.getName(), usuario.getEmail()) );
    	    	 
    	//OJO: observar, aunque no necesaria aquí, la copia de direcciones sobre el usuario en el input oculto del html (actualizarUsuario.html)    	
        return "redirect:/listarUsuarios";    	
    }
	
    /**
     * DELETE USUARIO
     */
    //Es invocado desde el boton típico de la papelera en listarUsuarios.html
    //se le envía el id del usuario y este se añade url con @PathVariable, aunque no llega a mostrarse la URL por la redireccion
    @DeleteMapping("/deleteUsuario/{id}")
    public String deleteUsuario(@PathVariable("id") Long id, Model model) {
    	System.out.println("\t usuarioController::deleteUsuario");
    	//no es estrictamente necesario añadir los usuarios al modelo en este caso
        model.addAttribute("usuarios", usuarioService.deleteUsuarioById(id));        
        return "redirect:/listarUsuarios";
    }
	

}
