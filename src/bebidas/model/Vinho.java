package bebidas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Vinho")
public class Vinho {
	
	@Id
	@Column(name = "idVinho", nullable = false)
	@GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
	private int idVinho;
	
	@Column(name = "nomeVinho")
	private String nomeVinho;
	
	@Column(name = "anoVinho")
	private int anoVinho;
	
	@Column(name = "corVinho")
	private String corVinho;
	
	@Column(name = "precoVinho")
	private double precoVinho;
	
	@Column(name = "qtdEstoque")
	private int qtdEstoque;

	public int getIdVinho() {
		return idVinho;
	}

	public void setIdVinho(int idVinho) {
		this.idVinho = idVinho;
	}

	public String getNomeVinho() {
		return nomeVinho;
	}

	public void setNomeVinho(String nomeVinho) {
		this.nomeVinho = nomeVinho;
	}

	public int getAnoVinho() {
		return anoVinho;
	}

	public void setAnoVinho(int anoVinho) {
		this.anoVinho = anoVinho;
	}
	
	public String getCorVinho() {
		return corVinho;
	}
	public void setCorVinho(String corVinho) {
		this.corVinho = corVinho;
	}

	public double getPrecoVinho() {
		return precoVinho;
	}

	public void setPrecoVinho(double precoVinho) {
		this.precoVinho = precoVinho;
	}

	public int getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(int qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}	
}

