package logico;

public class Estadistica {
	public int idEstadistica;
	public String Descripcion;
	public int valor;
	
	public Estadistica(int idEstadistica, String descripcion, int valor) {
		super();
		this.idEstadistica = idEstadistica;
		this.Descripcion = descripcion;
		this.valor = valor;
	}

	public int getIdEstadistica() {
		return idEstadistica;
	}

	public void setIdEstadistica(int idEstadistica) {
		this.idEstadistica = idEstadistica;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.Descripcion = descripcion;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
	
}
