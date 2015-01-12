package com.covisint.util.apache.dynamic.ctrust;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.covisint.util.apache.dynamic.main.CmdLineParams;
import com.covisint.util.apache.dynamic.main.CmdLineParams.ParamNamesE;
import com.covisint.util.apache.dynamic.main.ServerTypesE;
import com.covisint.util.apache.dynamic.realm.model.RealmInfo;
import com.covisint.util.apache.dynamic.util.FileUtil;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

/**
 * Service responsible for generating the webagent conf informaiton.
 * 
 * @author Owais
 *
 */
public class WebagentConfService {

	/**
	 * The Webagent Conf Template File
	 */
	private final static String WEBAGENT_CONF_TEMPLATE_FILE = "com/covisint/util/apache/dynamic/ctrust/WebagentConfTemplate";

	/**
	 * internal Instance of this singleton service
	 */
	private final static WebagentConfService _INSTANCE = new WebagentConfService();

	/**
	 * Initializes the template
	 */
	private static Template WEBAGENT_CONF_TEMPLATE = initializeWebagentConfTemplate();

	/**
	 * No More than one instance of this service
	 */
	private WebagentConfService() {
	}

	/**
	 * Singleton patter
	 * 
	 * @return
	 */
	public static final WebagentConfService get() {
		return _INSTANCE;
	}

	/**
	 * Gets the Webagentconfiguration
	 * 
	 * @param serverType
	 * @param ctrustServer
	 * @param realmInfos
	 * @return
	 */
	public String webagentConf(List<RealmInfo> realmInfos) {
	    
		checkArgument(null != realmInfos && !realmInfos.isEmpty(), "Atleast one realm information needs to be provided.");
		
		CmdLineParams cmdlineParams = CmdLineParams.get();
		ServerTypesE serverType = ServerTypesE.valueOf(cmdlineParams.getParamValue(ParamNamesE.SERVER_TYPE));
        String ctrustServer = cmdlineParams.getParamValue(ParamNamesE.CTRUST_SERVER);
        String defaultRealm = cmdlineParams.getParamValue(ParamNamesE.DEFAULT_REALM);
        String ctrustAgentEnabled = cmdlineParams.getParamValue(ParamNamesE.ENABLE_CTRUST_AGENT);
        
		List<String> trustedDomains = new ArrayList<String>();
		List<String> virtualHostList = new ArrayList<String>();

		RealmInfo defaultRealmInfo = null;
		for (RealmInfo realmInfo : realmInfos) {
			trustedDomains.add(realmInfo.getDomain());

			if (realmInfo.getRealmId().equalsIgnoreCase(defaultRealm)) {
				defaultRealmInfo = realmInfo;
			}

			String virtualHostSnippet = getVirtualHostSnippet(serverType, ctrustAgentEnabled, realmInfo);
			virtualHostList.add(virtualHostSnippet);
		}

		checkState(null != defaultRealmInfo,
				"The Default Domain does not match any of the Realm Informations. Could not determine the default realm.");

		return WEBAGENT_CONF_TEMPLATE.execute(new WebagentConfInformationContainer(virtualHostList, trustedDomains, ctrustServer, defaultRealmInfo, serverType));
	}

	/**
	 * Get the virtual host snippet for each of the realm informations.
	 * 
	 * @param serverType
	 * @param realmInfo
	 * @return
	 */
	private String getVirtualHostSnippet(ServerTypesE serverType, String ctrustAgentEnabled, RealmInfo realmInfo) {
		String webagentHostName = getWebagentHostname(serverType, realmInfo);

		String virtualHostSnippet = VirtualHostSectionService.get().virtualHostSection(webagentHostName, serverType.getServerNamePrefix(), realmInfo, ctrustAgentEnabled);
		return virtualHostSnippet;
	}

	private static String getWebagentHostname(ServerTypesE serverType, RealmInfo realmInfo) {
		Method hostnameGetterMethod = null;
		String webagentHostName = null;
		try {
			hostnameGetterMethod = RealmInfo.class.getMethod("get" + serverType.getCommonName() + "Hostname");

			webagentHostName = (String) hostnameGetterMethod.invoke(realmInfo, new Object[] {});
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new IllegalStateException(e);
		}
		return webagentHostName;
	}

	private static Template initializeWebagentConfTemplate() {
		String templateFile = FileUtil.readClasspathFileAsString(WEBAGENT_CONF_TEMPLATE_FILE);
		return Mustache.compiler().escapeHTML(false).compile(templateFile);
	}

	public static class WebagentConfInformationContainer {
		private List<String> virtualHostSections;
		private List<String> trustedDomains;
		private String ctrustServer;
		private RealmInfo defaultRealmInfo;
		private String webagentHostServerNamePrefix;
		private String webagentHostname;

		WebagentConfInformationContainer(List<String> virtualHostSections, List<String> trustedDomains, String cleartrustServer,
				RealmInfo defaultRealmInfo, ServerTypesE serverType) {
			super();
			this.virtualHostSections = virtualHostSections;
			this.trustedDomains = trustedDomains;
			this.ctrustServer = cleartrustServer;
			this.defaultRealmInfo = defaultRealmInfo;
			this.webagentHostServerNamePrefix = serverType.getServerNamePrefix();
			this.webagentHostname = WebagentConfService.getWebagentHostname(serverType, defaultRealmInfo);
		}

		/**
		 * @return the virtualHostSections
		 */
		public List<String> getVirtualHostSections() {
			return virtualHostSections;
		}

		/**
		 * @return the trustedDomains
		 */
		public List<String> getTrustedDomains() {
			return trustedDomains;
		}

		/**
		 * @return the cleartrustServer
		 */
		public String getCtrustServer() {
			return ctrustServer;
		}

		/**
		 * @return the defaultRealmInfo
		 */
		public RealmInfo getDefaultRealmInfo() {
			return defaultRealmInfo;
		}

		/**
		 * @return the webagentHostServerNamePrefix
		 */
		public String getWebagentHostServerNamePrefix() {
			return webagentHostServerNamePrefix;
		}

		/**
		 * @return the webagentHostname
		 */
		public String getWebagentHostname() {
			return webagentHostname;
		}

	}

}
