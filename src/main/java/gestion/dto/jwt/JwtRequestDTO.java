/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package gestion.dto.jwt;

import java.io.Serializable;

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
public class JwtRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String identificador;
	private String password;

}
