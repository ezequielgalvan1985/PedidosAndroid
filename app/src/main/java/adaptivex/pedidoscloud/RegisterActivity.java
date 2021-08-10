package adaptivex.pedidoscloud;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Entity.ParameterEntity;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Core.IniciarApp;

import adaptivex.pedidoscloud.View.Users.HomeLoginFragment;
import adaptivex.pedidoscloud.View.Users.LoginFragment;
import adaptivex.pedidoscloud.View.Users.RegisterFragment;

public class RegisterActivity
        extends AppCompatActivity
        implements
        HomeLoginFragment.OnFragmentInteractionListener,
        LoginFragment.OnFragmentInteractionListener,
        RegisterFragment.OnFragmentInteractionListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        try{
            preLoadActivity();

        }catch(Exception e){
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    private void preLoadActivity(){
        //Si no esta instalada, se instala, y luego se pide registrarse
        IniciarApp ia = new IniciarApp(RegisterActivity.this);

        //Esta Instalada, pregunta Esta recordado el usuario, entonces se inicia la app directamente,
        ParameterEntity rememberme = FactoryRepositories
                .getInstancia()
                .getParameterRepository()
                .abrir()
                .findByNombre(GlobalValues.PARAM_REMEMBERME);

        if(rememberme ==null)
        {
            openLoginFragment();
        } else if(rememberme.getValor_texto().equals("N")) {
            openLoginFragment();
        }else{
            //cargar datos del usuario
            ia.loadUserLoginRemember();

            Intent i = new Intent(this.getBaseContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }


    private void openLoginFragment(){
        try{
            Fragment fragment = new HomeLoginFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main_register, fragment).addToBackStack(Constants.FRAGMENT_HOME_LOGIN)
                    .commit();

        }catch(Exception e){
            Toast.makeText(getBaseContext(),"Error: ",Toast.LENGTH_LONG).show();
        }
    }

    private void openRegisterFragment(){
        try{
            Fragment fragment = new RegisterFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main_register, fragment).addToBackStack(Constants.FRAGMENT_HOME_LOGIN)
                    .commit();

        }catch(Exception e){
            Toast.makeText(getBaseContext(),"Error: ",Toast.LENGTH_LONG).show();
        }

    }






    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}

