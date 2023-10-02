package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.dto.DatosAutenticacionUsuario;
import med.voll.api.dto.DatosJWTtoken;
import med.voll.api.model.Usuario;
import med.voll.api.services.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosUsuario) {
		var authToken = new UsernamePasswordAuthenticationToken(datosUsuario.login(), 
					datosUsuario.clave());
		var usuarioAutenticado = manager.authenticate(authToken);
		var JwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
		return ResponseEntity.ok(new DatosJWTtoken(JwtToken));
	}
}
