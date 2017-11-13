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
	
	public static String editarCliente( int idCliente, String nomeCliente, int anoCliente, String corCliente, double precoCliente, int qtdEstoque ) {
		ClienteDAO dao = new ClienteDAO();

		// Verifica se todos os campos estão preenchidos
		if( nomeCliente == null || anoCliente <= 0 || corCliente == null || precoCliente < 0 || qtdEstoque < 0 ) { 
			String mensagem = "Não foi possível editar o vinho: Preencha todos os campos obrigatórios.";
			return mensagem;
		}

		// Verifica se já existe vinho com este nome
		Cliente existente = dao.selecionarPorNome(nomeCliente);
		if( existente != null && existente.getIdCliente() != idCliente ) {
			String mensagem = "Não foi possível editar o vinho: Já existe outro vinho com este nome.";
			return mensagem;
		}

		// Recupera o vinho a editar
		existente = (Cliente)dao.selecionarPorId(idCliente);
//		existente.setNomeCliente(nomeCliente);
//		existente.setAnoCliente(anoCliente);
//		existente.setCorCliente(corCliente);
//		existente.setPrecoCliente(precoCliente);
//		existente.setQtdEstoque(qtdEstoque);
		
		try {
			dao.atualizar(existente);
			String mensagem = "Cliente " + existente.getNomeCliente() + " atualizado com sucesso.";
			return mensagem;
		} catch( Exception e ) {
			e.printStackTrace();
			String mensagem = "Não foi possível editar o vinho";
			return mensagem;
		}
	}
	
	public static String apagarCliente( int idCliente ) {
		ClienteDAO dao = new ClienteDAO();
		
		try {
			dao.apagar(idCliente);
			String mensagem = "Cliente apagado com sucesso: ";
			return mensagem;
		} catch( Exception e ) {
			e.printStackTrace();
			String mensagem = "Não foi possível apagar o vinho: ";
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
		Cliente vinho = dao.selecionarPorId(idCliente);
		return vinho;
	}


	// Limpeza do BD

	public static void limparBD() {
		ClienteDAO vinhoDao = new ClienteDAO();
		List<Cliente> vinhos = vinhoDao.selecionarTodos();
		for (Cliente vinho : vinhos) {
			vinhoDao.apagar(vinho.getIdCliente());
		}		
		System.out.println("Clientes apagados com sucesso!");
	}
	
	public static void popularBD() {
		ClienteDAO vinhoDao = new ClienteDAO();
		
		Cliente novo = new Cliente();
//		novo.setNomeCliente("Santa Helena");
//		novo.setAnoCliente(2016);
//		novo.setCorCliente("Tinto");
//		novo.setPrecoCliente(37.78);
//		novo.setQtdEstoque(4);
		vinhoDao.inserir(novo);
		
		novo = new Cliente();
		novo.setNomeCliente("Santa Sara");
//		novo.setAnoCliente(2015);
//		novo.setCorCliente("Rose");
//		novo.setPrecoCliente(22.78);
//		novo.setQtdEstoque(3);
		vinhoDao.inserir(novo);
		
		novo = new Cliente();
		novo.setNomeCliente("Santa Maria");
//		novo.setAnoCliente(2013);
//		novo.setCorCliente("Branco");
//		novo.setPrecoCliente(345.78);
//		novo.setQtdEstoque(2);
		vinhoDao.inserir(novo);
	}
}
