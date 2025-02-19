/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package gestion.gestionHospitales;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 *
 * @author ck
 */
public class AcercaDe extends JDialog {

	private static final long serialVersionUID = 1L;

	public AcercaDe(JDialog parent, boolean modal) throws IOException {
		super(parent, modal);
		initComponents();
		InputStream inputAvatar = OpcionesHos.class.getResourceAsStream("/Logo.jpg");
		jLabel3.setIcon(new ImageIcon(ImageIO.read(inputAvatar)));
		setTitle("Acerca de");
		setLocationRelativeTo(null);
	}

	private void initComponents() {

		jLabel1 = new JLabel();
		jLabel3 = new JLabel();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel1.setText("Autor: Carlos Díaz Rodríguez");

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(20, 20, 20).addComponent(jLabel1))
		                .addGroup(layout.createSequentialGroup().addGap(40, 40, 40).addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE))).addContainerGap(38, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addGap(22, 22, 22).addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE).addContainerGap(58, Short.MAX_VALUE)));

		pack();
	}

	private JLabel jLabel1;
	private JLabel jLabel3;
}
