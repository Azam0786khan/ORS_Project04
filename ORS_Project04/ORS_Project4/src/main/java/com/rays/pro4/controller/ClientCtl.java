package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.ClientBean;
import com.rays.pro4.Bean.ClientBean;
import com.rays.pro4.Bean.DoctorBean;
import com.rays.pro4.Bean.PrescriptionBean;
import com.rays.pro4.Bean.UserBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.ClientModel;
import com.rays.pro4.Model.ClientModel;
import com.rays.pro4.Model.DoctorModel;
import com.rays.pro4.Model.PrescriptionModel;
import com.rays.pro4.Model.UserModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name ="ClientCtl", urlPatterns = "/ctl/ClientCtl")

public class ClientCtl extends BaseCtl  {
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("fullName"))) {
			request.setAttribute("fullName", PropertyReader.getValue("error.require", "fullName"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", PropertyReader.getValue("error.require", "phoneNo"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("illness"))) {
			request.setAttribute("illness", PropertyReader.getValue("error.require", "illness"));
			pass = false;
		}
		return pass;
		
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
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("u ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		ClientModel model = new ClientModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			ClientBean bean;
			try {
				bean = model.FindByPK(id);
				System.out.println("Ankit11111111111");
				System.out.println(bean);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}
    
    /**
     * Contains Submit logics.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		
		// Get Model
		ClientModel model = new ClientModel();
		long id = DataUtility.getLong(request.getParameter("id"));
	
		if(OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)){
			ClientBean bean =(ClientBean) populateBean(request);
		try{
			if(id>0){		
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Client is Successfully Updated", request);
				
			}else{
				 long pk = model.add(bean);
				 ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Client is Successfully Added", request);
				
			//		bean.setId(pk);
			}
				ServletUtility.setBean(bean, request);
			//ServletUtility.setSuccessMessage("Client is Successfully Added", request);
		
		}catch(ApplicationException e ){
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		} catch (DuplicateRecordException e) {
			ServletUtility.setBean(bean, request);
			ServletUtility.setErrorMessage("Client Name Already Exist", request);
			
		}		
		}/*else if (OP_DELETE.equalsIgnoreCase(op)) {
			ClientBean bean =(ClientBean) populateBean(request);
			try {
				model.delete(bean);;
				ServletUtility.redirect(ORSView.CLIENT_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return ;
			}
		}*/
		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CLIENT_LIST_CTL, request, response);
			return;
		}
		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CLIENT_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	
	}

	@Override
	protected String getView() {
		return ORSView.CLIENT_VIEW;
	}

}