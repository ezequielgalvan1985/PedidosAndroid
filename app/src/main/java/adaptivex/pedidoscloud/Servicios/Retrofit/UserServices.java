package adaptivex.pedidoscloud.Servicios.Retrofit;

/**
 * Created by Ezequiel on 01/08/2021.
 * Proposito:
 * descargar todos los registros de categoria y gudardarlos en la base local
 */

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Repositories.UserRepository;
import adaptivex.pedidoscloud.Entity.LoginData;
import adaptivex.pedidoscloud.Entity.LoginResult;
import adaptivex.pedidoscloud.Entity.UserEntity;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IUserRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class UserServices {
    public List<UserEntity> usersList;
    public UserEntity user;

    private Context ctx;
    private HashMap<String, String> registro;
    private UserRepository usersCtr;

    public UserServices() {
    }

    public UserServices(Context pCtx){
        this.setCtx(pCtx);
        this.usersCtr = new UserRepository(this.getCtx());
    }

    private void setCtx(Context pCtx) {
    }

    public Context getCtx() {
        return ctx;
    }



    public void postLogin(String email, String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configurador.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IUserRetrofit service = retrofit.create(IUserRetrofit.class);

        Call<LoginResult> call=service.getLoginToken(new LoginData(email,password));
        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if(!response.isSuccessful()){
                    Log.println(Log.INFO,"user: Error",String.valueOf(response.code()));
                    return;
                }

                Log.println(Log.INFO,"Login: ","Login Exitoso");
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Log.println(Log.ERROR,"Codigo: ",t.getMessage());
            }
        });



    }
/*
    private void registerService(){
        try{
            WebRequest webreq = new WebRequest();

            // Setear datos de usuario loguin para hacer el post
            registro = new HashMap<String, String>();

            registro.put("username", getUser().getUsername().toString());
            registro.put("password", getUser().getPassword().toString());
            registro.put("email", getUser().getEmail().toString());

            registro.put("localidad", getUser().getLocalidad().toString());
            registro.put("calle", getUser().getCalle().toString());
            registro.put("nro", getUser().getNro().toString());
            registro.put("piso", getUser().getPiso().toString());
            registro.put("contacto", getUser().getContacto().toString());
            registro.put("telefono", getUser().getTelefono().toString());

            //Enviar Post
            jsonStr = webreq.makeWebServiceCall(Configurador.urlPostRegister, WebRequest.POST, this.registro);

        }catch (Exception e){
            setRespuesta(RETURN_ERROR);
            Log.e("Error:",e.getMessage());
        }
    }

 */
}
