package adaptivex.pedidoscloud.Servicios;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Repositories.PedidoRepository;
import adaptivex.pedidoscloud.Repositories.PedidodetalleRepository;
import adaptivex.pedidoscloud.Core.parserJSONtoModel.PedidoParser;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
import adaptivex.pedidoscloud.Entity.PedidodetalleEntity;
import adaptivex.pedidoscloud.Entity.ProductoEntity;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperPedidos;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class IntentServiceEnvioPedidos extends IntentService {

    private Context ctx;
    private HashMap<String,String> registro;
    private ProductoEntity producto;
    private int respuesta; //1=ok, 200=error
    private int opcion; //1 enviar Post Producto
    private ProgressDialog pDialog;
    private PedidoRepository pedidoCtr;
    private HelperPedidos hp;
    public IntentServiceEnvioPedidos() {
        super("IntentServiceStockPrecios");
    }

    public static void startActionGetStockPrecios(Context context) {

        Intent intent = new Intent(context, IntentServiceEnvioPedidos.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            while (true) {
                final String action = intent.getAction();
                try {
                    PedidoEntity paramPedido = new PedidoEntity();
                    List<PedidoEntity> listaPedidos = FactoryRepositories
                            .getInstancia()
                            .getPedidoRepository()
                            .abrir()
                            .findByEstadoId(GlobalValues.getInstancia().ESTADO_NUEVO);

                    for (PedidoEntity pedido : listaPedidos) {
                        URL url;
                        URLConnection urlConn;
                        DataOutputStream printout;
                        DataInputStream input;


                        //Seteo de conexion al servicio API
                        URL object = new URL(Configurador.urlPostPedido);
                        HttpURLConnection con = (HttpURLConnection) object.openConnection();
                        con.setDoOutput(true);
                        con.setDoInput(true);
                        con.setRequestProperty("Content-Type", "application/json");
                        con.setRequestProperty("Accept", "application/json");
                        con.setRequestMethod("POST");


                        //Create JSONObject here
                        JSONObject objectjson = new JSONObject();
                        JSONObject pedido_json = new JSONObject();
                        JSONArray pedidodetalles = new JSONArray();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date fechahoy = new Date();
                        String fechahoystr = dateFormat.format(fechahoy);
                        System.out.println(dateFormat.format(fechahoy));

                        pedido_json.put("fecha", String.valueOf(fechahoystr));
                        pedido_json.put("user_id", String.valueOf(GlobalValues.getInstancia().getUsuariologueado().getId()));
                        //pedido_json.put("cliente_id", paramPedido.getCliente_id().toString());
                        pedido_json.put("android_id", String.valueOf(paramPedido.getAndroid_id()));
                        pedido_json.put("estado_id", String.valueOf(GlobalValues.getInstancia().consPedidoEstadoEnviado));
                        pedido_json.put("monto", String.valueOf(paramPedido.getMonto()));
                        pedido_json.put("iva", String.valueOf(paramPedido.getIva()));
                        pedido_json.put("subtotal", String.valueOf(paramPedido.getSubtotal()));


                        JSONObject pedidodetallejson = new JSONObject();

                        //Armar Json detalle de pedidos
                        //Enviar Pedido Items
                        PedidodetalleEntity pd = new PedidodetalleEntity();
                        PedidodetalleRepository pdc = new PedidodetalleRepository(getBaseContext());

                        for (int x = 0; x < paramPedido.getItems().size(); x++) {
                            JSONObject item = new JSONObject();
                            pd = (PedidodetalleEntity) paramPedido.getItems().get(x);
                            item.put("producto_id", pd.getProductoId().toString());
                            item.put("cantidad", String.valueOf(pd.getCantidad()));
                            item.put("android_id", String.valueOf(pd.getAndroidId()));
                            item.put("pedido_android_id", String.valueOf(paramPedido.getAndroid_id()));
                            pedidodetalles.put(item);
                        }

                        pedido_json.put("pedidodetalles", pedidodetalles);
                        objectjson.put("pedido", pedido);

                        //Enviar Json
                        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                        wr.write(objectjson.toString());
                        wr.flush();

                        //Procesar Respuesta, capurar nuero de pedido en el sistema web, y asignar a pedido.setId()
                        StringBuilder sb = new StringBuilder();
                        int HttpResult = con.getResponseCode();
                        if (HttpResult == HttpURLConnection.HTTP_OK) {

                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(con.getInputStream(), "utf-8"));
                            String line = null;
                            while ((line = br.readLine()) != null) {
                                sb.append(line + "\n");
                            }
                            //JSONObject obj = new JSONObject(sb.toString());
                            br.close();
                            System.out.println("" + sb.toString());
                        } else {
                            System.out.println(con.getResponseMessage());
                        }

                        //Procesa post

                        PedidoParser pp = new PedidoParser(sb.toString());
                        PedidoEntity pedidopostsave = pp.parseJsonToObject();
                        PedidoEntity pedidoprevsave = pedidoCtr.abrir().findByAndroidId(paramPedido.getAndroid_id());
                        pedidoprevsave.setId(pedidopostsave.getId());
                        pedidoprevsave.setEstadoId(GlobalValues.getInstancia().consPedidoEstadoEnviado);
                        pedidoCtr.abrir().modificar(pedidoprevsave, true);
                        pedidoCtr.cerrar();
                        //setRespuesta(GlobalValues.getInstancia().RETURN_OK);
                        Log.println(Log.ERROR, "Helper:", " Guardado Correctamente ");

                    }
                    Thread.sleep(10000);

                } catch (InterruptedException e) {
                    // Restore interrupt status.
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    setRespuesta(GlobalValues.getInstancia().RETURN_ERROR);
                    Log.println(Log.ERROR, "ErrorHelper:", e.getMessage());
                    Toast.makeText(getBaseContext(), "Error " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }
}
