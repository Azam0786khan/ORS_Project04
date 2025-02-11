
<%@page import="com.rays.pro4.controller.ClientCtl"%>
<%@page import="com.rays.pro4.controller.DoctorCtl"%>
<%@page import="com.rays.pro4.controller.PrescriptionCtl"%>
<%@page import="com.rays.pro4.controller.PrescriptionCtl"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

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
<%@ include file="Header.jsp"%>
	<form action="<%=ORSView.CLIENT_CTL%>" method="post">
		
		<jsp:useBean id="bean" class="com.rays.pro4.Bean.ClientBean"
			scope="request"></jsp:useBean>

		<div align="center">

			<h1>
				<font color="navy"> <%
 if (bean != null && bean.getId() > 0) {
 %> Update <%
 } else {
 %> Add <%
 }
 %> Client
				</font>
			</h1>

			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>
			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>
				<tr>
					<th>Full Name</th>
					<td><input type="text" name="fullName"
						placeholder="Enter fullName" value="<%=DataUtility.getStringData(bean.getFullName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("fullName", request)%></font></td>
				</tr>
				<tr>
					<th>Date:</th>
					<td><input type="text" id="udate" name="date"
						placeholder="Select Date"
						value="<%=DataUtility.getDateString(bean.getDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("date", request)%></font></td>
				</tr>
				<tr>
					<th>Phone No</th>
					<td><input type="text" name="phoneNo"
						placeholder="Enter phoneNo" value="<%=DataUtility.getStringData(bean.getPhoneNo())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("phoneNo", request)%></font></td>
				</tr>
				
				<tr>
					<th>Illness</th>
					<td><input type="text" name="illness"
						placeholder="Enter Illness" value="<%=DataUtility.getStringData(bean.getIllness())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("illness", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<%
					if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=ClientCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=ClientCtl.OP_CANCEL%>">
						<%
						} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=ClientCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=ClientCtl.OP_RESET%>">
						<%
						}
						%>
				</tr>
			</table>
		</div>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>