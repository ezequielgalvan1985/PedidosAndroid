package adaptivex.pedidoscloud.Entity;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Core.WorkNumber;

/**
 * Created by egalvan on 9/3/2018.
 */

public class PoteEntity {
    private PedidoEntity pedido;
    private Integer nroPote;
    private Integer kilos;
    private Double  heladomonto;
    private ArrayList <PoteItemEntity> itemsPote; // es el helado que va a estar en el pote

    public PoteEntity(){
        nroPote     = 0;
        kilos       = 0;
        heladomonto = 0.0;
        itemsPote   = new ArrayList <PoteItemEntity>();
    }

    public void addItemPote(PoteItemEntity it){
        this.itemsPote.add(it);
    }

    public String getMontoString(){
        return "$ " + String.valueOf(this.getHeladomonto());
    }

    public String getMontoHeladoFormatMoney(){
        return WorkNumber.moneyFormat(this.getHeladomonto());
    }
    public String getCantidadKilosFormatString(){
        return WorkNumber.kilosFormat(this.getKilos());
    }


    public Integer getNroPote() {
        return nroPote;
    }

    public void setNroPote(Integer nroPote) {
        this.nroPote = nroPote;
    }

    public Integer getKilos() {
        return kilos;
    }

    public void setKilos(Integer kilos) {
        this.kilos = kilos;
    }

    public Double getHeladomonto() {
        return heladomonto;
    }

    public void setHeladomonto(Double heladomonto) {
        this.heladomonto = heladomonto;
    }

    public ArrayList<PoteItemEntity> getItemsPote() {
        return itemsPote;
    }

    public void setItemsPote(ArrayList<PoteItemEntity> itemsPote) {
        this.itemsPote = itemsPote;
    }

    public PedidoEntity getPedido() {
        return pedido;
    }

    public void setPedido(PedidoEntity pedido) {
        this.pedido = pedido;
    }
}
