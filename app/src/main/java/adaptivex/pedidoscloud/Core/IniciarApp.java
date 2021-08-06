package adaptivex.pedidoscloud.Core;

/**
 * Created by Ezequiel on 05/03/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.ParameterRepository;
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
import adaptivex.pedidoscloud.Entity.User;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.UserDataBaseHelper;
import adaptivex.pedidoscloud.Servicios.FactoryServices;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperHorarios;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperParameters;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperProductos;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperPromos;

import static java.lang.Thread.sleep;

public  class IniciarApp  {
    private Context context;


    public IniciarApp(Context c ){
        //leer valor de parametro
        setContext(c);
        iniciarFactories();

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
            UserRepository uc = new UserRepository(this.getContext());
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
            UserRepository uc = new UserRepository(this.getContext());
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
            UserRepository uc = new UserRepository(this.getContext());
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
            return true;
        }catch (Exception e ){
          Log.e("IniciarAPP", e.getMessage());
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

            iniciarBD();
            downloadDatabase();
            createUserSystem();
            setInstalledDatabase();
            return true;
        }catch (Exception e){
            Log.d("IniciarApp", e.getMessage());
            return false;
        }
    }

    public void iniciarFactories(){
        try{
            FactoryRepositories.getInstancia(getContext());
            FactoryServices.getInstancia();
        }catch (Exception e ){
            Log.e("IniciarApp:", e.getMessage());
        }

    }

    public boolean createUserSystem() {
        try {
            User u = new User();
            u.setId(GlobalValues.getINSTANCIA().ID_ANDROID);
            UserRepository uc = new UserRepository(this.getContext());
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
            ParameterRepository pc = new ParameterRepository(getContext());
            ParameterEntity p = new ParameterEntity();
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
            ParameterRepository pc = new ParameterRepository(getContext());
            ParameterEntity p = new ParameterEntity();
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