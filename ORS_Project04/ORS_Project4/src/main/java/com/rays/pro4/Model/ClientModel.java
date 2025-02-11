package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.ClientBean;
import com.rays.pro4.Bean.CollegeBean;
import com.rays.pro4.Bean.CourseBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

/**
 * JDBC Implementation of CourseModel.
 * 
 * @author Azam Khan
 *
 */
public class ClientModel {

	
	   private static Logger log= Logger.getLogger(ClientModel.class);
	   
	   
	   
	   private Integer nextPK() throws DatabaseException {
		   log.debug("Model nextpk Started");
		   Connection conn=null;
		   int pk=0;
		   try {
		   
			   conn = JDBCDataSource.getConnection();
			   PreparedStatement pstmt =conn.prepareStatement("SELECT MAX(ID) FROM st_client");
			   ResultSet rs = pstmt.executeQuery();
			   while(rs.next()) {
				   pk = rs.getInt(1);
			   }
		   rs.close();
		   }  catch(Exception e) {
			   log.error("Database Exception..",e);
			   throw new DatabaseException("Exception : Exception in getting pk");
			   
		   }finally {
			   JDBCDataSource.closeConnection(conn);
		   }
		   log.debug("Model next pk End" );
		   return pk = pk+1;
	   }
	   
	   public long add(ClientBean bean) throws ApplicationException, DuplicateRecordException {
		   log.debug("Model add Started");
		   Connection conn=null;
		   int pk=0;
		   
		   
			/*
			 * CourseBean duplicatecourseName = findByName(bean.getName());
			 * if(duplicatecourseName != null) { throw new
			 * DuplicateRecordException("course already exist"); }
			 */
		   
		   
		   try {
			   conn = JDBCDataSource.getConnection();
			   pk=nextPK();
			   conn.setAutoCommit(false);
			   PreparedStatement pstmt = conn.prepareStatement("INSERT  st_client VALUE(?,?,?,?,?,?,?,?,?)");
			   	pstmt.setInt(1, pk);
			   	pstmt.setString(2, bean.getFullName());
				pstmt.setTimestamp(3, new Timestamp(bean.getDate().getTime()));
				pstmt.setString(4, bean.getPhoneNo());
			   	pstmt.setString(5, bean.getIllness());
			   	pstmt.setString(6, bean.getCreatedBy());
			   	pstmt.setString(7, bean.getModifiedBy());
			   	pstmt.setTimestamp(8, bean.getCreatedDatetime());
			   	pstmt.setTimestamp(9, bean.getCreatedDatetime());
			   	pstmt.executeUpdate();
			   	conn.commit();
			   	pstmt.close();
			   	conn.close();
		   }catch(Exception e) {
			   log.error("Database Exception....",e);
			   try {
				   conn.rollback();
			   }catch(Exception ex) {
				   ex.printStackTrace();
				 //  throw new ApplicationException("Excetion : add rollback Exception " +ex.getMessage());
			   }
			 //  throw new ApplicationException("Exception : Exception in add course" );
		   }finally {
			   JDBCDataSource.closeConnection(conn);
		   }
		   log.debug("Model add End");
		return pk;
		  
	   }
	   public void delete(Long id) throws ApplicationException {
		   log.debug("Model Delete Started");
		   Connection conn = null;
		   try {
			   conn = JDBCDataSource.getConnection();
			   conn.setAutoCommit(false);
			   PreparedStatement pstmt = conn.prepareStatement("DELETE  FROM st_client WHERE ID=?");
			   pstmt.setLong(1, id);
			   pstmt.executeUpdate();
			   conn.commit();
			   pstmt.close();
		   }catch(Exception e) {
			   log.error("Database Exception....",e);
			   try {
				   conn.rollback();
			   }catch(Exception ex) {
				   throw new ApplicationException("Exception : Delete rollback Wxception "+ ex.getMessage());
			   }
			   throw new ApplicationException("Exception in delete course");
			   
		   }finally {
			   JDBCDataSource.closeConnection(conn);
		   }
		   log.debug("Model delete End");
		   
	   }
	   
