package bebidas.model;
import java.util.List;

import bebidas.dao.ClienteDAO;

public class ClienteManager {

	public static String cadastrarCliente (String nomeCliente, String cpf, String cep, String endereco, String numero, String complemento, String bairro, String cidade, String estado, String pais ) {
		ClienteDAO dao = new ClienteDAO();
				
		// Verifica se todos os campos estão preenchidos
		if( nomeCliente == null || cpf == null || cep == null || endereco == null || numero == null || complemento == null || bairro == null || cidade == null || estado == null || pais == null) { 
			String mensagem = "Não foi possível cadastrar o cliente: Preencha todos os campos obrigatórios.";
			return mensagem;
		}

		// Verifica se já existe cliente com este cpf
		Cliente existente = dao.selecionarPorCpf(cpf);
		if( existente != null ) {
			String mensagem = "Não foi possível cadastrar o cliente: Já existe outro cliente com este cpf.";
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
			String mensagem = "Não foi possível cadastrar o cliente";
			return mensagem;
		}
	}
	
	public static String editarCliente(int idCliente, String nomeCliente, String cpf, String cep, String endereco, String numero, String complemento, String bairro, String cidade, String estado, String pais ) {
		ClienteDAO dao = new ClienteDAO();

		// Verifica se todos os campos estão preenchidos
		if( nomeCliente == null || cpf == null || cep == null || endereco == null || numero == null || complemento == null || bairro == null || cidade == null || estado == null || pais == null) { 
			String mensagem = "Não foi possível editar o cliente: Preencha todos os campos obrigatórios.";
			return mensagem;
		}

		// Verifica se já existe cliente com este nome
		Cliente existente = dao.selecionarPorNome(nomeCliente);
		if( existente != null && existente.getIdCliente() != idCliente ) {
			String mensagem = "Não foi possível editar o cliente: Já existe outro cliente com este nome.";
			return mensagem;
		}
		
		if( existente != null && existente.getCpf() == cpf ) {
			String mensagem = "Não foi possível editar o cliente: Já existe outro cliente com este cpf.";
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
			String mensagem = "Não foi possível editar o cliente";
			return mensagem;
		}
	}
	
	public static String apagarCliente( int idCliente ) {
		ClienteDAO dao = new ClienteDAO();
		
		try {
			dao.apagar(idCliente);
			String mensagem = "Cliente apagado com sucesso!";
			return mensagem;
		} catch( Exception e ) {
			e.printStackTrace();
			String mensagem = "Não foi possível apagar o cliente, contate o administrador do sistema.";
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
	
	//Esta função está comentada, pois não será necessário implementar para clientes.

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
//		novo.setNomeCliente("João da Silva");
//		novo.setCpf("83137546648");
//		novo.setCep("72581-470");
//		novo.setEndereco("Chácara Chácara");
//		novo.setNumero("13");
//		novo.setComplemento("A");
//		novo.setBairro("Núcleo Rural Santa Maria");
//		novo.setCidade("Brasília");
//		novo.setEstado("DF");
//		novo.setPais("Brasil");
//		clienteDao.inserir(novo);
//	}
}
