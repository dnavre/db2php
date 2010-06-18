/*
 * Copyright (C) 2008 Andreas Schnaiter
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */
package org.afraid.poison.db2php.generator;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import org.afraid.poison.camelcase.CamelCaseFairy;
import org.afraid.poison.db2php.generator.databaselayer.DatabaseLayer;

/**
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
public class Settings {

	private DatabaseLayer databaseLayer=DatabaseLayer.PDO;
	private boolean generateChecks=false;
	private boolean trackFieldModifications=true;
	private boolean fluentInterface=true;
	private boolean useInterfaces=true;
	private boolean ezComponents=false;
	private CamelCaseFairy camelCaseFairy=null;
	private String classNamePrefix=new String();
	private String classNameSuffix=new String();
	private File outputDirectory;
	private String identifierQuoteString=null;
	private Set<String> additionalInterfaces=new LinkedHashSet<String>();
	private boolean createBackup=true;

	public Settings() {
	}

	public Settings(Settings parentSettings) {
		setDatabaseLayer(parentSettings.getDatabaseLayer());
		setGenerateChecks(parentSettings.isGenerateChecks());
		setTrackFieldModifications(parentSettings.isTrackFieldModifications());
		setFluentInterface(parentSettings.isFluentInterface());
		setUseInterfaces(parentSettings.isUseInterfaces());
		setEzComponents(parentSettings.isEzComponents());
		setCamelCaseFairy(parentSettings.getCamelCaseFairy());
		setClassNamePrefix(parentSettings.getClassNamePrefix());
		setClassNameSuffix(parentSettings.getClassNameSuffix());
		setOutputDirectory(parentSettings.getOutputDirectory());
		setIdentifierQuoteString(parentSettings.getIdentifierQuoteString());
		getAdditionalInterfaces().addAll(parentSettings.getAdditionalInterfaces());
		setCreateBackup(parentSettings.isCreateBackup());
	}

	/**
	 * @return the databaseLayer
	 */
	public DatabaseLayer getDatabaseLayer() {
		return databaseLayer;
	}

	/**
	 * @param databaseLayer the databaseLayer to set
	 */
	public void setDatabaseLayer(DatabaseLayer databaseLayer) {
		this.databaseLayer=databaseLayer;
	}

	/**
	 * @return the generateChecks
	 */
	public boolean isGenerateChecks() {
		return generateChecks;
	}

	/**
	 * @param generateChecks the generateChecks to set
	 */
	public void setGenerateChecks(boolean generateChecks) {
		this.generateChecks=generateChecks;
	}

	/**
	 * @return the trackFieldModifications
	 */
	public boolean isTrackFieldModifications() {
		return trackFieldModifications;
	}

	/**
	 * @param trackFieldModifications the trackFieldModifications to set
	 */
	public void setTrackFieldModifications(boolean trackFieldModifications) {
		this.trackFieldModifications=trackFieldModifications;
	}

	/**
	 * @return the fluentInterface
	 */
	public boolean isFluentInterface() {
		return fluentInterface;
	}

	/**
	 * @param fluentInterface the fluentInterface to set
	 */
	public void setFluentInterface(boolean fluentInterface) {
		this.fluentInterface=fluentInterface;
	}

	/**
	 * @return the ezComponents
	 */
	public boolean isEzComponents() {
		return ezComponents;
	}

	/**
	 * @param ezComponents the ezComponents to set
	 */
	public void setEzComponents(boolean ezComponents) {
		this.ezComponents=ezComponents;
	}

	/**
	 * @return the camelCaseFairy
	 */
	public CamelCaseFairy getCamelCaseFairy() {
		return camelCaseFairy;
	}

	/**
	 * @param camelCaseFairy the camelCaseFairy to set
	 */
	public void setCamelCaseFairy(CamelCaseFairy camelCaseFairy) {
		this.camelCaseFairy=camelCaseFairy;
	}

	/**
	 * @return the classNamePrefix
	 */
	public String getClassNamePrefix() {
		return classNamePrefix;
	}

	/**
	 * @param classNamePrefix the classNamePrefix to set
	 */
	public void setClassNamePrefix(String classNamePrefix) {
		this.classNamePrefix=classNamePrefix;
	}

	/**
	 * @return the classNameSuffix
	 */
	public String getClassNameSuffix() {
		return classNameSuffix;
	}

	/**
	 * @param classNameSuffix the classNameSuffix to set
	 */
	public void setClassNameSuffix(String classNameSuffix) {
		this.classNameSuffix=classNameSuffix;
	}

	/**
	 * @return the outputDirectory
	 */
	public File getOutputDirectory() {
		return outputDirectory;
	}

	/**
	 * @param outputDirectory the outputDirectory to set
	 */
	public void setOutputDirectory(File outputDirectory) {
		this.outputDirectory=outputDirectory;
	}

	/**
	 * @return the identifierQuoteString
	 */
	public String getIdentifierQuoteString() {
		return identifierQuoteString;
	}

	/**
	 * @param identifierQuoteString the identifierQuoteString to set
	 */
	public void setIdentifierQuoteString(String identifierQuoteString) {
		this.identifierQuoteString=identifierQuoteString;
	}

	/**
	 * @return the useInterfaces
	 */
	public boolean isUseInterfaces() {
		return useInterfaces;
	}

	/**
	 * @param useInterfaces the useInterfaces to set
	 */
	public void setUseInterfaces(boolean useInterfaces) {
		this.useInterfaces=useInterfaces;
	}

	public Set<String> getAdditionalInterfaces() {
		return additionalInterfaces;
	}

	public void setAdditionalInterfaces(Set<String> additionalInterfaces) {
		this.additionalInterfaces=additionalInterfaces;
	}

	public boolean isCreateBackup() {
		return createBackup;
	}

	public void setCreateBackup(boolean createBackup) {
		this.createBackup=createBackup;
	}


	@Override
	public String toString() {
		return new StringBuilder().append("databaseLayer:").append(getDatabaseLayer()).append("\n").append("generateChecks:").append(isGenerateChecks()).append("\n").append("trackFieldModifications:").append(isTrackFieldModifications()).append("\n").append("classNamePrefix:").append(getClassNamePrefix()).append("\n").append("classNameSuffix:").append(getClassNameSuffix()).append("\n").append("identifierQuoteString:").append(getIdentifierQuoteString()).append("\n").append("outputDirectory:").append(getOutputDirectory()).append("\n").toString();
	}
}
