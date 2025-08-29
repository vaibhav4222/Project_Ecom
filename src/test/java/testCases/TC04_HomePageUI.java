package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;

public class TC04_HomePageUI extends BaseClass {
	HomePage hp ;
	
	@Test(groups = {"Master","UI"})
	void HeaderShown() {
		 hp = new HomePage(driver);
		logger.info("Header Shown started");
		Assert.assertTrue(hp.isHeaderDisplayed());
		logger.info("Header passed");

	}
	
	
	
	@Test(groups = {"Master","UI"})
	void informationSitemapShown() {
		logger.info("SitemapShown started");

		
		Assert.assertEquals(true, hp.isinformationSitemapDisplayed());
		
		logger.info("SitemapShown finished");

	}
	
	
	@Test(groups = {"Master","UI"})
	void isLogoDisplayed() {
		logger.info("LogoDisplayed started");

		Assert.assertEquals(hp.isLogoDisplayed(), true); 
		logger.info("LogoDisplayed finished");

	}
	
	

}
