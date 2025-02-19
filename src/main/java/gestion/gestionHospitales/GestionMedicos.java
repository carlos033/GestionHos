/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package gestion.gestionHospitales;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import gestion.dto.CitaDTO;
import gestion.dto.InformeDTO;
import gestion.dto.PacienteDTO;
import gestion.textArea.TxtAreaCitas;
import gestion.textArea.TxtAreaInformes;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 *
 * @author ck
 */
public class GestionMedicos extends JDialog {

	private static final long serialVersionUID = 1L;
	private final String token;
	private final String nLicencia;
	private List<PacienteDTO> listaP;
	private List<InformeDTO> listaI;
	private List<InformeDTO> listaIM;
	private List<CitaDTO> listaC;

	public GestionMedicos(JDialog parent, boolean modal) {
		super(parent, modal);
		initComponents();
		setTitle("GESTIONES MÉDICAS");
		setLocationRelativeTo(null);
		token = VariablesLogin.getToken();
		nLicencia = VariablesLogin.getIdUsuario();
		cargaPaciente().execute();
	}

	private SwingWorker<Response<List<PacienteDTO>>, Void> cargaPaciente() {
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
						listaP = resultado.body();
						CmbPaciente.removeAllItems();
						CmbPaciente.addItem("");
						for (int i = 0; i < listaP.size(); i++) {
							CmbPaciente.addItem(listaP.get(i).getNss());
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

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
	 */
	private void initComponents() {

		jPanel2 = new JPanel();
		CmbPaciente = new JComboBox<>();
		jLabel2 = new JLabel();
		CmbInforme = new JComboBox<>();
		CmbInforme.addItem("");
		jLabel1 = new JLabel();
		jPanel1 = new JPanel();
		BtnListarC = new JButton();
		BtnEliminar = new JButton();
		BtnListarI = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		jPanel2.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

		CmbPaciente.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		CmbPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				CmbPacienteActionPerformed(evt);
			}
		});

		jLabel2.setText("Nombre del informe");

