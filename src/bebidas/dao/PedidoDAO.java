package bebidas.dao;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import bebidas.model.Cliente;
import bebidas.model.Pedido;

public class PedidoDAO extends CommonsDAO {
	
	public Pedido setEstado(Object pedidoObj) {
		Pedido pedido = null;
		
		if(pedidoObj instanceof Pedido) {
			pedido = (Pedido) pedidoObj;
			
			String estadoPedido = pedido.getEstadoPedido();
			switch (estadoPedido) {
			case "Aberto":
				pedido.setEstadoAtual(pedido.getPedidoAberto());
				break;
			case "Em andamento":
				pedido.setEstadoAtual(pedido.getPedidoEmAndamento());
				break;
			case "Encerrado":
				pedido.setEstadoAtual(pedido.getPedidoEncerrado());
				break;
			case "Cancelado":
				pedido.setEstadoAtual(pedido.getPedidoCancelado());
				break;
			}
		}
		
		return pedido;
	}
	
	

	public boolean apagar(int identificador) {
		EntityManagerFactory factory = HibernateUtil.getEntityManagerFactory();
		EntityManager manager = factory.createEntityManager();
		Pedido resultado = manager.find(Pedido.class, identificador);
		try {
			manager.getTransaction().begin();
			manager.remove( resultado );
			manager.getTransaction().commit();
		} catch( Exception e ){
			e.printStackTrace();
			return false;
		}
		manager.close();	
		return true;
	}

	@Override
	public List<Pedido> selecionarTodos() {
		List<?> resultadoObjList = new ArrayList<>();
		List<Pedido> resultado = new ArrayList<Pedido>();
		EntityManagerFactory factory = HibernateUtil.getEntityManagerFactory();
		EntityManager manager = factory.createEntityManager();		
	    Query query = manager.createQuery("from Pedido p order by idPedido");
	    resultadoObjList = query.getResultList();
	    manager.close();
	    
	    for (Object pedido : resultadoObjList) {
			Pedido pedidoCast = setEstado(pedido);
			resultado.add((Pedido) pedidoCast);
		}
		return resultado;
	}

	@Override
	public Pedido selecionarPorId(int idPedido) {
		EntityManagerFactory factory = HibernateUtil.getEntityManagerFactory();
		EntityManager manager = factory.createEntityManager();
		Query query = manager.createQuery("select p from Pedido p where p.idPedido = :idPedido");
		query.setParameter("idPedido", idPedido);
		if( query.getResultList() != null && !query.getResultList().isEmpty()  ) {
			Pedido resultado = (Pedido)query.getResultList().get(0);
			Pedido pedidoCast = setEstado(resultado);
			manager.close();
			return pedidoCast;
		} 
		manager.close();
		return null;
	}

	public List<Pedido> selecionarPorEstado(String estadoPedido) {
		EntityManagerFactory factory = HibernateUtil.getEntityManagerFactory();
		EntityManager manager = factory.createEntityManager();
		Query query = manager.createQuery("select p from Pedido p where p.estadoPedido = :estadoPedido");
		query.setParameter("estadoPedido", estadoPedido);
		if( query.getResultList() != null && !query.getResultList().isEmpty() ) {
			List<?> resultadoObjList = (List<?>) query.getResultList();
			List<Pedido> resultado = new ArrayList<Pedido>();
			for (Object pedido : resultadoObjList) {
				Pedido pedidoCast = setEstado(pedido);
				resultado.add(pedidoCast);
			}
			manager.close();
			return resultado;
		} 
		manager.close();
		return null;
	}
	
	public List<Pedido> selecionarPorCliente(Cliente cliente) {
		EntityManagerFactory factory = HibernateUtil.getEntityManagerFactory();
		EntityManager manager = factory.createEntityManager();
		Query query = manager.createQuery("select p from Pedido p where p.cliente = :cliente");
		query.setParameter("cliente", cliente);
		if( query.getResultList() != null && !query.getResultList().isEmpty() ) {
			List<?> resultadoObjList = (List<?>) query.getResultList();
			List<Pedido> resultado = new ArrayList<Pedido>();
			for (Object pedido : resultadoObjList) {
				Pedido pedidoCast = setEstado(pedido);
				resultado.add(pedidoCast);
			}
			manager.close();
			return resultado;
		} 
		manager.close();
		return null;
	}
	
}
