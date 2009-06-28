/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.db2php;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.afraid.poison.db2php.generator.Table;

/**
 *
 * @author poison
 */
class DatbaseTablesListRenderer extends JLabel implements ListCellRenderer {

	public DatbaseTablesListRenderer() {
		setOpaque(true);
	}

	/**
	 *
	 * @param list
	 * @param value
	 * @param index
	 * @param iss is selecte
	 * @param chf has focus
	 * @return
	 */
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean iss, boolean chf) {
		setText(value.toString());
		if (value instanceof Table) {
			if (((Table) value).getPrimaryKeys().isEmpty()) {
				setBackground(Color.ORANGE);
				setToolTipText("No Primary Key!");
			}
		}
		if (iss) {
			setBorder(BorderFactory.createLineBorder(list.getSelectionForeground(), 2));
		} else {
			setBorder(BorderFactory.createLineBorder(list.getBackground(), 2));
		}

		return this;
	}
}
