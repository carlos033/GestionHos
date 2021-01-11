package gestion.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ck
 */
public class PacienteDTO {

    private String nSS;
    private String nombre;
    private String password;
    private Date fNacimiento;
    private List<CitaDTO> citas;
    private List<InformeDTO> informes;

    public PacienteDTO() {
        this.informes = new ArrayList<>();
        this.citas = new ArrayList<>();
    }

    public PacienteDTO(String nSS, String nombre, String password, Date fNacimiento,
            List<CitaDTO> citas, List<InformeDTO> informes) {
        this.nSS = nSS;
        this.nombre = nombre;
        this.password = password;
        this.fNacimiento = fNacimiento;
        this.citas = citas;
        this.informes = informes;
    }

    public String getnSS() {
        return nSS;
    }

    public void setnSS(String nSS) {
        this.nSS = nSS;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getfNacimiento() {
        return fNacimiento;
    }

    public void setfNacimiento(Date fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    public List<CitaDTO> getCitas() {
        return citas;
    }

    public void setCitas(List<CitaDTO> citas) {
        this.citas = citas;
    }

    public List<InformeDTO> getInformes() {
        return informes;
    }

    public void setInformes(List<InformeDTO> informes) {
        this.informes = informes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
