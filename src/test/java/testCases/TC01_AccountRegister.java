package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.RegisterPage;

public class TC01_AccountRegister extends BaseClass  {
	
	public static String email = Randomusername() + "@gma.coo" ;


	@Test(groups = {"Functional","Master"})
	public void register() {

		HomePage home = new HomePage(driver);
		home.clickRegi();

		try {
			
		
		RegisterPage RegiP = new RegisterPage(driver);
		
		logger.info("Test cases started");

		RegiP.setText_FirstName("Vaibhav");
		

		RegiP.setText_LastName("Tanwade");
		logger.info(" Entered First and Last Name");


		RegiP.setText_Email(email);

		RegiP.setText_Company("CorporateWorld");

		RegiP.setText_Password("Bbbbbb@11");

		RegiP.setText_ConfirmPassword("Bbbbbb@11");

		RegiP.clickRegister();
		logger.info("Assertion sucess message assertion starts");

		String actualM = RegiP.GetSuccessMessage();
		
		Assert.assertEquals(actualM, "Your registration completed");
		
		logger.info("assertion done...");
		System.out.println("Registration done...Password is Bbbbbb@11");
		home.clickLogout();
		}
		catch(Exception e) {
			logger.error("Exception occured");
			logger.debug("Debug logs..");
			Assert.fail();
		}

	}
	
	

}
