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
class DatbaseTablesListRenderer implements ListCellRenderer {

	public DatbaseTablesListRenderer() {
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
		JLabel label=new JLabel(value.toString());
		label.setOpaque(true);

		if (value instanceof Table) {
			if (((Table) value).getPrimaryKeys().isEmpty()) {
				label.setBackground(Color.ORANGE);
				label.setToolTipText("No Primary Key!");

			} else {
				label.setBackground(list.getBackground());
			}
			if (iss) {
				label.setBorder(BorderFactory.createLineBorder(list.getSelectionForeground(), 2));
			} else {
				label.setBorder(BorderFactory.createLineBorder(list.getSelectionBackground(), 2));
			}
		}
		


		return label;
	}
}
