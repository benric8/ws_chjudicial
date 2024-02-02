/*
 * Copyright 2022 Poder Judicial del Perú
 */
package pe.gob.pj.hjudicial.dao.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.hjudicial.dao.dto.consultas.PrisionPreventivaDTO;
import pe.gob.pj.hjudicial.dao.repository.ConsultaPrisionPreventivaDao;
import pe.gob.pj.pjseguridad.dao.config.ClientDatabaseContextHolder;
import pe.gob.pj.pjseguridad.dao.dto.DataSourceDTO;

/**
 * <pre>
 * Objeto     : ConsultaPrisionPreventivaDaoImpl.
 * Descripción: Clase de acceso a datos que implementa las operaciones de consulta de prisiones preventivas.
 * Fecha      : 2022-07-14
 * Autor      : CALTAMIRANOME
 * ----------------------------------------------------------------------------------------------------------------------
 * ID    Fecha         Autor               Método                            Tipo Cambio     Descripción                             
 * ----------------------------------------------------------------------------------------------------------------------
 * #1    2022-07-14    CALTAMIRANOME       -                                 Nuevo           Creación de la clase y sus métodos.
 * </pre>
 */
@Slf4j
@Component("consultaPrisionPreventivaDao")
public class ConsultaPrisionPreventivaDaoImpl implements ConsultaPrisionPreventivaDao {

	@Autowired
	@Qualifier("dynamicSijJdbcTemplate")
	private JdbcTemplate dynamicSijJdbcTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PrisionPreventivaDTO> consultaPorDni(String cuo, String numeroDocumento, DataSourceDTO dataSource) throws Exception {
		List<PrisionPreventivaDTO> lstResultado = new ArrayList<PrisionPreventivaDTO>();
		ClientDatabaseContextHolder.set(dataSource.getCodigoBd());
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("{call ");
			sql.append(dataSource.getEsquema());
			sql.append(".usp_ListadoPrisionPrevDni(?)}");
			lstResultado = dynamicSijJdbcTemplate.query(sql.toString(), new RowMapper<PrisionPreventivaDTO>() { 
				@Override
				public PrisionPreventivaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					PrisionPreventivaDTO row = new PrisionPreventivaDTO();
					row.setTipoParte(StringUtils.defaultString(rs.getString("TipoParte")));
					row.setDni(StringUtils.defaultString(rs.getString("DNI")));
					row.setNombres(StringUtils.defaultString(rs.getString("nombres")));
					row.setDelito(StringUtils.defaultString(rs.getString("Delito")));
					row.setMedida(StringUtils.defaultString(rs.getString("Medida")));
					
					row.setFechaInicio(StringUtils.defaultString(rs.getString("f_inicio")));
					row.setFechaFin(StringUtils.defaultString(rs.getString("f_final")));
					row.setEstado(StringUtils.defaultString(rs.getString("Estado")));
					return row;
				}
			}, numeroDocumento);
			
		} catch (Exception e) {
			log.error("cuo, Ocurrio un error consultando al SP usp_ListadoPrisionPrevDni de la base de datos: {}", cuo, dataSource.getCodigoBd());
			log.error("{} Detalle del error: {}", cuo, e.getMessage());
		} finally {
			ClientDatabaseContextHolder.clear();
		}
		return lstResultado;
	}

	@Override
	public List<PrisionPreventivaDTO> consultaPorApellidosYNombres(String cuo, String apellidoPaterno,
			String apellidoMaterno, String nombres, DataSourceDTO dataSource) throws Exception {
		List<PrisionPreventivaDTO> lista = new ArrayList<PrisionPreventivaDTO>();
		ClientDatabaseContextHolder.set(dataSource.getCodigoBd());
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("{call ");
			sql.append(dataSource.getEsquema());
			sql.append(".usp_ListadoPrisionPrevParte(?, ?, ?)}");
			lista = dynamicSijJdbcTemplate.query(sql.toString(), new RowMapper<PrisionPreventivaDTO>() { 
				@Override
				public PrisionPreventivaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					PrisionPreventivaDTO row = new PrisionPreventivaDTO();
					row.setTipoParte(StringUtils.defaultString(rs.getString("TipoParte")));
					row.setDni(StringUtils.defaultString(rs.getString("DNI")));
					row.setNombres(StringUtils.defaultString(rs.getString("nombres")));
					row.setDelito(StringUtils.defaultString(rs.getString("Delito")));
					row.setMedida(StringUtils.defaultString(rs.getString("Medida")));
					row.setFechaInicio(StringUtils.defaultString(rs.getString("f_inicio")));
					row.setFechaFin(StringUtils.defaultString(rs.getString("f_final")));
					row.setEstado(StringUtils.defaultString(rs.getString("Estado")));
					return row;
				}
			}, apellidoPaterno, apellidoMaterno, nombres);
		} catch (Exception e) {
			log.error("{} Ocurrio un error consultando al SP usp_ListadoPrisionPrevParte de la base de datos: {}", cuo, dataSource.getCodigoBd());
			log.error("{} Detalle del error: {}", cuo, e.getMessage());
		} finally {
			ClientDatabaseContextHolder.clear();
		}
		return lista;
	}
	
}
