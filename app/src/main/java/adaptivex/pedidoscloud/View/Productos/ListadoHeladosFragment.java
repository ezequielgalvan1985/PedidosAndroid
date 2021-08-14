package adaptivex.pedidoscloud.View.Productos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Entity.ProductoEntity;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Repositories.ProductoRepository;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.ProductoDataBaseHelper;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterProducto;

public class ListadoHeladosFragment extends Fragment {

    private RecyclerView rvHelados;



    public ListadoHeladosFragment() {
        // Required empty public constructor
    }


    public static ListadoHeladosFragment newInstance(String param1, String param2) {
        ListadoHeladosFragment fragment = new ListadoHeladosFragment();

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
        View v = inflater.inflate(R.layout.fragment_listado_helados, container, false);
        TextView titulo = (TextView) v.findViewById(R.id.listado_helados_titulo);
        //1 - RECYCLE VIEW
        rvHelados = (RecyclerView)v.findViewById(R.id.rvHeladosPostres);
        GridLayoutManager manager = new GridLayoutManager(v.getContext(), 1, GridLayoutManager.VERTICAL, false);
        rvHelados.setLayoutManager(manager);
        RVAdapterProducto adapterProducto = new RVAdapterProducto(getFragmentManager());
        rvHelados.setAdapter(adapterProducto);
        return v;
    }





}
