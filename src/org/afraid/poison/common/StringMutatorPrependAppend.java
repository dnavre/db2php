/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.common;

/**
 *
 * @author poison
 */
public class StringMutatorPrependAppend implements StringMutator {

	private String prepend;
	private String append;

	public StringMutatorPrependAppend() {
	}

	public StringMutatorPrependAppend(String prepend, String append) {
		setPrepend(prepend);
		setAppend(append);
	}

	/**
	 * @return the prepend
	 */
	public String getPrepend() {
		return prepend;
	}

	/**
	 * @param prepend the prepend to set
	 */
	public void setPrepend(String prepend) {
		this.prepend=prepend;
	}

	/**
	 * @return the append
	 */
	public String getAppend() {
		return append;
	}

	/**
	 * @param append the append to set
	 */
	public void setAppend(String append) {
		this.append=append;
	}

	public String transform(Object input) {
		StringBuilder s=new StringBuilder();
		if (null!=getPrepend()) {
			s.append(getPrepend());
		}
		s.append(input);
		if (null!=getAppend()) {
			s.append(getAppend());
		}
		return s.toString();
	}
}
