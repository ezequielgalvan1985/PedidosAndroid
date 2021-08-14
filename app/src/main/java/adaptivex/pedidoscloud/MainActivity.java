package adaptivex.pedidoscloud;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Core.IniciarApp;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
import adaptivex.pedidoscloud.View.Categorias.ListadoCategoriasFragment;
import adaptivex.pedidoscloud.View.Consulting.ConfigFragment;
import adaptivex.pedidoscloud.View.Consulting.ResumenFragment;
import adaptivex.pedidoscloud.View.Marcas.ListadoMarcasFragment;
import adaptivex.pedidoscloud.View.Pedidos.CargarDireccionFragment;
import adaptivex.pedidoscloud.View.Pedidos.CargarProductosFragment;
import adaptivex.pedidoscloud.View.Pedidos.ListadoPedidosFragment;
import adaptivex.pedidoscloud.View.Pedidos.ResumenPedidoFragment;
import adaptivex.pedidoscloud.View.Productos.ListadoHeladosFragment;
import adaptivex.pedidoscloud.View.Promos.ListadoPromosFragment;
import adaptivex.pedidoscloud.View.Pruebas.DescargaImagenActivity;
import adaptivex.pedidoscloud.View.Resumenes.HomeFragment;

public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        ListadoCategoriasFragment.OnFragmentInteractionListener,
        ListadoMarcasFragment.OnFragmentInteractionListener,

        HomeFragment.OnFragmentInteractionListener,
        ResumenFragment.OnFragmentInteractionListener,
        ConfigFragment.OnFragmentInteractionListener,
        CargarProductosFragment.OnFragmentInteractionListener,
        ListadoPromosFragment.OnFragmentInteractionListener
{
    private FloatingActionButton BTN_PRINCIPAL;
    protected Intent intentServiceStockPrecios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Actualizando la informacion del sistema...", Toast.LENGTH_LONG);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





        Fragment  fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, fragment).addToBackStack(Constants.FRAGMENT_CARGAR_HOME)
                .commit();



    }



    @Override
    public void onResume() {
        super.onResume();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return  super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        boolean fragmentTransaction = false;
        Fragment fragment = null;
        Bundle args = new Bundle();
        //noinspection SimplifiableIfStatement


        if (id == R.id.mnu_nuevo_pedido) {
            if (FactoryRepositories.getInstancia()
                    .getPedidoRepository()
                    .abrir()
                    .crearNuevoPedido() > 0){
                fragment = new CargarDireccionFragment();
                fragmentTransaction = true;
            }

        }else if (id == R.id.mnu_ver_pedido_actual) {
            fragment = new ResumenPedidoFragment();

            if (FactoryRepositories.getInstancia()
                    .getPedidoRepository()
                    .abrir()
                    .findByPedidoActual()
                    .getAndroid_id()>0)
            {
                fragmentTransaction = true;
            }else{
                Toast.makeText(this, "No Hay Pedidos EN PREPARACIÓN", Toast.LENGTH_LONG).show();
                Log.println(Log.ERROR, "MainActivity:", " No Hay Pedidos Generados ");
            }

        }else if (id==R.id.mnu_continuar_pedido_actual){

            long nroPedido = FactoryRepositories.getInstancia().getPedidoRepository().findByLastAndroidId();
            if (nroPedido > 0) {
                PedidoEntity p = FactoryRepositories.getInstancia().getPedidoRepository().findByAndroidId(nroPedido);
                FactoryRepositories.getInstancia().PEDIDO_TEMPORAL = p;
                fragment = new CargarDireccionFragment();
            } else {
                Toast.makeText(this, "MainActivity: No Hay Pedidos Generados", Toast.LENGTH_LONG);
                Log.println(Log.ERROR, "MainActivity:", " No Hay Pedidos Generados ");
            }

            GlobalValues.getInstancia().setVgFlagMenuNuevoPedido(true);
            fragmentTransaction = true;
            fragment.setArguments(args);


        }

        if (fragmentTransaction) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, fragment).addToBackStack(null)
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //Click en menu Navegation
        boolean fragmentTransaction = false;
        Fragment fragment = null;
        Bundle args = new Bundle();
        try{
            switch (id){

                case R.id.nav_promos:
                    try{
                        fragment = new ListadoPromosFragment();
                        fragmentTransaction = true;
                    }catch(Exception e){
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case  R.id.nav_pedidos_pendientes:
                    try{
                        fragment = new ListadoPedidosFragment();
                        fragmentTransaction = true;

                        GlobalValues.getInstancia().setActualFragment(GlobalValues.getInstancia().LISTADOPEDIDOS);
                        GlobalValues.getInstancia().setESTADO_ID_SELECCIONADO(GlobalValues.getInstancia().consPedidoEstadoNuevo);
                    }catch(Exception e){
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                case R.id.nav_pedidos_enviados:
                    try {
                        fragment = new ListadoPedidosFragment();
                        fragmentTransaction = true;

                        GlobalValues.getInstancia().setActualFragment(GlobalValues.getInstancia().LISTADOPEDIDOS);
                        GlobalValues.getInstancia().setESTADO_ID_SELECCIONADO(GlobalValues.getInstancia().consPedidoEstadoEnviado);
                    }catch(Exception e){
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.nav_enviarpedidospendientes:
                    try {

                    }catch(Exception e){
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case  R.id.nav_categorias:
                    fragment = new ListadoCategoriasFragment();
                    fragmentTransaction = true;
                    GlobalValues.getInstancia().setActualFragment(GlobalValues.getInstancia().LISTADOCATEGORIAS);
                    break;

                case R.id.nav_productos:
                    fragment = new ListadoHeladosFragment();
                    args.putInt(Constants.PARAM_TIPO_LISTADO,Constants.VALUE_TIPO_LISTADO_HELADOS);
                    fragment.setArguments(args);
                    fragmentTransaction = true;
                    break;

                case R.id.nav_postres:
                    fragment = new ListadoHeladosFragment();
                    args.putInt(Constants.PARAM_TIPO_LISTADO,Constants.VALUE_TIPO_LISTADO_POSTRES);
                    fragment.setArguments(args);
                    fragmentTransaction = true;
                    break;

                case R.id.nav_marcas:
                    fragment = new ListadoMarcasFragment();
                    fragmentTransaction = true;
                    GlobalValues.getInstancia().setActualFragment(GlobalValues.getInstancia().LISTADOMARCAS);
                    break;

                case R.id.nav_pedidosentrega:
                    Toast.makeText(this, "Descarga de Pedidos para entregar " , Toast.LENGTH_LONG).show();
                    break;

                case R.id.nav_descargaImagen:
                    Intent i = new Intent(this, DescargaImagenActivity.class);
                    startActivity(i);
                    break;

                case R.id.nav_login:
                    Intent re = new Intent(this, RegisterActivity.class);
                    startActivity(re);
                    break;

                case R.id.nav_datos_user:
                    fragment = new CargarDireccionFragment();
                    args = new Bundle();
                    args.putBoolean(Constants.PARAM_MODE_EDIT_USER, Constants.PARAM_MODE_EDIT_USER_ON);
                    fragment.setArguments(args);
                    fragmentTransaction = true;
                    GlobalValues.getInstancia().setActualFragment(GlobalValues.getInstancia().DATOS_USER);
                    break;

                case R.id.nav_configuracion:
                    try{
                        fragment = new ConfigFragment();
                        fragmentTransaction = true;
                        GlobalValues.getInstancia().setActualFragment(GlobalValues.getInstancia().CONFIGURACION);
                    }catch(Exception e){
                        Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.nav_logout:
                    GlobalValues.getInstancia().setUsuariologueado(null);
                    // Borrar parametro de Base de datos
                    IniciarApp ia = new IniciarApp(getBaseContext());
                    ia.logout();
                    Intent ir = new Intent(this, RegisterActivity.class);
                    startActivity(ir);
                    break;
                case  R.id.nav_home:
                    try{
                        fragment = new HomeFragment();
                        fragmentTransaction = true;
                        GlobalValues.getInstancia().setActualFragment(GlobalValues.getInstancia().HOME);
                    }catch(Exception e){
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            if (fragmentTransaction) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main, fragment).addToBackStack(null)
                        .commit();

            }
            drawer.closeDrawers();
        }catch(Exception e){
            Toast.makeText(this,"MainActivity:"+e.getMessage(), Toast.LENGTH_LONG);
            Log.println(Log.ERROR,"MainActivity:",e.getMessage());
        }
        return true;
    }





        @Override
        public void onFragmentInteraction(Uri uri) {
            Toast.makeText(this,"Mensaje: " + uri.getFragment().toString(),Toast.LENGTH_LONG).show();
        }




}
