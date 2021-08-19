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
import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterProducto;

public class CargarProductosFragment extends Fragment  {

    private OnFragmentInteractionListener mListener;

    //Variables
    private RecyclerView rvProductos;
    private RVAdapterProducto rvAdapterProducto ;

    public CargarProductosFragment() {
    }


    public static CargarProductosFragment newInstance(String param1, String param2) {
        CargarProductosFragment fragment = new CargarProductosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_cargar_productos, container, false);

            rvProductos = (RecyclerView)v.findViewById(R.id.rvProductos);
            rvProductos.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
            rvProductos.setLayoutManager(llm);

            rvAdapterProducto = new RVAdapterProducto(getFragmentManager());
            rvProductos.setAdapter(rvAdapterProducto);

            Button btnCargarProductosListo = (Button) v.findViewById(R.id.cargar_productos_btn_listo);

            btnCargarProductosListo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //validar que se cargo algo

                    CargarOtrosDatosFragment fragment       = new CargarOtrosDatosFragment();
                    FragmentManager fragmentManager         = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_main, fragment);
                    fragmentTransaction.addToBackStack(Constants.FRAGMENT_CARGAR_OTROS_DATOS);
                    fragmentTransaction.commit();
                }
            });

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









    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        void onFragmentInteraction(Uri uri);
    }
}
