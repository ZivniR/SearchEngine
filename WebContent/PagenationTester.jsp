<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%@ page import="modle.FileMaster"%>
<%@ page import="modle.hibernateSearchEngine"%>
<%@ page import= "java.util.List" %>

<%  
hibernateSearchEngine hsc = new hibernateSearchEngine();
String spageid=request.getParameter("page");  
int pageid=Integer.parseInt(spageid);   
int NumberPerPage=2;
int NumberShowed=0;
session.setAttribute("PerPage",NumberPerPage);
if(pageid==1){}  
else{
	NumberShowed = pageid * NumberPerPage;} 
List<FileMaster> list = (List<FileMaster>)hsc.allFiles();  
if(list.size()-NumberShowed-NumberPerPage <= 0)
	NumberPerPage=list.size(); 
out.print("<table border='0' cellpadding='4' width='60%'>");  

for(int i = NumberShowed; i<NumberShowed + NumberPerPage;i++ ){  
	if(i >= list.size())
		break;
	out.print("<tr><td><h3>"+"<a href=\""+list.get(i).getUrl()+"\">"+ list.get(i).getName()+"</a>"+"</h3>");
	  out.print("<h4>"+list.get(i).getArtist()+"<br>");
	  out.print(list.get(i).getDescription()+"</h4></td>" );} 
  
out.print("</table>");  
if(NumberPerPage != list.size()){
	out.print("<a href=\"PagenationTester.jsp?page=" + (pageid+1) + "\"" + ">" + " next " + "</a>");}
if(NumberShowed != 0){
	out.println("<a href=\"PagenationTester.jsp?page=" + (pageid-1) + "\"" + ">" + " back " + "</a>");}
%>  

</body>
</html>