/*
 * $Id$
 * $Rev$
 * $Date$
 *
 * ====================================================================
 * Copyright 2001-2005 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.apache.commons.validator;

import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.xml.sax.SAXException;

                                                          
/**                                                       
 * Performs Validation Test.
 */                                                       
public class RequiredNameTest extends TestCommon {
   
   /**
    * The key used to retrieve the set of validation 
    * rules from the xml file.
    */
   protected static String FORM_KEY = "nameForm";   

   /**
    * The key used to retrieve the validator action.
    */
   protected static String ACTION = "required";

   public RequiredNameTest(String name) {                  
       super(name);                                      
   }                                                     

   /**
    * Start the tests.
    *
    * @param theArgs the arguments. Not used
    */
   public static void main(String[] theArgs) {
       junit.awtui.TestRunner.main(new String[] {RequiredNameTest.class.getName()});
   }

   /**
    * @return a test suite (<code>TestSuite</code>) that includes all methods
    *         starting with "test"
    */
   public static Test suite() {
       // All methods starting with "test" will be executed in the test suite.
       return new TestSuite(RequiredNameTest.class);
   }

   /**
    * Load <code>ValidatorResources</code> from 
    * validator-name-required.xml.
    */
   protected void setUp() throws IOException, SAXException {
      // Load resources
      loadResources("validator-name-required.xml");
   }

   protected void tearDown() {
   }

   /**
    * Tests the required validation failure.
    */
   public void testRequired() throws ValidatorException {
      // Create bean to run test on.
      NameBean name = new NameBean();
      
      // Construct validator based on the loaded resources 
      // and the form key
      Validator validator = new Validator(resources, FORM_KEY);
      // add the name bean to the validator as a resource 
      // for the validations to be performed on.
      validator.setParameter(Validator.BEAN_PARAM, name);

      // Get results of the validation.
      ValidatorResults results = null;
      
      // throws ValidatorException, 
      // but we aren't catching for testing 
      // since no validation methods we use 
      // throw this
      results = validator.validate();
      
      assertNotNull("Results are null.", results);
      
      ValidatorResult firstNameResult = results.getValidatorResult("firstName");
      ValidatorResult lastNameResult = results.getValidatorResult("lastName");
      
      assertNotNull("First Name ValidatorResult should not be null.", firstNameResult);
      assertTrue("First Name ValidatorResult should contain the '" + ACTION +"' action.", firstNameResult.containsAction(ACTION));
      assertTrue("First Name ValidatorResult for the '" + ACTION +"' action should have failed.", !firstNameResult.isValid(ACTION));
      
      assertNotNull("First Name ValidatorResult should not be null.", lastNameResult);
      assertTrue("Last Name ValidatorResult should contain the '" + ACTION +"' action.", lastNameResult.containsAction(ACTION));
      assertTrue("Last Name ValidatorResult for the '" + ACTION +"' action should have failed.", !lastNameResult.isValid(ACTION));
   }

   /**
    * Tests the required validation for first name if it is blank.
    */
   public void testRequiredFirstNameBlank() throws ValidatorException {
      // Create bean to run test on.
      NameBean name = new NameBean();
      name.setFirstName("");
      
      // Construct validator based on the loaded resources 
      // and the form key
      Validator validator = new Validator(resources, FORM_KEY);
      // add the name bean to the validator as a resource 
      // for the validations to be performed on.
      validator.setParameter(Validator.BEAN_PARAM, name);

      // Get results of the validation.
      ValidatorResults results = null;
      
      results = validator.validate();
      
      assertNotNull("Results are null.", results);
      
      ValidatorResult firstNameResult = results.getValidatorResult("firstName");
      ValidatorResult lastNameResult = results.getValidatorResult("lastName");
      
      assertNotNull("First Name ValidatorResult should not be null.", firstNameResult);
      assertTrue("First Name ValidatorResult should contain the '" + ACTION +"' action.", firstNameResult.containsAction(ACTION));
      assertTrue("First Name ValidatorResult for the '" + ACTION +"' action should have failed.", !firstNameResult.isValid(ACTION));
      
      assertNotNull("First Name ValidatorResult should not be null.", lastNameResult);
      assertTrue("Last Name ValidatorResult should contain the '" + ACTION +"' action.", lastNameResult.containsAction(ACTION));
      assertTrue("Last Name ValidatorResult for the '" + ACTION +"' action should have failed.", !lastNameResult.isValid(ACTION));
   }

   /**
    * Tests the required validation for first name.
    */
   public void testRequiredFirstName() throws ValidatorException {
      // Create bean to run test on.
      NameBean name = new NameBean();
      name.setFirstName("Joe");
      
      // Construct validator based on the loaded resources 
      // and the form key
      Validator validator = new Validator(resources, FORM_KEY);
      // add the name bean to the validator as a resource 
      // for the validations to be performed on.
      validator.setParameter(Validator.BEAN_PARAM, name);

      // Get results of the validation.
      ValidatorResults results = null;
      
      results = validator.validate();
      
      assertNotNull("Results are null.", results);
      
      ValidatorResult firstNameResult = results.getValidatorResult("firstName");
      ValidatorResult lastNameResult = results.getValidatorResult("lastName");
      
      assertNotNull("First Name ValidatorResult should not be null.", firstNameResult);
      assertTrue("First Name ValidatorResult should contain the '" + ACTION +"' action.", firstNameResult.containsAction(ACTION));
      assertTrue("First Name ValidatorResult for the '" + ACTION +"' action should have passed.", firstNameResult.isValid(ACTION));
      
      assertNotNull("First Name ValidatorResult should not be null.", lastNameResult);
      assertTrue("Last Name ValidatorResult should contain the '" + ACTION +"' action.", lastNameResult.containsAction(ACTION));
      assertTrue("Last Name ValidatorResult for the '" + ACTION +"' action should have failed.", !lastNameResult.isValid(ACTION));
   }

