<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Style-Type" content="text/css">
<title>SearchPage</title>
<link rel="stylesheet" type="text/css" href="searcher.css" >
<style type="text/css">
<link rel="stylesheet" href="searcher.css">
</style>
</head>
<body>
<div style=float:right>
<a href="ClientHelp.html" target="_blank" > <button>Help</button> </a> </div>
<br>
	
<%@ page import="modle.hibernateSearchEngine"%>
<%@ page import="modle.FileMaster"%>
<%@ page import= "java.util.List" %>
<h1>Z I V N I R</h1>
<form action="ManagerPanel" method="post" name="search" enctype="multipart/form-data" class="form-wrapper">
    <h4>  search word:
    <INPUT TYPE="HIDDEN" NAME="User" VALUE="client">
    <INPUT TYPE="HIDDEN" NAME="FormName" VALUE="search">
    <input type="text" placeholder="write here"  name="words"/>
    <input type="submit" name="commit" value="find">
    </h4>
    </form>
<%List<FileMaster> list = (List<FileMaster>)session.getAttribute("SearchWord");
  //System.out.println(word);
  //hibernateSearchEngine hsc = new hibernateSearchEngine();
  //List<FileMaster> list = (List<FileMaster>)hsc.SearchWord(word);
  if(list==null){}
  else{
 
	  String spageid=request.getParameter("page");  
	  int pageid=Integer.parseInt(spageid);   
	  int NumberPerPage=4;
	  int NumberShowed=0;
	  session.setAttribute("PerPage",NumberPerPage);
	  if(pageid==1){}  
	  else{
	  	NumberShowed = pageid * NumberPerPage;}   
	  if(list.size()-NumberShowed-NumberPerPage <= 0)
	  	NumberPerPage=list.size(); 
	  out.print("<table border='0' cellpadding='4' width='60%' align='center'>");  

	  for(int i = NumberShowed; i<NumberShowed + NumberPerPage;i++ ){  
	  	if(i >= list.size())
	  		break;
	  	out.print("<tr><td><h3>"+"<a href=\""+list.get(i).getUrl()+"\" target= \" _blank \" >"+ list.get(i).getName()+"</a>"+"</h3>");
	  	  out.print("<h4>"+list.get(i).getArtist()+"<br>");
	  	  out.print(list.get(i).getDescription()+"</h4></td>" );} 
	    
	  out.print("</table>");  
	  if(NumberShowed != 0){
	  	out.println("<a href=\"SearchPage.jsp?page=" + (pageid-1) + "\"" + ">" + "<button>Back</button> " + "</a>");}
	  	out.print(" &nbsp;");
	  if(NumberPerPage != list.size()){
	  	out.println("<a href=\"SearchPage.jsp?page=" + (pageid+1) + "\"" + ">" + "<button>Next</button>" + "</a>");}

	  
  }%>
 
 <a href="./Manager.jsp?page=1">
   	<button>Manager</button>
	</a>

</body>
</html>