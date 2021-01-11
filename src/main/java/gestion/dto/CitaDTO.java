package gestion.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ck
 */
public class CitaDTO {

    private int id;
    private Date fHoraCita;
    private PacienteDTO paciente;
    private MedicoDTO medico;

    public CitaDTO() {
    }

    public CitaDTO(Date fHoraCita, PacienteDTO paciente, MedicoDTO medico) {
        this.fHoraCita = fHoraCita;
        this.paciente = paciente;
        this.medico = medico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getfHoraCita() {
        return fHoraCita;
    }

    public void setfHoraCita(Date fHoraCita) {
        this.fHoraCita = fHoraCita;
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
        SimpleDateFormat formater = new SimpleDateFormat("EEE, d MMM yyyy k:mm");
        return " id: " + id + ", cita: " + formater.format(fHoraCita) + "\n Paciente: " + paciente.getNombre()
                + "\n Médico: " + medico.getNombre() + "\n\n";
    }

}
