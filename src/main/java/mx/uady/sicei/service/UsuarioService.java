package mx.uady.sicei.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.sicei.exception.NotFoundException;
import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.model.request.UsuarioRequest;
import mx.uady.sicei.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new LinkedList<>();
        usuarioRepository.findAll().iterator().forEachRemaining(usuarios::add);

        return usuarios;
    }


    public Usuario getUsuario(Integer id) {

        Optional<Usuario> opt = usuarioRepository.findById(id);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new NotFoundException();
    }

    public Usuario getUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    public Usuario editarUsuario(Integer id, UsuarioRequest request) {
        return usuarioRepository.findById(id)
        .map(usuario -> {
            usuario.setUsuario(request.getUsuario());
            return usuarioRepository.save(usuario);
        })
        .orElseThrow(() -> new NotFoundException("No existe ese usuario"));
    }

}