
<%@page import="com.rays.pro4.controller.FollowUpCtl"%>
<%@page import="com.rays.pro4.controller.PrescriptionCtl"%>
<%@page import="com.rays.pro4.controller.PrescriptionCtl"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="Header.jsp"%>
	<form action="<%=ORSView.FOLLOWUP_CTL%>" method="post">
		
		<jsp:useBean id="bean" class="com.rays.pro4.Bean.FollowUpBean"
			scope="request"></jsp:useBean>

		<div align="center">

			<h1>
				<font color="navy"> <%
 if (bean != null && bean.getId() > 0) {
 %> Update <%
 } else {
 %> Add <%
 }
 %> Follow Up
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
					<th>Patient</th>
					<td><input type="text" name="patient"
						placeholder="Enter Patient Name" value="<%=DataUtility.getStringData(bean.getPatient())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("patient", request)%></font></td>
				</tr>
				<tr>
					<th>Doctor</th>
					<td><input type="text" name="doctor"
						placeholder="Enter doctor" value="<%=DataUtility.getStringData(bean.getDoctor())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("doctor", request)%></font></td>
				</tr>
				<tr>
					<th>Visit Date</th>
					<td><input type="text" id="udate" name="visitDate"
						placeholder="Select Date"
						value="<%=DataUtility.getDateString(bean.getVisitDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("visitDate", request)%></font></td>
				</tr>
				<tr>
					<th>Fees</th>
					<td><input type="text" name="fees"
						placeholder="Enter Fees" value="<%=DataUtility.getStringData(bean.getFees())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("fees", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<%
					if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=FollowUpCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=FollowUpCtl.OP_CANCEL%>">
						<%
						} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=FollowUpCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=FollowUpCtl.OP_RESET%>">
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