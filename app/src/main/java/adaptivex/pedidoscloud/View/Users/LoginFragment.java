package adaptivex.pedidoscloud.View.Users;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Core.IniciarApp;
import adaptivex.pedidoscloud.Entity.ParameterEntity;
import adaptivex.pedidoscloud.MainActivity;
import adaptivex.pedidoscloud.Entity.LoginData;
import adaptivex.pedidoscloud.Entity.LoginResult;
import adaptivex.pedidoscloud.Entity.MarcaEntity;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IUserRetrofit;
import adaptivex.pedidoscloud.Servicios.Retrofit.UserServices;
import retrofit2.Call;
import retrofit2.Callback;

import java.util.List;

import adaptivex.pedidoscloud.Entity.UserEntity;
import adaptivex.pedidoscloud.R;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    private EditText login_password, login_email;
    private TextView loginTxtCartel;
    private Button btn_login, btn_register;

    private UserEntity usertmp;

    private List<MarcaEntity>marcasList;

    public LoginFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        //Iniciar los campos
        login_email    = (EditText) v.findViewById(R.id.login_email);
        login_password = (EditText) v.findViewById(R.id.login_password);
        btn_login      = (Button)   v.findViewById(R.id.login_btn_login);
        btn_register   = (Button)   v.findViewById(R.id.login_btn_register);
        loginTxtCartel = (TextView) v.findViewById(R.id.loginTxtCartel);

        //Asignar Funcionalidad
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    private void clickLogin(){

        if (validateLogin()){
            //se reemplaza la funcionalidad utilizando RETROFIT usando las clases Services

            UserServices us = new UserServices();
            us.postLogin(usertmp.getUsername(), usertmp.getPassword());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Configurador.urlBase)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            IUserRetrofit service = retrofit.create(IUserRetrofit.class);
            Call<LoginResult> call=service.getLoginToken(new LoginData(usertmp.getUsername(),usertmp.getPassword()));
            call.enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                    if(!response.isSuccessful()){
                        Log.println(Log.INFO,"user: Error",String.valueOf(response.code()));
                        return;
                    }
                    Log.println(Log.INFO,"Login: ","Login Exitoso");
                    //se recibe el token
                    //guardamos el token
                    LoginResult token = response.body();
                    GlobalValues.getInstancia().setToken(token);



                    //graba bandera instalado, luego de descargar
                    ParameterEntity p = FactoryRepositories
                            .getInstancia()
                            .getParameterRepository()
                            .abrir()
                            .findByNombre(GlobalValues.getInstancia().PARAM_INSTALLED);
                    if (p==null){
                        IniciarApp ia = new IniciarApp(getContext());
                        ia.downloadDatabase();
                    }
                    //RECORDARME
                    //graba bandera rememberme
                    ParameterEntity prememberme =FactoryRepositories
                            .getInstancia()
                            .getParameterRepository()
                            .abrir()
                            .findByNombre(GlobalValues.PARAM_REMEMBERME);
                    prememberme.setValor_texto("Y");
                    FactoryRepositories.getInstancia().getParameterRepository().abrir().modificar(prememberme);

                    //graba token en parametro para recordarlo
                    ParameterEntity ptoken =FactoryRepositories
                            .getInstancia()
                            .getParameterRepository()
                            .abrir()
                            .findByNombre(GlobalValues.PARAM_TOKEN);
                    ptoken.setValor_texto(token.getToken());
                    FactoryRepositories.getInstancia().getParameterRepository().abrir().modificar(ptoken);


                    Log.println(Log.INFO,"Login: ",GlobalValues.getInstancia().getAuthorization()  );
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    getActivity()
                            .finish();
                }

                @Override
                public void onFailure(Call<LoginResult> call, Throwable t) {
                    Log.println(Log.ERROR,"Codigo: ",t.getMessage());
                }
            });
        }



    }

    private boolean validateLogin(){
        try {
            boolean validate = true;
            if(login_email.getText().length() < 0) {
                Toast.makeText(getContext(), "Usuario o Email es Obligatorio: ", Toast.LENGTH_LONG).show();
            }
            if(login_password.getText().length()<4){
                Toast.makeText(getContext(), "Contraseña debe tener 4 caracteres minimo: ", Toast.LENGTH_LONG).show();
            }
            usertmp = new UserEntity();
            usertmp.setUsername(login_email.getText().toString());
            usertmp.setPassword(login_password.getText().toString());

            return validate;
        }catch (Exception e){
            Toast.makeText(getContext(),"Error: " + e.getMessage() ,Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void clickRegister(){
        RegisterFragment fragment      = new RegisterFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.content_main_register, fragment).addToBackStack(null)
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_login:
                clickLogin();
                break;
            case R.id.login_btn_register:
                clickRegister();
                break;

            default:
                break;
        }
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }





}
