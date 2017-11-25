package bebidas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="ItemPedido")
public class ItemPedido {
	
	@Id
	@Column(name = "id", nullable = false)
	@GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
	private int id;
	
	@Column(name = "qtdVinho")
	private int qtdVinho;
	
	@Column(name = "valorTotalItem")
	private double valorTotalItem;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idPedido")
	private Pedido pedido;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="idVinho")
	private Vinho vinho;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQtdVinho() {
		return qtdVinho;
	}

	public void setQtdVinho(int qtdVinho) {
		this.qtdVinho = qtdVinho;
	}

	public double getValorTotalItem() {
		return valorTotalItem;
	}

	public void setValorTotalItem(double valorTotalItem) {
		this.valorTotalItem = valorTotalItem;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Vinho getVinho() {
		return vinho;
	}

	public void setVinho(Vinho vinho) {
		this.vinho = vinho;
	}
	
	
}
