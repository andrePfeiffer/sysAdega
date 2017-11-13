package bebidas.state;

public class PedidoCancelado implements State {

	@Override
	public String estadoPedido() {
		return "Cancelado";
	}


}
