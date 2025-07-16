package logico;

public class EstadisticaJuego {
	public int idJuego;
	public int idEstadistica;
	public int idJugador;
	public int cantEstadistica;
	
	public EstadisticaJuego(int idJuego, int idEstadistica, int idJugador, int cantEstadistica) {
		super();
		this.idJuego = idJuego;
		this.idEstadistica = idEstadistica;
		this.idJugador = idJugador;
		this.cantEstadistica = cantEstadistica;
	}

	public int getCantEstadistica() {
		return cantEstadistica;
	}

	public void setCantEstadistica(int cantEstadistica) {
		this.cantEstadistica = cantEstadistica;
	}

	public int getIdJuego() {
		return idJuego;
	}

	public int getIdEstadistica() {
		return idEstadistica;
	}

	public int getIdJugador() {
		return idJugador;
	}
	
	
}
