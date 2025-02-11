package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.ClientBean;
import com.rays.pro4.Bean.ClientBean;
import com.rays.pro4.Bean.ClientBean;
import com.rays.pro4.Bean.ClientBean;

import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Model.ClientModel;
import com.rays.pro4.Model.ClientModel;
import com.rays.pro4.Model.ClientModel;
import com.rays.pro4.Model.ClientModel;

import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;
@WebServlet(name ="ClientListCtl", urlPatterns = { "/ctl/ClientListCtl" })
public class ClientListCtl extends BaseCtl {
	
	  @Override
	    protected void preload(HttpServletRequest request){
	    	ClientModel dmodel=new ClientModel();
	    	List<ClientBean> cList;
				try {
					cList = dmodel.list();
					
					request.setAttribute("cList", cList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    }

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		ClientBean bean = new ClientBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFullName(DataUtility.getString(request.getParameter("fullName")));
		bean.setDate(DataUtility.getDate(request.getParameter("date")));

		bean.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));
		bean.setIllness(DataUtility.getString(request.getParameter("illness")));
		populateDTO(bean, request);
		return bean;

	}

	/**
	 * Contains Display logics.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;

		List nextList = null;

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		ClientBean bean = (ClientBean) populateBean(request);

		ClientModel model = new ClientModel();

		/*
		 * String op = DataUtility.getString(request.getParameter("operation"));
		 * String[] ids = request.getParameterValues("ids");
		 */
		try {
			list = model.search(bean, pageNo, pageSize);
			System.out.println("List" + list);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

			ServletUtility.setList(list, request);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record Found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			return;
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * Contains Submit logics.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		System.out.println("after delete wapas");
		List list = null;
		List nextList = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));

		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(request.getParameter("pageSize")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		ClientBean bean = (ClientBean) populateBean(request);

		ClientModel model = new ClientModel();

		System.out.println("-----------------)))((((" + ids);
		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
			pageNo--;
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CLIENT_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CLIENT_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				ClientBean deletebean = new ClientBean();
				for (String id : ids) {
					try {
						model.delete(DataUtility.getLong(id));
						// ServletUtility.forward(getView(), request, response);
					} catch (ApplicationException e) {
						e.printStackTrace();
						ServletUtility.handleException(e, request, response);
						return;
					}
					ServletUtility.setSuccessMessage("Client Deleted Successfully", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		try {
			// if (!OP_DELETE.equalsIgnoreCase(op)) {
			list = model.search(bean, pageNo, pageSize);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

			ServletUtility.setBean(bean, request);

			// System.out.println("(-----------------)"+ list.size());
			// ServletUtility.setList(list, request);
			// }

		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No record Found", request);
		}

		ServletUtility.setBean(bean, request);

		ServletUtility.setList(list, request);
		ServletUtility.setBean(bean, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
	}
	@Override
	protected String getView() {
		return ORSView.CLIENT_LIST_VIEW;
	}

}
