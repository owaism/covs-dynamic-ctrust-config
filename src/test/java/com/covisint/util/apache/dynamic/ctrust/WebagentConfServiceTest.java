/**
 * 
 */
package com.covisint.util.apache.dynamic.ctrust;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.covisint.util.apache.dynamic.main.ServerTypesE;
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
public class WebagentConfServiceTest {

	@Test
	public void webagentConf() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<RealmInfo> realmInfoList = Arrays.asList(mapper.readValue(
				FileUtil.readClasspathFileAsString("com/covisint/util/apache/dynamic/ctrust/SampleConfigurationServiceResponse"), RealmInfo[].class));

		for (RealmInfo realmInfo : realmInfoList) {
			RealmInfoUtil.sanitize(realmInfo);
		}

		System.out.println(WebagentConfService.get().webagentConf(realmInfoList));
	}

}
