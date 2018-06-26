package modle;

public class Indexer {
	String words;
	FileMaster file;
	int id;
	
	Indexer() {}

	public Indexer(String word, FileMaster _fm) {
		setWords(word);
		setFile(_fm);
	}
	
	public Indexer(Indexer index) {
		setWords(index.getWords());
		setFile(index.getFile());
		setId(index.getId());
		file.setSerial(-1);
	}
	
	public String getWords()
	{
		return words;
	}
	
	public void setWords(String word)
	{
		words = word;
	}

	public FileMaster getFile()
	{
		return file;
	}
	
	public void setFile(FileMaster word)
	{
		file = word;
	}

	public int getId()
	{
		return id;
	}
	
	public void setId(int word)
	{
		id = word;
	}

	public String toString() {
		
		return "words: " + getWords() + " file name: " + file.getName();
	}
	
}
