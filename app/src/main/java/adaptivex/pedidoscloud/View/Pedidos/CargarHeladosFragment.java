package adaptivex.pedidoscloud.View.Pedidos;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Repositories.PedidoRepository;
import adaptivex.pedidoscloud.Repositories.PedidodetalleRepository;
import adaptivex.pedidoscloud.Repositories.ProductoRepository;
import adaptivex.pedidoscloud.Entity.ItemHelado;
import adaptivex.pedidoscloud.Entity.PedidodetalleEntity;
import adaptivex.pedidoscloud.Entity.ProductoEntity;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterHelado;

public class CargarHeladosFragment extends Fragment implements View.OnClickListener, View.OnKeyListener {

    private OnFragmentInteractionListener mListener;

    //Variables
    private RecyclerView rvHelados;

    private ArrayList<Object> listaHelados = new ArrayList<Object>();
    private RVAdapterHelado rvAdapterHelado;

    //Lista que se carga cuando se recibe por parametro el pedidoid y nropote

    private long    pedido_android_id = 0 ;
    private Integer pedido_nro_pote = 0 ;






    public CargarHeladosFragment() {
        // Required empty public constructor
    }


    public static CargarHeladosFragment newInstance(String param1, String param2) {
        CargarHeladosFragment fragment = new CargarHeladosFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            /* Edicion de pedido */
            pedido_android_id = getArguments().getLong(Constants.PARAM_PEDIDO_ANDROID_ID);
            pedido_nro_pote = getArguments().getInt(Constants.PARAM_PEDIDO_NRO_POTE);



        }

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_cargar_helados, container, false);
            ArrayList<PedidodetalleEntity> listaHeladosSelected = new ArrayList<PedidodetalleEntity>();

            rvHelados = (RecyclerView)v.findViewById(R.id.rvHelados);
            rvHelados.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
            rvHelados.setLayoutManager(llm);

            rvAdapterHelado = new RVAdapterHelado();
            rvAdapterHelado.init(v.getContext(), pedido_android_id, pedido_nro_pote );

            rvHelados.setAdapter(rvAdapterHelado);

            Button btnListo = (Button) v.findViewById(R.id.cargar_helados_btn_listo);
            btnListo.setOnClickListener(this);

            return v;
        }catch(Exception e){
            Toast.makeText(getContext(), "Error: " +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }









    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void clickListo(){
        if (validateForm()){
            // Gennerar los pedidos detalles
            if(savePedidodetalle()){
                //Cerrar Fragment actual
                openFragmentCargarCantidad();
            };
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cargar_helados_btn_listo:
                clickListo();
                break;



        }
    }

    public void openFragmentCargarCantidad(){
        //getFragmentManager().beginTransaction().remove(this).commit();
        CargarCantidadFragment fragment      = new CargarCantidadFragment();
        FragmentManager fragmentManager         = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.addToBackStack(Constants.FRAGMENT_CARGAR_HELADOS);
        fragmentTransaction.commit();
    }




    public boolean savePedidodetalle(){

        try{
            PedidodetalleRepository pdc = new PedidodetalleRepository(getContext());
            PedidoRepository pc = new PedidoRepository(getContext());

            /*
            * 1 recorrer lista de items seleccionados
            * 2 consultar si el item esta en selecionado, obtener item
            * 3     consultar si item tiene pedidodetalle asignado, entonces se actualiza en DB y en Listatemporal
            * 4
         * * */
            for (int i=0; i<GlobalValues.getInstancia().listaHeladosSeleccionados.size(); i++){


                //si no esta seleccionado, busca si tiene pedidodetalle y lo eliminar de la BD y de la lista
                if (!GlobalValues.getInstancia().listaHeladosSeleccionados.get(i).isChecked()){
                    ItemHelado item = (ItemHelado) (GlobalValues.getInstancia().listaHeladosSeleccionados.get(i));
                    if (item.getPedidodetalle() !=null){
                         pdc.abrir().eliminar(item.getPedidodetalle());
                         item.setPedidodetalle(null);
                    }
                }


                if (GlobalValues.getInstancia().listaHeladosSeleccionados.get(i).isChecked()){
                    ItemHelado item = (ItemHelado) (GlobalValues.getInstancia().listaHeladosSeleccionados.get(i));
                    PedidodetalleEntity pd = new PedidodetalleEntity();
                    if (item.getPedidodetalle() ==null){
                        //agregar item
                        pd.setId(0);
                        pd.setNroPote(GlobalValues.getInstancia().PEDIDO_ACTUAL_NRO_POTE);

                    }else{
                        //Editar Item
                        pd = item.getPedidodetalle();
                    }
                    pd.setPedidoAndroidId(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getAndroid_id());
                    pd.setEstadoId(Constants.ESTADO_NUEVO);
                    pd.setMedidaPote(GlobalValues.getInstancia().PEDIDO_ACTUAL_MEDIDA_POTE);
                    pd.setMonto(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.getPrecioMedidaPote(GlobalValues.getInstancia().PEDIDO_ACTUAL_MEDIDA_POTE));
                    pd.setCantidad(Double.parseDouble(item.getProporcion().toString())); //POCO - EQUILIBRADO - MUCHO
                    pd.setProporcionHelado(item.getProporcion()); //POCO - EQUILIBRADO - MUCHO

                    pd.setProducto(item.getHelado());




                    if (item.getPedidodetalle() == null){
                        //agregar item
                        long idAndroid = pdc.abrir().agregar(pd);
                        Integer idandroidinteger = (int) (long) idAndroid;
                        pd.setAndroidId(idandroidinteger);
                        FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.addPedidodetalle(pd);

                    }else{
                        //Editar Item

                        pdc.abrir().modificar(pd);
                        FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.editPedidodetalle(pd);

                    }



                }
            }
            pc.abrir().edit(FactoryRepositories.getInstancia().PEDIDO_TEMPORAL);
            return true;
        }catch (Exception e ){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }




    public boolean validateForm(){
        boolean validate = true;
        Integer contador = 0;
        try{

            for (int i=0; i<GlobalValues.getInstancia().listaHeladosSeleccionados.size(); i++){
                if (GlobalValues.getInstancia().listaHeladosSeleccionados.get(i).isChecked()){
                    contador++;
                }
            }

            if (GlobalValues.getInstancia().PEDIDO_ACTUAL_MEDIDA_POTE==Constants.MEDIDA_KILO && contador > 4){
                Toast.makeText(getContext(), "Solo se Pueden Elegir Hasta 4 Helados ",Toast.LENGTH_LONG).show();
                validate = false;
            }

            if (GlobalValues.getInstancia().PEDIDO_ACTUAL_MEDIDA_POTE!=Constants.MEDIDA_KILO && contador > 3){
                Toast.makeText(getContext(), "Solo se Pueden Elegir Hasta 3 Helados ",Toast.LENGTH_LONG).show();
                validate = false;
            }
            if ( contador == 0){
                Toast.makeText(getContext(), "Debe Seleccionar al Menos 1 Helado ",Toast.LENGTH_LONG).show();
                validate = false;
            }
            return validate;

        }catch (Exception e){
            Toast.makeText(getContext(), "Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
            return validate;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_BACK )
        {
            if (validateForm()){
                return true;
            }
        }
        return false;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        void onFragmentInteraction(Uri uri);
    }
}
