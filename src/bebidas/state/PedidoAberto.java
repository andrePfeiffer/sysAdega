package bebidas.state;

public class PedidoAberto implements State {

	@Override
	public String estadoPedido() {
		return "Aberto";
	}

}
