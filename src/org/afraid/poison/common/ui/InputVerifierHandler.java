/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.common.ui;

import javax.swing.JComponent;

/**
 *
 * @author poison
 */
public interface InputVerifierHandler {
	public boolean performPostValidationAction(JComponent input, boolean valid);

}
