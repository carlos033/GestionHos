/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.gestionHospitales;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import gestion.api.MiApi;
import gestion.api.NetworkClient;
import gestion.api.VariablesLogin;
import gestion.dto.CitaDTO;
import gestion.dto.MedicoDTO;
import gestion.dto.PacienteDTO;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 *
 * @author ck
 */
public class AniadirCitas extends javax.swing.JDialog {

    private Set<String> listaSinRepetir;
    private JCalendar calendar;
    private List<MedicoDTO> listM = new ArrayList<>();
    private List<MedicoDTO> listMH;
    private final JComboBox hora = new JComboBox(new Integer[]{8, 9, 10, 11, 12, 13, 14, 15});
    private final JComboBox minutos = new JComboBox(new Integer[]{0, 15, 30, 45});

    /**
     * Creates new form AniadirCitas
     *
     * @param parent
     * @param modal
     */
    public AniadirCitas(javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        cmbEspecialidad.addItem("");
        calendario();
        iniciarComponentes();
        setTitle("GESTION HOSPITALARIA");
        setLocationRelativeTo(null);
    }

    private void calendario() {
        calendar = new JCalendar();
        txtF.add(calendar);
        calendar.setWeekOfYearVisible(false);
        Date d = new Date();
        calendar.setDate(d);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +1);
        Date maniana = cal.getTime();
        calendar.setMinSelectableDate(maniana);
        JDialog dialog = new JDialog();
        JDateChooser chooser = new JDateChooser();
        chooser.setMinSelectableDate(maniana);
        chooser.setLocale(getLocale());
        chooser.addPropertyChangeListener("date", (PropertyChangeEvent evt) -> {
            jtNSS.requestFocus();
            JDateChooser chooser1 = (JDateChooser) evt.getSource();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            txtF.setText(formatter.format(chooser1.getDate()));
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
        txtF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                jButton1.requestFocus();
                dialog.setVisible(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
    }

    private void iniciarComponentes() {
        buscarMedicoHospital().execute();
        JDialog dialogHora = new JDialog();
        txtH.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                jButton1.requestFocus();
                dialogHora.setVisible(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        JButton btnA = new JButton();
        btnA.setText("Aceptar");
        JPanel contentHora = new JPanel();
        contentHora.add(btnA);
        contentHora.add(hora);
        contentHora.add(minutos);
        dialogHora.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogHora.getContentPane().add(contentHora);
        dialogHora.pack();
        dialogHora.setLocationRelativeTo(this);
        dialogHora.setTitle("Elige hora");
        dialogHora.setModal(true);
        dialogHora.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                String h = hora.getSelectedItem().toString();
                String m = minutos.getSelectedItem().toString();
                if (m.equals("0")) {
                    txtH.setText(h + ": 00");
                } else {
                    txtH.setText(h + ":" + m);
                }
            }
        });
        btnA.addActionListener((ActionEvent e) -> {
            dialogHora.dispose();
        });

    }

    private SwingWorker<Response<List<MedicoDTO>>, Void> buscarMedicoHospital() {
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
                        listMH = resultado.body();
                        listaSinRepetir = new HashSet<>();
                        for (int i = 0; i < listMH.size(); i++) {
                            String especialidad = listMH.get(i).getEspecialidad();
                            listaSinRepetir.add(especialidad);
                        }
                        for (Iterator iterator = listaSinRepetir.iterator(); iterator.hasNext();) {
                            String especialidad = iterator.next().toString();
                            cmbEspecialidad.addItem(especialidad);
                            cmbNombre.removeAllItems();
                            //cmbNombre.addItem("");
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
            Logger.getLogger(Login.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return resp;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cmbEspecialidad = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbNombre = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtLicencia = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jtNSS = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtF = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtH = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        cmbEspecialidad.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmbEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEspecialidadActionPerformed(evt);
            }
        });

        jLabel1.setText("Especialidad");

        jLabel2.setText("Médico");

