/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.db2php.generator.xml;

import java.io.File;
import org.afraid.poison.db2php.generator.databaselayer.DatabaseLayer;
import org.jdom.Element;

/**
 *
 * @author Andreas Schnaiter <as@euro-solutions.de>
 */
public class Settings extends org.afraid.poison.db2php.generator.Settings {

	public Settings() {
	}

	public Settings(org.afraid.poison.db2php.generator.Settings parentSettings) {
		super(parentSettings);
	}

	
	public static Settings fromElement(Element element) {
		return fromElement(element, null);
	}

	public static Settings fromElement(Element element, Settings parentSettings) {
		Settings settings=null;
		if (null==parentSettings) {
			settings=new Settings();
			settings.setDatabaseLayer(DatabaseLayer.PDO);
			settings.setIdentifierQuoteString("`");
		} else {
			settings=new Settings(parentSettings);
		}
		if (null!=element.getAttributeValue("databaseLayer")) {

		}
		if (null!=element.getAttributeValue("destinationPath")) {
			settings.setOutputDirectory(new File(element.getAttributeValue("destinationPath")));
		}
		if (null!=element.getAttributeValue("classNamePrefix")) {
			settings.setClassNamePrefix(element.getAttributeValue("classNamePrefix"));
		}
		if (null!=element.getAttributeValue("classNameSuffix")) {
			settings.setClassNameSuffix(element.getAttributeValue("classNameSuffix"));
		}
		if (null!=element.getAttributeValue("identifierQuote")) {
			settings.setIdentifierQuoteString(element.getAttributeValue("identifierQuote"));
		}
		if (null!=element.getAttributeValue("calmelCaseFairy")) {
			//settings.setCamelCaseFairy("false".equals(element.getAttributeValue("calmelCaseFairy")));
		}
		if (null!=element.getAttributeValue("trackFieldModifications")) {
			settings.setTrackFieldModifications("true".equals(element.getAttributeValue("trackFieldModifications")));
		}
		if (null!=element.getAttributeValue("fluentInterface")) {
			settings.setFluentInterface("true".equals(element.getAttributeValue("fluentInterface")));
		}
		if (null!=element.getAttributeValue("ezcSupport")) {
			settings.setEzComponents("true".equals(element.getAttributeValue("ezcSupport")));
		}
		for (Object interfacesO : element.getChildren("interface")) {
			settings.getAdditionalInterfaces().add(((Element) interfacesO).getAttributeValue("name").trim());
		}
		return settings;
	}
}
