package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.FollowUpBean;
import com.rays.pro4.Bean.PrescriptionBean;
import com.rays.pro4.Model.FollowUpModel;
import com.rays.pro4.Model.PrescriptionModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "FollowUpCtl", urlPatterns = "/ctl/FollowUpCtl")

public class FollowUpCtl extends BaseCtl{
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("patient"))) {
			request.setAttribute("patient", PropertyReader.getValue("error.require", "patient"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("doctor"))) {
			request.setAttribute("doctor", PropertyReader.getValue("error.require", "doctor"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("visitDate"))) {
			request.setAttribute("visitDate", PropertyReader.getValue("error.require", "visitDate"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("fees"))) {
			request.setAttribute("fees", PropertyReader.getValue("error.require", "fees"));
			pass = false;
		}
		return pass;
		
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		Long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {

			FollowUpModel model = new FollowUpModel();

			try {
				FollowUpBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FollowUpModel model = new FollowUpModel();
		String op = DataUtility.getString(request.getParameter("operation"));
		
		System.out.println(request.getParameter("operation"));
		
		if(OP_SAVE.equalsIgnoreCase(op)) {
			
			FollowUpBean bean = (FollowUpBean) populateBean(request);
			try {
				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data Added successfully", request);
				
				ServletUtility.forward(getView(), request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FOLLOWUP_CTL, request, response);
			return;
		}
	}

	@Override
	protected String getView() {
		return ORSView.FOLLOWUP_VIEW;
	}

}