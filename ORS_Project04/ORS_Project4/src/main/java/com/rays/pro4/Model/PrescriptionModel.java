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
import com.rays.pro4.Bean.PrescriptionBean;
import com.rays.pro4.Util.JDBCDataSource;



public class PrescriptionModel {

	public Integer nextPk() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_pres");
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}
		JDBCDataSource.closeConnection(conn);
		return pk + 1;
	}

	public void add(PrescriptionBean bean) throws Exception {

		int pk = nextPk();
		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("insert into st_pres values(?,?,?,?,?,?,?,?,?)");

		pstmt.setLong(1, pk);
		pstmt.setString(2, bean.getName());
		pstmt.setString(3, bean.getDecease());
		pstmt.setTimestamp(4, new Timestamp(bean.getDate().getTime()));
		pstmt.setDouble(5, bean.getCapacity());
		pstmt.setString(6, bean.getCreatedBy());
		pstmt.setString(7, bean.getModifiedBy());
		pstmt.setTimestamp(8, bean.getCreatedDatetime());
		pstmt.setTimestamp(9, bean.getModifiedDatetime());

		int i = pstmt.executeUpdate();

		JDBCDataSource.closeConnection(conn);

		System.out.println("Data Insreted = " + i);

	}

	public void update(PrescriptionBean bean) throws Exception {

		int pk = nextPk();
		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_pres set name=?, decease=?, date=?, capacity=?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

		pstmt.setString(1, bean.getName());
		pstmt.setString(2, bean.getDecease());
		pstmt.setTimestamp(3, new Timestamp(bean.getDate().getTime()));
		pstmt.setDouble(4, bean.getCapacity());
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

		PreparedStatement pstmt = conn.prepareStatement("delete from st_pres where id = ?");

		pstmt.setLong(1, id);

		int i = pstmt.executeUpdate();

		JDBCDataSource.closeConnection(conn);

		System.out.println("Data Deleted = " + i);
	}

	public PrescriptionBean findByPk(long id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select * from st_pres where id = ?");

		pstmt.setLong(1, id);

		ResultSet rs = pstmt.executeQuery();

		PrescriptionBean bean = null;

		while (rs.next()) {
			bean = new PrescriptionBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDecease(rs.getString(3));
			bean.setDate(rs.getDate(4));
			bean.setCapacity(rs.getDouble(5));
			bean.setCreatedBy(rs.getString(6));
			bean.setModifiedBy(rs.getString(7));
			bean.setCreatedDatetime(rs.getTimestamp(8));
			bean.setModifiedDatetime(rs.getTimestamp(9));
			
		}
		JDBCDataSource.closeConnection(conn);
		return bean;

	}

	public List search(PrescriptionBean bean, int pageNo, int pageSize) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		StringBuffer sql = new StringBuffer("select * from st_pres where 1=1");

		if (bean != null) {
			

			if (bean.getId() > 0) {
				sql.append(" and id =" + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}

			if (bean.getDecease() != null && bean.getDecease().length() > 0) {
				sql.append(" and decease like '" + bean.getDecease() + "%'");
			}
			if (bean.getDate() != null) {
				sql.append("and date like '" + bean.getDate() + "%'");
			}

			if (bean.getCapacity() > 0) {
				sql.append(" and capacity =" + bean.getCapacity());

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
			bean = new PrescriptionBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDecease(rs.getString(3));
			bean.setDate(rs.getDate(4));
			bean.setCapacity(rs.getDouble(5));
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
