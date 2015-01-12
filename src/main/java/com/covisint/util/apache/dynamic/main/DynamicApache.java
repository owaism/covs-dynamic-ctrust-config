/**
 * 
 */
package com.covisint.util.apache.dynamic.main;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.covisint.util.apache.dynamic.ctrust.WebagentConfService;
import com.covisint.util.apache.dynamic.main.CmdLineParams.ParamNamesE;
import com.covisint.util.apache.dynamic.realm.RealmInfoService;
import com.covisint.util.apache.dynamic.realm.RealmInfoUtil;
import com.covisint.util.apache.dynamic.realm.model.RealmInfo;
import com.covisint.util.apache.dynamic.util.FileUtil;

/**
 * @author omohamed
 */
public class DynamicApache {

    /**
     * Logger for this class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicApache.class);

    /**
     * Singleton Instance
     */
    private static final DynamicApache _INSTANCE = new DynamicApache();

    public final static DynamicApache get() {
        return _INSTANCE;
    }

    public final int configure() {
        LOGGER.info("Starting Dynamic Apache..");
        try {
            LOGGER.debug("Reading Command Line Attributes...");
            CmdLineParams cmdlineParams = CmdLineParams.get();
            LOGGER.debug("Command Line Parameters: {}", cmdlineParams);

            String result = "";

            List<RealmInfo> realmInfos = RealmInfoService.get().realmInfo();
            if (RealmInfoUtil.hasRealmInfoChange(realmInfos)) {
                LOGGER.info("Realm Info Has Changed.");
                File webagentConfFile = generateWebagentConfFile(cmdlineParams, realmInfos);
                LOGGER.debug("New Web Agent Configuration written to {}", webagentConfFile.getAbsolutePath());
                RealmInfoUtil.serializeRealmInfo(realmInfos);
                LOGGER.debug("New Realm Info Serialized Successfully!!");
                result = webagentConfFile.getAbsolutePath();
            } else {
                LOGGER.info("No Change In Realm Info. So nothing will be done.");
            }
            FileUtil.writeToFile(result, new File(cmdlineParams.getParamValue(ParamNamesE.RESULTS_FILE)));
            LOGGER.debug("Result: {}. Written To Results file.");
        } catch (Throwable ex) {
            LOGGER.error("Exception from Dynamic Apache Java Program: ", ex);
            throw ex;
        }

        LOGGER.info("Dynamic Apache Program ended successfully..");
        return 0;

    }

    private File generateWebagentConfFile(CmdLineParams cmdlineParams, List<RealmInfo> realmInfos) {
        
        String confDir = cmdlineParams.getParamValue(ParamNamesE.WEB_AGENT_CONF_LOCATION);

        String webagentConf =
                WebagentConfService.get().webagentConf(realmInfos);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss.SSS");
        File webagentConfFile =
                new File(confDir.concat(File.separator).concat("webagent.conf-").concat(sdf.format(new Date())));
        FileUtil.writeToFile(webagentConf, webagentConfFile);
        return webagentConfFile;
    }

    public static void main(String[] args) {
        System.exit(DynamicApache.get().configure());
    }
}
