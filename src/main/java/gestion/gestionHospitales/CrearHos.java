/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package gestion.gestionHospitales;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import gestion.api.MiApi;
import gestion.api.NetworkClient;
import gestion.api.VariablesLogin;
import gestion.dto.HospitalDTO;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 *
 * @author ck
 */
public class CrearHos extends JDialog {

	private static final long serialVersionUID = 1L;

	public CrearHos(JDialog parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	private void initComponents() {

		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();
		txtNomb = new JTextField();
		txtPoblacion = new JTextField();
		jLabel4 = new JLabel();
		txtConsultas = new JTextField();
		jButton1 = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel1.setText("Crear hospital");

		jLabel2.setText("Nombre: ");

		jLabel3.setText("Población");

		jLabel4.setText("Num. consultas");

		jButton1.setText("Añadir");
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addGap(43, 43, 43).addComponent(jLabel1))
		                .addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(txtConsultas))
		                .addGroup(layout.createSequentialGroup().addContainerGap()
		                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
		                                .addGroup(layout.createSequentialGroup().addComponent(jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(txtNomb,
		                                        GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
		                                .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(jLabel3).addGap(38, 38, 38).addComponent(txtPoblacion, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))))
		                .addGroup(layout.createSequentialGroup().addGap(60, 60, 60).addComponent(jButton1))).addContainerGap(19, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(txtNomb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18)
		                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel3).addComponent(txtPoblacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18)
		                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel4).addComponent(txtConsultas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(26, 26, 26)
		                .addComponent(jButton1).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pack();
	}

	private void jButton1ActionPerformed(ActionEvent evt) {
		if (!txtConsultas.getText().equals("") && !txtNomb.getText().equals("") && !txtPoblacion.getText().equals("")) {
			aniadirH().execute();
		} else {
			JOptionPane.showMessageDialog(null, "Rellene los campos");
		}
	}

	private SwingWorker<Response<HospitalDTO>, Void> aniadirH() {
		return new SwingWorker<Response<HospitalDTO>, Void>() {
			@Override
			protected Response<HospitalDTO> doInBackground() throws Exception {
				Response<HospitalDTO> aniadirH = aniadirHospital();
				return aniadirH;
			}

			@Override
			protected void done() {
				Response<HospitalDTO> resultado;
				try {
					resultado = get();
					if (resultado.isSuccessful()) {
						JOptionPane.showMessageDialog(null, "Datos añadidos correctamente");
						txtNomb.setText("");
						txtPoblacion.setText("");
						txtConsultas.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "Error de carga");
					}
				} catch (InterruptedException | ExecutionException ex) {
					JOptionPane.showMessageDialog(null, "Fallo de comunicacion " + ex);
				}
			}
		};
	}

	private Response<HospitalDTO> aniadirHospital() {
		String token = VariablesLogin.getToken();
		Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
		MiApi myAPI = retrofit.create(MiApi.class);
		String nombre = txtNomb.getText();
		String poblacion = txtPoblacion.getText();
		String consulta = txtConsultas.getText();
		HospitalDTO hos = new HospitalDTO();
		hos.setNombreHos(nombre);
		hos.setPoblacion(poblacion);
		hos.setNumConsultas(consulta);
		Call<HospitalDTO> call = myAPI.aniadirHospital(hos);
		Response<HospitalDTO> resp = null;
		try {
			resp = call.execute();
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
		return resp;
	}

	private JButton jButton1;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JTextField txtConsultas;
	private JTextField txtNomb;
	private JTextField txtPoblacion;
}
