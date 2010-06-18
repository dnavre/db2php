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
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.afraid.poison.common.CollectionUtil;
import org.afraid.poison.db2php.generator.Index;
import org.afraid.poison.db2php.generator.Table;

/**
 * custom cell renderer for table list
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
class TablesListCellRenderer extends JLabel implements ListCellRenderer {

	public TablesListCellRenderer() {
		super();
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean iss, boolean chf) {

		setText(value.toString());
		if (value instanceof Table) {
			Table t=(Table) value;
			StringBuilder tooltipText=new StringBuilder("<html><h2>").append(t.getName()).append("</h2>");
			if (!t.getFieldsPrimaryKey().isEmpty()) {
				tooltipText.append("<strong>Primary key(s): </strong>").append(CollectionUtil.join(t.getFieldsPrimaryKey(), ", ")).append("<br />");
			} else {
				tooltipText.append("<strong>Has no primary key!</strong><br />");
				tooltipText.append("<strong>Will be using: </strong>").append(CollectionUtil.join(t.getFieldsIdentifiers(), ", ")).append("<br />");
			}

			if (!t.getFieldsBestRowIdentifiers().isEmpty()) {
				tooltipText.append("<strong>Best Identifiers per Driver: </strong>").append(CollectionUtil.join(t.getFieldsBestRowIdentifiers(), ", ")).append("<br />");
			}
			Set<Index> indexesUnique=t.getIndexes(true);
			if (!indexesUnique.isEmpty()) {
				tooltipText.append("<strong>Unique Indexes: </strong><br />");
				tooltipText.append(getIndexHtml(indexesUnique));
			}
			Set<Index> indexesNonUnique=t.getIndexes(false);
			if (!indexesNonUnique.isEmpty()) {
				tooltipText.append("<strong>Non-Unique Indexes: </strong><br />");
				tooltipText.append(getIndexHtml(indexesNonUnique));
			}

			/*
			if (!t.getFieldsIndexesUnique().isEmpty()) {
			tooltipText.append("<strong>Unique Indexes: </strong>").append(CollectionUtil.join(t.getFieldsIndexesUnique(), ", ")).append("<br />");
			}
			if (!t.getFieldsIndexesNonUnique().isEmpty()) {
			tooltipText.append("<strong>Non-Unique Indexes: </strong>").append(CollectionUtil.join(t.getFieldsIndexesNonUnique(), ", ")).append("<br />");
			}
			 */
			if (!t.getIndexes().isEmpty()) {
				//tooltipText.append("<strong>Indexes: </strong>").append(t.getIndexes());
			}
			tooltipText.append("</html>");
			setToolTipText(tooltipText.toString());
			if (!isEnabled()) {
				setBackground(Color.GRAY);
			} else if (t.getFieldsPrimaryKey().isEmpty()) {
				setBackground(Color.ORANGE);
				//setToolTipText(new StringBuilder("Table ").append(t.getName()).append(" has no primary key! Will be using: ").append(CollectionUtil.join(t.getFieldsIdentifiers(), ", ")).toString());
				if (iss) {
					setBorder(BorderFactory.createLineBorder(list.getSelectionBackground(), 2));
				} else {
					setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
				}
			} else {
				//setToolTipText(new StringBuilder("Primary key(s) of ").append(t.getName()).append(": ").append(CollectionUtil.join(t.getFieldsPrimaryKey(), ", ")).toString());
				if (iss) {
					setBorder(BorderFactory.createLineBorder(list.getSelectionBackground(), 2));
					setBackground(list.getSelectionBackground());
				} else {
					setBackground(Color.WHITE);
					setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
				}
			}

		} else {
			if (!isEnabled()) {
				setBackground(Color.GRAY);
			} else if (iss) {
				setBorder(BorderFactory.createLineBorder(list.getSelectionBackground(), 2));
				setBackground(list.getSelectionBackground());
			} else {
				setBackground(Color.WHITE);
				setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
			}
		}

		return this;
	}

	private CharSequence getIndexHtml(Set<Index> indexes) {
		StringBuilder sb=new StringBuilder();

		sb.append("<table border=1 cellpadding=0 cellspacing=0>");
		for (Index i : indexes) {
			sb.append(getIndexHtml(i));
		}
		sb.append("</table>");
		return sb;
	}

	private CharSequence getIndexHtml(Index index) {
		StringBuilder sb=new StringBuilder();
		//sb.append("<tr><td>").append(index.getName()).append(":</td><td><ul><li>").append(CollectionUtil.join(index.getFields(), "</li><li>")).append("</ul></td></tr>");
		sb.append("<tr><td>").append(index.getName()).append(":</td><td>").append(CollectionUtil.join(index.getFields(), ", ")).append("</td></tr>");
		return sb;
	}
}
