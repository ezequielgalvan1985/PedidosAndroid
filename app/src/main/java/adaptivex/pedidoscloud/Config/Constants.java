package adaptivex.pedidoscloud.Config;

/**
 * Created by egalvan on 11/3/2018.
 */

public class Constants {

    //HELADERIA MEDIDAS DE LOS POTES
    public static final int MEDIDA_KILO        = 1000;
    public static final int MEDIDA_TRESCUARTOS = 750;
    public static final int MEDIDA_MEDIO       = 500;
    public static final int MEDIDA_CUARTO      = 250;


    //Estados de un pedido
    public static final int ESTADO_NUEVO            = 0;
    public static final int ESTADO_ENPREPARACION    = 1;
    public static final int ESTADO_ENCAMINO         = 2;
    public static final int ESTADO_ENTREGADO        = 3;
    public static final int ESTADO_TODOS            = 99;


    public static final String MEDIDA_HELADO_POCO        = "Poco";
    public static final String MEDIDA_HELADO_EQUILIBRADO = "Equilibrado";
    public static final String MEDIDA_HELADO_MUCHO       = "Mucho";


    public static final int MEDIDA_HELADO_POCO_DESDE = 0;
    public static final int MEDIDA_HELADO_POCO_HASTA = 50;

    public static final int MEDIDA_HELADO_EQUILIBRADO_DESDE = 51;
    public static final int MEDIDA_HELADO_EQUILIBRADO_HASTA = 100;

    public static final int MEDIDA_HELADO_MUCHO_LIMIT_DESDE = 101;
    public static final int MEDIDA_HELADO_MUCHO_LIMIT_HASTA = 150;

    public static final String PARAM_MODE_EDIT_USER             = "datos_user";
    public static final String PARAM_PRECIO_CUCURUCHO           = "preciocucurucho";

    public static final boolean PARAM_MODE_EDIT_USER_ON         = true;
    public static final boolean PARAM_MODE_EDIT_USER_OFF        = false;
    public static final String PARAM_TIPO_LISTADO               = "tipo_listado";
    public static final Integer VALUE_TIPO_LISTADO_HELADOS      = 1;
    public static final Integer VALUE_TIPO_LISTADO_POSTRES      = 2;



    //PRECIO DEL HELADO, simula el parametro hasta que se desarrolle la funcionalidad
    public static double PRECIO_HELADO_KILO         = 0;
    public static double PRECIO_HELADO_MEDIO        = 0;
    public static double PRECIO_HELADO_CUARTO       = 0;
    public static double PRECIO_HELADO_TRESCUARTOS  = 0;
    public static double PRECIO_CUCURUCHO           = 0;


    public static final String FRAGMENT_CARGAR_DIRECCION    = "cargar_direccion";
    public static final String FRAGMENT_CARGAR_OTROS_DATOS  = "cargar_otros_datos";
    public static final String FRAGMENT_CARGAR_HOME         = "cargar_home";
    public static final String FRAGMENT_HOME_LOGIN          = "home_login";
    public static final String FRAGMENT_DETAIL              = "detail_producto";
    public static final String FRAGMENT_CARGAR_PRODUCTOS    = "cargar_productos";


    //FECHA FORMATOS
    public static final String DATE_FORMAT_SQLITE = "yyyy-MM-dd";
    public static final String DATE_FORMAT_DISPLAY_APP = "dd-MM-yyyy";



    public static final int LUNES       = 1;
    public static final int MARTES      = 2;
    public static final int MIERCOLES   = 3;
    public static final int JUEVES      = 4;
    public static final int VIERNES     = 5;



}
