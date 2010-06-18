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
package org.afraid.poison.db2php;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.afraid.poison.camelcase.CamelCaseFairy;
import org.afraid.poison.camelcase.de.LanguageDE;
import org.afraid.poison.camelcase.deen.LanguageDEEN;
import org.afraid.poison.camelcase.en.LanguageEN;
import org.afraid.poison.db2php.generator.Settings;
import org.afraid.poison.db2php.generator.CodeGenerator;
import org.afraid.poison.db2php.generator.Table;
import org.afraid.poison.db2php.generator.databaselayer.DatabaseLayer;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileChangeAdapter;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;

public final class PhpClassWizardIterator implements WizardDescriptor.InstantiatingIterator {

	private int index;
	private WizardDescriptor wizard;
	private WizardDescriptor.Panel[] panels;

	/**
	 * Initialize panels representing individual wizard's steps and sets
	 * various properties for them influencing wizard appearance.
	 */
	private WizardDescriptor.Panel[] getPanels() {
		if (panels==null) {
			panels=new WizardDescriptor.Panel[]{
						new PhpClassWizardPanelTableSelection(wizard),
						new PhpClassWizardPanel2(wizard)
					};
			String[] steps=createSteps();
			for (int i=0; i<panels.length; i++) {
				Component c=panels[i].getComponent();
				if (steps[i]==null) {
					// Default step name to component name of panel. Mainly
					// useful for getting the name of the target chooser to
					// appear in the list of steps.
					steps[i]=c.getName();
				}
				if (c instanceof JComponent) { // assume Swing components
					JComponent jc=(JComponent) c;
					// Sets step number of a component
					// TODO if using org.openide.dialogs >= 7.8, can use WizardDescriptor.PROP_*:
					jc.putClientProperty("WizardPanel_contentSelectedIndex", new Integer(i));
					// Sets steps names for a panel
					jc.putClientProperty("WizardPanel_contentData", steps);
					// Turn on subtitle creation on each step
					jc.putClientProperty("WizardPanel_autoWizardStyle", Boolean.TRUE);
					// Show steps on the left side with the image on the background
					jc.putClientProperty("WizardPanel_contentDisplayed", Boolean.TRUE);
					// Turn on numbering of all steps
					jc.putClientProperty("WizardPanel_contentNumbered", Boolean.TRUE);
				}
			}

		}
		return panels;
	}

