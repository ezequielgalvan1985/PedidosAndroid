package adaptivex.pedidoscloud.Core;

import adaptivex.pedidoscloud.Repositories.CategoriaRepository;
import adaptivex.pedidoscloud.Repositories.EstadoRepository;
import adaptivex.pedidoscloud.Repositories.HorarioRepository;
import adaptivex.pedidoscloud.Repositories.MarcaRepository;
import adaptivex.pedidoscloud.Repositories.ParameterRepository;
import adaptivex.pedidoscloud.Repositories.PedidoRepository;
import adaptivex.pedidoscloud.Repositories.PedidodetalleRepository;
import adaptivex.pedidoscloud.Repositories.ProductoRepository;
import adaptivex.pedidoscloud.Repositories.PromoRepository;
import adaptivex.pedidoscloud.Repositories.UnidadmedidaRepository;
import adaptivex.pedidoscloud.Repositories.UserProfileRepository;
import adaptivex.pedidoscloud.Repositories.UserRepository;

public interface IAbstractFactoryRepositories {
    public CategoriaRepository getCategoriaRepository();
    public MarcaRepository getMarcaRepository();
    public ProductoRepository getProductoRepository();
    public EstadoRepository getEstadoRepository();
    public HorarioRepository getHorarioRepository();
    public ParameterRepository getParameterRepository();
    public PedidoRepository getPedidoRepository();
    public PromoRepository getPromoRepository();
    public UserProfileRepository getUserProfileRepository();
    public UnidadmedidaRepository getUnidadmedidaRepository();
    public UserRepository getUserRepository ();
    public PedidodetalleRepository getPedidodetalleRepository();

}