   /**
    * Tests the required validation for last name if it is blank.
    */
   public void testRequiredLastNameBlank() throws ValidatorException {
      // Create bean to run test on.
      NameBean name = new NameBean();
      name.setLastName("");
      
      // Construct validator based on the loaded resources 
      // and the form key
      Validator validator = new Validator(resources, FORM_KEY);
      // add the name bean to the validator as a resource 
      // for the validations to be performed on.
      validator.setParameter(Validator.BEAN_PARAM, name);

      // Get results of the validation.
      ValidatorResults results = null;
      
      results = validator.validate();
      
      assertNotNull("Results are null.", results);
      
      ValidatorResult firstNameResult = results.getValidatorResult("firstName");
      ValidatorResult lastNameResult = results.getValidatorResult("lastName");
      
      assertNotNull("First Name ValidatorResult should not be null.", firstNameResult);
      assertTrue("First Name ValidatorResult should contain the '" + ACTION +"' action.", firstNameResult.containsAction(ACTION));
      assertTrue("First Name ValidatorResult for the '" + ACTION +"' action should have failed.", !firstNameResult.isValid(ACTION));
      
      assertNotNull("First Name ValidatorResult should not be null.", lastNameResult);
      assertTrue("Last Name ValidatorResult should contain the '" + ACTION +"' action.", lastNameResult.containsAction(ACTION));
      assertTrue("Last Name ValidatorResult for the '" + ACTION +"' action should have failed.", !lastNameResult.isValid(ACTION));
   }

   /**
    * Tests the required validation for last name.
    */
   public void testRequiredLastName() throws ValidatorException {
      // Create bean to run test on.
      NameBean name = new NameBean();
      name.setLastName("Smith");
      
      // Construct validator based on the loaded resources 
      // and the form key
      Validator validator = new Validator(resources, FORM_KEY);
      // add the name bean to the validator as a resource 
      // for the validations to be performed on.
      validator.setParameter(Validator.BEAN_PARAM, name);

      // Get results of the validation.
      ValidatorResults results = null;
      
      results = validator.validate();
      
      assertNotNull("Results are null.", results);
      
      ValidatorResult firstNameResult = results.getValidatorResult("firstName");
      ValidatorResult lastNameResult = results.getValidatorResult("lastName");
      
      assertNotNull("First Name ValidatorResult should not be null.", firstNameResult);
      assertTrue("First Name ValidatorResult should contain the '" + ACTION +"' action.", firstNameResult.containsAction(ACTION));
      assertTrue("First Name ValidatorResult for the '" + ACTION +"' action should have failed.", !firstNameResult.isValid(ACTION));
      
      assertNotNull("First Name ValidatorResult should not be null.", lastNameResult);
      assertTrue("Last Name ValidatorResult should contain the '" + ACTION +"' action.", lastNameResult.containsAction(ACTION));
      assertTrue("Last Name ValidatorResult for the '" + ACTION +"' action should have passed.", lastNameResult.isValid(ACTION));

   }

   /**
    * Tests the required validation for first and last name.
    */
   public void testRequiredName() throws ValidatorException {
      // Create bean to run test on.
      NameBean name = new NameBean();
      name.setFirstName("Joe");
      name.setLastName("Smith");
      
      // Construct validator based on the loaded resources 
      // and the form key
      Validator validator = new Validator(resources, FORM_KEY);
      // add the name bean to the validator as a resource 
      // for the validations to be performed on.
      validator.setParameter(Validator.BEAN_PARAM, name);

      // Get results of the validation.
      ValidatorResults results = null;
      
      results = validator.validate();
      
      assertNotNull("Results are null.", results);
      
      ValidatorResult firstNameResult = results.getValidatorResult("firstName");
      ValidatorResult lastNameResult = results.getValidatorResult("lastName");
      
      assertNotNull("First Name ValidatorResult should not be null.", firstNameResult);
      assertTrue("First Name ValidatorResult should contain the '" + ACTION +"' action.", firstNameResult.containsAction(ACTION));
      assertTrue("First Name ValidatorResult for the '" + ACTION +"' action should have passed.", firstNameResult.isValid(ACTION));
      
      assertNotNull("First Name ValidatorResult should not be null.", lastNameResult);
      assertTrue("Last Name ValidatorResult should contain the '" + ACTION +"' action.", lastNameResult.containsAction(ACTION));
      assertTrue("Last Name ValidatorResult for the '" + ACTION +"' action should have passed.", lastNameResult.isValid(ACTION));
   }
   
}                                                         