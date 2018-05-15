/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.afam.apps.biodata.web;

import com.afam.apps.biodata.ejb.BiodataBean;
import com.afam.apps.biodata.entity.UserBiodata;
import java.beans.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import javax.ejb.EJB;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.apache.log4j.Priority;

/**
 *
 * @author Tivas-Tech
 */
@Named
@SessionScoped
public class BiodataManBean implements Serializable {

    @EJB
    private BiodataBean bioDataBean;
    protected int age=16;
    private String username="";
    UserBiodata userBiodata;
    Properties properties;
    
    /**
     * Creates a new instance of BiodataManBean
     */
    public BiodataManBean() {

    }
    public String processBiodata() {
        bioDataBean.saveUserBiodata(username, password, address, age, occupation);
        logger.log(Level.INFO, "DB Saved Address: "+address);
        logger.log(Level.INFO, "DB Saved UserName:"+username);
        return "/viewBiodata.xhtml";
    }
    public String processUpdatedBiodata() {
        logger.log(Level.INFO, "DB Updated Count Is Starting the Bean Class Method.... ");
        userBiodata=new UserBiodata(username, password, address, age, occupation);
        int updateUserBiodata = bioDataBean.updateUserBiodata(userBiodata);
        logger.log(Level.INFO, "DB Updated Count Is: "+updateUserBiodata);
        this.statusMessage="Successfully Updated Your Biodata, Please re-Log in";
        return "/loginBiodata.xhtml";
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    private String password="";
    private String statusMessage="";

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    private String address="";
    private String  occupation="";
    private static final Logger logger = Logger.getLogger("com.afam.apps.biodata.web.BiodataManBean");

    public String loginBiodata() {
        userBiodata=bioDataBean.getBiodata(username,password);
        if(this.userBiodata==null){
            this.statusMessage="OOps! Something went wrong. hence can't sign you in";
            logger.log(Level.INFO, "Status Message "+statusMessage);
            return "/loginBiodata.xhtml";
        }
        this.setUsername(userBiodata.getUsername());
        logger.log(Level.INFO, "Retrieved UserName "+ username);
        this.setPassword(userBiodata.getPassword());
        this.setAddress(userBiodata.getAddress());
        logger.log(Level.INFO, "Retrieved Address "+ address);
        this.setAge(userBiodata.getAge());
        logger.log(Level.INFO, "Retrieved Age "+ age);
        this.setOccupation(userBiodata.getOccupation());
        return "/viewBiodata.xhtml";
    }
    public  String loadAppProperties(String param) {
    properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application1.properties"));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(BiodataManBean.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return properties.getProperty(param);
}
    

    /**
     * Get the value of age
     *
     * @return the value of age
     */
    public int getAge() {
        try {
            String url=loadAppProperties("remoteUrl1");
            if(url==null ||url.isEmpty()){
                logger.log(Level.INFO, "Webservie Remote Url Missing, Hence the returned age is -1 ");
                return -1;
            }
            Client client = ClientBuilder.newClient();
            WebTarget target
                    = client.target(url);
            String response = target.request().get(String.class);
            age = Integer.parseInt(response);
        } catch (IllegalArgumentException | NullPointerException |
                WebApplicationException ex) {
            logger.info("processing of HTTP response failed");
        }
        return age;
    }

    /**
     * Set the value of age
     *
     * @param age new value of age
     */
    public void setAge(int age) {
        this.age = age;
    }

    
}
