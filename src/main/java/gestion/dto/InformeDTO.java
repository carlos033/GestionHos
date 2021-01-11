package gestion.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ck
 */
public class InformeDTO {

    private String url;
    private String nombreInf;
    private PacienteDTO paciente;
    private MedicoDTO medico;

    public InformeDTO() {
        this.medico = new MedicoDTO();
        this.paciente = new PacienteDTO();
    }

    public InformeDTO(String url, String nombreInf, PacienteDTO paciente, MedicoDTO medico) {
        this.url = url;
        this.nombreInf = nombreInf;
        this.paciente = paciente;
        this.medico = medico;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombreInf() {
        return nombreInf;
    }

    public void setNombreInf(String nombreInf) {
        this.nombreInf = nombreInf;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    public MedicoDTO getMedico() {
        return medico;
    }

    public void setMedico(MedicoDTO medico) {
        this.medico = medico;
    }

    @Override
    public String toString() {
        return " Nombre Informe: " + nombreInf + "\n Paciente: " + paciente.getNombre()
                + "\n Url: " + url + "\n\n";
    }

}
