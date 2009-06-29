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
 * @author Andreas Schnaiter <rc.poison@gmail.com>
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
