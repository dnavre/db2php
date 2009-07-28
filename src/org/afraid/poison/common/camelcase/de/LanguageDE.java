/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.common.camelcase.de;

import org.afraid.poison.common.camelcase.en.*;
import org.afraid.poison.common.FileUtil;
import org.afraid.poison.common.camelcase.CamelCaseFairy;
import org.afraid.poison.common.camelcase.Dictionary.Descriptor;

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
