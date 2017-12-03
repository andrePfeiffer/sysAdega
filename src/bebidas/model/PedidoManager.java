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
		
		// Verifica se h� ao menos um vinho no pedido
		if(vinhos == null) {
			return "N�o foi poss�vel criar pedido vazio";
		}
		
		// Cria lista consolidando itens do pedido (soma qtd para itens com mesmo idVinho)
		ArrayList<ItemPedido> itemPedidoList = new ArrayList<ItemPedido>();
		int qtProdutos = vinhos.length;
		for(int i=0; i < qtProdutos; i++) {
			if(vinhos[i].equals("0")) {
				return "N�o foi poss�vel criar pedido com vinho n�o selecionado.";
			}
			
			int idVinho = Integer.parseInt(vinhos[i]);
			int qtdVinho = Integer.parseInt(qtdVinhos[i]);
			if(qtdVinho < 1) {
				return "N�o foi poss�vel criar item de pedido com quantidade menor que 1.";
			}
			
			ItemPedido itemPedido = new ItemPedido();
			
			Vinho vinho = new Vinho();
			try {
				vinho = vinhoDAO.selecionarPorId(idVinho);
			} catch( Exception e ) {
				e.printStackTrace();
				return "N�o foi poss�vel criar o pedido. Vinho n�o encontrado.";
			}
			
			itemPedido.setVinho(vinho);
			itemPedido.setQtdVinho(qtdVinho);
			
			// verifica se j� foi inserido algum item com o mesmo idVinho
			boolean itemInserido = false;
			for(int j=0; j < itemPedidoList.size(); j++) {
				if(itemPedidoList.get(j).getVinho().getIdVinho() == idVinho) {
					// soma qtdes
					int qtdInicial = itemPedidoList.get(j).getQtdVinho();
					int qtdNova = qtdInicial + qtdVinho;
					// define nova quantidade no itemPedido j� listado
					itemPedidoList.get(j).setQtdVinho(qtdNova);
					itemInserido = true;
					break;
				}
			}
			// caso n�o tenha sido inserido anteriormente, insere um novo itemPedido
			if(!itemInserido) {
				itemPedidoList.add(itemPedido);
			}			
		}
		
		
		// Verifica a quantidade de cada item do pedido em rela��o ao estoque
		for (ItemPedido itemPedido : itemPedidoList) {
			if(itemPedido.getQtdVinho() > itemPedido.getVinho().getQtdEstoque()) {
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
			mensagem = "N�o foi poss�vel criar o pedido";
			return mensagem;
		}
		
		// Associa itens do pedido ao pedido e insere no BD
		double valorTotalPedido = 0.0;
		for (ItemPedido itemPedido : itemPedidoList) {
			Vinho vinho = itemPedido.getVinho();
			String nomeVinho = vinho.getNomeVinho();
			double precoVinho = vinho.getPrecoVinho();
			int qtdVinho = itemPedido.getQtdVinho();
			double valorTotalItem = precoVinho * qtdVinho;
			valorTotalPedido += valorTotalItem;
			
			// Atualiza os dados do itemPedido;
			itemPedido.setPedido(pedido);
			itemPedido.setValorTotalItem(valorTotalItem);
			
			// Insere o itemPedido no BD
			try {
				itemPedidoDAO.inserir(itemPedido);
				mensagem = mensagem + " " + nomeVinho + " (" + qtdVinho + ").";
			} catch( Exception e ) {
				e.printStackTrace();
				mensagem = "N�o foi poss�vel inserir item do pedido";				
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
	
	// o M�todo encerrar foi transferido para dentro do State
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

