/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package gestion.gestionHospitales;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
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

import gestion.api.MiApi;
import gestion.api.NetworkClient;
import gestion.api.VariablesLogin;
import gestion.dto.MedicoDTO;
import gestion.dto.PacienteDTO;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 *
 * @author ck
 */
public class EliminarMedicoOPaciente extends JDialog {

	private static final long serialVersionUID = 1L;
	private List<MedicoDTO> listM = new ArrayList<>();
	private List<PacienteDTO> listP = new ArrayList<>();

	public EliminarMedicoOPaciente(JDialog parent, boolean modal) {
		super(parent, modal);
		initComponents();
		setTitle("Eliminar medicos/pacientes");
		setLocationRelativeTo(null);
	}

	private void initComponents() {

		buttonGroup1 = new ButtonGroup();
		jPanel2 = new JPanel();
		optMedico = new JRadioButton();
		optPaciente = new JRadioButton();
		jLabel1 = new JLabel();
		cmbNombre = new JComboBox<>();
		jLabel2 = new JLabel();
		jButton1 = new JButton();
		jtLicencia = new JTextField();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		jPanel2.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

		buttonGroup1.add(optMedico);
		optMedico.setText("Médico");
		optMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				optMedicoActionPerformed(evt);
			}
		});

		buttonGroup1.add(optPaciente);
		optPaciente.setText("Paciente");
		optPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				optPacienteActionPerformed(evt);
			}
		});

		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(optMedico).addComponent(optPaciente)).addContainerGap(18, Short.MAX_VALUE)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
		        jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(optMedico).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(optPaciente).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jLabel1.setText("Nombre");

		cmbNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cmbNombreActionPerformed(evt);
			}
		});

		jLabel2.setText("NSS/nº licencia");

		jButton1.setText("Eliminar");
		jButton1.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jtLicencia.setEditable(false);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
		        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18)
		                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                                .addGroup(layout.createSequentialGroup().addComponent(jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(cmbNombre, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE))
		                                .addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(jButton1).addComponent(jLabel2))
		                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jtLicencia, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)))
		                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(layout.createSequentialGroup().addContainerGap()
		                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                        .addGroup(layout.createSequentialGroup()
		                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(cmbNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(jLabel1,
		                                        GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
		                                .addGap(18, 18, 18)
		                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(jLabel2).addGap(24, 24, 24).addComponent(jButton1))
		                                        .addGroup(layout.createSequentialGroup().addComponent(jtLicencia).addGap(32, 32, 32))))
		                        .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pack();
	}

	private void optMedicoActionPerformed(ActionEvent evt) {
		cmbNombre.removeAllItems();
		cmbNombre.addItem("");
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

	private void jButton1ActionPerformed(ActionEvent evt) {
		if (optPaciente.isSelected()) {
			if (jtLicencia.getText().startsWith("ES")) {
				elininarP().execute();
			} else {
				JOptionPane.showMessageDialog(null, "Seleccione una id");
			}
		} else if (optMedico.isSelected()) {
			if (jtLicencia.getText().startsWith("M")) {
				elininarM().execute();
			} else {
				JOptionPane.showMessageDialog(null, "Seleccione una id");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Seleccione medico o paciente");
		}
	}

	private SwingWorker<Response<Void>, Void> elininarP() {
		return new SwingWorker<Response<Void>, Void>() {
			@Override
			protected Response<Void> doInBackground() throws Exception {
				Response<Void> eP = elininarPaciente();
				return eP;
			}

			@Override
			protected void done() {
				Response<Void> resultado;
				try {
					resultado = get();
					if (resultado.isSuccessful()) {
						JOptionPane.showMessageDialog(null, "Paciente eliminada correctamente");
						jtLicencia.setText("");
						cmbNombre.removeAllItems();
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

	private Response<Void> elininarPaciente() {
		String id = jtLicencia.getText();
		String token = VariablesLogin.getToken();
		Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
		MiApi myAPI = retrofit.create(MiApi.class);
		Call<Void> call = myAPI.eliminarPaciente(id);
		Response<Void> resp = null;
		try {
			resp = call.execute();
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
		return resp;
	}

	private SwingWorker<Response<Void>, Void> elininarM() {
		return new SwingWorker<Response<Void>, Void>() {
			@Override
			protected Response<Void> doInBackground() throws Exception {
				Response<Void> eM = elininarMedico();
				return eM;
			}

			@Override
			protected void done() {
				Response<Void> resultado;
				try {
					resultado = get();
					if (resultado.isSuccessful()) {
						JOptionPane.showMessageDialog(null, "Medico eliminado correctamente");
						jtLicencia.setText("");
						cmbNombre.removeAllItems();
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

	private Response<Void> elininarMedico() {
		String id = jtLicencia.getText();
		String token = VariablesLogin.getToken();
		Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
		MiApi myAPI = retrofit.create(MiApi.class);
		Call<Void> call = myAPI.eliminarMedico(id);
		Response<Void> resp = null;
		try {
			resp = call.execute();
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
		return resp;
	}

	private void cmbNombreActionPerformed(ActionEvent evt) {
		jtLicencia.setText("");
		String selectNombre = cmbNombre.getSelectedItem() != null ? cmbNombre.getSelectedItem().toString() : "";
		if (optPaciente.isSelected() && !selectNombre.equals("")) {
			PacienteDTO paciente = (PacienteDTO) cmbNombre.getSelectedItem();
			jtLicencia.setText(paciente.getNss());
		} else if (optMedico.isSelected() && !selectNombre.equals("")) {
			MedicoDTO medico = (MedicoDTO) cmbNombre.getSelectedItem();
			jtLicencia.setText(medico.getNumLicencia());
		}
	}

	private void optPacienteActionPerformed(ActionEvent evt) {
		cmbNombre.removeAllItems();
		cmbNombre.addItem("");
		listarP().execute();
	}

	private SwingWorker<Response<List<PacienteDTO>>, Void> listarP() {
		return new SwingWorker<Response<List<PacienteDTO>>, Void>() {
			@Override
			protected Response<List<PacienteDTO>> doInBackground() throws Exception {
				Response<List<PacienteDTO>> listP = listarPaciente();
				return listP;
			}

			@Override
			protected void done() {
				Response<List<PacienteDTO>> resultado;
				try {
					resultado = get();
					if (resultado.isSuccessful()) {
						listP = resultado.body();
						for (int i = 0; i < listP.size(); i++) {
							cmbNombre.addItem(listP.get(i));
						}
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

	private Response<List<PacienteDTO>> listarPaciente() {
		String token = VariablesLogin.getToken();
		Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
		MiApi myAPI = retrofit.create(MiApi.class);
		Call<List<PacienteDTO>> call = myAPI.listPacientes();
		Response<List<PacienteDTO>> resp = null;
		try {
			resp = call.execute();
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
		return resp;
	}

	private ButtonGroup buttonGroup1;
	private JComboBox<Object> cmbNombre;
	private JButton jButton1;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JPanel jPanel2;
	private JTextField jtLicencia;
	private JRadioButton optMedico;
	private JRadioButton optPaciente;
}
