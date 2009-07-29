/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.camelcase.de;

import org.afraid.poison.common.FileUtil;
import org.afraid.poison.camelcase.Dictionary.Descriptor;

/**
 *
 * @author poison
 */
public class LanguageDE extends Descriptor {

	@Override
	public String[] getLanguageFiles() {
		return new String[] {new StringBuilder(FileUtil.getPackagePath(getClass())).append("/wordlist.de").toString()};
	}

	@Override
	public String getId() {
		return "de";
	}

}
