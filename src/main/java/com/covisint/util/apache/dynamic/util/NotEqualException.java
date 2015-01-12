/**
 * 
 */
package com.covisint.util.apache.dynamic.util;

/**
 * Used to indicate a non equals relation ship. This is just a indicator
 * exception.
 * 
 * @author Owais
 *
 */
public class NotEqualException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1130294876896133257L;

	private Object obj1;
	private Object obj2;

	public NotEqualException(Object obj1, Object obj2) {
		super(String.format("%s not eaual to %s", obj1, obj2));
		this.obj1 = obj1;
		this.obj2 = obj2;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the obj1
	 */
	public Object getObj1() {
		return obj1;
	}

	/**
	 * @return the obj2
	 */
	public Object getObj2() {
		return obj2;
	}
	
	
}
