package mx.uady.sicei.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mx.uady.sicei.model.request.LoginRequest;
import mx.uady.sicei.service.LoginService;
import mx.uady.sicei.service.UsuarioService;


@RestController
public class LoginRest {

    @Autowired
    private LoginService loginService;
    
    // POST /login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) throws RuntimeException{

        String token = loginService.loginUsuario(request);

        if (token == null) {
            String errMessage = "El usuario o la contrasena son incorrectos";
            return ResponseEntity.ok(errMessage);
        }

        return ResponseEntity.ok(token);
    }
}