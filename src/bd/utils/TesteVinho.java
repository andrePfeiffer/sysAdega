package bd.utils;

import java.util.List;

import bebidas.dao.*;
import bebidas.model.*;


public class TesteVinho {

	public static void main(String[] args) {
		
		System.out.println("***** Esta aplicação está usando o Hibernate");
		
		// Apaga o BD, pois o Hibernate vai criar
		//BDVinhos.apagaTabelas();
		BDVinhos.initDB();
		
//		System.out.println("********** Cadastros **********");
//		Uva u1 = new Uva(1, "Cabernet Sauvignon");
//		Uva u2 = new Uva(2, "Merlot");
//		
//		VinhoManager.cadastrarUva(u1);
//		VinhoManager.cadastrarUva(u2);
//		
//		Vinho v1 = new Vinho(1, "Santa Helena", 2010, CorEnum.TINTO.getNomeCor(), u1);
//		Vinho v2 = new Vinho(2, "Santa Clara", 2010, CorEnum.BRANCO.getNomeCor(), u2);
//		Vinho v3 = new Vinho(3, "Santa Sara", 2012, CorEnum.BRANCO.getNomeCor(), u1);
//		Vinho v4 = new Vinho(4, "Santa Maria", 2012, CorEnum.TINTO.getNomeCor(), u2);
//		
//		VinhoManager.cadastrarVinho(v1);
//		VinhoManager.cadastrarVinho(v2);
//		VinhoManager.cadastrarVinho(v3);
//		VinhoManager.cadastrarVinho(v4);
//		
//		System.out.println("********** Consultas **********");
//		
//		VinhoManager.consultarTodosVinhos();
//		
//		VinhoManager.consultarVinhosPorCor(CorEnum.TINTO.getNomeCor());
//		VinhoManager.consultarVinhosPorCor(CorEnum.BRANCO.getNomeCor());
//		VinhoManager.consultarVinhosPorCor(CorEnum.ROSE.getNomeCor());
//		
//		VinhoManager.consultarVinhoPorId(1);
		
//		System.out.println("********** Limpeza do banco **********");
//		limparBD();
	}
	
	public static void limparBD() {
		VinhoDAO vinhoDao = new VinhoDAO();
		List<Vinho> vinhos = vinhoDao.selecionarTodos();
		for (Vinho vinho : vinhos) {
			vinhoDao.apagar(vinho.getIdVinho());
		}		
		System.out.println("Vinhos apagados com sucesso!");
	}

}
