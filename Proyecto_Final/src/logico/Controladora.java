package logico;

import java.util.ArrayList;

public class Controladora {

	public ArrayList<Equipo> misEquipos;
	public ArrayList<Ciudad> misCiudades;
	public ArrayList<Jugador> misJugadores;
	public ArrayList<Estadistica> misEstadisticas;
	public ArrayList<Juego> misJuegos;
	public ArrayList<EstadisticaJuego> misEstadisticaJuegos;
	public static Controladora miControladora = null;
	public static int codEquipo = 1;
	public static int codCiudad = 1;
	public static int codJugador = 1;
	public static int codEstadistica = 1;
	public static int codJuego = 1;

	public Controladora() {
		super();
		this.misJugadores = new ArrayList<Jugador>();
		this.misCiudades = new ArrayList<Ciudad>();
		this.misEquipos = new ArrayList<Equipo>();
		this.misEstadisticaJuegos = new ArrayList<EstadisticaJuego>();
		this.misEstadisticas = new ArrayList<Estadistica>();
		
	}

	 public static Controladora getInstance() {
		if(miControladora == null) {
			miControladora = new Controladora();
		}
		return null;
	 }

	public ArrayList<Equipo> getMisEquipos() {
		return misEquipos;
	}


	public void setMisEquipos(ArrayList<Equipo> misEquipos) {
		this.misEquipos = misEquipos;
	}


	public ArrayList<Ciudad> getMisCiudades() {
		return misCiudades;
	}


	public void setMisCiudades(ArrayList<Ciudad> misCiudades) {
		this.misCiudades = misCiudades;
	}


	public ArrayList<Jugador> getMisJugadores() {
		return misJugadores;
	}


	public void setMisJugadores(ArrayList<Jugador> misJugadores) {
		this.misJugadores = misJugadores;
	}


	public ArrayList<Estadistica> getMisEstadisticas() {
		return misEstadisticas;
	}


	public void setMisEstadisticas(ArrayList<Estadistica> misEstadisticas) {
		this.misEstadisticas = misEstadisticas;
	}


	public ArrayList<Juego> getMisJuegos() {
		return misJuegos;
	}


	public void setMisJuegos(ArrayList<Juego> misJuegos) {
		this.misJuegos = misJuegos;
	}


	public ArrayList<EstadisticaJuego> getMisEstadisticaJuegos() {
		return misEstadisticaJuegos;
	}


	public void setMisEstadisticaJuegos(ArrayList<EstadisticaJuego> misEstadisticaJuegos) {
		this.misEstadisticaJuegos = misEstadisticaJuegos;
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
		misEstadisticas.add(estadistica);
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
		misEstadisticas.set(ind, estadistica);
		
		
	}
	public void updateJuego(Juego juego, int ind) {
		misJuegos.set(ind, juego);
		
		
	}
	public void deleteEquipo (int codEquipo) {
		Equipo equipo = searchEquipoByCod(codEquipo);
		misEquipos.remove(equipo);
	}
	
	public void deleteCiudad (int codCiudad) {
		Ciudad ciudad = searchCiudadByCod(codCiudad);
		misCiudades.remove(ciudad);
	}
	
	public void deleteJugador (int codJugador) {
		Jugador jugador = searchJugadorByCod(codJugador);
		misJugadores.remove(jugador);
	}
	
	public void deleteEstadistica (int codEstadistica) {
		Estadistica estadistica = searchEstadisticaByCod(codEstadistica);
		misEstadisticas.remove(estadistica);
	}
	
	public void deleteJuego (int codJuego) {
		Juego juego = searchJuegoByCod(codJuego);
		misJuegos.remove(juego);
	}
	
	 public Equipo searchEquipoByCod(int codEquipo) {
	        Equipo equipo = null;
	        boolean encontrado = false;
	        int i = 0;

	        while (!encontrado && i < misEquipos.size()) {
	            if (misEquipos.get(i).getIdEquipo() == codEquipo) {
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
	            if (misJugadores.get(i).getIdJugador() == codJugador) {
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
	            if (misJuegos.get(i).getIdJuego() == codJuego) {
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
	            if (misEstadisticas.get(i).getIdEstadistica() == codEstadistica) {
	                encontrado = true;
	                estadistica = misEstadisticas.get(i);
	            }
	            i++;
	        }
	        return estadistica;
	    }


	
}
