package adaptivex.pedidoscloud.Servicios;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Repositories.CategoriaRepository;
import adaptivex.pedidoscloud.Repositories.MarcaRepository;
import adaptivex.pedidoscloud.Repositories.PedidoRepository;
import adaptivex.pedidoscloud.Repositories.PedidodetalleRepository;
import adaptivex.pedidoscloud.Repositories.ProductoRepository;
import adaptivex.pedidoscloud.Entity.CategoriaEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.CategoriaDataBaseHelper;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.ClienteDataBaseHelper;
import adaptivex.pedidoscloud.Entity.MarcaEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.MarcaDataBaseHelper;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.PedidoDataBaseHelper;
import adaptivex.pedidoscloud.Entity.ProductoEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.ProductoDataBaseHelper;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperCategorias;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperClientes;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperMarcas;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperProductos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SyncDatosActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syncro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button btnSyncClientes = (Button) findViewById(R.id.btnSyncClientes);
        btnSyncClientes.setOnClickListener(this);

        Button btnSyncProductos = (Button) findViewById(R.id.btnSyncProductos);
        btnSyncProductos.setOnClickListener(this);

        Button btnSyncMarcas = (Button) findViewById(R.id.btnSyncMarcas);
        btnSyncMarcas.setOnClickListener(this);

        Button btnSyncCategorias = (Button) findViewById(R.id.btnSyncCategorias);
        btnSyncCategorias.setOnClickListener(this);

        Button btnSyncHojaruta = (Button) findViewById(R.id.btnSyncHojarutas);
        btnSyncHojaruta.setOnClickListener(this);

        Button btnSyncPedidos = (Button) findViewById(R.id.btnSyncPedidos);
        btnSyncPedidos.setOnClickListener(this);


        Button btnSyncHojarutadetalle = (Button) findViewById(R.id.btnSyncHojarutadetalles);
        btnSyncHojarutadetalle.setOnClickListener(this);


        Button btnLimpiarPedidos = (Button) findViewById(R.id.btnLimpiarPedidos);
        btnLimpiarPedidos.setOnClickListener(this);

        Button btnLimpiarPedidoDetalles = (Button) findViewById(R.id.btnLimpiarPedidoDetalles);
        btnLimpiarPedidoDetalles.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        Syncronizacion sync = new Syncronizacion();

        switch (v.getId()){

            case R.id.btnSyncClientes:
                Toast.makeText(getBaseContext(), "Descargando Clientes", Toast.LENGTH_LONG).show();
                HelperClientes hc = new HelperClientes(getBaseContext());
                hc.execute();
                break;

            case R.id.btnSyncProductos:
                Toast.makeText(getBaseContext(), "Descargando Productos", Toast.LENGTH_LONG).show();
                HelperProductos hp = new HelperProductos(getBaseContext());
                hp.execute();
                break;

            case R.id.btnSyncCategorias:
                Toast.makeText(getBaseContext(), "Descargando Categorias", Toast.LENGTH_LONG).show();
                HelperCategorias hcat = new HelperCategorias(getBaseContext());
                hcat.execute();
                break;

            case R.id.btnSyncMarcas:
                Toast.makeText(getBaseContext(), "Descargando Marcas", Toast.LENGTH_LONG).show();
                HelperMarcas hmar = new HelperMarcas(getBaseContext());
                hmar.execute();
                break;

            case R.id.btnSyncPedidos:
                Toast.makeText(getBaseContext(), "Descargando Pedidos", Toast.LENGTH_LONG).show();
                sync.setEntidad(PedidoDataBaseHelper.TABLE_NAME);
                sync.execute();
                break;



            case R.id.btnLimpiarPedidos:
                PedidoRepository pd = new PedidoRepository(this);
                pd.abrir();
                pd.limpiar();
                pd.cerrar();
                Toast.makeText(getBaseContext(), "Tabla Pedidos Limpia", Toast.LENGTH_LONG).show();

                break;

            case R.id.btnLimpiarPedidoDetalles:
                PedidodetalleRepository pdet = new PedidodetalleRepository(this);
                pdet.abrir();
                pdet.limpiar();
                pdet.cerrar();
                Toast.makeText(getBaseContext(), "Tabla Pedidos Detalles Limpia", Toast.LENGTH_LONG).show();


                break;

            default:
                break;
        }


    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class Syncronizacion extends AsyncTask<Void, Void, Void> {

        // Hashmap for ListView
        public ArrayList<HashMap<String, String>> Listaregistros;
        private HashMap<String,String> registro;

        ProgressDialog pDialog;
        public String jsontexto;
        private String entidad;
        private String vMensaje;


        public void Syncronizacion(String entidad){
            Log.d("Debug: ", "> " + this.getEntidad());
            this.setEntidad(entidad);
            registro = new HashMap<String, String>();
            //registro.put("empresa_id", String.valueOf(GlobalValues.getInstancia().getUserlogued().getEntidad_id()));
            this.setvMensaje("Registros Agregados correctamente");
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(SyncDatosActivity.this);
            pDialog.setMessage("Sincronizando...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            WebRequest webreq = new WebRequest();

            String jsonStr="";
            Log.d("Debug: ", "> " + this.getEntidad());
            //CONSUMIR WEB SERVICES
            switch (this.getEntidad()){

                case PedidoDataBaseHelper.TABLE_NAME:
                    jsonStr = webreq.makeWebServiceCall(Configurador.urlPedidos, WebRequest.GET,this.registro);
                    SincronizarPedidos(jsonStr);
                    break;

                case ClienteDataBaseHelper.TABLE_NAME:
                    jsonStr = webreq.makeWebServiceCall(Configurador.urlClientes, WebRequest.GET,this.registro);
                    //SincronizarClientes(jsonStr);
                    break;

                case ProductoDataBaseHelper.TABLE_NAME:
                    jsonStr = webreq.makeWebServiceCall(Configurador.urlProductos, WebRequest.GET,this.registro);
                    SincronizarProductos(jsonStr);
                    break;

                case CategoriaDataBaseHelper.TABLE_NAME:
                    jsonStr = webreq.makeWebServiceCall(Configurador.urlCategorias, WebRequest.GET,this.registro);
                    SincronizarCategorias(jsonStr);
                    break;

                case MarcaDataBaseHelper.TABLE_NAME:
                    jsonStr = webreq.makeWebServiceCall(Configurador.urlMarcas, WebRequest.GET,this.registro);
                    SincronizarMarcas(jsonStr);
                    break;

            }

            //setListaregistros(ParseJSON(jsonStr));

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            Log.d("Debug onPostExecute: ", "> " + jsontexto);
            switch (this.getEntidad()){
                case "cliente":
                    //SincronizarClientes(jsontexto);


                    break;

                case "producto":
                    SincronizarProductos(jsontexto);

                    break;

                case "categoria":
                    SincronizarCategorias(jsontexto);

                    break;

                case "marca":
                    SincronizarMarcas(jsontexto);
                    break;

                default:
                    Toast.makeText(getBaseContext(),getvMensaje(), Toast.LENGTH_LONG).show();
                    break;
            }



        }

        public ArrayList<HashMap<String, String>> getListaregistros() {
            return Listaregistros;
        }

        public void setListaregistros(ArrayList<HashMap<String, String>> Listaregistros) {
            this.Listaregistros = Listaregistros;
        }

        public String getEntidad() {
            return entidad;
        }

        public void setEntidad(String entidad) {
            this.entidad = entidad;
        }

        public String getvMensaje() {
            return vMensaje;
        }

        public void setvMensaje(String vMensaje) {
            this.vMensaje = vMensaje;
        }




    private boolean SincronizarPedidos(String json){
        PedidoRepository dbHelper = new PedidoRepository(getBaseContext());

        Log.d("Debug: json ", json);
        //dbHelper.beginTransaction();
        if (json != null) {
            try {
                Boolean resultado;
                resultado = false;
                //
                JSONObject jsonObj = new JSONObject(json);

                Log.d("Debug: ", jsonObj.toString());

                JSONArray pedidos = jsonObj.getJSONArray("pedidos");

                PedidoEntity pedido = new PedidoEntity();
                dbHelper.abrir().limpiar();
                //Recorrer Lista
                for (int i = 0; i < pedidos.length(); i++) {

                    JSONObject c = pedidos.getJSONObject(i);
                    JSONObject registro = c.getJSONObject("Pedidocabecera");
                    Log.d("Debug: ", "> " + registro.toString());

                   // pedido.setId(registro.getInt("id"));
                    pedido.setId(registro.getInt(PedidoDataBaseHelper.CAMPO_ID));
                    pedido.setCreated(registro.getString(PedidoDataBaseHelper.CAMPO_CREATED));
                    //pedido.setCliente_id(registro.getInt(PedidoDataBaseHelper.CAMPO_CLIENTE_ID));

                    pedido.setMonto(registro.getDouble(PedidoDataBaseHelper.CAMPO_MONTO));
                    pedido.setIva(registro.getDouble(PedidoDataBaseHelper.CAMPO_IVA));
                    pedido.setBonificacion(registro.getDouble(PedidoDataBaseHelper.CAMPO_BONIFICACION));

                    dbHelper.abrir().agregar(pedido);
                }
                resultado = true;
                setvMensaje("Sincro OK");

                return resultado;

            } catch (JSONException e) {
                Log.d("SyncDatosactivity: ", e.toString());
                setvMensaje(e.getMessage());
                e.printStackTrace();
                return false;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return false;
        }


    }

    private boolean SincronizarProductos(String json){
        ProductoRepository dbHelper = new ProductoRepository(getBaseContext());
        Log.d("Debug: ", "SincronizarProductos ");
        //dbHelper.beginTransaction();
        if (json != null) {
            try {
                Boolean resultado;
                resultado = false;
                JSONObject jsonObj = new JSONObject(json);

                Log.d("Debug: ", jsonObj.toString());

                JSONArray productos = jsonObj.getJSONArray("data");

                ProductoEntity producto = new ProductoEntity();

                dbHelper.abrir().limpiar();



                //Recorrer Lista
                for (int i = 0; i < productos.length(); i++) {

                    JSONObject c = productos.getJSONObject(i);
                    JSONObject registro = c.getJSONObject("Producto");
                    Log.d("Debug: ", "> " + registro.toString());

                    producto.setId(registro.getInt("id"));
                    producto.setNombre(registro.getString(ProductoDataBaseHelper.CAMPO_NOMBRE));
                    producto.setDescripcion(registro.getString(ProductoDataBaseHelper.CAMPO_DESCRIPCION));
                    producto.setPrecio(registro.getInt(ProductoDataBaseHelper.CAMPO_PRECIO));
                    producto.setImagen(registro.getString(ProductoDataBaseHelper.CAMPO_IMAGEN));

                    String imagenurl = Configurador.urlImgClientes + registro.getInt("id") + "/"+ registro.getString(ProductoDataBaseHelper.CAMPO_IMAGEN);
                    producto.setImagenurl(imagenurl);

                    dbHelper.abrir().add(producto);
                }
                resultado = true;
                setvMensaje("Sincro OK");

                return resultado;

            } catch (JSONException e) {
                Log.d("SyncDatosActivity: ", e.toString());
                e.printStackTrace();

                setvMensaje(e.getMessage());

                return false;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return false;
        }

    } // Fin SincronizarProductos


    private boolean SincronizarCategorias(String json){
        CategoriaRepository dbHelper = new CategoriaRepository(getBaseContext());
        //dbHelper.beginTransaction();
        if (json != null) {
            try {
                Boolean resultado;
                resultado = false;

                JSONObject jsonObj = new JSONObject(json);
                Log.d("Debug: ", jsonObj.toString());
                JSONArray categorias = jsonObj.getJSONArray("data");
                CategoriaEntity categoria = new CategoriaEntity();
                dbHelper.abrir().limpiar();
                //Recorrer Lista
                for (int i = 0; i < categorias.length(); i++) {

                    JSONObject c = categorias.getJSONObject(i);
                    JSONObject registro = c.getJSONObject("Categoria");
                    Log.d("Debug: ", "> " + registro.toString());
                    categoria.setId(registro.getInt("id"));
                    categoria.setDescripcion(registro.getString(CategoriaDataBaseHelper.CAMPO_DESCRIPCION));
                    dbHelper.abrir().agregar(categoria);
                }
                resultado = true;
                setvMensaje("SincroOK");

                return resultado;
            } catch (JSONException e) {
                Log.d("SyncDatosActivity: ", e.toString());
                setvMensaje(e.getMessage());

                return false;
            }
        } else {
            Log.e("Sincronizacion:", "No se pudo obtener informacion del web services Categorias");

            return false;
        }

    } // Fin SincronizarCategorias


    private boolean SincronizarMarcas(String json){
        MarcaRepository dbHelper = new MarcaRepository(getBaseContext());
        dbHelper.abrir();

        //dbHelper.beginTransaction();
        if (json != null) {
            try {
                Boolean resultado;
                resultado = false;

                JSONObject jsonObj = new JSONObject(json);
                JSONArray marcas = jsonObj.getJSONArray("data");
                MarcaEntity marcaEntity = new MarcaEntity();
                dbHelper.limpiar();

                Log.e("Debug:", marcaEntity.toString() );
                //Recorrer Lista
                for (int i = 0; i < marcas.length(); i++) {

                    JSONObject c = marcas.getJSONObject(i);
                    JSONObject registro = c.getJSONObject("Marca");
                    Log.d("Debuging1: ", "> " + registro.toString());

                    marcaEntity.setId(registro.getInt("id"));

                    marcaEntity.setDescripcion(registro.getString(CategoriaDataBaseHelper.CAMPO_DESCRIPCION));
                    dbHelper.abrir().agregar(marcaEntity);
                }
                resultado = true;
                setvMensaje("Sincro OK");

                return resultado;
            } catch (JSONException e) {
                setvMensaje(e.getMessage());

                Log.d("SyncDatosActivity: ", e.toString());

                return false;
            }
        } else {

            return false;
        }

    } // Fin SincronizarMarcas



    }
}
