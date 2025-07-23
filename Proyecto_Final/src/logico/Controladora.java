package logico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.SQLConnection;

public class Controladora {

	public ArrayList<Equipo> misEquipos;
	public ArrayList<Ciudad> misCiudades;
	public ArrayList<Jugador> misJugadores;
	public ArrayList<Estadistica> misEstadisticas;
	public ArrayList<Juego> misJuegos;
	public ArrayList<EstadisticaJuego> misEstadisticaJuegos;
	public static Controladora miControladora = null;
	public static int codEquipo = 1;
	public static int IdCiudad = 1;
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
		this.misJuegos = new ArrayList<Juego>();
		
	}

	 public static Controladora getInstance() {
		if(miControladora == null) {
			miControladora = new Controladora();
		}
		return miControladora;
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


	public static int getIdCiudad() {
		IdCiudad += 1;
		return IdCiudad;
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
	    Connection conn = SQLConnection.getConnection();
	    try {
	        String selectSql = "SELECT MAX(IdEquipo) as lastId FROM Equipo";
	        PreparedStatement selectStmt = conn.prepareStatement(selectSql);
	        ResultSet rs = selectStmt.executeQuery();
	        int newId = 1;
	        if (rs.next()) {
	            int lastId = rs.getInt("lastId");
	            if (!rs.wasNull() && lastId > 0) {
	                newId = lastId + 1;
	            }
	        }
	        
	        String idCiudadFormatted = String.format("%02d", equipo.getIdCiudad()).trim();
	        
	        String sql = "INSERT INTO Equipo (IdEquipo, Nombre_Equipo, IdCiudad) VALUES (?, ?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, newId);
	        pstmt.setString(2, equipo.getNombreString());
	        pstmt.setString(3, idCiudadFormatted);
	        
	        pstmt.executeUpdate();
	        
	        equipo.setIdEquipo(newId);
	        misEquipos.add(equipo);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void insertarCiudad(Ciudad ciudad) {
		Connection conn = SQLConnection.getConnection();
		try {
			String selectSql = "SELECT MAX(IdCiudad) as lastId FROM Ciudad";
			PreparedStatement selectStmt = conn.prepareStatement(selectSql);
			ResultSet rs = selectStmt.executeQuery();
			int newId = 1;
			if (rs.next()) {
				int lastId = rs.getInt("lastId");
				if (!rs.wasNull() && lastId > 0) {
					newId = lastId + 1;
				}
			}
			
			String sql = "INSERT INTO Ciudad (IdCiudad, Nombre_Ciudad) VALUES (?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, newId);
			pstmt.setString(2, ciudad.getNombre());
			pstmt.executeUpdate();
			
			ciudad.setCodCiudad(newId);
			misCiudades.add(ciudad);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertarJugador(Jugador jugador) {
    Connection conn = SQLConnection.getConnection();
    PreparedStatement selectStmt = null;
    PreparedStatement pstmt = null;
    
    try {
        if (jugador == null) {
            throw new IllegalArgumentException("El jugador no puede ser null");
        }
        
        String selectSql = "SELECT MAX(CAST(IdJugador AS INT)) as lastId FROM Jugador";
        selectStmt = conn.prepareStatement(selectSql);
        ResultSet rs = selectStmt.executeQuery();
        
        int newId = 1;
        if (rs.next()) {
            int lastId = rs.getInt("lastId");
            if (!rs.wasNull()) {
                newId = lastId + 1;
            }
        }
        
        String newIdFormatted = String.format("%02d", newId);
        String idCiudadFormatted = String.format("%02d", jugador.getIdCiudad()).trim();
        String idEquipoFormatted = String.format("%02d", jugador.getIdEquipo()).trim();
        
        if (newIdFormatted == null || newIdFormatted.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del jugador no puede ser null o vacío");
        }
        if (jugador.getNombre() == null || jugador.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del jugador no puede ser null o vacío");
        }
        if (jugador.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser null");
        }
        
        String sql = "INSERT INTO Jugador (IdJugador, Nombre, IdCiudad, Fecha_Nacimiento, Numero_Jugador, IdEquipo) VALUES (?, ?, ?, ?, ?, ?)";
        pstmt = conn.prepareStatement(sql);
        
        pstmt.setString(1, newIdFormatted.trim());
        pstmt.setString(2, jugador.getNombre().trim());
        pstmt.setString(3, idCiudadFormatted);
        pstmt.setDate(4, new java.sql.Date(jugador.getFechaNacimiento().getTime()));
        pstmt.setInt(5, jugador.getNumeroJugador());
        pstmt.setString(6, idEquipoFormatted);
        
        int rowsAffected = pstmt.executeUpdate();
        
        if (rowsAffected > 0) {
            jugador.setIdJugador(newId);
            misJugadores.add(jugador);
        } else {
            throw new SQLException("No se insertó ninguna fila");
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error al insertar jugador: " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Error al insertar jugador: " + e.getMessage());
    } finally {
        try {
            if (pstmt != null) pstmt.close();
            if (selectStmt != null) selectStmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            //
        }
    }
	}
	
	public void insertarEstadistica(Estadistica estadistica) {
		Connection conn = SQLConnection.getConnection();
		try {
			String selectSql = "SELECT MAX(idEstadistica) as lastId FROM Estadistica";
			PreparedStatement selectStmt = conn.prepareStatement(selectSql);
			ResultSet rs = selectStmt.executeQuery();
			int newId = 1;
			if (rs.next()) {
				int lastId = rs.getInt("lastId");
				if (!rs.wasNull() && lastId > 0) {
					newId = lastId + 1;
				}
			}
			
			String sql = "INSERT INTO Estadistica (idEstadistica, Descripcion, valor) VALUES (?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, newId);
			pstmt.setString(2, estadistica.getDescripcion());
			pstmt.setInt(3, estadistica.getValor());
			pstmt.executeUpdate();
			
			estadistica.setIdEstadistica(newId);
			misEstadisticas.add(estadistica);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertarJuego(Juego juego) {
	    Connection conn = SQLConnection.getConnection();
	    try {
	        String selectSql = "SELECT MAX(idJuego) as lastId FROM Juego";
	        PreparedStatement selectStmt = conn.prepareStatement(selectSql);
	        ResultSet rs = selectStmt.executeQuery();
	        
	        int newId = 1;
	        if (rs.next()) {
	            int lastId = rs.getInt("lastId");
	            if (!rs.wasNull()) {
	                newId = lastId + 1;
	            }
	        }
	        
	        String checkSql = "SELECT COUNT(*) as count FROM Juego WHERE idJuego = ?";
	        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
	        checkStmt.setInt(1, newId);
	        ResultSet checkRs = checkStmt.executeQuery();
	        
	        while (checkRs.next() && checkRs.getInt("count") > 0) {
	            newId++;
	            checkStmt.setInt(1, newId);
	            checkRs = checkStmt.executeQuery();
	        }
	        
	        String idEquipoAFormatted = String.format("%02d", juego.getIdEquipoA()).trim();
	        String idEquipoBFormatted = String.format("%02d", juego.getIdEquipoB()).trim();
	        
	        String sql = "INSERT INTO Juego (idJuego, Descripcion, Id_EquipoA_Local, Id_EquipoB_Visitante, fecha_hora) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, newId);
	        pstmt.setString(2, juego.getDescripcion());
	        pstmt.setString(3, idEquipoAFormatted);
	        pstmt.setString(4, idEquipoBFormatted);
	        pstmt.setTimestamp(5, new java.sql.Timestamp(juego.getFecha_hora().getTime()));
	        
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            juego.setIdJuego(newId);
	            misJuegos.add(juego);
	        } else {
	            throw new SQLException("No se pudo insertar el juego");
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Error al insertar juego: " + e.getMessage());
	    }
	}
	
	public void updateEquipo(Equipo equipo, int ind) {
    Connection conn = SQLConnection.getConnection();
    try {
        // Formatear el ID de ciudad con cero a la izquierda
        String idCiudadFormatted = String.format("%02d", equipo.getIdCiudad()).trim();
        
        String sql = "UPDATE Equipo SET Nombre_Equipo = ?, IdCiudad = ? WHERE IdEquipo = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, equipo.getNombreString());
        pstmt.setString(2, idCiudadFormatted); // Usar String formateado
        pstmt.setInt(3, equipo.getIdEquipo());
        pstmt.executeUpdate();
        misEquipos.set(ind, equipo);
    } catch (SQLException e) {
        e.printStackTrace();
    }
	}
	
	public void updateCiudad(Ciudad ciudad, int ind) {
		Connection conn = SQLConnection.getConnection();
		try {
			String sql = "UPDATE Ciudad SET Nombre_Ciudad = ? WHERE IdCiudad = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ciudad.getNombre());
			pstmt.setInt(2, ciudad.getCodCiudad());
			pstmt.executeUpdate();
			misCiudades.set(ind, ciudad);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateJugador(Jugador jugador, int ind) {
    Connection conn = SQLConnection.getConnection();
    try {
        // Formatear IDs con ceros a la izquierda
        String idCiudadFormatted = String.format("%02d", jugador.getIdCiudad()).trim();
        String idEquipoFormatted = String.format("%02d", jugador.getIdEquipo()).trim();
        
        String sql = "UPDATE Jugador SET Nombre = ?, idCiudad = ?, Fecha_Nacimiento = ?, Numero_Jugador = ?, idEquipo = ? WHERE idJugador = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, jugador.getNombre());
        pstmt.setString(2, idCiudadFormatted); // Usar String formateado
        pstmt.setDate(3, new java.sql.Date(jugador.getFechaNacimiento().getTime()));
        pstmt.setInt(4, jugador.getNumeroJugador());
        pstmt.setString(5, idEquipoFormatted); // Usar String formateado
        pstmt.setInt(6, jugador.getIdJugador());
        pstmt.executeUpdate();
        misJugadores.set(ind, jugador);
    } catch (SQLException e) {
        e.printStackTrace();
    }
	}
	
	public void updateEstadistica(Estadistica estadistica, int ind) {
		Connection conn = SQLConnection.getConnection();
		try {
			String sql = "UPDATE Estadistica SET Descripcion = ?, valor = ? WHERE idEstadistica = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, estadistica.getDescripcion());
			pstmt.setInt(2, estadistica.getValor());
			pstmt.setInt(3, estadistica.getIdEstadistica());
			pstmt.executeUpdate();
			misEstadisticas.set(ind, estadistica);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateJuego(Juego juego, int ind) {
    Connection conn = SQLConnection.getConnection();
    try {
        // Formatear IDs de equipos con ceros a la izquierda
        String idEquipoAFormatted = String.format("%02d", juego.getIdEquipoA()).trim();
        String idEquipoBFormatted = String.format("%02d", juego.getIdEquipoB()).trim();
        
        String sql = "UPDATE Juego SET Descripcion = ?, Id_EquipoA_Local = ?, Id_EquipoB_Visitante = ?, fecha_hora = ? WHERE idJuego = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, juego.getDescripcion());
        pstmt.setString(2, idEquipoAFormatted); // Usar String formateado
        pstmt.setString(3, idEquipoBFormatted); // Usar String formateado
        pstmt.setTimestamp(4, new java.sql.Timestamp(juego.getFecha_hora().getTime()));
        pstmt.setInt(5, juego.getIdJuego());
        pstmt.executeUpdate();
        misJuegos.set(ind, juego);
    } catch (SQLException e) {
        e.printStackTrace();
    }
	}
	public void deleteEquipo(int codEquipo) {
		Connection conn = SQLConnection.getConnection();
		try {
			String sql = "DELETE FROM Equipo WHERE IdEquipo = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, codEquipo);
			pstmt.executeUpdate();
			Equipo equipo = searchEquipoByCod(codEquipo);
			if (equipo != null) {
				misEquipos.remove(equipo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCiudad(int IdCiudad) {
		Connection conn = SQLConnection.getConnection();
		try {
			String sql = "DELETE FROM Ciudad WHERE IdCiudad = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, IdCiudad);
			pstmt.executeUpdate();
			Ciudad ciudad = searchCiudadByCod(IdCiudad);
			if (ciudad != null) {
				misCiudades.remove(ciudad);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteJugador(int codJugador) {
		Connection conn = SQLConnection.getConnection();
		try {
			String sql = "DELETE FROM Jugador WHERE idJugador = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, codJugador);
			pstmt.executeUpdate();
			Jugador jugador = searchJugadorByCod(codJugador);
			if (jugador != null) {
				misJugadores.remove(jugador);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteEstadistica(int codEstadistica) {
		Connection conn = SQLConnection.getConnection();
		try {
			String sql = "DELETE FROM Estadistica WHERE idEstadistica = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, codEstadistica);
			pstmt.executeUpdate();
			Estadistica estadistica = searchEstadisticaByCod(codEstadistica);
			if (estadistica != null) {
				misEstadisticas.remove(estadistica);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteJuego(int codJuego) {
		Connection conn = SQLConnection.getConnection();
		try {
			String sql = "DELETE FROM Juego WHERE idJuego = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, codJuego);
			pstmt.executeUpdate();
			Juego juego = searchJuegoByCod(codJuego);
			if (juego != null) {
				misJuegos.remove(juego);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	 
	 public Ciudad searchCiudadByCod(int IdCiudad) {
	        Ciudad ciudad = null;
	        boolean encontrado = false;
	        int i = 0;

	        while (!encontrado && i < misCiudades.size()) {
	            if (misCiudades.get(i).getCodCiudad() == IdCiudad) {
	                encontrado = true;
	                ciudad = misCiudades.get(i);
	            }
	            i++;
	        }
	        return ciudad;
	    }
		
	 public Ciudad searchCiudadByNombre(String nombreCiudad) {
		    Ciudad ciudad = null;
		    boolean encontrado = false;
		    int i = 0;

		    while (!encontrado && i < misCiudades.size()) {
		        if (misCiudades.get(i).getNombre().trim().equalsIgnoreCase(nombreCiudad.trim())) {  // Agregar trim()
		            encontrado = true;
		            ciudad = misCiudades.get(i);
		        }
		        i++;
		    }
		    return ciudad;
		}

		public Equipo searchEquipoByNombre(String nombreEquipo) {
		    Equipo equipo = null;
		    boolean encontrado = false;
		    int i = 0;

		    while (!encontrado && i < misEquipos.size()) {
		        if (misEquipos.get(i).getNombreString().trim().equalsIgnoreCase(nombreEquipo.trim())) {  // Agregar trim()
		            encontrado = true;
		            equipo = misEquipos.get(i);
		        }
		        i++;
		    }
		    return equipo;
		}

	// También corregir el método searchEstadisticaByCod que está incompleto (líneas 515-524):
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
	// Methods to load data from database
	public void cargarEquiposFromDB() {
		misEquipos.clear();
		Connection conn = SQLConnection.getConnection();
		try {
			String sql = "SELECT IdEquipo, Nombre_Equipo, IdCiudad FROM Equipo";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int idEquipo = rs.getInt("IdEquipo");
				String nombreEquipo = rs.getString("Nombre_Equipo");
				int idCiudad = rs.getInt("IdCiudad");
				Equipo equipo = new Equipo(idEquipo, nombreEquipo, idCiudad);
				misEquipos.add(equipo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void cargarCiudadesFromDB() {
		misCiudades.clear();
		Connection conn = SQLConnection.getConnection();
		try {
			String sql = "SELECT * FROM Ciudad";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Ciudad ciudad = new Ciudad(
					rs.getInt("IdCiudad"),
					rs.getString("Nombre_Ciudad")
				);
				misCiudades.add(ciudad);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void cargarJugadoresFromDB() {
		misJugadores.clear();
		Connection conn = SQLConnection.getConnection();
		try {
			String sql = "SELECT * FROM Jugador";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Jugador jugador = new Jugador(
					rs.getInt("idJugador"),
					rs.getString("Nombre"),
					rs.getInt("idCiudad"),
					rs.getDate("Fecha_Nacimiento"),
					rs.getInt("Numero_Jugador"),
					rs.getInt("idEquipo")
				);
				misJugadores.add(jugador);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void cargarEstadisticasFromDB() {
		misEstadisticas.clear();
		Connection conn = SQLConnection.getConnection();
		try {
			String sql = "SELECT * FROM Estadistica";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Estadistica estadistica = new Estadistica(
					rs.getInt("idEstadistica"),
					rs.getString("Descripcion"),
					rs.getInt("valor")
				);
				misEstadisticas.add(estadistica);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void cargarJuegosFromDB() {
		misJuegos.clear();
		Connection conn = SQLConnection.getConnection();
		try {
			String sql = "SELECT * FROM Juegos";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Juego juego = new Juego(
					rs.getInt("idJuego"),
					rs.getString("Descripcion"),
					rs.getInt("Id_EquipoA_Local"),
					rs.getInt("Id_EquipoB_Visitante"),
					rs.getTimestamp("fecha_hora")
				);
				misJuegos.add(juego);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void cargarTodosLosDatos() {
		cargarCiudadesFromDB();
		cargarEquiposFromDB();
		cargarJugadoresFromDB();
		cargarEstadisticasFromDB();
		cargarJuegosFromDB();
	}
}
