package adaptivex.pedidoscloud.Core.Components;

import adaptivex.pedidoscloud.Entity.PedidodetalleEntity;

/**
 * Created by egalvan on 10/3/2018.
 */

public class ListItemPote {

    private PedidodetalleEntity pedidodetalleEntity;


    //constructor initializing values
    public ListItemPote(PedidodetalleEntity pd) {
        this.setPedidodetalle(pd);
    }


    public PedidodetalleEntity getPedidodetalle() {
        return pedidodetalleEntity;
    }

    public void setPedidodetalle(PedidodetalleEntity pedidodetalleEntity) {
        this.pedidodetalleEntity = pedidodetalleEntity;
    }
}
