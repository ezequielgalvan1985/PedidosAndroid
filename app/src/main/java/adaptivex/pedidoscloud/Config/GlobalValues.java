package adaptivex.pedidoscloud.Config;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import java.util.List;

import adaptivex.pedidoscloud.Entity.UserProfileEntity;

import adaptivex.pedidoscloud.Entity.ItemProducto;
import adaptivex.pedidoscloud.Entity.LoginResult;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
import adaptivex.pedidoscloud.Entity.PoteEntity;

/**
 * Created by ezequiel on 26/06/2016.
 * VARIABLES DE APLICACION, INTERNA ENTRE ACTIVIDADES, VA
 */
public class GlobalValues {

    private static GlobalValues INSTANCIA;
    public  static final String ACTION_GET_STOCK_PRECIOS = "1";


    private FragmentManager fragmentManager;

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public Integer CURRENT_FRAGMENT_NUEVO_PEDIDO;
    public String PRECIO_CUCURUCHO_MONEY;
    private LoginResult token;


    private UserProfileEntity usuariologueado;

    public UserProfileEntity getUsuariologueado() {
        return usuariologueado;
    }

    public void setUsuariologueado(UserProfileEntity usuariologueado) {
        this.usuariologueado = usuariologueado;
    }


    public LoginResult getToken() {
        return token;
    }

    public void setToken(LoginResult token) {
        this.token = token;
    }
    public String getAuthorization (){
        return "Token " + this.getToken().getToken();
    }




    private int ActualFragment;
    public static final int LISTADOCATEGORIAS = 4;
    public static final int LISTADOPEDIDOS = 5;
    public static final int HOME = 10;
    public static final int CONFIGURACION = 11;
    public static final int DATOS_USER = 12;

    //FRAGMENTS Heladerias
    public static final int NP_CARGAR_DIRECCION         = 13;



    //ERRORES
    public static final int RETURN_OK = 1;
    public static final int RETURN_ERROR = 20;

    //Estados de un pedido
    public static final int consPedidoEstadoNuevo = 0;
    public static final int consPedidoEstadoEnviado = 1;
    public static final int consPedidoEstadoPreparado = 2;
    public static final int consPedidoEstadoEntregado = 3;
    public static final int consPedidoEstadoTodos = 99;

    //Estados de un pedido
    public static final int ESTADO_NUEVO = 0;
    public static final int ESTADO_ENVIADO = 1;
    public static final int ESTADO_PREPARADO = 2;
    public static final int ESTADO_ENTREGADO = 3;
    public static final int ESTADO_TODOS = 99;

    private int PEDIDO_ACTION_VALUE;

    public static final int PEDIDO_ACTION_VIEW = 3;

    private int diaSelecionado;


    //DATOS DE USUARIO
    public static final String PARAM_TOKEN     = "PARAM_TOKEN";

    public static final String PARAM_REMEMBERME     = "PARAM_REMEMBERME";
    public static final String PARAM_INSTALLED      = "PARAM_INSTALLED";
    public static final String PARAM_SERVICE_STOCK_PRECIOS_ACTIVATE = "PARAM_SERVICE_STOCK_PRECIOS_ACTIVATE";
    public static final String PARAM_SERVICE_STOCK_PRECIOS_WORKING  = "PARAM_SERVICE_STOCK_PRECIOS_WORKING";
    public static final String PARAM_DOWNLOAD_DATABASE              = "PARAM_DOWNLOAD_DATABASE";
    public static final String PARAM_SERVICE_ENVIO_PEDIDOS_ACTIVATE = "PARAM_SERVICE_ENVIO_PEDIDOS_ACTIVATE";
    public static final String PARAM_PRECIO_CUCURUCHO = "PARAM_PRECIO_CUCURUCHO";


    public static  String[] ESTADOS = {"NUEVO","EN PREPARACION","EN CAMINO","ENTREGADO"};

    //Variables globales para Generar Pedido
    private long        vgPedidoIdActual;
    private long        vgPedidoSeleccionado;
    private int         vgClienteSelecionado;
    private boolean     vgFlagMenuNuevoPedido;
    private int         ESTADO_ID_SELECCIONADO;
    public long         PEDIDO_ID_ACTUAL;
    public int          CLIENTE_ID_PEDIDO_ACTUAL;

    public boolean IS_MEMO;

    //Valores impuestos
    public static final Double consIva = 21.00;


    public  static GlobalValues getInstancia() {

        if (INSTANCIA==null) {

            INSTANCIA=new GlobalValues();
        }
        return INSTANCIA;
    }



    public int getActualFragment() {
        return ActualFragment;
    }

    public void setActualFragment(int gvActualFragment) {
        ActualFragment = gvActualFragment;
    }

    public long getVgPedidoIdActual() {
        return vgPedidoIdActual;
    }

    public boolean isUserAuthenticated(){
        if (getUsuariologueado()==null){
            return false;
        }else{
            return true;
        }

    }
    public void setVgPedidoIdActual(long vgPedidoIdActual) {
        this.vgPedidoIdActual = vgPedidoIdActual;
    }

    public int getVgClienteSelecionado() {
        return vgClienteSelecionado;
    }

    public void setVgClienteSelecionado(int vgClienteSelecionado) {
        this.vgClienteSelecionado = vgClienteSelecionado;
    }

    public boolean isVgFlagMenuNuevoPedido() {
        return vgFlagMenuNuevoPedido;
    }

    public void setVgFlagMenuNuevoPedido(boolean vgFlagMenuNuevoPedido) {
        this.vgFlagMenuNuevoPedido = vgFlagMenuNuevoPedido;
    }



    public long getVgPedidoSeleccionado() {
        return vgPedidoSeleccionado;
    }

    public void setVgPedidoSeleccionado(long vgPedidoSeleccionado) {
        this.vgPedidoSeleccionado = vgPedidoSeleccionado;
    }

    public int getESTADO_ID_SELECCIONADO() {
        return ESTADO_ID_SELECCIONADO;
    }

    public void setESTADO_ID_SELECCIONADO(int ESTADO_ID_SELECCIONADO) {
        this.ESTADO_ID_SELECCIONADO = ESTADO_ID_SELECCIONADO;
    }

    public int getPEDIDO_ACTION_VALUE() {
        return PEDIDO_ACTION_VALUE;
    }

    public void setPEDIDO_ACTION_VALUE(int PEDIDO_ACTION_VALUE) {
        this.PEDIDO_ACTION_VALUE = PEDIDO_ACTION_VALUE;
    }

    public int getDiaSelecionado() {
        return diaSelecionado;
    }

    public void setDiaSelecionado(int diaSelecionado) {
        this.diaSelecionado = diaSelecionado;
    }





    // Lista de los items que se seleccinaron en pantalla.
    // NOTA! cuando se edita un Pote, se debe cargar la ListaHeladosSeleecionados con los Items Seleccionados
    // Se pone aca la lista, porque se debe ver desde CargarProductosFragment y RVAdapterHelado
    public List<ItemProducto> listaHeladosSeleccionados;
    public Integer PEDIDO_ACTUAL_NRO_POTE;
    public Integer PEDIDO_ACTUAL_MEDIDA_POTE;
    public PoteEntity PEDIDO_ACTUAL_POTE;









}
