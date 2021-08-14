package adaptivex.pedidoscloud.View.Pedidos;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.nio.file.attribute.FileAttribute;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
import adaptivex.pedidoscloud.Entity.PedidodetalleEntity;
import adaptivex.pedidoscloud.Entity.ProductoEntity;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PRODUCTO_ID = "producto_id";

    // TODO: Rename and change types of parameters
    private long mProductoId;
    private ProductoEntity producto;
    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PRODUCTO_ID, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductoId = getArguments().getLong(ARG_PRODUCTO_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try{
            View v = inflater.inflate(R.layout.fragment_detail, container, false);

            // se crean los objetos
            TextView txtProductoId    = (TextView) v.findViewById(R.id.fg_detail_producto_id);
            TextView txtNombre        = (TextView) v.findViewById(R.id.fg_detail_producto_nombre);
            TextView txtDescripcion   = (TextView) v.findViewById(R.id.fg_detail_producto_descripcion);
            TextView txtPrecio        = (TextView) v.findViewById(R.id.fg_detail_producto_precio);
            Button  btnAgregar        = (Button) v.findViewById(R.id.fg_detail_producto_btn_agregar);
            TextView txtCantidad      = (TextView) v.findViewById(R.id.fg_detail_producto_cantidad);

            //se asignan los valores
            producto = FactoryRepositories
                    .getInstancia()
                    .getProductoRepository()
                    .abrir()
                    .findById(mProductoId);

            txtProductoId.setText(producto.getId().toString());
            txtNombre.setText(producto.getNombre());
            txtDescripcion.setText(producto.getDescripcion());
            txtPrecio.setText(String.valueOf(producto.getPrecio()));

            //si ya existe un item detalle con el producto, se carga la cantidad
            PedidodetalleEntity detalle = FactoryRepositories
                    .getInstancia()
                    .getPedidodetalleRepository()
                    .abrir()
                    .findByPedidoAndProducto(FactoryRepositories.PEDIDO_TEMPORAL, producto);
            if (detalle !=null)
                txtCantidad.setText(String.valueOf(detalle.getCantidad()));

            btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //si existe producto en pedido detalle:
                //          se actualiza
                //   de lo contrario: se agrega pedido detalle cantidad al pedido detalle
                PedidodetalleEntity pd = new PedidodetalleEntity();
                pd.setCantidad(Double.valueOf(txtCantidad.getText().toString()));
                pd.setProducto(producto);

                //resuelve si agregar o modificar un items
                FactoryRepositories
                        .getInstancia()
                        .getPedidoRepository()
                        .abrir()
                        .modificarItems(FactoryRepositories.PEDIDO_TEMPORAL, pd);

                getFragmentManager().popBackStack();
            }
        });
        return v;
        }catch(Exception e ){
            Log.e("DetailFragment", e.getMessage());
            return null;
        }
    }
}