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
<title> Order List</title>

<script src="<%=ORSView.APP_CONTEXT %>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT %>/js/Checkbox11.js"></script>

</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.OrderBean"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.ORDER_LIST_CTL%>" method="post">
		<%@include file="Header.jsp"%>

		<center>

			<div align="center">
				<h1>Order List</h1>
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
			Iterator<OrderBean> it = list.iterator();

			if (list.size() != 0) {
			%>

			<table width="100%" align="center">
				<tr>
					<td align="center"><label> Quantity:</label> <input
						type="text" name="quantity" placeholder="Enter quantity" Size="25"
						value="<%=ServletUtility.getParameter("quantity", request)%>">&nbsp;


						&nbsp; <label>Product:</label> <input type="text" name="product"
						placeholder="Enter product" Size="25"
						value="<%=ServletUtility.getParameter("product", request)%>">

						&nbsp; <input type="submit" name="operation"
						value="<%=OrderListCtl.OP_SEARCH%>"> <input type="submit"
						name="operation" value="<%=OrderListCtl.OP_RESET%>"></td>
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
						<th>Quantity</th>
						<th>Product</th>

						<th>Date</th>
						<th>Amount</th>

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
						<td><%=bean.getQuantity()%></td>
						<td><%=bean.getProduct()%></td>
						<td><%=bean.getDate()%></td>
						<td><%=bean.getAmount()%></td>

						<td><a href="OrderCtl?id=<%=bean.getId()%>">Edit</a></td>
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
							value="<%=OrderListCtl.OP_PREVIOUS%>"> <%
 } else {
 %>
						<td><input type="submit" name="operation"
							value="<%=OrderListCtl.OP_PREVIOUS%>"></td>
						<%
						}
						%>

						<td><input type="submit" name="operation"
							value="<%=OrderListCtl.OP_DELETE%>"></td>
						<td><input type="submit" name="operation"
							value="<%=OrderListCtl.OP_NEW%>"></td>

						<%
						OrderModel model = new OrderModel();
						%>
						<%
						if (list.size() < pageSize || model.nextPk() - 1 == bean.getId()) {
						%>
						<td align="right"><input type="submit" name="operation"
							disabled="disabled" value="<%=OrderListCtl.OP_NEXT%>"></td>
						<%
						} else {
						%>
						<td align="right"><input type="submit" name="operation"
							value="<%=OrderListCtl.OP_NEXT%>"></td>
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
					value="<%=OrderListCtl.OP_BACK%>"></td>
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