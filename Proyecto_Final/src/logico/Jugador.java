package logico;

import java.util.Date;

public class Jugador {
	public int idJugador;
	public String Nombre;
	public int idCiudad;
	public Date fechaNacimiento;
	public int numeroJugador;
	public int idEquipo;
	
	public Jugador(int idJugador, String nombre, int idCiudad, Date fechaNacimiento, int numeroJugador,
			int idEquipo) {
		super();
		this.idJugador = idJugador;
		this.Nombre = nombre;
		this.idCiudad = idCiudad;
		this.fechaNacimiento = fechaNacimiento;
		this.numeroJugador = numeroJugador;
		this.idEquipo = idEquipo;
	}

	public int getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getNumeroJugador() {
		return numeroJugador;
	}

	public void setNumeroJugador(int numeroJugador) {
		this.numeroJugador = numeroJugador;
	}

	public int getIdCiudad() {
		return idCiudad;
	}

	public int getIdEquipo() {
		return idEquipo;
	}

	
	
}
	