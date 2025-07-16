package logico;

import java.util.Date;

public class Juego {
	public int idJuego;
	public String Descripcion;
	public int idEquipoA;
	public int idEquipoB;
	public Date fecha_hora;
	
	public Juego(int idJuego, String descripcion, int idEquipoA, int idEquipoB, Date fecha_hora) {
		super();
		this.idJuego = idJuego;
		Descripcion = descripcion;
		this.idEquipoA = idEquipoA;
		this.idEquipoB = idEquipoB;
		this.fecha_hora = fecha_hora;
	}

	public int getIdJuego() {
		return idJuego;
	}

	public void setIdJuego(int idJuego) {
		this.idJuego = idJuego;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public int getIdEquipoA() {
		return idEquipoA;
	}

	public void setIdEquipoA(int idEquipoA) {
		this.idEquipoA = idEquipoA;
	}

	public int getIdEquipoB() {
		return idEquipoB;
	}

	public void setIdEquipoB(int idEquipoB) {
		this.idEquipoB = idEquipoB;
	}

	public Date getFecha_hora() {
		return fecha_hora;
	}

	public void setFecha_hora(Date fecha_hora) {
		this.fecha_hora = fecha_hora;
	}

	
}
