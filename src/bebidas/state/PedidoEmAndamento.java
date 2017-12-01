package bebidas.state;

import java.util.Date;
import java.util.List;

import bebidas.dao.*;
import bebidas.model.ItemPedido;
import bebidas.model.Pedido;
import bebidas.model.Vinho;

public class PedidoEmAndamento implements State {
	
	Pedido pedido;

	// Construtor
	public PedidoEmAndamento(Pedido pedido) {
		super();
		this.pedido = pedido;
	}


	@Override
	public String getDescricaoEstadoPedido() {
		return "Em andamento";
	}

	@Override
	public String preparar() {
		return "ERRO: Não é possível colocar em andamento pedido já em andamento." ;
	}

	@Override
	public String encerrar() {
	
		// Atualiza o pedido
      	PedidoDAO pedidoDAO = new PedidoDAO();
		pedido.setDtEncerramento(new Date());
		pedido.setEstadoAtual(pedido.getPedidoEncerrado());
		pedidoDAO.atualizar(pedido);
		
		return "Pedido encerrado com sucesso!";
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
