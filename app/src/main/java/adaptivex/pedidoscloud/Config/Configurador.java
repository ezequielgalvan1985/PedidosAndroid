package adaptivex.pedidoscloud.Config;

import adaptivex.pedidoscloud.Model.User;

/**
 * Created by ezequiel on 23/05/2016.
 * VARIABLES Y CONFIGURACION DE LA APP EN GENERAL, ORIGNENES DE DATOS, SON TODAS ESTATICAS
 */
public class Configurador {

    public static final int DBVersion = 11;
    public static final String DBName = "heladeria";
    private static Configurador INSTANCIA;
    public static  User userlogin;
    public static final String urlImgClientes = "http://www.ellechero.com.ar/files/producto/imagen/";

    //RED CELU
    //public static String strRoot = "http://192.168.43.44:8000";

    //Casa
    public static final String strRoot = "http://192.168.0.167:8000";
    public static final String urlBase = "http://192.168.0.167:8000";
    //claxson
    //public static String strRoot = "http://10.4.4.93:8000";

    //amazon
    //public static String strRoot = "http://18.228.6.207";

    public static final String urlUsers               = strRoot +"/v1/users/";
    public static final String urlPedidos             = strRoot +"/v1/pedidos";
    public static final String urlMemos               = strRoot +"/v1/memoclientes";
    public static final String urlClientes            = strRoot +"/v1/clientes";
    public static final String urlProductos           = strRoot +"/v1/productos";
    public static final String urlCategorias          = strRoot +"/v1/categorias";
    public static final String urlMarcas              = strRoot +"/v1/marcas/";
    public static final String urlHojarutas           = strRoot +"/api/hojarutas";
    public static final String urlHojarutadetalles    = strRoot +"/api/hojarutadetalles";
    public static final String urlPostPedido          = strRoot +"/api/pedido/add";
    public static final String urlPedidoFindById      = strRoot +"/api/pedido/findbyid";
    public static final String urlPostPedidodetalle   = strRoot +"/api/pedidodetallessend";
    public static final String urlPostClientes        = strRoot +"/api/clientes";
    //public static String urlPostLogin           = strRoot +"/api/user/login";
    public static final String urlPostLogin           = strRoot +"/v1/api-token-auth/";
    public static final String urlPostRegister        = strRoot +"/api/user/register";
    public static String urlPostUpdateUser      = strRoot +"/api/user/update";
    public static String urlPromos              = strRoot +"/api/promos";
    public static String urlParameters          = strRoot +"/api/parametros";
    public static String urlHorarios            = strRoot + "/horario/v1/all";

    public  static Configurador getConfigurador() {

        if (INSTANCIA==null) {

            INSTANCIA=new Configurador();
        }
        return INSTANCIA;
    }




}
