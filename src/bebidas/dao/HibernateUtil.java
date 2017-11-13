package bebidas.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

    public static EntityManagerFactory singleton;

    public HibernateUtil() {
    }

    public static EntityManagerFactory getEntityManagerFactory() { 
    	
    	
    	// persistenceMap pode ser usado para modificar o persistence.xml dinamicamente, podemos mudar o url do jdbc em runtime.
    	Map<String, String> persistenceMap = new HashMap<String, String>();
		persistenceMap.put("javax.persistence.jdbc.url", "jdbc:sqlite:C:/Users/LUIS/workspace/VinhosWeb/adega.db3");

		//TODO(imagal): Verificar se é possível conseguir o Path do projeto. O Tomcat aponta pra pasta do server, no caso o ${catalina_base}. No fim, cada um clona o projeto onde quer, não achei uma forma decente para capturar o diretório dinamicamente.
		// Path do diretório do TomCat
		System.out.println("Path do Tomcat: " + System.getProperty("catalina.home"));
		// Path do Server, aponta pro workspace, mas não para o projeto.
		System.out.println("Path do server: " + System.getProperty("catalina.base"));
		// Path onde o eclipse foi aberto.
		System.out.println("Path do eclipse que foi executado: " + System.getProperty("user.dir"));
		
        if (singleton == null) {
            try {
            	singleton = Persistence.createEntityManagerFactory("vinhos", persistenceMap);
            } catch (Throwable ex) {
                System.out.println("Erro ao inicar o Hibernate " + ex);
                throw new ExceptionInInitializerError(ex);
            }
            return singleton;
            
        } else {
            return singleton;
        }
    }
}