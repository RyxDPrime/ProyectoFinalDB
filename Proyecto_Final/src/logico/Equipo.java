package logico;

public class Equipo {
	public int idEquipo;
	public String nombreString;
	public int idCiudad;
	
	public Equipo(int idEquipo, String nombre, int idCiudad) {
		super();
		this.idEquipo = idEquipo;
		this.nombreString = nombreString;
		this.idCiudad = idCiudad;
	}

	public int getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(int idEquipo) {
		this.idEquipo = idEquipo;
	}

	public String getNombreString() {
		return nombreString;
	}

	public void setNombreString(String nombreString) {
		this.nombreString = nombreString;
	}

	public int getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}
	
	
	
	
}
