/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.common.camelcase.deen;

import java.util.Comparator;
import org.afraid.poison.common.camelcase.Dictionary.DescriptorSorted;
import org.afraid.poison.common.camelcase.de.LanguageDE;
import org.afraid.poison.common.camelcase.en.LanguageEN;
import org.afraid.poison.common.string.CharSequenceLengthComparator;

/**
 *
 * @author poison
 */
public class LanguageDEEN extends DescriptorSorted {

	public LanguageDEEN() {
		super(new CharSequenceLengthComparator(false));
	}

	@Override
	public String[] getLanguageFiles() {
		return new String[]{new LanguageDE().getLanguageFiles()[0], new LanguageEN().getLanguageFiles()[0]};
	}

	@Override
	public String getId() {
		return "deen";
	}
}
