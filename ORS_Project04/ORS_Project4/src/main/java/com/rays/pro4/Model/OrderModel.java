package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.Result;
import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Util.JDBCDataSource;



public class OrderModel {

	public Integer nextPk() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_order");
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}
		JDBCDataSource.closeConnection(conn);
		return pk + 1;
	}

	public void add(OrderBean bean) throws Exception {

		int pk = nextPk();
		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("insert into st_order values(?,?,?,?,?,?,?,?,?)");

		pstmt.setLong(1, pk);
		pstmt.setInt(2, bean.getQuantity());
		pstmt.setString(3, bean.getProduct());
		pstmt.setTimestamp(4, new Timestamp(bean.getDate().getTime()));
		pstmt.setDouble(5, bean.getAmount());
		pstmt.setString(6, bean.getCreatedBy());
		pstmt.setString(7, bean.getModifiedBy());
		pstmt.setTimestamp(8, bean.getCreatedDatetime());
		pstmt.setTimestamp(9, bean.getModifiedDatetime());

		int i = pstmt.executeUpdate();

		JDBCDataSource.closeConnection(conn);

		System.out.println("Data Insreted = " + i);

	}

	public void update(OrderBean bean) throws Exception {

		int pk = nextPk();
		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_order set quantity=?, product=?, date=?, amount=?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

		pstmt.setInt(1, bean.getQuantity());
		pstmt.setString(2, bean.getProduct());
		pstmt.setTimestamp(3, new Timestamp(bean.getDate().getTime()));
		pstmt.setDouble(4, bean.getAmount());
		pstmt.setString(5, bean.getCreatedBy());
		pstmt.setString(6, bean.getModifiedBy());
		pstmt.setTimestamp(7, bean.getCreatedDatetime());
		pstmt.setTimestamp(8, bean.getModifiedDatetime());
		pstmt.setLong(9, bean.getId());

		int i = pstmt.executeUpdate();

		JDBCDataSource.closeConnection(conn);

		System.out.println("Data Updated = " + i);

	}

	public void delete(long id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("delete from st_order where id = ?");

		pstmt.setLong(1, id);

		int i = pstmt.executeUpdate();

		JDBCDataSource.closeConnection(conn);

		System.out.println("Data Deleted = " + i);
	}

	public OrderBean findByPk(long id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select * from st_order where id = ?");

		pstmt.setLong(1, id);

		ResultSet rs = pstmt.executeQuery();

		OrderBean bean = null;

		while (rs.next()) {
			bean = new OrderBean();
			bean.setId(rs.getLong(1));
			bean.setQuantity(rs.getInt(2));
			bean.setProduct(rs.getString(3));
			bean.setDate(rs.getTimestamp(4));
			bean.setAmount(rs.getDouble(5));
			bean.setCreatedBy(rs.getString(6));
			bean.setModifiedBy(rs.getString(7));
			bean.setCreatedDatetime(rs.getTimestamp(8));
			bean.setModifiedDatetime(rs.getTimestamp(9));
			
		}
		JDBCDataSource.closeConnection(conn);
		return bean;

	}

	public List search(OrderBean bean, int pageNo, int pageSize) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		StringBuffer sql = new StringBuffer("select * from st_order where 1=1");

		if (bean != null) {
			if (bean.getQuantity() > 0) {
				sql.append(" and quantity =" + bean.getQuantity());
			}

			if (bean.getId() > 0) {
				sql.append(" and id =" + bean.getId());
			}

			if (bean.getProduct() != null && bean.getProduct().length() > 0) {
				sql.append(" and product like '" + bean.getProduct() + "%'");
			}
			if (bean.getDate() != null) {
				sql.append("and date like '" + bean.getDate() + "%'");
			}

			if (bean.getAmount() > 0) {
				sql.append(" and amount =" + bean.getAmount());

			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		System.out.println("sql = " + sql.toString());

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();

		List list = new ArrayList();

		while (rs.next()) {
			bean = new OrderBean();
			bean.setId(rs.getLong(1));
			bean.setQuantity(rs.getInt(2));
			bean.setProduct(rs.getString(3));
			bean.setDate(rs.getTimestamp(4));
			bean.setAmount(rs.getDouble(5));
			bean.setCreatedBy(rs.getString(6));
			bean.setModifiedBy(rs.getString(7));
			bean.setCreatedDatetime(rs.getTimestamp(8));
			bean.setModifiedDatetime(rs.getTimestamp(9));
			list.add(bean);

		}
		JDBCDataSource.closeConnection(conn);
		return list;

	}
	public List list() throws Exception {
		return search(null, 0, 0);
	}
}
