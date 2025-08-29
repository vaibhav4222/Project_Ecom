package Utilities;

import org.testng.annotations.DataProvider;

import testCases.TC01_AccountRegister;

public class DataProviders {
	
	String newUserEmail = TC01_AccountRegister.email;
	
	@DataProvider(name = "LoginData")
	public Object[][] getUserCredentials() {
		
        return new Object[][] {
            {"user1@gmail.com", "pass1","invalid"},
            {newUserEmail, "Aaaaa@11","valid"},
            {"user3@sdfdf.cc", "pass3","invalid"},
            
        };
		
		
	}
	

}
