package com.covisint.util.apache.dynamic.realm;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.covisint.util.apache.dynamic.main.CmdLineParams;
import com.covisint.util.apache.dynamic.main.CmdLineParams.ParamNamesE;
import com.covisint.util.apache.dynamic.realm.model.RealmInfo;
import com.covisint.util.apache.dynamic.util.FileUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RealmInfoUtilTest {

    private static final String SERIALIZE_FILE_PATH = CmdLineParams.get().getParamValue(ParamNamesE.CACHE_FILE_LOCATION);

    @Test
    public void testHasRealmInfoChange() throws JsonParseException, JsonMappingException, IOException {
        File serializeFile = new File(SERIALIZE_FILE_PATH);
        if (serializeFile.exists()) {
            serializeFile.delete();
        }
        ObjectMapper mapper = new ObjectMapper();
        List<RealmInfo> realmInfoList =
                Arrays.asList(mapper.readValue(
                        FileUtil.readClasspathFileAsString("com/covisint/util/apache/dynamic/ctrust/SampleConfigurationServiceResponse"),
                        RealmInfo[].class));
        
        
        Assert.assertTrue(RealmInfoUtil.hasRealmInfoChange(realmInfoList));
        
        RealmInfoUtil.serializeRealmInfo(realmInfoList);
        
        
        Assert.assertFalse(RealmInfoUtil.hasRealmInfoChange(realmInfoList));
        
        List<RealmInfo> newRealmInfoList =
                Arrays.asList(mapper.readValue(
                        FileUtil.readClasspathFileAsString("com/covisint/util/apache/dynamic/ctrust/SampleConfigurationServiceResponse2"),
                        RealmInfo[].class));
        
        Assert.assertTrue(RealmInfoUtil.hasRealmInfoChange(newRealmInfoList));
    }

}
