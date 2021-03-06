package mx.uady.sicei.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;

public class UsuarioRequest{


    @NotNull
    @Size(min = 3, max = 255)
    @NotEmpty
    private String usuario;

    @NotNull
    @Size(min = 5, max = 50)
    @NotEmpty
    private String password;

    @Size(min = 5, max = 50)
    private String token;

    public UsuarioRequest(){
        //empty
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}