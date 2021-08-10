package adaptivex.pedidoscloud.View.Pedidos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Repositories.PedidoRepository;
import adaptivex.pedidoscloud.Repositories.UserRepository;
import adaptivex.pedidoscloud.Entity.UserEntity;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperUser;

public class CargarDireccionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private AutoCompleteTextView txtLocalidad;
    private AutoCompleteTextView txtCalle;
    private AutoCompleteTextView txtTelefono;
    private AutoCompleteTextView txtNro;
    private AutoCompleteTextView txtPiso;
    private AutoCompleteTextView txtContacto;
    private Button               btnSiguiente;
    private Button               btnAnterior;
    private TextView             lblTitulo;
    private boolean              MODE_EDIT_USER = false;


    private void flushPedidoTemporal(){
        FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.setTelefono(txtTelefono.getText().toString());
        FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.setLocalidad(txtLocalidad.getText().toString());
        FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.setCalle(txtCalle.getText().toString());
        FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.setNro(txtNro.getText().toString());
        FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.setPiso(txtPiso.getText().toString());
        FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.setContacto(txtContacto.getText().toString());
    }

    private boolean validateForm(){
        boolean validate = true;
        String message = "";

        txtTelefono  = (AutoCompleteTextView) getView().findViewById(R.id.cargar_direccion_telefono);
        txtLocalidad = (AutoCompleteTextView) getView().findViewById(R.id.cargar_direccion_localidad);
        txtCalle     = (AutoCompleteTextView) getView().findViewById(R.id.cargar_direccion_calle);
        txtNro       = (AutoCompleteTextView) getView().findViewById(R.id.cargar_direccion_nro);
        txtPiso      = (AutoCompleteTextView) getView().findViewById(R.id.cargar_direccion_piso);
        txtContacto  = (AutoCompleteTextView) getView().findViewById(R.id.cargar_direccion_contacto);

        if (txtTelefono.getText() == null || txtTelefono.getText().toString().isEmpty())
        {
            validate  = false;
            message ="* Telefono es Obligatorio \n";
        }

        if (txtLocalidad.getText() == null || txtLocalidad.getText().toString().isEmpty())
        {
            message +="* Localidad es Obligatorio \n";
            validate = false;
        }

        if (txtCalle.getText() == null || txtCalle.getText().toString().isEmpty()){
            validate     = false;
            message +="* Calle es Obligatorio \n";
        }

        if (txtNro.getText() == null || txtNro.getText().toString().isEmpty()){
            validate  = false;
            message  +="* Nro es Obligatorio \n";
        }
        if (txtContacto.getText() == null || txtContacto.getText().toString().isEmpty()){
            validate  = false;
            message +="* Contacto es Obligatorio \n";
        }

        if (validate == false){
            Toast.makeText(getView().getContext(),message,Toast.LENGTH_LONG).show();
        }
        return validate;
    }


    public CargarDireccionFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try{
            View v = inflater.inflate(R.layout.fragment_cargar_direccion, container, false);
            GlobalValues.getInstancia().CURRENT_FRAGMENT_NUEVO_PEDIDO = GlobalValues.getInstancia().NP_CARGAR_DIRECCION;
            //Cargar datos del usuario logueado


            if (GlobalValues.getInstancia().getUsuariologueado()==null){
                Toast.makeText(v.getContext(), "Error: No se pudo obtener los datos de usuario", Toast.LENGTH_SHORT).show();
                return null;
            }
            //Asignar los valores a los campos
            txtTelefono   = (AutoCompleteTextView) v.findViewById(R.id.cargar_direccion_telefono);
            txtLocalidad  = (AutoCompleteTextView) v.findViewById(R.id.cargar_direccion_localidad);
            txtCalle      = (AutoCompleteTextView) v.findViewById(R.id.cargar_direccion_calle);
            txtNro        = (AutoCompleteTextView) v.findViewById(R.id.cargar_direccion_nro);
            txtPiso       = (AutoCompleteTextView) v.findViewById(R.id.cargar_direccion_piso);
            txtContacto   = (AutoCompleteTextView) v.findViewById(R.id.cargar_direccion_contacto);
            lblTitulo = (TextView) v.findViewById(R.id.cargar_direccion_lbl_titulo);
            btnSiguiente = (Button) v.findViewById(R.id.cargar_direccion_btn_siguiente);

            txtTelefono.setText(GlobalValues.getInstancia().getUsuariologueado().getTelefono());
            txtCalle.setText(GlobalValues.getInstancia().getUsuariologueado().getCalle());
            txtPiso.setText(GlobalValues.getInstancia().getUsuariologueado().getPiso());
            txtNro.setText(GlobalValues.getInstancia().getUsuariologueado().getNro());
            txtContacto.setText(GlobalValues.getInstancia().getUsuariologueado().getContacto());



            btnSiguiente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Realizar validaciones
                    if(validateForm()){
                        //LLAMAR AL SIGUIENTE FRAMENT
                        if (MODE_EDIT_USER) {
                            if (saveDireccionUser()){
                                Toast.makeText(getContext(),"Datos Guardado Correctamente",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            if (saveDireccion()){
                                openFragmentCargarCantidad();
                            }
                        }
                    }
                }
            });
            return v;
        }catch(Exception e){
            Log.e("CargarDireccion",e.getMessage());
            return null;
        }
    }
    public void setTitle(){
        if (MODE_EDIT_USER){
            lblTitulo.setText("Mi Cuenta - Editar Dirección");
        }
    }
    public void openFragmentCargarCantidad(){
        //getFragmentManager().beginTransaction().remove(this).commit();
        CargarCantidadFragment fragment         = new CargarCantidadFragment();
        FragmentManager fragmentManager         = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.addToBackStack(Constants.FRAGMENT_CARGAR_DIRECCION);
        fragmentTransaction.commit();
    }


    public boolean saveDireccionUser() {
        try{
            UserRepository uc = new UserRepository(getContext());
            UserEntity u = uc.abrir().getUserDB(GlobalValues.getInstancia().getUsuariologueado().getId());
            uc.abrir().editDB(u);

            //Envia datos al servidor
            HelperUser hu = new HelperUser(getContext());
            hu.setOpcion(HelperUser.OPTION_UPDATE);
            hu.setUser(u);
            hu.execute();


            return true;
        }catch(Exception e) {
            Toast.makeText(getContext(), "Error: No se pudo obtener los datos de usuario", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean saveDireccion(){
        boolean validate = false;
        flushPedidoTemporal();
        PedidoRepository pc = new PedidoRepository(getContext());
        pc.abrir().edit(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL);
        validate = true;
        return validate;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            MODE_EDIT_USER = getArguments().getBoolean(Constants.PARAM_MODE_EDIT_USER);
        }
    }

}
