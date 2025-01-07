package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Bean.PrescriptionBean;
import com.rays.pro4.Model.OrderModel;
import com.rays.pro4.Model.PrescriptionModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "PrescriptionCtl", urlPatterns = "/ctl/PrescriptionCtl")

public class PrescriptionCtl extends BaseCtl  {
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("decease"))) {
			request.setAttribute("decease", PropertyReader.getValue("error.require", "decease"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("capacity"))) {
			request.setAttribute("capacity", PropertyReader.getValue("error.require", "capacity"));
			pass = false;
		}
		return pass;
		
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		Long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {

			PrescriptionModel model = new PrescriptionModel();

			try {
				PrescriptionBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrescriptionModel model = new PrescriptionModel();
		String op = DataUtility.getString(request.getParameter("operation"));
		
		System.out.println(request.getParameter("operation"));
		
		if(OP_SAVE.equalsIgnoreCase(op)) {
			
			PrescriptionBean bean = (PrescriptionBean) populateBean(request);
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
			ServletUtility.redirect(ORSView.PRESCRIPTION_CTL, request, response);
			return;
		}
	}

	@Override
	protected String getView() {
		return ORSView.PRESCRIPTION_VIEW;
	}

}