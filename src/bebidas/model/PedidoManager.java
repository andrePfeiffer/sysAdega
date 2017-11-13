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
		Cliente cliente = clienteDAO.selecionarPorId(idCliente);
		
		Pedido pedido = new Pedido();
		pedido.setVinho(vinho);
		pedido.setEstadoAtual(pedido.getPedidoAberto());
		pedido.setCliente(cliente);
		pedido.setQtdVinho(qtdVinho);
		pedido.setDtPedido(new Date());
		pedido.setValorTotal(qtdVinho * vinho.getPrecoVinho());
		
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
			Vinho vinho = pedido.getVinho();
			int qtdFinal = vinho.getQtdEstoque()-pedido.getQtdVinho();
			if( qtdFinal < 0 ) {
				return "Não foi possível encerrar o pedido: Estoque insuficiente!";
			}
			vinho.setQtdEstoque(qtdFinal);
			vinhoDAO.atualizar(vinho);
			
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

