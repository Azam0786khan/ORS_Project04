
<%@page import="com.rays.pro4.controller.VehicleCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="javax.swing.text.html.HTML"%>
<%@page import="com.rays.pro4.Bean.CollegeBean"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.controller.StudentCtl"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title> Vehicle Registration Page</title>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $( function() {
    $( "#udate4" ).datepicker({
      changeMonth: true,
      changeYear: true,
	  yearRange:'1980:2020', 
	  
    });
  } );
  </script>


</head>
<body>
    <jsp:useBean id="bean" class="com.rays.pro4.Bean.VehicleRegistrationBean" scope="request"></jsp:useBean>
    	
	<form action="<%=ORSView.VEHICLE_CTL%>" method="post">
    <%@include file="Header.jsp"%>
    

    
    <center>
        <h1>
        	<%
        		if( bean != null && bean.getId()>0){
        	%> 
        	<tr><th><font>Update Vehicle</font></th></tr>
        	<% }else{%>
        	<tr><th><font>Add Vehicle</font></th></tr>
        	<% }%>
        </h1>
		
		<div>
		<h3><font style="color: green"><%=ServletUtility.getSuccessMessage(request) %></font></h1>
		<h3><font style="color: red"><%=ServletUtility.getErrorMessage(request) %></font>
		</h1>
		</div>
		
		<input type="hidden" name="id" value="<%=bean.getId()%>">
		<input type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedby" value="<%=bean.getModifiedBy()%>">
		<input type="hidden" name="createddatetime" value="<%=bean.getCreatedDatetime()%>">
		<input type="hidden" name="modifieddatetime" value="<%=bean.getModifiedDatetime()%>">

	<table>
	
				
	  <tr><th style="padding: 3px"></th></tr>    	
		
		<tr>
		<th align="left">OwnerName <span style="color: red">*</span> :</th>
		<td><input type="text" name="ownerName" placeholder="Enter Owner Name" size="26" value="<%=DataUtility.getStringData(bean.getOwnerName())%>"></td>
		<td style="position: fixed"><font  color="red"><%=ServletUtility.getErrorMessage("ownerName", request)%></font>
		</td>
		</tr>
		
		  <tr><th style="padding: 3px"></th></tr>    
		
		<tr>
		<th align="left" >Address <span style="color: red">*</span> :</th>
		<td><input type="text" name="address" placeholder="Enter addtress" size="26" value="<%=DataUtility.getStringData(bean.getAddress())%>"></td>
		<td style="position: fixed"><font  color="red"><%=ServletUtility.getErrorMessage("address", request)%></font>
		</td>
		</tr>
		  <tr><th style="padding: 3px"></th></tr>    
		
		<tr>
		<th align="left" >State <span style="color: red">*</span> :</th>
		<td><input type="text" name="state" placeholder="Enter state" size="26" value="<%=DataUtility.getStringData(bean.getState())%>"></td>
		<td style="position: fixed"><font  color="red"><%=ServletUtility.getErrorMessage("state", request)%></font>
		</td>
		</tr>
		  <tr><th style="padding: 3px"></th></tr>    
		
		<tr>
		<th align="left" >City <span style="color: red">*</span> :</th>
		<td><input type="text" name="city" placeholder="Enter city" size="26" value="<%=DataUtility.getStringData(bean.getCity())%>"></td>
		<td style="position: fixed"><font  color="red"><%=ServletUtility.getErrorMessage("city", request)%></font>
		</td>
		</tr>
		
		
		 
   
   <tr><th style="padding: 3px"></th></tr>    
	
		<tr>
		<th align="left">PhoneNo <span style="color: red">*</span> :</th>
		<td><input type="text" name="phoneNo" maxlength="10" placeholder="Enter Phone No" size="26" value="<%=DataUtility.getStringData(bean.getPhoneNo())%>"></td>
		<td style="position: fixed" ><font color="red"><%=ServletUtility.getErrorMessage("phoneNo", request)%></font>
		</td>
		</tr>
	
		
	
		  <tr><th style="padding: 3px"></th></tr>    
		

	<tr>
	<th></th>
		<%
		if(bean.getId() > 0){ %>
		<td>
		 &nbsp;  &emsp;
		<input type="submit" name="operation" value="<%=VehicleCtl.OP_UPDATE %>">
		 &nbsp;  &nbsp;
		<input type="submit" name="operation" value="<%=VehicleCtl.OP_CANCEL%>"></td>
		<%}else{ %>
		<td>
		 &nbsp;  &emsp;
		<input type="submit" name="operation" value="<%=VehicleCtl.OP_SAVE %>">
		 &nbsp;  &nbsp;
		<input type="submit" name="operation" value="<%=VehicleCtl.OP_RESET%>"></td>
	
		<%} %>
	</tr>
	
	</table>
</form>
</center>

<%@ include file = "Footer.jsp" %>
</body>
</html>
