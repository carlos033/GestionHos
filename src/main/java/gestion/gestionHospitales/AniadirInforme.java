/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package gestion.gestionHospitales;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gestion.api.MiApi;
import gestion.api.NetworkClient;
import gestion.api.VariablesLogin;
import gestion.dto.InformeDTO;
import gestion.dto.MedicoDTO;
import gestion.dto.PacienteDTO;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 *
 * @author ck
 */
public class AniadirInforme extends JDialog {

	private static final long serialVersionUID = 1L;
	private Set<String> especialidad = new HashSet<>();
	private List<MedicoDTO> listM = new ArrayList<>();
	private List<MedicoDTO> listE;
	private final String urlRegex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

	public AniadirInforme(JDialog parent, boolean modal) {
		super(parent, modal);
		initComponents();
		cmbEspecialidad.addItem("");
		espeialidades().execute();
		setTitle("AÑADIR INFORME");
		setLocationRelativeTo(null);
	}

	private SwingWorker<Response<List<MedicoDTO>>, Void> espeialidades() {
		return new SwingWorker<Response<List<MedicoDTO>>, Void>() {
			@Override
			protected Response<List<MedicoDTO>> doInBackground() throws Exception {
				Response<List<MedicoDTO>> medico = BuscarMedicoHos();
				return medico;
			}

			@Override
			protected void done() {
				Response<List<MedicoDTO>> resultado;
				try {
					resultado = get();
					if (resultado.isSuccessful()) {
						listE = resultado.body();
						for (int i = 0; i < listE.size(); i++) {
							especialidad.add(listE.get(i).getEspecialidad());
						}
						especialidad.forEach(cmbEspecialidad::addItem);
					} else {
						JsonObject convertedObject = new Gson().fromJson(resultado.errorBody().string(), JsonObject.class);
						JsonElement msgElement = convertedObject.get("message");
						JOptionPane.showMessageDialog(null, "Error de carga " + msgElement.toString());
					}
				} catch (InterruptedException | ExecutionException ex) {
					JOptionPane.showMessageDialog(null, "Fallo de comunicacion " + ex);
				} catch (IOException ex) {
					Logger.getLogger(AniadirInforme.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		};
	}

	private Response<List<MedicoDTO>> BuscarMedicoHos() {
		String nombreHos = VariablesLogin.getMedico().getHospital().getNombreHos();
		String token = VariablesLogin.getToken();
		Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
		MiApi myAPI = retrofit.create(MiApi.class);
		Call<List<MedicoDTO>> call = myAPI.BuscarMedicosXHospital(nombreHos);
		Response<List<MedicoDTO>> resp = null;
		try {
			resp = call.execute();
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
		return resp;
	}

	private void initComponents() {

		jPanel3 = new JPanel();
		jTUrl = new JTextField();
		jTNombre = new JTextField();
		jLabel5 = new JLabel();
		jLabel6 = new JLabel();
		jLabel7 = new JLabel();
		jButton1 = new JButton();
		jPanel1 = new JPanel();
		cmbEspecialidad = new JComboBox<>();
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		cmbNombre = new JComboBox<>();
		jLabel3 = new JLabel();
		jLabel9 = new JLabel();
		jtLicencia = new JTextField();
		jPanel2 = new JPanel();
		jLabel4 = new JLabel();
		jtNSS = new JTextField();
		jLabel8 = new JLabel();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		jPanel3.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

		jLabel5.setText("Nombre ");

		jLabel6.setText("Url");

		jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel7.setText("Datos informe");

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jLabel5).addComponent(jLabel6))
		                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(jTNombre, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE).addComponent(jTUrl)).addContainerGap())
		        .addGroup(jPanel3Layout.createSequentialGroup().addGap(89, 89, 89).addComponent(jLabel7).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING,
		        jPanel3Layout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel7).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jTNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(jLabel5)).addGap(18, 18, 18)
		                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jTUrl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(jLabel6)).addContainerGap()));

		jButton1.setText("Crear");
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jPanel1.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

		cmbEspecialidad.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		cmbEspecialidad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cmbEspecialidadActionPerformed(evt);
			}
		});

		jLabel1.setText("Especialidad");

		jLabel2.setText("Médico");

		cmbNombre.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		cmbNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cmbNombreActionPerformed(evt);
			}
		});

		jLabel3.setText("Licencia");

		jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel9.setText("Datos Médico");

		jtLicencia.setEditable(false);

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
		        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jLabel1).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(jLabel2).addComponent(jLabel3)))
		        .addGap(22, 22, 22)
		        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jLabel9).addGroup(
		                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(cmbEspecialidad, 0, 171, Short.MAX_VALUE).addComponent(cmbNombre, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jtLicencia)))
		        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(jPanel1Layout.createSequentialGroup().addGap(6, 6, 6).addComponent(jLabel9).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel1).addComponent(cmbEspecialidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
		                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(cmbNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(jLabel2)).addGap(18, 18, 18)
		                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel3).addComponent(jtLicencia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap()));

		jPanel2.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

		jLabel4.setText("Nº SS");

		jtNSS.setText("ES");
		jtNSS.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

		jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel8.setText("Datos paciente");

		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
		                .addComponent(jtNSS, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE).addContainerGap())
		        .addGroup(jPanel2Layout.createSequentialGroup().addGap(89, 89, 89).addComponent(jLabel8).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING,
		        jPanel2Layout.createSequentialGroup().addComponent(jLabel8).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jtNSS, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(jLabel4)).addContainerGap()));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING,
		        layout.createSequentialGroup().addContainerGap()
		                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jPanel3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                        .addGroup(layout.createSequentialGroup()
		                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(jPanel1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                        .addComponent(jPanel2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                                .addGap(0, 0, Short.MAX_VALUE)))
		                .addGap(18, 18, 18).addComponent(jButton1).addGap(15, 15, 15)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(layout.createSequentialGroup().addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jButton1).addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18)
		                .addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));

		pack();
	}

	private SwingWorker<Response<List<MedicoDTO>>, Void> listarM() {
		return new SwingWorker<Response<List<MedicoDTO>>, Void>() {
			@Override
			protected Response<List<MedicoDTO>> doInBackground() throws Exception {
				Response<List<MedicoDTO>> listM = listarMedico();
				return listM;
			}

			@Override
			protected void done() {
				Response<List<MedicoDTO>> resultado;
				try {
					resultado = get();
					if (resultado.isSuccessful()) {
						listM = resultado.body();
						for (int i = 0; i < listM.size(); i++) {
							cmbNombre.addItem(listM.get(i));
						}
					} else {
						JsonObject convertedObject = new Gson().fromJson(resultado.errorBody().string(), JsonObject.class);
						JsonElement msgElement = convertedObject.get("message");
						JOptionPane.showMessageDialog(null, "Error de carga " + msgElement.toString());
					}
				} catch (InterruptedException | ExecutionException ex) {
					JOptionPane.showMessageDialog(null, "Fallo de comunicacion " + ex);
				} catch (IOException ex) {
					Logger.getLogger(AniadirInforme.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		};
	}

	private Response<List<MedicoDTO>> listarMedico() {
		String nombre = VariablesLogin.getMedico().getHospital().getNombreHos();
		String esp = cmbEspecialidad.getSelectedItem() != null ? cmbEspecialidad.getSelectedItem().toString() : "";
		String token = VariablesLogin.getToken();
		Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
		MiApi myAPI = retrofit.create(MiApi.class);
		Call<List<MedicoDTO>> call = myAPI.BuscarMedicoXEspecialidad(esp, nombre);
		Response<List<MedicoDTO>> resp = null;
		try {
			resp = call.execute();
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
		return resp;
	}

	private void jButton1ActionPerformed(ActionEvent evt) {
		if (!jtLicencia.getText().equals("") && !jtNSS.getText().equals("ES") && !jTUrl.getText().equals("") && !jTNombre.getText().equals("") && jTUrl.getText().matches(urlRegex)) {

			aniadirI().execute();
		} else if (!jtLicencia.getText().equals("") && !jtNSS.getText().equals("ES") && !jTNombre.getText().equals("") && !jTUrl.getText().matches(urlRegex)) {
			JOptionPane.showMessageDialog(null, "Introduzca una URL correcta");
		} else {
			JOptionPane.showMessageDialog(null, "Rellene los campos");
		}
	}

	private void cmbEspecialidadActionPerformed(ActionEvent evt) {
		cmbNombre.removeAllItems();
		if (!cmbEspecialidad.getSelectedItem().equals("")) {
			listarM().execute();
		}
	}

	private void cmbNombreActionPerformed(ActionEvent evt) {
		if (cmbNombre.getSelectedItem() != null) {
			MedicoDTO medico = (MedicoDTO) cmbNombre.getSelectedItem();
			jtLicencia.setText(medico.getNumLicencia());
		}
	}

	private SwingWorker<Response<InformeDTO>, Void> aniadirI() {
		return new SwingWorker<Response<InformeDTO>, Void>() {
			@Override
			protected Response<InformeDTO> doInBackground() throws Exception {
				Response<InformeDTO> aniadirI = aniadirInforme();
				return aniadirI;
			}

			@Override
			protected void done() {
				Response<InformeDTO> resultado;
				try {
					resultado = get();
					if (resultado.isSuccessful()) {
						JOptionPane.showMessageDialog(null, "Informe creado correctamente");
						cmbNombre.removeAllItems();
						jtLicencia.setText("");
						jtNSS.setText("ES");
						jTNombre.setText("");
						jTUrl.setText("");
						cmbEspecialidad.setSelectedIndex(0);
					} else {
						JsonObject convertedObject = new Gson().fromJson(resultado.errorBody().string(), JsonObject.class);
						JsonElement msgElement = convertedObject.get("message");
						JOptionPane.showMessageDialog(null, "Error de carga " + msgElement.toString());
					}
				} catch (InterruptedException | ExecutionException ex) {
					JOptionPane.showMessageDialog(null, "Fallo de comunicacion " + ex);
				} catch (IOException ex) {
					Logger.getLogger(AniadirInforme.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		};
	}

	private Response<InformeDTO> aniadirInforme() throws MalformedURLException {
		Response<InformeDTO> resp = null;

		String token = VariablesLogin.getToken();
		Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
		MiApi myAPI = retrofit.create(MiApi.class);
		String nLicencia = jtLicencia.getText();
		String nSS = jtNSS.getText();
		PacienteDTO p = new PacienteDTO();
		p.setNss(nSS);
		String nombre = jTNombre.getText();
		String url = jTUrl.getText();
		MedicoDTO m = new MedicoDTO();
		m.setNumLicencia(nLicencia);
		InformeDTO informe = new InformeDTO(url, nombre, p, m);
		Call<InformeDTO> call = myAPI.aniadirInforme(informe);
		try {
			resp = call.execute();
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
		return resp;
	}

	private JComboBox<String> cmbEspecialidad;
	private JComboBox<MedicoDTO> cmbNombre;
	private JButton jButton1;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JTextField jTNombre;
	private JTextField jTUrl;
	private JTextField jtLicencia;
	private JTextField jtNSS;
}
