package bebidas.model;
import java.util.List;

import bebidas.dao.VinhoDAO;

public class VinhoManager {

	public static String cadastrarVinho( String nomeVinho, int anoVinho, String corVinho, double precoVinho, int qtdEstoque ) {
		VinhoDAO dao = new VinhoDAO();

		// Verifica se todos os campos estão preenchidos
		if( nomeVinho == null || anoVinho <= 0 || corVinho == null || precoVinho < 0 || qtdEstoque < 0 ) { 
			String mensagem = "Não foi possível cadastrar o vinho: Preencha todos os campos obrigatórios.";
			return mensagem;
		}

		// Verifica se já existe vinho com este nome
		Vinho existente = dao.selecionarPorNome(nomeVinho);
		if( existente != null ) {
			String mensagem = "Não foi possível cadastrar o vinho: Já existe outro vinho com este nome.";
			return mensagem;
		}

		Vinho novo = new Vinho();
		novo.setNomeVinho(nomeVinho);
		novo.setAnoVinho(anoVinho);
		novo.setCorVinho(corVinho);
		novo.setPrecoVinho(precoVinho);
		novo.setQtdEstoque(qtdEstoque);
		
		try {
			dao.inserir(novo);
			String mensagem = "Vinho " + novo.getNomeVinho() + " inserido com sucesso.";
			return mensagem;
		} catch( Exception e ) {
			e.printStackTrace();
			String mensagem = "Não foi possível cadastrar o vinho";
			return mensagem;
		}
	}
	
	public static String editarVinho( int idVinho, String nomeVinho, int anoVinho, String corVinho, double precoVinho, int qtdEstoque ) {
		VinhoDAO dao = new VinhoDAO();

		// Verifica se todos os campos estão preenchidos
		if( nomeVinho == null || anoVinho <= 0 || corVinho == null || precoVinho < 0 || qtdEstoque < 0 ) { 
			String mensagem = "Não foi possível editar o vinho: Preencha todos os campos obrigatórios.";
			return mensagem;
		}

		// Verifica se já existe vinho com este nome
		Vinho existente = dao.selecionarPorNome(nomeVinho);
		if( existente != null && existente.getIdVinho() != idVinho ) {
			String mensagem = "Não foi possível editar o vinho: Já existe outro vinho com este nome.";
			return mensagem;
		}

		// Recupera o vinho a editar
		existente = (Vinho)dao.selecionarPorId(idVinho);
		existente.setNomeVinho(nomeVinho);
		existente.setAnoVinho(anoVinho);
		existente.setCorVinho(corVinho);
		existente.setPrecoVinho(precoVinho);
		existente.setQtdEstoque(qtdEstoque);
		
		try {
			dao.atualizar(existente);
			String mensagem = "Vinho " + existente.getNomeVinho() + " atualizado com sucesso.";
			return mensagem;
		} catch( Exception e ) {
			e.printStackTrace();
			String mensagem = "Não foi possível editar o vinho";
			return mensagem;
		}
	}
	
	public static String apagarVinho( int idVinho ) {
		VinhoDAO dao = new VinhoDAO();
		
		try {
			dao.apagar(idVinho);
			String mensagem = "Vinho apagado com sucesso: ";
			return mensagem;
		} catch( Exception e ) {
			e.printStackTrace();
			String mensagem = "Não foi possível apagar o vinho: ";
			return mensagem;
		}
	}


	public static List<Vinho> consultarTodosVinhos() {
		VinhoDAO dao = new VinhoDAO();
		List<Vinho> lista = dao.selecionarTodos();
		return lista;
	}

	public static Vinho consultarVinhoPorId( int idVinho ) {
		VinhoDAO dao = new VinhoDAO();
		Vinho vinho = dao.selecionarPorId(idVinho);
		return vinho;
	}


	// Limpeza do BD

	public static void limparBD() {
		VinhoDAO vinhoDao = new VinhoDAO();
		List<Vinho> vinhos = vinhoDao.selecionarTodos();
		for (Vinho vinho : vinhos) {
			vinhoDao.apagar(vinho.getIdVinho());
		}		
		System.out.println("Vinhos apagados com sucesso!");
	}
	
	public static void popularBD() {
		VinhoDAO vinhoDao = new VinhoDAO();
		
		Vinho novo = new Vinho();
		novo.setNomeVinho("Santa Helena");
		novo.setAnoVinho(2016);
		novo.setCorVinho("Tinto");
		novo.setPrecoVinho(37.78);
		novo.setQtdEstoque(4);
		vinhoDao.inserir(novo);
		
		novo = new Vinho();
		novo.setNomeVinho("Santa Sara");
		novo.setAnoVinho(2015);
		novo.setCorVinho("Rose");
		novo.setPrecoVinho(22.78);
		novo.setQtdEstoque(3);
		vinhoDao.inserir(novo);
		
		novo = new Vinho();
		novo.setNomeVinho("Santa Maria");
		novo.setAnoVinho(2013);
		novo.setCorVinho("Branco");
		novo.setPrecoVinho(345.78);
		novo.setQtdEstoque(2);
		vinhoDao.inserir(novo);
	}
}
