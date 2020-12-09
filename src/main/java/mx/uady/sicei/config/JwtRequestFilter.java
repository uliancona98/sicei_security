package mx.uady.sicei.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64;

import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.model.TokenBlacklist;
import mx.uady.sicei.repository.UsuarioRepository;
import mx.uady.sicei.repository.TokenRepository;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	//@Autowired
	//private JwtUserDetailsService jwtUserDetailsService;

    @Autowired 
    private UsuarioRepository usuarioRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private TokenRepository tokenRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
        DecodedToken token = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);

			try {
                token = DecodedToken.getDecoded(jwtToken);
                username = token.sub;
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

			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtTokenUtil.validateToken(token, usuario)) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(request, response);
	}

}