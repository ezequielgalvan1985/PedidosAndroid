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

import adaptivex.pedidoscloud.Entity.DatabaseHelper.MarcaDataBaseHelper;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.ProductoDataBaseHelper;
import adaptivex.pedidoscloud.Entity.MarcaEntity;
import adaptivex.pedidoscloud.Entity.ProductoEntity;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
/**
 * Created by ezequiel on 30/05/2016.
 */
public class ProductoRepository {
    private Context context;
    private ProductoDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    private String[] campos = {
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
    private ContentValues valores = new ContentValues();

    public ProductoRepository(Context context) {
        this.context = context;
    }

    public ProductoRepository abrir() throws SQLiteException {
        dbHelper = new ProductoDataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        if (db != null) {
            db.close();
        }
    }

    public void setValores(ProductoEntity item) {
        try {
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
        } catch (Exception e) {
            Log.e("Productorepo", e.getMessage());
        }
    }

    /*CRUD*/
    public long add(ProductoEntity item) {
        try {
            setValores(item);
            return db.insert(ProductoDataBaseHelper.TABLE_NAME, null, valores);
        } catch (Exception e) {
            Log.e("Productos:", e.getMessage());
            return -1;
        }
    }

    public boolean edit(ProductoEntity item) {
        try {
            String[] argumentos = new String[]
                    {String.valueOf(item.getId())};
            setValores(item);
            db.update(ProductoDataBaseHelper.TABLE_NAME, valores,
                    ProductoDataBaseHelper.CAMPO_ID + " = ?", argumentos);
            return true;
        } catch (Exception e) {
            Log.d("Productos:", e.getMessage());
            return false;
        }

    }

    public boolean delete(ProductoEntity item) {
        try {
            String[] argumentos = new String[]{String.valueOf(item.getId())};
            db.delete(ProductoDataBaseHelper.TABLE_NAME, ProductoDataBaseHelper.CAMPO_ID + " = ?", argumentos);
            return true;
        } catch (Exception e) {
            Log.d("Productos:", e.getMessage());
            return false;
        }
    }

    public List<ProductoEntity> findAll() {
        try {
            String orderBy = ProductoDataBaseHelper.CAMPO_NOMBRE + " ASC ";
            String[] argumentos = {""};
            String sWhere = "";
            return parseCursorToListProducto(findBy(sWhere,argumentos));
        } catch (Exception e) {
            Toast.makeText(context, "Error " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    public ArrayList<ProductoEntity> findAllToArrayList(){
        ArrayList<ProductoEntity> lista = new ArrayList<ProductoEntity>();
        List <ProductoEntity> listaproductos = findAll();

        for (ProductoEntity p: listaproductos
             ) {
            lista.add(p);
        }
        return lista;
    }

    public ProductoEntity findByEnabled() {
        try {
            String sWhere = ProductoDataBaseHelper.CAMPO_ENABLED + "=?";
            String[] argumentos = new String[]{String.valueOf(1)};
            return parseRecordToProducto(findBy(sWhere,argumentos));
        } catch (Exception e) {
            Toast.makeText(context, "Error " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public ProductoEntity buscar(int id) {
            return findById(id);
    }
    public ProductoEntity findById(int id){
        try{
            String[] argumentos = {String.valueOf(id)};
            String sWhere = ProductoDataBaseHelper.CAMPO_ID + " = ?";
            return parseRecordToProducto(findBy(sWhere,argumentos));
        }catch(Exception e){
            Log.e("MarcaRepo findbyid",e.getMessage());
            return null;
        }
    }

    public ProductoEntity findByCodigoExterno(String codigoExterno) {
        try {
            //checkServiceWorking();
            ProductoEntity registro = null;
            String[] argumentos = {codigoExterno};
            String where = ProductoDataBaseHelper.CAMPO_CODIGOEXTERNO + "=?";

            return registro;
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    /* FIN funciones de negocio*/



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

    /* ====== Parseadores de Cursores ========= */
    public Cursor findBy(String sWhere, String[] argumentos ){
        try{
            Cursor resultado = db.query(ProductoDataBaseHelper.TABLE_NAME, campos,
                    sWhere, argumentos, null, null, null);
            if (resultado != null)
            {
                resultado.moveToFirst();
            }
            return resultado;
        }catch(Exception e){
            Log.e("ProductoRepo findby",e.getMessage());
            return null;
        }
    }

    public List<ProductoEntity> parseCursorToListProducto(Cursor resultado){
        try{
            List<ProductoEntity> productoEntityList = null;
            if (resultado != null)
            {
                while(resultado.moveToNext()){
                    productoEntityList.add(parseRecordToProducto(resultado));
                }
            }
            return productoEntityList;
        }catch(Exception e) {
            Log.e("ProductoRepo", e.getMessage());
            return null;
        }
    }

    public ProductoEntity parseRecordToProducto(Cursor resultado){
        try{
            ProductoEntity registro = new ProductoEntity();
            if (resultado != null)
            {
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
            Log.e("ProductoRepo ", e.getMessage());
            return null;
        }
    }

}