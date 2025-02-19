/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package gestion.api;

import gestion.dto.MedicoDTO;

/**
 *
 * @author ck
 */
public class VariablesLogin {

	private static String token;
	private static String idUsuario;
	private static MedicoDTO medico;

	public static MedicoDTO getMedico() {
		return medico;
	}

	public static void setMedico(MedicoDTO medico) {
		VariablesLogin.medico = medico;
	}

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		VariablesLogin.token = token;
	}

	public static String getIdUsuario() {
		return idUsuario;
	}

	public static void setIdUsuario(String idUsuario) {
		VariablesLogin.idUsuario = idUsuario;
	}

}
