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
		
		// Verifica se há ao menos um vinho no pedido
		if(vinhos == null) {
			return "Não foi possível criar pedido vazio";
		}
		
		// Verifica a quantidade de cada item do pedido
		int qtProdutos = vinhos.length;
		for(int i=0; i < qtProdutos; i++) {
			int idVinho = Integer.parseInt(vinhos[i]);
			int qtdVinho = Integer.parseInt(qtdVinhos[i]);
			if(qtdVinho < 1) {
				return "Não foi possível criar item de pedido com quantidade menor que 1";
			}
			Vinho vinho = vinhoDAO.selecionarPorId(idVinho);
			int qtdEstoque = vinho.getQtdEstoque();
			if(qtdVinho > qtdEstoque) {
				return "Estoque insuficiente";
			}
		}
		
		// Inicializa mensagem de retorno
		String mensagem = "";
		
		// Cria o pedido		
		Cliente cliente = clienteDAO.selecionarPorId(idCliente);
		Pedido pedido = new Pedido();
		pedido.setEstadoPedido("Aberto");
		pedido.setCliente(cliente);
		pedido.setDtPedido(new Date());
		// Insere o pedido
		try {
			pedidoDAO.inserir(pedido);
			mensagem = "Pedido criado com sucesso.";
		} catch( Exception e ) {
			e.printStackTrace();
			mensagem = "Não foi possível criar o pedido";
			return mensagem;
		}
		
		//Insere itens do pedido
		double valorTotalPedido = 0.0;
		for(int i=0; i < qtProdutos; i++) {
			ItemPedido itemPedido = new ItemPedido();
			int idVinho = Integer.parseInt(vinhos[i]);
			int qtdVinho = Integer.parseInt(qtdVinhos[i]);
			Vinho vinho = vinhoDAO.selecionarPorId(idVinho);
			String nomeVinho = vinho.getNomeVinho();
			double precoVinho = vinho.getPrecoVinho();
			double valorTotalItem = precoVinho * qtdVinho;
			valorTotalPedido += valorTotalItem;
			itemPedido.setPedido(pedido);
			itemPedido.setQtdVinho(qtdVinho);
			itemPedido.setVinho(vinho);
			itemPedido.setValorTotalItem(valorTotalItem);
			try {
				itemPedidoDAO.inserir(itemPedido);
				mensagem = mensagem + " " + nomeVinho + " (" + qtdVinho + ").";
			} catch( Exception e ) {
				e.printStackTrace();
				mensagem = "Não foi possível inserir item do pedido";				
				return mensagem;
			}
			// Retira qtde do estoque do vinho (reserva qtde para o pedido)
      		int qtdFinal = vinho.getQtdEstoque() - qtdVinho;
	      	vinho.setQtdEstoque(qtdFinal);
	      	vinhoDAO.atualizar(vinho);      		
		}
		
		// Atualiza o valor total do pedido
		pedido.setValorTotal(valorTotalPedido);
		pedidoDAO.atualizar(pedido);
      	
		return mensagem;
		
	}
	
	// o Método encerrar foi transferido para dentro do State
	public static String encerrarPedido(int idPedido) {
		PedidoDAO pedidoDAO = new PedidoDAO();
		
		Pedido pedido = pedidoDAO.selecionarPorId(idPedido);
		
		return pedido.encerrarPedido();
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

