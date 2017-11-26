package bebidas.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bebidas.dao.ClienteDAO;
import bebidas.dao.ItemPedidoDAO;
import bebidas.dao.PedidoDAO;
import bebidas.dao.VinhoDAO;
import bebidas.state.State;

public class PedidoManager {

	public static String criarPedido(String[] vinhos, int idCliente, String[] qtdVinhos) {
		VinhoDAO vinhoDAO = new VinhoDAO();
		PedidoDAO pedidoDAO = new PedidoDAO();
		ClienteDAO clienteDAO = new ClienteDAO();
		ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO();
		
		Cliente cliente = clienteDAO.selecionarPorId(idCliente);
		Pedido pedido = new Pedido();
//		State estadoPedido = pedido.getPedidoAberto();
//		pedido.setEstadoAtual(estadoPedido);
		pedido.setEstadoPedido("Aberto");
		pedido.setCliente(cliente);
		pedido.setDtPedido(new Date());
		pedidoDAO.inserir(pedido);

		int qtProdutos = vinhos.length;
		double valorTotalPedido = 0.0;
		for(int i=0; i < qtProdutos; i++) {
			ItemPedido itemPedido = new ItemPedido();
			int idVinho = Integer.parseInt(vinhos[i]);
			int qtdVinho = Integer.parseInt(qtdVinhos[i]);
			Vinho vinho = vinhoDAO.selecionarPorId(idVinho);
			double precoVinho = vinho.getPrecoVinho();
			double valorTotalItem = precoVinho * qtdVinho;
			valorTotalPedido += valorTotalItem;
			itemPedido.setPedido(pedido);
			itemPedido.setQtdVinho(qtdVinho);
			itemPedido.setVinho(vinho);
			itemPedido.setValorTotalItem(valorTotalItem);
			itemPedidoDAO.inserir(itemPedido);
		}
		
		pedido.setValorTotal(valorTotalPedido);
		pedidoDAO.atualizar(pedido);
		
		//TODO: fazer o tratamento de erro
		
		return "Pedido cadastrado com sucesso";
		
	}
	
	// o Método encerrar foi transferido para dentro do State
	public static String encerrarPedido(int idPedido) {
		PedidoDAO pedidoDAO = new PedidoDAO();
		
		Pedido pedido = pedidoDAO.selecionarPorId(idPedido);
		
		return pedido.encerrarPedido();
	}
	
//	public static String encerrarPedido(int idPedido) {
//		VinhoDAO vinhoDAO = new VinhoDAO();
//		PedidoDAO pedidoDAO = new PedidoDAO();
//		
//		Pedido pedido = pedidoDAO.selecionarPorId(idPedido);
//		
//		if(pedido.getEstadoAtual() != pedido.getPedidoEncerrado()) {
//			// Atualiza o vinho
//			List<Vinho> vinhos = pedido.getVinhos();
//			for (Vinho vinho : vinhos) {
//				int qtdFinal = vinho.getQtdEstoque()-1; //TODO: substituir o 1 por pedido.getQtdVinho()
//				if( qtdFinal < 0 ) {
//					return "Não foi possível encerrar o pedido: Estoque insuficiente!";
//				}
//				vinho.setQtdEstoque(qtdFinal);
//				vinhoDAO.atualizar(vinho);
//			}
//			
//			// Atualiza o pedido
//			pedido.setDtEncerramento(new Date());
//			pedido.setEstadoAtual(pedido.getPedidoEncerrado());
//			pedidoDAO.atualizar(pedido);
//			
//			return "Pedido encerrado com sucesso!";
//		}
//		
//		return "Não foi possível encerrar o pedido: Pedido já encerrado!";
//		
//	}

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
	
	public static String prepararPedido(int idPedido) {
		PedidoDAO pedidoDAO = new PedidoDAO();
		
		Pedido pedido = pedidoDAO.selecionarPorId(idPedido);
		
		return pedido.prepararPedido();
	}
	
	public static String cancelarPedido(int idPedido) {
		PedidoDAO pedidoDAO = new PedidoDAO();
		
		Pedido pedido = pedidoDAO.selecionarPorId(idPedido);
		
		return pedido.cancelarPedido();
	}
}

