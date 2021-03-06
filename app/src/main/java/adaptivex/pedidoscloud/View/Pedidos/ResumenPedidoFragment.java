package adaptivex.pedidoscloud.View.Pedidos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Core.Interfaces.OnTaskCompleted;
import adaptivex.pedidoscloud.Core.WorkDate;
import adaptivex.pedidoscloud.Core.WorkNumber;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.Servicios.FactoryServices;

public class ResumenPedidoFragment extends Fragment implements View.OnClickListener ,OnTaskCompleted {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private TextView lbl_cantidad_kilos, lbl_kilos_monto, lbl_cucuruchos_monto, lbl_monto_total;
    private TextView txt_cucuruchos, txt_direccion, txt_cucharitas, txt_monto_total, txtEnvio,
                     txt_pedido_id, txt_hora_entrega, txt_estado,
                     txt_monto_descuento, txt_cantidad_descuento, txt_tiempo_demora, txt_monto_abona, lbl_cucuruchos;
    private Button   btnEnviarPedido;

    public ResumenPedidoFragment() {

    }


    public static ResumenPedidoFragment newInstance(String param1, String param2) {
        ResumenPedidoFragment fragment = new ResumenPedidoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.SEND_SMS )!= PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.SEND_SMS)){
                    ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.SEND_SMS}, 1);
                }else{
                    ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.SEND_SMS}, 1);
                }
            }else{
                // algo

            }
        }catch(Exception e){
            Toast.makeText(getContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_resumen_pedido, container, false);
        txt_direccion        = (TextView) v.findViewById(R.id.resumen_pedido_direccion);
        lbl_cantidad_kilos   = (TextView) v.findViewById(R.id.resumen_pedido_lbl_cantidad_kilos);
        lbl_kilos_monto      = (TextView) v.findViewById(R.id.resumen_pedido_lbl_kilos_monto);
        txt_cucuruchos       = (TextView) v.findViewById(R.id.resumen_pedido_txt_cucuruchos);
        lbl_cucuruchos_monto = (TextView) v.findViewById(R.id.resumen_pedido_txt_cucuruchos_monto);
        txt_cucharitas       = (TextView) v.findViewById(R.id.resumen_pedido_txt_cucharitas);
        lbl_monto_total      = (TextView) v.findViewById(R.id.resumen_pedido_txt_monto_total);
        txtEnvio             = (TextView) v.findViewById(R.id.resumen_pedido_txt_envio);
        txt_monto_total      = (TextView) v.findViewById(R.id.resumen_pedido_txt_monto_total);

        txt_estado           = (TextView) v.findViewById(R.id.resumen_pedido_txt_estado);
        txt_hora_entrega     = (TextView) v.findViewById(R.id.resumen_pedido_txt_hora_entrega);
        txt_tiempo_demora    = (TextView) v.findViewById(R.id.resumen_pedido_txt_tiempo_demora);
        txt_pedido_id        = (TextView) v.findViewById(R.id.resumen_pedido_txt_pedido_id);

        //txt_cantidad_descuento = (TextView) v.findViewById(R.id.resumen_pedido_txt_descuento_cantidad);
        txt_monto_descuento    = (TextView) v.findViewById(R.id.resumen_pedido_txt_descuento_monto);
        txt_monto_abona        = (TextView) v.findViewById(R.id.resumen_pedido_txt_monto_abona);
        lbl_cucuruchos         =  (TextView) v.findViewById(R.id.resumen_pedido_lbl_cucuruchos);


        lbl_cucuruchos.setText("Cucuruchos ("+ GlobalValues.getInstancia().PRECIO_CUCURUCHO_MONEY +" C/u): ");
        btnEnviarPedido        =  (Button) v.findViewById(R.id.resumen_pedido_btn_enviar);
        btnEnviarPedido.setOnClickListener(this);
        checkStatusPedido();
        refreshTextViews();
        return v;
    }





    public void refreshTextViews(){
        try{
            //Actualizar valor de los textview, formatear valores pesos a #.##
            txt_direccion.setText(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getStringDireccion());
            lbl_cantidad_kilos.setText(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getCantidadKilosFormatString());
            txt_cucuruchos.setText(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getCucuruchos().toString());
            lbl_cucuruchos_monto.setText(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getMontoCucuruchosFormatMoney());
            txt_cucharitas.setText(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getCucharitas().toString());
            txtEnvio.setText(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getEnvioDomicilio());

            txt_pedido_id.setText(WorkNumber.getValue(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getId()).toString());
            String estado = GlobalValues.getInstancia().ESTADOS[WorkNumber.getValue(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getEstadoId())];
            txt_estado.setText(estado);

            txt_hora_entrega.setText(WorkDate.parseDateToStringFormatHHmmss(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getHoraentrega()));

            txt_tiempo_demora.setText(WorkDate.calculateDiffereceDatesFormatMM(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getHoraentrega(),WorkDate.getNowDate() ));

            //txt_cantidad_descuento.setText(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getCantidadDescuento().toString());
            txt_monto_descuento.setText(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getMontoDescuentoFormatMoney());
            txt_monto_total.setText(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getMontoFormatMoney());
            lbl_kilos_monto.setText(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getMontoHeladoFormatMoney());

            txt_monto_abona.setText(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getMontoAbonaFormatMoney());

            btnEnviarPedido.setEnabled(true);
            if (FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getId()!=null){
                if (FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getId()> 0 ){
                    btnEnviarPedido.setEnabled(false);
                }
            }

        }catch(Exception e ){
            Toast.makeText(getContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void checkStatusPedido(){
        try{
            PedidoEntity p = FactoryRepositories.getInstancia().PEDIDO_TEMPORAL;
            if(p.getId()!=null){
                if (p.getId() > 0 ){
                    SystemClock.sleep(1000);
                }
            }
        }catch(Exception e ){
            Toast.makeText(getContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.resumen_pedido_btn_enviar:
                enviarPedido();



        }
    }


    public void sendSMS(String number, String message){
         try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
         }catch(Exception e){
             Toast.makeText(getContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
         }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case 1 :{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getContext(),"Permisos otorgados: " ,Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getContext(),"NO Permisos otorgados: " ,Toast.LENGTH_LONG).show();
                }
                return ;
            }


        }
    }


    public void enviarPedido(){
        //
        FactoryServices
                .getInstancia()
                .getPedidoServices()
                .postPedido(FactoryRepositories.PEDIDO_TEMPORAL);



    }




    @Override
    public void onTaskCompleted() {
       
    }

    @Override
    public void onTaskError() {
        Toast.makeText(getContext(),"Hubo un Error" ,Toast.LENGTH_LONG).show();

    }
}
