<%@page import="com.rays.pro4.Model.VehicleRegistrationModel"%>
<%@page import="com.rays.pro4.controller.VehicleListCtl"%>
<%@page import="com.rays.pro4.Bean.VehicleRegistrationBean"%>
<%@page import="com.rays.pro4.Model.StudentModel"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.rays.pro4.Bean.StudentBean"%>
<%@page import="com.rays.pro4.controller.StudentListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title> Vehicle List</title>

<script src="<%=ORSView.APP_CONTEXT %>/js/jquery.min.js""></script>
<script src="<%=ORSView.APP_CONTEXT %>/js/Checkbox11.js"></script>


</head>
<body>

<jsp:useBean id="bean" class="com.rays.pro4.Bean.VehicleRegistrationBean" scope="request" ></jsp:useBean>

  <form action="<%=ORSView.VEHICLE_LIST_CTL%>" method="post">
    <%@include file="Header.jsp"%>
   
    <center>
    
     <div align="center">
	        <h1>Vehicle List</h1>
            <h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
            <font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>
     </div>
     
    
     
       <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<VehicleRegistrationBean> it = list.iterator();

	       			if(list.size() !=0){
                    %>
            
            <table width="100%" align="center">
                 <tr>
                 <td align="center">
                 <label> Owner Name:</label> 
                 	<input type="text" name="ownerName" placeholder="Enter Owner Name" Size= "25" value="<%=ServletUtility.getParameter("ownerName", request)%>">
					&nbsp;
                 	<label>City:</label> 
    	             <input type="text" name="city" placeholder="Enter City" Size= "25" value="<%=ServletUtility.getParameter("city", request)%>">
                    &nbsp;
                 	<label>phoneNo:</label> 
    	             <input type="text" name="phoneNo" placeholder="Enter phoneNo" Size= "25" value="<%=ServletUtility.getParameter("phoneNo", request)%>">
                    &nbsp; 
                     
        	         <input type="submit" name="operation" value="<%=VehicleListCtl.OP_SEARCH%>">
        	       
        	         <input type="submit" name="operation" value="<%=VehicleListCtl.OP_RESET%>">
        	         
                 </td>
                </tr>
            </table>
            
            <br>
            
            <table border="1" width="100%" align="center" cellpadding=6px cellspacing=".2">
                <tr style="background: skyblue">
                <th><input type="checkbox" id="select_all" name="select">Select All.</th>
                
                <th>S No.</th>
               
                <th>Owner Name.</th>
                <th>Address.</th>
                <th>State.</th>
                <th>City.</th>
                <th>Phone No.</th>
                
                <th>Edit</th>
                </tr>
                
                <%
                	while(it.hasNext())
                	{
                	 bean = it.next();
                %>
                
                
                
       <tr align="center">
           	<td><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId() %>">
                    <td><%=index++%></td>
                  
                    <td><%=bean.getOwnerName()%></td>
                    <td><%=bean.getAddress()%></td>
                    <td><%=bean.getState()%></td>
                    <td><%=bean.getCity()%></td>
                    <td><%=bean.getPhoneNo()%></td>
                    <td><a href="VehicleCtl?id=<%=bean.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>
            <table width="100%">
                <tr>
                <%if(pageNo == 1){ %>
                    <td><input type="submit" name="operation" disabled="disabled" value="<%=VehicleListCtl.OP_PREVIOUS%>">
       				<%}else{ %>
       				<td><input type="submit" name="operation"  value="<%=VehicleListCtl.OP_PREVIOUS%>"></td>
       				<%} %>		
                     
                     <td><input type="submit" name="operation" value="<%=VehicleListCtl.OP_DELETE%>"> </td>
                    <td> <input type="submit" name="operation" value="<%=VehicleListCtl.OP_NEW%>"></td>
                    
                  <% VehicleRegistrationModel model = new VehicleRegistrationModel();                  
                  %>  
                  <% if(list.size() < pageSize || model.nextPK()-1 == bean.getId()){ %>
                  <td align="right"> <input type="submit" name="operation" disabled="disabled" value="<%=VehicleListCtl.OP_NEXT%>"></td>
  					<%}else{ %>                   
  				  <td align="right"> <input type="submit" name="operation"  value="<%=VehicleListCtl.OP_NEXT%>"></td>
   					<%} %>                 
                    
                </tr>
            </table>
            		<%}if(list.size() == 0){ %>
            		<td align="center"><input type="submit" name="operation" value="<%=VehicleListCtl.OP_BACK%>"></td>	
            		<% } %>
            	
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> 
            <input type="hidden" name="pageSize" value="<%=pageSize%>">
        </form>
        </br>
         </br>
         </br>
                   </br>
                   </br>
                   </br>
    </center>

 <%@include file="Footer.jsp"%>
 </body>
</html>