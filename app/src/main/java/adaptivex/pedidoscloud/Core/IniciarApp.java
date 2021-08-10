package adaptivex.pedidoscloud.Core;

/**
 * Created by Ezequiel on 05/03/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Entity.LoginResult;
import adaptivex.pedidoscloud.Entity.UserProfileEntity;
import adaptivex.pedidoscloud.MainActivity;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Repositories.UserRepository;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.CategoriaDataBaseHelper;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.ClienteDataBaseHelper;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.EstadoDataBaseHelper;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.HorarioDataBaseHelper;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.MarcaDataBaseHelper;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.UnidadmedidaDataBaseHelper;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.UserProfileDataBaseHelper;
import adaptivex.pedidoscloud.Entity.ParameterEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.ParameterDataBaseHelper;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.PedidoDataBaseHelper;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.PedidodetalleDataBaseHelper;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.ProductoDataBaseHelper;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.PromoDataBaseHelper;
import adaptivex.pedidoscloud.Entity.UserEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.UserDataBaseHelper;
import adaptivex.pedidoscloud.Servicios.FactoryServices;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperParameters;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperPromos;

import static java.lang.Thread.sleep;

public  class IniciarApp {
    private Context context;


    public IniciarApp(Context c) {
        //leer valor de parametro
        setContext(c);
        iniciarFactories();
        checkInstalation();
    }


    public boolean createDataBase() {
        try {

            SQLiteDatabase db;
            //Tablas
            ClienteDataBaseHelper dba = new ClienteDataBaseHelper(this.getContext());
            db = dba.getWritableDatabase();
            db.execSQL(dba.DROP_TABLE);
            db.execSQL(dba.CREATE_TABLE);
            db.close();

            ProductoDataBaseHelper dbp = new ProductoDataBaseHelper(this.getContext());
            db = dbp.getWritableDatabase();
            db.execSQL(dbp.DROP_TABLE);
            db.execSQL(dbp.CREATE_TABLE);
            db.close();

            MarcaDataBaseHelper m = new MarcaDataBaseHelper(getContext());
            db = m.getWritableDatabase();
            db.execSQL(m.DROP_TABLE);
            db.execSQL(m.CREATE_TABLE);
            db.close();


            PedidoDataBaseHelper pe = new PedidoDataBaseHelper(getContext());
            db = pe.getWritableDatabase();
            db.execSQL(pe.DROP_TABLE);
            db.execSQL(pe.CREATE_TABLE);
            db.close();


            PedidodetalleDataBaseHelper ped = new PedidodetalleDataBaseHelper(getContext());
            db = ped.getWritableDatabase();
            db.execSQL(ped.DROP_TABLE);
            db.execSQL(ped.CREATE_TABLE);
            db.close();


            ParameterDataBaseHelper par = new ParameterDataBaseHelper(getContext());
            db = par.getWritableDatabase();
            db.execSQL(par.DROP_TABLE);
            db.execSQL(par.CREATE_TABLE);
            db.close();


            PromoDataBaseHelper promo = new PromoDataBaseHelper(getContext());
            db = promo.getWritableDatabase();
            db.execSQL(promo.DROP_TABLE);
            db.execSQL(promo.CREATE_TABLE);
            db.close();

            UserDataBaseHelper userdb = new UserDataBaseHelper(getContext());
            db = userdb.getWritableDatabase();
            db.execSQL(userdb.DROP_TABLE);
            db.execSQL(userdb.CREATE_TABLE);
            db.close();

            HorarioDataBaseHelper ho = new HorarioDataBaseHelper(getContext());
            db = ho.getWritableDatabase();
            db.execSQL(ho.DROP_TABLE);
            db.execSQL(ho.CREATE_TABLE);
            db.close();

            CategoriaDataBaseHelper cat = new CategoriaDataBaseHelper(getContext());
            db = cat.getWritableDatabase();
            db.execSQL(cat.DROP_TABLE);
            db.execSQL(cat.CREATE_TABLE);
            db.close();

            EstadoDataBaseHelper est = new EstadoDataBaseHelper(getContext());
            db = est.getWritableDatabase();
            db.execSQL(est.DROP_TABLE);
            db.execSQL(est.CREATE_TABLE);
            db.close();

            UnidadmedidaDataBaseHelper um = new UnidadmedidaDataBaseHelper(getContext());
            db = um.getWritableDatabase();
            db.execSQL(um.DROP_TABLE);
            db.execSQL(um.CREATE_TABLE);
            db.close();


            UserProfileDataBaseHelper up = new UserProfileDataBaseHelper(getContext());
            db = up.getWritableDatabase();
            db.execSQL(up.DROP_TABLE);
            db.execSQL(up.CREATE_TABLE);
            db.close();

            return true;
        } catch (Exception e) {
            Log.println(Log.DEBUG, "IniciarrApp: ", e.getMessage());
            Toast.makeText(context, "IniciarApp:" + e.getMessage(), Toast.LENGTH_LONG);
            return false;
        }

    }

    public boolean loginRemember(UserEntity user) {
        try {
            /* Lee parametros, y los setea con el valor del usuario. Si no existen, los crea */
            boolean respuesta = true;
            ParameterEntity p2;
            p2 = FactoryRepositories
                    .getInstancia()
                    .getParameterRepository()
                    .abrir()
                    .findByNombre(GlobalValues.getInstancia().PARAM_REMEMBERME);
            p2.setValor_texto("N");

            FactoryRepositories
                    .getInstancia()
                    .getParameterRepository()
                    .abrir()
                    .modificar(p2);

            return respuesta;
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }

    }


    public void logout() {
        try {
            UserRepository uc = new UserRepository(this.getContext());
            UserEntity u = uc.abrir().findUser(GlobalValues.getInstancia().getUsuariologueado().getId());
            if (u != null) {
                u.setLogued("N");
                uc.abrir().editDB(u);
            }
        } catch (Exception e) {
            Log.d("IniciarAPP", e.getMessage());
        }
    }


    public boolean downloadDatabase() {
        try {
            FactoryServices.getInstancia().getMarcaServices().getMarcas();
            FactoryServices.getInstancia().getCategoriaServices().getCategorias();
            FactoryServices.getInstancia().getProductoServices().getProductos();
            FactoryServices.getInstancia().getUserProfileServices().getUserProfiles();
            FactoryServices.getInstancia().getParameterServices().getParameters();
            FactoryServices.getInstancia().getPromoServices().getPromos();
            FactoryServices.getInstancia().getHorarioServices().getHorarios();
            FactoryServices.getInstancia().getEstadoServices().getEstados();
            FactoryServices.getInstancia().getUnidadmedidaServices().getUnidadmedidas();
            FactoryServices.getInstancia().getPedidoServices().getPedidos();

            createParameters();
            //INSTALADO
            ParameterEntity p = FactoryRepositories
                    .getInstancia()
                    .getParameterRepository()
                    .abrir()
                    .findByNombre(GlobalValues.getInstancia().PARAM_INSTALLED);
            p.setValor_texto("Y");
            FactoryRepositories
                    .getInstancia()
                    .getParameterRepository()
                    .abrir()
                    .modificar(p);

            return true;
        } catch (Exception e) {
            Log.e("IniciarAPP", e.getMessage());
            return false;
        }
    }





    public void iniciarFactories() {
        try {
            FactoryRepositories.getInstancia(getContext());
            FactoryServices.getInstancia();
        } catch (Exception e) {
            Log.e("IniciarApp:", e.getMessage());
        }

    }


    public void checkInstalation() {
        //Leer Archivo de sistema el parametro INSTALLED
        try {
            ParameterEntity p = FactoryRepositories
                    .getInstancia()
                    .getParameterRepository()
                    .abrir()
                    .findByNombre(GlobalValues.getInstancia().PARAM_INSTALLED);

            if (p == null) {
                //crear base de datos
                createDataBase();

            }

        } catch (Exception e) {
            Log.e("IniciarApp", e.getMessage());
        }
    }

    public void createParameters()
    {
        try{
            ParameterEntity p = new ParameterEntity();
            p.setNombre(GlobalValues.getInstancia().PARAM_INSTALLED);
            p.setValor_texto("N");

            FactoryRepositories
                    .getInstancia()
                    .getParameterRepository()
                    .abrir()
                    .agregar(p);


            ParameterEntity p2 = new ParameterEntity();
            p2.setNombre(GlobalValues.getInstancia().PARAM_REMEMBERME);
            p2.setValor_texto("N");
            FactoryRepositories
                    .getInstancia()
                    .getParameterRepository()
                    .abrir()
                    .agregar(p2);


            ParameterEntity token = new ParameterEntity();
            token.setNombre(GlobalValues.getInstancia().PARAM_TOKEN);
            //token.setValor_texto(GlobalValues.getInstancia().getAuthorization());
            FactoryRepositories
                    .getInstancia()
                    .getParameterRepository()
                    .abrir()
                    .agregar(token);


        }catch (Exception e){
            Log.e("IniciarApp", e.getMessage());
        }

    }

    public void loadUserLoginRemember(){
        try{
            ParameterEntity ptoken =FactoryRepositories
                    .getInstancia()
                    .getParameterRepository()
                    .abrir()
                    .findByNombre(GlobalValues.PARAM_TOKEN);
            LoginResult lr = new LoginResult();
            lr.setToken(ptoken.getValor_texto());
            GlobalValues.getInstancia().setToken(lr);

            UserProfileEntity current =
                    FactoryRepositories
                            .getInstancia()
                            .getUserProfileRepository()
                            .abrir()
                            .findByCurrentUser();
            GlobalValues.getInstancia().setUsuariologueado(current);

        }catch(Exception e){
            Log.e("Iniciarapp loadlogin",e.getMessage());
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}