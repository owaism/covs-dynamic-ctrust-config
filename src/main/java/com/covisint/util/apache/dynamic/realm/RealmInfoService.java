/**
 * 
 */
package com.covisint.util.apache.dynamic.realm;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.covisint.util.apache.dynamic.main.ApplicationProperties;
import com.covisint.util.apache.dynamic.main.CmdLineParams;
import com.covisint.util.apache.dynamic.main.CmdLineParams.ParamNamesE;
import com.covisint.util.apache.dynamic.realm.model.RealmInfo;

/**
 * Gets the Realm Information form external services
 * 
 * @author Owais
 *
 */
public class RealmInfoService {

	private static final RealmInfoService _INSTANCE = new RealmInfoService();
	
	/**
	 * Logger for this class
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private String configurationServiceURL;
	private String realm;
	private Client restClient;

	private RealmInfoService() {
		this.configurationServiceURL = CmdLineParams.get().getParamValue(ParamNamesE.CONFIGURATION_SERVICE_URL);
		this.realm = CmdLineParams.get().getParamValue(ParamNamesE.DEFAULT_REALM);
		restClient = ClientBuilder.newClient();
		restClient.register(LoggingFilter.class).register(JacksonFeature.class);
	}

	public static RealmInfoService get() {
		return _INSTANCE;
	}

	/**
	 * Gets the realm informaiton from external service.
	 * @return
	 */
	public List<RealmInfo> realmInfo() {
		logger.debug("Getting realm information from {}",this.configurationServiceURL);
		WebTarget target = restClient.target(this.configurationServiceURL);
		RealmInfo[] realmInfoArr = target
				.request()
				.header("X-Realm", realm)
				.header("x-requestor", ApplicationProperties.NAME.value())
				.header("x-requestor-app", ApplicationProperties.NAME.value())
				.accept(ApplicationProperties.CONFIGURATION_SERVICE_ACCEPTS_MEDIA_TYPE.value())
				.get(RealmInfo[].class);
		
		for(RealmInfo realmInfo:realmInfoArr){
			RealmInfoUtil.sanitize(realmInfo);
		}
		
		logger.debug("Got Realm Information from service successfully!!!");
		return Arrays.asList(realmInfoArr);
	}
	
	/**
	 * Setter method required for Mocking\testing.
	 * @param restClient
	 */
	public RealmInfoService setRestClient(Client restClient){
	    this.restClient = restClient;
	    return this;
	}
}
