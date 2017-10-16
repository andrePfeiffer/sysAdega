package bebidas.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public abstract class CommonsDAO implements GenericDAO {
	

	public void inserir(Object objeto) {
		EntityManagerFactory factory = HibernateUtil.getEntityManagerFactory();
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.persist(objeto);
		manager.getTransaction().commit();
		manager.close();
	}


	public abstract boolean apagar(int identificador);
	


	public void atualizar(Object objeto) {
		EntityManagerFactory factory = HibernateUtil.getEntityManagerFactory();
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.merge(objeto);
		manager.getTransaction().commit();
		manager.close();
	}


	public abstract List<?> selecionarTodos();


	public abstract Object selecionarPorId(int identificador);

}
