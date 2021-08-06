package adaptivex.pedidoscloud.Servicios;

import android.content.Context;

import adaptivex.pedidoscloud.Core.FactoryRepositories;
import adaptivex.pedidoscloud.Servicios.Retrofit.CategoriaServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.EstadoServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.HorarioServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.MarcaServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.ParameterServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.PedidoServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.ProductoServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.PromoServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.UnidadmedidaServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.UserProfileServices;
import adaptivex.pedidoscloud.Servicios.Retrofit.UserServices;

public class FactoryServices implements IFactoryAbstractServices{
    private static FactoryServices INSTANCIA;

    //servicios del sistema
    private static MarcaServices        marcas;
    private static CategoriaServices    categorias;
    private static ProductoServices     productos;
    private static UserProfileServices  profiles;
    private static EstadoServices       estados;
    private static HorarioServices      horarios;
    private static ParameterServices    parameters;
    private static PedidoServices       pedidos;
    private static PromoServices        promos;
    private static UnidadmedidaServices unidadmedidas;
    private static UserServices         users;


    public  static FactoryServices getInstancia() {
        if (INSTANCIA==null) {
            INSTANCIA=new FactoryServices();
        }
        return INSTANCIA;
    }




    @Override
    public MarcaServices getMarcaServices() {
        if (marcas==null)
            marcas = new MarcaServices();
        return marcas;
    }

    @Override
    public CategoriaServices getCategoriaServices() {
        if (categorias==null)
            categorias = new CategoriaServices();
        return categorias;
    }

    @Override
    public UserProfileServices getUserProfileServices() {
        if (profiles==null)
            profiles = new UserProfileServices();
        return profiles;
    }

    @Override
    public ProductoServices getProductoServices() {
        if (productos==null)
            productos = new ProductoServices();
        return productos;
    }

    @Override
    public EstadoServices getEstadoServices() {
        if (estados==null)
            estados = new EstadoServices();
        return estados;
    }

    @Override
    public HorarioServices getHorarioServices() {
        if (horarios==null)
            horarios = new HorarioServices();
        return horarios;
    }

    @Override
    public ParameterServices getParameterServices() {
        if (parameters==null)
            parameters = new ParameterServices();
        return parameters;
    }

    @Override
    public PedidoServices getPedidoServices() {
        if (pedidos==null)
            pedidos = new PedidoServices();
        return pedidos;
    }

    @Override
    public PromoServices getPromoServices() {
        if (promos==null)
            promos = new PromoServices();
        return promos;
    }

    @Override
    public UnidadmedidaServices getUnidadmedidaServices() {
        if (unidadmedidas==null)
            unidadmedidas = new UnidadmedidaServices();
        return unidadmedidas;

    }

    @Override
    public UserServices getUserServices() {
        if (users==null)
            users = new UserServices();
        return users;
    }


}
