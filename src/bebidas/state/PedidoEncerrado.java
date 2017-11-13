package bebidas.state;

public class PedidoEncerrado implements State {

	@Override
	public String estadoPedido() {
		return "Encerrado";
	}
	
}
