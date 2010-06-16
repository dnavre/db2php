/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.camelcase.en;

import org.afraid.poison.common.FileUtil;
import org.afraid.poison.camelcase.Dictionary.Descriptor;

/**
 *
 * @author poison
 */
public class LanguageEN extends Descriptor {

	@Override
	public String[] getLanguageFiles() {
		return new String[] {new StringBuilder(FileUtil.getPackagePath(getClass())).append("/wordlist.en").toString()};
	}

	@Override
	public String getId() {
		return "en";
	}

}
