package gestion.dto;

/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author ck
 */
@NoArgsConstructor
@Getter
@Setter
public class CitaDTO {

	private long id;
	private Date fHoraCita;
	private PacienteDTO paciente;
	private MedicoDTO medico;

	public CitaDTO(Date fHoraCita, PacienteDTO paciente, MedicoDTO medico) {
		this.fHoraCita = fHoraCita;
		this.paciente = paciente;
		this.medico = medico;
	}

	@Override
	public String toString() {
		SimpleDateFormat formater = new SimpleDateFormat("EEE, d MMM yyyy k:mm");
		return " id: " + id + ", cita: " + formater.format(fHoraCita) + "\n Paciente: " + paciente.getNombre() + "\n Médico: " + medico.getNombre() + "\n\n";
	}

}
