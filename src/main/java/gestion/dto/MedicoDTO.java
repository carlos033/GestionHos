package gestion.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicoDTO {

	private String numLicencia;
	private String nombre;
	private String password;
	private String especialidad;
	private int consulta;
	private HospitalDTO hospital;
	private List<CitaDTO> listaCitas;
	private List<InformeDTO> listaInformes;

	public String getDatos() {
		return " Licencia: " + numLicencia + ", nombre: " + nombre + "\n especialidad: " + especialidad + " consulta: " + consulta + "\n\n";
	}
}
