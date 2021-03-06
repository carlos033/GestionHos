/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.gestionHospitales;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gestion.api.MiApi;
import gestion.api.NetworkClient;
import gestion.api.VariablesLogin;
import gestion.dto.jwt.JwtRequestDTO;
import gestion.dto.jwt.JwtResponseDTO;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 *
 * @author ck
 */
public class Login extends javax.swing.JFrame {

    private OpcionesHos panelOpc;

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        passwordEditText.setText("");
        setTitle("GESTION HOSPITALARIA");
        setLocationRelativeTo(null);
    }

    private SwingWorker<Response<JwtResponseDTO>, Void> getWorker() {
        return new SwingWorker<Response<JwtResponseDTO>, Void>() {

            @Override
            protected Response<JwtResponseDTO> doInBackground() throws Exception {
                Response<JwtResponseDTO> respuestaInicioSesion = iniciarSesion();
                return respuestaInicioSesion;
            }

            @Override
            protected void done() {
                Response<JwtResponseDTO> resultado;
                try {
                    resultado = get();
                    if (resultado.isSuccessful()) {
                        guardarInfoEnVariables(resultado.body().getJwttoken(), usernameEditText.getText());
                        panelOpc = new OpcionesHos(Login.this, true);
                        panelOpc.setVisible(true);
                        dispose();
                    } else {
                        JsonObject convertedObject = new Gson().fromJson(resultado.errorBody().string(), JsonObject.class);
                        JsonElement msgElement = convertedObject.get("message");
                        JOptionPane.showMessageDialog(null, "Error usuario " + msgElement.toString());
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    JOptionPane.showMessageDialog(null, "Fallo de comunicacion " + ex);
                } catch (IOException ex) {
                    Logger.getLogger(AniadirCitas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }

    public Response<JwtResponseDTO> iniciarSesion() {
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        MiApi myAPI = retrofit.create(MiApi.class);
        JwtRequestDTO jwtRequestDTO = new JwtRequestDTO();
        jwtRequestDTO.setIdentificador(usernameEditText.getText());
        jwtRequestDTO.setPassword(passwordEditText.getText());
        Call<JwtResponseDTO> llamadaLogin = myAPI.login(jwtRequestDTO);

        Response<JwtResponseDTO> resp = null;
        try {
            resp = llamadaLogin.execute();
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resp;
    }

    private void guardarInfoEnVariables(String jwttoken, String idUsuario) {
        VariablesLogin.setToken(jwttoken);
        VariablesLogin.setIdUsuario(idUsuario);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usernameEditText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        passwordEditText = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        usernameEditText.setText("M");

        jLabel1.setText("Usuario:");

        jLabel2.setText("Password:");

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(loginButton)
                .addContainerGap(132, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(passwordEditText, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(usernameEditText, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(usernameEditText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(passwordEditText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(loginButton)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        if (!usernameEditText.getText().startsWith("M")) {
            JOptionPane.showMessageDialog(null, "El nº de licencia debe emepezar por 'M'");
        } else {
            getWorker().execute();
        }
    }//GEN-LAST:event_loginButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordEditText;
    private javax.swing.JTextField usernameEditText;
    // End of variables declaration//GEN-END:variables
}
