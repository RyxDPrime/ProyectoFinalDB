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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import logico.Controladora;
import logico.Equipo;
import logico.Ciudad;
import logico.RoundedBorder;
import server.SQLConnection;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultCellEditor;
import javax.swing.table.TableCellEditor;
import java.util.Vector;

public class ListEquipo extends JDialog {

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

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static Object row[];
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private JTable table;
	private String equipo = "";
	private String equipoId = "";
	private JPanel panel;
	private JButton searchbtn;
	private JButton returnbtn;
	private JButton deletebtn;
	private JButton updatebtn;
	private JButton addbtn;
	private JTextField equpotxt;
	private boolean isAddingNewRow = false;
	private boolean isUpdatingRow = false;

	public static void main(String[] args) {
		try {
			ListEquipo dialog = new ListEquipo();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListEquipo() {
		setUndecorated(true);
		setBounds(100, 100, 1349, 704);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
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
						equipo = (String) table.getValueAt(ind, 0);

						try {
							Controladora controladora = Controladora.getInstance();
							for (Equipo eq : controladora.getMisEquipos()) {
								if (eq.getNombreString().equals(equipo)) {
									equipoId = String.valueOf(eq.getIdEquipo());
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
			table.setFont(new Font("Century Gothic", Font.PLAIN, 14));
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			String[] header = { "Equipo", "Ciudad" };
			model.setColumnIdentifiers(header);
			table.setModel(model);
			scrollPane.setViewportView(table);

			table.getTableHeader().setPreferredSize(new Dimension(10, 40));
			table.getTableHeader().setBackground(PrimaryC);
			table.getTableHeader().setForeground(Color.WHITE);

			table.setFont(new Font("Century Gothic", Font.PLAIN, 16));
			table.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 18));
			table.setRowHeight(30);
			table.setBorder(new RoundedBorder(Color.WHITE, 1, 20));

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

			scrollPane.setViewportView(table);
		}
		{
			panel = new JPanel();
			panel.setBackground(PrimaryC);
			panel.setBounds(1118, 0, 213, 704);
			contentPanel.add(panel);
			panel.setLayout(null);
			{
				equpotxt = new JTextField();
				equpotxt.setBorder(
						new CompoundBorder(new RoundedBorder(PrimaryC, 1, 15), new EmptyBorder(0, 10, 0, 10)));
				equpotxt.setBounds(22, 23, 169, 40);
				panel.add(equpotxt);
				equpotxt.setFont(new Font("Century Gothic", Font.PLAIN, 18));
				equpotxt.setColumns(10);
			}
			{
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
						String nombre = equpotxt.getText().trim();

						if (nombre.isEmpty()) {
							loadEquipos();
							return;
						}

						try {
							Controladora controladora = Controladora.getInstance();
							controladora.cargarEquiposFromDB();
							controladora.cargarCiudadesFromDB();

							model.setRowCount(0);

							for (Equipo equipoItem : controladora.getMisEquipos()) {
								if (equipoItem.getNombreString().toLowerCase().contains(nombre.toLowerCase())) {
									Ciudad ciudad = controladora.searchCiudadByCod(equipoItem.getIdCiudad());
									String nombreCiudad = ciudad != null ? ciudad.getNombre() : "N/A";

									Object[] row = { equipoItem.getNombreString(), nombreCiudad };
									model.addRow(row);
								}
							}

							if (model.getRowCount() == 0) {
								JOptionPane.showMessageDialog(ListEquipo.this,
										"No se encontraron equipos que coincidan con: " + nombre, "Sin resultados",
										JOptionPane.INFORMATION_MESSAGE);
							}

							equpotxt.setText("");
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(ListEquipo.this,
									"Error al buscar equipos: " + e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							e2.printStackTrace();
						}
					}
				});
				searchbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
				searchbtn.setForeground(SecondaryC);
				searchbtn.setBackground(new Color(248, 248, 248));
				searchbtn.setOpaque(true);
				searchbtn.setBounds(22, 73, 169, 40);
				searchbtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
				panel.add(searchbtn);
			}
			{
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
						if (isAddingNewRow) {
							cancelAddMode();
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
			}
			{
				deletebtn = new JButton("Eliminar");
				deletebtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (equipoId != null && !equipoId.isEmpty()) {
							int confirm = JOptionPane.showConfirmDialog(ListEquipo.this,
									"¿Está seguro de eliminar el equipo '" + equipo + "'?", "Confirmar Eliminación",
									JOptionPane.YES_NO_OPTION);

							if (confirm == JOptionPane.YES_OPTION) {
								try {
									Controladora controladora = Controladora.getInstance();
									controladora.deleteEquipo(Integer.parseInt(equipoId));

									JOptionPane.showMessageDialog(ListEquipo.this, "Equipo eliminado correctamente",
											"Éxito", JOptionPane.INFORMATION_MESSAGE);
									loadEquipos();
									deletebtn.setEnabled(false);
									updatebtn.setEnabled(false);
								} catch (Exception ex) {
									JOptionPane.showMessageDialog(ListEquipo.this,
											"Error al eliminar equipo: " + ex.getMessage(), "Error",
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
			}
			{
				updatebtn = new JButton("Actualizar");
				updatebtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (!isUpdatingRow) {
							int selectedRow = table.getSelectedRow();
							if (selectedRow != -1) {
								makeTableEditable();
								table.editCellAt(selectedRow, 0);
								updatebtn.setText("Guardar");
								returnbtn.setText("Cancelar");
								isUpdatingRow = true;

								addbtn.setEnabled(false);
								deletebtn.setEnabled(false);
								searchbtn.setEnabled(false);
							}
						} else {
							if (table.getCellEditor() != null) {
								table.getCellEditor().stopCellEditing();
							}

							int selectedRow = table.getSelectedRow();
							if (selectedRow != -1) {
								String nuevoNombre = (String) model.getValueAt(selectedRow, 0);
								String nombreCiudad = (String) model.getValueAt(selectedRow, 1);

								if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
									JOptionPane.showMessageDialog(ListEquipo.this,
											"El nombre del equipo no puede estar vacío", "Error",
											JOptionPane.ERROR_MESSAGE);
									return;
								}

								try {
									Controladora controladora = Controladora.getInstance();
									Ciudad ciudad = controladora.searchCiudadByNombre(nombreCiudad);
									if (ciudad == null) {
										JOptionPane.showMessageDialog(ListEquipo.this, "Ciudad no válida", "Error",
												JOptionPane.ERROR_MESSAGE);
										return;
									}

									Equipo actualizado = new Equipo(Integer.parseInt(equipoId), nuevoNombre.trim(),
											ciudad.getCodCiudad());
									Equipo viejo = controladora.searchEquipoByCod(Integer.parseInt(equipoId));
									int index = controladora.getMisEquipos().indexOf(viejo);
									controladora.updateEquipo(actualizado, index);

									JOptionPane.showMessageDialog(ListEquipo.this, "Equipo actualizado correctamente",
											"Éxito", JOptionPane.INFORMATION_MESSAGE);

									resetUpdateMode();
									loadEquipos();
								} catch (Exception ex) {
									JOptionPane.showMessageDialog(ListEquipo.this,
											"Error al actualizar equipo: " + ex.getMessage(), "Error",
											JOptionPane.ERROR_MESSAGE);
									ex.printStackTrace();
									resetUpdateMode();
									loadEquipos();
								}
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
			}
			{
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
							try {
								Controladora controladora = Controladora.getInstance();
								controladora.cargarCiudadesFromDB();

								if (controladora.getMisCiudades().isEmpty()) {
									JOptionPane.showMessageDialog(ListEquipo.this,
											"Debe crear al menos una ciudad antes de crear equipos", "Error",
											JOptionPane.ERROR_MESSAGE);
									return;
								}

								Object[] newRow = { "Nuevo Equipo", controladora.getMisCiudades().get(0).getNombre() };
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

							} catch (Exception ex) {
								JOptionPane.showMessageDialog(ListEquipo.this, "Error: " + ex.getMessage(), "Error",
										JOptionPane.ERROR_MESSAGE);
							}

						} else {
							try {
								if (table.getCellEditor() != null) {
									table.getCellEditor().stopCellEditing();
								}

								int rowIndex = model.getRowCount() - 1;
								String nombreEquipo = (String) model.getValueAt(rowIndex, 0);
								String nombreCiudad = (String) model.getValueAt(rowIndex, 1);

								if (nombreEquipo == null || nombreEquipo.trim().isEmpty()) {
									JOptionPane.showMessageDialog(ListEquipo.this,
											"El nombre del equipo no puede estar vacío", "Error",
											JOptionPane.ERROR_MESSAGE);
									return;
								}
								Controladora controladora = Controladora.getInstance();
								controladora.cargarCiudadesFromDB();

								Ciudad ciudadSeleccionada = null;
								for (Ciudad ciudad : controladora.getMisCiudades()) {
									if (ciudad.getNombre().equals(nombreCiudad)) {
										ciudadSeleccionada = ciudad;
										break;
									}
								}

								if (ciudadSeleccionada == null) {
									JOptionPane.showMessageDialog(ListEquipo.this,
											"Error: Ciudad '" + nombreCiudad + "' no encontrada en la base de datos.\n"
													+ "Ciudades disponibles: " + controladora.getMisCiudades().size(),
											"Error", JOptionPane.ERROR_MESSAGE);
									return;
								}

								boolean ciudadExisteEnBD = verificarCiudadEnBD(ciudadSeleccionada.getCodCiudad());
								if (!ciudadExisteEnBD) {
									JOptionPane.showMessageDialog(ListEquipo.this,
											"Error: La ciudad con ID " + ciudadSeleccionada.getCodCiudad()
													+ " no existe en la base de datos.",
											"Error de Integridad", JOptionPane.ERROR_MESSAGE);
									return;
								}

								for (Equipo equipoExistente : controladora.getMisEquipos()) {
									if (equipoExistente.getNombreString().equalsIgnoreCase(nombreEquipo.trim())) {
										JOptionPane.showMessageDialog(ListEquipo.this,
												"Ya existe un equipo con el nombre '" + nombreEquipo.trim() + "'",
												"Error", JOptionPane.ERROR_MESSAGE);
										return;
									}
								}
								Equipo nuevoEquipo = new Equipo(0, nombreEquipo.trim(),
										ciudadSeleccionada.getCodCiudad());
								controladora.insertarEquipo(nuevoEquipo);

								JOptionPane.showMessageDialog(ListEquipo.this, "Equipo agregado correctamente", "Éxito",
										JOptionPane.INFORMATION_MESSAGE);

								resetAddMode();
								loadEquipos();

							} catch (Exception ex) {
								JOptionPane.showMessageDialog(ListEquipo.this,
										"Error al guardar equipo: " + ex.getMessage(), "Error",
										JOptionPane.ERROR_MESSAGE);
								ex.printStackTrace();

								resetAddMode();
								loadEquipos();
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
			}
			loadEquipos();
		}
	}

	public void loadEquipos() {
		model.setRowCount(0);

		try {
			Controladora controladora = Controladora.getInstance();
			controladora.cargarEquiposFromDB();
			controladora.cargarCiudadesFromDB();

			for (Equipo equipo : controladora.getMisEquipos()) {
				Ciudad ciudad = controladora.searchCiudadByCod(equipo.getIdCiudad());
				String nombreCiudad = ciudad != null ? ciudad.getNombre() : "N/A";

				Object[] row = { equipo.getNombreString(), nombreCiudad };
				model.addRow(row);
			}

			if (controladora.getMisEquipos().isEmpty()) {
				JOptionPane.showMessageDialog(this, "No se encontraron equipos en la base de datos.", "Información",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al cargar equipos: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		updatebtn.setEnabled(false);
		deletebtn.setEnabled(false);
	}

	private void makeTableEditable() {
		try {
			Controladora controladora = Controladora.getInstance();

			JComboBox<String> ciudadComboBox = new JComboBox<>();
			for (Ciudad ciudad : controladora.getMisCiudades()) {
				ciudadComboBox.addItem(ciudad.getNombre());
			}

			table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JTextField()));
			table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(ciudadComboBox));

			table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
				@Override
				public boolean isCellEditable(java.util.EventObject e) {
					return isAddingNewRow;
				}
			});

			table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(ciudadComboBox) {
				@Override
				public boolean isCellEditable(java.util.EventObject e) {
					return isAddingNewRow;
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

		table.setDefaultEditor(Object.class, null);
		table.getColumnModel().getColumn(0).setCellEditor(null);
		table.getColumnModel().getColumn(1).setCellEditor(null);

		searchbtn.setEnabled(true);
		updatebtn.setEnabled(false);
		deletebtn.setEnabled(false);
	}

	private void cancelAddMode() {
		int lastRow = model.getRowCount() - 1;
		if (lastRow >= 0 && isAddingNewRow) {
			model.removeRow(lastRow);
		}
		resetAddMode();
	}

	private void resetUpdateMode() {
		isUpdatingRow = false;
		updatebtn.setText("Actualizar");
		returnbtn.setText("Retornar");
		table.setDefaultEditor(Object.class, null);
		table.getColumnModel().getColumn(0).setCellEditor(null);
		table.getColumnModel().getColumn(1).setCellEditor(null);

		addbtn.setEnabled(true);
		searchbtn.setEnabled(true);
		updatebtn.setEnabled(false);
		deletebtn.setEnabled(false);
	}

	private boolean verificarCiudadEnBD(int idCiudad) {
		try {
			SQLConnection dbConnection = new SQLConnection();
			Connection connection = dbConnection.getConnection();

			String idCiudadFormatted = String.format("%02d", idCiudad);

			String query = "SELECT COUNT(*) FROM Ciudad WHERE IdCiudad = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, idCiudadFormatted);

			java.sql.ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				rs.close();
				stmt.close();
				connection.close();
				return count > 0;
			}

			rs.close();
			stmt.close();
			connection.close();
			return false;

		} catch (Exception e) {
			System.err.println("Error verificando ciudad en BD: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
