package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import logico.Ciudad;
import logico.Controladora;
import logico.Equipo;
import logico.RoundedBorder;
import server.SQLConnection;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;
import javax.swing.AbstractCellEditor;

public class DetalleJuego extends JDialog {

	private static final Color PrimaryC = new Color(3, 88, 157);
	private static final Color SecondaryC = new Color(3, 104, 196);
	private static final Color ThirdC = new Color(247, 251, 255);
	private static final Color AccentColor = new Color(247, 109, 71); // 255, 150, 95
	private static final Color AccentHoverColor = new Color(255, 136, 73);
	private static final Color BGC = new Color(236, 240, 241);
	private static final Color TextColor = new Color(52, 73, 94);
	private static final Color WTextColor = new Color(255, 255, 255);
	private static final Color ButtonColor = new Color(42, 145, 230);
	private static final Color ButtonBorderColor = new Color(42, 145, 230);
	private static final Color HoverEffectColor = new Color(3, 109, 195);
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JScrollPane scrollPane;
	private JPanel panel;
	private DefaultTableModel model;
	private Object row[];
	private String jugador = "";
	private JButton updatebtn;
	private JButton returnbtn;
	private int juegoId;
	private JLabel lblTitulo;
	private JLabel lblJuego;
	private JLabel lblFecha;
	private JLabel locallabel;
	private JLabel visitanteLabel;
	private JLabel teamAlabel;
	private JLabel teamBlabel;
	private boolean modoEdicion = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DetalleJuego dialog = new DetalleJuego(0);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DetalleJuego(int juegoId) {
		this.juegoId = juegoId;
		setUndecorated(true);
		setBounds(100, 100, 1331, 704);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		// Configurar header de la tabla ANTES de crear el modelo
		String[] header = {"Jugador", "Puntos", "Asistencias", "Rebotes", "Bolas Robadas", "Faltas", "Técnicas", "Bolas Perdidas"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(header);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 100, 1116, 604);
		contentPanel.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int ind = table.getSelectedRow();
				if (ind >= 0) {
					jugador = (String) table.getValueAt(ind, 0); // Nombre del jugador
				}
			}
		});
		table.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(model);
		scrollPane.setViewportView(table);

		table.getTableHeader().setPreferredSize(new Dimension(10, 40));
		table.getTableHeader().setBackground(PrimaryC);
		table.getTableHeader().setForeground(Color.WHITE);

		table.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		table.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 18));
		table.setRowHeight(30);
		table.setBorder(new RoundedBorder(Color.WHITE, 1, 20));

		// Renderer personalizado para manejar headers de equipos y totales
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				
				String jugadorValue = (String) table.getValueAt(row, 0);
				
				// Verificar si es un header de equipo
				if (jugadorValue != null && (jugadorValue.startsWith("EQUIPO LOCAL:") || jugadorValue.startsWith("EQUIPO VISITANTE:"))) {
					c.setBackground(PrimaryC);
					c.setForeground(Color.WHITE);
					setFont(new Font("Century Gothic", Font.BOLD, 16));
				}
				// Verificar si es una fila de total
				else if (jugadorValue != null && jugadorValue.startsWith("TOTAL ")) {
					c.setBackground(new Color(240, 248, 255));
					c.setForeground(new Color(0, 102, 204));
					setFont(new Font("Century Gothic", Font.BOLD, 14));
				}
				// Filas normales de jugadores
				else if (!isSelected) {
					c.setBackground(row % 2 == 0 ? new Color(250, 250, 250) : Color.WHITE);
					c.setForeground(Color.BLACK);
					setFont(new Font("Century Gothic", Font.PLAIN, 14));
					table.setGridColor(row % 2 == 0 ? new Color(240, 240, 240) : Color.WHITE);
				}
				
				return c;
			}
		});

		scrollPane.setViewportView(table);
		
		panel = new JPanel();
		panel.setBackground(PrimaryC);
		panel.setBounds(1118, 0, 213, 704);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		returnbtn = new JButton("Retornar");
		returnbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				returnbtn.setBorder(new RoundedBorder(new Color(189, 189, 189), 1, 20));
				returnbtn.setBackground(new Color(189, 189, 189));
				returnbtn.setForeground(Color.black);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				returnbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
				returnbtn.setBackground(new Color(248, 248, 248));
				returnbtn.setForeground(SecondaryC);
			}
		});
		returnbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		returnbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
		returnbtn.setBackground(new Color(248, 248, 248));
		returnbtn.setForeground(SecondaryC);
		returnbtn.setOpaque(true);
		returnbtn.setFont(new Font("century gothic", Font.BOLD, 18));
		returnbtn.setBounds(22, 650, 169, 40);
		panel.add(returnbtn);
		
		updatebtn = new JButton("Editar");

		updatebtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (updatebtn.isEnabled()) {
					updatebtn.setBorder(new RoundedBorder(new Color(255, 183, 77), 1, 20));
					updatebtn.setBackground(new Color(255, 183, 77));
					updatebtn.setForeground(Color.white);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				updatebtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
				updatebtn.setForeground(SecondaryC);
				updatebtn.setBackground(new Color(248, 248, 248));
			}
		});
		updatebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!modoEdicion) {
					// Activar modo edición
					modoEdicion = true;
					updatebtn.setText("Guardar");
					configurarEditores();
				} else {
					// Guardar cambios
					guardarEstadisticas();
					modoEdicion = false;
					updatebtn.setText("Editar");
					removerEditores();
				}
			}
		});
		updatebtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
		updatebtn.setBackground(new Color(248, 248, 248));
		updatebtn.setForeground(SecondaryC);
		updatebtn.setOpaque(true);
		updatebtn.setFont(new Font("century gothic", Font.BOLD, 18));
		updatebtn.setBounds(22, 599, 169, 40);
		panel.add(updatebtn);
		
		lblTitulo = new JLabel("ESTADÍSTICAS DEL JUEGO");
		lblTitulo.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblTitulo.setForeground(PrimaryC);
		lblTitulo.setBounds(20, 10, 400, 30);
		contentPanel.add(lblTitulo);
		
		lblJuego = new JLabel("JUEGO: ");
		lblJuego.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblJuego.setBounds(20, 45, 200, 20);
		contentPanel.add(lblJuego);
		
		lblFecha = new JLabel("FECHA: ");
		lblFecha.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblFecha.setBounds(400, 45, 200, 20);
		contentPanel.add(lblFecha);
		
		locallabel = new JLabel("Local:");
		locallabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
		locallabel.setBounds(20, 75, 68, 14);
		contentPanel.add(locallabel);
		
		visitanteLabel = new JLabel("Visitante:");
		visitanteLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
		visitanteLabel.setBounds(400, 75, 68, 14);
		contentPanel.add(visitanteLabel);
		
		teamAlabel = new JLabel("New label");
		teamAlabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
		teamAlabel.setBounds(67, 75, 169, 14);
		contentPanel.add(teamAlabel);
		
		teamBlabel = new JLabel("New label");
		teamBlabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
		teamBlabel.setBounds(467, 72, 200, 20);
		contentPanel.add(teamBlabel);
		
		// Cargar datos del stored procedure
		cargarEstadisticasJuego();
		
		// Cargar información básica del juego
		cargarInformacionJuego();
	}

	// Método modificado para cargar estadísticas y actualizar labels
	private void cargarEstadisticasJuego() {
	    model.setRowCount(0); // Limpiar tabla
	    
	    try (Connection connection = SQLConnection.getConnection();
	         CallableStatement stmt = connection.prepareCall("{call ObtenerEstadisticasJuego(?)}")) {
	        
	        stmt.setInt(1, juegoId);
	        ResultSet rs = stmt.executeQuery();
	        
	        String equipoLocalInfo = "";
	        String fechaInfo = "";
	        String juegoInfo = "";
	        
	        while (rs.next()) {
	            String contenido = rs.getString(1);
	            
	            // Extraer información para los labels pero NO agregarla a la tabla
	            if (contenido != null) {
	                // Filtrar filas que no queremos mostrar en la tabla
	                if (contenido.startsWith("ESTADÍSTICAS DEL J") ||
	                    contenido.startsWith("JUEGO:") ||
	                    contenido.contains("FECHA:")) {
	                    
	                    // Extraer información para los labels
	                    if (contenido.startsWith("JUEGO:")) {
	                        juegoInfo = contenido.split("FECHA:")[0].trim();
	                        if (contenido.contains("FECHA:")) {
	                            fechaInfo = "FECHA: " + contenido.split("FECHA:")[1].trim();
	                        }
	                    }
	                    // NO agregar estas filas a la tabla
	                    continue;
	                }
	            }
	            
	            // Solo agregar filas que SÍ queremos mostrar (jugadores, headers de equipos, totales)
	            Object[] row = new Object[8];
	            row[0] = rs.getString(1); // Primera columna (Jugador/Header)
	            row[1] = rs.getString(2); // Puntos
	            row[2] = rs.getString(3); // Asistencias
	            row[3] = rs.getString(4); // Rebotes
	            row[4] = rs.getString(5); // Bolas Robadas
	            row[5] = rs.getString(6); // Faltas
	            row[6] = rs.getString(7); // Técnicas
	            row[7] = rs.getString(8); // Bolas Perdidas
	            
	            model.addRow(row);
	        }
	        
	        // Actualizar los labels con la información extraída
	        if (!juegoInfo.isEmpty()) {
	            lblJuego.setText(juegoInfo);
	        } else {
	            lblJuego.setText("JUEGO: " + String.format("%05d", juegoId));
	        }
	        
	        if (!fechaInfo.isEmpty()) {
	            lblFecha.setText(fechaInfo);
	        }
	        
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(this,
	            "Error al cargar estadísticas: " + e.getMessage(),
	            "Error", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	    }
	}

	// Método para determinar el ganador
	private void determinarGanador(String equipoLocal, String equipoVisitante) {
	    try (Connection connection = SQLConnection.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(
	             "SELECT " +
	             "    eq.Nombre_Equipo, " +
	             "    ISNULL(SUM(CASE WHEN e.Descripcion = 'Puntos' THEN ej.Valor ELSE 0 END), 0) AS TotalPuntos " +
	             "FROM Jugador j " +
	             "JOIN Equipo eq ON j.IdEquipo = eq.IdEquipo " +
	             "LEFT JOIN [Estadistica de Juego] ej ON j.IdJugador = ej.IdJugador AND ej.IdJuego = ? " +
	             "LEFT JOIN Estadistica e ON ej.IdEstadistica = e.IdEstadistica " +
	             "WHERE eq.Nombre_Equipo IN (?, ?) " +
	             "GROUP BY eq.Nombre_Equipo")) {
	        
	        stmt.setInt(1, juegoId);
	        stmt.setString(2, equipoLocal);
	        stmt.setString(3, equipoVisitante);
	        
	        ResultSet rs = stmt.executeQuery();
	        
	        int puntosLocal = 0, puntosVisitante = 0;
	        
	        while (rs.next()) {
	            String equipo = rs.getString("Nombre_Equipo");
	            int puntos = rs.getInt("TotalPuntos");
	            
	            if (equipo.equals(equipoLocal)) {
	                puntosLocal = puntos;
	            } else if (equipo.equals(equipoVisitante)) {
	                puntosVisitante = puntos;
	            }
	        }
	        
	        // Actualizar los labels de equipos con información del ganador
	        if (puntosLocal > puntosVisitante) {
	            teamAlabel.setText(equipoLocal + " (Ganador)");
	            teamBlabel.setText(equipoVisitante);
	        } else if (puntosVisitante > puntosLocal) {
	            teamAlabel.setText(equipoLocal);
	            teamBlabel.setText(equipoVisitante + " (Ganador)");
	        } else {
	            teamAlabel.setText(equipoLocal + " (Empate)");
	            teamBlabel.setText(equipoVisitante + " (Empate)");
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	// Agregar este método para cargar información básica del juego
	private void cargarInformacionJuego() {
	    try (Connection connection = SQLConnection.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(
	             "SELECT " +
	             "    CONVERT(VARCHAR(10), j.Fecha_Hora, 103) AS Fecha, " +
	             "    el.Nombre_Equipo AS EquipoLocal, " +
	             "    ev.Nombre_Equipo AS EquipoVisitante " +
	             "FROM Juego j " +
	             "JOIN Equipo el ON j.Id_EquipoA_Local = el.IdEquipo " +
	             "JOIN Equipo ev ON j.Id_EquipoB_Visitante = ev.IdEquipo " +
	             "WHERE j.IdJuego = ?")) {
	        
	        stmt.setInt(1, juegoId);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            lblJuego.setText("JUEGO: " + String.format("%05d", juegoId));
	            lblFecha.setText("FECHA: " + rs.getString("Fecha"));
	            
	            // Determinar ganador basado en puntos del stored procedure
	            determinarGanadorDesdeTabla(rs.getString("EquipoLocal"), rs.getString("EquipoVisitante"));
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	// Método para determinar ganador desde los datos ya cargados en la tabla
	private void determinarGanadorDesdeTabla(String equipoLocal, String equipoVisitante) {
	    int puntosLocal = 0, puntosVisitante = 0;
	    boolean dentroEquipoLocal = false;
	    boolean dentroEquipoVisitante = false;
	    
	    // Hacer trim a los nombres de equipos
	    equipoLocal = equipoLocal != null ? equipoLocal.trim() : "";
	    equipoVisitante = equipoVisitante != null ? equipoVisitante.trim() : "";
	    
	    for (int i = 0; i < model.getRowCount(); i++) {
	        String jugador = (String) model.getValueAt(i, 0);
	        
	        if (jugador != null) {
	            if (jugador.contains("Equipo Local:")) {
	                dentroEquipoLocal = true;
	                dentroEquipoVisitante = false;
	            } else if (jugador.contains("Equipo Visitante:")) {
	                dentroEquipoLocal = false;
	                dentroEquipoVisitante = true;
	            } else if (jugador.startsWith("Total:")) {
	                String puntos = (String) model.getValueAt(i, 1);
	                if (puntos != null && !puntos.isEmpty()) {
	                    try {
	                        if (dentroEquipoLocal) {
	                            puntosLocal = Integer.parseInt(puntos);
	                        } else if (dentroEquipoVisitante) {
	                            puntosVisitante = Integer.parseInt(puntos);
	                        }
	                    } catch (NumberFormatException e) {
	                        // Ignorar errores de conversión
	                    }
	                }
	            }
	        }
	    }
	    
	    // Actualizar los labels de los equipos con el ganador
	    if (puntosLocal > puntosVisitante) {
	        teamAlabel.setText(equipoLocal + " (Ganador)");
	        teamBlabel.setText(equipoVisitante);
	    } else if (puntosVisitante > puntosLocal) {
	        teamAlabel.setText(equipoLocal);
	        teamBlabel.setText(equipoVisitante + " (Ganador)");
	    } else {
	        teamAlabel.setText(equipoLocal + " (Empate)");
	        teamBlabel.setText(equipoVisitante + " (Empate)");
	    }
	}
	
	// Configurar editores de celda con spinners
    private void configurarEditores() {
        for (int col = 1; col < table.getColumnCount(); col++) {
            table.getColumnModel().getColumn(col).setCellEditor(new SpinnerEditor());
        }
        table.setEnabled(true);
    }

    // Remover editores de celda
    private void removerEditores() {
        for (int col = 1; col < table.getColumnCount(); col++) {
            table.getColumnModel().getColumn(col).setCellEditor(null);
        }
    }

    // Verificar si una fila es editable (no es header de equipo ni total)
    private boolean isRowEditable(int row) {
        if (row < 0 || row >= table.getRowCount()) return false;
        
        String jugador = (String) table.getValueAt(row, 0);
        if (jugador == null || jugador.trim().isEmpty()) return false;
        
        // No editar headers de equipos, totales o encabezados
        boolean esEditable = !jugador.startsWith("Total:") && 
                            !jugador.contains("Equipo Local:") && 
                            !jugador.contains("Equipo Visitante:") &&
                            !jugador.equals("Jugador");
        
        System.out.println("Fila " + row + " - " + jugador + " - Editable: " + esEditable);
        return esEditable;
    }

    // Extraer el nombre del jugador del formato "13   – Bam A."
    private String extraerNombreJugador(String textoCompleto) {
        if (textoCompleto == null) return null;
        
        // Eliminar números y guiones al inicio
        String nombre = textoCompleto.replaceAll("^\\d+\\s*–\\s*", "").trim();
        
        // Debug
        System.out.println("Extracción: '" + textoCompleto + "' -> '" + nombre + "'");
        
        return nombre;
    }

    // Guardar estadísticas en la base de datos
    private void guardarEstadisticas() {
        System.out.println("Iniciando guardado de estadísticas...");
        Connection connection = null;
        
        try {
            connection = SQLConnection.getConnection();
            connection.setAutoCommit(false);
            
            int filasEditadas = 0;
            for (int row = 0; row < table.getRowCount(); row++) {
                if (isRowEditable(row)) {
                    String textoCompleto = (String) table.getValueAt(row, 0);
                    String nombreJugador = extraerNombreJugador(textoCompleto);
                    
                    System.out.println("Procesando fila " + row + ": " + nombreJugador);
                    
                    int jugadorId = obtenerIdJugador(nombreJugador, connection);
                    if (jugadorId > 0) {
                        actualizarEstadisticasJugador(jugadorId, row, connection);
                        filasEditadas++;
                    } else {
                        System.out.println("NO se encontró ID para: " + nombreJugador);
                    }
                }
            }
            
            connection.commit();
            JOptionPane.showMessageDialog(this, 
                "Estadísticas guardadas correctamente (" + filasEditadas + " jugadores)", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException ex) {}
            
            JOptionPane.showMessageDialog(this, 
                "Error al guardar: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {}
            
            // Recargar datos
            cargarEstadisticasJuego();
            cargarInformacionJuego();
        }
    }

    // Obtener ID del jugador por nombre
    private int obtenerIdJugador(String nombreJugador, Connection connection) throws SQLException {
        System.out.println("Buscando ID para: '" + nombreJugador + "'");
        
        String sql = "SELECT IdJugador FROM Jugador WHERE Nombre = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombreJugador);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("IdJugador");
                System.out.println("Encontrado ID: " + id);
                return id;
            } else {
                System.out.println("NO encontrado en BD");
                return 0;
            }
        }
    }

    // Actualizar estadísticas de un jugador
    private void actualizarEstadisticasJugador(int jugadorId, int row, Connection connection) throws SQLException {
        String[] estadisticas = {"Puntos", "Asistencias", "Rebotes", "Bolas Robadas", "Faltas", "Técnicas", "Bolas Perdidas"};
        
        for (int col = 1; col < 8; col++) {
            String valor = table.getValueAt(row, col).toString();
            int valorInt = Integer.parseInt(valor);
            
            // Obtener ID de la estadística
            int estadisticaId = obtenerIdEstadistica(estadisticas[col - 1], connection);
            
            if (estadisticaId > 0) {
                // Actualizar o insertar estadística
                String updateSql = "UPDATE [Estadistica de Juego] SET Valor = ? " +
                                 "WHERE IdJugador = ? AND IdJuego = ? AND IdEstadistica = ?";
                
                try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, valorInt);
                    updateStmt.setInt(2, jugadorId);
                    updateStmt.setInt(3, juegoId);
                    updateStmt.setInt(4, estadisticaId);
                    
                    int rowsUpdated = updateStmt.executeUpdate();
                    
                    // Si no se actualizó, insertar nuevo registro
                    if (rowsUpdated == 0) {
                        String insertSql = "INSERT INTO [Estadistica de Juego] (IdJugador, IdJuego, IdEstadistica, Valor) " +
                                         "VALUES (?, ?, ?, ?)";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                            insertStmt.setInt(1, jugadorId);
                            insertStmt.setInt(2, juegoId);
                            insertStmt.setInt(3, estadisticaId);
                            insertStmt.setInt(4, valorInt);
                            insertStmt.executeUpdate();
                        }
                    }
                }
            }
        }
    }

    // Obtener ID de la estadística por descripción
    private int obtenerIdEstadistica(String descripcion, Connection connection) throws SQLException {
        String sql = "SELECT IdEstadistica FROM Estadistica WHERE Descripcion = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, descripcion);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("IdEstadistica") : 0;
        }
    }

    // Clase para editor de spinner
    private class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
        private JSpinner spinner;

        public SpinnerEditor() {
            spinner = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, 
                                                   boolean isSelected, int row, int column) {
            // Solo permitir edición en filas editables
            if (!isRowEditable(row)) {
                return null;
            }
            
            try {
                int val = value != null ? Integer.parseInt(value.toString()) : 0;
                spinner.setValue(val);
            } catch (NumberFormatException e) {
                spinner.setValue(0);
            }
            
            return spinner;
        }

        @Override
        public Object getCellEditorValue() {
            return spinner.getValue().toString();
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            if (!modoEdicion) return false;
            
            if (e instanceof MouseEvent) {
                MouseEvent me = (MouseEvent) e;
                int row = table.rowAtPoint(me.getPoint());
                return isRowEditable(row);
            }
            return false;
        }
    }
}