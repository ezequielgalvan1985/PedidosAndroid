package adaptivex.pedidoscloud.Entity;

/**
 * Created by ezequiel on 25/06/2016.
 */
public class PedidodetalleEntity {
    private Integer   id;
    private Integer   androidId;
    private Integer   pedidoId;
    private long      pedidoAndroidId;

    private Integer   productoId;
    private Double    cantidad;
    private Double    preciounitario;
    private Double    monto;
    private Integer   estadoId;
    private Producto  producto;
    private Integer nroPote;
    private Integer proporcionHelado;
    private Integer medidaPote;


    public Integer getAndroidId() {
        return androidId;
    }

    public void setAndroidId(Integer androidId) {
        this.androidId = androidId;
    }

    public long getPedidoAndroidId() {
        return pedidoAndroidId;
    }

    public void setPedidoAndroidId(long pedidoAndroidId) {
        this.pedidoAndroidId = pedidoAndroidId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }



    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPreciounitario() {
        return preciounitario;
    }

    public void setPreciounitario(Double preciounitario) {
        this.preciounitario = preciounitario;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }


    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }


    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {

        this.producto = producto;
        this.productoId = producto.getId();
    }


    public Integer getNroPote() {
        return nroPote;
    }

    public void setNroPote(Integer nroPote) {
        this.nroPote = nroPote;
    }

    public Integer getProporcionHelado() {
        return proporcionHelado;
    }

    public void setProporcionHelado(Integer proporcionHelado) {
        this.proporcionHelado = proporcionHelado;
    }


    public Integer getMedidaPote() {
        return medidaPote;
    }

    public void setMedidaPote(Integer medidaPote) {
        this.medidaPote = medidaPote;
    }
}
