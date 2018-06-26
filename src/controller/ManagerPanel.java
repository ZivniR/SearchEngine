package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.apache.tomcat.jni.File;

import modle.FileMaster;
import modle.hibernateSearchEngine;


@ WebServlet("/ManagerPanel")
@MultipartConfig
public class ManagerPanel extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public Hashtable<String,Poster> table;
	hibernateSearchEngine hsc1;
	boolean notflag=true;
	
	public ManagerPanel() {
		super();
		table =new Hashtable<String,Poster>();
		hsc1 = new hibernateSearchEngine();
		hsc1.InitHashTab(table);
	}
	
	@SuppressWarnings("unused")
	private static String getSubmittedFileName(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}
	
	public void doPost (HttpServletRequest request, HttpServletResponse response )throws ServletException, IOException {
		String step = request.getParameter("FormName");
		hibernateSearchEngine hsc = new hibernateSearchEngine();
		switch(step) {
			case "add":{
				String word = null;
				Set< String > words = new TreeSet<String>(); 
				List<Part> fileParts = request.getParts().stream().filter(part -> "files".equals(part.getName())).collect(Collectors.toList()); // Retrieves <input type="file" name="file" multiple="true">

				for (Part filePart : fileParts) {
					String fileName = Paths.get(getSubmittedFileName(filePart)).getFileName().toString(); // MSIE fix.
					InputStream fileContent = filePart.getInputStream(); 
					word = filePart.getHeader("content-disposition");
					String []parts=word.split(";");
					word=parts[2].substring(11, parts[2].length()-1); 
					System.out.println(word.length());
					if(word.length()==0) {
					    break;
					}
					BufferedReader br =
							new BufferedReader(
									new FileReader(word));
				                 	// {sorted,unique}
					BufferedReader br1 =
							new BufferedReader(
									new FileReader(word));
					fileName = br.readLine();
					String art = br.readLine();
					System.out.println(art + fileName);
					StreamTokenizer st = new StreamTokenizer( br );
						st.lowerCaseMode(true);
						while( st.nextToken() != StreamTokenizer.TT_EOF ) {
							if( st.ttype == StreamTokenizer.TT_WORD ) {
								words.add( st.sval );}
						}
						int counter = 12;
						String description = "";
						int readler=0;
						int linecounter=3;
						char Achar;
						
						while(readler != -1) {
							 if(linecounter ==0) {
								readler = fileContent.read();
								Achar = (char)readler;
								description += Achar;
								if (readler == 32)
									counter--;
								if(counter == 0)
									break;
								}
							 else {
								readler = fileContent.read();
								if(readler == 10)
									linecounter--;
								}
							 
						}
						filePart = request.getPart("files"); // Retrieves <input type="file" name="file">
						//fileName = Paths.get(getSubmittedFileName(filePart)).getFileName().toString();
						response.setContentType("text/html");  
						out = response.getWriter();	     
						List<FileMaster> lfm = hsc.allFiles();
						boolean flagfm = false;
						for(int q=0;q<lfm.size();q++)
							if(lfm.get(q).getArtist().equals(art) && lfm.get(q).getName().equals(fileName))
								flagfm=true;
						if(flagfm == false) {
							FileMaster fm1 = new FileMaster(fileName,word,description);
							fm1.setArtist(art);
							if(fileName !=null)
								hsc.addFile(fm1, words,table);
						}
						br.close();
						words.clear();
				}
				RequestDispatcher rd=request.getRequestDispatcher("Manager.jsp?page=1");  
			    rd.forward(request,response);  
				break;
			}
			case "search":{
				int size = 0;
				HttpSession session = request.getSession(true); 
				String word = request.getParameter("words");
				  String[] list = word.split("\\(");
		          List<FileMaster> left = new ArrayList<FileMaster>();
		          List<FileMaster> right = new ArrayList<FileMaster>();
		          List<FileMaster> result = new ArrayList<FileMaster>();
		          for(int i = 0 ; i < list.length ; i++) {
		              list[i] = list[i].replace(")", "");
		              System.out.println(list[i]);
		          
		          }
		          for(int i=list.length-1; i>-1;i--) {
		        	 String []str= list[i].split("\\s");
		        	 size = str.length;
		        	 for (int j=0;j<size;j++) {
		        		 switch(str[j]) {
		        		 case "AND":{
		        			if(j+1<size)
		        				right = hsc.SearchWord(str[j+1],table);
		        			 if(left.isEmpty())
			        			 {left = result;
			        			 result = new ArrayList<FileMaster>();}
			        		 if(right.isEmpty())
			        			 {right = result;
			        			 result = new ArrayList<FileMaster>();}
		        			for(int x=0; x<right.size();x++) {
		        				if(left.contains(right.get(x))) {
		        					result.add(right.get(x));
		        					}
		        				}
		        		 	left.clear();
		        		 	right.clear();
		        			j++;
		        		 	break;
		        		 	}
		        		 case "OR":{
		        			 if(j+1<size)
		        				 right = hsc.SearchWord(str[j+1],table);
		        			 if(left.isEmpty())
		        			 	{left = result;
		        			 	result = new ArrayList<FileMaster>();}
		        			 if(right.isEmpty())
		        				 {right = result;
		        				 result = new ArrayList<FileMaster>();}
			        		 result.addAll(right);
		        			 result.addAll(left);
		        			 for (int x=0; x<result.size();x++)
		        				 for (int y=0; y<result.size();y++)
		        					 if(x!=y)
		        						if(result.get(x).getSerial() == result.get(y).getSerial())
		        							result.remove(y);
		        			 left.clear();
		        			 right.clear();
		        			 j++;
		        			 break;
		        		 	}
		        		 case "NOT":{
		        			 List<FileMaster> temp= new  ArrayList<FileMaster>();
		        			 if(left.isEmpty()) {
		        				 notflag=false;
		        				 left = hsc.SearchWord(str[j+1],table);
		        			 	 List<FileMaster> all = hsc.allFiles();
		        			 	 for(int x=0; x<all.size();x++) {
		        			 		 if(left.contains(all.get(x))) {}
		        			 		 else {
		        			 			 	temp.add(all.get(x));
		        						  }
		        			 	 	}
		        			 	left = temp; 
		        			 	}
		        		     else {
		        				 right = hsc.SearchWord(str[j+1],table);
		        			 	 List<FileMaster> all = hsc.allFiles();
		        			 	 for(int x=0; x<all.size();x++) {
		        			 		 if(right.contains(all.get(x))) {}
		        			 		 else {
		        			 			 	temp.add(all.get(x));
		        						  }
		        			 	 	}
		        			 	right = temp; 
		        			 	}
		        		 	j++;
		        		 	break;
		        		 	}
		        		 default:{
		        			 if(left.isEmpty())
		        				 left = hsc.SearchWord(str[j],table);
		        			 else {
				        			System.out.println("here!!!!");
				        			System.out.println(j+1 + "<" + size);
		        				 	if(j<size)
				        				right = hsc.SearchWord(str[j],table);
				        			 if(left.isEmpty())
					        			 {left = result;
					        			 result = new ArrayList<FileMaster>();}
					        		 if(right.isEmpty())
					        			 {right = result;
					        			 result = new ArrayList<FileMaster>();}
					        		 for(int x=0; x<right.size();x++) {
				        				if(left.contains(right.get(x))) {
				        					System.out.println(right.get(x).getName());
				        					result.add(right.get(x));
				        					}
				        				}
				        		 	left.clear();
				        		 	right.clear();
				        			j++;
		        			 }
		        				  
		        			 break;
		        		 	}
		        		 }
		        	 }
		          }
				if (list.length < 3)
					if(result.isEmpty())
						result=left;
				String worder="";
				String []strs = {""};
				//if((word.contains("AND"))||(word.contains("OR"))||(word.contains("NOT"))||(word.contains("("))||(word.contains(")")))
					worder=word.replace(")","").replace("(", "").replace("AND ", "").replace("OR ", "").replace("NOT ", "");
					strs = worder.split("\\s");
				/*else 
					  strs[0] = word;*/ 
				
				for(String str1 : strs)
					if(str1 == " ")
						str1=null;
				String dec = "";
				String temp = "";
				int counter =0;
				double idf = result.size();
				int files = hsc.allFiles().size();
				idf = idf / files;
				double []counters = new double[result.size()];
				for (int z=0;z<strs.length;z++)
					 {if(strs[z]!=null)
					 	strs[z]=strs[z].toLowerCase();}
				for(int q=0; q<result.size();q++) {
					for(int p=0; p<strs.length;p++) {	
						FileReader fr = new FileReader(result.get(q).getUrl());
						BufferedReader br = new BufferedReader(fr);
						int linecounter=0;
						br.readLine();
						br.readLine();
						while ((dec = br.readLine())!=null) {
							dec=dec.toLowerCase();
							String	[]dec2 = dec.split("\\s");
							counter += dec2.length;
							if(dec.contains(strs[p])) {
								counters[q] +=1;
								if(temp.contains(dec) || linecounter == 3 || strs[p].equals("")) {}
								else {	temp += dec + "<br>";
										linecounter++;} 
								}	
						} 	
					}
				counters[q] = counters[q] / counter;
				counter = 0;
				result.get(q).setCounter(counters[q]/idf);
				System.out.println(result.get(q).getName()+": " + result.get(q).getCounter());
				if(temp != "" )
					result.get(q).setDescription(temp);
				if (temp == "" && notflag)				
					result.remove(q);
				temp="";
				}
				Collections.sort(result, new Comparator<FileMaster>() {
				    @Override
				    public int compare(FileMaster lhs, FileMaster rhs) {
				        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
				        return lhs.getCounter() > rhs.getCounter() ? -1 : (lhs.getCounter() < rhs.getCounter()) ? 1 : 0;
				    }
				});
				System.out.println(strs[0]);
				session.setAttribute("SearchWord", result);
				String user = request.getParameter("User");
				System.out.println(user);
				if(user.contains("client"))
					{
					RequestDispatcher rd=request.getRequestDispatcher("SearchPage.jsp?page=1");  
			    	rd.forward(request,response);}
				else
					{RequestDispatcher rd=request.getRequestDispatcher("Manager.jsp?page=1");  
					rd.forward(request,response);}
			 
				break;
				
			}
			case "delete":{
				HttpSession session = request.getSession(true); 
				String word = request.getParameter("words");
				System.out.println(word);
				hsc.deleteFile(word,table);
				RequestDispatcher rd=request.getRequestDispatcher("Manager.jsp?page=1");  
			    rd.forward(request,response); 
			 
				break;
				
			}
	     
	}
		//out.close();
		}

	
	}
