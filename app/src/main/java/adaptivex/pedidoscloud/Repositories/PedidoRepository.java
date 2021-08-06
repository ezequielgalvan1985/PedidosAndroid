package adaptivex.pedidoscloud.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import adaptivex.pedidoscloud.Core.Interfaces.ControllerInterface;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.PedidoDataBaseHelper;
import adaptivex.pedidoscloud.Entity.PedidodetalleEntity;
import adaptivex.pedidoscloud.Entity.Pote;
import adaptivex.pedidoscloud.Entity.PoteItem;
import adaptivex.pedidoscloud.Entity.Promo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class PedidoRepository implements ControllerInterface
{
    private Context context;
    private PedidoDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues valores;
    private ArrayList<PedidoEntity> arrayListPedidos;
    private List<PedidoEntity> listPedidos;

    private String[] campos = {
            PedidoDataBaseHelper.CAMPO_ID,
            PedidoDataBaseHelper.CAMPO_CREATED,
            PedidoDataBaseHelper.CAMPO_SUBTOTAL,
            PedidoDataBaseHelper.CAMPO_IVA,
            PedidoDataBaseHelper.CAMPO_MONTO,
            PedidoDataBaseHelper.CAMPO_CLIENTE_ID,
            PedidoDataBaseHelper.CAMPO_BONIFICACION,
            PedidoDataBaseHelper.CAMPO_ESTADO_ID,
            PedidoDataBaseHelper.CAMPO_ID_TMP,
            PedidoDataBaseHelper.CAMPO_CUCURUCHOS,
            PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO,
            //POTES
            PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO,
            PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO,
            PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO,
            PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO,
            PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS,
            PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO,
            PedidoDataBaseHelper.CAMPO_MONTO_ABONA,
            PedidoDataBaseHelper.CAMPO_MONTO
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


    private void setValores(PedidoEntity item){
        try{
            valores.put(PedidoDataBaseHelper.CAMPO_ID, item.getId());
            valores.put(PedidoDataBaseHelper.CAMPO_SUBTOTAL, item.getSubtotal());
            valores.put(PedidoDataBaseHelper.CAMPO_IVA, item.getIva());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO, item.getMonto());
            valores.put(PedidoDataBaseHelper.CAMPO_CLIENTE_ID, item.getCliente_id());
            valores.put(PedidoDataBaseHelper.CAMPO_BONIFICACION, item.getBonificacion());
            valores.put(PedidoDataBaseHelper.CAMPO_ESTADO_ID, item.getEstadoId());
            valores.put(PedidoDataBaseHelper.CAMPO_LOCALIDAD, item.getLocalidad());
            valores.put(PedidoDataBaseHelper.CAMPO_CALLE,     item.getCalle());
            valores.put(PedidoDataBaseHelper.CAMPO_NRO,       item.getNro());
            valores.put(PedidoDataBaseHelper.CAMPO_PISO,      item.getPiso());
            valores.put(PedidoDataBaseHelper.CAMPO_TELEFONO,  item.getTelefono());
            valores.put(PedidoDataBaseHelper.CAMPO_CONTACTO,  item.getContacto());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_KILOS,   item.getCantidadKilos());
            //POTES
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO, item.getCantPoteCuarto());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO, item.getCantPoteMedio());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO, item.getCantPoteTresCuarto());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO, item.getCantPoteKilo());

            valores.put(PedidoDataBaseHelper.CAMPO_CUCHARITAS,       item.getCucharitas());
            valores.put(PedidoDataBaseHelper.CAMPO_CUCURUCHOS,       item.getCucuruchos());
            valores.put(PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO,  item.isEnvioDomicilio());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS, item.getMontoCucuruchos());

            //MONTO
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO,  item.getMontoDescuento());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO_ABONA,  item.getMontoabona());
            valores.put(PedidoDataBaseHelper.CAMPO_MONTO,  item.getMonto());
            valores.put(PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO, item.getCantidadDescuento());
            valores.put(PedidoDataBaseHelper.CAMPO_ID_TMP, item.getAndroid_id());

            valores.put(PedidoDataBaseHelper.CAMPO_HORA_ENTREGA, String.valueOf(item.getHoraentrega()));
            valores.put(PedidoDataBaseHelper.CAMPO_HORA_RECEPCION, String.valueOf(item.getHoraRecepcion()));
            valores.put(PedidoDataBaseHelper.CAMPO_TIEMPO_DEMORA, item.getTiempoDemora());

        }catch(Exception e){
            Log.e("PedidoRepository",e.getMessage());

        }
    }




    public ArrayList<Pote> getPotesArrayList2(long idAndroid ){
        try{
            PedidodetalleRepository pdc = new PedidodetalleRepository(context);
            //Crear arraylist de potes
            ArrayList<Pote> listaPotes = new ArrayList<Pote>();
            PedidoEntity p = new PedidoEntity();

            p = this.abrir().findByAndroidId(idAndroid);
            //obtener todos los pedido detalles
            ArrayList  <PedidodetalleEntity> lista = pdc.findByPedido_android_idToArrayList(idAndroid);
            Integer currentnropote = 0;
            Pote pote            = new Pote();
            Pote poteanterior    = null;
            for (PedidodetalleEntity pd :lista){

                if  (currentnropote != pd.getNroPote()) {

                    //Crear Pote
                    pote = new Pote();
                    pote.setPedido(p);
                    pote.setNroPote(pd.getNroPote());
                    pote.setKilos(pd.getMedidaPote());
                    pote.setHeladomonto(pd.getMonto());
                    // FIn creacion de pote
                }
                // Crea el item del pote
                PoteItem item = new PoteItem();
                item.setCantidad(pd.getProporcionHelado());
                item.setProducto(pd.getProducto());
                pote.addItemPote(item);

                //
                poteanterior = pote;
                if  (currentnropote != pd.getNroPote()) {
                    if (poteanterior != null ) {
                        listaPotes.add(poteanterior);
                    }
                }
                currentnropote = pd.getNroPote();

            }

            return listaPotes;
        }catch(Exception e ){
            Toast.makeText(context, "Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
    }



    public long agregar(PedidoEntity item)
    {
        try{
            setValores(item);
            return db.insert(PedidoDataBaseHelper.TABLE_NAME, null, valores);
        }catch(Exception e ){
            Log.e("PedidoRepository",e.getMessage());
            return -1;
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
            String sSelect = "select max("+PedidoDataBaseHelper.CAMPO_ID_TMP +") ";
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
            Toast.makeText(this.context, "PedidoController" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return nroPedido;
    }


    public void modificar(PedidoEntity item, boolean isIdTmp )
    {
        try{
            setValores(item);
            if (isIdTmp){
                String[] argumentos = new String[] {String.valueOf(item.getAndroid_id())};
                db.update(PedidoDataBaseHelper.TABLE_NAME, valores,
                        PedidoDataBaseHelper.CAMPO_ID_TMP + " = ?", argumentos);
            }else{
                String[] argumentos = new String[]
                        {String.valueOf(item.getId())};
                db.update(PedidoDataBaseHelper.TABLE_NAME, valores,
                        PedidoDataBaseHelper.CAMPO_ID + " = ?", argumentos);
            }
        }catch(Exception e){
            Toast.makeText(context, "Error " + e.getMessage(),Toast.LENGTH_LONG).show();
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
                PedidoDataBaseHelper.CAMPO_ID_TMP + " = ?", argumentos);
    }


    public List<PedidoEntity> findByEstadoId(int pEstadoId)
    {
        try {
            String[] argumentos = {String.valueOf(pEstadoId)};
            String sWhere = PedidoDataBaseHelper.CAMPO_ESTADO_ID + " = ?";
            return parseCursorToListPedido(findAllBy(sWhere,argumentos));
        }catch(Exception e){
            Log.d("PedidoController", e.getMessage());
            return null;
        }
    }

    public List<PedidoEntity> obtenerTodos()
    {
        return parseCursorToListPedido(findAllBy(null,null));
    }


    public boolean calculatePromoBeforeEdit2(PedidoEntity pedido){
        try{
            PromoRepository promoCtr = new PromoRepository(context);
            Promo promo = promoCtr.abrir().matchPromoForPedido(pedido);
            if (promo!= null){
                if (promo.getImporteDescuento()!=null ){
                    pedido.setMontoDescuento(promo.getImporteDescuento());

                }
            }
            abrir().edit(pedido);
            return true;
        }catch (Exception e){
            Toast.makeText(context,"Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }
    }




    //Metodos de Interface
    @Override
    public long add(Object object)
    {
        PedidoEntity item = (PedidoEntity) object;
        try{
            //valores.put(PedidoDataBaseHelper.CAMPO_ID_TMP, item.getAndroid_id()); // es ID Autonumerico
            setValores(item);
            return db.insert(PedidoDataBaseHelper.TABLE_NAME, null, valores);
        }catch(Exception e ){
            Toast.makeText(context,"Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
            return -1;
        }
    }

    @Override
    public boolean edit(Object object) {

        try {
            PedidoEntity item = (PedidoEntity) object;
            setValores(item);
            String[] argumentos = new String[]{String.valueOf(item.getAndroid_id())};
            db.update(PedidoDataBaseHelper.TABLE_NAME, valores, PedidoDataBaseHelper.CAMPO_ID_TMP + " = ?", argumentos);
            return true;
        } catch (Exception e) {
            Toast.makeText(context, "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public boolean delete(Object object) {
        return false;
    }



    /**
     * Devuelve Entity Pedido
     * @param androidId string con el nombre de los campos
     * @author Ezequiel
     * @version 2021.08
     * @since 1.0
     */
    public PedidoEntity findByAndroidId(long androidId)
    {
        String[] argumentos = {String.valueOf(androidId)};
        String sWhere = PedidoDataBaseHelper.CAMPO_ID_TMP + " = ?";
        return parseRecordToPedido(findAllBy(sWhere,argumentos));
    }


    public PedidoEntity findById(int id){
        try{
            String[] argumentos = {String.valueOf(id)};
            String sWhere = PedidoDataBaseHelper.CAMPO_ID + " = ?";
            return parseRecordToPedido(findAllBy(sWhere,argumentos));
        }catch(Exception e){
            Log.e("MarcaRepo",e.getMessage());
            return null;
        }
    }

    /* Acceso a datos  */

    /**
     * Devuelve Cursor,
     * @param sWhere string con el nombre de los campos
     * @param argumentos array de string con los valores de cada parametro
     * @author Ezequiel
     * @version 2021.08
     * @since 1.0
     */
    public Cursor findAllBy(String sWhere, String[] argumentos ){
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
    public List<PedidoEntity> parseCursorToListPedido(Cursor resultado){
        try{
            List<PedidoEntity> pedidoList= null;
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

    public PedidoEntity parseCursorToPedido(Cursor resultado){
        try{
            PedidoEntity pedido= null;
            if (resultado != null)
            {
               resultado.moveToFirst();
               pedido = parseRecordToPedido(resultado);
            }
            return pedido;
        }catch(Exception e) {
            Log.e("PedidoRepo", e.getMessage());
            return null;
        }
    }

    public PedidoEntity parseRecordToPedido(Cursor resultado){
        try{
            PedidoEntity registro = new PedidoEntity();
            PedidodetalleRepository dbDetalles = new PedidodetalleRepository(this.context);

            if (resultado != null)
            {
                registro.setId(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ID)));
                registro.setCreated(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CREATED)));
                registro.setSubtotal(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_SUBTOTAL)));
                registro.setIva(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_IVA)));
                registro.setCliente_id(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CLIENTE_ID)));
                registro.setBonificacion(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_BONIFICACION)));
                registro.setEstadoId(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ESTADO_ID)));
                registro.setAndroid_id(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ID_TMP)));

                //Nuevos campos de PEdido para Heladeria
                registro.setEnvioDomicilio(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_ENVIO_DOMICILIO)) >0);
                registro.setLocalidad(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_LOCALIDAD)));
                registro.setCalle(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CALLE)));
                registro.setNro(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_NRO)));
                registro.setPiso(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_PISO)));
                registro.setContacto(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CONTACTO)));
                registro.setTelefono(resultado.getString(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_TELEFONO)));

                registro.setCucuruchos(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CUCURUCHOS)));
                registro.setCucharitas(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CUCHARITAS)));
                registro.setCantidadKilos(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_KILOS)));

                //POTES
                registro.setCantPoteCuarto(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO)));
                registro.setCantPoteMedio(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO)));
                registro.setCantPoteTresCuarto(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO)));
                registro.setCantPoteKilo(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO)));

                registro.setMontoHelados(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO_HELADOS)));
                registro.setMontoCucuruchos(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO_CUCURUCHOS)));
                registro.setCantidadDescuento(resultado.getInt(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_CANTIDAD_DESCUENTO)));
                registro.setMontoDescuento(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO_DESCUENTO)));
                registro.setMonto(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO)));
                registro.setMontoabona(resultado.getDouble(resultado.getColumnIndex(PedidoDataBaseHelper.CAMPO_MONTO_ABONA)));

                ArrayList<PedidodetalleEntity> lista =  dbDetalles.abrir().findByPedido_android_idToArrayList(registro.getAndroid_id());
                registro.setDetalles(lista);
                dbDetalles.cerrar();

            }
            return registro;
        }catch(Exception e) {
            Log.e("PedidoRepo", e.getMessage());
            return null;
        }
    }
    public ArrayList<PedidoEntity> parseListToArrayList(List<PedidoEntity> listaPedido){
        ArrayList<PedidoEntity> arrayOfPedidos = new ArrayList<PedidoEntity>();
        for (PedidoEntity pedido :listaPedido) {
            arrayOfPedidos.add (pedido) ;
        }
        return arrayOfPedidos;
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