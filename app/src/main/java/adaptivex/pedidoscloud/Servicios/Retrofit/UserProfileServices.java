package adaptivex.pedidoscloud.Servicios.Retrofit;

/**
 * Created by Ezequiel on 01/08/2021.
 * Proposito:
 * descargar todos los registros de categoria y gudardarlos en la base local
 */

import android.util.Log;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Entity.UserProfileEntity;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IUserProfileRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class UserProfileServices {
    public List<UserProfileEntity> userprofilesList;



    public UserProfileServices() {
    }


    public List<UserProfileEntity> getUserProfiles(){
        try{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Configurador.urlBase)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            IUserProfileRetrofit service = retrofit.create(IUserProfileRetrofit.class);
            Call<List<UserProfileEntity>> call = service.getUserProfiles(GlobalValues.getInstancia().getAuthorization());
            call.enqueue(new Callback<List<UserProfileEntity>>() {
                @Override
                public void onResponse(Call<List<UserProfileEntity>> call, Response<List<UserProfileEntity>> response) {
                    try{
                        if(!response.isSuccessful()){
                            Log.println(Log.INFO,"UserProfile: Error",String.valueOf(response.code()));
                            return;
                        }
                        FactoryRepositories.getInstancia().getUserProfileRepository().abrir().limpiar();
                        userprofilesList = response.body();
                        String content = "";
                        for (UserProfileEntity userprofile: userprofilesList){
                            //Recorrer Lista
                            FactoryRepositories.getInstancia().getUserProfileRepository().abrir().agregar(userprofile);
                            content = userprofile.getId() + " " +userprofile.getNombre() + " " + userprofile.getUser().getUsername();
                            Log.println(Log.INFO,"UserProfile: ",content);
                            GlobalValues.getInstancia().setUsuariologueado(userprofile);
                        }
                    }catch (Exception e){
                        Log.println(Log.ERROR,"UserProfile: ",e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<List<UserProfileEntity>> call, Throwable t) {
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
