package bebidas.model;
import java.util.InputMismatchException;
import java.util.List;

import bebidas.dao.ClienteDAO;
import bebidas.dao.PedidoDAO;

public class ClienteManager {
	
	public static boolean isCPF(String CPF) {
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		    if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
		        CPF.equals("22222222222") || CPF.equals("33333333333") ||
		        CPF.equals("44444444444") || CPF.equals("55555555555") ||
		        CPF.equals("66666666666") || CPF.equals("77777777777") ||
		        CPF.equals("88888888888") || CPF.equals("99999999999") ||
		       (CPF.length() != 11))
		       return(false);

		    char dig10, dig11;
		    int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
		    try {
		// Calculo do 1o. Digito Verificador
		      sm = 0;
		      peso = 10;
		      for (i=0; i<9; i++) {              
		// converte o i-esimo caractere do CPF em um numero:
		// por exemplo, transforma o caractere '0' no inteiro 0         
		// (48 eh a posicao de '0' na tabela ASCII)         
		        num = (int)(CPF.charAt(i) - 48); 
		        sm = sm + (num * peso);
		        peso = peso - 1;
		      }

		      r = 11 - (sm % 11);
		      if ((r == 10) || (r == 11))
		         dig10 = '0';
		      else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

		// Calculo do 2o. Digito Verificador
		      sm = 0;
		      peso = 11;
		      for(i=0; i<10; i++) {
		        num = (int)(CPF.charAt(i) - 48);
		        sm = sm + (num * peso);
		        peso = peso - 1;
		      }

		      r = 11 - (sm % 11);
		      if ((r == 10) || (r == 11))
		         dig11 = '0';
		      else dig11 = (char)(r + 48);

		// Verifica se os digitos calculados conferem com os digitos informados.
		      if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
		         return(true);
		      else return(false);
		    } catch (InputMismatchException erro) {
		        return(false);
		    }
		  }
	
	
	public static String cadastrarCliente (String nomeCliente, String cpf, String cep, String endereco, String numero, String complemento, String bairro, String cidade, String estado, String pais ) {
		ClienteDAO dao = new ClienteDAO();
				
		// Verifica se todos os campos est�o preenchidos
		if( nomeCliente == null || cep.replaceAll("[^0-9]", "") == "" || endereco == null || numero == null || complemento == null || bairro == null || cidade == null || estado == null || pais == null) { 
			String mensagem = "N�o foi poss�vel cadastrar o cliente: Preencha todos os campos obrigat�rios.";
			return mensagem;
		}
		
		// Verifica se o CPF � v�lido
		if(!isCPF(cpf.replaceAll("[^0-9]", ""))){
			String mensagem = "N�o foi poss�vel cadastrar o cliente: Digite um CPF v�lido.";
			return mensagem;
		}

		// Verifica se j� existe cliente com este cpf
		Cliente existente = dao.selecionarPorCpf(cpf);
		if( existente != null ) {
			String mensagem = "N�o foi poss�vel cadastrar o cliente: J� existe outro cliente com este cpf.";
			return mensagem;
		}

		Cliente novo = new Cliente();
		novo.setNomeCliente(nomeCliente);
		novo.setCpf(cpf);
		novo.setCep(cep);
		novo.setEndereco(endereco);
		novo.setNumero(numero);
		novo.setComplemento(complemento);
		novo.setBairro(bairro);
		novo.setCidade(cidade);
		novo.setEstado(estado);
		novo.setPais(pais);
				
		try {
			dao.inserir(novo);
			String mensagem = "Cliente " + novo.getNomeCliente() + " inserido com sucesso.";
			return mensagem;
		} catch( Exception e ) {
			e.printStackTrace();
			String mensagem = "N�o foi poss�vel cadastrar o cliente";
			return mensagem;
		}
	}
	
	public static String editarCliente(int idCliente, String nomeCliente, String cpf, String cep, String endereco, String numero, String complemento, String bairro, String cidade, String estado, String pais ) {
		ClienteDAO dao = new ClienteDAO();

		// Verifica se todos os campos est�o preenchidos
		if( nomeCliente == null || cpf == null || cep == null || endereco == null || numero == null || complemento == null || bairro == null || cidade == null || estado == null || pais == null) { 
			String mensagem = "N�o foi poss�vel editar o cliente: Preencha todos os campos obrigat�rios.";
			return mensagem;
		}
		
		// Verifica se o CPF � v�lido
		if(!isCPF(cpf.replaceAll("[^0-9]", ""))){
			String mensagem = "N�o foi poss�vel editar o cliente: Digite um CPF v�lido.";
			return mensagem;
		}

		// Verifica se j� existe cliente com este nome
		Cliente existente = dao.selecionarPorNome(nomeCliente);
		if( existente != null && existente.getIdCliente() != idCliente ) {
			String mensagem = "N�o foi poss�vel editar o cliente: J� existe outro cliente com este nome.";
			return mensagem;
		}
		
		if( existente != null && existente.getCpf() == cpf ) {
			String mensagem = "N�o foi poss�vel editar o cliente: J� existe outro cliente com este cpf.";
			return mensagem;
		}

		// Recupera o cliente a editar
		existente = (Cliente)dao.selecionarPorId(idCliente);
		existente.setNomeCliente(nomeCliente);
		existente.setCpf(cpf);
		existente.setCep(cep);
		existente.setEndereco(endereco);
		existente.setNumero(numero);
		existente.setComplemento(complemento);
		existente.setBairro(bairro);
		existente.setCidade(cidade);
		existente.setEstado(estado);
		existente.setPais(pais);
		
		try {
			dao.atualizar(existente);
			String mensagem = "Cliente " + existente.getNomeCliente() + " atualizado com sucesso.";
			return mensagem;
		} catch( Exception e ) {
			e.printStackTrace();
			String mensagem = "N�o foi poss�vel editar o cliente";
			return mensagem;
		}
	}
	
	public static String apagarCliente( int idCliente ) {
		// Verifica se h� pedido associado a este cliente (se houver, n�o permite que o cliente seja apagado)
		ClienteDAO clienteDAO = new ClienteDAO();
		PedidoDAO pedidoDAO = new PedidoDAO();		
		Cliente cliente = clienteDAO.selecionarPorId(idCliente);
		String nomeCliente = cliente.getNomeCliente();
		List<Pedido> listaPedido = pedidoDAO.selecionarPorCliente(cliente);
		if(listaPedido != null) {
			int qtdPedidos = listaPedido.size();
			if(qtdPedidos > 0) {
				String mensagem = "N�o foi poss�vel apagar o cliente " + nomeCliente + " pois h� " + qtdPedidos + " pedido(s) associado(s) a ele.";
				return mensagem;
			}
		}
		
		// Apaga o cliente
		try {
			clienteDAO.apagar(idCliente);
			String mensagem = "Cliente apagado com sucesso!";
			return mensagem;
		} catch( Exception e ) {
			e.printStackTrace();
			String mensagem = "N�o foi poss�vel apagar o cliente, contate o administrador do sistema.";
			return mensagem;
		}
	}


	public static List<Cliente> consultarTodosClientes() {
		ClienteDAO dao = new ClienteDAO();
		List<Cliente> lista = dao.selecionarTodos();
		return lista;
	}

	public static Cliente consultarClientePorId( int idCliente ) {
		ClienteDAO dao = new ClienteDAO();
		Cliente cliente = dao.selecionarPorId(idCliente);
		return cliente;
	}


	// Limpeza do BD
	
	//Esta fun��o est� comentada, pois n�o ser� necess�rio implementar para clientes.

//	public static void limparBD() {
//		ClienteDAO clienteDao = new ClienteDAO();
//		List<Cliente> clientes = clienteDao.selecionarTodos();
//		for (Cliente cliente : clientes) {
//			clienteDao.apagar(cliente.getIdCliente());
//		}		
//		System.out.println("Clientes apagados com sucesso!");
//	}
//	
//	public static void popularBD() {
//		ClienteDAO clienteDao = new ClienteDAO();
//		
//		Cliente novo = new Cliente();
//		novo.setNomeCliente("Jo�o da Silva");
//		novo.setCpf("83137546648");
//		novo.setCep("72581-470");
//		novo.setEndereco("Ch�cara Ch�cara");
//		novo.setNumero("13");
//		novo.setComplemento("A");
//		novo.setBairro("N�cleo Rural Santa Maria");
//		novo.setCidade("Bras�lia");
//		novo.setEstado("DF");
//		novo.setPais("Brasil");
//		clienteDao.inserir(novo);
//	}
}
