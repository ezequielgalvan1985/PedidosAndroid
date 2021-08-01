package adaptivex.pedidoscloud.Servicios.Retrofit;

import java.util.List;

import adaptivex.pedidoscloud.Model.Marca;

public interface CallBackListener {
    void onCallBack1();// pass any parameter in your onCallBack which you want to return

    void onCallBack2(List<Marca> listMarcas);
}