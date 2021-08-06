package adaptivex.pedidoscloud.Entity;

/**
 * Created by egalvan on 13/3/2018.
 */

public class ItemHelado {
    private boolean checked;
    private Producto helado;
    private Integer proporcion;
    private PedidodetalleEntity pedidodetalleEntity;

    public ItemHelado(){}

    public ItemHelado(Producto h, boolean c, Integer p){
        setHelado(h);
        setChecked(c);
        setProporcion(p);
    }
    public ItemHelado(Producto h, boolean c, Integer p, PedidodetalleEntity pd){
        setHelado(h);
        setChecked(c);
        setProporcion(p);
        setPedidodetalle(pd);
    }

    public boolean isChecked(){
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Producto getHelado() {
        return helado;
    }

    public void setHelado(Producto helado) {
        this.helado = helado;
    }

    public Integer getProporcion() {
        return proporcion;
    }

    public void setProporcion(Integer proporcion) {
        this.proporcion = proporcion;
    }

    public PedidodetalleEntity getPedidodetalle() {
        return pedidodetalleEntity;
    }

    public void setPedidodetalle(PedidodetalleEntity pedidodetalleEntity) {
        this.pedidodetalleEntity = pedidodetalleEntity;
    }
}
