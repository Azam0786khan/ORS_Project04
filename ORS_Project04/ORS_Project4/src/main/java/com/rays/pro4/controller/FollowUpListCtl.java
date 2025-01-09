package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.FollowUpBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.FollowUpModel;

import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.ServletUtility;
@WebServlet(name = "FollowUpListCtl", urlPatterns = { "/ctl/FollowUpListCtl" })
public class FollowUpListCtl extends BaseCtl {
	
	  @Override
	    protected void preload(HttpServletRequest request){
	    	FollowUpModel fmodel=new FollowUpModel();
	    		List flist;
				try {
					flist = fmodel.list();
					request.setAttribute("FollowUpList", flist);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    }

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		FollowUpBean bean = new FollowUpBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPatient(DataUtility.getString(request.getParameter("patient")));
		bean.setDoctor(DataUtility.getString(request.getParameter("doctor")));
		bean.setVisitDate(DataUtility.getDate(request.getParameter("visitDate")));
		bean.setFees(DataUtility.getDouble(request.getParameter("fees")));
		populateDTO(bean, request);
		return bean;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list;

		int pageNo = 1;
		int pageSize = 10;

		FollowUpBean bean = (FollowUpBean) populateBean(request);
		FollowUpModel model = new FollowUpModel();

		String op = DataUtility.getString(request.getParameter("operation"));

		try {
			list = model.search(bean, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		String op = DataUtility.getString(request.getParameter("operation"));

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? 10 : pageSize;

		FollowUpBean bean = (FollowUpBean) populateBean(request);

		String[] ids = request.getParameterValues("ids");
		FollowUpModel model = new FollowUpModel();

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
			pageNo--;
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FOLLOWUP_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FOLLOWUP_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;

			if (ids != null && ids.length > 0) {
				for (String id : ids) {
					try {
						model.delete(DataUtility.getInt(id));
						ServletUtility.setSuccessMessage("Data Successfully Deleted", request);

					} catch (ApplicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}

					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}

				}
			} else {
				ServletUtility.setErrorMessage("Select Atleast One Record", request);
			}

		}

		try {
			list = model.search(bean, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No Record Found", request);
		}
		ServletUtility.setBean(bean, request);
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.FOLLOWUP_LIST_VIEW;
	}

}
