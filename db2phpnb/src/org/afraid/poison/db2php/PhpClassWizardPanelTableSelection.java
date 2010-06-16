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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class PhpClassWizardPanelTableSelection implements WizardDescriptor.Panel, ListSelectionListener {

	private WizardDescriptor wizard;

	public PhpClassWizardPanelTableSelection(WizardDescriptor wizard) {
		this.wizard=wizard;
	}


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
			component=new PhpClassVisualPanelTableSelection();
			((PhpClassVisualPanelTableSelection) component).getTablesSelection().addListSelectionListener(this);
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
		return getComponent().isValid();
		// If it depends on some condition (form filled out...), then:
		// return someCondition();
		// and when this condition changes (last form field filled in...) then:
		//fireChangeEvent();
		// and uncomment the complicated stuff below.
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
	}

	@Override
	public void storeSettings(Object settings) {
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		fireChangeEvent();
	}

	/**
	 * @return the wizard
	 */
	public WizardDescriptor getWizard() {
		return wizard;
	}

	/**
	 * @param wizard the wizard to set
	 */
	public void setWizard(WizardDescriptor wizard) {
		this.wizard=wizard;
	}
}

