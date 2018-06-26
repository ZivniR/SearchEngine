<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Style-Type" content="text/css">
<title>Manager Panel</title>
<link rel="stylesheet" type="text/css" href="searcher.css" >
<style type="text/css">
<link rel="stylesheet" href="searcher.css">
</style>
</head>
<body>
<div style=float:right>
<a href="ManagerHelp.html" target="_blank" > <button>Help</button> </a> </div>
<br>
<%@ page import="modle.FileMaster"%>
<%@ page import="modle.hibernateSearchEngine"%>
<%@ page import= "java.util.List" %>
    <div align="center">
    <h1> Z I V N I R</h1>
     </div>
<%
hibernateSearchEngine hsc = new hibernateSearchEngine();
String spageid=request.getParameter("page");  
int pageid=Integer.parseInt(spageid);   
int NumberPerPage=2;
int NumberShowed=0;
session.setAttribute("PerPage",NumberPerPage);
if(pageid==1){}  
else{
	NumberShowed = pageid * NumberPerPage;
	NumberShowed = NumberShowed-NumberPerPage;} 
List<FileMaster> list1 = (List<FileMaster>)session.getAttribute("SearchWord");	
List<FileMaster> list = (List<FileMaster>)hsc.allFiles();
int count;
if(list1 ==null)
	count=0;
else 
	count = list1.size();


if(count >= 1)
	list=list1;
if(list.size()-NumberShowed-NumberPerPage <= 0)
	NumberPerPage=list.size(); 
out.print("<table border='0' cellpadding='4' width='60%' align='center'>");  
int roof= NumberShowed + NumberPerPage;
for(int i = NumberShowed; i<roof;i++ ){  
	if(i >= list.size())
		break;
	out.print("<tr><td><h3>"+"<a href=\""+list.get(i).getUrl()+"\" target= \" _blank \">"+ list.get(i).getName()+"</a>"+"</h3>");
	  out.print("<h4>"+list.get(i).getArtist()+"<br>");
	  out.print(list.get(i).getDescription()+"</h4></td>" );} 
  
out.print("</table>");  
if(NumberShowed != 0){
	out.println("<a href=\"Manager.jsp?page=" + (pageid-1) + "\"" + ">" + "<button>Back</button> " + "</a>");}
	out.print(" &nbsp;");
if(NumberPerPage != list.size()){
	out.println("<a href=\"Manager.jsp?page=" + (pageid+1) + "\"" + ">" + "<button>Next</button>" + "</a>");}

%> 
<form action="ManagerPanel" method="post" name="add" enctype="multipart/form-data" class="form-wrapper">
    <h4> add file: 
    <INPUT TYPE="HIDDEN" NAME="FormName" VALUE="add">
    <input type="file" multiple name="files">
    <input type="submit" name="commit" value="add">
    </h4>
    </form>

<form action="ManagerPanel" method="post" name="search" enctype="multipart/form-data" class="form-wrapper">
    <h4>  search word: 
     <INPUT TYPE="HIDDEN" NAME="User" VALUE="Manager">
    <INPUT TYPE="HIDDEN" NAME="FormName" VALUE="search">
    <input type="text" placeholder="write here"  name="words"/>
    <input type="submit" name="commit" value="find">
    </h4>
    </form>

<form action="ManagerPanel" method="post" name="delete" enctype="multipart/form-data" class="form-wrapper">
    <h4>  delete file: 
    <INPUT TYPE="HIDDEN" NAME="FormName" VALUE="delete">
    <input type="text" placeholder="write here"  name="words"/>
    <input type="submit" name="commit" value="delete">
    </h4>
    </form>
	<a href="./SearchPage.jsp?page=1">
   	<button>Client</button>
	</a>
   
</body>
</html>