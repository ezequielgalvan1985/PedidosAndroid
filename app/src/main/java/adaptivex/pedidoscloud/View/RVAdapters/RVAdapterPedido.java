package adaptivex.pedidoscloud.View.RVAdapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
import adaptivex.pedidoscloud.R;

import java.util.ArrayList;

/**
 * Created by ezequiel on 22/06/2016.
 */
public class RVAdapterPedido extends RecyclerView.Adapter<RVAdapterPedido.PedidoViewHolder>{
    private ArrayList<PedidoEntity> pedidos;
    private ContextWrapper cw;
    private Context ctx;

    public Context getCtx() {
        return ctx;
    }
    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }
    public ArrayList<PedidoEntity> getPedidos() {
        return pedidos;
    }



    public void RVAdapterPedido(ArrayList<PedidoEntity> pedidos){
        this.setPedidos(pedidos);
    }


    @Override
    public int getItemCount() {
        return pedidos.size();
    }



    @Override
    public PedidoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pedido, viewGroup, false);
        cw = new ContextWrapper(v.getContext());
        PedidoViewHolder pvh = new PedidoViewHolder(v, getCtx(), getPedidos());
        return pvh;
    }

    @Override
    public void onBindViewHolder(PedidoViewHolder pedidoViewHolder, int i) {
        pedidoViewHolder.tvIpfId.setText(String.valueOf(pedidos.get(i).getId()));
        pedidoViewHolder.tvCreated.setText(pedidos.get(i).getCreated());
        pedidoViewHolder.tvMonto.setText(String.valueOf(pedidos.get(i).getMonto()));
        pedidoViewHolder.tvIpfEstadoDesc.setText(GlobalValues.ESTADOS[pedidos.get(i).getEstadoId()]);
        pedidoViewHolder.tvIpfIdTmp.setText(String.valueOf( pedidos.get(i).getAndroid_id()));
        pedidoViewHolder.tvIpfClienteDesc.setText(pedidos.get(i).getCliente().getContacto());

        GlobalValues.getInstancia().setVgPedidoSeleccionado(pedidos.get(i).getAndroid_id());



    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setPedidos(ArrayList<PedidoEntity> pedidos) {
        this.pedidos = pedidos;
    }





    public static class PedidoViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        ArrayList<PedidoEntity> pedidos = new ArrayList<PedidoEntity>();
        Context ctx;
        OnHeadlineSelectedListener mCallback;


        CardView cv;
        TextView tvIpfId, tvCreated, tvSubtotal, tvIva, tvMonto,
                 tvClienteId, tvBonificacion, tvEstadoId, tvIpfEstadoDesc, tvIpfIdTmp, tvIpfClienteDesc;
        Button btnEnviar;


        public PedidoViewHolder(View itemView, Context ctx, ArrayList<PedidoEntity> pedidos) {
            super(itemView);

            mCallback = (OnHeadlineSelectedListener) ctx;
            this.pedidos = pedidos;
            this.ctx = ctx;
            itemView.setOnClickListener(this);

            cv = (CardView) itemView.findViewById(R.id.cvPedido);
            tvIpfId = (TextView) itemView.findViewById(R.id.tvIpfId);
            tvIpfIdTmp = (TextView) itemView.findViewById(R.id.tvIpfIdTmp);
            tvCreated = (TextView) itemView.findViewById(R.id.tvIpfCreated);
            tvMonto = (TextView) itemView.findViewById(R.id.tvIpfMonto);
            tvIpfEstadoDesc = (TextView) itemView.findViewById(R.id.tvIpfEstadoDesc);
            tvIpfClienteDesc = (TextView) itemView.findViewById(R.id.tvIpfClienteDesc);
            btnEnviar = (Button) itemView.findViewById(R.id.btnEnviarPedido);


            btnEnviar = (Button) itemView.findViewById(R.id.btnEnviarPedido);
            btnEnviar.setOnClickListener(this);
            //Elimina el Pedido Seleccionado



        }
        @Override
        public void onClick(View v) {
            int position  = getAdapterPosition();
            PedidoEntity pedido = this.pedidos.get(position);
             if (v.getId()== R.id.btnEnviarPedido) {
                /* Enviar el pedido
                * Obtener el idTmp
                * crear un PedidoController
                * Elminar registro
                * Mostrar mensaje con el resultado
                */
                try{


                }catch (Exception e){
                    Toast.makeText(v.getContext(),"Error RVAdapterPedido: "+ e.getMessage(), Toast.LENGTH_LONG);
                }

            }else{
                    GlobalValues.getInstancia().setPEDIDO_ACTION_VALUE(GlobalValues.getInstancia().PEDIDO_ACTION_VIEW);
            }

            this.mCallback.onPedidoSelected(position, pedido);


        }

    }
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListener {
        public void onPedidoSelected(int position, PedidoEntity pedido);
    }





}