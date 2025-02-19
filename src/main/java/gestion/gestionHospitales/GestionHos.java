/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package gestion.gestionHospitales;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import gestion.api.MiApi;
import gestion.api.NetworkClient;
import gestion.api.VariablesLogin;
import gestion.dto.HospitalDTO;
import gestion.dto.MedicoDTO;
import gestion.dto.PacienteDTO;
import gestion.textArea.TxtAreaMedicos;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 *
 * @author ck
 */
public class GestionHos extends JDialog {

	private static final long serialVersionUID = 1L;
	private JCalendar calendar;
	private List<MedicoDTO> listM;

	public GestionHos(JDialog parent, boolean modal) {
		super(parent, modal);
		initComponents();
		setTitle("GESTION HOSPITALARIA");
		setLocationRelativeTo(null);
		cmbEspecialidad.setVisible(false);
		jTFecha.setVisible(false);
		cmbConsulta.setVisible(false);
		jLabel4.setVisible(false);
		jLabel5.setVisible(false);
		jLabel6.setVisible(false);
		jPanel4.setVisible(false);
		jPanel5.setVisible(false);
		iniciarCalendario();
		cargarCombos();
	}

	private void cargarCombos() {
		int nConsutlas = Integer.parseInt(VariablesLogin.getMedico().getHospital().getNumConsultas());
		for (int i = 1; i <= nConsutlas; i++) {
			cmbConsulta.addItem(i);
		}
		String[] listaEsp = new String[] { "Alergología", "Anatomía Patológica", "Anestesiología y Reanimación", "Atencion primaria", "Angiología y Cirugía Vascular", "Aparato Digestivo", "Cardiología", "Cirugía Cardiovascular", "Cirugía General",
		        "Maxilofacial", "Traumatología", "Pediatría", "Dermatología", "Nutrición", "Fisioterapia", "Neurología", "Oncología", "Ginecología", "Otorrinolaringología", "Psiquiatría", "Urología" };
		for (String listaEsp1 : listaEsp) {
			cmbEspecialidad.addItem(listaEsp1);
		}
	}

	private void iniciarCalendario() {
		calendar = new JCalendar();
		calendar.setWeekOfYearVisible(false);
		Date d = new Date();
		calendar.setDate(d);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date ayer = cal.getTime();
		calendar.setMaxSelectableDate(ayer);
		JDialog dialog = new JDialog();
		JDateChooser chooser = new JDateChooser();
		chooser.setMaxSelectableDate(ayer);
		chooser.setLocale(getLocale());
		chooser.addPropertyChangeListener("date", (PropertyChangeEvent evt) -> {
			jTxtPassword.requestFocus();
			JDateChooser chooser1 = (JDateChooser) evt.getSource();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			jTFecha.setText(formatter.format(chooser1.getDate()));
			dialog.dispose();
		});
		JPanel content = new JPanel();
		content.add(chooser);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.getContentPane().add(content);
		dialog.pack();
		dialog.setLocationRelativeTo(this);
		dialog.setTitle("Elige fecha");
		dialog.setModal(true);
		jTFecha.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				jButton5.requestFocus();
				dialog.setVisible(true);
			}

