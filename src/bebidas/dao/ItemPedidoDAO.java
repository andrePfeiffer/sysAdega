package bebidas.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import bebidas.model.Cliente;
import bebidas.model.ItemPedido;
import bebidas.model.Pedido;
import bebidas.model.Vinho;

public class ItemPedidoDAO extends CommonsDAO {

	@Override
	public boolean apagar(int identificador) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<?> selecionarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object selecionarPorId(int identificador) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public List<ItemPedido> selecionarPorVinho(Vinho vinho) {
		List<?> resultadoObj = new ArrayList<>();
		List<ItemPedido> resultado = new ArrayList<ItemPedido>();
		EntityManagerFactory factory = HibernateUtil.getEntityManagerFactory();
		EntityManager manager = factory.createEntityManager();		
	    Query query = manager.createQuery("select i from ItemPedido i where i.vinho = :vinho");
	    query.setParameter("vinho", vinho);
	    resultadoObj = query.getResultList();
	    manager.close();
	    
	    for (Object itemPedido : resultadoObj) {
			if(itemPedido instanceof ItemPedido) resultado.add((ItemPedido) itemPedido);
		}
		return resultado;
	}

}
