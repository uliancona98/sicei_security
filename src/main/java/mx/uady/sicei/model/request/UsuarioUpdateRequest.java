package mx.uady.sicei.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsuarioUpdateRequest{

    @NotBlank
    @Size(min = 3, max = 255)
    private String usuario;

    public UsuarioUpdateRequest(){
        
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}