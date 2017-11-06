package bebidas.dao;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import bebidas.model.Pedido;

public class PedidoDAO extends CommonsDAO {

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
		List<?> resultadoObj = new ArrayList<>();
		List<Pedido> resultado = new ArrayList<Pedido>();
		EntityManagerFactory factory = HibernateUtil.getEntityManagerFactory();
		EntityManager manager = factory.createEntityManager();		
	    Query query = manager.createQuery("from Pedido p order by idPedido");
	    resultadoObj = query.getResultList();
	    manager.close();
	    
	    for (Object pedido : resultadoObj) {
			if(pedido instanceof Pedido) resultado.add((Pedido) pedido);
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
			manager.close();
			return resultado;
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
			List<?> resultado = (List<?>) query.getResultList();
			List<Pedido> resultadoPedido = new ArrayList<Pedido>();
			for (Object resultadoObject : resultado) {
				if(resultadoObject instanceof Pedido && resultadoObject != null) {
					resultadoPedido.add((Pedido)resultadoObject);
				}
			}
			manager.close();
			return resultadoPedido;
		} 
		manager.close();
		return null;
	}
	
}
