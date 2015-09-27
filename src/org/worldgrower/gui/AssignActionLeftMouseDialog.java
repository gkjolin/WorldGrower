/*******************************************************************************
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.worldgrower.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.worldgrower.gui.util.IconUtils;

public class AssignActionLeftMouseDialog extends JDialog {

	private String selectedAction = null;

	public AssignActionLeftMouseDialog(String[] actionDescriptions) {
		setBounds(100, 100, 450, 475);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setLayout(null);
		
		setModalityType(ModalityType.APPLICATION_MODAL);		
		getContentPane().setLayout(null);
		IconUtils.setIcon(this);
		SwingUtils.installEscapeCloseOperation(this);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 32, 362, 292);
		getContentPane().add(scrollPane);
		
		JList<String> list = new JList<>(actionDescriptions);
		list.setSelectedIndex(0);
		scrollPane.setViewportView(list);
		
		JLabel lblNewLabel = new JLabel("Ctrl-left mouse click to talk with a person");
		lblNewLabel.setBounds(34, 343, 360, 24);
		getContentPane().add(lblNewLabel);

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(34, 373, 360, 50);
		getContentPane().add(buttonPane);

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
		addActions(list, okButton, cancelButton);
	}
	
	public String showMe() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		return selectedAction;
	}

	private void addActions(JList<String> list, JButton okButton, JButton cancelButton) {
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedAction = list.getSelectedValue();
				AssignActionLeftMouseDialog.this.dispose();
				
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AssignActionLeftMouseDialog.this.dispose();
			}
		});
	}
}