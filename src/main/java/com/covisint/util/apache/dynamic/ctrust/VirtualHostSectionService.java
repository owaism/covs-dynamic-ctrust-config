/**
 * 
 */
package com.covisint.util.apache.dynamic.ctrust;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.covisint.util.apache.dynamic.realm.model.RealmInfo;
import com.covisint.util.apache.dynamic.util.FileUtil;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

/**
 * Used to create the Virtual Host Sections for the Webagent Config File
 * @author Owais
 */
public class VirtualHostSectionService {

    private final static String VIRTUAL_HOST_SECTION_SNIPPET_FILE =
            "com/covisint/util/apache/dynamic/ctrust/VirtualHostSnippet";

    private static final VirtualHostSectionService _INSTANCE = new VirtualHostSectionService();

    private static Template VIRTAUL_HOST_SECTION_TEMPLATE = initializeVirtualHostSectionTemplate();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
     * Private Constructor
     */
    private VirtualHostSectionService() {
    }

    public static VirtualHostSectionService get() {
        return _INSTANCE;
    }

    /**
     * Generates a particular Virtual Host Section which will be part of a larger file.
     * @param webagentHostName
     * @param webagentHostServerNamePrefix
     * @param realmInfo
     * @return
     */
    public String virtualHostSection(String webagentHostName, String webagentHostServerNamePrefix, RealmInfo realmInfo,
            String ctrustAgentEnabled) {
        String webserverName = getWebServerName(webagentHostServerNamePrefix, realmInfo);

        VirtualHostSectionInformationContainer infoContainer =
                new VirtualHostSectionInformationContainer(webagentHostName, webagentHostServerNamePrefix, realmInfo,
                        ctrustAgentEnabled, webserverName);

        return VIRTAUL_HOST_SECTION_TEMPLATE.execute(infoContainer);
    }

    /**
     * @param webagentHostServerNamePrefix
     * @param realmInfo
     * @return
     */
    private String getWebServerName(String webagentHostServerNamePrefix, RealmInfo realmInfo) {
        logger.debug("Webserver Name Key: {}", "cleartrust.agent." + webagentHostServerNamePrefix + "_web_server_name");
        String webserverName =
                realmInfo.getRealm().getAttributeMap()
                        .get("cleartrust-agent-" + webagentHostServerNamePrefix + "_web_server_name");
        if (null == webserverName) {
            webserverName = String.format("%s_%s", webagentHostServerNamePrefix, realmInfo.getRealmId());
        }

        logger.debug("Web Server Name: " + webserverName);
        return webserverName;
    }

    private static final Template initializeVirtualHostSectionTemplate() {
        String virtualHostTemplate = FileUtil.readClasspathFileAsString(VIRTUAL_HOST_SECTION_SNIPPET_FILE);
        return Mustache.compiler().compile(virtualHostTemplate);
    }

    /**
     * Just a locally used container class
     * @author Owais
     */
    public static class VirtualHostSectionInformationContainer {
        private String webagentHostname;
        private String ctrustAgentEnabled;
        private String webagentHostServerNamePrefix;
        private RealmInfo realmInfo;
        private String webserverName;

        public VirtualHostSectionInformationContainer(String webagentHostname, String webagentHostServerNamePrefix,
                RealmInfo realmInfo, String ctrustAgentEnabled, String webserverName) {
            super();
            this.webagentHostname = webagentHostname;
            this.realmInfo = realmInfo;
            this.webagentHostServerNamePrefix = webagentHostServerNamePrefix;
            this.ctrustAgentEnabled = ctrustAgentEnabled;
            this.webserverName = webserverName;
        }

        /**
         * @return the webagentHostname
         */
        public String getWebagentHostname() {
            return webagentHostname;
        }

        /**
         * @return the realmInfo
         */
        public RealmInfo getRealmInfo() {
            return realmInfo;
        }

        /**
         * @return the webagentHostServerNamePrefix
         */
        public String getWebagentHostServerNamePrefix() {
            return webagentHostServerNamePrefix;
        }

        /**
         * @return the ctrustAgentEnabled
         */
        public String getCtrustAgentEnabled() {
            return ctrustAgentEnabled;
        }

        /**
         * @return the webserverName
         */
        public String getWebserverName() {
            return webserverName;
        }

    }

}
