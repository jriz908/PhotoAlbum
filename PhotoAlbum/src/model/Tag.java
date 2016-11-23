package model;

import java.io.Serializable;

/**
 * 
 * @author Jacob Rizer
 * @author Terence Williams
 *
 * This is our Tag class. It represents one tag with a type and value.
 */
public class Tag implements Serializable{
	/**
	 * Tag type
	 */
	private String tag;
	
	/**
	 * Tag value
	 */
	private String value;
	
	static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 * 
	 * @param t - type
	 * @param v - value
	 */
	public Tag (String t, String v) {
		tag = t;
		value = v;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String toString () {
		return value;
	}
	
}