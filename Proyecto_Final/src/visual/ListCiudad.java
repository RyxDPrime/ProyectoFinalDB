package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import logico.Ciudad;
import logico.Controladora;
import logico.RoundedBorder;
import server.SQLConnection;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTextField;

public class ListCiudad extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static Object row[];
	private DefaultTableModel model;
	private String cod = "";
	private String nameCiudad;
	
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
	private JTable table;
	private JTextField nametxt;
	private JButton returnbtn;
	private JButton searchbtn;
	private JButton deletebtn;
	private JButton updatebtn;
	private JButton addbtn;

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListCiudad dialog = new ListCiudad();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListCiudad() {
		setUndecorated(true);
		setBounds(100, 100, 1349, 704);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1116, 704);
		contentPanel.add(scrollPane);
		model = new DefaultTableModel();
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int ind = table.getSelectedRow();
				if(ind >= 0) { 
					nameCiudad = (String) table.getValueAt(ind, 0);
					deletebtn.setEnabled(true);
					updatebtn.setEnabled(true);
				}
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String header[] = {"Nombre"};
		model.setColumnIdentifiers(header);
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		table.setFont(new Font("century gothic", Font.PLAIN, 16));
		table.getTableHeader().setFont(new Font("century gothic", Font.BOLD, 18));
		table.setRowHeight(30);
		table.setBorder(new RoundedBorder(Color.white, 1, 20));

		table.getTableHeader().setPreferredSize(new Dimension(10, 40));
		table.getTableHeader().setBackground(PrimaryC);
		table.getTableHeader().setForeground(Color.white);
		
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
		
		JPanel panel = new JPanel();
		panel.setBackground(PrimaryC);
		panel.setBounds(1118, 0, 213, 704);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		nametxt = new JTextField();
		nametxt.setBorder(new CompoundBorder(new RoundedBorder(PrimaryC, 1, 20), new EmptyBorder(0, 10, 0, 10)));
		nametxt.setBounds(22, 23, 169, 40);
		nametxt.setFont(new Font("century Gothic", Font.PLAIN, 18));
		panel.add(nametxt);
		nametxt.setColumns(10);
		
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
			        String nombre = nametxt.getText().trim();
			        
			        try (Connection connection = SQLConnection.getConnection();
			             PreparedStatement stmt = connection.prepareStatement(
			                 "SELECT Nombre_Ciudad FROM Ciudad WHERE Nombre_Ciudad LIKE ? ORDER BY Nombre_Ciudad")) {
			            
			            stmt.setString(1, "%" + nombre + "%");
			            ResultSet rs = stmt.executeQuery();
			            
			            model.setRowCount(0); // Limpiar tabla
			            
			            while (rs.next()) {
			                Object[] row = {
			                    rs.getString("Nombre_Ciudad")
			                };
			                model.addRow(row);
			            }
			        } catch (SQLException ex) {
			            JOptionPane.showMessageDialog(ListCiudad.this, 
			                "Error al buscar ciudades: " + ex.getMessage(),
			                "Error", JOptionPane.ERROR_MESSAGE);
			            ex.printStackTrace();
			        }
			    }
		});
		searchbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
		searchbtn.setBackground(Color.white);
		searchbtn.setForeground(SecondaryC);
		searchbtn.setOpaque(true);
		searchbtn.setBounds(22, 73, 169, 40);
		searchbtn.setFont(new Font("century gothic", Font.BOLD, 18));
		panel.add(searchbtn);
		
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
		
		deletebtn = new JButton("Eliminar");
		deletebtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
			        if (nameCiudad != null && !nameCiudad.isEmpty()) {
			            int confirm = JOptionPane.showConfirmDialog(
			                ListCiudad.this, 
			                "¿Está seguro de eliminar la ciudad '" + nameCiudad + "'?",
			                "Confirmar Eliminación",
			                JOptionPane.YES_NO_OPTION);
			            
			            if (confirm == JOptionPane.YES_OPTION) {
			                try (Connection connection = SQLConnection.getConnection();
			                     PreparedStatement stmt = connection.prepareStatement(
			                         "DELETE FROM Ciudad WHERE Nombre_Ciudad = ?")) {
			                    
			                    stmt.setString(1, nameCiudad);
			                    int rowsAffected = stmt.executeUpdate();
			                    
			                    if (rowsAffected > 0) {
			                        JOptionPane.showMessageDialog(
			                            ListCiudad.this, 
			                            "Ciudad eliminada correctamente",
			                            "Éxito",
			                            JOptionPane.INFORMATION_MESSAGE);
			                        loadCiudad(); // Actualizar la tabla
			                        deletebtn.setEnabled(false);
			                        updatebtn.setEnabled(false);
			                    }
			                } catch (SQLException ex) {
			                    JOptionPane.showMessageDialog(
			                        ListCiudad.this, 
			                        "Error al eliminar ciudad: " + ex.getMessage(),
			                        "Error", 
			                        JOptionPane.ERROR_MESSAGE);
			                    ex.printStackTrace();
			                }
			            }
			        }
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
		deletebtn.setBackground(new Color(248, 248, 248));
		deletebtn.setForeground(SecondaryC);
		deletebtn.setFont(new Font("century gothic", Font.BOLD, 18));
		deletebtn.setEnabled(false);
		deletebtn.setBounds(22, 599, 169, 40);
		panel.add(deletebtn);
		
		updatebtn = new JButton("Actualizar");
		updatebtn.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (nameCiudad != null && !nameCiudad.isEmpty()) {
		            String nuevoNombre = JOptionPane.showInputDialog(
		                ListCiudad.this, 
		                "Ingrese el nuevo nombre para la ciudad:",
		                "Actualizar Ciudad",
		                JOptionPane.PLAIN_MESSAGE);
		            
		            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
		                try (Connection connection = SQLConnection.getConnection();
		                     PreparedStatement stmt = connection.prepareStatement(
		                         "UPDATE Ciudad SET Nombre_Ciudad = ? WHERE Nombre_Ciudad = ?")) {
		                    
		                    stmt.setString(1, nuevoNombre.trim());
		                    stmt.setString(2, nameCiudad);
		                    int rowsAffected = stmt.executeUpdate();
		                    
		                    if (rowsAffected > 0) {
		                        JOptionPane.showMessageDialog(
		                            ListCiudad.this, 
		                            "Ciudad actualizada correctamente",
		                            "Éxito",
		                            JOptionPane.INFORMATION_MESSAGE);
		                        loadCiudad(); // Actualizar la tabla
		                        updatebtn.setEnabled(false);
		                        deletebtn.setEnabled(false);
		                    }
		                } catch (SQLException ex) {
		                    JOptionPane.showMessageDialog(
		                        ListCiudad.this, 
		                        "Error al actualizar ciudad: " + ex.getMessage(),
		                        "Error", 
		                        JOptionPane.ERROR_MESSAGE);
		                    ex.printStackTrace();
		                }
		            }
		        }
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
		updatebtn.setBackground(new Color(248, 248, 248));
		updatebtn.setForeground(SecondaryC);
		updatebtn.setOpaque(true);
		updatebtn.setFont(new Font("century gothic", Font.BOLD, 18));
		updatebtn.setEnabled(false);
		updatebtn.setBounds(22, 547, 169, 40);
		panel.add(updatebtn);
		
		addbtn = new JButton("Agregar");
		addbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				addbtn.setBackground(new Color(11, 182, 72));
				addbtn.setForeground(Color.white);
				addbtn.setBorder(new RoundedBorder(new Color(11, 182, 72), 1, 20));
			}
			@Override
			public void mouseExited(MouseEvent e) {
			addbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
			addbtn.setBackground(new Color(248, 248, 248));
			addbtn.setForeground(SecondaryC);
			}
		});
		addbtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
			        String nombre = JOptionPane.showInputDialog(
			            ListCiudad.this, 
			            "Ingrese el nombre de la nueva ciudad:",
			            "Agregar Ciudad",
			            JOptionPane.PLAIN_MESSAGE);
			        
			        if (nombre != null && !nombre.trim().isEmpty()) {
			            try (Connection connection = SQLConnection.getConnection();
			                 PreparedStatement stmt = connection.prepareStatement(
			                     "INSERT INTO Ciudad (Nombre_Ciudad) VALUES (?)")) {
			                
			                stmt.setString(1, nombre.trim());
			                int rowsAffected = stmt.executeUpdate();
			                
			                if (rowsAffected > 0) {
			                    JOptionPane.showMessageDialog(
			                        ListCiudad.this, 
			                        "Ciudad agregada correctamente",
			                        "Éxito",
			                        JOptionPane.INFORMATION_MESSAGE);
			                    loadCiudad(); // Actualizar la tabla
			                }
			            } catch (SQLException ex) {
			                JOptionPane.showMessageDialog(
			                    ListCiudad.this, 
			                    "Error al agregar ciudad: " + ex.getMessage(),
			                    "Error", 
			                    JOptionPane.ERROR_MESSAGE);
			                ex.printStackTrace();
			            }
			        }
			    }
		});
		addbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
		addbtn.setBackground(new Color(248, 248, 248));
		addbtn.setForeground(SecondaryC);
		addbtn.setOpaque(true);
		addbtn.setFont(new Font("century gothic", Font.BOLD, 18));
		addbtn.setBounds(22, 494, 169, 40);
		panel.add(addbtn);
		
		loadCiudad();
	}
	
	public void loadCiudad() {
	    model.setRowCount(0); // Limpiar la tabla
	    
	    try (Connection connection = SQLConnection.getConnection();
	         Statement stmt = connection.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT Nombre_Ciudad FROM Ciudad ORDER BY Nombre_Ciudad")) {
	        
	        while (rs.next()) {
	            Object[] row = {
	                rs.getString("Nombre_Ciudad")
	            };
	            model.addRow(row);
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(this, 
	            "Error al cargar ciudades: " + e.getMessage(),
	            "Error", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	    }
	    
	    updatebtn.setEnabled(false);
	    deletebtn.setEnabled(false);
	}
}
