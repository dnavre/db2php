/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.common.string;

/**
 *
 * @author poison
 */
public class StringMutatorPrependAppend implements StringMutator {

	private String prepend;
	private String append;

	/**
	 * CTOR
	 */
	public StringMutatorPrependAppend() {
	}

	/**
	 * CTOR
	 *
	 * @param prepend string to prepend
	 * @param append string to append
	 */
	public StringMutatorPrependAppend(String prepend, String append) {
		setPrepend(prepend);
		setAppend(append);
	}

	/**
	 * @return the prepend string
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
	 * @return the append string
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

	/**
	 * prepend and append specified string to passed value
	 *
	 * @param input the input
	 * @return the resulting string
	 */
	@Override
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
