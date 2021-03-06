package adaptivex.pedidoscloud.Repositories;

import android.content.Context;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Core.BusinessRules;
import adaptivex.pedidoscloud.Core.IAbstractFactoryRepositories;
import adaptivex.pedidoscloud.Core.IniciarApp;
import adaptivex.pedidoscloud.Core.WorkNumber;
import adaptivex.pedidoscloud.Entity.ParameterEntity;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
import adaptivex.pedidoscloud.Entity.UserProfileEntity;

public class FactoryRepositories implements IAbstractFactoryRepositories {
    private static FactoryRepositories INSTANCIA;
    private static Context ctx;

    public static PedidoEntity PEDIDO_TEMPORAL;

    //Repositorios del sistema
    private static CategoriaRepository categorias;
    private static MarcaRepository marcas;
    private static ProductoRepository productos;
    private static UserProfileRepository profiles;
    private static EstadoRepository estados;
    private static HorarioRepository horarios;
    private static ParameterRepository parameters;
    private static PedidoRepository pedidos;
    private static PromoRepository promos;
    private static UnidadmedidaRepository unidadmedidas;
    private static UserRepository users;
    private static PedidodetalleRepository pedidodetalles;




    public  static FactoryRepositories getInstancia() {
        return INSTANCIA;
    }

    public  static FactoryRepositories getInstancia(Context pctx) {
        if (INSTANCIA==null) {
            INSTANCIA=new FactoryRepositories();
            ctx = pctx;
        }
        return INSTANCIA;
    }


    public FactoryRepositories(){}

    public Context getCtx() {
        return ctx;
    }

    public static void setCtx(Context ctx) {
        ctx = ctx;
    }




    @Override
    public CategoriaRepository getCategoriaRepository() {
        if (categorias==null)
            categorias = new CategoriaRepository(ctx);
        return categorias;
    }

    @Override
    public MarcaRepository getMarcaRepository() {
        if (marcas == null)
            marcas = new MarcaRepository(ctx);
        return marcas;

    }

    @Override
    public ProductoRepository getProductoRepository() {
        if (productos == null)
            productos = new ProductoRepository(ctx);
        return productos;
    }

    @Override
    public EstadoRepository getEstadoRepository() {
        if (estados == null)
            estados = new EstadoRepository(ctx);
        return estados;
    }

    @Override
    public HorarioRepository getHorarioRepository() {
        if (horarios == null)
            horarios = new HorarioRepository(ctx);
        return horarios;
    }

    @Override
    public ParameterRepository getParameterRepository() {
        if (parameters == null)
            parameters = new ParameterRepository(ctx);
        return parameters;
    }

    @Override
    public PedidoRepository getPedidoRepository() {
        if (pedidos == null)
            pedidos = new PedidoRepository(ctx);
        return pedidos;
    }

    @Override
    public PedidodetalleRepository getPedidodetalleRepository() {
        if (pedidodetalles == null)
            pedidodetalles = new PedidodetalleRepository(ctx);
        return pedidodetalles;
    }

    @Override
    public PromoRepository getPromoRepository() {
        if (promos == null)
            promos = new PromoRepository(ctx);
        return promos;
    }

    @Override
    public UserProfileRepository getUserProfileRepository() {
        if (profiles == null)
            profiles = new UserProfileRepository(ctx);
        return profiles;
    }

    @Override
    public UnidadmedidaRepository getUnidadmedidaRepository() {
        if (unidadmedidas == null)
            unidadmedidas = new UnidadmedidaRepository(ctx);
        return unidadmedidas;
    }

    @Override
    public UserRepository getUserRepository() {
        if (users == null)
            users = new UserRepository(ctx);
        return users;
    }

}
