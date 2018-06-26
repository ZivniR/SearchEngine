package modle;


import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import controller.Poster;




public interface IhibernateSearchEngine {
	
	public List<FileMaster> SearchWord(String word,Hashtable<String,Poster> table);
	
	public void addFile(FileMaster name, Set< String > words,Hashtable<String,Poster> table);
	
	public void addWords(String word,FileMaster file,Hashtable<String,Poster> table);
	
	public void deleteFile(String name,Hashtable<String,Poster> table);
	
	public List<FileMaster> allFiles();
	
	public boolean StopWords(String word ); 

}
