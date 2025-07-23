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
import java.util.HashMap;
import java.util.Map;

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
	private Map<Integer, Integer> filaToJugadorId = new HashMap<>();
	
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
		
		// Configurar header de la tabla ANTES de crear el modelo - MODIFICADO
		String[] header = {"Jugador", "Puntos de 2", "Puntos de 3", "Puntos Totales", "Asistencias", "Rebotes", "Bolas Robadas", "Faltas", "Técnicas", "Bolas Perdidas"};
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
		cargarEstadisticasJuegoConIds();
	}
	
	// Método principal para cargar estadísticas con IDs
    private void cargarEstadisticasJuegoConIds() {
        model.setRowCount(0);
        filaToJugadorId.clear();
        
        try (Connection connection = SQLConnection.getConnection()) {
            // Cargar información básica del juego
            cargarInformacionBasica(connection);
            
            // Obtener equipos del juego
            String[] equipos = obtenerEquiposDelJuego(connection);
            
            if (equipos[0] != null && equipos[1] != null) {
                // Agregar equipo local
                agregarEquipoATabla(connection, equipos[0], "EQUIPO LOCAL: " + equipos[0], true);
                
                // Agregar equipo visitante  
                agregarEquipoATabla(connection, equipos[1], "EQUIPO VISITANTE: " + equipos[1], false);
                
                // Determinar ganador
                determinarGanadorDesdeTabla(equipos[0], equipos[1]);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar estadísticas: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // Método para cargar información básica del juego
    private void cargarInformacionBasica(Connection connection) throws SQLException {
        String sql = "SELECT " +
                    "    CONVERT(VARCHAR(10), j.Fecha_Hora, 103) AS Fecha, " +
                    "    el.Nombre_Equipo AS EquipoLocal, " +
                    "    ev.Nombre_Equipo AS EquipoVisitante " +
                    "FROM Juego j " +
                    "JOIN Equipo el ON j.Id_EquipoA_Local = el.IdEquipo " +
                    "JOIN Equipo ev ON j.Id_EquipoB_Visitante = ev.IdEquipo " +
                    "WHERE j.IdJuego = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, juegoId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                lblJuego.setText("JUEGO: " + String.format("%05d", juegoId));
                lblFecha.setText("FECHA: " + rs.getString("Fecha"));
            }
        }
    }
    
    // Método para obtener equipos del juego
    private String[] obtenerEquiposDelJuego(Connection connection) throws SQLException {
        String sql = "SELECT el.Nombre_Equipo AS EquipoLocal, ev.Nombre_Equipo AS EquipoVisitante " +
                    "FROM Juego j " +
                    "JOIN Equipo el ON j.Id_EquipoA_Local = el.IdEquipo " +
                    "JOIN Equipo ev ON j.Id_EquipoB_Visitante = ev.IdEquipo " +
                    "WHERE j.IdJuego = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, juegoId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new String[]{rs.getString("EquipoLocal"), rs.getString("EquipoVisitante")};
            }
        }
        return new String[]{null, null};
    }
    
    // Método para obtener ID de estadística - MEJORADO con debug
    private int obtenerIdEstadistica(String descripcion, Connection connection) throws SQLException {
        String sql = "SELECT IdEstadistica FROM Estadistica WHERE Descripcion = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, descripcion);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("IdEstadistica");
                System.out.println("Encontrado: '" + descripcion + "' -> ID: " + id);
                return id;
            } else {
                System.out.println("NO encontrado: '" + descripcion + "'");
                // Mostrar qué estadísticas existen realmente
                mostrarEstadisticasDisponibles(connection);
                return 0;
            }
        }
    }
    
    // Método para debug - mostrar todas las estadísticas disponibles
    private void mostrarEstadisticasDisponibles(Connection connection) throws SQLException {
        String sql = "SELECT IdEstadistica, Descripcion FROM Estadistica ORDER BY IdEstadistica";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            System.out.println("=== ESTADÍSTICAS DISPONIBLES EN BD ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("IdEstadistica") + " - Descripción: '" + rs.getString("Descripcion") + "'");
            }
            System.out.println("========================================");
        }
    }

    // Método simplificado para guardar estadísticas
    private void guardarEstadisticas() {
        System.out.println("Iniciando guardado de estadísticas...");
        Connection connection = null;
        
        try {
            connection = SQLConnection.getConnection();
            connection.setAutoCommit(false);
            
            int filasEditadas = 0;
            for (int row = 0; row < table.getRowCount(); row++) {
                if (isRowEditable(row)) {
                    Integer jugadorId = filaToJugadorId.get(row);
                    
                    if (jugadorId != null) {
                        System.out.println("Procesando fila " + row + " - Jugador ID: " + jugadorId);
                        actualizarEstadisticasJugador(jugadorId, row, connection);
                        filasEditadas++;
                    } else {
                        System.out.println("NO se encontró ID para fila: " + row);
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
            cargarEstadisticasJuegoConIds();
        }
    }

    // Configurar editores de celda con spinners
    private void configurarEditores() {
        for (int col = 1; col < table.getColumnCount(); col++) {
            // No configurar editor para la columna de Puntos Totales (columna 3)
            if (col != 3) {
                table.getColumnModel().getColumn(col).setCellEditor(new SpinnerEditor());
            }
        }
        table.setEnabled(true);
    }

    // Remover editores de celda
    private void removerEditores() {
        for (int col = 1; col < table.getColumnCount(); col++) {
            table.getColumnModel().getColumn(col).setCellEditor(null);
        }
    }

    // Verificar si una fila es editable
    private boolean isRowEditable(int row) {
        if (row < 0 || row >= table.getRowCount()) return false;
        
        String jugador = (String) table.getValueAt(row, 0);
        if (jugador == null || jugador.trim().isEmpty()) return false;
        
        // No editar headers de equipos, totales o encabezados
        boolean esEditable = !jugador.startsWith("TOTAL ") && 
                            !jugador.contains("EQUIPO LOCAL:") && 
                            !jugador.contains("EQUIPO VISITANTE:") &&
                            !jugador.equals("Jugador");
        
        return esEditable;
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
            if (!isRowEditable(row) || column == 3) {
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
                int column = table.columnAtPoint(me.getPoint());
                
                if (column == 3) return false;
                return isRowEditable(row);
            }
            return false;
        }
    }

    // Método para actualizar puntos totales después de editar
    private void actualizarPuntosTotales(int row) {
        if (isRowEditable(row)) {
            try {
                int puntos2 = Integer.parseInt(table.getValueAt(row, 1).toString());
                int puntos3 = Integer.parseInt(table.getValueAt(row, 2).toString());
                int puntosTotal = (puntos2 * 2) + (puntos3 * 3);
                
                table.setValueAt(puntosTotal, row, 3);
            } catch (NumberFormatException e) {
                table.setValueAt(0, row, 3);
            }
        }
    }

    // Agregar listener para actualizar puntos automáticamente
    private void configurarListenerPuntos() {
        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            
            if ((column == 1 || column == 2) && isRowEditable(row)) {
                actualizarPuntosTotales(row);
            }
        });
    }

    private void agregarEquipoATabla(Connection connection, String nombreEquipo, String headerEquipo, boolean esLocal) throws SQLException {
        // Agregar header del equipo
        Object[] headerRow = new Object[10];
        headerRow[0] = headerEquipo;
        for (int i = 1; i < 10; i++) headerRow[i] = "";
        model.addRow(headerRow);
        
        String sql = "SELECT " +
                    "j.IdJugador, " +
                    "CASE " +
                    "    WHEN LEN(j.Nombre) > 10 THEN " +
                    "        CAST(j.Numero_Jugador AS VARCHAR) + '   – ' + LEFT(j.Nombre, CHARINDEX(' ', j.Nombre + ' ') - 1) + ' ' + LEFT(SUBSTRING(j.Nombre, CHARINDEX(' ', j.Nombre) + 1, LEN(j.Nombre)), 1) + '.' " +
                    "    ELSE " +
                    "        CAST(j.Numero_Jugador AS VARCHAR) + '   – ' + j.Nombre " +
                    "END AS NombreFormateado, " +
                    "ISNULL(ep2.Puntos2, 0) AS Puntos2, " +
                    "ISNULL(ep3.Puntos3, 0) AS Puntos3, " +
                    "ISNULL(ea.Asistencias, 0) AS Asistencias, " +
                    "ISNULL(er.Rebotes, 0) AS Rebotes, " +
                    "ISNULL(ebr.BolasRobadas, 0) AS BolasRobadas, " +
                    "ISNULL(ef.Faltas, 0) AS Faltas, " +
                    "ISNULL(et.Tecnicas, 0) AS Tecnicas, " +
                    "ISNULL(ebp.BolasPerdidas, 0) AS BolasPerdidas " +
                    "FROM Jugador j " +
                    "JOIN Equipo e ON j.IdEquipo = e.IdEquipo " +
                    "LEFT JOIN (SELECT IdJugador, Cantidad_Estadistica AS Puntos2 FROM [Estadistica de Juego] WHERE IdJuego = ? AND IdEstadistica = (SELECT IdEstadistica FROM Estadistica WHERE Descripcion = 'Punto de dos')) ep2 ON j.IdJugador = ep2.IdJugador " +
                    "LEFT JOIN (SELECT IdJugador, Cantidad_Estadistica AS Puntos3 FROM [Estadistica de Juego] WHERE IdJuego = ? AND IdEstadistica = (SELECT IdEstadistica FROM Estadistica WHERE Descripcion = 'Punto de tres')) ep3 ON j.IdJugador = ep3.IdJugador " +
                    "LEFT JOIN (SELECT IdJugador, Cantidad_Estadistica AS Asistencias FROM [Estadistica de Juego] WHERE IdJuego = ? AND IdEstadistica = (SELECT IdEstadistica FROM Estadistica WHERE Descripcion = 'Asistencia')) ea ON j.IdJugador = ea.IdJugador " +
                    "LEFT JOIN (SELECT IdJugador, Cantidad_Estadistica AS Rebotes FROM [Estadistica de Juego] WHERE IdJuego = ? AND IdEstadistica = (SELECT IdEstadistica FROM Estadistica WHERE Descripcion = 'Rebotes')) er ON j.IdJugador = er.IdJugador " +
                    "LEFT JOIN (SELECT IdJugador, Cantidad_Estadistica AS BolasRobadas FROM [Estadistica de Juego] WHERE IdJuego = ? AND IdEstadistica = (SELECT IdEstadistica FROM Estadistica WHERE Descripcion = 'Bolas Robadas')) ebr ON j.IdJugador = ebr.IdJugador " +
                    "LEFT JOIN (SELECT IdJugador, Cantidad_Estadistica AS Faltas FROM [Estadistica de Juego] WHERE IdJuego = ? AND IdEstadistica = (SELECT IdEstadistica FROM Estadistica WHERE Descripcion = 'Faltas personales')) ef ON j.IdJugador = ef.IdJugador " +
                    "LEFT JOIN (SELECT IdJugador, Cantidad_Estadistica AS Tecnicas FROM [Estadistica de Juego] WHERE IdJuego = ? AND IdEstadistica = (SELECT IdEstadistica FROM Estadistica WHERE Descripcion = 'Faltas tecnicas')) et ON j.IdJugador = et.IdJugador " +
                    "LEFT JOIN (SELECT IdJugador, Cantidad_Estadistica AS BolasPerdidas FROM [Estadistica de Juego] WHERE IdJuego = ? AND IdEstadistica = (SELECT IdEstadistica FROM Estadistica WHERE Descripcion = 'Bolas perdidas')) ebp ON j.IdJugador = ebp.IdJugador " +
                    "WHERE e.Nombre_Equipo = ? " +
                    "ORDER BY j.Numero_Jugador";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 1; i <= 8; i++) {
                stmt.setInt(i, juegoId);
            }
            stmt.setString(9, nombreEquipo);
            
            ResultSet rs = stmt.executeQuery();
            
            int totalPuntos2 = 0, totalPuntos3 = 0, totalAsistencias = 0, totalRebotes = 0, totalBolasRobadas = 0;
            int totalFaltas = 0, totalTecnicas = 0, totalBolasPerdidas = 0;
            
            while (rs.next()) {
                int jugadorId = rs.getInt("IdJugador");
                int puntos2 = rs.getInt("Puntos2");
                int puntos3 = rs.getInt("Puntos3");
                int puntosTotal = (puntos2 * 2) + (puntos3 * 3);
                
                Object[] row = new Object[10];
                row[0] = rs.getString("NombreFormateado");
                row[1] = puntos2;
                row[2] = puntos3;
                row[3] = puntosTotal;
                row[4] = rs.getInt("Asistencias");
                row[5] = rs.getInt("Rebotes");
                row[6] = rs.getInt("BolasRobadas");
                row[7] = rs.getInt("Faltas");
                row[8] = rs.getInt("Tecnicas");
                row[9] = rs.getInt("BolasPerdidas");
                
                filaToJugadorId.put(model.getRowCount(), jugadorId);
                model.addRow(row);
                
                totalPuntos2 += puntos2;
                totalPuntos3 += puntos3;
                totalAsistencias += rs.getInt("Asistencias");
                totalRebotes += rs.getInt("Rebotes");
                totalBolasRobadas += rs.getInt("BolasRobadas");
                totalFaltas += rs.getInt("Faltas");
                totalTecnicas += rs.getInt("Tecnicas");
                totalBolasPerdidas += rs.getInt("BolasPerdidas");
            }
            
            Object[] totalRow = new Object[10];
            totalRow[0] = "TOTAL " + nombreEquipo;
            totalRow[1] = totalPuntos2;
            totalRow[2] = totalPuntos3;
            totalRow[3] = (totalPuntos2 * 2) + (totalPuntos3 * 3);
            totalRow[4] = totalAsistencias;
            totalRow[5] = totalRebotes;
            totalRow[6] = totalBolasRobadas;
            totalRow[7] = totalFaltas;
            totalRow[8] = totalTecnicas;
            totalRow[9] = totalBolasPerdidas;
            model.addRow(totalRow);
        }
        
        configurarListenerPuntos();
    }

    // Actualizar estadísticas de un jugador - CORREGIDO con mapeo por descripción
    private void actualizarEstadisticasJugador(int jugadorId, int row, Connection connection) throws SQLException {
        // Mapeo de columnas a descripciones exactas de tu tabla Estadistica
        String[] estadisticasDescripciones = {
            "Punto de dos",     // columna 1
            "Punto de tres",    // columna 2  
            "Asistencia",       // columna 4
            "Rebotes",          // columna 5
            "Bolas Robadas",    // columna 6
            "Faltas personales",// columna 7
            "Faltas tecnicas",  // columna 8
            "Bolas perdidas"    // columna 9
        };
        
        for (int col = 1; col < 10; col++) {
            if (col == 3) continue; // Saltar columna de Puntos Totales
            
            try {
                String valor = table.getValueAt(row, col).toString();
                int valorInt = Integer.parseInt(valor);
                
                int estadisticaIndex = col > 3 ? col - 2 : col - 1;
                
                if (estadisticaIndex < estadisticasDescripciones.length) {
                    String descripcionEstadistica = estadisticasDescripciones[estadisticaIndex];
                    
                    // Obtener el ID real de la estadística por descripción
                    int estadisticaId = obtenerIdEstadisticaPorDescripcion(descripcionEstadistica, connection);
                    
                    if (estadisticaId > 0) {
                        System.out.println("Columna " + col + " -> Estadística: " + descripcionEstadistica + " (ID: " + estadisticaId + ") = " + valorInt);
                        actualizarOInsertarEstadistica(jugadorId, estadisticaId, valorInt, connection);
                    } else {
                        System.out.println("No se encontró ID para estadística: " + descripcionEstadistica);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error procesando columna " + col + " para jugador " + jugadorId + ": " + e.getMessage());
            }
        }
    }
    
    // Método mejorado para obtener ID por descripción exacta - CORREGIDO para formato con ceros
    private int obtenerIdEstadisticaPorDescripcion(String descripcion, Connection connection) throws SQLException {
        String sql = "SELECT IdEstadistica FROM Estadistica WHERE Descripcion = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, descripcion);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String idString = rs.getString("IdEstadistica").trim(); // AGREGAR .trim() aquí
                System.out.println("Encontrado: '" + descripcion + "' -> ID: " + idString);
                
                // Convertir a int para uso interno
                try {
                    return Integer.parseInt(idString);
                } catch (NumberFormatException e) {
                    System.out.println("Error al convertir ID a número: " + idString);
                    return 0;
                }
            } else {
                System.out.println("NO encontrado: '" + descripcion + "'");
                return 0;
            }
        }
    }

    // Método para actualizar o insertar estadística - MODIFICADO para usar String en ambos IDs
    private void actualizarOInsertarEstadistica(int jugadorId, int estadisticaId, int valor, Connection connection) throws SQLException {
        // Formatear ambos IDs con cero a la izquierda
        String jugadorIdFormatted = String.format("%02d", jugadorId);
        String estadisticaIdFormatted = String.format("%02d", estadisticaId);
        
        String updateSql = "UPDATE [Estadistica de Juego] SET Cantidad_Estadistica = ? " +
                          "WHERE IdJugador = ? AND IdJuego = ? AND IdEstadistica = ?";
        
        try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
            updateStmt.setInt(1, valor);
            updateStmt.setString(2, jugadorIdFormatted); // Usar String formateado para IdJugador
            updateStmt.setInt(3, juegoId);
            updateStmt.setString(4, estadisticaIdFormatted); // Usar String formateado para IdEstadistica
            
            int rowsUpdated = updateStmt.executeUpdate();
            
            if (rowsUpdated == 0) {
                String insertSql = "INSERT INTO [Estadistica de Juego] (IdJugador, IdJuego, IdEstadistica, Cantidad_Estadistica) " +
                                 "VALUES (?, ?, ?, ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                    insertStmt.setString(1, jugadorIdFormatted); // Usar String formateado para IdJugador
                    insertStmt.setInt(2, juegoId);
                    insertStmt.setString(3, estadisticaIdFormatted); // Usar String formateado para IdEstadistica
                    insertStmt.setInt(4, valor);
                    insertStmt.executeUpdate();
                    System.out.println("Insertado: Jugador " + jugadorIdFormatted + ", Estadística " + estadisticaIdFormatted + " = " + valor);
                }
            } else {
                System.out.println("Actualizado: Jugador " + jugadorIdFormatted + ", Estadística " + estadisticaIdFormatted + " = " + valor);
            }
        }
    }

    // Método para determinar ganador desde los datos ya cargados en la tabla - MODIFICADO
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
                if (jugador.contains("EQUIPO LOCAL:")) {
                    dentroEquipoLocal = true;
                    dentroEquipoVisitante = false;
                } else if (jugador.contains("EQUIPO VISITANTE:")) {
                    dentroEquipoLocal = false;
                    dentroEquipoVisitante = true;
                } else if (jugador.startsWith("TOTAL ")) {
                    String puntos = (String) model.getValueAt(i, 3).toString(); // Usar columna 3 para puntos totales
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
}