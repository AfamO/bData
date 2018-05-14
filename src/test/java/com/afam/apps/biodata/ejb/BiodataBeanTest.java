/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.afam.apps.biodata.ejb;

import com.afam.apps.biodata.entity.UserBiodata;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Tivas-Tech
 */
public class BiodataBeanTest {
    private static Context  ctx;
    private static EJBContainer ejbContainer;
    public BiodataBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        ejbContainer = EJBContainer.createEJBContainer();      
        ctx = ejbContainer.getContext();
    }
    
    @AfterClass
    public static void tearDownClass() {
        ejbContainer.close();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getBiodata method, of class BiodataBean.
     */
    //@Test
    public void testGetBiodata() throws Exception {
        System.out.println("getBiodata");
        //ejbContainer = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BiodataBean instance = (BiodataBean)ejbContainer.getContext().lookup("java:global/classes/BiodataBean");
        UserBiodata result = instance.getBiodata("","");
        UserBiodata expResult = null;
        assertEquals(expResult, result);
       
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveUserBiodata method, of class BiodataBean.
     */
    //@Test
    public void testSaveUserBiodata() throws Exception {
        System.out.println("saveUserBiodata");
        String username = "AfamO";
        String password = "d!219@wX";
        String address = "419 Idumota Lagos";
        int age = 20;
        String occupation = "Programmer";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BiodataBean instance = (BiodataBean)ejbContainer.getContext().lookup("java:global/classes/BiodataBean");
        boolean result = instance.saveUserBiodata(username, password, address, age, occupation);
        boolean expResult = true;
        assertEquals(expResult, result);
        //container.close();
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }
    
}