	/**
	 * write code for passed tables
	 * @param tables the tables to write code for
	 * @param settings the generator settings
	 * @return the tables for which the code could not be written
	 */
	private Set<Table> writeCode(final Set<Table> tables, final Settings settings) {
		final Set<Table> failed=new LinkedHashSet<Table>();
		
		final ProgressHandle ph=ProgressHandleFactory.createHandle("Generating PHP Entity classes");
		
		ph.setInitialDelay(0);
		ph.start(tables.size());
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					int done=0;
					CodeGenerator generator;
					FileObject fob;
					for (Table t : tables) {
						generator=new CodeGenerator(t, settings);
						generator.setCamelCaseFairy(settings.getCamelCaseFairy());
						ph.progress(t.getName());
						try {
							generator.writeCode();
							//fob=FileUtil.toFileObject(generator.getFile());
							FileUtil.refreshFor(generator.getFile());
							//openFile(generator.getFile());
						} catch (IOException ex) {
							failed.add(t);
							Exceptions.printStackTrace(ex);
						}
						ph.progress(++done);
					}
				} finally {
					ph.finish();
				}
			}
		}).start();


		return failed;
	}

	/**
	 * try to open file in editor
	 *
	 * @param file the file to open
	 */
	private void openFile(File file) {
		try {
			FileObject fob=FileUtil.toFileObject(file);
			
			//fob.
			if (fob!=null) {
				DataObject dob=DataObject.find(fob);
				OpenCookie oc=dob.getLookup().lookup(OpenCookie.class);
				if (oc!=null) {
					oc.open();
				}
			}
		} catch (Exception ex) {
			//Exceptions.printStackTrace(ex);
		}
	}

	@Override
	public Set instantiate() throws IOException {
		if (wizard.getValue()==WizardDescriptor.FINISH_OPTION) {
			PhpClassVisualPanelTableSelection p1=(PhpClassVisualPanelTableSelection) getPanels()[0].getComponent();
			Set<Table> tables=p1.getSelected();

			PhpClassVisualPanel2 p2=(PhpClassVisualPanel2) getPanels()[1].getComponent();
			Settings settings=new Settings();
			settings.setDatabaseLayer((DatabaseLayer) p2.getDatabaseLayerSelection().getSelectedItem());
			settings.setGenerateChecks(p2.getGenerateChecksSelection().isSelected());
			settings.setFluentInterface(p2.getFluentInterfaceSelection().isSelected());
			settings.setEzComponents(p2.getEzcSupportSelection().isSelected());
			settings.setTrackFieldModifications(p2.getTrackModificationsSelection().isSelected());
			settings.setClassNamePrefix(p2.getClassNamePrefix().getText());
			settings.setClassNameSuffix(p2.getClassNameSuffix().getText());
			settings.setOutputDirectory(p2.getDirectory());
			settings.setIdentifierQuoteString((String) p2.getIdentifierQuoteString().getSelectedItem());
			String ccfs=(String) p2.getCamelCaseFairy().getSelectedItem();
			if ("en".equals(ccfs)) {
				settings.setCamelCaseFairy(new CamelCaseFairy(new LanguageEN()));
			} else if ("de".equals(ccfs)) {
				settings.setCamelCaseFairy(new CamelCaseFairy(new LanguageDE()));
			} else if ("deen".equals(ccfs)) {
				settings.setCamelCaseFairy(new CamelCaseFairy(new LanguageDEEN()));
			}
			p2.storeSettings();
			Set<Table> failed=writeCode(tables, settings);
			if (!failed.isEmpty()) {
				CodeGenerator generator;
				StringBuilder failedMessage=new StringBuilder("The following files were not created:\n");
				for (Table t : failed) {
					generator=new CodeGenerator(t, settings);
					failedMessage.append(generator.getFileName());
					if (generator.getFile().exists()) {
						failedMessage.append(" (already exists, refusing to overwrite)");
					}
					failedMessage.append("\n");
				}
				DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(failedMessage));
			}

		}
		return Collections.EMPTY_SET;
	}

	@Override
	public void initialize(WizardDescriptor wizard) {
		this.wizard=wizard;

	}

	@Override
	public void uninitialize(WizardDescriptor wizard) {
		panels=null;
	}

	@Override
	public WizardDescriptor.Panel current() {
		return getPanels()[index];
	}

	@Override
	public String name() {
		return index+1+". from "+getPanels().length;
	}

	@Override
	public boolean hasNext() {
		return index<getPanels().length-1;
	}

	@Override
	public boolean hasPrevious() {
		return index>0;
	}

	@Override
	public void nextPanel() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		index++;
	}

	@Override
	public void previousPanel() {
		if (!hasPrevious()) {
			throw new NoSuchElementException();
		}
		index--;
	}
	// If something changes dynamically (besides moving between panels), e.g.
	// the number of panels changes in response to user input, then uncomment
	// the following and call when needed: fireChangeEvent();
	private Set<ChangeListener> listeners=new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0

	@Override
	public final void addChangeListener(ChangeListener l) {
		synchronized (listeners) {
			listeners.add(l);
		}
	}

	@Override
	public final void removeChangeListener(ChangeListener l) {
		synchronized (listeners) {
			listeners.remove(l);
		}
	}

	protected final void fireChangeEvent() {
		Iterator<ChangeListener> it;
		synchronized (listeners) {
			it=new HashSet<ChangeListener>(listeners).iterator();
		}
		ChangeEvent ev=new ChangeEvent(this);
		while (it.hasNext()) {
			it.next().stateChanged(ev);
		}
	}

	// You could safely ignore this method. Is is here to keep steps which were
	// there before this wizard was instantiated. It should be better handled
	// by NetBeans Wizard API itself rather than needed to be implemented by a
	// client code.
	private String[] createSteps() {
		String[] beforeSteps=null;
		Object prop=wizard.getProperty("WizardPanel_contentData");
		if (prop!=null&&prop instanceof String[]) {
			beforeSteps=(String[]) prop;
		}

		if (beforeSteps==null) {
			beforeSteps=new String[0];
		}

		String[] res=new String[(beforeSteps.length-1)+panels.length];
		for (int i=0; i<res.length; i++) {
			if (i<(beforeSteps.length-1)) {
				res[i]=beforeSteps[i];
			} else {
				res[i]=panels[i-beforeSteps.length+1].getComponent().getName();
			}
		}
		return res;
	}
}
