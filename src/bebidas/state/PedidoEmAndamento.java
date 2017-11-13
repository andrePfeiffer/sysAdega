package bebidas.state;

public class PedidoEmAndamento implements State {

	@Override
	public String estadoPedido() {
		return "Em Andamento";
	}

}
