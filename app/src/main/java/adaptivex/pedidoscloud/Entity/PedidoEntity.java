package adaptivex.pedidoscloud.Entity;

import android.database.Cursor;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Core.WorkDate;
import adaptivex.pedidoscloud.Core.WorkNumber;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.PedidodetalleDataBaseHelper;

/**
 * Created by ezequiel on 25/06/2016.
 */
public class PedidoEntity {
    //Datos de la api
    private Integer             id;
    private long                android_id;
    private EstadoEntity        estado;
    private UserProfileEntity   cliente;
    private Double              subtotal;
    private Double              monto;
    private Date                fecha;
    private String              localidad;
    private String              calle;
    private String              nro;
    private String              piso;
    private String              telefono;
    private String              contacto;
    private ArrayList<PedidodetalleEntity> items;




    //datos que usa app android internamente
    private String  created;
    private Date    fechaWebRecibido;
    private Date    fechaWebEnviado;
    private Double  iva;
    private Double  bonificacion;
    private Integer estadoId;
    private Integer nroPedidoReal;
    private Double  montoabona;
    //Datos que no se guardan en la DB
    //Para la heladeria
    private ArrayList<PoteEntity> potes;

    //Entidades externas

    private Double  precioxkilo;
    private Double  montoDescuento;
    private Integer cantidadDescuento;


    private Integer cantidadKilos= 0;
    private Integer cucuruchos= 0 ;
    private Integer cucharitas= 0 ;
    private Double  montoCucuruchos;
    private Double  montoHelados;
    private boolean envioDomicilio;
    private Double  precioUnitCucurucho;

    private Date     horaRecepcion;
    private Integer  tiempoDemora;
    private Date     horaentrega;

    private boolean entregado = false;












    /* getters and setters */

    public ArrayList<PedidodetalleEntity> getItems() {
        return items;
    }

    public void setItems(ArrayList<PedidodetalleEntity> items) {
        this.items = items;
    }

    public EstadoEntity getEstado() {
        return estado;
    }

    public void setEstado(EstadoEntity estado) {
        this.estado = estado;
    }

    public Double getPrecioUnitCucurucho() {
        return precioUnitCucurucho;
    }

    public void setPrecioUnitCucurucho(Double precioUnitCucurucho) {
        this.precioUnitCucurucho = precioUnitCucurucho;
    }

    /*GETTERS AND SETTERS */

    public long getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(long android_id) {
        this.android_id = android_id;
    }

    public void setCliente(UserProfileEntity cliente) {
        this.cliente = cliente;
    }


    public double getPrecioMedidaPote(Integer medidaPote){
        double precio = 0.0;
        if (medidaPote== Constants.MEDIDA_KILO){
            precio = Constants.PRECIO_HELADO_KILO;
        }
        if (medidaPote==Constants.MEDIDA_MEDIO){
            precio = Constants.PRECIO_HELADO_MEDIO;
        }
        if (medidaPote==Constants.MEDIDA_CUARTO){
            precio = Constants.PRECIO_HELADO_CUARTO;
        }
        if (medidaPote==Constants.MEDIDA_TRESCUARTOS){
            precio = Constants.PRECIO_HELADO_TRESCUARTOS;
        }
        return precio;
    }


    public PedidoEntity(){
        this.items = new ArrayList<PedidodetalleEntity>();
        this.cantidadKilos = 0;
        this.cucharitas = 0 ;
        this.cucuruchos = 0;
        this.montoCucuruchos = 0.0;
        this.montoHelados = 0.0;
        this.monto = 0.0;


    }

    public void setMontoCucuruchos(Double monto){
        this.montoCucuruchos= monto;
    }
    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getStringDireccion(){
        String direccion = "Localidad: " + getLocalidad() + "\n"+
                           "Calle:         " + getCalle() + " " + getNro() + " (Piso: " + getPiso() + ")\n"+
                           "Telefono:  " + getTelefono() +"\n"+
                           "Contacto: " + getContacto() ;
        return direccion;
    }


    public Integer getCucuruchos() {
        return WorkNumber.getValue(cucuruchos);
    }

    public void setCucuruchos(Integer cucuruchos) {
        this.cucuruchos = cucuruchos;
        //Cada vez que se setea los cucuruchos se calcula su monto
        Double monto = cucuruchos * Constants.PRECIO_CUCURUCHO;
        setMontoCucuruchos(monto);
        refreshMontoTotal();
    }

    public void refreshMontoTotal(){
        subtotal =  WorkNumber.getValue(montoHelados) + WorkNumber.getValue(montoCucuruchos);
        monto    =  WorkNumber.getValue(montoHelados) + WorkNumber.getValue(montoCucuruchos) - WorkNumber.getValue(montoDescuento);
    }

    public Integer getCucharitas() {
        return WorkNumber.getValue(cucharitas);
    }

    public void setCucharitas(Integer cucharitas) {
        this.cucharitas = cucharitas;
    }





