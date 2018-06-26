package modle;


import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import controller.Poster;



public class hibernateSearchEngine implements IhibernateSearchEngine  {

	@Override
	public List<FileMaster> SearchWord(String word,Hashtable<String,Poster> table) {
		// TODO Auto-generated method stub
		word=word.toLowerCase();
		List<FileMaster> result;
		SessionFactory factory = HibernateSessionFactory.getInstance();
		Session session = factory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("select fm from FileMaster as fm ,Indexer as idx where fm.serial=idx.file.serial and words= :word ");
		query.setParameter("word", word);
		result = (List<FileMaster>) query.list();
		//System.out.println(result.toString());
		session.getTransaction().commit();
		Poster p;
		if(table.containsKey(word)) 
		{
			p =table.get(word);
			result= new ArrayList( (List<FileMaster>)p.files);
		}
		return result;
	
		
	}

	@Override
	public void addFile(FileMaster name,Set< String > words,Hashtable<String,Poster> table) {
		// TODO Auto-generated method stub
		SessionFactory factory = HibernateSessionFactory.getInstance();
		Session session = factory.openSession();
		session.beginTransaction();
		session.save(name);
		session.getTransaction().commit();
		for(String word: words) {
			if(StopWords(word))
				addWords(word,name,table);
			}

	}

	@Override
	public void addWords(String word,FileMaster file,Hashtable<String,Poster> table) {
		// TODO Auto-generated method stub
		Indexer indx = new Indexer(word,file);
		SessionFactory factory = HibernateSessionFactory.getInstance();
		Session session = factory.openSession();
		session.beginTransaction();
		session.save(indx);
		session.getTransaction().commit();
		if(table.containsKey(word))
		{
			table.get(word).counter++;
			table.get(word).files.add(file);
		}
		else
		{
			table.put(word,new Poster());
			table.get(word).files.add(file);
		}
		
		
	}

	@Override
	public void deleteFile(String name,Hashtable<String,Poster> table) {
		// TODO Auto-generated method stub
		//List<Indexer> result;
		//Indexer indx;
		//FileMaster fm;
		int serial = 0;
		SessionFactory factory = HibernateSessionFactory.getInstance();
		Session session1 = factory.openSession();
		session1.beginTransaction();
		Query query = session1.createQuery("from FileMaster where name= :word ");
		query.setParameter("word", name);
		List<FileMaster> list = (List<FileMaster>) query.list();
		for(int i=0;i<list.size();i++) {
			serial = list.get(i).getSerial();}
		Session session = factory.openSession();
		session.beginTransaction();
		query = session.createQuery("delete Indexer as indx where indx.file.serial= :ser");
		query.setParameter("ser", serial);
		int result = query.executeUpdate();
		session.getTransaction().commit();
		session = factory.openSession();
		session.beginTransaction();
		query = session.createQuery("delete FileMaster where name= :word");
		query.setParameter("word", name);
		result = query.executeUpdate();
		session.getTransaction().commit();
		Set<String> e =table.keySet();
		for(String key : e)
		{
			Poster p  = table.get(key);
			for(int j=0; j<p.files.size();j++) 
			{
				FileMaster fm=(FileMaster) p.files.get(j);
				if(fm!=null) 
				{
					//System.out.println("Name in list:" + fm.getName());
					//System.out.println("Want to delete:" + name);
					name = name.toLowerCase();
					if(name.equals(fm.getName().toLowerCase())) 
					{
						p.files.remove(j);
						p.counter--;
					}
			
				}
				
			}
		}
		

	}

	@Override
	public List<FileMaster> allFiles() {
		// TODO Auto-generated method stub
		List<FileMaster> result;
		SessionFactory factory = HibernateSessionFactory.getInstance();
		Session session = factory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from FileMaster");
		result = (List<FileMaster>) query.list();
		session.getTransaction().commit();
		return result;
	}

	@Override
	public boolean StopWords(String word) {
		List<String> stopwords = new ArrayList<String>();
		String[] words = {"a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", "alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and", "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became", "because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps", "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves", "the"};
		for (String myword: words) {
			stopwords.add(myword);
		}
		if(stopwords.contains(word))
			return false;
		else
			return true;
	}

	public void InitHashTab(Hashtable<String,Poster> table)
	{
		List<FileMaster> result;
		SessionFactory factory = HibernateSessionFactory.getInstance();
		Session session = factory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from FileMaster");
		result = (List<FileMaster>) query.list();
		session.getTransaction().commit();
		for(int i=0 ; i<result.size();i++)
		{
			Session session2 = factory.openSession();
			session2.beginTransaction();
			query = session2.createQuery("from Indexer as indx where indx.file.serial= :ser ");
			query.setParameter("ser", result.get(i).getSerial());
			List<Indexer> words = (List<Indexer>) query.list();
			session2.getTransaction().commit();
			System.out.println(words.size());
			for(int j=0;j<words.size();j++)
			{
				if(table.containsKey(words.get(j).getWords()))
				{
					
					table.get(words.get(j).getWords()).counter++;
					table.get(words.get(j).getWords()).files.add(words.get(j).getFile());
				}
				else
				{
					table.put(words.get(j).getWords(),new Poster());
					table.get(words.get(j).getWords()).files.add(words.get(j).getFile());
					
				}
				
			}
		}
	}
}
