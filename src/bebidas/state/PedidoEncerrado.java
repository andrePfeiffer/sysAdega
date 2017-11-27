package bebidas.state;

import bebidas.model.Pedido;

public class PedidoEncerrado implements State {
	
	Pedido pedido;

	// Construtor
	public PedidoEncerrado(Pedido pedido) {
		super();
		this.pedido = pedido;
	}


	@Override
	public String getDescricaoEstadoPedido() {
		return "Encerrado";
	}

	@Override
	public String preparar() {
		return "ERRO: Não é possível colocar em andamento pedido já Encerrado." ;
	}

	@Override
	public String encerrar() {	
		return "ERRO: Não é possível encerrar pedido já Encerrado." ;
	}

	@Override
	public String cancelar() {
		return "ERRO: Não é possível cancelar pedido já Encerrado." ;
	}
	
}
