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
		return "ERRO: N�o � poss�vel colocar em andamento pedido j� Encerrado." ;
	}

	@Override
	public String encerrar() {	
		return "ERRO: N�o � poss�vel encerrar pedido j� Encerrado." ;
	}

	@Override
	public String cancelar() {
		return "ERRO: N�o � poss�vel cancelar pedido j� Encerrado." ;
	}
	
}
