package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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

import logico.Controladora;
import logico.RoundedBorder;
import logico.Estadistica;
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

import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultCellEditor;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;

public class ListEstadisticas extends JDialog {

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
	private JTable table;
	private static Object row[];
	private DefaultTableModel model;
	private String estadistica = "";
	private JTextField estadisticastxt;
	private JButton searchbtn;
	private JButton updatebtn;
	private JButton deletebtn;
	private JButton returnbtn;
	private JButton addbtn;
	private String estadisticaId = "";
	private boolean isAddingNewRow = false;
	private boolean isUpdatingRow = false;
	private int updatingRowIndex = -1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListEstadisticas dialog = new ListEstadisticas();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListEstadisticas() {
		setUndecorated(true);
		setModal(true);
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
					estadistica = (String) table.getValueAt(ind, 0);

					try {
						Controladora controladora = Controladora.getInstance();
						for (Estadistica est : controladora.getMisEstadisticas()) {
							if (est.getDescripcion().equals(estadistica)) {
								estadisticaId = String.valueOf(est.getIdEstadistica());
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
		table.setFont(new Font("century gothic", Font.PLAIN, 16));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String headers[] = { "Estadistica", "Valor" };
		model.setColumnIdentifiers(headers);
		table.setModel(model);
		scrollPane.setViewportView(table);

		table.setFont(new Font("century gothic", Font.PLAIN, 16));
		table.getTableHeader().setFont(new Font("century gothic", Font.BOLD, 18));
		table.setRowHeight(30);
		table.setBorder(new RoundedBorder(Color.white, 1, 20));

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

		estadisticastxt = new JTextField();
		estadisticastxt
				.setBorder(new CompoundBorder(new RoundedBorder(PrimaryC, 1, 20), new EmptyBorder(0, 10, 0, 10)));
		estadisticastxt.setBounds(22, 23, 169, 40);
		estadisticastxt.setFont(new Font("century Gothic", Font.PLAIN, 18));
		panel.add(estadisticastxt);
		estadisticastxt.setColumns(10);

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
				String descripcion = estadisticastxt.getText().trim();

				if (descripcion.isEmpty()) {
					loadEstadisticas();
					return;
				}

				try {
					Controladora controladora = Controladora.getInstance();
					controladora.cargarEstadisticasFromDB();

					model.setRowCount(0);

					for (Estadistica est : controladora.getMisEstadisticas()) {
						if (est.getDescripcion().toLowerCase().contains(descripcion.toLowerCase())) {
							Object[] row = { est.getDescripcion(), est.getValor() };
							model.addRow(row);
						}
					}

					if (model.getRowCount() == 0) {
						JOptionPane.showMessageDialog(ListEstadisticas.this,
								"No se encontraron estadísticas que coincidan con: " + descripcion, "Sin resultados",
								JOptionPane.INFORMATION_MESSAGE);
					}

					estadisticastxt.setText("");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(ListEstadisticas.this,
							"Error al buscar estadísticas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});
		searchbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
		searchbtn.setBackground(Color.white);
		searchbtn.setForeground(SecondaryC);
		searchbtn.setOpaque(true);
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
				if (estadisticaId != null && !estadisticaId.isEmpty()) {
					int confirm = JOptionPane.showConfirmDialog(ListEstadisticas.this,
							"¿Está seguro de eliminar la estadística '" + estadistica + "'?", "Confirmar Eliminación",
							JOptionPane.YES_NO_OPTION);

					if (confirm == JOptionPane.YES_OPTION) {
						try {
							Controladora controladora = Controladora.getInstance();
							controladora.deleteEstadistica(Integer.parseInt(estadisticaId));

							JOptionPane.showMessageDialog(ListEstadisticas.this, "Estadística eliminada correctamente",
									"Éxito", JOptionPane.INFORMATION_MESSAGE);
							loadEstadisticas();
							deletebtn.setEnabled(false);
							updatebtn.setEnabled(false);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(ListEstadisticas.this,
									"Error al eliminar estadística: " + ex.getMessage(), "Error",
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

		updatebtn = new JButton("Actualizar");
		updatebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isUpdatingRow) {
					if (estadisticaId != null && !estadisticaId.isEmpty()) {
						try {
							int selectedRow = table.getSelectedRow();
							if (selectedRow < 0) {
								JOptionPane.showMessageDialog(ListEstadisticas.this,
										"Debe seleccionar una estadística para actualizar", "Error",
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
							JOptionPane.showMessageDialog(ListEstadisticas.this,
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

						String nuevaDescripcion = (String) model.getValueAt(updatingRowIndex, 0);
						Integer nuevoValor = (Integer) model.getValueAt(updatingRowIndex, 1);

						if (nuevaDescripcion == null || nuevaDescripcion.trim().isEmpty()) {
							JOptionPane.showMessageDialog(ListEstadisticas.this, "La descripción no puede estar vacía",
									"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						if (nuevoValor == null || nuevoValor < 0) {
							JOptionPane.showMessageDialog(ListEstadisticas.this, "El valor debe ser mayor o igual a 0",
									"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						Controladora controladora = Controladora.getInstance();
						Estadistica estadisticaActualizada = new Estadistica(Integer.parseInt(estadisticaId),
								nuevaDescripcion.trim(), nuevoValor);

						int index = -1;
						for (int i = 0; i < controladora.getMisEstadisticas().size(); i++) {
							if (controladora.getMisEstadisticas().get(i).getIdEstadistica() == Integer
									.parseInt(estadisticaId)) {
								index = i;
								break;
							}
						}

						if (index != -1) {
							controladora.updateEstadistica(estadisticaActualizada, index);

							JOptionPane.showMessageDialog(ListEstadisticas.this,
									"Estadística actualizada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

							resetUpdateMode();
							loadEstadisticas();
						} else {
							JOptionPane.showMessageDialog(ListEstadisticas.this,
									"Error: No se pudo encontrar la estadística", "Error", JOptionPane.ERROR_MESSAGE);
						}

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(ListEstadisticas.this,
								"Error al guardar cambios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
						resetUpdateMode();
						loadEstadisticas();
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

					Object[] newRow = { "Nueva Estadística", 0 };
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
						String descripcion = (String) model.getValueAt(rowIndex, 0);
						Integer valor = (Integer) model.getValueAt(rowIndex, 1);

						if (descripcion == null || descripcion.trim().isEmpty()) {
							JOptionPane.showMessageDialog(ListEstadisticas.this, "La descripción no puede estar vacía",
									"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						if (valor == null || valor < 0) {
							JOptionPane.showMessageDialog(ListEstadisticas.this, "El valor debe ser mayor o igual a 0",
									"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						Controladora controladora = Controladora.getInstance();
						Estadistica nuevaEstadistica = new Estadistica(0, descripcion.trim(), valor);
						controladora.insertarEstadistica(nuevaEstadistica);

						JOptionPane.showMessageDialog(ListEstadisticas.this, "Estadística agregada correctamente",
								"Éxito", JOptionPane.INFORMATION_MESSAGE);

						resetAddMode();
						loadEstadisticas();

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(ListEstadisticas.this,
								"Error al guardar estadística: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
						resetAddMode();
						loadEstadisticas();
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

		loadEstadisticas();
	}

	public void loadEstadisticas() {
		if (table.getCellEditor() != null) {
			table.getCellEditor().cancelCellEditing();
		}

		table.setDefaultEditor(Object.class, null);
		if (table.getColumnCount() > 0) {
			table.getColumnModel().getColumn(0).setCellEditor(null);
		}
		if (table.getColumnCount() > 1) {
			table.getColumnModel().getColumn(1).setCellEditor(null);
		}

		model.setRowCount(0);

		try {
			Controladora controladora = Controladora.getInstance();
			controladora.cargarEstadisticasFromDB();

			for (Estadistica estadistica : controladora.getMisEstadisticas()) {

				Object[] row = { estadistica.getDescripcion(), estadistica.getValor() };
				model.addRow(row);
			}

			if (controladora.getMisEstadisticas().isEmpty()) {
				JOptionPane.showMessageDialog(this, "No se encontraron estadísticas en la base de datos.",
						"Información", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al cargar estadísticas: " + e.getMessage(), "Error",
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

			table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JTextField()));
			table.getColumnModel().getColumn(1).setCellEditor(new SpinnerCellEditor());

			table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
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

			table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JTextField()));
			table.getColumnModel().getColumn(1).setCellEditor(new SpinnerCellEditor());

			table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
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

	private class SpinnerCellEditor extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1L;
		private JSpinner spinner;

		public SpinnerCellEditor() {
			spinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		}

		@Override
		public Object getCellEditorValue() {
			return spinner.getValue();
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (value instanceof Integer) {
				spinner.setValue(value);
			} else {
				spinner.setValue(0);
			}
			return spinner;
		}

		@Override
		public boolean isCellEditable(java.util.EventObject e) {
			return isAddingNewRow || isUpdatingRow;
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
		table.getColumnModel().getColumn(1).setCellEditor(null);

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
		table.getColumnModel().getColumn(1).setCellEditor(null);

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
		loadEstadisticas();
	}
}
