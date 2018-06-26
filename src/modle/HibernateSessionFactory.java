package modle;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateSessionFactory {

	   private static SessionFactory instance = null;
	   /*
	    * private contractor
	    */
	   protected HibernateSessionFactory() {
	      // Exists only to defeat instantiation.
	   }
	   /*
	    * return instance
	    */
	   public static SessionFactory getInstance() {
	      if(instance == null) {
	         instance =  new AnnotationConfiguration().configure().buildSessionFactory();
	      }
	      return instance;
	   }
	
}
