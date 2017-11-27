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

import bebidas.state.*;


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
	private State estadoAtual;
	
	@Transient
	private State pedidoAberto = new PedidoAberto(this);
	
	@Transient
	private State pedidoEmAndamento = new PedidoEmAndamento(this);
	
	@Transient
	private State pedidoEncerrado = new PedidoEncerrado(this);

	@Transient
	private State pedidoCancelado = new PedidoCancelado(this);
	
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
		this.setEstadoPedido(this.estadoAtual.getDescricaoEstadoPedido());
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

	public State getPedidoEmAndamento() {
		return pedidoEmAndamento;
	}

	public void setPedidoEmAndamento(State pedidoEmAndamento) {
		this.pedidoEmAndamento = pedidoEmAndamento;
	}

	public State getPedidoCancelado() {
		return pedidoCancelado;
	}

	public void setPedidoCancelado(State pedidoCancelado) {
		this.pedidoCancelado = pedidoCancelado;
	}

	public void setPedidoAberto(State pedidoAberto) {
		this.pedidoAberto = pedidoAberto;
	}

	public void setPedidoEncerrado(State pedidoEncerrado) {
		this.pedidoEncerrado = pedidoEncerrado;
	}
	
	// Métodos do State
	public String prepararPedido() {
		return estadoAtual.preparar();
	}
	
	public String encerrarPedido() {
		return estadoAtual.encerrar();
	}
	
	public String cancelarPedido() {
		return estadoAtual.cancelar();
	}
	

}
