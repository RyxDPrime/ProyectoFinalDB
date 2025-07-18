package logico;

import java.util.ArrayList;

public class Controladora {

	public static ArrayList<Equipo> misEquipos;
	public static ArrayList<Ciudad> misCiudades;
	public static ArrayList<Jugador> misJugadores;
	public static ArrayList<Estadistica> misEstadisticas;
	public static ArrayList<Juego> misJuegos;
	public static ArrayList<EstadisticaJuego> misEstadisticaJuegos;
	public static int codEquipo = 1;
	public static int codCiudad = 1;
	public static int codJugador = 1;
	public static int codEstadistica = 1;
	public static int codJuego = 1;
	
	
	public static void main(String[] args) {

	}


	public static ArrayList<Equipo> getMisEquipos() {
		return misEquipos;
	}


	public static void setMisEquipos(ArrayList<Equipo> misEquipos) {
		Controladora.misEquipos = misEquipos;
	}


	public static ArrayList<Ciudad> getMisCiudades() {
		return misCiudades;
	}


	public static void setMisCiudades(ArrayList<Ciudad> misCiudades) {
		Controladora.misCiudades = misCiudades;
	}


	public static ArrayList<Jugador> getMisJugadores() {
		return misJugadores;
	}


	public static void setMisJugadores(ArrayList<Jugador> misJugadores) {
		Controladora.misJugadores = misJugadores;
	}


	public static ArrayList<Estadistica> getMisEstadisticas() {
		return misEstadisticas;
	}


	public static void setMisEstadisticas(ArrayList<Estadistica> misEstadisticas) {
		Controladora.misEstadisticas = misEstadisticas;
	}


	public static ArrayList<Juego> getMisJuegos() {
		return misJuegos;
	}


	public static void setMisJuegos(ArrayList<Juego> misJuegos) {
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
	
	public void insertarEquipo(Equipo equipo) {
		misEquipos.add(equipo);
		codEquipo++;
	}
	
	public void insertarCiudad(Ciudad ciudad) {
		misCiudades.add(ciudad);
		codCiudad++;
	}
	
	public void insertarJugador(Jugador jugador) {
		misJugadores.add(jugador);
		codJugador++;
	}
	
	public void insertarEstadistica(Estadistica estadistica) {
		misestadisticas.add(estadistica);
		codEstadistica++;
	}
	
	public void insertarJuego(Juego juego) {
		misJuegos.add(juego);
		codJuego++;
	}
	
	public void updateEquipo(Equipo equipo, int ind) {
		misEquipos.set(ind, equipo);
		
		
	}
	
	public void updateCiudad(Ciudad ciudad, int ind) {
		misCiudades.set(ind, ciudad);
		
		
	}
	public void updateJugador (Jugador jugador, int ind) {
		misJugadores.set(ind, jugador);
		
		
	}
	public void updateEstadistica(Estadistica estadistica, int ind) {
		misEstadisticas.set(ind, stadistica);
		
		
	}
	public void updateJuego(Juego juego, int ind) {
		misJuegos.set(ind, juego);
		
		
	}
	public void deleteEquipo (String codEquipo) {
		Equipo equipo = searchEquipoByCod(codEquipo);
		misEquipos.remove(equipo);
	}
	
	public void deleteCiudad (String codCiudad) {
		ciudad ciudad = searchCiudadByCod(codCiudad);
		misCiudades.remove(ciudad);
	}
	
	public void deleteJugador (String codJugador) {
		Jugador jugador = searchJugadorByCod(codJugador);
		misJugadores.remove(jugador);
	}
	
	public void deleteEstadistica (String codEstadistica) {
		Estadistica estadistica = searchEstadisticaByCod(codEstadistica);
		misEstadisticas.remove(estadistica);
	}
	
	public void deleteJuego (String codJuego) {
		Juego juego = searchJuegoByCod(codJuego);
		misJuegos.remove(juego);
	}
	
	 public Equipo searchEquipoByCod(int codEquipo) {
	        Equipo equipo = null;
	        boolean encontrado = false;
	        int i = 0;

	        while (!encontrado && i < misEquipos.size()) {
	            if (misEquipos.get(i).getCodEquipo() == codEquipo) {
	                equipo = misEquipos.get(i);
	                encontrado = true;
	            }
	            i++;
	        }
	        return equipo;
	    }
	 
	 public Jugador searchJugadorByCod(int codJugador) {
	        Jugador jugador = null;
	        boolean encontrado = false;
	        int i = 0;

	        while (!encontrado && i < misJugadores.size()) {
	            if (misJugadores.get(i).getCodJugador() == codJugador) {
	                encontrado = true;
	                jugador = misJugadores.get(i);
	            }
	            i++;
	        }
	        return jugador;
	    }
	
	 public Juego searchJuegoByCod(int codJuego) {
	        Juego juego = null;
	        boolean encontrado = false;
	        int i = 0;

	        while (!encontrado && i < misJuegos.size()) {
	            if (misJuegos.get(i).getCodJuego() == codJuego) {
	                encontrado = true;
	                juego = misJuegos.get(i);
	            }
	            i++;
	        }
	        return juego;
	    }
	 
	 public Ciudad searchCiudadByCod(int codCiudad) {
	        Ciudad ciudad = null;
	        boolean encontrado = false;
	        int i = 0;

	        while (!encontrado && i < misCiudades.size()) {
	            if (misCiudades.get(i).getCodCiudad() == codCiudad) {
	                encontrado = true;
	                ciudad = misCiudades.get(i);
	            }
	            i++;
	        }
	        return ciudad;
	    }
	 
	 public Estadistica searchEstadisticaByCod(int codEstadistica) {
	        Estadistica estadistica = null;
	        boolean encontrado = false;
	        int i = 0;

	        while (!encontrado && i < misEstadisticas.size()) {
	            if (misEstadisticas.get(i).getCodEstadistica() == codEstadistica) {
	                encontrado = true;
	                estadistica = misEstadisticas.get(i);
	            }
	            i++;
	        }
	        return estadistica;
	    }
	
}
