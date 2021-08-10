package adaptivex.pedidoscloud.View.RVAdapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Repositories.PedidoRepository;
import adaptivex.pedidoscloud.Repositories.PedidodetalleRepository;
import adaptivex.pedidoscloud.Entity.PedidodetalleEntity;
import adaptivex.pedidoscloud.Entity.PoteEntity;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.Pedidos.CargarCantidadFragment;
import adaptivex.pedidoscloud.View.Pedidos.CargarHeladosFragment;

/**
 * Created by egalvan on 11/3/2018.
 */

public class RVAdapterPote extends RecyclerView.Adapter<RVAdapterPote.PoteViewHolder> {
    private ArrayList<PoteEntity> potes;
    private ContextWrapper cw;
    private Context ctx;
    private FragmentManager fragmentManager;


    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public void RVAdapterPote(ArrayList<PoteEntity> potes){
        this.setPotes(potes);
    }



    @Override
    public int getItemCount() {
        return getPotes().size();
    }


    @Override
    public PoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pote, viewGroup, false);
        cw = new ContextWrapper(v.getContext());
        PoteViewHolder pvh = new PoteViewHolder(v, ctx, getPotes());
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PoteViewHolder holder, int i) {
        //Completa el Item Pote dentro del recycle view
        final PoteEntity pote = getPotes().get(i);

        holder.txtNro.setText(String.valueOf(getPotes().get(i).getNroPote()));
        holder.txtKilos.setText(getPotes().get(i).getCantidadKilosFormatString());
        holder.txtMonto.setText(getPotes().get(i).getMontoHeladoFormatMoney());
        holder.txtOption.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    //Algo
                                                    PopupMenu popmenu = new PopupMenu(ctx, holder.txtOption);
                                                    popmenu.inflate(R.menu.mnu_item_pote);
                                                    popmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                        @Override
                                                        public boolean onMenuItemClick(MenuItem item) {

                                                            switch (item.getItemId()) {
                                                                case R.id.menu_editar_helados:
                                                                    openEditarHelados(pote);

                                                                    break;
                                                                case R.id.menu_eliminar_pote:
                                                                    openEliminarHelados(pote);

                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                            return false;
                                                        }
                                                    });
                                                    popmenu.show();
                                                }


                                            });
                        }


    public void openEditarHelados(PoteEntity p){
        try{
            Bundle args =new Bundle();
            args.putLong(Constants.PARAM_PEDIDO_ANDROID_ID, p.getPedido().getAndroid_id());
            args.putInt(Constants.PARAM_PEDIDO_NRO_POTE, p.getNroPote());

            //Estas variables se usan cuando se da de alta un nuevo pedidodetalle
            GlobalValues.getInstancia().PEDIDO_ACTUAL_NRO_POTE    = p.getNroPote();
            GlobalValues.getInstancia().PEDIDO_ACTUAL_MEDIDA_POTE = p.getKilos();



            CargarHeladosFragment fragment      = new CargarHeladosFragment();
            fragment.setArguments(args);

            FragmentManager fragmentManager         = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }catch (Exception e){
            Toast.makeText(getCtx(), "Error: " +e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void openEliminarHelados(PoteEntity pote){
        try{

            FactoryRepositories.getInstancia().PEDIDO_TEMPORAL.quitarPote(pote);

            Fragment fragment                       = new CargarCantidadFragment();
            FragmentManager fragmentManager         = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }catch (Exception e){
            Toast.makeText(getCtx(), "Error: " +e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public ArrayList<PoteEntity> getPotes() {
        return potes;
    }

    public void setPotes(ArrayList<PoteEntity> potes) {
        this.potes = potes;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    public static class PoteViewHolder extends RecyclerView.ViewHolder
            implements  View.OnClickListener{

        private ArrayList<PoteEntity> potes = new ArrayList<PoteEntity>();
        private Context ctx;
        private CardView cv;
        private TextView txtNro, txtMonto, txtKilos, txtOption;
        private ListView lvHelados;

        OnHeadlineSelectedListener mCallback;


        public PoteViewHolder(View itemView, Context ctx, ArrayList<PoteEntity> potes) {
            super(itemView);
            this.potes = potes;
            this.ctx = ctx;
            itemView.setOnClickListener(this);

            //Obtener Referencias a Objetos
            txtNro      = (TextView) itemView.findViewById(R.id.item_pote_nro);
            txtMonto    = (TextView) itemView.findViewById(R.id.item_pote_monto);
            txtKilos    = (TextView) itemView.findViewById(R.id.item_pote_kilos);
            txtOption   = (TextView) itemView.findViewById(R.id.item_pote_option);
        }


        @Override
        public void onClick(View v) {
            int position  = getAdapterPosition();
            PoteEntity pote = this.potes.get(position);


        }


    }

    public interface OnHeadlineSelectedListener {

    }

}
