package adaptivex.pedidoscloud;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Repositories.PedidoRepository;
import adaptivex.pedidoscloud.Core.IniciarApp;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
import adaptivex.pedidoscloud.View.Pedidos.CargarProductosFragment;

public class NuevoPedidoActivity extends AppCompatActivity implements  CargarProductosFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }








    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
