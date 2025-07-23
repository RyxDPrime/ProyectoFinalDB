package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import logico.Ciudad;
import logico.Controladora;
import logico.Equipo;
import logico.Jugador;
import logico.RoundedBorder;
import server.SQLConnection;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.HttpRetryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListJugadores extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static Object row[];
	private DefaultTableModel model;
	private int NumJugador;
	
	private static final Color PrimaryC = new Color(3, 88, 157);
	private static final Color SecondaryC = new Color(3, 104, 196);
	private static final Color ThirdC = new Color(247, 251, 255);
	private static final Color AccentColor = new Color(247, 109, 71); //255, 150, 95
	private static final Color AccentHoverColor = new Color(255, 136, 73);
	private static final Color BGC = new Color(236, 240, 241);
	private static final Color TextColor = new Color(52, 73, 94);
	private static final Color WTextColor = new Color(255, 255, 255);
	private static final Color ButtonColor = new Color(42, 145, 230);
	private static final Color ButtonBorderColor = new Color(42, 145, 230);
	private static final Color HoverEffectColor = new Color(3, 109, 195);
	private JScrollPane scrollPane;
	private JPanel panel;
	private JTable table;
	private JTextField numtxt;
	private JButton searchbtn;
	private JComboBox teamcombo;
	private JButton updateBtn;
	private JButton deleteBtn;
	private JButton returnbtn;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListJugadores dialog = new ListJugadores();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListJugadores() {
		setUndecorated(true);
		setBounds(100, 100, 1349, 704);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1116, 704);
		contentPanel.add(scrollPane);

		model = new DefaultTableModel();
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int ind = table.getSelectedRow();
				if (ind >= 0) {
					NumJugador = (int) table.getValueAt(ind, 0);
					updateBtn.setEnabled(true);
					deleteBtn.setEnabled(true);
					
				}
			}
		});
		table.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String[] header = {"Numero", "Nombre Completo", "Ciudad", "Equipo", "Fecha de Nacimiento"};
		model.setColumnIdentifiers(header);
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		table.getTableHeader().setPreferredSize(new Dimension(10, 40));
		table.getTableHeader().setBackground(PrimaryC);
		table.getTableHeader().setForeground(Color.WHITE);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		table.getColumnModel().getColumn(1).setPreferredWidth(75);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(25);
		
		table.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		table.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 18));
		table.setRowHeight(30);
		table.setBorder(new RoundedBorder(Color.WHITE, 1, 20));
		
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        if (!isSelected) {
		            c.setBackground(row % 2 == 0 ? new Color(240, 240, 240) : Color.WHITE);
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
		
		numtxt = new JTextField();
		numtxt.setBorder(new CompoundBorder(new RoundedBorder(PrimaryC, 1, 15), new EmptyBorder(0, 10, 0, 10)));
		numtxt.setBounds(22, 58, 169, 40);
		panel.add(numtxt);
		numtxt.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		numtxt.setColumns(10);
		
		searchbtn = new JButton("Buscar");
		searchbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				searchbtn.setBorder(new RoundedBorder(new Color(66, 133, 244) , 1, 20));
				searchbtn.setBackground(new Color(66, 133, 244));
				searchbtn.setForeground(Color.white);
				searchbtn.setOpaque(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				searchbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
				searchbtn.setBackground(Color.white);
				searchbtn.setForeground(SecondaryC);
				searchbtn.setOpaque(true);
			}
		});
		searchbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Logica para la busqueda
			}
		});
		searchbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
		searchbtn.setForeground(SecondaryC);
		searchbtn.setBackground(new Color(248, 248, 248));
		searchbtn.setOpaque(true);
		searchbtn.setBounds(22, 109, 169, 40);
		searchbtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		panel.add(searchbtn);
		
		teamcombo = new JComboBox();
		teamcombo.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		teamcombo.setModel(new DefaultComboBoxModel(new String[] {"<Equipos>", "Todos"}));
		teamcombo.setBounds(24, 16, 163, 31);
		panel.add(teamcombo);
		
		returnbtn = new JButton("Retornar");
		returnbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
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
		returnbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
		returnbtn.setForeground(SecondaryC);
		returnbtn.setBackground(new Color(248, 248, 248));
		returnbtn.setOpaque(true);
		returnbtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		returnbtn.setBounds(22, 652, 169, 40);
		panel.add(returnbtn);
		
		deleteBtn = new JButton("Eliminar");
		deleteBtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
			        if(NumJugador != 0) {
			            // Mostrar información del jugador antes de eliminar
			            int selectedRow = table.getSelectedRow();
			            if (selectedRow >= 0) {
			                String nombreJugador = (String) table.getValueAt(selectedRow, 1);
			                String equipoJugador = (String) table.getValueAt(selectedRow, 3);
			                
			                // NUEVA VALIDACIÓN: Verificar si tiene estadísticas registradas
			                try {
			                    int idJugador = obtenerIdJugadorPorNumero(NumJugador);
			                    if (idJugador == -1) {
			                        JOptionPane.showMessageDialog(null, 
			                            "No se pudo encontrar el jugador", 
			                            "Error", 
			                            JOptionPane.ERROR_MESSAGE);
			                        return;
			                    }
			                    
			                    // Verificar si tiene estadísticas en Estadistica_Juego
			                    if (jugadorTieneEstadisticas(idJugador)) {
			                        JOptionPane.showMessageDialog(null, 
			                            "No se puede eliminar el jugador porque tiene estadísticas registradas en juegos.\n\n" +
			                            "Jugador: " + nombreJugador + "\n" +
			                            "Equipo: " + equipoJugador + "\n" +
			                            "Número: " + NumJugador + "\n\n" +
			                            "Para eliminar este jugador, primero debe eliminar sus estadísticas de los juegos correspondientes.", 
			                            "No se puede eliminar", 
			                            JOptionPane.WARNING_MESSAGE);
			                        return;
			                    }
			                    
			                } catch (SQLException ex) {
			                    JOptionPane.showMessageDialog(null, 
			                        "Error al verificar estadísticas del jugador: " + ex.getMessage(), 
			                        "Error", 
			                        JOptionPane.ERROR_MESSAGE);
			                    ex.printStackTrace();
			                    return;
			                }
			                
			                int option = JOptionPane.showConfirmDialog(null, 
			                    "¿Está seguro de eliminar al jugador?\n\n" +
			                    "Nombre: " + nombreJugador + "\n" +
			                    "Equipo: " + equipoJugador + "\n" +
			                    "Número: " + NumJugador + "\n\n" +
			                    "Esta acción no se puede deshacer.", 
			                    "Confirmar Eliminación", 
			                    JOptionPane.YES_NO_OPTION,
			                    JOptionPane.WARNING_MESSAGE);
			                
			                if(option == JOptionPane.YES_OPTION) {
			                    try (Connection connection = SQLConnection.getConnection();
			                         PreparedStatement stmt = connection.prepareStatement(
			                             "DELETE FROM Jugador WHERE Numero_Jugador = ?")) {
			                        
			                        stmt.setInt(1, NumJugador);
			                        int rowsAffected = stmt.executeUpdate();
			                        
			                        if(rowsAffected > 0) {
			                            JOptionPane.showMessageDialog(null, 
			                                "Jugador eliminado correctamente", 
			                                "Éxito", 
			                                JOptionPane.INFORMATION_MESSAGE);
			                            
			                            // Actualizar tabla y resetear selección
			                            loadJugadores();
			                            updateBtn.setEnabled(false);
			                            deleteBtn.setEnabled(false);
			                            NumJugador = 0;
			                            
			                        } else {
			                            JOptionPane.showMessageDialog(null, 
			                                "No se encontró el jugador", 
			                                "Error", 
			                                JOptionPane.ERROR_MESSAGE);
			                        }
			                    } catch (SQLException ex) {
			                        JOptionPane.showMessageDialog(null, 
			                            "Error al eliminar jugador: " + ex.getMessage(), 
			                            "Error", 
			                            JOptionPane.ERROR_MESSAGE);
			                        ex.printStackTrace();
			                    }
			                }
			            }
			        } else {
			            JOptionPane.showMessageDialog(null, 
			                "Por favor seleccione un jugador para eliminar", 
			                "Seleccionar Jugador", 
			                JOptionPane.WARNING_MESSAGE);
			        }
			    }
		});
		deleteBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(deleteBtn.isEnabled()) {
					deleteBtn.setBackground(new Color(167, 34, 34));
					deleteBtn.setForeground(Color.white);
					deleteBtn.setBorder(new RoundedBorder(new Color(167, 34, 34), 1, 20));
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				deleteBtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
				deleteBtn.setBackground(new Color(248, 248, 248));
				deleteBtn.setForeground(SecondaryC);
			}
		});
		deleteBtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
		deleteBtn.setForeground(SecondaryC);
		deleteBtn.setBackground(new Color(248, 248, 248));
		deleteBtn.setOpaque(true);
		deleteBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		deleteBtn.setEnabled(false);
		deleteBtn.setBounds(22, 604, 169, 40);
		panel.add(deleteBtn);
		
		updateBtn = new JButton("Actualizar");
		updateBtn.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (NumJugador != 0) {
		            try (Connection connection = SQLConnection.getConnection();
		                 PreparedStatement stmt = connection.prepareStatement(
		                     "SELECT j.idJugador, j.Nombre, j.IdCiudad, j.Fecha_Nacimiento, " +
		                     "j.Numero_Jugador, j.IdEquipo, c.Nombre_Ciudad, e.Nombre_Equipo " +
		                     "FROM Jugador j " +
		                     "LEFT JOIN Ciudad c ON j.IdCiudad = c.IdCiudad " +
		                     "LEFT JOIN Equipo e ON j.IdEquipo = e.IdEquipo " +
		                     "WHERE j.Numero_Jugador = ?")) {
		        
		        stmt.setInt(1, NumJugador);
		        
		        try (ResultSet rs = stmt.executeQuery()) {
		            if (rs.next()) {
		                // Crear instancia de RegJugador en modo actualización
		                RegJugador regJugador = new RegJugador();
		                
		                // Configurar modo actualización
		                regJugador.configurarModoActualizacion(
		                    rs.getInt("idJugador"),
		                    rs.getString("Nombre"),
		                    rs.getString("Nombre_Ciudad"),
		                    rs.getDate("Fecha_Nacimiento"),
		                    rs.getInt("Numero_Jugador"),
		                    rs.getString("Nombre_Equipo")
		                );
		                
		                // Mostrar el diálogo
		                regJugador.setModal(true);
		                regJugador.setVisible(true);
		                
		                // Actualizar la tabla después de cerrar el diálogo
		                loadJugadores();
		                
		                // NO resetear la selección inmediatamente
		                // Mantener los botones habilitados para permitir ediciones consecutivas
		                // updateBtn.setEnabled(false);
		                // deleteBtn.setEnabled(false);
		                // NumJugador = 0;
		                
		            } else {
		                JOptionPane.showMessageDialog(null, 
		                    "No se encontraron los datos del jugador", 
		                    "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        }
		        
		    } catch (SQLException ex) {
		        JOptionPane.showMessageDialog(null, 
		            "Error al obtener datos del jugador: " + ex.getMessage(), 
		            "Error", JOptionPane.ERROR_MESSAGE);
		    }
		}
		    }
		});
		updateBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(updateBtn.isEnabled()) {
					updateBtn.setBorder(new RoundedBorder(new Color(255, 183, 77), 1, 20));
					updateBtn.setBackground(new Color(255, 183, 77));
					updateBtn.setForeground(Color.white);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				updateBtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
				updateBtn.setForeground(SecondaryC);
				updateBtn.setBackground(new Color(248, 248, 248));			}
		});
		updateBtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
		updateBtn.setForeground(SecondaryC);
		updateBtn.setBackground(new Color(248, 248, 248));
		updateBtn.setOpaque(true);
		updateBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		updateBtn.setEnabled(false);
		updateBtn.setBounds(22, 556, 169, 40);
		panel.add(updateBtn);
		
		loadEquiposComboBox();
		loadJugadores();
	}
	
	public void loadJugadores() {
	    model.setRowCount(0); // Limpiar tabla
	    
	    try (Connection connection = SQLConnection.getConnection();
	         Statement stmt = connection.createStatement();
	         ResultSet rs = stmt.executeQuery(
	             "SELECT j.Numero_Jugador, j.Nombre, c.Nombre_Ciudad as Ciudad, " +
	             "e.Nombre_Equipo as Equipo, j.Fecha_Nacimiento " +
	             "FROM Jugador j " +
	             "LEFT JOIN Ciudad c ON j.IdCiudad = c.IdCiudad " +
	             "LEFT JOIN Equipo e ON j.IdEquipo = e.IdEquipo")) {
	        
	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("Numero_Jugador"),
	                rs.getString("Nombre"),
	                rs.getString("Ciudad"),
	                rs.getString("Equipo"),
	                formatFecha(rs.getDate("Fecha_Nacimiento")) // Formatear la fecha aquí
	            };
	            model.addRow(row);
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(this, 
	            "Error al cargar jugadores: " + e.getMessage(),
	            "Error", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	    }
	}
	
	private void loadEquiposComboBox() {
	    teamcombo.removeAllItems();
	    teamcombo.addItem("<Todos>"); // Opción por defecto
	    
	    try (Connection connection = SQLConnection.getConnection();
	         Statement stmt = connection.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT DISTINCT Nombre_Equipo FROM Equipo ORDER BY Nombre_Equipo")) {
	        
	        while (rs.next()) {
	            teamcombo.addItem(rs.getString("Nombre_Equipo"));
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(this, 
	            "Error al cargar equipos: " + e.getMessage(),
	            "Error", JOptionPane.ERROR_MESSAGE);
	    }
	    
	    // Agregar el listener para filtrar automáticamente
	    teamcombo.addActionListener(e -> filtrarPorEquipo());
	}
	
	private void filtrarPorEquipo() {
	    String equipoSeleccionado = (String) teamcombo.getSelectedItem();
	    
	    if (equipoSeleccionado == null || equipoSeleccionado.equals("<Todos>")) {
	        loadJugadores(); // Esto ahora cargará todos los jugadores correctamente
	        return;
	    }
	    
	    try (Connection connection = SQLConnection.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(
	             "SELECT j.Numero_Jugador, j.Nombre, c.Nombre_Ciudad as Ciudad, " +
	             "e.Nombre_Equipo as Equipo, j.Fecha_Nacimiento " +
	             "FROM Jugador j " +
	             "LEFT JOIN Ciudad c ON j.IdCiudad = c.IdCiudad " +
	             "LEFT JOIN Equipo e ON j.IdEquipo = e.IdEquipo " +
	             "WHERE e.Nombre_Equipo = ?")) {
	        
	        stmt.setString(1, equipoSeleccionado);
	        ResultSet rs = stmt.executeQuery();
	        
	        model.setRowCount(0); // Limpiar tabla
	        
	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("Numero_Jugador"),
	                rs.getString("Nombre"),
	                rs.getString("Ciudad"),
	                rs.getString("Equipo"),
	                formatFecha(rs.getDate("Fecha_Nacimiento")) // Formatear la fecha aquí
	            };
	            model.addRow(row);
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(this, 
	            "Error al filtrar jugadores: " + e.getMessage(),
	            "Error", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Obtiene el ID del jugador basado en su número
	 */
	private int obtenerIdJugadorPorNumero(int numeroJugador) throws SQLException {
	    try (Connection connection = SQLConnection.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(
	             "SELECT idJugador FROM Jugador WHERE Numero_Jugador = ?")) {
	        
	        stmt.setInt(1, numeroJugador);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getInt("idJugador");
	        }
	    }
	    return -1; // No encontrado
	}

	/**
	 * Verifica si el jugador tiene estadísticas registradas en la tabla Estadistica_Juego
	 */
	private boolean jugadorTieneEstadisticas(int idJugador) throws SQLException {
	    try (Connection connection = SQLConnection.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(
	             "SELECT COUNT(*) FROM [Estadistica de Juego]\r\n"
	             + " WHERE IdJugador = ?")) {
	        
	        stmt.setInt(1, idJugador);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getInt(1) > 0; // Retorna true si tiene estadísticas
	        }
	    }
	    return false;
	}

	private String formatFecha(java.sql.Date fecha) {
	    if (fecha == null) {
	        return "";
	    }
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
	    return sdf.format(fecha);
	}
}
