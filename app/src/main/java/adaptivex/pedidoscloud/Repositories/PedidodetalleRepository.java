package adaptivex.pedidoscloud.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adaptivex.pedidoscloud.Entity.DatabaseHelper.PedidoDataBaseHelper;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
import adaptivex.pedidoscloud.Entity.PedidodetalleEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.PedidodetalleDataBaseHelper;
import adaptivex.pedidoscloud.Entity.PoteEntity;
import adaptivex.pedidoscloud.Entity.ProductoEntity;
import adaptivex.pedidoscloud.Entity.UserProfileEntity;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class PedidodetalleRepository
{
    private Context context;
    private PedidodetalleDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues valores;

    String[] campos = {PedidodetalleDataBaseHelper.CAMPO_ID,
            PedidodetalleDataBaseHelper.CAMPO_ANDROID_ID,
            PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID,
            PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ANDROID_ID,
            PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID,
            PedidodetalleDataBaseHelper.CAMPO_CANTIDAD,
            PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO,
            PedidodetalleDataBaseHelper.CAMPO_MONTO,
            PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID

    };
    public PedidodetalleRepository(Context context)
    {
        this.context = context;
        this.valores =  new ContentValues();
    }

    public PedidodetalleRepository abrir() throws SQLiteException
    {
        dbHelper = new PedidodetalleDataBaseHelper(context);
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

    public void setValores(PedidodetalleEntity item){
        valores.put(PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID, item.getPedidoId());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ANDROID_ID, item.getPedidoAndroidId());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID, item.getProductoId());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_CANTIDAD, item.getCantidad());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO, item.getPreciounitario());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_MONTO, item.getMonto());
        valores.put(PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID, item.getEstadoId());
        //valores.put(PedidodetalleDataBaseHelper.CAMPO_NRO_POTE, item.getNroPote());
        //valores.put(PedidodetalleDataBaseHelper.CAMPO_MEDIDA_POTE, item.getMedidaPote());
        //valores.put(PedidodetalleDataBaseHelper.CAMPO_PROPORCION_HELADO, item.getProporcionHelado());

    }

    public long agregar(PedidodetalleEntity item)
    {
        try{
            setValores(item);
            return db.insert(PedidodetalleDataBaseHelper.TABLE_NAME, null, valores);
        }catch(Exception e ){
            Log.e("detalle repo:",e.getMessage());
            return -1;
        }
    }


    public void modificar(PedidodetalleEntity item)
    {
        try{
            String[] argumentos = new String[]
                    {String.valueOf(item.getAndroidId())};
            setValores(item);
            db.update(PedidodetalleDataBaseHelper.TABLE_NAME, valores,
                    PedidodetalleDataBaseHelper.CAMPO_ANDROID_ID + " = ?", argumentos);
        }catch(Exception e ){
            Log.e("detalle repo:",e.getMessage());
        }
    }

    public void eliminar(PedidodetalleEntity registro)
    {
        try{
            String[] argumentos = new String[]
                    {String.valueOf(registro.getAndroidId())};
            db.delete(PedidodetalleDataBaseHelper.TABLE_NAME,
                    PedidodetalleDataBaseHelper.CAMPO_ANDROID_ID + " = ?", argumentos);
        }catch(Exception e ){
            Log.e("detalle repo:",e.getMessage());
        }
    }


    /**
     * Devuelve ArrayList Pedidodetalles
     * @author Ezequiel
     * @version 2021.08
     * @since 1.0
     */
    public ArrayList<PedidodetalleEntity> findAll()
    {
        return parseCursorToListPedidodetalle(findBy(null, null));
    }

    /**
     * Devuelve PedidodetalleEntity
     * @author Ezequiel
     * @version 2021.08
     * @since 1.0
     */
    public PedidodetalleEntity findByPedidoAndProducto(PedidoEntity pedido, ProductoEntity producto){
        try{
            String[] argumentos = {String.valueOf(pedido.getAndroid_id()),String.valueOf(producto.getId())};
            String sWhere = PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ANDROID_ID + " = ? AND ";
            sWhere += PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID + " = ?";
            Cursor c = findBy(sWhere, argumentos);
            if (c!= null && c.moveToFirst())
                return parseRecordToPedidodetalle(c);
            return null;
        }catch(Exception e){
            Log.e("findbyPedidoAndProducto", e.getMessage());
            return null;
        }
    }
    /**
     * Devuelve Entity Pedidodetalle
     * @param androidId string con el nombre de los campos
     * @author Ezequiel
     * @version 2021.08
     * @since 1.0
     */
    public PedidodetalleEntity findByAndroidId(long androidId)
    {
        String[] argumentos = {String.valueOf(androidId)};
        String sWhere = PedidodetalleDataBaseHelper.CAMPO_ANDROID_ID + " = ?";
        Cursor c = findBy(sWhere,argumentos);
        PedidodetalleEntity pd = null;
        if (c!=null){
            while (c.moveToNext()){
                pd = parseRecordToPedidodetalle(c);
            }
        }
        return pd;
    }
    /*
    * Devuelve arraylist pedidodetalles filtrado por PEDIDO
    *
    * */
    public ArrayList<PedidodetalleEntity> findAllByPedido(PedidoEntity pedido){
        try {
            String[] argumentos = {String.valueOf(pedido.getAndroid_id())};
            String sWhere = PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ANDROID_ID + " = ?";
            Cursor c = findBy(sWhere,argumentos);
            if(c!= null)
                return parseCursorToListPedidodetalle(c);
            return null;
        }catch (Exception e){
            Log.e("findAllByPedido",e.getMessage());
            return null;
        }
    }

    /**
     * Devuelve Entity Pedidodetalle
     * @author Ezequiel
     * @version 2021.08
     * @since 1.0
     */
    public PedidodetalleEntity findById(long id){
        try{
            String[] argumentos = {String.valueOf(id)};
            String sWhere = PedidodetalleDataBaseHelper.CAMPO_ID + " = ?";
            return parseRecordToPedidodetalle(findBy(sWhere,argumentos));
        }catch(Exception e){
            Log.e("MarcaRepo",e.getMessage());
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
            Cursor resultado = db.query(PedidodetalleDataBaseHelper.TABLE_NAME, campos,
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


    /* ====== Parseadores de Cursores ========= */
    public ArrayList<PedidodetalleEntity> parseCursorToListPedidodetalle(Cursor resultado){
        try{
            ArrayList<PedidodetalleEntity> pedidoList= null;
            if (resultado != null)
            {
                resultado.moveToFirst();
                pedidoList= new ArrayList<PedidodetalleEntity>();
                do{
                    pedidoList.add(parseRecordToPedidodetalle(resultado));
                }while(resultado.moveToNext());
            }
            return pedidoList;
        }catch(Exception e) {
            Log.e("PedidoRepo", e.getMessage());
            return null;
        }
    }

    public PedidodetalleEntity parseRecordToPedidodetalle(Cursor resultado){
        try{
            //lee de la base de datos y lo guarda en un objeto
            PedidodetalleEntity registro = null;

            if (resultado != null)
            {
                registro = new PedidodetalleEntity();
                registro.setId(resultado.getInt(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_ID)));
                registro.setPedidoId(resultado.getInt(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID)));
                registro.setProductoId(resultado.getInt(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID)));
                registro.setCantidad(resultado.getDouble(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_CANTIDAD)));
                registro.setPreciounitario(resultado.getDouble(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO)));
                registro.setMonto(resultado.getDouble(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_MONTO)));
                registro.setEstadoId(resultado.getInt(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID)));
                registro.setAndroidId(resultado.getInt(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_ANDROID_ID)));
                registro.setPedidoAndroidId(resultado.getInt(resultado.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ANDROID_ID)));
                ProductoEntity p  = FactoryRepositories.getInstancia().getProductoRepository().abrir().findById(registro.getProductoId());
               
                registro.setProducto(p);
            }
            return registro;
        }catch(Exception e) {
            Log.e("PedidoRepo", e.getMessage());
            return null;
        }
    }





    public void limpiar()
    {

        db.delete(PedidodetalleDataBaseHelper.TABLE_NAME, null, null);

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