package adaptivex.pedidoscloud.Servicios;

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

public interface IFactoryAbstractServices {
    public MarcaServices getMarcaServices();
    public CategoriaServices getCategoriaServices();
    public UserProfileServices getUserProfileServices();
    public ProductoServices getProductoServices();
    public EstadoServices getEstadoServices();
    public HorarioServices getHorarioServices();
    public ParameterServices getParameterServices();
    public PedidoServices getPedidoServices();
    public PromoServices getPromoServices();
    public UnidadmedidaServices getUnidadmedidaServices();



}
