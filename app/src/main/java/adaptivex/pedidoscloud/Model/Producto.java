package adaptivex.pedidoscloud.Model;

/**
 * Created by ezequiel on 23/05/2016.
 */
public class Producto {
    private Integer id;
    private String  nombre;
    private String  descripcion;
    private float   precio;
    private String  imagen;
    private String  imagenurl;
    private Integer stock;
    private String  codigoexterno;
    private Integer categoriaId;
    private Integer marcaId;
    private Boolean enabled;

    private Boolean ispromo;
    private Integer unidadmedidaId;
    private Boolean isfraccionado;
    private float   preciopromo;

    private Marca marca;
    private Categoria categoria;
    private Unidadmedida unidadmedida;

    public Unidadmedida getUnidadmedida() {
        return unidadmedida;
    }

    public void setUnidadmedida(Unidadmedida unidadmedida) {
        this.unidadmedida = unidadmedida;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Boolean getIsfraccionado() {
        return isfraccionado;
    }

    public void setIsfraccionado(Boolean isfraccionado) {
        this.isfraccionado = isfraccionado;
    }

    public Integer getUnidadmedidaId() {
        return unidadmedidaId;
    }

    public void setUnidadmedidaId(Integer unidadmedidaId) {
        this.unidadmedidaId = unidadmedidaId;
    }



    public float getPreciopromo() {
        return preciopromo;
    }

    public void setPreciopromo(float preciopromo) {
        this.preciopromo = preciopromo;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public Boolean getEnabled() {
        return enabled;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagenurl() {
        return imagenurl;
    }

    public void setImagenurl(String imagenurl) {
        this.imagenurl = imagenurl;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCodigoexterno() {
        return codigoexterno;
    }

    public void setCodigoexterno(String codigoexterno) {
        this.codigoexterno = codigoexterno;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Integer getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }


    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getIspromo() {
        return ispromo;
    }

    public void setIspromo(Boolean ispromo) {
        this.ispromo = ispromo;
    }



}