    //GETTERS AND SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Double getSubtotal() {
        return WorkNumber.getValue(subtotal);
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getIva() {
        return WorkNumber.getValue(iva);
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getMonto() {
        return WorkNumber.getValue(monto);
    }

    public String getMontoFormatMoney(){
        return WorkNumber.moneyFormat(getMonto());
    }

    public String getMontoHeladoFormatMoney(){
        return WorkNumber.moneyFormat(getMontoHelados());
    }

    public String getMontoAbonaFormatMoney(){
        return WorkNumber.moneyFormat(getMontoabona());
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }



    public Double getBonificacion() {
        return WorkNumber.getValue(bonificacion);
    }

    public void setBonificacion(Double bonificacion) {
        this.bonificacion = bonificacion;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public String getEstadoDescripcion(){
        String estadodesc ="";
        return estadodesc;
    }
    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }


    public Integer getNroPedidoReal() {
        return nroPedidoReal;
    }

    public void setNroPedidoReal(Integer nroPedidoReal) {
        this.nroPedidoReal = nroPedidoReal;
    }

    public UserProfileEntity getCliente() {
        return cliente;
    }

    public void setUserProfile(UserProfileEntity cliente) {
        this.cliente = cliente;
    }





    public void setDetalles(Cursor c) {
        //Recibe cursor y completa el arralist de pedidodetalles

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            PedidodetalleEntity registro = new PedidodetalleEntity();
            registro.setPedidoId(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID)));
            registro.setProductoId(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID)));
            registro.setCantidad(c.getDouble(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_CANTIDAD)));
            registro.setPreciounitario(c.getDouble(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO)));
            registro.setMonto(c.getDouble(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_MONTO)));
            registro.setEstadoId(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID)));
            this.getItems().add(registro);
        }
    }




    public String getCreatedDMY(){
        String fecha=getCreated();
        try{
            DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date date = df1.parse(getCreated());
            fecha = df2.format(date);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return fecha;
    }


    public Double getPrecioxkilo() {
        return WorkNumber.getValue(precioxkilo);
    }

    public void setPrecioxkilo(Double precioxkilo) {
        this.precioxkilo = precioxkilo;
    }

    public String getCantidadKilosFormatString(){
        return WorkNumber.kilosFormat(this.getCantidadKilos());
    }

    public Integer getCantidadKilos() {
        return WorkNumber.getValue(cantidadKilos);
    }

    public void setCantidadKilos(Integer cantidadKilos) {
        this.cantidadKilos = cantidadKilos;
    }

    public boolean isEnvioDomicilio() {
        return envioDomicilio;
    }

    public void setEnvioDomicilio(boolean envioDomicilio) {
        this.envioDomicilio = envioDomicilio;
    }




    public double getMontoCucuruchos() {
        return WorkNumber.getValue(montoCucuruchos);
    }
    public String getMontoCucuruchosFormatMoney() {
        return WorkNumber.moneyFormat(this.montoCucuruchos);
    }



    public double getMontoHelados() {
        return WorkNumber.getValue(montoHelados);
    }

    public void setMontoHelados(double montoHelados) {
        this.montoHelados = montoHelados;
    }

    public String getEnvioDomicilio(){
        String cartel = "NO";
        if (this.envioDomicilio) cartel = "SI";
        return cartel;
    }
    public boolean getEnvioDomicilioBoolean(){
        return this.envioDomicilio;
    }

    public Double getMontoDescuento() {
        return WorkNumber.getValue(montoDescuento);
    }
    public String getMontoDescuentoFormatMoney(){
        return WorkNumber.moneyFormat(this.montoDescuento);
    }
    public void setMontoDescuento(Double montoDescuento) {
        this.montoDescuento = montoDescuento;
        refreshMontoTotal();
    }

    public Integer getCantidadDescuento() {
        return WorkNumber.getValue(cantidadDescuento);
    }

    public void setCantidadDescuento(Integer cantidadDescuento) {
        this.cantidadDescuento = cantidadDescuento;
    }





    public Date getFechaWebRecibido() {
        return fechaWebRecibido;
    }

    public void setFechaWebRecibido(Date fechaWebRecibido) {
        this.fechaWebRecibido = fechaWebRecibido;
    }

    public Date getFechaWebEnviado() {
        return fechaWebEnviado;
    }

    public void setFechaWebEnviado(Date fechaWebEnviado) {
        this.fechaWebEnviado = fechaWebEnviado;
    }

    public Date getHoraentrega() {
        return horaentrega;
    }

    public void getHoraentrega(Date horaentrega) {
        this.setHoraentrega(horaentrega);
    }

    public void setHoraentrega(Date horaentrega) {
        this.horaentrega = horaentrega;
    }

    //Recibe Hora en String y lo pasa a
    public void setHoraEntregaStringToDate(String paramHoraentrega){
        this.horaentrega = WorkDate.parseStringToDate(paramHoraentrega);

    }

    public String getHoraEntrega(){
        return  WorkDate.parseDateToStringFormatHHmmss(this.horaentrega);

    }
    public String getHoraEntregaForSMS(){
        Date fecha = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 45);
        fecha = cal.getTime();
        DateFormat df1 = new SimpleDateFormat("HH:mm:00");
        String fechaHMS = df1.format(fecha);

        return "ROMA HELADOS te comunica que tu pedido llegara aproximadamente a las  "+ fechaHMS +" hs. Dentro de los siguientes 45 minutos.";
    }

    public Date getHoraRecepcion() {
        return horaRecepcion;
    }

    public void setHoraRecepcion(Date horaRecepcion) {
        this.horaRecepcion = horaRecepcion;
    }

    public Integer getTiempoDemora() {
        return tiempoDemora;
    }

    public void setTiempoDemora(Integer tiempoDemora) {
        this.tiempoDemora = tiempoDemora;
    }

    public Double getMontoabona() {
        return montoabona;
    }

    public void setMontoabona(Double montoabona) {
        this.montoabona = montoabona;
    }

}
