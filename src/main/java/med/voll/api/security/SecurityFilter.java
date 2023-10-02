package med.voll.api.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.repositories.UsuarioRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// OBTENER TOKEN DEL HEADER
		var authHeader = request.getHeader("Authorization");
		if(authHeader != null) {  
			var token = authHeader.replace("Bearer ", "");
			//System.out.println(token);
			//System.out.println(tokenService.getSubject(token));
			var nombreUsuario = tokenService.getSubject(token);
			if(nombreUsuario != null) {
				// TOKEN VALIDO
				var usuario = usuarioRepository.findByLogin(nombreUsuario);
				var authentication = new UsernamePasswordAuthenticationToken(usuario, null, 
							usuario.getAuthorities()); // FORZANDO EL INICIO DE SESION
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
	}

}
