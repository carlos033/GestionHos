package gestion.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ck
 */
public class MedicoDTO {

    private String nLicencia;
    private String nombre;
    private String password;
    private String especialidad;
    private int consulta;
    private HospitalDTO hospital;
    private List<CitaDTO> listaCitas;
    private List<InformeDTO> listaInformes;

    public MedicoDTO() {
        this.listaInformes = new ArrayList<>();
        this.listaCitas = new ArrayList<>();
    }

    public MedicoDTO(String nLicencia, String nombre, String password, String especialidad, int consulta, HospitalDTO hospital, List<CitaDTO> listaCitas, List<InformeDTO> listaInformes) {
        this.nLicencia = nLicencia;
        this.nombre = nombre;
        this.password = password;
        this.especialidad = especialidad;
        this.consulta = consulta;
        this.hospital = hospital;
        this.listaCitas = listaCitas;
        this.listaInformes = listaInformes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getnLicencia() {
        return nLicencia;
    }

    public void setnLicencia(String nLicencia) {
        this.nLicencia = nLicencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getConsulta() {
        return consulta;
    }

    public void setConsulta(int consulta) {
        this.consulta = consulta;
    }

    public HospitalDTO getHospital() {
        return hospital;
    }

    public void setHospital(HospitalDTO hospital) {
        this.hospital = hospital;
    }

    public List<CitaDTO> getListaCitas() {
        return listaCitas;
    }

    public void setListaCitas(List<CitaDTO> listaCitas) {
        this.listaCitas = listaCitas;
    }

    public List<InformeDTO> getListaInformes() {
        return listaInformes;
    }

    public void setListaInformes(List<InformeDTO> listaInformes) {
        this.listaInformes = listaInformes;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getDatos() {
        return " Licencia: " + nLicencia + ", nombre: " + nombre + "\n especialidad: "
                + especialidad + " consulta: " + consulta + "\n\n";
    }
}
