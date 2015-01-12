/**
 * 
 */
package com.covisint.util.apache.dynamic.main;

import static com.google.common.base.Preconditions.checkState;

import java.util.HashMap;
import java.util.Map;

/**
 * Model containing all command line parameters.
 * @author omohamed
 *
 */
public final class CmdLineParams {
	
	/**
	 * Command Line Parameter Names
	 * @author Owais
	 *
	 */
	public static enum ParamNamesE{
		/** Directory Location where the webagent conf file has to be put in */
		WEB_AGENT_CONF_LOCATION(true),
		/**
		 * URL of the Service which provides all the configurations for web agent conf
		 */
		CONFIGURATION_SERVICE_URL(true),
		
		/**
		 * The Default realm for this web agent
		 */
		DEFAULT_REALM(true),
		
		/**
		 * Clear Trust Server
		 */
		CTRUST_SERVER(true),
		
		/**
		 * Type of the server this is
		 */
		SERVER_TYPE(true),
		
		/**
		 * Enable CTRUST Agent or not
		 */
		ENABLE_CTRUST_AGENT(true),
		/**
		 * Results file where the file outputs the result
		 */
		RESULTS_FILE(true),
		
		/**
		 * A Random Identifier identifying 
		 */
		CACHE_FILE_LOCATION(true);
		
		ParamNamesE(boolean mandatory){
			this.mandatory = mandatory;
		}
		
		private boolean mandatory;
		
		/**
		 * 
		 * @return
		 */
		public boolean isMandatory() {
			return mandatory;
		}
	}
	
	/**
	 * 
	 */
	private static final CmdLineParams _CMD_LINE_PARAMS = new CmdLineParams();
	
	private final Map<ParamNamesE, String> cmdLineParams = new HashMap<CmdLineParams.ParamNamesE, String>((int)Math.ceil(ParamNamesE.values().length/0.75)+1);
	
	
	private String getSystemProperty(ParamNamesE propertyName){
		String value = System.getProperty(propertyName.name());
		checkState(!propertyName.isMandatory() || null != value, "Property '%s' was not passed as part of the command line properties", propertyName);
		return value;
	}
	
	private CmdLineParams(){
		for(ParamNamesE paramName: ParamNamesE.values()){
			cmdLineParams.put(paramName, getSystemProperty(paramName));
		}
	}
	
	
	public static CmdLineParams get(){
		return _CMD_LINE_PARAMS;
	}
	
	/**
	 * Gets the parameter values.
	 * @param paramName
	 * @return
	 */
	public String getParamValue(ParamNamesE paramName){
		return cmdLineParams.get(paramName);
	}
	
	@Override
	public String toString() {
	    return _CMD_LINE_PARAMS.toString();
	}
	
}
