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
import adaptivex.pedidoscloud.Controller.UserProfileController;
import adaptivex.pedidoscloud.Model.UserProfile;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IUserProfileRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class UserProfileServices {
    public List<UserProfile> userprofilesList;
    public UserProfile userprofile;

    private Context ctx;
    private HashMap<String, String> registro;

    public UserProfileController getUserProfilesCtr() {
        return userprofilesCtr;
    }

    public void setUserProfilesCtr(UserProfileController userprofilesCtr) {
        this.userprofilesCtr = userprofilesCtr;
    }

    private UserProfileController userprofilesCtr;

    public UserProfileServices() {
    }

    public UserProfileServices(Context pCtx){
        this.setCtx(pCtx);
        this.setUserProfilesCtr(new UserProfileController(pCtx));
    }

    private void setCtx(Context pCtx) {
    }

    public Context getCtx() {
        return ctx;
    }

    public List<UserProfile> getUserProfiles(){
        try{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Configurador.urlBase)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            IUserProfileRetrofit service = retrofit.create(IUserProfileRetrofit.class);
            //UserSessionLogin.getINSTANCIA().getUser().setToken("5d3b54d7422aa18506d26656bd93a0db5e4fcc6c");
            Call<List<UserProfile>> call = service.getUserProfiles("Basic YWRtaW4yOjEyMzQ=");
            call.enqueue(new Callback<List<UserProfile>>() {
                @Override
                public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                    if(!response.isSuccessful()){
                        Log.println(Log.INFO,"UserProfile: Error",String.valueOf(response.code()));
                        return;
                    }
                    getUserProfilesCtr().abrir().limpiar();
                    userprofilesList = response.body();
                    String content = "";
                    for (UserProfile userprofile: userprofilesList){
                        //Recorrer Lista
                        getUserProfilesCtr().abrir().agregar(userprofile);
                        content = userprofile.getId() + " " +userprofile.getNombre() + " " + userprofile.getUser().getUsername();
                        Log.println(Log.INFO,"UserProfile: ",content);
                    }
                }

                @Override
                public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                    Log.println(Log.ERROR,"Codigo: ",t.getMessage());
                }
            });
            return userprofilesList;
        }catch (Exception e){
            Log.println(Log.ERROR,"UserProfile services",e.getMessage());
            return null;
        }
    }
}
