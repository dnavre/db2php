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
import org.afraid.poison.common.CollectionUtil;
import org.afraid.poison.db2php.generator.Table;

/**
 *
 * @author poison
 */
class DatbaseTablesListRenderer extends JLabel implements ListCellRenderer {

	public DatbaseTablesListRenderer() {
		super();
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
			Table t=(Table) value;
			if (t.getPrimaryKeys().isEmpty()) {
				setBackground(Color.ORANGE);
				setToolTipText(new StringBuilder("Table ").append(t.getName()).append(" has no primary key!").toString());
				if (iss) {
					setBorder(BorderFactory.createLineBorder(list.getSelectionBackground(), 2));
				} else {
					setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
				}
			} else {
				setToolTipText(new StringBuilder("Primary key(s) of ").append(t.getName()).append(": ").append(CollectionUtil.join(t.getPrimaryKeys(), ", ")).toString());
				if (iss) {
					setBorder(BorderFactory.createLineBorder(list.getSelectionBackground(), 2));
					setBackground(list.getSelectionBackground());
				} else {
					setBackground(Color.WHITE);
					setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
				}
			}

		} else {
			if (iss) {
				setBorder(BorderFactory.createLineBorder(list.getSelectionBackground(), 2));
				setBackground(list.getSelectionBackground());
			} else {
				setBackground(Color.WHITE);
				setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
			}
		}

		return this;
	}
}
