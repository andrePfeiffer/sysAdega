package bebidas.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bebidas.dao.ClienteDAO;
import bebidas.dao.PedidoDAO;
import bebidas.dao.VinhoDAO;

public class PedidoManager {

	public static String criarPedido(int idVinho, int idCliente, int qtdVinho) {
		VinhoDAO vinhoDAO = new VinhoDAO();
		PedidoDAO pedidoDAO = new PedidoDAO();
		ClienteDAO clienteDAO = new ClienteDAO();
		
		Vinho vinho = vinhoDAO.selecionarPorId(idVinho);
		List<Vinho> vinhos = new ArrayList<>();
		vinhos.add(vinho);
		Cliente cliente = clienteDAO.selecionarPorId(idCliente);
		
		Pedido pedido = new Pedido();
		pedido.setVinhos(vinhos);
		pedido.setEstadoAtual(pedido.getPedidoAberto());
		pedido.setCliente(cliente);
		pedido.setDtPedido(new Date());
		
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setQtdVinho(qtdVinho);
		int precoVinho = (int) vinho.getPrecoVinho();
		itemPedido.setValorTotalItem(qtdVinho * precoVinho);
		
		pedidoDAO.inserir(pedido);
		
		//TODO: fazer o tratamento de erro
		
		return "Pedido cadastrado com sucesso";
		
	}
	
	public static String encerrarPedido(int idPedido) {
		VinhoDAO vinhoDAO = new VinhoDAO();
		PedidoDAO pedidoDAO = new PedidoDAO();
		
		Pedido pedido = pedidoDAO.selecionarPorId(idPedido);
		
		if(pedido.getEstadoAtual() != pedido.getPedidoEncerrado()) {
			// Atualiza o vinho
			List<Vinho> vinhos = pedido.getVinhos();
			for (Vinho vinho : vinhos) {
				int qtdFinal = vinho.getQtdEstoque()-1; //TODO: substituir o 1 por pedido.getQtdVinho()
				if( qtdFinal < 0 ) {
					return "N�o foi poss�vel encerrar o pedido: Estoque insuficiente!";
				}
				vinho.setQtdEstoque(qtdFinal);
				vinhoDAO.atualizar(vinho);
			}
			
			// Atualiza o pedido
			pedido.setDtEncerramento(new Date());
			pedido.setEstadoAtual(pedido.getPedidoEncerrado());
			pedidoDAO.atualizar(pedido);
			
			return "Pedido encerrado com sucesso!";
		}
		
		return "N�o foi poss�vel encerrar o pedido: Pedido j� encerrado!";
		
	}

	public static List<Pedido> consultarPedidoPorEstado(String estadoPedido) {
		PedidoDAO pedidoDAO = new PedidoDAO();
		List<Pedido> lista = new ArrayList<Pedido>();
		if( estadoPedido.equals("todos")) {
			lista = pedidoDAO.selecionarTodos();
		} else {
			lista = pedidoDAO.selecionarPorEstado(estadoPedido);
		}
		return lista;
	}
}

