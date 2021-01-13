package mx.uady.sicei.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.model.request.LoginRequest;
import mx.uady.sicei.repository.UsuarioRepository;

@Service
public class LoginService {

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

        String token = UUID.randomUUID().toString();
        usuario.setSecret(token);
        usuarioRepository.save(usuario);

        return token;
    }
}
