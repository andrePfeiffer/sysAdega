package bebidas.dao;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import bebidas.model.Cliente;

public class ClienteDAO extends CommonsDAO {

	public boolean apagar(int identificador) {
		EntityManagerFactory factory = HibernateUtil.getEntityManagerFactory();
		EntityManager manager = factory.createEntityManager();
		
		Cliente resultado = manager.find(Cliente.class, identificador);
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
	public List<Cliente> selecionarTodos() {
		List<?> resultadoObj = new ArrayList<>();
		List<Cliente> resultado = new ArrayList<Cliente>();
		EntityManagerFactory factory = HibernateUtil.getEntityManagerFactory();
		EntityManager manager = factory.createEntityManager();		
	    Query query = manager.createQuery("from Cliente c order by idCliente");
	    resultadoObj = query.getResultList();
	    manager.close();
	    
	    for (Object cliente : resultadoObj) {
			if(cliente instanceof Cliente) resultado.add((Cliente) cliente);
		}
		return resultado;
	}

	@Override
	public Cliente selecionarPorId(int idCliente) {
		EntityManagerFactory factory = HibernateUtil.getEntityManagerFactory();
		EntityManager manager = factory.createEntityManager();
		Query query = manager.createQuery("select c from Cliente c where c.idCliente = :idCliente");
		query.setParameter("idCliente", idCliente);
		if( query.getResultList() != null && !query.getResultList().isEmpty()  ) {
			Cliente resultado = (Cliente)query.getResultList().get(0);
			manager.close();
			return resultado;
		} 
		manager.close();
		return null;
	}
	
	public Cliente selecionarPorNome( String nomeCliente ) {
		EntityManagerFactory factory = HibernateUtil.getEntityManagerFactory();
		EntityManager manager = factory.createEntityManager();
		Query query = manager.createQuery("select c from Cliente c where c.nomeCliente = :nomeCliente order by idCliente");
		query.setParameter("nomeCliente", nomeCliente);
		if( query.getResultList() != null && !query.getResultList().isEmpty()  ) {
			Cliente resultado = (Cliente)query.getResultList().get(0);
			manager.close();
			return resultado;
		} 
		manager.close();
		return null;
	}
	
	public Cliente selecionarPorCpf( String cpf ) {
		EntityManagerFactory factory = HibernateUtil.getEntityManagerFactory();
		EntityManager manager = factory.createEntityManager();
		Query query = manager.createQuery("select c from Cliente c where c.cpf = :cpf order by idCliente");
		query.setParameter("cpf", cpf);
		if( query.getResultList() != null && !query.getResultList().isEmpty()  ) {
			Cliente resultado = (Cliente)query.getResultList().get(0);
			manager.close();
			return resultado;
		} 
		manager.close();
		return null;
	}

}
