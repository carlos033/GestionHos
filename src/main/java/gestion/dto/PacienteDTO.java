package gestion.dto;

import java.util.Date;
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
public class PacienteDTO {

	private String nss;
	private String nombre;
	private String password;
	private Date fechaNacimiento;
	private List<CitaDTO> citas;
	private List<InformeDTO> informes;

	@Override
	public String toString() {
		return nombre;
	}

}
