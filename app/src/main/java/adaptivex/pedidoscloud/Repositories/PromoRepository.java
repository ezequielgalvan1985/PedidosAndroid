package adaptivex.pedidoscloud.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Entity.PedidoEntity;
import adaptivex.pedidoscloud.Entity.PromoEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.PromoDataBaseHelper;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class PromoRepository
{
    private Context context;
    private PromoDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public PromoRepository(Context context)
    {
        this.context = context;
    }

    public PromoRepository abrir() throws SQLiteException
    {
        dbHelper = new PromoDataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public Integer count(){
        try{
            return abrir().findAll().getCount();
        }catch(Exception e ){
            Log.d("Clientes:", e.getMessage());
            return null;
        }

    }
    public void cerrar()
    {
        if ( db != null )
        {
            db.close();
        }
    }

    public long add(PromoEntity item)
    {
        try {
            ContentValues valores = new ContentValues();
            valores.put(PromoDataBaseHelper.CAMPO_ID, item.getId());
            valores.put(PromoDataBaseHelper.CAMPO_NOMBRE, item.getNombre());
            valores.put(PromoDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());
            valores.put(PromoDataBaseHelper.CAMPO_FECHA_DESDE, item.getFechaDesde());
            valores.put(PromoDataBaseHelper.CAMPO_FECHA_HASTA, item.getFechaHasta());
            valores.put(PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS, item.getCantKilos());
            valores.put(PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO, item.getImporteDescuento());
            valores.put(PromoDataBaseHelper.CAMPO_PRECIO_PROMO, item.getPrecioPromo());
            valores.put(PromoDataBaseHelper.CAMPO_PRECIO_ANTERIOR, item.getPrecioAnterior());

            valores.put(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO, item.getCantPoteCuarto());
            valores.put(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO, item.getCantPoteKilo());
            valores.put(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO, item.getCantPoteTresCuarto());
            valores.put(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO, item.getCantPoteMedio());

            valores.put(PromoDataBaseHelper.CAMPO_ENABLED, true);
            return db.insert(PromoDataBaseHelper.TABLE_NAME, null, valores);
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return -1;
        }

    }



    public void edit(PromoEntity item)
    {
        try {
            String[] argumentos = new String[]
                    {String.valueOf(item.getId())};

            ContentValues valores = new ContentValues();

            valores.put(PromoDataBaseHelper.CAMPO_ID, item.getId());
            valores.put(PromoDataBaseHelper.CAMPO_NOMBRE, item.getNombre());
            valores.put(PromoDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());
            valores.put(PromoDataBaseHelper.CAMPO_FECHA_DESDE, item.getFechaDesde());
            valores.put(PromoDataBaseHelper.CAMPO_FECHA_HASTA, item.getFechaHasta());
            valores.put(PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS, item.getCantKilos());
            valores.put(PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO, item.getImporteDescuento());
            valores.put(PromoDataBaseHelper.CAMPO_PRECIO_PROMO, item.getPrecioPromo());
            valores.put(PromoDataBaseHelper.CAMPO_PRECIO_ANTERIOR, item.getPrecioAnterior());

            valores.put(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO, item.getCantPoteCuarto());
            valores.put(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO, item.getCantPoteKilo());
            valores.put(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO, item.getCantPoteTresCuarto());
            valores.put(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO, item.getCantPoteMedio());

            valores.put(PromoDataBaseHelper.CAMPO_ENABLED, item.isEnabled());

            db.update(PromoDataBaseHelper.TABLE_NAME, valores,
                    PromoDataBaseHelper.CAMPO_ID + " = ?", argumentos);
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    public void delete(PromoEntity promo)
    {
        try{
            String[] argumentos = new String[]
                    {String.valueOf(promo.getIdAndroid())};
            db.delete(PromoDataBaseHelper.TABLE_NAME, PromoDataBaseHelper.CAMPO_ID_ANDROID + " = ?", argumentos);
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAll()
    {
        try{
            db.delete(PromoDataBaseHelper.TABLE_NAME, null, null);
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public Cursor findAll()
    {
        try{
            String[] campos = {
                    PromoDataBaseHelper.CAMPO_ID,
                    PromoDataBaseHelper.CAMPO_NOMBRE,
                    PromoDataBaseHelper.CAMPO_DESCRIPCION,
                    PromoDataBaseHelper.CAMPO_FECHA_DESDE,
                    PromoDataBaseHelper.CAMPO_FECHA_HASTA,
                    PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS,
                    PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO,
                    PromoDataBaseHelper.CAMPO_PRECIO_PROMO,
                    PromoDataBaseHelper.CAMPO_PRECIO_ANTERIOR,
                    PromoDataBaseHelper.CAMPO_ID_ANDROID,
                    PromoDataBaseHelper.CAMPO_ENABLED,
                    PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO,
                    PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO,
                    PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO,
                    PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO

            };
            Cursor resultado = db.query(PromoDataBaseHelper.TABLE_NAME, campos,
                    null, null, null, null, null);
            if (resultado != null)
            {
                resultado.moveToFirst();
            }
            return resultado;

        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public Cursor findByEnabled(){
        try{
            String[] campos = {
                    PromoDataBaseHelper.CAMPO_ID,
                    PromoDataBaseHelper.CAMPO_NOMBRE,
                    PromoDataBaseHelper.CAMPO_DESCRIPCION,
                    PromoDataBaseHelper.CAMPO_FECHA_DESDE,
                    PromoDataBaseHelper.CAMPO_FECHA_HASTA,
                    PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS,
                    PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO,
                    PromoDataBaseHelper.CAMPO_PRECIO_PROMO,
                    PromoDataBaseHelper.CAMPO_PRECIO_ANTERIOR,
                    PromoDataBaseHelper.CAMPO_ID_ANDROID,
                    PromoDataBaseHelper.CAMPO_ENABLED,
                    PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO,
                    PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO,
                    PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO,
                    PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO
            };
            String[] argumentos = {String.valueOf(1)};

            Cursor c = db.query(PromoDataBaseHelper.TABLE_NAME, campos,
                    PromoDataBaseHelper.CAMPO_ENABLED + " = ?", argumentos, null, null, null);
            if (c != null)
            {
                c.moveToFirst();
            }
            return c;
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public ArrayList<PromoEntity> findByEnabledToArrayList(){
        try{
            Cursor c = findByEnabled();
            ArrayList<PromoEntity> lista = new ArrayList<PromoEntity>();
            lista = parseCursorToArrayList(c);
            return lista;
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }

    }


    //Calculate Promo Aplicable
    //Buscar promos activadas y donde Kilos >=

    // Una vez obtenido los descuentos activos y que se aplican a los kilos llevados por el cliente,
    // se debe calcular, cuantos descuentos se aplican al cliente
    //Caso de Prueba 1:
    /*
    * Promo llevando 2 kilos pagas 1
    * Cliente compra 2 kilos, countDiscount = 1
    * Cliente compra 3 kilos, countDiscount = 1
    * Cliente compra 4 kilos, countDisconut = 2
    *
    * montoDescuento = countDiscount * importeDescuento
    *
    * Se puede dar de tener 2 descuento
    * Se toma el descuento con el kilo mas alto
    * */
    public PromoEntity calculateDiscount(Integer paramKilos){
        try{
            PromoEntity p;
            p = findByOneAplicableToPromo(paramKilos);
            if (p!=null){
                if (p.getCantKilos()!= null){
                    //Calcular cantidad de descuentos que se van a aplicar
                    Double countDiscount = Math.floor(paramKilos /p.getCantKilos());
                    Double mountDiscount = p.getImporteDescuento() * countDiscount;
                    p.setCountDiscount(countDiscount.intValue());
                    p.setMountDiscount(mountDiscount);
                }
            }
            return p;
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }



    public Cursor findByAplicable(Integer paramKilos){
        try{
            String[] campos = {
                    PromoDataBaseHelper.CAMPO_ID,
                    PromoDataBaseHelper.CAMPO_NOMBRE,
                    PromoDataBaseHelper.CAMPO_DESCRIPCION,
                    PromoDataBaseHelper.CAMPO_FECHA_DESDE,
                    PromoDataBaseHelper.CAMPO_FECHA_HASTA,
                    PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS,
                    PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO,
                    PromoDataBaseHelper.CAMPO_PRECIO_PROMO,
                    PromoDataBaseHelper.CAMPO_PRECIO_ANTERIOR,
                    PromoDataBaseHelper.CAMPO_ID_ANDROID,
                    PromoDataBaseHelper.CAMPO_ENABLED,
                    PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO,
                    PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO,
                    PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO,
                    PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO
            };
            String[] argumentos = {String.valueOf(1), String.valueOf(paramKilos)};
            String where   = PromoDataBaseHelper.CAMPO_ENABLED + " = ? AND " + PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS + " <= ? ";
            String orderBy =  PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS + " ASC ";
            Cursor c = db.query(PromoDataBaseHelper.TABLE_NAME, campos, where, argumentos, null, null, null);
            if (c != null)
            {
                c.moveToFirst();
            }
            return c;
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public ArrayList<PromoEntity> findByAplicableToArrayList(Integer paramKilos){
        try{
            ArrayList<PromoEntity> lista = new  ArrayList<PromoEntity>();
            Cursor c = findByAplicable(paramKilos);
            lista = parseCursorToArrayList(c);
            return lista;
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public PromoEntity findByOneAplicableToPromo(Integer paramKilos){
        try{
            Cursor c = findByAplicable(paramKilos);
            PromoEntity p;
            p = parseObjectFromRecord(c);
            return p;
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    public PromoEntity findByIdAndroid(Integer idAndroid)
    {
        PromoEntity registro = new PromoEntity();
        String[] campos = {
                PromoDataBaseHelper.CAMPO_ID,
                PromoDataBaseHelper.CAMPO_NOMBRE,
                PromoDataBaseHelper.CAMPO_DESCRIPCION,
                PromoDataBaseHelper.CAMPO_FECHA_DESDE,
                PromoDataBaseHelper.CAMPO_FECHA_HASTA,
                PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS,
                PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO,
                PromoDataBaseHelper.CAMPO_PRECIO_ANTERIOR,
                PromoDataBaseHelper.CAMPO_PRECIO_PROMO,
                PromoDataBaseHelper.CAMPO_ID_ANDROID,
                PromoDataBaseHelper.CAMPO_ENABLED,
                PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO,
                PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO,
                PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO,
                PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO
        };
        String[] argumentos = {String.valueOf(idAndroid)};

        Cursor resultado = db.query(PromoDataBaseHelper.TABLE_NAME, campos,
                PromoDataBaseHelper.CAMPO_ID_ANDROID + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = parseObjectFromRecord(resultado);
        }
        return registro;
    }


    public PromoEntity findById(Integer id)
    {
        PromoEntity registro = new PromoEntity();
        String[] campos = {
                PromoDataBaseHelper.CAMPO_ID,
                PromoDataBaseHelper.CAMPO_NOMBRE,
                PromoDataBaseHelper.CAMPO_DESCRIPCION,
                PromoDataBaseHelper.CAMPO_FECHA_DESDE,
                PromoDataBaseHelper.CAMPO_FECHA_HASTA,
                PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS,
                PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO,
                PromoDataBaseHelper.CAMPO_PRECIO_ANTERIOR,
                PromoDataBaseHelper.CAMPO_PRECIO_PROMO,
                PromoDataBaseHelper.CAMPO_ID_ANDROID,
                PromoDataBaseHelper.CAMPO_ENABLED,
                PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO,
                PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO,
                PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO,
                PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO
        };
        String[] argumentos = {String.valueOf(id)};

        Cursor resultado = db.query(PromoDataBaseHelper.TABLE_NAME, campos,
                PromoDataBaseHelper.CAMPO_ID + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = parseObjectFromRecord(resultado);
        }
        return registro;
    }



    public ArrayList<PromoEntity> parseCursorToArrayList(Cursor c){
        try{

            ArrayList<PromoEntity> lista = new ArrayList<PromoEntity>();
            //Recibe cursor y completa el arralist de pedidodetalles
            PromoEntity registro;
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                registro = new PromoEntity();
                registro.setIdAndroid(c.getInt(c.getColumnIndex(PromoDataBaseHelper.CAMPO_ID_ANDROID)));
                registro.setId(c.getInt(c.getColumnIndex(PromoDataBaseHelper.CAMPO_ID)));
                registro.setNombre(c.getString(c.getColumnIndex(PromoDataBaseHelper.CAMPO_NOMBRE)));
                registro.setDescripcion(c.getString(c.getColumnIndex(PromoDataBaseHelper.CAMPO_DESCRIPCION)));
                registro.setCantKilos(c.getInt(c.getColumnIndex(PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS)));
                registro.setFechaDesde(c.getString(c.getColumnIndex(PromoDataBaseHelper.CAMPO_FECHA_DESDE)));
                registro.setFechaHasta(c.getString(c.getColumnIndex(PromoDataBaseHelper.CAMPO_FECHA_HASTA)));
                registro.setImporteDescuento(c.getDouble(c.getColumnIndex(PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO)));
                registro.setPrecioPromo(c.getDouble(c.getColumnIndex(PromoDataBaseHelper.CAMPO_PRECIO_PROMO)));
                registro.setPrecioAnterior(c.getDouble(c.getColumnIndex(PromoDataBaseHelper.CAMPO_PRECIO_ANTERIOR)));
                registro.setEnabled(c.getInt(c.getColumnIndex(PromoDataBaseHelper.CAMPO_ENABLED))>0);

                registro.setCantPoteCuarto(c.getInt(c.getColumnIndex(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO)));
                registro.setCantPoteMedio(c.getInt(c.getColumnIndex(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO)));
                registro.setCantPoteTresCuarto(c.getInt(c.getColumnIndex(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO)));
                registro.setCantPoteKilo(c.getInt(c.getColumnIndex(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO)));


                //registro = parseObjectFromRecord(c);
                lista.add(registro);
                registro = null;
            }
            return lista;
        }catch(Exception e){
            Toast.makeText(context,"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public PromoEntity parseObjectFromRecord(Cursor c ){
        try{
            PromoEntity object = new PromoEntity();
            if (c!=null){
                if (c.getCount()>0 ){
                    c.moveToFirst();
                    object.setIdAndroid(c.getInt(c.getColumnIndex(PromoDataBaseHelper.CAMPO_ID_ANDROID)));
                    object.setId(c.getInt(c.getColumnIndex(PromoDataBaseHelper.CAMPO_ID)));
                    object.setNombre(c.getString(c.getColumnIndex(PromoDataBaseHelper.CAMPO_NOMBRE)));
                    object.setDescripcion(c.getString(c.getColumnIndex(PromoDataBaseHelper.CAMPO_DESCRIPCION)));
                    object.setFechaDesde(c.getString(c.getColumnIndex(PromoDataBaseHelper.CAMPO_FECHA_DESDE)));
                    object.setFechaHasta(c.getString(c.getColumnIndex(PromoDataBaseHelper.CAMPO_FECHA_HASTA)));
                    object.setCantKilos(c.getInt(c.getColumnIndex(PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS)));
                    object.setImporteDescuento(c.getDouble(c.getColumnIndex(PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO)));
                    object.setPrecioPromo(c.getDouble(c.getColumnIndex(PromoDataBaseHelper.CAMPO_PRECIO_PROMO)));
                    object.setPrecioAnterior(c.getDouble(c.getColumnIndex(PromoDataBaseHelper.CAMPO_PRECIO_ANTERIOR)));
                    object.setEnabled(c.getInt(c.getColumnIndex(PromoDataBaseHelper.CAMPO_ENABLED))>0);
                }
            }
            return object;
        }catch(Exception e){
            Toast.makeText(context,"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public void limpiar()
    {
        db.delete(PromoDataBaseHelper.TABLE_NAME, null, null);
    }
    public void beginTransaction()
    {
        if ( db != null )
            db.beginTransaction();
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
