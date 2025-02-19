package gestion.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
/**
 *
 * @author ck
 */
@NoArgsConstructor
@Getter
@Setter
public class InformeDTO {

	private long id;
	private String url;
	private String nombreInf;
	private PacienteDTO paciente;
	private MedicoDTO medico;

	public InformeDTO(String url, String nombreInf, PacienteDTO paciente, MedicoDTO medico) {
		this.url = url;
		this.nombreInf = nombreInf;
		this.paciente = paciente;
		this.medico = medico;
	}

	@Override
	public String toString() {
		return " Nombre Informe: " + nombreInf + "\n Paciente: " + paciente.getNombre() + "\n Url: " + url + "\n\n";
	}

}
