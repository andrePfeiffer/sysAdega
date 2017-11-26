package bebidas.state;

import bebidas.model.Pedido;

public class PedidoCancelado implements State {
	
	Pedido pedido;

	// Construtor
	public PedidoCancelado(Pedido pedido) {
		super();
		this.pedido = pedido;
	}


	@Override
	public String getDescricaoEstadoPedido() {
		return "Cancelado";
	}

	@Override
	public String preparar() {
		return "ERRO: Não é possível colocar em andamento pedido Cancelado." ;
	}

	@Override
	public String encerrar() {
		return "ERRO: Não é possível encerrar pedido Cancelado." ;
	}

	@Override
	public String cancelar() {
		return "ERRO: Não é possível cancelar pedido já Cancelado." ;
	}


}
