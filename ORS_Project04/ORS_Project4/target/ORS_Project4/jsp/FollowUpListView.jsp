<%@page import="com.rays.pro4.Model.FollowUpModel"%>
<%@page import="com.rays.pro4.controller.FollowUpListCtl"%>
<%@page import="com.rays.pro4.Bean.FollowUpBean"%>
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
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title> FollowUp List</title>

<script src="<%=ORSView.APP_CONTEXT %>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT %>/js/Checkbox11.js"></script>

</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.FollowUpBean"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.FOLLOWUP_LIST_CTL%>" method="post">
		<%@include file="Header.jsp"%>

		<center>

			<div align="center">
				<h1>Follow Up List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>
			</div>


			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			List<FollowUpBean> freslist = (List<FollowUpBean>)request.getAttribute("FollowUpList"); 

			List list = ServletUtility.getList(request);
			Iterator<FollowUpBean> it = list.iterator();

			if (list.size() != 0) {
			%>

			<table width="100%" align="center">
				<tr>
					<td align="center"><label> Patient:</label> <input
						type="text" name="patient" placeholder="Enter Patient Name" Size="25"
						value="<%=ServletUtility.getParameter("patient", request)%>">&nbsp;

						
						&nbsp; <input type="submit" name="operation"
						value="<%=FollowUpListCtl.OP_SEARCH%>"> <input type="submit"
						name="operation" value="<%=FollowUpListCtl.OP_RESET%>"></td>
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
						<th>Patient</th>
						<th>Doctor</th>

						<th>Visit Date</th>
						<th>Fees</th>

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
						<td><%=bean.getPatient()%></td>
						<td><%=bean.getDoctor()%></td>
						<td><%=bean.getVisitDate()%></td>
						<td><%=bean.getFees()%></td>

						<td><a href="FollowUpCtl?id=<%=bean.getId()%>">Edit</a></td>
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
							value="<%=FollowUpListCtl.OP_PREVIOUS%>"> <%
 } else {
 %>
						<td><input type="submit" name="operation"
							value="<%=FollowUpListCtl.OP_PREVIOUS%>"></td>
						<%
						}
						%>

						<td><input type="submit" name="operation"
							value="<%=FollowUpListCtl.OP_DELETE%>"></td>
						<td><input type="submit" name="operation"
							value="<%=FollowUpListCtl.OP_NEW%>"></td>

						<%
						FollowUpModel model = new FollowUpModel();
						%>
						<%
						if (list.size() < pageSize || model.nextPk() - 1 == bean.getId()) {
						%>
						<td align="right"><input type="submit" name="operation"
							disabled="disabled" value="<%=FollowUpListCtl.OP_NEXT%>"></td>
						<%
						} else {
						%>
						<td align="right"><input type="submit" name="operation"
							value="<%=FollowUpListCtl.OP_NEXT%>"></td>
						<%
						}
						%>

					</tr>
				</table>
				<%
				}
				if (list.size() == 0) {
				%>
				<td align="center"><input type="submit" name="operation"
					value="<%=FollowUpListCtl.OP_BACK%>"></td>
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