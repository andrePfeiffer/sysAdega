package bd.utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BDVinhos {
	/**
	 * Cria uma nova tabela de vinhos, apagando tudo que existir na base
	 */
	public static void initDB() {
		try {
			String sql = "DROP TABLE IF EXISTS Vinho";	
			Connection con = new ConnectionFactory().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.execute();
//			
//			sql = "DROP TABLE IF EXISTS Uva";	
//			stmt = con.prepareStatement(sql);
//			stmt.execute();
			
//			sql = "CREATE TABLE Uva (idUva integer PRIMARY KEY NOT NULL, "
//					+ "nomeUva varchar(50));";
//			// colocar o relacionamento
//			stmt = con.prepareStatement(sql);
//			stmt.execute();				
//
//			// Se quiser criar manualmente as tabelas
//			sql = "CREATE TABLE Vinho (idVinho integer PRIMARY KEY NOT NULL, "
//					+ "nomeVinho varchar(50), ano integer, cor varchar(50), idUva integer, "
//					+ "FOREIGN KEY(idUva) REFERENCES Uva(idUva) );";
			
//			sql = "CREATE TABLE Vinho (idVinho integer PRIMARY KEY NOT NULL, "
//					+ "nomeVinho varchar(50), ano integer, cor varchar(50), preco real, qtdEstoque real"
//					+ ");";
//			stmt = con.prepareStatement(sql);
//			stmt.execute();	

			stmt.close();
			con.close();
			
			System.out.println("***** Tabelas criadas com sucesso");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void apagaTabelas() {
		try {
			String sql = "DROP TABLE IF EXISTS Uva";	
			Connection con = new ConnectionFactory().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.execute();
//			
			sql = "DROP TABLE IF EXISTS Vinho";	
			stmt = con.prepareStatement(sql);
			stmt.execute();			
			
			stmt.close();
			con.close();
			
			System.out.println("***** Tabelas apagadas com sucesso");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
