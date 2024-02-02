package pe.gob.pj.hjudicial.dao.repository.impl;

import java.io.Serializable;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import pe.gob.pj.hjudicial.dao.dto.ResponseConsultaCondenasDTO;
import pe.gob.pj.hjudicial.dao.dto.consultas.CondenaDTO;
import pe.gob.pj.hjudicial.dao.procedures.ProcedureRnc;
import pe.gob.pj.hjudicial.dao.repository.ConsultaCondenasDao;
import pe.gob.pj.hjudicial.dao.utils.ConstantesProject;
import pe.gob.pj.hjudicial.dao.utils.UtilsProject;

/**
 * Objeto     : ConsultaCondenasDaoImpl.
 * Descripción: Clase que implementa las operaciones dao de opciones de consulta de condenas.
 * Fecha      : 2022-07-12
 * Autor      : oruizb
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-12    oruizb       		-                                 Nuevo           Creación de la clase y sus métodos.
 */

@Component("consultaCondenasDao")
public class ConsultaCondenasDaoImpl implements Serializable, ConsultaCondenasDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(ConsultaCondenasDaoImpl.class);
	
	@Autowired
	@Qualifier("jdbcTemplateCondenas")
	protected JdbcTemplate jdbcCondenas;

	@Override
	public ResponseConsultaCondenasDTO consultaXDni(String cuo, String numeroDocumento) throws Exception {
		ResponseConsultaCondenasDTO responseConsulta = new ResponseConsultaCondenasDTO();
		try {
			
			List<CondenaDTO> condenas = new ArrayList<CondenaDTO>();		
			
			StringBuilder sql = new StringBuilder();
			sql.append(ProcedureRnc.QUERY_CONSULTAR_CONDENAS_DNI);
			
			Object[] params = {numeroDocumento};
			int[] tipo = { Types.VARCHAR};
			
			condenas = this.jdbcCondenas.query(sql.toString(), params, tipo, 
					(rs, rowNum) -> new CondenaDTO(
							rs.getString("dni"), 
							rs.getString("nombres") + " " + rs.getString("apll_pater_solic") + (!UtilsProject.isNullOrEmpty(rs.getString("apll_mater_solic")) ? " " + rs.getString("apll_mater_solic") : ""), 
							rs.getString("desc_pena"), 
							rs.getString("delito1"), 
							(UtilsProject.isNullOrEmpty(rs.getString("anno_pena_boletn")) ? "0" : rs.getString("anno_pena_boletn")) + " AÑO(S)," + 
							(UtilsProject.isNullOrEmpty(rs.getString("mess_pena_boletn")) ? "0" : rs.getString("mess_pena_boletn")) + " MES(ES)," + 
							(UtilsProject.isNullOrEmpty(rs.getString("diaa_pena_boletn")) ? "0" : rs.getString("diaa_pena_boletn")) + " DIAS," , 
							UtilsProject.isNull(rs.getString("fech_pronu_boletn")), 
							UtilsProject.isNull(rs.getString("fech_inici_conden")), 
							UtilsProject.isNull(rs.getString("fech_fin_conden")), 
							UtilsProject.isNullOrEmpty(rs.getString("indc_rehabilitado")) ? "NO" : rs.getString("indc_rehabilitado").equalsIgnoreCase(ConstantesProject.LETRA_S) ? "SI" : "NO" ));
			
			responseConsulta.setIndicador(ConstantesProject.RPTA_1);
			responseConsulta.setMensaje("Consulta de condenas por número de documento exitoso.");
			
			responseConsulta.setCondenas(condenas);
		} catch (Exception e) {
			logger.error("{} Error dao consultaXDni: {}", cuo, e.getMessage());
			throw new Exception(e);
		}
		
		return responseConsulta;
	}

	@Override
	public ResponseConsultaCondenasDTO consultaXNombres(String cuo, String apellidoPaterno, String apellidoMaterno, String nombres) throws Exception {
		ResponseConsultaCondenasDTO responseConsulta = new ResponseConsultaCondenasDTO();
		try {
			
			List<CondenaDTO> condenas = new ArrayList<CondenaDTO>();
			
			StringBuilder sql = new StringBuilder();
			sql.append(ProcedureRnc.QUERY_CONSULTAR_CONDENAS_NOMBRES);
			
			Object[] params = {apellidoPaterno,apellidoMaterno,nombres};
			int[] tipo = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
			
			condenas = this.jdbcCondenas.query(sql.toString(), params, tipo, 
					(rs, rowNum) -> new CondenaDTO(
							rs.getString("dni"), 
							rs.getString("nombres") + " " + rs.getString("apll_pater_solic") + (!UtilsProject.isNullOrEmpty(rs.getString("apll_mater_solic")) ? " " + rs.getString("apll_mater_solic") : ""), 
							rs.getString("desc_pena"), 
							rs.getString("delito1"), 
							(UtilsProject.isNullOrEmpty(rs.getString("anno_pena_boletn")) ? "0" : rs.getString("anno_pena_boletn")) + " AÑO(S)," + 
							(UtilsProject.isNullOrEmpty(rs.getString("mess_pena_boletn")) ? "0" : rs.getString("mess_pena_boletn")) + " MES(ES)," + 
							(UtilsProject.isNullOrEmpty(rs.getString("diaa_pena_boletn")) ? "0" : rs.getString("diaa_pena_boletn")) + " DIAS," , 
							UtilsProject.isNull(rs.getString("fech_pronu_boletn")), 
							UtilsProject.isNull(rs.getString("fech_inici_conden")), 
							UtilsProject.isNull(rs.getString("fech_fin_conden")), 
							UtilsProject.isNullOrEmpty(rs.getString("indc_rehabilitado")) ? "NO" : rs.getString("indc_rehabilitado").equalsIgnoreCase(ConstantesProject.LETRA_S) ? "SI" : "NO" ));
			
			responseConsulta.setIndicador(ConstantesProject.RPTA_1);
			responseConsulta.setMensaje("Consulta de condenas por nombres exitoso.");
			
			responseConsulta.setCondenas(condenas);
		} catch (Exception e) {
			logger.error("{} Error dao consultaXNombres: {}", cuo, e.getMessage());
			throw new Exception(e);
		}
		return responseConsulta;
	}

}
