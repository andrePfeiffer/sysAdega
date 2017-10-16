package bebidas.dao;

import java.util.List;

public interface GenericDAO {

	public void inserir( Object o );
	
	public boolean apagar( int identificador );
	
	public void atualizar( Object o );
	
	public List<?> selecionarTodos();
	
	public Object selecionarPorId( int identificador );
}
