package com.covisint.util.apache.dynamic.realm;

import java.io.IOException;

import org.junit.Test;

import com.covisint.util.apache.dynamic.util.TestUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class RealmInfoServiceTest {

	@Test
	public void testRealmInfo() throws JsonParseException, JsonMappingException, IOException{
	    
	    TestUtil.mockServiceCall();
	    
	    
		RealmInfoService.get().realmInfo();
	}

}
