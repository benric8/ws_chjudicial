package pe.gob.pj.hjudicial.dao.dto.consultas;

import java.io.Serializable;

import lombok.Data;

@Data
public class CondenaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String documento;
	private String nombresApellidos;
	private String pena;
	private String delito;
	private String tiempoCondena;
	private String fechaSentencia;
	private String fechaInicio;
	private String fechaFin;
	private String rehabilitado;
	
	public CondenaDTO(String documento, String nombresApellidos, String pena, String delito,
			String tiempoCondena, String fechaSentencia, String fechaInicio, String fechaFin, String rehabilitado) {
		super();
		this.documento = documento;
		this.nombresApellidos = nombresApellidos;
		this.pena = pena;
		this.delito = delito;
		this.tiempoCondena = tiempoCondena;
		this.fechaSentencia = fechaSentencia;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.rehabilitado = rehabilitado;
	}

	public CondenaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
