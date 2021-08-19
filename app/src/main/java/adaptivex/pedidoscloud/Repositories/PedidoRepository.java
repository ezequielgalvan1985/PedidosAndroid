package adaptivex.pedidoscloud.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Core.BusinessRules;
import adaptivex.pedidoscloud.Core.IniciarApp;
import adaptivex.pedidoscloud.Core.Interfaces.ControllerInterface;
import adaptivex.pedidoscloud.Core.WorkNumber;
import adaptivex.pedidoscloud.Entity.ParameterEntity;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.PedidoDataBaseHelper;
import adaptivex.pedidoscloud.Entity.PedidodetalleEntity;
import adaptivex.pedidoscloud.Entity.PoteEntity;
import adaptivex.pedidoscloud.Entity.PoteItemEntity;
import adaptivex.pedidoscloud.Entity.ProductoEntity;
import adaptivex.pedidoscloud.Entity.PromoEntity;
import adaptivex.pedidoscloud.Entity.UserProfileEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class PedidoRepository
{
    private Context context;
    private PedidoDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues valores;


    private String[] campos = {
            PedidoDataBaseHelper.CAMPO_ID,
            PedidoDataBaseHelper.CAMPO_ANDROID_ID,
            PedidoDataBaseHelper.CAMPO_CLIENTE_ID,
            PedidoDataBaseHelper.CAMPO_CREATED,
            PedidoDataBaseHelper.CAMPO_SUBTOTAL,
            PedidoDataBaseHelper.CAMPO_IVA,
            PedidoDataBaseHelper.CAMPO_MONTO,
            PedidoDataBaseHelper.CAMPO_BONIFICACION,
            PedidoDataBaseHelper.CAMPO_ESTADO_ID,

            PedidoDataBaseHelper.CAMPO_CUCURUCHOS,
            PedidoDataBaseHelper.CAMPO_CUCHARITAS,
            PedidoDataBaseHelper.CAMPO_CANTIDAD_KILOS,
            PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO,

            //POTES
            PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO,
            PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO,
            PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO,
            PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO,

            PedidoDataBaseHelper.CAMPO_MONTO_HELADOS,
            PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS,
            PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO,
            PedidoDataBaseHelper.CAMPO_MONTO_ABONA,
            PedidoDataBaseHelper.CAMPO_MONTO,

            //direccion
            PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO,
            PedidoDataBaseHelper.CAMPO_LOCALIDAD,
            PedidoDataBaseHelper.CAMPO_CALLE,
            PedidoDataBaseHelper.CAMPO_NRO,
            PedidoDataBaseHelper.CAMPO_PISO,
            PedidoDataBaseHelper.CAMPO_CONTACTO,
            PedidoDataBaseHelper.CAMPO_TELEFONO


};





    public PedidoRepository(Context context)
    {
        this.context = context;
        this.valores = new ContentValues();
    }

    public PedidoRepository abrir() throws SQLiteException
    {
        dbHelper = new PedidoDataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        if ( db != null )
        {
            db.close();
        }
    }

    /*
    * Solo setea la informacion de la cabecera del pedido
    * @Param item recibe PedidoEntity
    * */
    private void setValores(PedidoEntity item){
        try{
            valores.put(PedidoDataBaseHelper.CAMPO_ID, item.getId());
            valores.put(PedidoDataBaseHelper.CAMPO_CLIENTE_ID, item.getCliente().getId());

            valores.put(PedidoDataBaseHelper.CAMPO_SUBTOTAL, item.getSubtotal());
            valores.put(PedidoDataBaseHelper.CAMPO_IVA, item.getIva());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO, item.getMonto());
            valores.put(PedidoDataBaseHelper.CAMPO_CLIENTE_ID, item.getCliente().getId());
            valores.put(PedidoDataBaseHelper.CAMPO_BONIFICACION, item.getBonificacion());
            valores.put(PedidoDataBaseHelper.CAMPO_ESTADO_ID, item.getEstadoId());
            valores.put(PedidoDataBaseHelper.CAMPO_LOCALIDAD, item.getLocalidad());
            valores.put(PedidoDataBaseHelper.CAMPO_CALLE,     item.getCalle());
            valores.put(PedidoDataBaseHelper.CAMPO_NRO,       item.getNro());
            valores.put(PedidoDataBaseHelper.CAMPO_PISO,      item.getPiso());
            valores.put(PedidoDataBaseHelper.CAMPO_TELEFONO,  item.getTelefono());
            valores.put(PedidoDataBaseHelper.CAMPO_CONTACTO,  item.getContacto());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_KILOS,   item.getCantidadKilos());

            valores.put(PedidoDataBaseHelper.CAMPO_CUCHARITAS,       item.getCucharitas());
            valores.put(PedidoDataBaseHelper.CAMPO_CUCURUCHOS,       item.getCucuruchos());
            valores.put(PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO,  item.isEnvioDomicilio());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS, item.getMontoCucuruchos());

            //MONTO
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO,  item.getMontoDescuento());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_ABONA,  item.getMontoabona());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO,  item.getMonto());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO, item.getCantidadDescuento());


            valores.put(PedidoDataBaseHelper.CAMPO_HORA_ENTREGA, String.valueOf(item.getHoraentrega()));
            valores.put(PedidoDataBaseHelper.CAMPO_HORA_RECEPCION, String.valueOf(item.getHoraRecepcion()));
            valores.put(PedidoDataBaseHelper.CAMPO_TIEMPO_DEMORA, item.getTiempoDemora());

        }catch(Exception e){
            Log.e("PedidoRepo valores",e.getMessage());
        }
    }




    public long agregar(PedidoEntity item)
    {
        try{
            setValores(item);
            return db.insert(PedidoDataBaseHelper.TABLE_NAME, null, valores);
        }catch(Exception e ){
            Log.e("PedidoRepos- agregar",e.getMessage());
            return -1;
        }
    }

    public void modificarItems(PedidoEntity pedido, PedidodetalleEntity pd){
        try{

            PedidodetalleEntity detalle =
                FactoryRepositories
                    .getInstancia()
                    .getPedidodetalleRepository()
                    .abrir()
                    .findByPedidoAndProducto(pedido, pd.getProducto());

            //NUEVO registro detalle de pedido
            if (detalle==null && pd.getCantidad()> 0){
                pd.setPedidoAndroidId(pedido.getAndroid_id());
                pd.setEstadoId(Constants.ESTADO_NUEVO);
                FactoryRepositories.getInstancia().getPedidodetalleRepository().abrir().agregar(pd);
            }

            //ACTUALIZA / ELIMINA
            if (detalle!=null ) {
                if (pd.getCantidad() > 0) {
                    //actualizar
                    detalle.setCantidad(pd.getCantidad());
                    FactoryRepositories.getInstancia().getPedidodetalleRepository().abrir().modificar(detalle);
                }else{
                    //eliminar
                    FactoryRepositories.getInstancia().getPedidodetalleRepository().abrir().eliminar(detalle);
                }
            }
            //regrescar el pedido temporal
            FactoryRepositories.PEDIDO_TEMPORAL = this.abrir().findByAndroidId(FactoryRepositories.PEDIDO_TEMPORAL.getAndroid_id());


        }catch(Exception e){
            Log.e("updateItems", e.getMessage());
        }
    }





    //Busca Maximo pedido por IDTMP, Es decir codigo autonumerico de SQLITE
    /**
     * Busca el maximo pedido por androidId
     * Devuelve el androidID tipo long
     * @author Ezequiel
     * @version 2021.08
     * @since 1.0
     */
    public long findByLastAndroidId(){

        long nroPedido = 0;
        try{
            String sSelect = "select max("+PedidoDataBaseHelper.CAMPO_ANDROID_ID +") ";
            String sFrom = " from "+ PedidoDataBaseHelper.TABLE_NAME  ;

            String selectQuery = sSelect + sFrom ;
            dbHelper = new PedidoDataBaseHelper(context);
            db = dbHelper.getReadableDatabase();

            Cursor c = db.rawQuery(selectQuery, null);
            if(c.getCount()>0){
                c.moveToFirst();
                nroPedido =  c.getLong(0);
            }
            c.close();
            db.close();

        }catch (Exception e){
            Toast.makeText(this.context, "PedidoController - findByLastAndroidId" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return nroPedido;
    }


    public void modificar(PedidoEntity pedido)
    {
        try{
            setValores(pedido);
            String[] argumentos = new String[] {String.valueOf(pedido.getAndroid_id())};
            db.update(PedidoDataBaseHelper.TABLE_NAME, valores,
                    PedidoDataBaseHelper.CAMPO_ANDROID_ID + " = ? ", argumentos);
        }catch(Exception e){
            Toast.makeText(context, "Error - modificar" + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    public void eliminar(PedidoEntity pedido)
    {
        String[] argumentos = new String[]
                {String.valueOf(pedido.getId())};
        db.delete(PedidoDataBaseHelper.TABLE_NAME,
                PedidoDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }
    public void deleteByIdTmp(long idTmp)
    {
        String[] argumentos = new String[]
                {String.valueOf(idTmp)};
        db.delete(PedidoDataBaseHelper.TABLE_NAME,
                PedidoDataBaseHelper.CAMPO_ANDROID_ID + " = ?", argumentos);
    }


    public List<PedidoEntity> findByEstadoId(int pEstadoId)
    {
        try {
            String[] argumentos = {String.valueOf(pEstadoId)};
            String sWhere = PedidoDataBaseHelper.CAMPO_ESTADO_ID + " = ?";
            return parseCursorToListPedido(findBy(sWhere,argumentos));
        }catch(Exception e){
            Log.d("PedidoCtr-findbyestid", e.getMessage());
            return null;
        }
    }


    public long crearNuevoPedido(){
        try{
            Date fecha      = new Date();
            Calendar cal    = Calendar.getInstance();
            fecha           = cal.getTime();

            DateFormat df1  = new SimpleDateFormat("yyyy-MM-dd");
            String fechaDMY = df1.format(fecha);

            //Setear valores nuevo pedido
            PedidoEntity pedido = new PedidoEntity();
            pedido.setEstadoId(Constants.ESTADO_NUEVO);
            pedido.setCliente(GlobalValues.getInstancia().getUsuariologueado());
            pedido.setCreated(fechaDMY);
            pedido.setPrecioUnitCucurucho(
                    FactoryRepositories.getInstancia()
                            .getParameterRepository()
                            .abrir()
                            .findByNombre(Constants.PARAM_PRECIO_CUCURUCHO)
                            .getValor_decimal()
            );
            //GUARDAR EN BD
            long id = FactoryRepositories.getInstancia().getPedidoRepository().abrir().agregar(pedido);

            FactoryRepositories.PEDIDO_TEMPORAL =
                    FactoryRepositories
                            .getInstancia()
                            .getPedidoRepository()
                            .abrir()
                            .findByAndroidId(id);

            return id;
        }catch (Exception e ){
            Log.e("PedidoRepo crearpedido",e.getMessage());
            return -1;
        }
    }

    /* Funciones de negocios */





    /**
     * Devuelve Entity Pedido
     * @param androidId string con el nombre de los campos
     * @author Ezequiel
     * @version 2021.08
     * @since 1.0
     */
    public PedidoEntity findByAndroidId(long androidId) {
        try {
            String[] argumentos = {String.valueOf(androidId)};
            String sWhere = PedidoDataBaseHelper.CAMPO_ANDROID_ID + " = ?";
            Cursor c = findBy(sWhere, argumentos);
            if (c != null && c.moveToFirst())
                return parseRecordToPedido(c);
            return null;
        } catch (Exception e) {
            Log.e("PedidoRepo", e.getMessage());
            return null;
        }
    }


    public PedidoEntity findById(long id){
        try{
            String[] argumentos = {String.valueOf(id)};
            String sWhere = PedidoDataBaseHelper.CAMPO_ID + " = ?";
            Cursor c = findBy(sWhere,argumentos);
            if (c != null && c.moveToNext())
                return parseRecordToPedido(c);
            return null;
        }catch(Exception e){
            Log.e("PedidoRepo",e.getMessage());
            return null;
        }
    }



    /**
     * Devuelve Cursor,
     * @param sWhere string con el nombre de los campos
     * @param argumentos array de string con los valores de cada parametro
     * @author Ezequiel
     * @version 2021.08
     * @since 1.0
     */
    public Cursor findBy(String sWhere, String[] argumentos ){
        try{
            Cursor resultado = db.query(PedidoDataBaseHelper.TABLE_NAME, campos,
                    sWhere, argumentos, null, null, null);
            if (resultado != null)
            {
                resultado.moveToFirst();
            }
            return resultado;
        }catch(Exception e){
            Log.e("PedidoRepository",e.getMessage());
            return null;
        }
    }

    /**
     * Devuelve Entity Pedido, el ultimo pedido generado en el dispositivo android
     * @author Ezequiel
     * @version 2021.08
     * @since 1.0
     */
    public PedidoEntity findByPedidoActual(){
        try {
            return findByAndroidId(findByLastAndroidId());
        }catch(Exception e ){
            Log.e("PedidoRepository",e.getMessage());
            return null;
        }
    }




    /* ====== Parseadores de Cursores ========= */
    public ArrayList<PedidoEntity> parseCursorToListPedido(Cursor resultado){
        try{
            ArrayList<PedidoEntity> pedidoList= null;
            if (resultado != null)
            {
                while(resultado.moveToNext()){
                    pedidoList.add(parseRecordToPedido(resultado));
                }
            }
            return pedidoList;
        }catch(Exception e) {
            Log.e("PedidoRepo", e.getMessage());
            return null;
        }
    }


    public PedidoEntity parseRecordToPedido(Cursor resultado){
        try{
            PedidoEntity registro = new PedidoEntity();

            if (resultado != null)
            {
                registro.setId(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ID)));
                registro.setCreated(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CREATED)));
                registro.setSubtotal(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_SUBTOTAL)));
                registro.setIva(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_IVA)));
                registro.setBonificacion(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_BONIFICACION)));
                registro.setEstadoId(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ESTADO_ID)));
                registro.setAndroid_id(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ANDROID_ID)));

                //Nuevos campos de PEdido para Heladeria
                registro.setEnvioDomicilio((resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO) >0));
                registro.setLocalidad(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_LOCALIDAD)));
                registro.setCalle(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CALLE)));
                registro.setNro(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_NRO)));
                registro.setPiso(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_PISO)));
                registro.setContacto(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CONTACTO)));
                registro.setTelefono(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_TELEFONO)));

                registro.setCucuruchos(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CUCURUCHOS)));
                registro.setCucharitas(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CUCHARITAS)));
                registro.setCantidadKilos(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_KILOS)));


                registro.setMontoHelados(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO_HELADOS)));
                registro.setMontoCucuruchos(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS)));
                registro.setCantidadDescuento(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO)));
                registro.setMontoDescuento(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO)));
                registro.setMonto(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO)));
                registro.setMontoabona(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO_ABONA)));

                UserProfileEntity cliente = FactoryRepositories
                        .getInstancia()
                        .getUserProfileRepository()
                        .abrir()
                        .findById(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CLIENTE_ID));

                registro.setCliente(cliente);


                ArrayList<PedidodetalleEntity> items =
                        FactoryRepositories
                        .getInstancia()
                        .getPedidodetalleRepository()
                        .abrir()
                        .findAllByPedido(registro);

                registro.setItems(items);
            }
            return registro;
        }catch(Exception e) {
            Log.e("PedidoRepo", e.getMessage());
            return null;
        }
    }


    public void limpiar()
    {
        db.delete(PedidoDataBaseHelper.TABLE_NAME, null, null);
    }
    public void reset(){

    }
    public void beginTransaction()
    {
        if ( db != null )
        {
            db.beginTransaction();
        }
    }
    public void flush()
    {
        if ( db != null )
        {
            db.setTransactionSuccessful();
        }
    }
    public void commit()
    {
        if ( db != null )
        {
            db.endTransaction();
        }
    }
}