package adaptivex.pedidoscloud.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Core.Interfaces.ControllerInterface;
import adaptivex.pedidoscloud.Core.ParameterHelper;
import adaptivex.pedidoscloud.Core.Sql.SqlManager;
import adaptivex.pedidoscloud.Entity.Producto;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.ProductoDataBaseHelper;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class ProductoRepository
{
    private Context context;
    private ProductoDataBaseHelper dbHelper;
    private SQLiteDatabase db;


    public ProductoRepository(Context context)
    {
        this.context = context;
    }

    public ProductoRepository abrir() throws SQLiteException
    {
        dbHelper = new ProductoDataBaseHelper(context);
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

    public Integer count(){
        try{
            return abrir().findAll().getCount();
        }catch(Exception e ){
            Log.d("Productos:", e.getMessage());
            return null;
        }

    }

    public long add(Object object) {
        try{
            Producto item = (Producto) object;
            ContentValues valores = new ContentValues();
            valores.put(ProductoDataBaseHelper.CAMPO_ID, item.getId());
            valores.put(ProductoDataBaseHelper.CAMPO_NOMBRE, item.getNombre());
            valores.put(ProductoDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());
            valores.put(ProductoDataBaseHelper.CAMPO_IMAGEN, item.getImagen());
            valores.put(ProductoDataBaseHelper.CAMPO_IMAGENURL, item.getImagenurl());
            valores.put(ProductoDataBaseHelper.CAMPO_PRECIO, item.getPrecio());
            valores.put(ProductoDataBaseHelper.CAMPO_STOCK, item.getStock());
            valores.put(ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO, item.getCodigoexterno());

            valores.put(ProductoDataBaseHelper.CAMPO_CATEGORIA_ID, item.getCategoria().getId());
            valores.put(ProductoDataBaseHelper.CAMPO_MARCA_ID, item.getMarca().getId());
            valores.put(ProductoDataBaseHelper.CAMPO_ENABLED, item.getEnabled());

            valores.put(ProductoDataBaseHelper.CAMPO_ISPROMO, item.getIspromo());
            valores.put(ProductoDataBaseHelper.CAMPO_UNIDADMEDIDA_ID, item.getUnidadmedida().getId());
            valores.put(ProductoDataBaseHelper.CAMPO_ISFRACCIONADO, item.getIsfraccionado());
            valores.put(ProductoDataBaseHelper.CAMPO_PRECIOPROMO, item.getPreciopromo());


            return db.insert(ProductoDataBaseHelper.TABLE_NAME, null, valores);
        }catch(Exception e ){
            Log.d("Productos:", e.getMessage());
            return -1;
        }
    }

    public boolean edit(Object object) {
        try{
            Producto item = (Producto) object;
            String[] argumentos = new String[]
                    {String.valueOf(item.getId())};
            ContentValues valores = new ContentValues();

            valores.put(ProductoDataBaseHelper.CAMPO_NOMBRE, item.getNombre());
            valores.put(ProductoDataBaseHelper.CAMPO_PRECIO, item.getPrecio());
            valores.put(ProductoDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());
            valores.put(ProductoDataBaseHelper.CAMPO_IMAGEN, item.getImagen());
            valores.put(ProductoDataBaseHelper.CAMPO_IMAGENURL, item.getImagenurl());
            valores.put(ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO, item.getCodigoexterno());
            valores.put(ProductoDataBaseHelper.CAMPO_STOCK, item.getStock());
            valores.put(ProductoDataBaseHelper.CAMPO_CATEGORIA_ID, item.getCategoria().getId());
            valores.put(ProductoDataBaseHelper.CAMPO_MARCA_ID, item.getMarca().getId());
            valores.put(ProductoDataBaseHelper.CAMPO_ENABLED, item.getEnabled());

            valores.put(ProductoDataBaseHelper.CAMPO_ISPROMO, item.getIspromo());
            valores.put(ProductoDataBaseHelper.CAMPO_UNIDADMEDIDA_ID, item.getUnidadmedida().getId());
            valores.put(ProductoDataBaseHelper.CAMPO_ISFRACCIONADO, item.getIsfraccionado());
            valores.put(ProductoDataBaseHelper.CAMPO_PRECIOPROMO, item.getPreciopromo());

            db.update(ProductoDataBaseHelper.TABLE_NAME, valores,
                    ProductoDataBaseHelper.CAMPO_ID + " = ?", argumentos);
            return true;
        }catch(Exception e ){
            Log.d("Productos:", e.getMessage());
            return false;
        }

    }

    public boolean delete(Object object) {
        try{

            Producto p = (Producto) object;
            String[] argumentos = new String[]{String.valueOf(p.getId())};
            db.delete(ProductoDataBaseHelper.TABLE_NAME, ProductoDataBaseHelper.CAMPO_ID + " = ?", argumentos);
            return true;
        }catch(Exception e ){
            Toast.makeText(context, "Error " +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Cursor findAll(){
        try{
            String[] campos = {
                    ProductoDataBaseHelper.CAMPO_ID,
                    ProductoDataBaseHelper.CAMPO_NOMBRE,
                    ProductoDataBaseHelper.CAMPO_DESCRIPCION,
                    ProductoDataBaseHelper.CAMPO_IMAGEN,
                    ProductoDataBaseHelper.CAMPO_IMAGENURL,
                    ProductoDataBaseHelper.CAMPO_PRECIO,
                    ProductoDataBaseHelper.CAMPO_STOCK,
                    ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO,
                    ProductoDataBaseHelper.CAMPO_CATEGORIA_ID,
                    ProductoDataBaseHelper.CAMPO_MARCA_ID,
                    ProductoDataBaseHelper.CAMPO_ENABLED,

                    ProductoDataBaseHelper.CAMPO_ISPROMO,
                    ProductoDataBaseHelper.CAMPO_UNIDADMEDIDA_ID,
                    ProductoDataBaseHelper.CAMPO_ISFRACCIONADO,
                    ProductoDataBaseHelper.CAMPO_PRECIOPROMO

            };
            String orderBy=  ProductoDataBaseHelper.CAMPO_NOMBRE + " ASC ";
            Cursor resultado = db.query(ProductoDataBaseHelper.TABLE_NAME, campos, null, null, null, null, orderBy);
            if (resultado != null)
            {
                resultado.moveToFirst();
            }
            return resultado;
        }catch(Exception e ){
            Toast.makeText(context, "Error " +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    public Cursor findByEnabled(){
        try{
            String[] campos = {
                    ProductoDataBaseHelper.CAMPO_ID,
                    ProductoDataBaseHelper.CAMPO_NOMBRE,
                    ProductoDataBaseHelper.CAMPO_DESCRIPCION,
                    ProductoDataBaseHelper.CAMPO_IMAGEN,
                    ProductoDataBaseHelper.CAMPO_IMAGENURL,
                    ProductoDataBaseHelper.CAMPO_PRECIO,
                    ProductoDataBaseHelper.CAMPO_STOCK,
                    ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO,
                    ProductoDataBaseHelper.CAMPO_CATEGORIA_ID,
                    ProductoDataBaseHelper.CAMPO_MARCA_ID,
                    ProductoDataBaseHelper.CAMPO_ENABLED,
                    ProductoDataBaseHelper.CAMPO_ISPROMO,
                    ProductoDataBaseHelper.CAMPO_UNIDADMEDIDA_ID,
                    ProductoDataBaseHelper.CAMPO_ISFRACCIONADO,
                    ProductoDataBaseHelper.CAMPO_PRECIOPROMO
            };
            String orderBy=  ProductoDataBaseHelper.CAMPO_NOMBRE + " ASC ";
            String selection = ProductoDataBaseHelper.CAMPO_ENABLED +"=?";
            String[] argumentos   = new String[]{String.valueOf(1)};


            Cursor resultado = db.query(ProductoDataBaseHelper.TABLE_NAME, campos, selection, argumentos, null, null, orderBy);
            if (resultado != null)
            {
                resultado.moveToFirst();
            }
            return resultado;
        }catch(Exception e ){
            Toast.makeText(context, "Error " +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    public Cursor findWhere(SqlManager sm){
        try{

            String[] campos = {
                    ProductoDataBaseHelper.CAMPO_ID,
                    ProductoDataBaseHelper.CAMPO_NOMBRE,
                    ProductoDataBaseHelper.CAMPO_DESCRIPCION,
                    ProductoDataBaseHelper.CAMPO_IMAGEN,
                    ProductoDataBaseHelper.CAMPO_IMAGENURL,
                    ProductoDataBaseHelper.CAMPO_PRECIO,
                    ProductoDataBaseHelper.CAMPO_STOCK,
                    ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO,
                    ProductoDataBaseHelper.CAMPO_CATEGORIA_ID,
                    ProductoDataBaseHelper.CAMPO_MARCA_ID,
                    ProductoDataBaseHelper.CAMPO_ENABLED,
                    ProductoDataBaseHelper.CAMPO_ISPROMO,
                    ProductoDataBaseHelper.CAMPO_UNIDADMEDIDA_ID,
                    ProductoDataBaseHelper.CAMPO_ISFRACCIONADO,
                    ProductoDataBaseHelper.CAMPO_PRECIOPROMO
            };
            String orderBy = ProductoDataBaseHelper.CAMPO_NOMBRE + " ASC ";
            String[] argumentos = sm.getArguments();
            String conditions = sm.getConditions();

            Cursor resultado = db.query(ProductoDataBaseHelper.TABLE_NAME, campos, conditions, argumentos, null, null, orderBy);
            if (resultado != null)
            {
                resultado.moveToFirst();
            }
            return resultado;
        }catch(Exception e ){
            Toast.makeText(context, "Error " +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }




    public Cursor findByIdAndroid(long idAndroid) {
        return null;
    }

    public ArrayList<Object> findAllToArrayList() {
        try{
            ArrayList<Object> lista = new  ArrayList<Object>();
            //Cursor c = findAll();
            Cursor c = findByEnabled();

            lista = parseCursorToArrayList(c);
            return lista;

        }catch(Exception e ){
            Toast.makeText(context, "Error " +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public ArrayList<Object> findWhereToArrayList(SqlManager sm){
        try{
            ArrayList<Object> lista = new  ArrayList<Object>();
            Cursor c = findWhere(sm);
            lista = parseCursorToArrayList(c);
            return lista;
        }catch(Exception e ){
            Toast.makeText(context, "Error " +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }





    public ArrayList<Object> parseCursorToArrayList(Cursor c) {
        try{
            ArrayList<Object> al = new ArrayList<Object>();
            do {
                Producto registro = new Producto();
                registro.setId(c.getInt(c.getColumnIndex(ProductoDataBaseHelper.CAMPO_ID)));
                registro.setCodigoexterno(c.getString(c.getColumnIndex(ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO)));
                registro.setDescripcion(c.getString(c.getColumnIndex(ProductoDataBaseHelper.CAMPO_DESCRIPCION)));
                registro.setNombre(c.getString(c.getColumnIndex(ProductoDataBaseHelper.CAMPO_NOMBRE)));
                registro.setPrecio(c.getFloat(c.getColumnIndex(ProductoDataBaseHelper.CAMPO_PRECIO)));
                registro.setStock(c.getInt(c.getColumnIndex(ProductoDataBaseHelper.CAMPO_STOCK)));
                registro.setCategoriaId(c.getInt(c.getColumnIndex(ProductoDataBaseHelper.CAMPO_CATEGORIA_ID)));
                registro.setMarcaId(c.getInt(c.getColumnIndex(ProductoDataBaseHelper.CAMPO_MARCA_ID)));
                registro.setEnabled(c.getInt(c.getColumnIndex(ProductoDataBaseHelper.CAMPO_ENABLED))==1);
                registro.setIspromo(c.getInt(c.getColumnIndex(ProductoDataBaseHelper.CAMPO_ISPROMO))==1);
                registro.setUnidadmedidaId(c.getInt(c.getColumnIndex(ProductoDataBaseHelper.CAMPO_UNIDADMEDIDA_ID)));
                registro.setIsfraccionado(c.getInt(c.getColumnIndex(ProductoDataBaseHelper.CAMPO_ISFRACCIONADO))==1);
                registro.setPreciopromo(c.getFloat(c.getColumnIndex(ProductoDataBaseHelper.CAMPO_PRECIOPROMO)));

                al.add(registro);
                registro = null;
            }while (c.moveToNext());
            return al;
        }catch(Exception e ){
            Log.d("ProductosController", e.getMessage());
            return null;
        }
    }













    public Producto buscar(int id)
    {

        try {
            // validar si producto esta siendo modificado
            //checkServiceWorking();
            Producto registro = null;
            String[] campos = {ProductoDataBaseHelper.CAMPO_ID,
                    ProductoDataBaseHelper.CAMPO_NOMBRE,
                    ProductoDataBaseHelper.CAMPO_DESCRIPCION,
                    ProductoDataBaseHelper.CAMPO_IMAGEN,
                    ProductoDataBaseHelper.CAMPO_IMAGENURL,
                    ProductoDataBaseHelper.CAMPO_PRECIO,
                    ProductoDataBaseHelper.CAMPO_STOCK,
                    ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO,
                    ProductoDataBaseHelper.CAMPO_CATEGORIA_ID,
                    ProductoDataBaseHelper.CAMPO_MARCA_ID,
                    ProductoDataBaseHelper.CAMPO_ENABLED,
                    ProductoDataBaseHelper.CAMPO_ISPROMO,
                    ProductoDataBaseHelper.CAMPO_ISFRACCIONADO,
                    ProductoDataBaseHelper.CAMPO_PRECIOPROMO,
                    ProductoDataBaseHelper.CAMPO_UNIDADMEDIDA_ID,
            };
            String[] argumentos = {String.valueOf(id)};
            Cursor resultado = db.query(ProductoDataBaseHelper.TABLE_NAME, campos,
                    ProductoDataBaseHelper.CAMPO_ID + " = ?", argumentos, null, null, null);
            if (resultado != null) {
                resultado.moveToFirst();
                registro = new Producto();
                Log.d("Debug: PProductoID ", String.valueOf(id));
                Log.d("Debug: ProductoVal ", String.valueOf(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_ID)));
                registro.setId(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_ID)));
                registro.setNombre(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_NOMBRE)));
                registro.setDescripcion(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_DESCRIPCION)));
                registro.setImagen(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_IMAGEN)));
                registro.setImagenurl(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_IMAGENURL)));
                registro.setPrecio(resultado.getFloat(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_PRECIO)));
                registro.setStock(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_STOCK)));
                registro.setCodigoexterno(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO)));
                registro.setCategoriaId(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_CATEGORIA_ID)));
                registro.setMarcaId(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_MARCA_ID)));
                registro.setEnabled(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_ENABLED))==1);

                registro.setIspromo(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_ISPROMO))==1);
                registro.setUnidadmedidaId(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_UNIDADMEDIDA_ID)));
                registro.setIsfraccionado(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_ISFRACCIONADO))==1);
                registro.setPreciopromo(resultado.getFloat(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_PRECIOPROMO)));

            }
            return registro;
        }catch(Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public Producto findByCodigoExterno(String codigoExterno)
    {
        try{
            //checkServiceWorking();
            Producto registro = null;
            String[] campos = {ProductoDataBaseHelper.CAMPO_ID,
                    ProductoDataBaseHelper.CAMPO_NOMBRE,
                    ProductoDataBaseHelper.CAMPO_DESCRIPCION,
                    ProductoDataBaseHelper.CAMPO_IMAGEN,
                    ProductoDataBaseHelper.CAMPO_IMAGENURL,
                    ProductoDataBaseHelper.CAMPO_PRECIO,
                    ProductoDataBaseHelper.CAMPO_STOCK,
                    ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO,
                    ProductoDataBaseHelper.CAMPO_CATEGORIA_ID,
                    ProductoDataBaseHelper.CAMPO_MARCA_ID,
                    ProductoDataBaseHelper.CAMPO_ENABLED,
                    ProductoDataBaseHelper.CAMPO_ISPROMO,
                    ProductoDataBaseHelper.CAMPO_ISFRACCIONADO,
                    ProductoDataBaseHelper.CAMPO_PRECIOPROMO,
                    ProductoDataBaseHelper.CAMPO_UNIDADMEDIDA_ID
            };
            String[] argumentos = {codigoExterno};
            String where =ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO + "=?";

            Cursor resultado = db.query(ProductoDataBaseHelper.TABLE_NAME, campos, where, argumentos, null, null, null);
            if (resultado != null)
            {
                if (resultado.getCount()> 0 ){
                    resultado.moveToFirst();
                    registro = new Producto();
                    registro.setId(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_ID)));
                    registro.setNombre(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_NOMBRE)));
                    registro.setDescripcion(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_DESCRIPCION)));
                    registro.setImagen(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_IMAGEN)));
                    registro.setImagenurl(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_IMAGENURL)));
                    registro.setPrecio(resultado.getFloat(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_PRECIO)));
                    registro.setStock(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_STOCK)));
                    registro.setCodigoexterno(resultado.getString(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO)));
                    registro.setCategoriaId(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_CATEGORIA_ID)));
                    registro.setMarcaId(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_MARCA_ID)));
                    registro.setEnabled(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_ENABLED))==1);

                    registro.setIspromo(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_ISPROMO))==1);
                    registro.setPreciopromo(resultado.getFloat(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_PRECIOPROMO)));
                    registro.setIsfraccionado(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_ISFRACCIONADO))==1);
                    registro.setUnidadmedidaId(resultado.getInt(resultado.getColumnIndex(ProductoDataBaseHelper.CAMPO_UNIDADMEDIDA_ID)));

                }
            }
            return registro;
        }catch(Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public void limpiar()
    {
        db.delete(ProductoDataBaseHelper.TABLE_NAME, null, null);
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