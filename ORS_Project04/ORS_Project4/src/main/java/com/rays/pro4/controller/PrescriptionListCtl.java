package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Bean.PrescriptionBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.CollegeModel;
import com.rays.pro4.Model.OrderModel;
import com.rays.pro4.Model.PrescriptionModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.ServletUtility;
@WebServlet(name = "PrescriptionListCtl", urlPatterns = { "/ctl/PrescriptionListCtl" })
public class PrescriptionListCtl extends BaseCtl {
	
	  @Override
	    protected void preload(HttpServletRequest request){
	    	PrescriptionModel pmodel=new PrescriptionModel();
	    		List plist;
				try {
					plist = pmodel.list();
					request.setAttribute("prescriptionList", plist);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    }

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		PrescriptionBean bean = new PrescriptionBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setDecease(DataUtility.getString(request.getParameter("decease")));
		bean.setDate(DataUtility.getDate(request.getParameter("date")));
		bean.setCapacity(DataUtility.getDouble(request.getParameter("capacity")));
		populateDTO(bean, request);
		return bean;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list;

		int pageNo = 1;
		int pageSize = 10;

		PrescriptionBean bean = (PrescriptionBean) populateBean(request);
		PrescriptionModel model = new PrescriptionModel();

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

		PrescriptionBean bean = (PrescriptionBean) populateBean(request);

		String[] ids = request.getParameterValues("ids");
		PrescriptionModel model = new PrescriptionModel();

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
			pageNo--;
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PRESCRIPTION_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PRESCRIPTION_LIST_CTL, request, response);
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
		return ORSView.PRESCRIPTION_LIST_VIEW;
	}

}
