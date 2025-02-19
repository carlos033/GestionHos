/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package gestion.textArea;

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import gestion.dto.MedicoDTO;

/**
 *
 * @author ck
 */
public class TxtAreaMedicos extends JDialog {

	private static final long serialVersionUID = 1L;
	private String txt = "";

	public TxtAreaMedicos(JDialog parent, boolean modal, List<MedicoDTO> medicos) {
		super(parent, modal);
		initComponents();
		setTitle("GESTION HOSPITALARIA");
		setLocationRelativeTo(null);
		medicos.forEach(m -> {
			txt += m.getDatos();
		});
		TxtA.setText(txt);
	}

	private void initComponents() {

		jScrollPane1 = new JScrollPane();
		TxtA = new JTextArea();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Médicos del hospital");

		TxtA.setEditable(false);
		TxtA.setColumns(20);
		TxtA.setRows(5);
		TxtA.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		jScrollPane1.setViewportView(TxtA);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING,
		        layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE).addContainerGap()));

		pack();
	}

	private JTextArea TxtA;
	private JScrollPane jScrollPane1;
}
