package bebidas.state;

public interface State {
	
	public String getDescricaoEstadoPedido();
	
	public String preparar();
	public String encerrar();
	public String cancelar();

}
