/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.db2php;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.afraid.poison.db2php.generator.xml.Connection;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbPreferences;

public class PhpClassesFromXmlWizardPanel1 implements WizardDescriptor.Panel, PropertyChangeListener {

	/**
	 * The visual component that displays this panel. If you need to access the
	 * component from this class, just use getComponent().
	 */
	private Component component;

	// Get the visual component for the panel. In this template, the component
	// is kept separate. This can be more efficient: if the wizard is created
	// but never displayed, or not all panels are displayed, it is better to
	// create only those which really need to be visible.
	@Override
	public Component getComponent() {
		if (component==null) {
			component=new PhpClassesFromXmlVisualPanel1();
			((PhpClassesFromXmlVisualPanel1) component).getXmlFileChooser().addPropertyChangeListener(this);
		}
		return component;
	}

	@Override
	public HelpCtx getHelp() {
		// Show no Help button for this panel:
		return HelpCtx.DEFAULT_HELP;
		// If you have context help:
		// return new HelpCtx(SampleWizardPanel1.class);
	}

	@Override
	public boolean isValid() {
		// If it is always OK to press Next or Finish, then:
		PhpClassesFromXmlVisualPanel1 panel1=(PhpClassesFromXmlVisualPanel1) getComponent();
		File f=panel1.getXmlFileChooser().getSelectedFile();

		return isValidFile(f);
		//return true;
		// If it depends on some condition (form filled out...), then:
		// return someCondition();
		// and when this condition changes (last form field filled in...) then:
		// fireChangeEvent();
		// and uncomment the complicated stuff below.
	}

	public static boolean isValidFile(File f) {
		if (null==f) {
			return false;
		}
		if (f.isFile()) {
			try {
				List<Connection> connections=Connection.fromXMLFile(f);
				return true;
			} catch (Exception e) {
				Exceptions.printStackTrace(e);
			}
		}
		return false;
	}
	private final Set<ChangeListener> listeners=new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0

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

	// You can use a settings object to keep track of state. Normally the
	// settings object will be the WizardDescriptor, so you can use
	// WizardDescriptor.getProperty & putProperty to store information entered
	// by the user.
	@Override
	public void readSettings(Object settings) {
		String file=NbPreferences.forModule(PhpClassesFromXmlVisualPanel1.class).get("file", null);
		if (null!=file) {
			File f=new File(file);
			if (f.exists()) {
				((PhpClassesFromXmlVisualPanel1) getComponent()).getXmlFileChooser().setSelectedFile(f);
			}
		}
	}

	@Override
	public void storeSettings(Object settings) {
		NbPreferences.forModule(PhpClassesFromXmlVisualPanel1.class).put("file", ((PhpClassesFromXmlVisualPanel1) getComponent()).getXmlFileChooser().getSelectedFile().getPath());
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
			fireChangeEvent();
		}
	}
}
