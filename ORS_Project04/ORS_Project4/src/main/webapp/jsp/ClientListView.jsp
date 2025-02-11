<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Model.ClientModel"%>
<%@page import="com.rays.pro4.controller.ClientListCtl"%>
<%@page import="com.rays.pro4.Bean.ClientBean"%>
<%@page import="com.rays.pro4.Model.DoctorModel"%>
<%@page import="com.rays.pro4.controller.DoctorListCtl"%>
<%@page import="com.rays.pro4.Bean.DoctorBean"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Model.PrescriptionModel"%>
<%@page import="com.rays.pro4.controller.PrescriptionListCtl"%>
<%@page import="com.rays.pro4.Bean.PrescriptionBean"%>
<%@page import="com.rays.pro4.Model.OrderModel"%>
<%@page import="com.rays.pro4.controller.OrderListCtl"%>
<%@page import="com.rays.pro4.Bean.OrderBean"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>


<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Client List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>

 <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $( function() {
    $( "#udate" ).datepicker({
      changeMonth: true,
      changeYear: true,
	  yearRange:'1980:2020',
    });
  } );
  </script>


</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ClientBean"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.CLIENT_LIST_CTL%>" method="post">
		<%@include file="Header.jsp"%>
		
		<%
				List<ClientBean> clist = (List<ClientBean>) request.getAttribute("cList");

			System.out.println("clist"+clist);
				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>

		<center>

			<div align="center">
				<h1>Client List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>
			</div>

			

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<ClientBean> it = list.iterator();

				if (list.size() != 0) {
			%>

			<table width="100%" align="center">
				<tr>
					<td align="center">
					<label> Full Name:</label> <input
						type="text" name="fullName" placeholder="Enter Full Name" Size="25"
						value="<%=ServletUtility.getParameter("fullName", request)%>">&nbsp;
						
						<label>Date </label><input type="text" id="udate" name="date"
						placeholder="Select Date"
						value="<%=DataUtility.getDateString(bean.getDate())%>">
						&nbsp;
						
						<label>Phone </label><input type="text" name="phoneNo"
						placeholder="Enter phoneNo" value="<%=DataUtility.getStringData(bean.getPhoneNo())%>">
						
						&nbsp; <label> Illness :</label> 
                 	<%=HTMLUtility.getList("illness", String.valueOf(bean.getIllness()), clist) %>


						&nbsp; <input type="submit" name="operation"
						value="<%=ClientListCtl.OP_SEARCH%>"> <input type="submit"
						name="operation" value="<%=ClientListCtl.OP_RESET%>"></td>
				</tr>
			</table>

			<br>

			<table width="100%" align="center">

				<br>

				<table border="1" width="100%" align="center" cellpadding=6px
					cellspacing=".2">
					<tr style="background: skyblue">
						<th><input type="checkbox" id="select_all" name="select"></th>
						<th>S No.</th>
						<th>Full Name</th>
						<th>Date</th>

						<th>PhoneNo</th>
						<th>Illness</th>

						<th>Edit</th>
					</tr>

					<%
						while (it.hasNext()) {
								bean = it.next();
					%>

					<tr align="center">
						<td><input type="checkbox" class="selectall" name="ids"
							value="<%=bean.getId()%>">
						<td><%=index++%></td>
						<td><%=bean.getFullName()%></td>
						<td><%=bean.getDate()%></td>
						<td><%=bean.getPhoneNo()%></td>
						<td><%=bean.getIllness()%></td>

						<td><a href="ClientCtl?id=<%=bean.getId()%>">Edit</a></td>
					</tr>
					<%
						}
					%>
				</table>

				<br>
				<table width="100%">
					<tr>
						<%
							if (pageNo == 1) {
						%>
						<td><input type="submit" name="operation" disabled="disabled"
							value="<%=ClientListCtl.OP_PREVIOUS%>"> <%
 	} else {
 %>
						<td><input type="submit" name="operation"
							value="<%=ClientListCtl.OP_PREVIOUS%>"></td>
						<%
							}
						%>

						<td><input type="submit" name="operation"
							value="<%=ClientListCtl.OP_DELETE%>"></td>
						<td><input type="submit" name="operation"
							value="<%=ClientListCtl.OP_NEW%>"></td>


						<td align="right"><input type="submit" name="operation"
							value="<%=ClientListCtl.OP_NEXT%>"
							<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>


					</tr>
				</table>
				<%
					}
					if (list.size() == 0) {
				%>
				<td align="center"><input type="submit" name="operation"
					value="<%=ClientListCtl.OP_BACK%>"></td>
				<%
					}
				%>

				<input type="hidden" name="pageNo" value="<%=pageNo%>">
				<input type="hidden" name="pageSize" value="<%=pageSize%>">
				</form>
				</center>

				<%@include file="Footer.jsp"%>
</body>
</html>