package com.covisint.util.apache.dynamic.main;

import java.io.IOException;

import org.junit.Test;

import com.covisint.util.apache.dynamic.util.TestUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class DynamicApacheTest {

    @Test
    public void testConfigure() throws JsonParseException, JsonMappingException, IOException {
        
        TestUtil.mockServiceCall();
        
        
        DynamicApache.get().configure();
    }

}
