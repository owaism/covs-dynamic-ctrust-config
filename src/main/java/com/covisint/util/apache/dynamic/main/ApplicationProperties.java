/**
 * 
 */
package com.covisint.util.apache.dynamic.main;

/**
 * Application Properties that will seldom (never) change
 * @author Owais
 *
 */
public enum ApplicationProperties {
	
	NAME("dynamic-apache-manager"),
	
	CONFIGURATION_SERVICE_ACCEPTS_MEDIA_TYPE("application/vnd.com.covisint.platform.director.instance.v1+json");
	
	public String value(){
		return this.value;
	}
	
	/**
	 * Value of the app property
	 */
	private final String value;
	private ApplicationProperties(final String value){
		this.value = value;
	}
}