		CmbInforme.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		CmbInforme.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		jLabel1.setText("Pacientes");

		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
		                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel1).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                        .addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(CmbInforme, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE)
		                                .addComponent(CmbPaciente, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE).addComponent(jLabel2)).addGap(0, 40, Short.MAX_VALUE)))));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addGap(11, 11, 11).addComponent(CmbPaciente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18)
		                .addComponent(jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(CmbInforme, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addContainerGap(22, Short.MAX_VALUE)));

		jPanel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		BtnListarC.setText("<html> Listar<br>Citas</html>");
		BtnListarC.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		BtnListarC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				BtnListarCActionPerformed(evt);
			}
		});

		BtnEliminar.setText("<html> Eliminar<br>Informe</html>");
		BtnEliminar.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		BtnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				BtnEliminarActionPerformed(evt);
			}
		});

		BtnListarI.setText("<html> Listar <br>Informes</html>");
		BtnListarI.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, new Color(204, 204, 204)));
		BtnListarI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				BtnListarIActionPerformed(evt);
			}
		});

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING,
		        jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(
		                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(BtnListarI, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE).addComponent(BtnEliminar).addComponent(BtnListarC, GroupLayout.Alignment.LEADING))
		                .addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(BtnListarC, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(BtnEliminar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(BtnListarI, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
		        layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                .addGap(18, 18, 18).addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(
		        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addGroup(layout.createSequentialGroup().addGap(44, 44, 44).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                        .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		                        .addContainerGap(46, Short.MAX_VALUE)));

		pack();
	}

	private void CmbPacienteActionPerformed(ActionEvent evt) {
		CmbInforme.removeAllItems();
		CmbInforme.addItem("");
		cargaInforme().execute();
	}

	private SwingWorker<Response<List<InformeDTO>>, Void> cargaInforme() {
		return new SwingWorker<Response<List<InformeDTO>>, Void>() {
			@Override
			protected Response<List<InformeDTO>> doInBackground() throws Exception {
				Response<List<InformeDTO>> cargaI = cargaI();
				return cargaI;
			}

			@Override
			protected void done() {
				Response<List<InformeDTO>> resultado;
				try {
					resultado = get();
					if (resultado.isSuccessful()) {
						listaI = resultado.body();
						if (listaI.isEmpty()) {
							JOptionPane.showMessageDialog(null, "Este paciente no tiene informes");
						} else {
							for (int i = 0; i < listaI.size(); i++) {
								CmbInforme.addItem(listaI.get(i).getNombreInf());
							}
						}
					}
				} catch (InterruptedException | ExecutionException ex) {
					JOptionPane.showMessageDialog(null, "Fallo de comunicacion" + ex);
				}
			}
		};
	}

	private Response<List<InformeDTO>> cargaI() {
		String nSS = CmbPaciente.getSelectedItem().toString();
		Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
		MiApi myAPI = retrofit.create(MiApi.class);
		Call<List<InformeDTO>> call = myAPI.buscarInformesXPaciente(nSS);
		Response<List<InformeDTO>> resp = null;
		try {
			resp = call.execute();
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
		return resp;
	}

	private void BtnEliminarActionPerformed(ActionEvent evt) {
		if (CmbPaciente.getSelectedItem().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "Seleccione un Informe");
		} else {
			elininarI().execute();
		}
	}

	private SwingWorker<Response<Void>, Void> elininarI() {
		return new SwingWorker<Response<Void>, Void>() {
			@Override
			protected Response<Void> doInBackground() throws Exception {
				Response<Void> eI = elininarInfome();
				return eI;
			}

			@Override
			protected void done() {
				Response<Void> resultado;
				try {
					resultado = get();
					if (resultado.isSuccessful()) {
						JOptionPane.showMessageDialog(null, "Informe eliminado correctamente");
						CmbInforme.removeAllItems();
						CmbInforme.addItem("");
						CmbPaciente.setSelectedIndex(0);
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

	private Response<Void> elininarInfome() {
		String nomb = CmbInforme.getSelectedItem().toString();
		Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
		MiApi myAPI = retrofit.create(MiApi.class);
		Call<Void> call = myAPI.eliminarInforme(nomb);
		Response<Void> resp = null;
		try {
			resp = call.execute();
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
		return resp;
	}

	private void BtnListarCActionPerformed(ActionEvent evt) {
		listarC().execute();
	}

	private SwingWorker<Response<List<CitaDTO>>, Void> listarC() {
		return new SwingWorker<Response<List<CitaDTO>>, Void>() {
			@Override
			protected Response<List<CitaDTO>> doInBackground() throws Exception {
				Response<List<CitaDTO>> list = listarCita();
				return list;
			}

			@Override
			protected void done() {
				Response<List<CitaDTO>> resultado;
				try {
					resultado = get();
					if (resultado.isSuccessful()) {
						listaC = resultado.body();
						TxtAreaCitas t = new TxtAreaCitas(GestionMedicos.this, true, listaC);
						t.setVisible(true);
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

	private Response<List<CitaDTO>> listarCita() {
		Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
		MiApi myAPI = retrofit.create(MiApi.class);
		Call<List<CitaDTO>> call = myAPI.buscarCitaXMedico(nLicencia);
		Response<List<CitaDTO>> resp = null;
		try {
			resp = call.execute();
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
		return resp;
	}

	private void BtnListarIActionPerformed(ActionEvent evt) {
		listarI().execute();
	}

	private SwingWorker<Response<List<InformeDTO>>, Void> listarI() {
		return new SwingWorker<Response<List<InformeDTO>>, Void>() {
			@Override
			protected Response<List<InformeDTO>> doInBackground() throws Exception {
				Response<List<InformeDTO>> list = listarInf();
				return list;
			}

			@Override
			protected void done() {
				Response<List<InformeDTO>> resultado;
				try {
					resultado = get();
					if (resultado.isSuccessful()) {
						listaIM = resultado.body();
						TxtAreaInformes t = new TxtAreaInformes(GestionMedicos.this, true, listaIM);
						t.setVisible(true);
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

	private Response<List<InformeDTO>> listarInf() {
		Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
		MiApi myAPI = retrofit.create(MiApi.class);
		Call<List<InformeDTO>> call = myAPI.buscarInformesXMedico(nLicencia);
		Response<List<InformeDTO>> resp = null;
		try {
			resp = call.execute();
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
		return resp;
	}

	private JButton BtnEliminar;
	private JButton BtnListarC;
	private JButton BtnListarI;
	private JComboBox<String> CmbInforme;
	private JComboBox<String> CmbPaciente;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JPanel jPanel1;
	private JPanel jPanel2;
}
