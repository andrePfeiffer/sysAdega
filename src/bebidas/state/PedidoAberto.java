package bebidas.state;

import java.util.List;

import bebidas.dao.PedidoDAO;
import bebidas.dao.VinhoDAO;
import bebidas.model.ItemPedido;
import bebidas.model.Pedido;
import bebidas.model.Vinho;

public class PedidoAberto implements State {
	
	Pedido pedido;
	
	// Construtor
	public PedidoAberto(Pedido pedido) {
		super();
		this.pedido = pedido;
	}

	@Override
	public String getDescricaoEstadoPedido() {
		return "Aberto";
	}

	@Override
	public String preparar() {

		pedido.setEstadoAtual(pedido.getPedidoEmAndamento());
		// atualiza pedido
		PedidoDAO pedidoDAO = new PedidoDAO();
		pedidoDAO.atualizar(pedido);
		return "Pedido atualizado para em adamento";
	}

	@Override
	public String encerrar() {
		return "ERRO: Não foi possível encerrar pois o pedido não está em andamento" ;
	}

	@Override
	public String cancelar() {
		pedido.setEstadoAtual(pedido.getPedidoCancelado());
		
		// atualiza pedido
		PedidoDAO pedidoDAO = new PedidoDAO();
		pedidoDAO.atualizar(pedido);
		
		// retorna qtdes do pedido ao estoque
		VinhoDAO vinhoDAO = new VinhoDAO();
		List<ItemPedido> itens = pedido.getItensPedido();
      	for(ItemPedido item : itens){
      		Vinho vinho = item.getVinho();
      		int qtdVinho = item.getQtdVinho();
      		int qtdFinal = vinho.getQtdEstoque() + qtdVinho;
      		vinho.setQtdEstoque(qtdFinal);
      		vinhoDAO.atualizar(vinho);      		
      	}
		
		return "Pedido cancelado com sucesso";
	}

}
