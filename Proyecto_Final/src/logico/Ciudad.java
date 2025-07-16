package logico;

public class Ciudad {
	public int codCiudad;
	public String Nombre;
	
	public Ciudad(int codCiudad, String nombre) {
		super();
		this.codCiudad = codCiudad;
		Nombre = nombre;
	}

	public int getCodCiudad() {
		return codCiudad;
	}

	public void setCodCiudad(int codCiudad) {
		this.codCiudad = codCiudad;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	
	
}
