package modle;

public class FileMaster {
	
	String name;
	String url;
	String description;
	int serial;
	double counter;
	String artist;
	
	FileMaster() {}
	
	public FileMaster(String _name, String _url,String _description)
	{
		setName(_name);
		setUrl(_url);
		setDescription(_description);
		serial=0;
		artist="";
	}

	public String getName()
	{
		return name;
	}

	public void setName(String word) {
		name = word;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String word) {
		url = word;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String word) {
		description = word;
	}

	public int getSerial()
	{
		return serial;
	}

	public void setSerial(int word) {
		serial = word;
	}

	public double getCounter()
	{
		return counter;
	}

	public void setCounter(double word) {
		counter = word;
	}
	
	public String getArtist()
	{
		return artist;
	}

	public void setArtist(String word) {
		artist = word;
	}

	
	public boolean equals(Object obj) {
		        if (this == obj) {
		            return true;
		        } else if (obj == null) {
		            return false;
		        } else if (obj instanceof FileMaster) {
		            FileMaster cust = (FileMaster) obj;
		            if ((cust.getName() == null && name == null) ||
		                (cust.getName().equals(name) && ((cust.getUrl() == null && url == null)
		                || cust.getUrl().equals(url)))) {
		                return true;
		            }
		        }
		        return false;
		    }

	public String toString() {
		return getName() + " " + getArtist();
	
	}

}
