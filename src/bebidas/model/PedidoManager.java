package bebidas.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bebidas.dao.ClienteDAO;
import bebidas.dao.PedidoDAO;
import bebidas.dao.VinhoDAO;

public class PedidoManager {

	public static String criarPedido(String[] vinhos, int idCliente, String[] qtdVinhos) {
		VinhoDAO vinhoDAO = new VinhoDAO();
		PedidoDAO pedidoDAO = new PedidoDAO();
		ClienteDAO clienteDAO = new ClienteDAO();
		
		List<Vinho> listaVinhos = new ArrayList<>();
		int qtProdutos = vinhos.length;
		for(int i=0; i < qtProdutos; i++) {
			int idVinho = Integer.parseInt(vinhos[i]);
			int qtdVinho = Integer.parseInt(qtdVinhos[i]);
			Vinho vinho = vinhoDAO.selecionarPorId(idVinho);
			listaVinhos.add(vinho);
		}
		
		Cliente cliente = clienteDAO.selecionarPorId(idCliente);
		
		Pedido pedido = new Pedido();
		pedido.setVinhos(listaVinhos);
		pedido.setEstadoAtual(pedido.getPedidoAberto());
		pedido.setCliente(cliente);
		pedido.setDtPedido(new Date());
		
//		ItemPedido itemPedido = new ItemPedido();
//		itemPedido.setQtdVinho(qtdVinho);
//		int precoVinho = (int) vinho.getPrecoVinho();
//		itemPedido.setValorTotalItem(qtdVinho * precoVinho);
		
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
					return "Não foi possível encerrar o pedido: Estoque insuficiente!";
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
		
		return "Não foi possível encerrar o pedido: Pedido já encerrado!";
		
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

