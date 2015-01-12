/**
 * 
 */
package com.covisint.util.apache.dynamic.main;

/**
 * Defines the different server types on which the web agent can be configured.
 * 
 * @author Owais
 *
 */
public enum ServerTypesE {

	PROVISIONER("admin"), AUTHENTICATOR("sso"), IDENTITY_BROKER("tib"), PORTAL(
			"portal"), API_MANAGER(""), RUNTIME("runtime"), FST("fst"), SCMANAGER(
			"scmanager");

	private ServerTypesE(String serverNamePrefix) {
		this.serverNamePrefix = serverNamePrefix;
	}

	private String serverNamePrefix;

	public String getServerNamePrefix() {
		return serverNamePrefix;
	}

	public String getCommonName() {
		return toCamelCase(this.name());
	}

	private static String toCamelCase(String s) {
		String[] parts = s.split("_");
		String camelCaseString = "";
		for (String part : parts) {
			camelCaseString = camelCaseString + toProperCase(part);
		}
		return camelCaseString;
	}

	private static String toProperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}
}
