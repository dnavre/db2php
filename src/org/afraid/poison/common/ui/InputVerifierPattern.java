/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.common.ui;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

/**
 *
 * @author poison
 */
public class InputVerifierPattern extends InputVerifier {

	public static final int LENGTH_UNLIMITED=-1;
	private int maxLength=LENGTH_UNLIMITED;
	private Pattern pattern=null;

	public InputVerifierPattern() {
		super();
	}

	/**
	 * @return the maxLength
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * @param maxLength the maxLength to set
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength=maxLength;
	}

	/**
	 * @return the pattern
	 */
	public Pattern getPattern() {
		return pattern;
	}

	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(Pattern pattern) {
		this.pattern=pattern;
	}

	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(String pattern) {
		setPattern(Pattern.compile(pattern));
	}

	@Override
	public boolean verify(JComponent input) {
		if (!(input instanceof JTextComponent)) {
			return true;
		}
		boolean valid=true;
		JTextComponent textComponent=(JTextComponent) input;
		if (getMaxLength()!=LENGTH_UNLIMITED&&textComponent.getText().length()>getMaxLength()) {
			valid=false;
		}
		if (null!=getPattern()) {
			Matcher matcher=getPattern().matcher(textComponent.getText());
			valid=matcher.lookingAt();
		}
		/*
		if (valid) {
			input.setBackground(Color.WHITE);
		} else {
			input.setBackground(Color.RED);
		}
		*/
		return valid;
	}
}
