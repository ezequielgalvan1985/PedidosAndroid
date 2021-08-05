package adaptivex.pedidoscloud.Core;

/**
 * Created by Ezequiel on 05/03/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ParameterController;
import adaptivex.pedidoscloud.Controller.UserController;
import adaptivex.pedidoscloud.Model.DatabaseHelper.CategoriaDataBaseHelper;
import adaptivex.pedidoscloud.Model.DatabaseHelper.ClienteDataBaseHelper;
import adaptivex.pedidoscloud.Model.DatabaseHelper.DatabaseHelper;
import adaptivex.pedidoscloud.Model.DatabaseHelper.EstadoDataBaseHelper;
import adaptivex.pedidoscloud.Model.DatabaseHelper.HorarioDataBaseHelper;
import adaptivex.pedidoscloud.Model.DatabaseHelper.MarcaDataBaseHelper;
import adaptivex.pedidoscloud.Model.DatabaseHelper.UnidadmedidaDataBaseHelper;
import adaptivex.pedidoscloud.Model.DatabaseHelper.UserProfileDataBaseHelper;
import adaptivex.pedidoscloud.Model.IModelMethods;
import adaptivex.pedidoscloud.Model.Parameter;
import adaptivex.pedidoscloud.Model.DatabaseHelper.ParameterDataBaseHelper;
import adaptivex.pedidoscloud.Model.DatabaseHelper.PedidoDataBaseHelper;
import adaptivex.pedidoscloud.Model.DatabaseHelper.PedidodetalleDataBaseHelper;
import adaptivex.pedidoscloud.Model.DatabaseHelper.ProductoDataBaseHelper;
import adaptivex.pedidoscloud.Model.DatabaseHelper.PromoDataBaseHelper;
import adaptivex.pedidoscloud.Model.Unidadmedida;
import adaptivex.pedidoscloud.Model.User;
import adaptivex.pedidoscloud.Model.DatabaseHelper.UserDataBaseHelper;
import adaptivex.pedidoscloud.Model.UserProfile;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperHorarios;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperParameters;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperProductos;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperPromos;
import adaptivex.pedidoscloud.Servicios.Retrofit.CategoriaServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.EstadoServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.HorarioServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.MarcaServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.ParameterServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.ProductoServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.MarcaServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.PromoServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.UnidadmedidaServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.UserServices;

import static java.lang.Thread.sleep;

import java.util.List;

public  class IniciarApp  {
    private Context context;


    public IniciarApp(Context c ){
        //leer valor de parametro
        setContext(c);
    }


    public  boolean  iniciarBD(){
        try{

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

            //HorarioDataBaseHelper   tabla_horario   = new HorarioDataBaseHelper(getContext());
            //CategoriaDataBaseHelper tabla_categoria = new CategoriaDataBaseHelper(getContext());
            //EstadoDataBaseHelper    tabla_estado    = new EstadoDataBaseHelper(getContext());

            //List<DatabaseHelper> listaTablas = null;

            //listaTablas.add(tabla_estado);
            //listaTablas.add(tabla_categoria);
            //listaTablas.add(tabla_horario);

            //crearTablas(listaTablas);

            return true;
        }catch(Exception e ){
            Log.println(Log.DEBUG,"IniciarrApp: ", e.getMessage());
            Toast.makeText(context,"IniciarApp:"+e.getMessage(), Toast.LENGTH_LONG);
            return false;
        }

    }

    /* no se usa, desarrollo por hacer
    public boolean crearTablas(List<DatabaseHelper> listaTablas){
        try{
            SQLiteDatabase db;
            for(DatabaseHelper tabla: listaTablas){

                Log.println(Log.INFO,"Tabla: ", tabla.getNameTable());
                db = tabla.getWritableDatabase();
                db.execSQL(tabla.getDropTable());
                db.execSQL(tabla.getCreateTable());
                db.close();
            }
            return true;
        }catch (Exception e){
            Log.println(Log.DEBUG,"Error al Crear Tablas: ", e.getMessage());
            return false;
        }
    }
*/
    public boolean loginRemember(User user){
        try{
            /* Lee parametros, y los setea con el valor del usuario. Si no existen, los crea */
            boolean respuesta = true;
            UserController uc = new UserController(this.getContext());
            User u = uc.abrir().findUser(user.getId());

            user.setLogued("Y");
            if (u == null){
                uc.abrir().addDB(user);
            }else{
                uc.abrir().editDB(user);
            }

            return respuesta;
        }catch(Exception e){
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }

    }


    public boolean isLoginRemember(){
        try{
            boolean respuesta = false;
            UserController uc = new UserController(this.getContext());
            User u = uc.abrir().findUser(1);

            if (u!=null){
                if(u.getLogued().equals("Y")){
                    respuesta = true;
                   // GlobalValues.getINSTANCIA().setUserlogued(u);
                };
            }
            return respuesta;
        }catch(Exception e){
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void logout(){
        try{
            UserController uc = new UserController(this.getContext());
            User u = uc.abrir().findUser(GlobalValues.getINSTANCIA().getUsuariologueado().getId());
            if (u != null){
                u.setLogued("N");
                uc.abrir().editDB(u);
            }
        }catch(Exception e){
            Log.d("IniciarAPP", e.getMessage());
        }
    }




    public boolean downloadDatabase(){
        try {
            MarcaServices m = new MarcaServices(getContext());
            m.getMarcas();

            CategoriaServices cat = new CategoriaServices(getContext());
            cat.getCategorias();

            ProductoServices ps = new ProductoServices(getContext());
            ps.getProductos();

            ParameterServices par = new ParameterServices(getContext());
            par.getParameters();

            //PromoServices ph = new PromoServices(getContext());
            //ph.getPromos();

            HorarioServices hs = new HorarioServices(getContext());
            hs.getHorarios();

            EstadoServices es = new EstadoServices(getContext());
            es.getEstados();

            UnidadmedidaServices um = new UnidadmedidaServices(getContext());
            um.getUnidadmedidas();

            UserProfile datos = new UserProfile((getContext());
            datos.getUser();

            /*
            * se reemplaza por implementacion de Retrofit2
            *
            HelperMarcas m = new HelperMarcas(getContext());
            m.execute();

            HelperCategorias ca = new HelperCategorias(getContext());
            ca.execute();

            HelperClientes c = new HelperClientes(getContext());
            c.execute();

            HelperProductos p = new HelperProductos(getContext());
            p.execute();

            HelperParameters hp = new HelperParameters(getContext());
            hp.setCURRENT_OPTION(HelperParameters.OPTION_ALL);
            hp.execute();

            HelperPromos ph = new HelperPromos(getContext());
            ph.execute();

            HelperHorarios hh = new HelperHorarios(getContext());
            hh.setOpcion(HelperHorarios.OPTION_FIND_ALL);
            hh.execute();

            return true;
            */
            return true;
        }catch (Exception e ){
          Log.d("IniciarAPP", e.getMessage());
            Toast.makeText(context, "Error " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void refreshHorariosOpenFromServer(){
        try{
            HelperHorarios helper = new HelperHorarios(getContext());
            helper.setOpcion(HelperHorarios.OPTION_FIND_ALL);
            helper.execute();

        }catch (Exception e ){
            Log.d("refreshDataFromServer", e.getMessage());
        }
    }

    public void refreshPromosFromServer(){
        try{
            HelperPromos ph = new HelperPromos(getContext());
            ph.execute();
        }catch (Exception e ){
            Log.d("refreshDataFromServer", e.getMessage());
        }
    }

    public void refreshPriceFromServer(){
        try{

            HelperParameters hp = new HelperParameters(getContext());
            hp.setCURRENT_OPTION(HelperParameters.OPTION_ALL);
            hp.execute();



        }catch (Exception e ){
            Log.d("refreshDataFromServer", e.getMessage());
        }
    }

    public void refreshHeladosDisponiblesFromServer(){
        try{

            HelperProductos hp = new HelperProductos(getContext());
            hp.setOPTION(HelperProductos.OPTION_UPDATE_ENABLED);
            hp.execute();

        }catch (Exception e ){
            Log.d("refreshDataFromServer", e.getMessage());
        }
    }

    public boolean instalarApp(){
        try {
            this.iniciarBD();
            this.downloadDatabase();
            this.createUserSystem();
            this.setInstalledDatabase();
            return true;
        }catch (Exception e){
            Log.d("IniciarApp", e.getMessage());
            return false;
        }
    }

    public boolean createUserSystem() {
        try {
            User u = new User();
            u.setId(GlobalValues.getINSTANCIA().ID_ANDROID);
            UserController uc = new UserController(this.getContext());
            uc.abrir().addDB(u);
            return true;
        } catch (Exception e) {
            Log.d("IniciarApp", e.getMessage());
            return false;
        }
    }


    public boolean isInstalled(){
        //Leer Archivo de sistema el parametro INSTALLED
        try {
            boolean respuesta = false;
            ParameterController pc = new ParameterController(getContext());
            Parameter p = new Parameter();
            p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_INSTALLED);
            if (p != null) {
                if (p.getValor_texto().equals("Y")) {
                    respuesta = true;
                }
            }
            return respuesta;
        }catch(Exception e ){
            Log.d("IniciarApp", e.getMessage());
            return false;
        }
    }

    public boolean setInstalledDatabase(){
        //Leer Archivo de sistema el parametro INSTALLED
        try {
            boolean respuesta = false;
            ParameterController pc = new ParameterController(getContext());
            Parameter p = new Parameter();
            p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_INSTALLED);
            if (p != null) {
                p.setValor_texto("Y");
                pc.abrir().modificar(p);
                respuesta = true;
            }
            return respuesta;
        }catch(Exception e ){
            Log.d("IniciarApp", e.getMessage());
            return false;
        }
    }




    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}