        cmbNombre.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmbNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNombreActionPerformed(evt);
            }
        });

        jLabel3.setText("Licencia");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Datos Médico");

        jtLicencia.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cmbEspecialidad, 0, 171, Short.MAX_VALUE)
                        .addComponent(cmbNombre, 0, 171, Short.MAX_VALUE)
                        .addComponent(jtLicencia)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtLicencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel4.setText("Nº SS");

        jtNSS.setText("ES");
        jtNSS.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Datos paciente");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(jtNSS, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtNSS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        jButton1.setText("Crear cita");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel5.setText("Fecha");

        jLabel6.setText("Hora");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Datos cita");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtF, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                        .addComponent(txtH, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(26, Short.MAX_VALUE)
                        .addComponent(jLabel5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel6))
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEspecialidadActionPerformed
        cmbNombre.removeAllItems();
        if (!cmbEspecialidad.getSelectedItem().equals("")) {
            listarM().execute();
        }
    }//GEN-LAST:event_cmbEspecialidadActionPerformed
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
                    Logger.getLogger(AniadirCitas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }

    private Response<List<MedicoDTO>> listarMedico() {
        String nombreHos = VariablesLogin.getMedico().getHospital().getNombreHos();
        String especialidad = cmbEspecialidad.getSelectedItem().toString();
        String token = VariablesLogin.getToken();
        Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
        MiApi myAPI = retrofit.create(MiApi.class);
        Call<List<MedicoDTO>> call = myAPI.BuscarMedicoXEspecialidad(especialidad, nombreHos);
        Response<List<MedicoDTO>> resp = null;
        try {
            resp = call.execute();
        } catch (IOException ex) {
            Logger.getLogger(Login.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return resp;
    }
    private void cmbNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNombreActionPerformed
        if (cmbNombre.getSelectedItem() != null) {
            MedicoDTO medico = (MedicoDTO) cmbNombre.getSelectedItem();
            jtLicencia.setText(medico.getnLicencia());
        }
    }//GEN-LAST:event_cmbNombreActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!jtLicencia.getText().equals("") && !jtNSS.getText().equals("")
                && !txtF.getText().equals("") && !txtH.getText().equals("")) {
            aniadirC().execute();
        } else {
            JOptionPane.showMessageDialog(null, "Rellene los campos");
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    private SwingWorker<Response<CitaDTO>, Void> aniadirC() {
        return new SwingWorker<Response<CitaDTO>, Void>() {
            @Override
            protected Response<CitaDTO> doInBackground() throws Exception {
                Response<CitaDTO> aniadirC = aniadirCita();
                return aniadirC;
            }

            @Override
            protected void done() {
                Response<CitaDTO> resultado;
                try {
                    resultado = get();
                    if (resultado.isSuccessful()) {
                        JOptionPane.showMessageDialog(null, "Cita asignada correctamente");
                        cmbNombre.removeAllItems();
                        jtLicencia.setText("");
                        cmbNombre.removeAllItems();
                        cmbEspecialidad.setSelectedIndex(0);
                        jtNSS.setText("ES");
                        txtF.setText("");
                        txtH.setText("");
                        buscarMedicoHospital();
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

    private Response<CitaDTO> aniadirCita() {
        String token = VariablesLogin.getToken();
        Retrofit retrofit = NetworkClient.getRetrofitClientWithToken(token);
        MiApi myAPI = retrofit.create(MiApi.class);
        String nLicencia = jtLicencia.getText();
        String nSS = jtNSS.getText();
        String fechaStr = txtF.getText();
        String horaStr = txtH.getText();
        Date miFecha = new Date();
        String[] fechaSplitted = fechaStr.split("/");
        int dia = Integer.parseInt(fechaSplitted[0]);
        int mes = Integer.parseInt(fechaSplitted[1]) - 1;
        int anio = Integer.parseInt(fechaSplitted[2]);
        int h = Integer.parseInt(hora.getSelectedItem().toString());
        int m = Integer.parseInt(minutos.getSelectedItem().toString());
        miFecha = getDate(anio, mes, dia, h, m, 00);
        PacienteDTO p = new PacienteDTO();
        p.setnSS(nSS);
        MedicoDTO me = new MedicoDTO();
        me.setnLicencia(nLicencia);
        CitaDTO cita = new CitaDTO(miFecha, p, me);
        Call<CitaDTO> call = myAPI.aniadirCita(cita);
        Response<CitaDTO> resp = null;
        try {
            resp = call.execute();
        } catch (IOException ex) {
            Logger.getLogger(Login.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return resp;
    }

    public static Date getDate(int anioC, int mesC, int diaC, int horaC, int minutoC, int segundoC) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, anioC);
        cal.set(Calendar.MONTH, mesC);
        cal.set(Calendar.DAY_OF_MONTH, diaC);
        cal.set(Calendar.HOUR_OF_DAY, horaC);
        cal.set(Calendar.MINUTE, minutoC);
        cal.set(Calendar.SECOND, segundoC);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbEspecialidad;
    private javax.swing.JComboBox<MedicoDTO> cmbNombre;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField jtLicencia;
    private javax.swing.JTextField jtNSS;
    private javax.swing.JTextField txtF;
    private javax.swing.JTextField txtH;
    // End of variables declaration//GEN-END:variables

}
