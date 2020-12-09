package mx.uady.sicei.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.model.request.LoginRequest;
import mx.uady.sicei.repository.UsuarioRepository;
import mx.uady.sicei.config.JwtTokenUtil;

@Service
public class LoginService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public String loginUsuario(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsuario(request.getUsuario());
        
        if (usuario == null) {
            return null;
        }

        if (!usuario.getPassword().equals(request.getPassword())) {
            return null;
        }
        usuarioRepository.save(usuario);
        String jwt = jwtTokenUtil.generateToken(usuario);

        //String token = UUID.randomUUID().toString();


        return jwt;
    }
}
