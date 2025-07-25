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
	private static final Color AccentColor = new Color(247, 109, 71);
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

	private boolean isAddingNewRow = false;
	private boolean isUpdatingRow = false;
	private int updatingRowIndex = -1;

	public static void main(String[] args) {
		try {
			ListCiudad dialog = new ListCiudad();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
				if (ind >= 0) {
					String nombreSeleccionado = (String) table.getValueAt(ind, 0);
					nameCiudad = nombreSeleccionado != null ? nombreSeleccionado.trim() : "";

					try {
						Controladora controladora = Controladora.getInstance();
						for (Ciudad ciudad : controladora.getMisCiudades()) {
							String nombreCiudad = ciudad.getNombre() != null ? ciudad.getNombre().trim() : "";
							if (nombreCiudad.equals(nameCiudad)) {
								cod = String.valueOf(ciudad.getCodCiudad());
								break;
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					deletebtn.setEnabled(true);
					updatebtn.setEnabled(true);
				}
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String header[] = { "Nombre" };
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
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
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
				searchbtn.setBorder(new RoundedBorder(new Color(66, 133, 244), 1, 20));
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

				if (nombre.isEmpty()) {
					loadCiudad();
					return;
				}

				try {
					Controladora controladora = Controladora.getInstance();
					controladora.cargarCiudadesFromDB();

					model.setRowCount(0);

					for (Ciudad ciudad : controladora.getMisCiudades()) {
						String nombreCiudad = ciudad.getNombre() != null ? ciudad.getNombre().trim() : "";
						if (nombreCiudad.toLowerCase().contains(nombre.toLowerCase())) {
							Object[] row = { nombreCiudad
							};
							model.addRow(row);
						}
					}

					nametxt.setText("");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(ListCiudad.this, "Error al buscar ciudades: " + ex.getMessage(),
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
				if (table.getCellEditor() != null) {
					table.getCellEditor().cancelCellEditing();
				}

				if (isAddingNewRow) {
					cancelAddMode();
				} else if (isUpdatingRow) {
					cancelUpdateMode();
				} else {
					dispose();
				}
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
				if (cod != null && !cod.isEmpty()) {
					try {
						if (ciudadTieneEquiposRelacionados(Integer.parseInt(cod))) {
							java.util.List<String> equiposRelacionados = obtenerEquiposRelacionados(
									Integer.parseInt(cod));

							StringBuilder mensaje = new StringBuilder();
							mensaje.append("No se puede eliminar la ciudad '").append(nameCiudad)
									.append("' porque está relacionada con los siguientes equipos:\n\n");

							for (String equipo : equiposRelacionados) {
								mensaje.append("• ").append(equipo).append("\n");
							}

							mensaje.append("\nPara eliminar esta ciudad, primero debe:")
									.append("\n1. Cambiar la ciudad de los equipos relacionados")
									.append("\n2. O eliminar los equipos que dependen de esta ciudad");

							JOptionPane.showMessageDialog(ListCiudad.this, mensaje.toString(),
									"No se puede eliminar - Ciudad en uso", JOptionPane.WARNING_MESSAGE);
							return;
						}

						int confirm = JOptionPane.showConfirmDialog(ListCiudad.this,
								"¿Está seguro de eliminar la ciudad '" + nameCiudad + "'?\n\n"
										+ "Esta acción no se puede deshacer.",
								"Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

						if (confirm == JOptionPane.YES_OPTION) {
							try {
								Controladora controladora = Controladora.getInstance();
								controladora.deleteCiudad(Integer.parseInt(cod));

								JOptionPane.showMessageDialog(ListCiudad.this, "Ciudad eliminada correctamente",
										"Éxito", JOptionPane.INFORMATION_MESSAGE);

								loadCiudad();
								deletebtn.setEnabled(false);
								updatebtn.setEnabled(false);

							} catch (Exception ex) {
								JOptionPane.showMessageDialog(ListCiudad.this,
										"Error al eliminar ciudad: " + ex.getMessage(), "Error",
										JOptionPane.ERROR_MESSAGE);
								ex.printStackTrace();
							}
						}

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(ListCiudad.this,
								"Error al verificar relaciones de la ciudad: " + ex.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
				}
			}
		});
		deletebtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (deletebtn.isEnabled()) {
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
				if (!isUpdatingRow) {
					if (cod != null && !cod.isEmpty()) {
						try {
							int selectedRow = table.getSelectedRow();
							if (selectedRow < 0) {
								JOptionPane.showMessageDialog(ListCiudad.this,
										"Debe seleccionar una ciudad para actualizar", "Error",
										JOptionPane.ERROR_MESSAGE);
								return;
							}

							makeTableEditableForUpdate();
							table.editCellAt(selectedRow, 0);

							updatebtn.setText("Guardar Cambios");
							returnbtn.setText("Cancelar");
							isUpdatingRow = true;
							updatingRowIndex = selectedRow;

							addbtn.setEnabled(false);
							deletebtn.setEnabled(false);
							searchbtn.setEnabled(false);

						} catch (Exception ex) {
							JOptionPane.showMessageDialog(ListCiudad.this,
									"Error al iniciar actualización: " + ex.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
							ex.printStackTrace();
						}
					}
				} else {
					try {
						if (table.getCellEditor() != null) {
							table.getCellEditor().stopCellEditing();
						}

						String nuevoNombreRaw = (String) model.getValueAt(updatingRowIndex, 0);
						String nuevoNombre = nuevoNombreRaw != null ? nuevoNombreRaw.trim() : "";

						if (nuevoNombre.isEmpty()) {
							JOptionPane.showMessageDialog(ListCiudad.this,
									"El nombre de la ciudad no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						Controladora controladora = Controladora.getInstance();
						Ciudad ciudadActualizada = new Ciudad(Integer.parseInt(cod), nuevoNombre);

						int index = -1;
						for (int i = 0; i < controladora.getMisCiudades().size(); i++) {
							if (controladora.getMisCiudades().get(i).getCodCiudad() == Integer.parseInt(cod)) {
								index = i;
								break;
							}
						}

						if (index != -1) {
							controladora.updateCiudad(ciudadActualizada, index);

							JOptionPane.showMessageDialog(ListCiudad.this, "Ciudad actualizada correctamente", "Éxito",
									JOptionPane.INFORMATION_MESSAGE);

							resetUpdateMode();
							loadCiudad();
						} else {
							JOptionPane.showMessageDialog(ListCiudad.this, "Error: No se pudo encontrar la ciudad",
									"Error", JOptionPane.ERROR_MESSAGE);
						}

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(ListCiudad.this, "Error al guardar cambios: " + ex.getMessage(),
								"Error", JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
						resetUpdateMode();
						loadCiudad();
					}
				}
			}
		});
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
				if (!isAddingNewRow) {
					Object[] newRow = { "Nueva Ciudad" };
					model.addRow(newRow);

					makeTableEditable();

					int newRowIndex = model.getRowCount() - 1;
					table.setRowSelectionInterval(newRowIndex, newRowIndex);
					table.editCellAt(newRowIndex, 0);

					addbtn.setText("Guardar");
					returnbtn.setText("Cancelar");
					isAddingNewRow = true;
					updatebtn.setEnabled(false);
					deletebtn.setEnabled(false);
					searchbtn.setEnabled(false);

				} else {
					try {
						if (table.getCellEditor() != null) {
							table.getCellEditor().stopCellEditing();
						}

						int rowIndex = model.getRowCount() - 1;
						String nombreCiudadRaw = (String) model.getValueAt(rowIndex, 0);
						String nombreCiudad = nombreCiudadRaw != null ? nombreCiudadRaw.trim() : "";

						if (nombreCiudad.isEmpty()) {
							JOptionPane.showMessageDialog(ListCiudad.this,
									"El nombre de la ciudad no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						Controladora controladora = Controladora.getInstance();
						Ciudad nuevaCiudad = new Ciudad(0, nombreCiudad);
						controladora.insertarCiudad(nuevaCiudad);

						JOptionPane.showMessageDialog(ListCiudad.this, "Ciudad agregada correctamente", "Éxito",
								JOptionPane.INFORMATION_MESSAGE);

						resetAddMode();
						loadCiudad();

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(ListCiudad.this, "Error al guardar ciudad: " + ex.getMessage(),
								"Error", JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
						resetAddMode();
						loadCiudad();
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
		if (table.getCellEditor() != null) {
			table.getCellEditor().cancelCellEditing();
		}

		table.setDefaultEditor(Object.class, null);
		if (table.getColumnCount() > 0) {
			table.getColumnModel().getColumn(0).setCellEditor(null);
		}

		model.setRowCount(0);

		try {
			Controladora controladora = Controladora.getInstance();
			controladora.cargarCiudadesFromDB();

			for (Ciudad ciudad : controladora.getMisCiudades()) {
				String nombreCiudad = ciudad.getNombre() != null ? ciudad.getNombre().trim() : "";

				Object[] row = { nombreCiudad
				};
				model.addRow(row);
			}

			if (controladora.getMisCiudades().isEmpty()) {
				JOptionPane.showMessageDialog(this, "No se encontraron ciudades en la base de datos.", "Información",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al cargar ciudades: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		updatebtn.setEnabled(false);
		deletebtn.setEnabled(false);
	}

	private void makeTableEditable() {
		try {
			if (table.getCellEditor() != null) {
				table.getCellEditor().cancelCellEditing();
			}

			table.getColumnModel().getColumn(0)
					.setCellEditor(new javax.swing.DefaultCellEditor(new javax.swing.JTextField()));

			table.setDefaultEditor(Object.class, new javax.swing.DefaultCellEditor(new javax.swing.JTextField()) {
				@Override
				public boolean isCellEditable(java.util.EventObject e) {
					return isAddingNewRow;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void makeTableEditableForUpdate() {
		try {
			if (table.getCellEditor() != null) {
				table.getCellEditor().cancelCellEditing();
			}

			table.getColumnModel().getColumn(0)
					.setCellEditor(new javax.swing.DefaultCellEditor(new javax.swing.JTextField()));

			table.setDefaultEditor(Object.class, new javax.swing.DefaultCellEditor(new javax.swing.JTextField()) {
				@Override
				public boolean isCellEditable(java.util.EventObject e) {
					if (!isUpdatingRow)
						return false;
					int row = table.getSelectedRow();
					return row == updatingRowIndex;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void resetAddMode() {
		addbtn.setText("Agregar");
		returnbtn.setText("Retornar");
		isAddingNewRow = false;

		if (table.getCellEditor() != null) {
			table.getCellEditor().cancelCellEditing();
		}

		table.setDefaultEditor(Object.class, null);
		table.getColumnModel().getColumn(0).setCellEditor(null);

		table.clearSelection();

		searchbtn.setEnabled(true);
		updatebtn.setEnabled(false);
		deletebtn.setEnabled(false);
	}

	private void cancelAddMode() {
		if (table.getCellEditor() != null) {
			table.getCellEditor().cancelCellEditing();
		}

		int lastRow = model.getRowCount() - 1;
		if (lastRow >= 0 && isAddingNewRow) {
			model.removeRow(lastRow);
		}
		resetAddMode();
	}

	private void resetUpdateMode() {
		updatebtn.setText("Actualizar");
		returnbtn.setText("Retornar");
		isUpdatingRow = false;
		updatingRowIndex = -1;

		if (table.getCellEditor() != null) {
			table.getCellEditor().cancelCellEditing();
		}

		table.setDefaultEditor(Object.class, null);
		table.getColumnModel().getColumn(0).setCellEditor(null);

		table.clearSelection();

		addbtn.setEnabled(true);
		searchbtn.setEnabled(true);
		updatebtn.setEnabled(false);
		deletebtn.setEnabled(false);
	}

	private void cancelUpdateMode() {
		if (table.getCellEditor() != null) {
			table.getCellEditor().cancelCellEditing();
		}

		resetUpdateMode();
		loadCiudad();
	}

	private boolean ciudadTieneEquiposRelacionados(int idCiudad) throws SQLException {
		String idCiudadFormatted = String.format("%02d", idCiudad);

		try (Connection connection = SQLConnection.getConnection();
				PreparedStatement stmt = connection
						.prepareStatement("SELECT COUNT(*) FROM Equipo WHERE IdCiudad = ?")) {

			stmt.setString(1, idCiudadFormatted);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		}
		return false;
	}

	private java.util.List<String> obtenerEquiposRelacionados(int idCiudad) throws SQLException {
		java.util.List<String> equipos = new ArrayList<>();

		String idCiudadFormatted = String.format("%02d", idCiudad);

		try (Connection connection = SQLConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(
						"SELECT Nombre_Equipo FROM Equipo WHERE IdCiudad = ? ORDER BY Nombre_Equipo")) {

			stmt.setString(1, idCiudadFormatted);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				equipos.add(rs.getString("Nombre_Equipo"));
			}
		}
		return equipos;
	}
}