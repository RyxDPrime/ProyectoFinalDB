package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import logico.RoundedBorder;
import server.SQLConnection;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListJuego extends JDialog {

	
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
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JScrollPane scrollPane;
	private JTable table;
	private static Object row[];
	private DefaultTableModel model;
	private String equipoA = "";
	private String equipoB = "";
	private Date fecha;
	private JPanel panel;
	private JComboBox teamAcb;
	private JComboBox teamBcb;
	private JButton returnbtn;
	private JButton deletebtn;
	private JButton updatebtn;
	private JButton detailbtn;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListJuego dialog = new ListJuego();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListJuego() {
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
		table = new JTable();table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int ind = table.getSelectedRow();
				if (ind >= 0) {	
					equipoA = (String) table.getValueAt(ind, 0);
					equipoB = (String) table.getValueAt(ind, 1);
					updatebtn.setEnabled(true);
					deletebtn.setEnabled(true);
					detailbtn.setEnabled(true);
				}
			}
		});
		table.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String[] header = {"Equipo Local", "Equipo Visitante", "Descripcion", "Fecha y Hora"};
		model.setColumnIdentifiers(header);
		table.setModel(model);
		
		table.getTableHeader().setPreferredSize(new Dimension(10, 40));
		table.getTableHeader().setBackground(PrimaryC);
		table.getTableHeader().setForeground(Color.WHITE);
		
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
		
		teamAcb = new JComboBox();
		teamAcb.setModel(new DefaultComboBoxModel(new String[] {"<Equipo Local>"}));
		teamAcb.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		teamAcb.setBounds(24, 16, 163, 31);
		panel.add(teamAcb);
		
		teamBcb = new JComboBox();
		teamBcb.setModel(new DefaultComboBoxModel(new String[] {"<Equipo Visitante>"}));
		teamBcb.setBounds(24, 54, 163, 31);
		teamBcb.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		panel.add(teamBcb);
		
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
		
		deletebtn = new JButton("Eliminar");
		deletebtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
			 }
		});
		deletebtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(deletebtn.isEnabled()) {
					deletebtn.setBackground(new Color(167, 34, 34));
					deletebtn.setForeground(Color.white);
					deletebtn.setBorder(new RoundedBorder(new Color(167, 34, 34), 1, 20));
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				deletebtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
				deletebtn.setBackground(new Color(248, 248, 248));
				deletebtn.setForeground(SecondaryC);
			}
		});
		deletebtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
		deletebtn.setForeground(SecondaryC);
		deletebtn.setBackground(new Color(248, 248, 248));
		deletebtn.setOpaque(true);
		deletebtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		deletebtn.setEnabled(false);
		deletebtn.setBounds(22, 604, 169, 40);
		panel.add(deletebtn);
		
		updatebtn = new JButton("Actualizar");
		updatebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		updatebtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(updatebtn.isEnabled()) {
					updatebtn.setBorder(new RoundedBorder(new Color(255, 183, 77), 1, 20));
					updatebtn.setBackground(new Color(255, 183, 77));
					updatebtn.setForeground(Color.white);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				updatebtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
				updatebtn.setForeground(SecondaryC);
				updatebtn.setBackground(new Color(248, 248, 248));			}
		});
		updatebtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
		updatebtn.setForeground(SecondaryC);
		updatebtn.setBackground(new Color(248, 248, 248));
		updatebtn.setOpaque(true);
		updatebtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		updatebtn.setEnabled(false);
		updatebtn.setBounds(22, 556, 169, 40);
		panel.add(updatebtn);
		
		detailbtn = new JButton("Detalles");
		detailbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(detailbtn.isEnabled()) {
					detailbtn.setBorder(new RoundedBorder(new Color(66, 165, 245), 1, 20));
					detailbtn.setBackground(new Color(66, 165, 245)); // #42A5F5
					detailbtn.setForeground(Color.BLACK);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				detailbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
				detailbtn.setForeground(SecondaryC);
				detailbtn.setBackground(new Color(248, 248, 248));
			}
		});
		detailbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		detailbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
		detailbtn.setForeground(SecondaryC);
		detailbtn.setBackground(new Color(248, 248, 248));
		detailbtn.setOpaque(true);
		detailbtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		detailbtn.setEnabled(false);
		detailbtn.setBounds(22, 509, 169, 40);
		panel.add(detailbtn);
		
		loadEquipos();
		loadJuegos();
	}
	
	private void loadEquipos() {
	    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
	    model.addElement("<Equipo Local>");
	    
	    DefaultComboBoxModel<String> modelVisitante = new DefaultComboBoxModel<String>();
	    modelVisitante.addElement("<Equipo Visitante>");
	    
	    try (Connection connection = SQLConnection.getConnection();
	         Statement stmt = connection.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT Nombre_Equipo FROM Equipo ORDER BY Nombre_Equipo")) {
	        
	        while (rs.next()) {
	            String nombreEquipo = rs.getString("Nombre_Equipo");
	            model.addElement(nombreEquipo);
	            modelVisitante.addElement(nombreEquipo);
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(this, 
	            "Error al cargar equipos: " + e.getMessage(),
	            "Error", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	    }
	    
	    teamAcb.setModel(model);
	    teamBcb.setModel(modelVisitante);
	    
	    // Agregar listeners para filtrar
	    teamAcb.addActionListener(e -> filtrarJuegos());
	    teamBcb.addActionListener(e -> filtrarJuegos());
	}
	
	private void filtrarJuegos() {
	    String equipoLocal = (String) teamAcb.getSelectedItem();
	    String equipoVisitante = (String) teamBcb.getSelectedItem();
	    
	    // Si ambos combobox tienen la selección por defecto, mostrar todos los juegos
	    if (equipoLocal.equals("<Equipo Local>") && equipoVisitante.equals("<Equipo Visitante>")) {
	        loadJuegos();
	        return;
	    }
	    
	    StringBuilder sql = new StringBuilder(
	        "SELECT e1.Nombre_Equipo AS Local, e2.Nombre_Equipo AS Visitante, " +
	        "j.Descripcion, j.Fecha_Hora " +
	        "FROM Juego j " +
	        "JOIN Equipo e1 ON j.Id_EquipoA_Local = e1.IdEquipo " +
	        "JOIN Equipo e2 ON j.Id_EquipoB_Visitante = e2.IdEquipo ");
	    
	    // Construir condiciones WHERE según las selecciones
	    if (!equipoLocal.equals("<Equipo Local>") && !equipoVisitante.equals("<Equipo Visitante>")) {
	        sql.append("WHERE e1.Nombre_Equipo = ? AND e2.Nombre_Equipo = ?");
	    } else if (!equipoLocal.equals("<Equipo Local>")) {
	        sql.append("WHERE e1.Nombre_Equipo = ?");
	    } else if (!equipoVisitante.equals("<Equipo Visitante>")) {
	        sql.append("WHERE e2.Nombre_Equipo = ?");
	    }
	    
	    sql.append(" ORDER BY j.Fecha_Hora DESC");
	    
	    try (Connection connection = SQLConnection.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
	        
	        // Establecer parámetros según las selecciones
	        int paramIndex = 1;
	        if (!equipoLocal.equals("<Equipo Local>") && !equipoVisitante.equals("<Equipo Visitante>")) {
	            stmt.setString(paramIndex++, equipoLocal);
	            stmt.setString(paramIndex++, equipoVisitante);
	        } else if (!equipoLocal.equals("<Equipo Local>")) {
	            stmt.setString(paramIndex++, equipoLocal);
	        } else if (!equipoVisitante.equals("<Equipo Visitante>")) {
	            stmt.setString(paramIndex++, equipoVisitante);
	        }
	        
	        ResultSet rs = stmt.executeQuery();
	        model.setRowCount(0); // Limpiar tabla
	        
	        while (rs.next()) {
	            Object[] row = {
	                rs.getString("Local"),
	                rs.getString("Visitante"),
	                rs.getString("Descripcion"),
	                rs.getTimestamp("Fecha_Hora")
	            };
	            model.addRow(row);
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(this, 
	            "Error al filtrar juegos: " + e.getMessage(),
	            "Error", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	    }
	}
	
	private void loadJuegos() {
	    model.setRowCount(0); // Limpiar tabla
	    
	    try (Connection connection = SQLConnection.getConnection();
	         Statement stmt = connection.createStatement();
	         ResultSet rs = stmt.executeQuery(
	             "SELECT e1.Nombre_Equipo AS Local, e2.Nombre_Equipo AS Visitante, " +
	             "j.Descripcion, j.Fecha_Hora AS Fecha_Hora " +
	             "FROM Juego j " +
	             "JOIN Equipo e1 ON j.Id_EquipoA_Local = e1.IdEquipo " +
	             "JOIN Equipo e2 ON j.Id_EquipoB_Visitante = e2.IdEquipo " +
	             "ORDER BY j.Fecha_Hora DESC")) {
	        
	        while (rs.next()) {
	            Object[] row = {
	                rs.getString("Local"),
	                rs.getString("Visitante"),
	                rs.getString("Descripcion"),
	                rs.getTimestamp("Fecha_Hora")
	            };
	            model.addRow(row);
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(this, 
	            "Error al cargar juegos: " + e.getMessage(),
	            "Error", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	    }
	}
}
