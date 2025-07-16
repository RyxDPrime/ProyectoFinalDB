package logico;

import java.util.ArrayList;

public class Controladora {

	public static ArrayList<Equipo> misEquipos;
	public static ArrayList<Ciudad> misCiudades;
	public static ArrayList<Jugador> misJugadores;
	public static ArrayList<Estadistica> misEstadisticas;
	public static ArrayList<Juego> misJuegos;
	public static ArrayList<EstadisticaJuego> misEstadisticaJuegos;
	public static int codEquipo = 0;
	public static int codCiudad = 0;
	public static int codJugador = 0;
	public static int codEstadistica = 0;
	public static int codJuego = 0;
	
	
	public static void main(String[] args) {

	}


	public static ArrayList<Equipos> getMisEquipos() {
		return misEquipos;
	}


	public static void setMisEquipos(ArrayList<Equipos> misEquipos) {
		Controladora.misEquipos = misEquipos;
	}


	public static ArrayList<Ciudad> getMisCiudades() {
		return misCiudades;
	}


	public static void setMisCiudades(ArrayList<Ciudad> misCiudades) {
		Controladora.misCiudades = misCiudades;
	}


	public static ArrayList<Jugadores> getMisJugadores() {
		return misJugadores;
	}


	public static void setMisJugadores(ArrayList<Jugadores> misJugadores) {
		Controladora.misJugadores = misJugadores;
	}


	public static ArrayList<Estadisticas> getMisEstadisticas() {
		return misEstadisticas;
	}


	public static void setMisEstadisticas(ArrayList<Estadisticas> misEstadisticas) {
		Controladora.misEstadisticas = misEstadisticas;
	}


	public static ArrayList<Juegos> getMisJuegos() {
		return misJuegos;
	}


	public static void setMisJuegos(ArrayList<Juegos> misJuegos) {
		Controladora.misJuegos = misJuegos;
	}


	public static ArrayList<EstadisticaJuego> getMisEstadisticaJuegos() {
		return misEstadisticaJuegos;
	}


	public static void setMisEstadisticaJuegos(ArrayList<EstadisticaJuego> misEstadisticaJuegos) {
		Controladora.misEstadisticaJuegos = misEstadisticaJuegos;
	}


	public static int getCodEquipo() {
		codEquipo += 1;
		return codEquipo;
	}


	public static int getCodCiudad() {
		codCiudad += 1;
		return codCiudad;
	}


	public static int getCodJugador() {
		codJugador += 1;
		return codJugador;
	}


	public static int getCodEstadistica() {
		codEstadistica += 1;
		return codEstadistica;
	}


	public static int getCodJuego() {
		codJuego += 1;
		return codJuego;
	}
	
}
