/**
 * 
 */
package com.covisint.util.apache.dynamic.ctrust;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.covisint.util.apache.dynamic.realm.RealmInfoUtil;
import com.covisint.util.apache.dynamic.realm.model.RealmInfo;
import com.covisint.util.apache.dynamic.util.FileUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Owais
 *
 */
public class VirtualHostSectionServiceTest extends TestCase{

	/**
	 * Test method for
	 * {@link com.covisint.util.apache.dynamic.ctrust.VirtualHostSectionService#virtualHostSection(java.lang.String, com.covisint.util.apache.dynamic.realm.model.RealmInfo)}
	 * .
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test
	public void testVirtualHostSection() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<RealmInfo> realmInfoList = Arrays
				.asList(mapper.readValue(
						FileUtil.readClasspathFileAsString("com/covisint/util/apache/dynamic/ctrust/SampleConfigurationServiceResponse"),
						RealmInfo[].class));
		
		for(RealmInfo realmInfo:realmInfoList){
			RealmInfoUtil.sanitize(realmInfo);
		}

		for (RealmInfo info : realmInfoList) {
			
			System.out.println(VirtualHostSectionService.get()
					.virtualHostSection("identity.something.com", "admin", info,"false"));
		}
	}

}