	   public ClientBean findByName(String name) throws ApplicationException {
			log.debug("Model findByName Started");
			StringBuffer sql = new StringBuffer("SELECT * FROM st_client WHERE NAME=?");
			ClientBean bean = null;
			Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, name);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					bean = new ClientBean();
					bean.setId(rs.getLong(1));
					 bean.setFullName(rs.getString(2));
					 bean.setDate(rs.getDate(3));
					 bean.setPhoneNo(rs.getString(4));
					 bean.setIllness(rs.getString(5));
					 bean.setCreatedBy(rs.getString(6));
					 bean.setModifiedBy(rs.getString(7));
					 bean.setCreatedDatetime(rs.getTimestamp(8));
					 bean.setModifiedDatetime(rs.getTimestamp(9));

				}
				rs.close();
			} catch (Exception e) {
				log.error("Database Exception", e);
				throw new ApplicationException("Exception : Exception in getting College byName");

			} finally {
				JDBCDataSource.closeConnection(conn);

			}
			log.debug("modal findByName End");
			return bean;
		}

		 
		 public ClientBean FindByPK(long pk) throws ApplicationException {
			 log.debug("Model FindByPK Started");
			 StringBuffer sql=new StringBuffer("SELECT * FROM st_client WHERE ID=?");
			 Connection conn =  null;
			 ClientBean bean = null;
			 try {
				 conn=JDBCDataSource.getConnection();
				 PreparedStatement pstmt=conn.prepareStatement(sql.toString());
				 pstmt.setLong(1, pk);
				 ResultSet rs=pstmt.executeQuery();
				 
				 while(rs.next()) {
					 bean = new ClientBean();
					 bean.setId(1);
					 bean.setFullName(rs.getString(2));
					 bean.setDate(rs.getDate(3));
					 bean.setPhoneNo(rs.getString(4));
					 bean.setIllness(rs.getString(5));
					 bean.setCreatedBy(rs.getString(6));
					 bean.setModifiedBy(rs.getString(7));
					 bean.setCreatedDatetime(rs.getTimestamp(8));
					 bean.setModifiedDatetime(rs.getTimestamp(9));
				 }
				 rs.close();
					 
			 }catch(Exception e) {
				 log.error("Database Exception...",e);
				 throw new ApplicationException("Exception in getting course by pk");
			 }finally {
				 JDBCDataSource.closeConnection(conn);
				 log.debug("Model FindbyPK End");
			 }
			 return bean;
		 }
		 
		 public void update(ClientBean bean) throws ApplicationException, DuplicateRecordException {
			 log.debug("model update Started");
			 Connection conn=null;
			 
		
			 try {
				 conn=JDBCDataSource.getConnection();
				 conn.setAutoCommit(false);
				 PreparedStatement pstmt = conn.prepareStatement("UPDATE st_client SET FULL_NAME=?,DATE=?,PHONE_NO=?,ILLNESS=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
				 
				 
				   	pstmt.setString(1, bean.getFullName());
					pstmt.setTimestamp(2, new Timestamp(bean.getDate().getTime()));
					pstmt.setString(3, bean.getPhoneNo());
				   	pstmt.setString(4, bean.getIllness());
				   	pstmt.setString(5, bean.getCreatedBy());
				   	pstmt.setString(6, bean.getModifiedBy());
				   	pstmt.setTimestamp(7, bean.getCreatedDatetime());
				   	pstmt.setTimestamp(8, bean.getCreatedDatetime());
				   
				 pstmt.setLong(9, bean.getId());
				 pstmt.executeUpdate();
				 conn.commit();
				 pstmt.close();
				 
			 }catch(Exception e) {
				 e.printStackTrace();
				 log.error("Database Exception..",e);
				 try {
					 conn.rollback();
				 }catch(Exception ex) {
					 throw new ApplicationException("Exception : update rollback Exception " + ex.getMessage());
				 }
//				 throw new ApplicationException("Exception in updatingcourse" );
			 }finally {
				 JDBCDataSource.closeConnection(conn);
			 }
			 log.debug("Model update End");
		 }
		 public List search(ClientBean bean) throws DatabaseException, ApplicationException {
			 return search(bean,0,0);
		 }
		 public List search(ClientBean bean,int pageNo,int pageSize) throws DatabaseException, ApplicationException {
			 log.debug("Model search Started");
			 StringBuffer sql= new StringBuffer("Select * from st_client where 1=1");
			 if(bean !=null) {
				 if(bean.getId() > 0) {
					 sql.append(" AND id = "+ bean.getId());
				 }
				 if(bean.getFullName() != null && bean.getFullName().length() > 0) {
					 sql.append(" AND FULL_NAME like '" + bean.getFullName() +"%'");
				 }
				 if (bean.getDate() != null && bean.getDate().getTime() > 0) {
						sql.append(" AND date like '" + new java.sql.Date(bean.getDate().getTime()) + "%'");
					}
				 if(bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0) {
					 sql.append(" AND PHONE_NO like '" + bean.getPhoneNo() +"%'");
				 }
				 if(bean.getIllness() != null && bean.getIllness().length() > 0) {
					 sql.append(" AND id like '" + bean.getIllness() + "%'");
				 }
			 }
			 if(pageSize > 0) {
				 pageNo = (pageNo - 1) * pageSize;
				 sql.append(" limit "+ pageNo +","+ pageSize );
			 }
			 System.out.println("sql"+sql);
			 ArrayList list = new ArrayList();
			 Connection conn=null;
			 try {
				 conn=JDBCDataSource.getConnection();
				 PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				 System.out.println(sql);
				 ResultSet rs = pstmt.executeQuery();
				 while(rs.next()) {
					 bean=new ClientBean();
					 bean.setId(1);
					 bean.setFullName(rs.getString(2));
					 bean.setDate(rs.getDate(3));
					 bean.setPhoneNo(rs.getString(4));
					 bean.setIllness(rs.getString(5));
					 bean.setCreatedBy(rs.getString(6));
					 bean.setModifiedBy(rs.getString(7));
					 bean.setCreatedDatetime(rs.getTimestamp(8));
					 bean.setModifiedDatetime(rs.getTimestamp(9));
						list.add(bean);
				 }
				 rs.close();
			 }catch(Exception e) {
				 log.error("Database Exception...",e);
				 throw new ApplicationException("Exception in the search"+e.getMessage());
			 }finally {
				 JDBCDataSource.closeConnection(conn);
			 }
			 log.debug("MOdel search End");
			 return list;
		 }
		 
		 public List list() throws Exception {
			 return list(0,0);
		 }
		
		  public List list(int pageNo,int pageSize) throws Exception{
		  
		  log.debug("model list started");
		  
		  List list = new ArrayList();
		  
		  StringBuffer sql = new StringBuffer("select * from st_client");
		  
		  if(pageSize>0){
			  pageNo= (pageNo-1)*pageSize;
		  sql.append(" limit "+pageNo+" ,"+pageSize); }
		  
		  Connection conn=null;
		  
		  try{ conn= JDBCDataSource.getConnection(); PreparedStatement pstmt =
		  conn.prepareStatement(sql.toString());
		  
		  ResultSet rs = pstmt.executeQuery(); 
		  ClientBean bean; 
		  while(rs.next()){
		  bean = new ClientBean();
		  
		  bean.setId(rs.getLong(1)); 
		  
			 bean.setFullName(rs.getString(2));
			 bean.setDate(rs.getDate(3));
			 bean.setPhoneNo(rs.getString(4));
			 bean.setIllness(rs.getString(5));
			 bean.setCreatedBy(rs.getString(6));
			 bean.setModifiedBy(rs.getString(7));
			 bean.setCreatedDatetime(rs.getTimestamp(8));
			 bean.setModifiedDatetime(rs.getTimestamp(9));
		  
		  list.add(bean); 
		}
		  rs.close();
		  pstmt.close();
		  conn.close();
		  } catch(Exception e){
			  e.printStackTrace();
			  log.error("Database Exception...",e);
			  throw new ApplicationException("Exception : Exception in getting lidt "+e.getMessage());
		 
		  } finally{ 
			  JDBCDataSource.closeConnection(conn);
			  }
		  return list;
		  }
		
	
}
