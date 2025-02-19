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
public class HospitalDTO {

	private long id;
	private String nombreHos;
	private String poblacion;
	private String numConsultas;
	private List<MedicoDTO> listaMedicos;

	public HospitalDTO(String nombreHos, String poblacion, String numConsultas, List<MedicoDTO> listaMedicos) {
		this.nombreHos = nombreHos;
		this.poblacion = poblacion;
		this.numConsultas = numConsultas;
		this.listaMedicos = listaMedicos;
	}

	@Override
	public String toString() {
		return "HospitalDTO{" + "nombreHos=" + nombreHos + ", poblacion=" + poblacion + ", numConsultas=" + numConsultas + ", listaMedicos=" + listaMedicos + '}';
	}
}
