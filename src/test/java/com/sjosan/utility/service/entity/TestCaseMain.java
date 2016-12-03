package com.sjosan.utility.service.entity;

import com.sjosan.utility.service.business.UtilityServiceBusiness;

import junit.framework.TestCase;

public class TestCaseMain extends TestCase{

	public void testSaveData(){
		UtilityServiceBusiness business= new UtilityServiceBusiness();
		
		business.saveData();
	}
	
}
