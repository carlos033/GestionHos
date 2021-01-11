package gestion.api;

import gestion.dto.CitaDTO;
import gestion.dto.HospitalDTO;
import gestion.dto.InformeDTO;
import gestion.dto.MedicoDTO;
import gestion.dto.PacienteDTO;
import gestion.dto.jwt.JwtRequestDTO;
import gestion.dto.jwt.JwtResponseDTO;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MiApi {

    @POST("/servidor/autenticacion/login")
    Call<JwtResponseDTO> login(@Body JwtRequestDTO request);

    @POST("/servidor/pacientes")
    Call<PacienteDTO> aniadirPaciente(@Body PacienteDTO paciente);

    @POST("/servidor/hospitales")
    Call<HospitalDTO> aniadirHospital(@Body HospitalDTO hospital);

    @POST("/servidor/medicos")
    Call<MedicoDTO> aniadirMedico(@Body MedicoDTO medico);

    @POST("/servidor/informes")
    Call<InformeDTO> aniadirInforme(@Body InformeDTO informeDto);

    @GET("/servidor/{nLicencia}/pacientes")
    Call<List<PacienteDTO>> buscarPacienteXMedico(@Path("nLicencia") String nLicencia);

    @GET("/servidor/medicos/{nLicencia}/citas")
    Call<List<CitaDTO>> buscarCitaXMedico(@Path("nLicencia") String nLicencia);

    @GET("/servidor/medicos/{nLicencia}/informes")
    Call<List<InformeDTO>> buscarInformesXMedico(@Path("nLicencia") String nLicencia);

    @DELETE("/servidor/pacientes/{nSS}")
    Call<Void> eliminarPaciente(@Path("nSS") String nSS);

    @DELETE("/servidor/medicos/{nLicencia}")
    Call<Void> eliminarMedico(@Path("nLicencia") String nLicencia);

    @GET("/servidor/medicos")
    Call<List<MedicoDTO>> listMedicos();

    @GET("/servidor/medicos/{nLicencia}")
    Call<MedicoDTO> obtenerMedico(@Path("nLicencia") String nLicencia);

    @GET("/servidor/medicos/{nombrehos}/hospital")
    Call<List<MedicoDTO>> BuscarMedicosXHospital(@Path("nombrehos") String nombrehos);

    @GET("/servidor/medicos/{especialidad}/{nombrehos}/hospital")
    Call<List<MedicoDTO>> BuscarMedicoXEspecialidad(@Path("especialidad") String especialidad,
            @Path("nombrehos") String nombrehos);

    @GET("/servidor/pacientes")
    Call<List<PacienteDTO>> listPacientes();

    @GET("/servidor/hospitales/")
    Call<List<HospitalDTO>> listaHospitales();

    @GET("/servidor/pacientes/{nSS}/citas")
    Call<List<CitaDTO>> buscarXPaciente(@Path("nSS") String nSS);

    @POST("/servidor/citas")
    Call<CitaDTO> aniadirCita(@Body CitaDTO citaDTO);

    @DELETE("/servidor/citas/{id}")
    Call<Void> eliminarCita(@Path("id") int id);

    @DELETE("/servidor/informes/{nombre}")
    Call<Void> eliminarInforme(@Path("nombre") String nombre);

    @GET("/servidor/pacientes/{nSS}/informes")
    Call<List<InformeDTO>> buscarInformesXPaciente(@Path("nSS") String nSS);

    @GET("/servidor/citas/{nSS}/buscarMMedico")
    Call<MedicoDTO> buscarMiMedico(@Path("nSS") String nSS);

}
