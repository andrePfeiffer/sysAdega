package bebidas.state;

import bebidas.dao.PedidoDAO;
import bebidas.model.Pedido;

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
		
		// TODO(leticiabac): Verificar se h� estoque suficiente para preparar o pedido?
		
		// Caso haja estoque suficiente
		pedido.setEstadoAtual(pedido.getPedidoEmAndamento());
		// atualiza pedido
		PedidoDAO pedidoDAO = new PedidoDAO();
		pedidoDAO.atualizar(pedido);
		return "Pedido atualizado para em adamento";
	}

	@Override
	public String encerrar() {
		return "ERRO: N�o foi poss�vel encerrar pois o pedido n�o est� em andamento" ;
	}

	@Override
	public String cancelar() {
		pedido.setEstadoAtual(pedido.getPedidoCancelado());
		
		// atualiza pedido
		PedidoDAO pedidoDAO = new PedidoDAO();
		pedidoDAO.atualizar(pedido);
		
		return "Pedido cancelado com sucesso";
	}

}
