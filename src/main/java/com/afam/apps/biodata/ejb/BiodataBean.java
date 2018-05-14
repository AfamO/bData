/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afam.apps.biodata.ejb;

import com.afam.apps.biodata.entity.UserBiodata;
import com.afam.apps.biodata.entity.UserBiodata_;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Tivas-Tech
 */
@Stateless
public class BiodataBean {

    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger logger = Logger.getLogger("com.afam.apps.biodata.web.BiodataManBean");

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public UserBiodata getBiodata(String username, String password) {
        UserBiodata userBdata = null;
        try {
            // query.setParameter("username", username);
            List<UserBiodata> result = entityManager.createNamedQuery("findBiodataWithName")
                    .setParameter("userName", username)
                    .setParameter("passWord", password)
                    .getResultList();
            //findBiodataWithName(username);
            logger.log(Level.INFO, "The retrieved ResultList Size is::{0}", result.size());
            
            if (result == null) {
                //do nothing
            } else {
                userBdata = (UserBiodata) result.get(0);
                logger.log(Level.INFO, "The retrieved UserName is::{0}", userBdata.getUsername());
                logger.log(Level.INFO, "The retrieved Password is::{0}", userBdata.getPassword());
                return userBdata;
            }
        } catch (Exception ex) {

        }
    return userBdata;
    }

    public List findBiodataWithName(String username) {
        return entityManager.createQuery(
                "SELECT u FROM UserBiodata u WHERE u.username LIKE :userName")
                .setParameter("userName", username)
                .setMaxResults(10)
                .getResultList();
    }

    public boolean saveUserBiodata(String username, String password, String address, int age, String occupation) {

        UserBiodata userBiodata = new UserBiodata(username, password, address, age, occupation);
        entityManager.persist(userBiodata);
        return true;
    }
    public UserBiodata queryUserBiodata(String username, String password) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        // Query for a List of objects.
        logger.log(Level.INFO, "The search criteria are ::{0} and {1}", new Object[]{username, password});
        logger.log(Level.INFO, "The search password is::{0}", password);
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
        Root<UserBiodata> userBiodata = criteriaQuery.from(entityManager.getMetamodel().entity(UserBiodata.class));
        //cq.where(cb.equal(root.get("username"));
        criteriaQuery.where(criteriaBuilder.equal(userBiodata.get(UserBiodata_.username), username), criteriaBuilder.equal(userBiodata.get(UserBiodata_.password), "Smith"));
       //criteriaQuery.where(criteriaBuilder.equal(userBiodata.get("username"), criteriaBuilder.parameter(String.class,"username")));
        //.(cb.equal(root.get(UserBiodata_.password), password))
        //cb.and(e.get("username"), username)
        Query query = entityManager.createQuery(criteriaQuery);
        UserBiodata userBdata = null;
        
        try {
            // query.setParameter("username", username);
            List result = query.getResultList();
            //findBiodataWithName(username);
            logger.log(Level.INFO, "The retrieved ResultList Size is::{0}", result.size());
            
            if (result == null) {
                //do nothing
            } else {
                userBdata = (UserBiodata) result.get(0);
                logger.log(Level.INFO, "The retrieved UserName is::{0}", userBdata.getUsername());
                logger.log(Level.INFO, "The retrieved Password is::{0}", userBdata.getPassword());
            }
        } catch (Exception ex) {

        }
     return userBdata;
    }
     public int updateUserBiodata(UserBiodata userBiodata) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        logger.log(Level.INFO, "DB Updated Count Is Starting the Bean Class Method.... ");
        // Query for a List of objects.
        logger.log(Level.INFO, "The update criteria are ::{0} and {1}", new Object[]{userBiodata.getUsername(),userBiodata.getPassword()});
       
        CriteriaUpdate criteriaUpdate = criteriaBuilder.createCriteriaUpdate(UserBiodata.class);
        Root<UserBiodata> userBdata = criteriaUpdate.from(UserBiodata.class);
        criteriaUpdate.set("address", userBiodata.getAddress());
        criteriaUpdate.set("age", userBiodata.getAge());
        criteriaUpdate.set("occupation", userBiodata.getOccupation());
        
        criteriaUpdate.where(criteriaBuilder.equal(userBdata.get(UserBiodata_.username), userBiodata.getUsername()), criteriaBuilder.equal(userBdata.get(UserBiodata_.password), userBiodata.getPassword()));
       
        Query query = entityManager.createQuery(criteriaUpdate);
        int rowCount=-1;
        try {
            // query.setParameter("username", username);
            rowCount = query.executeUpdate();
            //findBiodataWithName(username);
            logger.log(Level.INFO, "The Update RowCount Size is::{0}", rowCount);
            
        } catch (Exception ex) {

        }
     return rowCount;
    }
      
   
}