			@Override
			public void focusLost(FocusEvent e) {
			}
		});
	}

	private void initComponents() {

		buttonGroup1 = new ButtonGroup();
		jLabel8 = new JLabel();
		jPanel2 = new JPanel();
		optPaciente = new JRadioButton();
		optMedico = new JRadioButton();
		jPanel3 = new JPanel();
		jButton2 = new JButton();
		jButton1 = new JButton();
		jPanel1 = new JPanel();
		jLabel1 = new JLabel();
		jtxtNombre = new JTextField();
		jLabel2 = new JLabel();
		jTxtId = new JTextField();
		jLabel3 = new JLabel();
		jTxtPassword = new JPasswordField();
		jPanel4 = new JPanel();
		jLabel4 = new JLabel();
		jTFecha = new JTextField();
		jPanel5 = new JPanel();
		jLabel5 = new JLabel();
		jLabel6 = new JLabel();
		cmbEspecialidad = new JComboBox<>();
		cmbConsulta = new JComboBox<>();
		jButton5 = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14));
		jLabel8.setText("Crear médico/ paciente");

		jPanel2.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

		buttonGroup1.add(optPaciente);
		optPaciente.setText("Paciente");
		optPaciente.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				optPacienteActionPerformed(evt);
			}
		});

		buttonGroup1.add(optMedico);
		optMedico.setText("Médico");
		optMedico.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				optMedicoActionPerformed(evt);
			}
		});

		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
		        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(optPaciente).addComponent(optMedico)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(optPaciente).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(optMedico).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jPanel3.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		jPanel3.setForeground(new java.awt.Color(255, 255, 255));

		jButton2.setText("<html>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Eliminar<br>Médico/ Paciente</html>");
		jButton2.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		jButton1.setText("<html> Listar <br> Médicos</html>");
		jButton1.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
		        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(jButton2).addComponent(jButton1)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
		        .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton1).addContainerGap()));

		jPanel1.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

		jLabel1.setText("Nombre: ");

		jLabel2.setText("NSS/NºLicencia");

		jLabel3.setText("Password");

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
		        .setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jLabel2).addComponent(jLabel1).addComponent(jLabel3))
		                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jTxtId)
		                                .addGroup(jPanel1Layout.createSequentialGroup().addComponent(jtxtNombre, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE)).addComponent(jTxtPassword))
		                        .addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
		        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel1).addComponent(jtxtNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18)
		        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(jTxtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
		        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel3).addComponent(jTxtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap()));

		jPanel4.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

		jLabel4.setText("Fecha nac:");

		GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout
		        .setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(jLabel4).addGap(27, 27, 27).addComponent(jTFecha).addContainerGap()));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(jPanel4Layout.createSequentialGroup().addContainerGap()
		                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel4).addComponent(jTFecha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		                .addContainerGap(23, Short.MAX_VALUE)));

		jPanel5.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

		jLabel5.setText("Especialidad: ");

		jLabel6.setText("Consulta: ");

		cmbEspecialidad.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

		cmbConsulta.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

		GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jLabel5).addComponent(jLabel6)).addGap(18, 18, 18)
		                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(cmbEspecialidad, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                        .addGroup(jPanel5Layout.createSequentialGroup().addComponent(cmbConsulta, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE)))
		                .addContainerGap()));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(jPanel5Layout.createSequentialGroup().addContainerGap()
		                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel5).addComponent(cmbEspecialidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel6).addComponent(cmbConsulta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		                .addContainerGap(14, Short.MAX_VALUE)));

		jButton5.setText("Crear");
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(layout.createSequentialGroup().addContainerGap()
		                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel2, GroupLayout.DEFAULT_SIZE,
		                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                        .addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                .addComponent(jLabel8, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE).addGap(45, 45, 45))
		                        .addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                                        .addGroup(layout.createSequentialGroup()
		                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel4,
		                                                        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                                                .addGap(0, 70, Short.MAX_VALUE))
		                                        .addComponent(jPanel5, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                                .addContainerGap())))
		        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jButton5).addGap(129, 129, 129)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel8, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE).addGap(5, 5, 5)
		                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                        .addGroup(layout.createSequentialGroup().addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                                .addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                                .addComponent(jPanel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                .addComponent(jButton5).addContainerGap())
		                        .addGroup(layout.createSequentialGroup().addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                                .addGap(89, 89, 89)))));

		pack();
	}

	private boolean validarCampos(boolean esMedico) {
		if (jtxtNombre.getText().isBlank() || jTxtId.getText().isBlank() || jTxtPassword.getPassword().length == 0) {
			return false;
		}
		if (esMedico && (cmbEspecialidad.getSelectedItem() == null || cmbConsulta.getSelectedItem() == null)) {
			return false;
		}
		return true;
	}

	private void jButton5ActionPerformed(ActionEvent evt) {
		boolean esMedico = optMedico.isSelected();

		if (!validarCampos(esMedico)) {
			JOptionPane.showMessageDialog(null, "Rellene todos los campos");
			return;
		}

		if (esMedico && !jTxtId.getText().startsWith("M")) {
			JOptionPane.showMessageDialog(null, "El nº de licencia debe empezar por 'M'");
			return;
		}

		if (optPaciente.isSelected() && !jTxtId.getText().startsWith("ES")) {
			JOptionPane.showMessageDialog(null, "El nº de SS debe empezar por 'ES'");
			return;
		}

		if (esMedico) {
			aniadirM().execute();
		} else {
			aniadirP().execute();
		}
	}

	private SwingWorker<Response<MedicoDTO>, Void> aniadirM() {
		jButton5.setEnabled(false);
		return new SwingWorker<Response<MedicoDTO>, Void>() {
			@Override
			protected Response<MedicoDTO> doInBackground() throws Exception {
				Response<MedicoDTO> aniadirM = aniadirMedico();
				return aniadirM;
			}

			@Override
			protected void done() {
				jButton5.setEnabled(true);
				try {
					Response<MedicoDTO> resultado = get();
					if (resultado.isSuccessful()) {
						JOptionPane.showMessageDialog(null, "Datos añadidos correctamente");
						limpiarCampos();
					} else {
						JsonObject convertedObject = new Gson().fromJson(resultado.errorBody().string(), JsonObject.class);
						JsonElement msgElement = convertedObject.get("message");
						JOptionPane.showMessageDialog(null, "Error de carga " + msgElement.toString());
					}
				} catch (InterruptedException | ExecutionException ex) {
					JOptionPane.showMessageDialog(null, "Fallo de comunicacion " + ex);
				} catch (IOException ex) {
					Logger.getLogger(AniadirCitas.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		};
	}

	private void limpiarCampos() {
		jtxtNombre.setText("");
		jTxtPassword.setText("");
		jTFecha.setText("");
		jTxtId.setText("");
		cmbEspecialidad.setSelectedIndex(0);
		cmbConsulta.setSelectedIndex(0);
	}

	private Response<MedicoDTO> aniadirMedico() {
		String token = VariablesLogin.getToken();
		Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
		MiApi myAPI = retrofit.create(MiApi.class);
		String nombre = jtxtNombre.getText();
		String nLicencia = jTxtId.getText();
		char[] passwordChars = jTxtPassword.getPassword();
		String password = new String(passwordChars);
		Arrays.fill(passwordChars, ' ');
		int consulta = Integer.parseInt(cmbConsulta.getSelectedItem().toString());
		String especialidad = cmbEspecialidad.getSelectedItem().toString();
		HospitalDTO hos = new HospitalDTO();
		hos.setNombreHos(VariablesLogin.getMedico().getHospital().getNombreHos());
		MedicoDTO medico = new MedicoDTO();
		medico.setConsulta(consulta);
		medico.setEspecialidad(especialidad);
		medico.setHospital(hos);
		medico.setNombre(nombre);
		medico.setPassword(password);
		medico.setNumLicencia(nLicencia);
		Call<MedicoDTO> call = myAPI.aniadirMedico(medico);
		Response<MedicoDTO> resp = null;
		try {
			resp = call.execute();
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
		return resp;
	}

	private SwingWorker<Response<PacienteDTO>, Void> aniadirP() {
		return new SwingWorker<Response<PacienteDTO>, Void>() {
			@Override
			protected Response<PacienteDTO> doInBackground() throws Exception {
				Response<PacienteDTO> aniadirP = aniadirPaciente();
				return aniadirP;
			}

			@Override
			protected void done() {
				Response<PacienteDTO> resultado;
				try {
					resultado = get();
					if (resultado.isSuccessful()) {
						JOptionPane.showMessageDialog(null, "Datos añadidos correctamente");
						jtxtNombre.setText("");
						jTxtPassword.setText("");
						jTFecha.setText("");
						jTxtId.setText("");
						cmbEspecialidad.setSelectedIndex(0);
						cmbConsulta.setSelectedIndex(0);
					} else {
						JsonObject convertedObject = new Gson().fromJson(resultado.errorBody().string(), JsonObject.class);
						JsonElement msgElement = convertedObject.get("message");
						JOptionPane.showMessageDialog(null, "Error de carga " + msgElement.toString());
					}
				} catch (InterruptedException | ExecutionException ex) {
					JOptionPane.showMessageDialog(null, "Fallo de comunicacion " + ex);
				} catch (IOException ex) {
					Logger.getLogger(AniadirCitas.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		};
	}

	private Response<PacienteDTO> aniadirPaciente() {
		String token = VariablesLogin.getToken();
		Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
		MiApi myAPI = retrofit.create(MiApi.class);
		String nSS = jTxtId.getText();
		String nombre = jtxtNombre.getText();
		char[] passwordChars = jTxtPassword.getPassword();
		String password = new String(passwordChars);
		Date fNacimiento = calendar.getDate();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		formatter.format(fNacimiento);
		PacienteDTO paciente = new PacienteDTO();
		paciente.setNombre(nombre);
		paciente.setPassword(password);
		paciente.setFechaNacimiento(fNacimiento);
		paciente.setNss(nSS);
		Call<PacienteDTO> call = myAPI.aniadirPaciente(paciente);
		Response<PacienteDTO> resp = null;
		Arrays.fill(passwordChars, ' ');
		try {
			resp = call.execute();
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
		return resp;
	}

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		EliminarMedicoOPaciente eliminar = new EliminarMedicoOPaciente(new JDialog(), true);
		eliminar.setVisible(true);
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		listarM().execute();
	}

	private SwingWorker<Response<List<MedicoDTO>>, Void> listarM() {
		return new SwingWorker<Response<List<MedicoDTO>>, Void>() {
			@Override
			protected Response<List<MedicoDTO>> doInBackground() throws Exception {
				Response<List<MedicoDTO>> listMd = listarMedico();
				return listMd;
			}

			@Override
			protected void done() {
				Response<List<MedicoDTO>> resultado;
				try {
					resultado = get();
					if (resultado.isSuccessful()) {
						listM = resultado.body();
						TxtAreaMedicos a = new TxtAreaMedicos(GestionHos.this, true, listM);
						a.setVisible(true);
					} else {
						JsonObject convertedObject = new Gson().fromJson(resultado.errorBody().string(), JsonObject.class);
						JsonElement msgElement = convertedObject.get("message");
						JOptionPane.showMessageDialog(null, "Error de carga " + msgElement.toString());
					}
				} catch (InterruptedException | ExecutionException ex) {
					JOptionPane.showMessageDialog(null, "Fallo de comunicacion " + ex);
				} catch (IOException ex) {
					Logger.getLogger(AniadirCitas.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		};
	}

	private Response<List<MedicoDTO>> listarMedico() {
		String nomHos = VariablesLogin.getMedico().getHospital().getNombreHos();
		String token = VariablesLogin.getToken();
		Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
		MiApi myAPI = retrofit.create(MiApi.class);
		Call<List<MedicoDTO>> call = myAPI.BuscarMedicosXHospital(nomHos);
		Response<List<MedicoDTO>> resp = null;
		try {
			resp = call.execute();
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
		return resp;
	}

	private void optPacienteActionPerformed(ActionEvent evt) {
		jPanel4.setVisible(true);
		jPanel5.setVisible(false);
		jTFecha.setVisible(true);
		jLabel4.setVisible(true);
		cmbEspecialidad.setVisible(false);
		jLabel5.setVisible(false);
		cmbConsulta.setVisible(false);
		jLabel6.setVisible(false);
		jTxtId.setText("ES");
	}

	private void optMedicoActionPerformed(ActionEvent evt) {
		jPanel4.setVisible(false);
		jPanel5.setVisible(true);
		cmbEspecialidad.setVisible(true);
		jLabel5.setVisible(true);
		cmbConsulta.setVisible(true);
		jLabel6.setVisible(true);
		jTFecha.setVisible(false);
		jLabel4.setVisible(false);
		jTxtId.setText("M");
	}

	private ButtonGroup buttonGroup1;
	private JComboBox<Integer> cmbConsulta;
	private JComboBox<String> cmbEspecialidad;
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton5;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel8;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel4;
	private JPanel jPanel5;
	private JTextField jTFecha;
	private JTextField jTxtId;
	private JPasswordField jTxtPassword;
	private JTextField jtxtNombre;
	private JRadioButton optMedico;
	private JRadioButton optPaciente;

}
