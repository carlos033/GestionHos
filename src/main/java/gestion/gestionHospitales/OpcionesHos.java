/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package gestion.gestionHospitales;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
import gestion.dto.MedicoDTO;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 *
 * @author ck
 */
public class OpcionesHos extends JDialog {

	private static final long serialVersionUID = 1L;

	public OpcionesHos(Frame parent, boolean modal) {
		super(parent, modal);
		try {
			initComponents();
			InputStream inputAvatar = OpcionesHos.class.getResourceAsStream("/hospital.jpg");
			jLabel1.setIcon(new ImageIcon(ImageIO.read(inputAvatar)));
			setTitle("GESTION HOSPITALARIA");
			setLocationRelativeTo(null);
			buscarMedicoLicencia().execute();
			ponLaAyuda();
		} catch (IOException ex) {
			Logger.getLogger(OpcionesHos.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void ponLaAyuda() {
		try {
			URL hsURL = HelpSet.findHelpSet(getClass().getClassLoader(), "help/help_set.hs");
			HelpSet helpset = new HelpSet(getClass().getClassLoader(), hsURL);
			HelpBroker hb = helpset.createHelpBroker();
			hb.enableHelpKey(getRootPane(), "OpcionesHos", helpset);
		} catch (IllegalArgumentException | HelpSetException e) {
		}
	}

	private SwingWorker<Response<MedicoDTO>, Void> buscarMedicoLicencia() {
		return new SwingWorker<Response<MedicoDTO>, Void>() {
			@Override
			protected Response<MedicoDTO> doInBackground() throws Exception {
				Response<MedicoDTO> medico = BuscarMedico();
				return medico;
			}

			@Override
			protected void done() {
				Response<MedicoDTO> resultado;
				try {
					resultado = get();
					if (resultado.isSuccessful()) {
						VariablesLogin.setMedico(resultado.body());
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

	private Response<MedicoDTO> BuscarMedico() {
		String licencia = VariablesLogin.getIdUsuario();
		String token = VariablesLogin.getToken();
		Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
		MiApi myAPI = retrofit.create(MiApi.class);
		Call<MedicoDTO> call = myAPI.obtenerMedico(licencia);
		Response<MedicoDTO> resp = null;
		try {
			resp = call.execute();
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
		return resp;
	}

	private void initComponents() {

		jPanel2 = new JPanel();
		jButton4 = new JButton();
		jButton5 = new JButton();
		jLabel1 = new JLabel();
		jPanel1 = new JPanel();
		jButton1 = new JButton();
		jButton3 = new JButton();
		jPanel3 = new JPanel();
		btnHos = new JButton();
		jMenuBar1 = new JMenuBar();
		jMenu1 = new JMenu();
		jMenuItem1 = new JMenuItem();
		jMenu2 = new JMenu();
		jMenuItem2 = new JMenuItem();
		jMenuItem3 = new JMenuItem();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		jPanel2.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

		jButton4.setText("<html> Añadir<br>Informe</html>");
		jButton4.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		jButton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton4ActionPerformed(evt);
			}
		});

		jButton5.setText("<html> Añadir <br>Cita</html>");
		jButton5.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		jButton5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});

		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout
		        .setHorizontalGroup(
		                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                        .addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
		                                .addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE).addComponent(jButton4, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
		                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel2Layout.setVerticalGroup(
		        jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(jButton4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButton5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jLabel1.setAutoscrolls(true);
		jLabel1.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

		jPanel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		jButton1.setText("<html> Gestión<br>Hospital</html>");
		jButton1.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jButton3.setText("Médicos");
		jButton3.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		jButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(
		        jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jButton1, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE).addComponent(jButton3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		        .addContainerGap()));
		jPanel1Layout.setVerticalGroup(
		        jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jButton1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                .addGap(18, 18, 18).addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jPanel3.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

		btnHos.setText("<html> Nuevo <br> Hospital</html>");
		btnHos.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		btnHos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnHosActionPerformed(evt);
			}
		});

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(btnHos).addContainerGap()));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(btnHos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jMenu1.setText("Gestion");

		jMenuItem1.setText("Salir");
		jMenuItem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jMenuItem1ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem1);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("Ayuda");

		jMenuItem2.setText("Ayuda F1");
		jMenuItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jMenuItem2ActionPerformed(evt);
			}
		});
		jMenu2.add(jMenuItem2);

		jMenuItem3.setText("Acerca de");
		jMenuItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jMenuItem3ActionPerformed(evt);
			}
		});
		jMenu2.add(jMenuItem3);

		jMenuBar1.add(jMenu2);

		setJMenuBar(jMenuBar1);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(layout.createSequentialGroup().addGap(22, 22, 22)
		                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel3, GroupLayout.DEFAULT_SIZE,
		                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap(30, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup()
		        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(43, 43, 43).addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		                .addGroup(layout.createSequentialGroup().addGap(44, 44, 44).addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		                        .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		                .addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)))
		        .addContainerGap(18, Short.MAX_VALUE)));

		pack();
	}

	private void jButton1ActionPerformed(ActionEvent evt) {
		GestionHos gestion = new GestionHos(this, true);
		gestion.setVisible(true);
	}

	private void jButton3ActionPerformed(ActionEvent evt) {
		GestionMedicos medicos = new GestionMedicos(this, true);
		medicos.setVisible(true);
	}

	private void jButton4ActionPerformed(ActionEvent evt) {
		AniadirInforme informe = new AniadirInforme(this, true);
		informe.setVisible(true);
	}

	private void jButton5ActionPerformed(ActionEvent evt) {
		AniadirCitas citas = new AniadirCitas(this, true);
		citas.setVisible(true);
	}

	private void jMenuItem1ActionPerformed(ActionEvent evt) {
		System.exit(0);
	}

	private void btnHosActionPerformed(ActionEvent evt) {
		if (VariablesLogin.getMedico().getEspecialidad().compareToIgnoreCase("Administrador") == 0) {
			CrearHos hos = new CrearHos(this, true);
			hos.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Solo los administradores del sistema pueden acceder a esta opcion");
		}
	}

	private void jMenuItem3ActionPerformed(ActionEvent evt) {
		try {
			AcercaDe a = new AcercaDe(this, true);
			a.setVisible(true);
		} catch (IOException ex) {
			Logger.getLogger(OpcionesHos.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void jMenuItem2ActionPerformed(ActionEvent evt) {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_F1);
			robot.keyRelease(KeyEvent.VK_F1);

		} catch (AWTException e) {
		}
	}

	private JButton btnHos;
	private JButton jButton1;
	private JButton jButton3;
	private JButton jButton4;
	private JButton jButton5;
	private JLabel jLabel1;
	private JMenu jMenu1;
	private JMenu jMenu2;
	private JMenuBar jMenuBar1;
	private JMenuItem jMenuItem1;
	private JMenuItem jMenuItem2;
	private JMenuItem jMenuItem3;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
}
