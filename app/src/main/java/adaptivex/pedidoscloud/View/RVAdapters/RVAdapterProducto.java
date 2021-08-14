package adaptivex.pedidoscloud.View.RVAdapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Entity.ProductoEntity;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.View.Pedidos.CargarProductosFragment;
import adaptivex.pedidoscloud.View.Pedidos.DetailFragment;

import java.util.ArrayList;

/**
 * Created by ezequiel on 22/06/2016.
 */
public class RVAdapterProducto extends RecyclerView.Adapter<RVAdapterProducto.ProductoViewHolder>
{

    private ContextWrapper cw;
    private ArrayList<ProductoEntity> lista;

    public RVAdapterProducto(FragmentManager fragmentManager) {
        GlobalValues.getInstancia().setFragmentManager(fragmentManager);
    }

    @Override
    public int getItemCount() {
        lista = FactoryRepositories
                .getInstancia()
                .getProductoRepository()
                .abrir()
                .findAll();
        return lista.size();
    }


    @Override
    public ProductoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_producto, viewGroup, false);
        cw = new ContextWrapper(v.getContext());
        ProductoViewHolder pvh = new ProductoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ProductoViewHolder productoViewHolder, int i) {
        productoViewHolder.ptvId.setText(lista.get(i).getId().toString());
        productoViewHolder.pNombre.setText(lista.get(i).getNombre());
        productoViewHolder.pPrecio.setText(String.valueOf(lista.get(i).getPrecio()));

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class ProductoViewHolder extends RecyclerView.ViewHolder{
        TextView pNombre, ptvId, pPrecio;

        public ProductoViewHolder(View itemView) {
            super(itemView);
            ptvId        = (TextView)itemView.findViewById(R.id.item_producto_id);
            pNombre      = (TextView)itemView.findViewById(R.id.item_producto_nombre);
            pPrecio = (TextView)itemView.findViewById(R.id.item_producto_precio);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                    Log.d("RVAdapterProducto", "click ");

                    DetailFragment fragment         = new DetailFragment();
                    Bundle bundle                   = new Bundle();
                    bundle.putLong("producto_id", Long.parseLong(ptvId.getText().toString()));
                    fragment.setArguments(bundle);

                    FragmentTransaction fragmentTransaction = GlobalValues.getInstancia().getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_main, fragment);
                    fragmentTransaction.addToBackStack(Constants.FRAGMENT_DETAIL);
                    fragmentTransaction.commit();
                    }catch(Exception e){
                        Log.e("rvadapterproducto",e.getMessage());
                    }
                }
            });
        }
    }
}