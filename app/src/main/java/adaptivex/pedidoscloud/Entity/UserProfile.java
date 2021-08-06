package adaptivex.pedidoscloud.Entity;

import adaptivex.pedidoscloud.Core.WorkString;

/**
 * Created by ezequiel on 23/05/2016.
 */
public class UserProfile {
    private Integer id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String calle;
    private String nro;
    private String piso;
    private String contacto;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCalle() {
        return WorkString.getTexto(calle);
    }
    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNro() {
        if (nro==null) nro = "";
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public String getPiso() {
        if (piso==null) piso = "";
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getContacto() {
        if (contacto==null){
            contacto = "";
        }
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {

        return WorkString.getTexto(telefono);
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


}
