/**
 * 
 */
package com.covisint.util.apache.dynamic.util;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import com.covisint.util.apache.dynamic.realm.RealmInfoService;
import com.covisint.util.apache.dynamic.realm.model.RealmInfo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Owais
 *
 */
public final class TestUtil {
    
    private TestUtil(){}
    
    public static void mockServiceCall() throws IOException, JsonParseException, JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        RealmInfo[] realmInfoArr = mapper.readValue(
                        FileUtil.readClasspathFileAsString("com/covisint/util/apache/dynamic/ctrust/SampleConfigurationServiceResponse"),
                        RealmInfo[].class);
        
        Client mockClient = mock(Client.class);
        WebTarget mockTarget = mock(WebTarget.class);
        Invocation.Builder mockBuilder = mock(Invocation.Builder.class);
        when(mockClient.target(anyString())).thenReturn(mockTarget);
        when(mockTarget.request()).thenReturn(mockBuilder);
        when(mockBuilder.header(anyString(), anyObject())).thenReturn(mockBuilder);
        when(mockBuilder.accept(anyString())).thenReturn(mockBuilder);
        when(mockBuilder.get(RealmInfo[].class)).thenReturn(realmInfoArr);
        
        RealmInfoService.get().setRestClient(mockClient);
    }

}
