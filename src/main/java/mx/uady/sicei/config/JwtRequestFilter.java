package mx.uady.sicei.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import mx.uady.sicei.model.TokenBlacklist;
import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.repository.TokenRepository;
import mx.uady.sicei.repository.UsuarioRepository;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired 
    private UsuarioRepository usuarioRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private TokenRepository tokenRepository;


	private Claims validateToken(HttpServletRequest request, String secret) {
		String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody();
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		DecodedToken token = null;
		//String signature = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);

			try {
				token = DecodedToken.getDecoded(jwtToken);
				//signature = DecodedToken.getSignature(jwtToken);
                username = token.getSub();
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (Exception e) {
                e.printStackTrace();
				System.out.println("JWT Token has expired");
			}

			// checar blacklist
			TokenBlacklist existingToken = tokenRepository.findByToken(requestTokenHeader);
			logger.warn(existingToken);
			if (existingToken != null) {
				logger.warn("JWT token no válido");
				username = null;
			}
		} else {
			System.out.println(requestTokenHeader);

			logger.warn("JWT Token does not begin with Bearer String");
		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			Usuario usuario = this.usuarioRepository.findByUsuario(username);
			boolean validationSecret = false;

			// if token is valid configure Spring Security to manually set
			// authentication
			try {
				String secret = usuario.getSecret();
				Claims claims = validateToken(request, secret);
				if (claims.get("expiracion") != null) {
					validationSecret = true;
				} else {
					SecurityContextHolder.clearContext();
				}
			}
			catch(Exception e){			
				e.printStackTrace();
			}

			if (jwtTokenUtil.validateToken(token, usuario) && validationSecret) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(request, response);
	}
}