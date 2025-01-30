
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
</head>
<body>
<%@ include file="Header.jsp"%>
	<form action="<%=ORSView.DOCTOR_CTL%>" method="post">
		
		<jsp:useBean id="bean" class="com.rays.pro4.Bean.DoctorBean"
			scope="request"></jsp:useBean>

		<div align="center">

			<h1>
				<font color="navy"> <%
 if (bean != null && bean.getId() > 0) {
 %> Update <%
 } else {
 %> Add <%
 }
 %> Doctor
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
					<th>Name</th>
					<td><input type="text" name="name"
						placeholder="Enter Name" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("quantity", request)%></font></td>
				</tr>
				<tr>
					<th>Dob:</th>
					<td><input type="text" id="udate" name="dob"
						placeholder="Select Dob"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				<tr>
					<th>Mobile</th>
					<td><input type="text" name="mobile"
						placeholder="Enter Mobile" value="<%=DataUtility.getStringData(bean.getMobile())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("mobile", request)%></font></td>
				</tr>
				
				<tr>
					<th>Expertise</th>
					<td><input type="text" name="expertise"
						placeholder="Enter Expertise" value="<%=DataUtility.getStringData(bean.getExpertise())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("expertise", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<%
					if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=DoctorCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=DoctorCtl.OP_CANCEL%>">
						<%
						} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=DoctorCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=DoctorCtl.OP_RESET%>">
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