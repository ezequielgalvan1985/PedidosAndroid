package adaptivex.pedidoscloud.View.Pedidos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Entity.ParameterEntity;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Repositories.PedidoRepository;
import adaptivex.pedidoscloud.Core.WorkNumber;
import adaptivex.pedidoscloud.R;

public class CargarOtrosDatosFragment extends Fragment  {

    private EditText txtCucuruchos, txtMontoAbona,txtCucharitas;
    private TextView txtCucuruchoPrecio, txt_monto_total_helados;
    private CheckBox chkEnvio;
    private Button   btnListo;



    private void getDataForm(){
        if (txtCucuruchos.getText()!= null) FactoryRepositories.PEDIDO_TEMPORAL.setCucuruchos(WorkNumber.parseInteger(txtCucuruchos.getText().toString()));
        if (txtCucharitas.getText()!= null) FactoryRepositories.PEDIDO_TEMPORAL.setCucharitas(WorkNumber.parseInteger(txtCucharitas.getText().toString()));
        if (txtMontoAbona.getText()!= null) FactoryRepositories.PEDIDO_TEMPORAL.setMontoabona(WorkNumber.parseDouble(txtMontoAbona.getText().toString()));
        FactoryRepositories.PEDIDO_TEMPORAL.setEnvioDomicilio(chkEnvio.isChecked());
    }

    public CargarOtrosDatosFragment() {
        // Required empty public constructor
    }


    public static CargarOtrosDatosFragment newInstance(String param1, String param2) {
        CargarOtrosDatosFragment fragment = new CargarOtrosDatosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try{
            View v = inflater.inflate(R.layout.fragment_cargar_otros_datos, container, false);
            //Inicializacion de Objetos
            txtCucuruchoPrecio      = (TextView) v.findViewById(R.id.cargar_otros_datos_txt_cucuruchos_precio);
            txtCucuruchos           = (EditText) v.findViewById(R.id.otros_datos_cantidad_cucuruchos);
            txtCucharitas           = (EditText) v.findViewById(R.id.otros_datos_cantidad_cucharitas);
            txtMontoAbona           = (EditText) v.findViewById(R.id.otros_datos_txt_monto_abona);
            chkEnvio                = (CheckBox) v.findViewById(R.id.otros_datos_chk_envio);
            btnListo                = (Button)   v.findViewById(R.id.otros_datos_btn_listo);
            txt_monto_total_helados = (TextView) v.findViewById(R.id.otros_datos_txt_monto_total_helados);

            //Cargar datos en los Objetos
            btnListo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validateForm()){
                        getDataForm();
                        FactoryRepositories
                                .getInstancia()
                                .getPedidoRepository()
                                .abrir()
                                .modificar(FactoryRepositories.PEDIDO_TEMPORAL);
                        openResumenFragment();
                    }
                }
            });
            ParameterEntity param = FactoryRepositories.getInstancia().getParameterRepository().abrir().findByNombre(Constants.PARAM_PRECIO_CUCURUCHO);

            txtCucuruchoPrecio.setText("Cucurucho ("+ param.getValor_decimal().toString()+" c/u):");
            txt_monto_total_helados.setText("Monto a Pagar: "+ FactoryRepositories.PEDIDO_TEMPORAL.getMontoHeladoFormatMoney());


            return v;
        }catch (Exception e){
            return null;
        }

    }





    public void openResumenFragment(){
        ResumenPedidoFragment fragment = new ResumenPedidoFragment();
        FragmentManager fragmentManager         = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.addToBackStack(Constants.FRAGMENT_CARGAR_OTROS_DATOS);
        fragmentTransaction.commit();
    }


    private boolean validateForm(){
        boolean validate = true;
        String message = "";
        Double monto = WorkNumber.parseDouble(txtMontoAbona.getText().toString());
        if (monto > 0.00 && monto < FactoryRepositories.PEDIDO_TEMPORAL.getMontoHelados() )
        {
            validate  = false;
            message ="* Monto ABONADO debe ser mayor a Monto a PAGAR \n";
        }
        if (validate == false){
            Toast.makeText(getView().getContext(),message,Toast.LENGTH_LONG).show();
        }
        return validate;
    }




}
