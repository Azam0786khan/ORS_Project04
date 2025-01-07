package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.StudentBean;
import com.rays.pro4.Bean.VehicleRegistrationBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.CollegeModel;
import com.rays.pro4.Model.StudentModel;
import com.rays.pro4.Model.VehicleRegistrationModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

//TODO: Auto-generated Javadoc
/**
* Student functionality Controller. Performs operation for add, update, delete
* and get Student
* 
*  @author Azam Khan
*/
@WebServlet(name="VehicleCtl", urlPatterns = {"/ctl/VehicleCtl"})
public class VehicleCtl extends BaseCtl{

	/** The log. */
    private static Logger log = Logger.getLogger(VehicleCtl.class);

    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
     */
//    @Override
//    protected void preload(HttpServletRequest request) {
//        CollegeModel model = new CollegeModel();
//        try {
//            List l = model.list();
//            request.setAttribute("collegeList", l);
//        } catch (ApplicationException e) {
//            log.error(e);
//        }
//
//    }

    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected boolean validate(HttpServletRequest request) {
    	System.out.println("validate started ... std ctl");
        log.debug("VehicleCtl Method validate Started");
        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("ownerName"))) {
            request.setAttribute("ownerName",PropertyReader.getValue("error.require", "Owner Name"));
            pass = false;
        }else if (!DataValidator.isName(request.getParameter("ownerName"))) {
        	  request.setAttribute("ownerName","Owner Name contains alphabet only");
              pass = false;
		}
        if (DataValidator.isNull(request.getParameter("address"))) {
            request.setAttribute("address",PropertyReader.getValue("error.require", "address"));
            pass = false;
	}
        if (DataValidator.isNull(request.getParameter("state"))) {
            request.setAttribute("state",PropertyReader.getValue("error.require", "state"));
            pass = false;
	}
        if (DataValidator.isNull(request.getParameter("city"))) {
            request.setAttribute("city",PropertyReader.getValue("error.require", "city"));
            pass = false;
	}


        
        if (DataValidator.isNull(request.getParameter("phoneNo"))) {
            request.setAttribute("phoneNo", PropertyReader.getValue("error.require", "phoneNo"));
            pass = false;
        }else if (!DataValidator.isMobileNo(request.getParameter("phoneNo"))) {
      	  request.setAttribute("phoneNo", "Mobile No. must be 10 Digit and No. Series start with 6-9");
          pass = false;
	}
        
        System.out.println("validate over ,.... Vehicle ctl");
        log.debug("VehicleCtl Method validate Ended");
        return pass;
    }

    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("VehicleCtl Method populatebean Started");

        VehicleRegistrationBean bean = new VehicleRegistrationBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setOwnerName(DataUtility.getString(request.getParameter("ownerName")));
        bean.setAddress(DataUtility.getString(request.getParameter("address")));
       
        bean.setState(DataUtility.getString(request.getParameter("state")));
        bean.setCity(DataUtility.getString(request.getParameter("city")));
        bean.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));
        populateDTO(bean, request);
        log.debug("VehicleCtl Method populatebean Ended");
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
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        log.debug("VehicleCtl Method doGet Started");

        String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));

        // get model

        VehicleRegistrationModel model = new VehicleRegistrationModel();
        if (id > 0 || op != null) {
            VehicleRegistrationBean bean;
            try {
                bean = model.findByPK(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
        log.debug("vehicleCtl Method doGett Ended");
    }

    /**
     * Contains Submit logics.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        log.debug("vehicleCtl Method doPost Started");

        String op = DataUtility.getString(request.getParameter("operation"));

        long id = DataUtility.getLong(request.getParameter("id"));
        // get model

        VehicleRegistrationModel model = new VehicleRegistrationModel();

        if (OP_SAVE.equalsIgnoreCase(op)|| OP_UPDATE.equalsIgnoreCase(op)) {
        	VehicleRegistrationBean bean = (VehicleRegistrationBean) populateBean(request);
            try {
                if (id > 0) {
                    model.Update(bean);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage(" Vehicle is successfully Updated",request);
                } else {
                    long pk = model.add(bean);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage(" Vehicle is successfully Added",request);
             //      bean.setId(pk);
                }
                ServletUtility.setBean(bean, request);
               // ServletUtility.setSuccessMessage(" Student is successfully Added",request);
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Owner Name already exists", request);
            }
        } 
        else if ( OP_RESET.equalsIgnoreCase(op)) {
            
         	ServletUtility.redirect(ORSView.VEHICLE_CTL, request, response);
             return;
         }
        else if (OP_CANCEL.equalsIgnoreCase(op) ) {
            
         	ServletUtility.redirect(ORSView.VEHICLE_LIST_CTL, request, response);
             return;
         }
/*
        else if (OP_DELETE.equalsIgnoreCase(op)) {

            StudentBean bean = (StudentBean) populateBean(request);
            try {
                model.delete(bean);
                ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
                return;

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
*/        ServletUtility.forward(getView(), request, response);

        log.debug("VehicleCtl Method doPost Ended");
    }

    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#getView()
     */
    @Override
    protected String getView() {
        return ORSView.VEHICLE_VIEW;
    }

	
}
