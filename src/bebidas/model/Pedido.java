package bebidas.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import bebidas.state.PedidoAberto;
import bebidas.state.PedidoEncerrado;
import bebidas.state.State;

@Entity
@Table(name="Pedido")
public class Pedido {
	
	@Id
	@Column(name = "idPedido", nullable = false)
	@GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
	private int idPedido;
	
	@JoinColumn(name = "idCliente", referencedColumnName = "idCliente", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private Cliente cliente;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ItemPedido", 
				joinColumns = {@JoinColumn(name = "idPedido")},
				inverseJoinColumns = {@JoinColumn(name = "idVinho")}
				)
    private List<Vinho> vinhos = new ArrayList<>();

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="idPedido")
    private List<ItemPedido> itensPedido = new ArrayList<>();

	@Column(name = "dtPedido")
	private Date dtPedido;
	
	@Column(name = "dtEncerramento")
	private Date dtEncerramento;
	
	@Column(name = "valorTotal")
	private double valorTotal;
	
	@Column(name = "estadoPedido")
	private String estadoPedido;
	
	@Transient
	private String estadoAtualDoPedido;
	
	@Transient
	private State estadoAtual;
	
	@Transient
	private State pedidoAberto = new PedidoAberto();
	
	@Transient
	private State pedidoEncerrado = new PedidoEncerrado();
	
	public List<Vinho> getVinhos() {
		return vinhos;
	}

	public void setVinhos(List<Vinho> vinhos) {
		this.vinhos = vinhos;
	}

	public List<ItemPedido> getItensPedido() {
		return itensPedido;
	}

	public void setItensPedido(List<ItemPedido> itensPedido) {
		this.itensPedido = itensPedido;
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDtPedido() {
		return dtPedido;
	}

	public void setDtPedido(Date dtPedido) {
		this.dtPedido = dtPedido;
	}

	public Date getDtEncerramento() {
		return dtEncerramento;
	}

	public void setDtEncerramento(Date dtEncerramento) {
		this.dtEncerramento = dtEncerramento;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public State getEstadoAtual() {
		return estadoAtual;
	}
	
	public void setEstadoPedido(String estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	public void setEstadoAtual(State estadoAtual) {
		this.estadoAtual = estadoAtual;
	}

	public State getPedidoAberto() {
		return pedidoAberto;
	}

	public State getPedidoEncerrado() {
		return pedidoEncerrado;
	}

	public String getEstadoPedido() {
		return estadoPedido;
	}

	public String getEstadoAtualDoPedido() {
		return estadoAtual.estadoPedido();
	}

}
