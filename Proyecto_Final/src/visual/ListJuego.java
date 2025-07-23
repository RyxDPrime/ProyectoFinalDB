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
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Calendar;

public class ListJuego extends JDialog {

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
	private JScrollPane scrollPane;
	private JTable table;
	private static Object row[];
	private DefaultTableModel model;
	private String equipoA = "";
	private String equipoB = "";
	private java.util.Date fecha;
	private JPanel panel;
	private JComboBox teamAcb;
	private JComboBox teamBcb;
	private JButton returnbtn;
	private JButton deletebtn;
	private JButton updatebtn;
	private JButton detailbtn;

	private boolean isUpdatingRow = false;
	private int juegoId = -1; // Para almacenar el ID del juego seleccionado

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
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int ind = table.getSelectedRow();
				if (ind >= 0) {
					equipoA = (String) table.getValueAt(ind, 0);
					equipoB = (String) table.getValueAt(ind, 1);
					java.sql.Timestamp fechaHora = (java.sql.Timestamp) table.getValueAt(ind, 3);

					// Obtener el ID del juego desde la base de datos usando fecha también
					try {
						juegoId = obtenerIdJuego(equipoA, equipoB, fechaHora);
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					updatebtn.setEnabled(true);
					deletebtn.setEnabled(true);
					detailbtn.setEnabled(true);
				}
			}
		});
		table.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String[] header = { "Equipo Local", "Equipo Visitante", "Descripcion", "Fecha y Hora" };
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

		panel = new JPanel();
		panel.setBackground(PrimaryC);
		panel.setBounds(1118, 0, 213, 704);
		contentPanel.add(panel);
		panel.setLayout(null);

		teamAcb = new JComboBox();
		teamAcb.setModel(new DefaultComboBoxModel(new String[] { "<Equipo Local>" }));
		teamAcb.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		teamAcb.setBounds(24, 16, 163, 31);
		panel.add(teamAcb);

		teamBcb = new JComboBox();
		teamBcb.setModel(new DefaultComboBoxModel(new String[] { "<Equipo Visitante>" }));
		teamBcb.setBounds(24, 54, 163, 31);
		teamBcb.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		panel.add(teamBcb);

		returnbtn = new JButton("Retornar");
		returnbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isUpdatingRow) {
					// Si está en modo edición, cancelar la edición
					cancelUpdateMode();
				} else {
					// Si no está en modo edición, cerrar el diálogo
					dispose();
				}
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
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					// Obtener información del juego seleccionado
					String equipoLocal = (String) table.getValueAt(selectedRow, 0);
					String equipoVisitante = (String) table.getValueAt(selectedRow, 1);
					String descripcion = (String) table.getValueAt(selectedRow, 2);
					java.sql.Timestamp fechaHora = (java.sql.Timestamp) table.getValueAt(selectedRow, 3);

					// Mostrar diálogo de confirmación
					int confirmacion = JOptionPane.showConfirmDialog(ListJuego.this,
							"¿Está seguro que desea eliminar este juego?\n\n" + "Equipo Local: " + equipoLocal + "\n"
									+ "Equipo Visitante: " + equipoVisitante + "\n" + "Descripción: " + descripcion
									+ "\n" + "Fecha: " + fechaHora,
							"Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (confirmacion == JOptionPane.YES_OPTION) {
						try {
							// Obtener el ID del juego a eliminar
							int juegoIdToDelete = obtenerIdJuego(equipoLocal, equipoVisitante, fechaHora);

							if (juegoIdToDelete != -1) {
								// Eliminar el juego usando la función de la Controladora
								eliminarJuego(juegoIdToDelete);

								JOptionPane.showMessageDialog(ListJuego.this, "Juego eliminado correctamente",
										"Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);

								// Recargar la tabla
								loadJuegos();

								// Deshabilitar botones ya que no hay selección
								deletebtn.setEnabled(false);
								updatebtn.setEnabled(false);
								detailbtn.setEnabled(false);

							} else {
								JOptionPane.showMessageDialog(ListJuego.this,
										"No se pudo encontrar el juego seleccionado", "Error",
										JOptionPane.ERROR_MESSAGE);
							}

						} catch (Exception ex) {
							JOptionPane.showMessageDialog(ListJuego.this,
									"Error al eliminar el juego: " + ex.getMessage(), "Error de Eliminación",
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
				if (!isUpdatingRow) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						makeTableEditable();
						table.editCellAt(selectedRow, 2); // Empieza edición en la columna descripción
						updatebtn.setText("Guardar");
						returnbtn.setText("Cancelar");
						isUpdatingRow = true;

						// Deshabilitar otros botones
						deletebtn.setEnabled(false);
						detailbtn.setEnabled(false);
						teamAcb.setEnabled(false);
						teamBcb.setEnabled(false);
					}
				} else {
					// Guardar cambios
					if (table.getCellEditor() != null) {
						table.getCellEditor().stopCellEditing();
					}

					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						String equipoLocal = (String) model.getValueAt(selectedRow, 0);
						String equipoVisitante = (String) model.getValueAt(selectedRow, 1);
						String descripcion = (String) model.getValueAt(selectedRow, 2);
						java.sql.Timestamp fechaHora = (java.sql.Timestamp) model.getValueAt(selectedRow, 3);

						// Validaciones
						if (equipoLocal == null || equipoLocal.trim().isEmpty() || equipoLocal.equals("")) {
							JOptionPane.showMessageDialog(ListJuego.this, "Debe seleccionar un equipo local válido",
									"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						if (equipoVisitante == null || equipoVisitante.trim().isEmpty() || equipoVisitante.equals("")) {
							JOptionPane.showMessageDialog(ListJuego.this, "Debe seleccionar un equipo visitante válido",
									"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						if (equipoLocal.equals(equipoVisitante)) {
							JOptionPane.showMessageDialog(ListJuego.this,
									"El equipo local y visitante no pueden ser el mismo", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}

						if (descripcion == null || descripcion.trim().isEmpty()) {
							JOptionPane.showMessageDialog(ListJuego.this, "La descripción no puede estar vacía",
									"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						try {
							// Obtener IDs de los equipos
							int idEquipoLocal = obtenerIdEquipoPorNombre(equipoLocal);
							int idEquipoVisitante = obtenerIdEquipoPorNombre(equipoVisitante);

							if (idEquipoLocal == -1 || idEquipoVisitante == -1) {
								JOptionPane.showMessageDialog(ListJuego.this,
										"Uno o ambos equipos no existen en la base de datos", "Error",
										JOptionPane.ERROR_MESSAGE);
								return;
							}

							// Actualizar en la base de datos
							actualizarJuego(juegoId, idEquipoLocal, idEquipoVisitante, descripcion, fechaHora);

							JOptionPane.showMessageDialog(ListJuego.this, "Juego actualizado correctamente", "Éxito",
									JOptionPane.INFORMATION_MESSAGE);

							// Reset estado
							resetUpdateMode();
							loadJuegos();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(ListJuego.this,
									"Error al actualizar juego: " + ex.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
							ex.printStackTrace();
							resetUpdateMode();
							loadJuegos();
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
				if (detailbtn.isEnabled()) {
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
				if (juegoId != -1) {
					DetalleJuego detalle = new DetalleJuego(juegoId);
					detalle.setLocationRelativeTo(ListJuego.this);
					detalle.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(ListJuego.this, "Seleccione un juego para ver los detalles",
							"Sin selección", JOptionPane.WARNING_MESSAGE);
				}
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
			JOptionPane.showMessageDialog(this, "Error al cargar equipos: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
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
				"SELECT e1.Nombre_Equipo AS Local, e2.Nombre_Equipo AS Visitante, " + "j.Descripcion, j.Fecha_Hora "
						+ "FROM Juego j " + "JOIN Equipo e1 ON j.Id_EquipoA_Local = e1.IdEquipo "
						+ "JOIN Equipo e2 ON j.Id_EquipoB_Visitante = e2.IdEquipo ");

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
				Object[] row = { rs.getString("Local"), rs.getString("Visitante"), rs.getString("Descripcion"),
						rs.getTimestamp("Fecha_Hora") };
				model.addRow(row);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al filtrar juegos: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void loadJuegos() {
		model.setRowCount(0); // Limpiar tabla

		try (Connection connection = SQLConnection.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT e1.Nombre_Equipo AS Local, e2.Nombre_Equipo AS Visitante, "
						+ "j.Descripcion, j.Fecha_Hora AS Fecha_Hora " + "FROM Juego j "
						+ "JOIN Equipo e1 ON j.Id_EquipoA_Local = e1.IdEquipo "
						+ "JOIN Equipo e2 ON j.Id_EquipoB_Visitante = e2.IdEquipo " + "ORDER BY j.Fecha_Hora DESC")) {

			while (rs.next()) {
				Object[] row = { rs.getString("Local"), rs.getString("Visitante"), rs.getString("Descripcion"),
						rs.getTimestamp("Fecha_Hora") };
				model.addRow(row);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al cargar juegos: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void makeTableEditable() {
		try {
			// Detener cualquier edición activa primero
			if (table.getCellEditor() != null) {
				table.getCellEditor().cancelCellEditing();
			}

			// Crear ComboBox para equipos locales
			JComboBox<String> equipoLocalComboBox = new JComboBox<>();

			// Crear ComboBox para equipos visitantes
			JComboBox<String> equipoVisitanteComboBox = new JComboBox<>();

			// Cargar equipos desde la base de datos (SIN elementos vacíos)
			try (Connection connection = SQLConnection.getConnection();
					Statement stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT Nombre_Equipo FROM Equipo ORDER BY Nombre_Equipo")) {

				while (rs.next()) {
					String nombreEquipo = rs.getString("Nombre_Equipo");
					equipoLocalComboBox.addItem(nombreEquipo);
					equipoVisitanteComboBox.addItem(nombreEquipo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			// Crear Spinner para fecha y hora con el mismo formato que RegJuego
			JSpinner fechaSpinner = new JSpinner();
			fechaSpinner.setFont(new Font("Century Gothic", Font.PLAIN, 16));
			fechaSpinner.setModel(new SpinnerDateModel(new java.util.Date(), null, null, Calendar.MINUTE));

			// Establece el formato de fecha y hora igual que in RegJuego
			JSpinner.DateEditor editor = new JSpinner.DateEditor(fechaSpinner, "dd/MM/yyyy HH:mm");
			fechaSpinner.setEditor(editor);

			// Crear editor personalizado para el spinner de fecha
			javax.swing.DefaultCellEditor fechaEditor = new javax.swing.DefaultCellEditor(
					new javax.swing.JTextField()) {
				private JSpinner spinner;
				private JSpinner.DefaultEditor spinnerEditor;

				@Override
				public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
						int column) {

					spinner = new JSpinner();
					spinner.setModel(new SpinnerDateModel(new java.util.Date(), null, null, Calendar.MINUTE));

					JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy HH:mm");
					spinner.setEditor(dateEditor);
					spinnerEditor = dateEditor;

					// Si hay un valor existente, establecerlo en el spinner
					if (value instanceof java.sql.Timestamp) {
						spinner.setValue(new java.util.Date(((java.sql.Timestamp) value).getTime()));
					} else if (value instanceof java.util.Date) {
						spinner.setValue((java.util.Date) value);
					} else {
						spinner.setValue(new java.util.Date());
					}

					return spinner;
				}

				@Override
				public Object getCellEditorValue() {
					if (spinner != null) {
						java.util.Date selectedDate = (java.util.Date) spinner.getValue();
						return new java.sql.Timestamp(selectedDate.getTime());
					}
					return new java.sql.Timestamp(new java.util.Date().getTime());
				}

				@Override
				public boolean isCellEditable(java.util.EventObject e) {
					return isUpdatingRow;
				}
			};

			// Configurar editores personalizados para cada columna
			table.getColumnModel().getColumn(0).setCellEditor(new javax.swing.DefaultCellEditor(equipoLocalComboBox) {
				@Override
				public boolean isCellEditable(java.util.EventObject e) {
					return isUpdatingRow;
				}
			});

			table.getColumnModel().getColumn(1)
					.setCellEditor(new javax.swing.DefaultCellEditor(equipoVisitanteComboBox) {
						@Override
						public boolean isCellEditable(java.util.EventObject e) {
							return isUpdatingRow;
						}
					});

			table.getColumnModel().getColumn(2)
					.setCellEditor(new javax.swing.DefaultCellEditor(new javax.swing.JTextField()) {
						@Override
						public boolean isCellEditable(java.util.EventObject e) {
							return isUpdatingRow;
						}
					});

			// Usar el editor personalizado del spinner para la columna de fecha
			table.getColumnModel().getColumn(3).setCellEditor(fechaEditor);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al configurar la tabla para edición: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void resetUpdateMode() {
		isUpdatingRow = false;
		updatebtn.setText("Actualizar");
		returnbtn.setText("Retornar");

		// IMPORTANTE: Detener cualquier edición activa antes de limpiar editores
		if (table.getCellEditor() != null) {
			table.getCellEditor().cancelCellEditing();
		}

		// Limpiar editores personalizados
		table.setDefaultEditor(Object.class, null);
		if (table.getColumnCount() > 0) {
			table.getColumnModel().getColumn(0).setCellEditor(null);
		}
		if (table.getColumnCount() > 1) {
			table.getColumnModel().getColumn(1).setCellEditor(null);
		}
		if (table.getColumnCount() > 2) {
			table.getColumnModel().getColumn(2).setCellEditor(null);
		}
		if (table.getColumnCount() > 3) {
			table.getColumnModel().getColumn(3).setCellEditor(null);
		}

		// Limpiar selección
		table.clearSelection();

		deletebtn.setEnabled(false);
		detailbtn.setEnabled(false);
		updatebtn.setEnabled(false);
		teamAcb.setEnabled(true);
		teamBcb.setEnabled(true);
	}

	private void cancelUpdateMode() {
		// Detener edición ANTES de resetear
		if (table.getCellEditor() != null) {
			table.getCellEditor().cancelCellEditing();
		}

		resetUpdateMode();
		loadJuegos();
	}

	private int obtenerIdJuego(String equipoLocal, String equipoVisitante, java.sql.Timestamp fechaHora)
			throws SQLException {
		String sql = "SELECT j.IdJuego FROM Juego j " + "JOIN Equipo e1 ON j.Id_EquipoA_Local = e1.IdEquipo "
				+ "JOIN Equipo e2 ON j.Id_EquipoB_Visitante = e2.IdEquipo "
				+ "WHERE e1.Nombre_Equipo = ? AND e2.Nombre_Equipo = ? AND j.Fecha_Hora = ?";

		try (Connection connection = SQLConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setString(1, equipoLocal);
			stmt.setString(2, equipoVisitante);
			stmt.setTimestamp(3, fechaHora);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("IdJuego");
			}
		}
		return -1;
	}

	private int obtenerIdEquipoPorNombre(String nombreEquipo) throws SQLException {
		String sql = "SELECT IdEquipo FROM Equipo WHERE Nombre_Equipo = ?";

		try (Connection connection = SQLConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setString(1, nombreEquipo);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("IdEquipo");
			}
		}
		return -1;
	}

	private void actualizarJuego(int idJuego, int idEquipoLocal, int idEquipoVisitante, String descripcion,
			java.sql.Timestamp fechaHora) throws SQLException {
		Connection connection = null;
		try {
			connection = SQLConnection.getConnection();
			connection.setAutoCommit(false);

			// Formatear los IDs de equipos con ceros a la izquierda
			String idEquipoLocalFormatted = String.format("%02d", idEquipoLocal).trim();
			String idEquipoVisitanteFormatted = String.format("%02d", idEquipoVisitante).trim();

			// 1. Verificar que ambos equipos existen
			String verificarEquipo = "SELECT COUNT(*) FROM Equipo WHERE IdEquipo = ?";
			try (PreparedStatement stmtVerificar = connection.prepareStatement(verificarEquipo)) {
				stmtVerificar.setString(1, idEquipoLocalFormatted); // Usar String formateado
				ResultSet rs1 = stmtVerificar.executeQuery();
				rs1.next();
				if (rs1.getInt(1) == 0) {
					throw new SQLException("El equipo local con ID " + idEquipoLocalFormatted + " no existe");
				}

				stmtVerificar.setString(1, idEquipoVisitanteFormatted); // Usar String formateado
				ResultSet rs2 = stmtVerificar.executeQuery();
				rs2.next();
				if (rs2.getInt(1) == 0) {
					throw new SQLException("El equipo visitante con ID " + idEquipoVisitanteFormatted + " no existe");
				}
			}

			// 2. Actualizar juego
			String sql = "UPDATE Juego SET Id_EquipoA_Local = ?, Id_EquipoB_Visitante = ?, "
					+ "Descripcion = ?, Fecha_Hora = ? WHERE IdJuego = ?";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setString(1, idEquipoLocalFormatted); // Usar String formateado
				stmt.setString(2, idEquipoVisitanteFormatted); // Usar String formateado
				stmt.setString(3, descripcion);
				stmt.setTimestamp(4, fechaHora);
				stmt.setInt(5, idJuego); // IdJuego se mantiene como int

				int filasActualizadas = stmt.executeUpdate();
				if (filasActualizadas == 0) {
					throw new SQLException("No se pudo actualizar el juego. Verifique que el juego existe.");
				}
			}

			// 3. Commit
			connection.commit();

		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException rollbackEx) {
					rollbackEx.printStackTrace();
				}
			}
			throw e;
		} finally {
			if (connection != null) {
				try {
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException closeEx) {
					closeEx.printStackTrace();
				}
			}
		}
	}

	private void eliminarJuego(int idJuego) throws SQLException {
		Connection connection = null;
		try {
			connection = SQLConnection.getConnection();
			connection.setAutoCommit(false);

			// 1. Verificar que el juego existe antes de eliminarlo
			String verificarSql = "SELECT COUNT(*) FROM Juego WHERE IdJuego = ?";
			try (PreparedStatement stmtVerificar = connection.prepareStatement(verificarSql)) {
				stmtVerificar.setInt(1, idJuego);
				ResultSet rs = stmtVerificar.executeQuery();
				rs.next();
				if (rs.getInt(1) == 0) {
					throw new SQLException("El juego con ID " + idJuego + " no existe");
				}
			}

			// 2. Eliminar primero las estadísticas del juego (si existen)
			String deleteEstadisticasSql = "DELETE FROM [dbo].[Estadistica de Juego] WHERE idJuego = ?";
			try (PreparedStatement stmtEstadisticas = connection.prepareStatement(deleteEstadisticasSql)) {
				stmtEstadisticas.setInt(1, idJuego);
				stmtEstadisticas.executeUpdate();
			}

			// 3. Eliminar el juego
			String deleteJuegoSql = "DELETE FROM Juego WHERE IdJuego = ?";
			try (PreparedStatement stmtJuego = connection.prepareStatement(deleteJuegoSql)) {
				stmtJuego.setInt(1, idJuego);
				int filasEliminadas = stmtJuego.executeUpdate();

				if (filasEliminadas == 0) {
					throw new SQLException("No se pudo eliminar el juego");
				}
			}

			// 4. Commit de la transacción
			connection.commit();

		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException rollbackEx) {
					rollbackEx.printStackTrace();
				}
			}
			throw e;
		} finally {
			if (connection != null) {
				try {
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException closeEx) {
					closeEx.printStackTrace();
				}
			}
		}
	}
}
