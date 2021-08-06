package adaptivex.pedidoscloud.Config;

/**
 * Created by ezequiel on 23/05/2016.
 * VARIABLES Y CONFIGURACION DE LA APP EN GENERAL, ORIGNENES DE DATOS, SON TODAS ESTATICAS
 */
public class Configurador {

    public static final int DBVersion = 1;
    public static final String DBName = "DB_ROMA7";
    private static Configurador INSTANCIA;
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
    public static final String urlPedidos             = strRoot +"/v1/pedidos/";
    public static final String urlMemos               = strRoot +"/v1/memoclientes/";
    public static final String urlClientes            = strRoot +"/v1/clientes/";
    public static final String urlProductos           = strRoot +"/v1/productos/";
    public static final String urlCategorias          = strRoot +"/v1/categorias/";
    public static final String urlMarcas              = strRoot +"/v1/marcas/";
    public static final String urlHojarutas           = strRoot +"/v1/hojarutas/";
    public static final String urlHojarutadetalles    = strRoot +"/v1/hojarutadetalles/";
    public static final String urlPostPedido          = strRoot +"/v1/pedido/add/";
    public static final String urlPedidoFindById      = strRoot +"/v1/pedido/findbyid/";
    public static final String urlPostPedidodetalle   = strRoot +"/v1/pedidodetallessend";
    public static final String urlPostClientes        = strRoot +"/v1/clientes";
    //public static String urlPostLogin           = strRoot +"/v1/user/login";
    public static final String urlPostLogin           = strRoot +"/v1/api-token-auth/";
    public static final String urlPostRegister        = strRoot +"/v1/user/register";
    public static final String urlPostUpdateUser      = strRoot +"/v1/user/update";
    public static final String urlPromos              = strRoot +"/v1/promos";
    public static final String urlParameters          = strRoot +"/v1/parametros/";
    public static final String urlHorarios            = strRoot + "/v1/horarios/";
    public static final String urlEstados             = strRoot + "/v1/estados/";
    public static final String urlUnidadmedidas       = strRoot +"/v1/unidadmedidas/";
    public static final String urlUserProfile         = strRoot +"/v1/userprofiles/";



    public  static Configurador getConfigurador() {

        if (INSTANCIA==null) {

            INSTANCIA=new Configurador();
        }
        return INSTANCIA;
    }




}
