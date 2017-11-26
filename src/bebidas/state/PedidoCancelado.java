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
		return "ERRO: N�o � poss�vel colocar em andamento pedido Cancelado." ;
	}

	@Override
	public String encerrar() {
		return "ERRO: N�o � poss�vel encerrar pedido Cancelado." ;
	}

	@Override
	public String cancelar() {
		return "ERRO: N�o � poss�vel cancelar pedido j� Cancelado." ;
	}


}
