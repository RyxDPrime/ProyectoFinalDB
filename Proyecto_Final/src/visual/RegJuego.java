package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.Controladora;
import logico.RoundedBorder;
import logico.Juego;
import server.SQLConnection;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;

public class RegJuego extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static final Color SecondaryC = new Color(3, 104, 196);
	private static final Color hoverEffectColor = new Color(3, 135, 255);
	private JLabel teamAlabel;
	private JComboBox teamAcb;
	private JComboBox teamBcb;
	private JLabel desclabel;
	private JTextField desctxt;
	private JSpinner spinner;
	private JButton regbtn;
	private JButton returnbtn;
	private java.util.List<String> todosLosEquipos = new java.util.ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegJuego dialog = new RegJuego();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegJuego() {
		setUndecorated(true);
		setBounds(100, 100, 450, 300);
		setModal(true);
		setLocationRelativeTo(getParent());
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(new Color(247, 247, 247));
			panel.setBounds(0, 0, 450, 382);
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);

			JPanel line = new JPanel();
			line.setBounds(0, 0, 450, 10);
			line.setBackground(SecondaryC);
			panel.add(line);

			JLabel titletxt = new JLabel("Registrar Juego");
			titletxt.setBounds(111, 16, 210, 41);
			titletxt.setFont(new Font("Century gothic", Font.BOLD, 24));
			panel.add(titletxt);

			teamAlabel = new JLabel("Equipo Local:");
			teamAlabel.setBounds(15, 75, 120, 20);
			teamAlabel.setFont(new Font("century gothic", Font.BOLD, 16));
			panel.add(teamAlabel);

			JLabel taemBlabel = new JLabel("Equipo Visitante:");
			taemBlabel.setFont(new Font("century gothic", Font.BOLD, 16));
			taemBlabel.setBounds(15, 113, 150, 20);
			panel.add(taemBlabel);

			teamAcb = new JComboBox();
			teamAcb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actualizarEquipoB();
				}
			});
			teamAcb.setModel(new DefaultComboBoxModel(new String[] { "<Seleccione>" }));
			teamAcb.setFont(new Font("century gothic", Font.BOLD, 16));
			teamAcb.setBounds(150, 73, 164, 20);
			panel.add(teamAcb);

			teamBcb = new JComboBox();
			teamBcb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actualizarEquipoA();
				}
			});
			teamBcb.setModel(new DefaultComboBoxModel(new String[] { "<Seleccione>" }));
			teamBcb.setFont(new Font("century Gothic", Font.BOLD, 16));
			teamBcb.setBounds(150, 111, 164, 20);
			panel.add(teamBcb);

			desclabel = new JLabel("Descripcion:");
			desclabel.setFont(new Font("century gothic", Font.BOLD, 16));
			desclabel.setBounds(15, 151, 164, 20);
			panel.add(desclabel);

			desctxt = new JTextField();
			desctxt.setFont(new Font("century gothic", Font.BOLD, 14));
			desctxt.setBounds(150, 149, 265, 20);
			panel.add(desctxt);
			desctxt.setColumns(10);

			JLabel datelabel = new JLabel("Fecha y Hora:");
			datelabel.setFont(new Font("century gothic", Font.BOLD, 16));
			datelabel.setBounds(15, 189, 164, 20);
			panel.add(datelabel);

			spinner = new JSpinner();
			spinner.setFont(new Font("Century Gothic", Font.PLAIN, 16));
			spinner.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));

			JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy HH:mm");
			spinner.setEditor(editor);

			spinner.setBounds(150, 187, 210, 20);
			panel.add(spinner);

			regbtn = new JButton("Registrar");
			regbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					registrarJuego();
				}
			});
			regbtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					regbtn.setBackground(new Color(11, 182, 72));
					regbtn.setForeground(Color.white);
					regbtn.setBorder(new RoundedBorder(new Color(11, 182, 72), 1, 20));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					regbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
					regbtn.setBackground(SecondaryC);
					regbtn.setForeground(Color.WHITE);
				}
			});
			regbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
			regbtn.setBackground(SecondaryC);
			regbtn.setForeground(Color.WHITE);
			regbtn.setFont(new Font("century gothic", Font.BOLD, 18));
			regbtn.setBounds(15, 239, 130, 40);
			panel.add(regbtn);

			returnbtn = new JButton("Cancelar");
			returnbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			returnbtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					returnbtn.setBackground(new Color(167, 34, 34));
					returnbtn.setForeground(Color.white);
					returnbtn.setBorder(new RoundedBorder(new Color(167, 34, 34), 1, 20));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					returnbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
					returnbtn.setBackground(SecondaryC);
					returnbtn.setForeground(Color.white);
				}
			});
			returnbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
			returnbtn.setBackground(SecondaryC);
			returnbtn.setForeground(Color.white);
			returnbtn.setFont(new Font("century gothic", Font.BOLD, 18));
			returnbtn.setBounds(300, 239, 130, 40);
			panel.add(returnbtn);

			loadEquipos();
		}
	}

	private void loadEquipos() {
		todosLosEquipos.clear();

		try (Connection connection = SQLConnection.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT Nombre_Equipo FROM Equipo ORDER BY Nombre_Equipo")) {

			while (rs.next()) {
				todosLosEquipos.add(rs.getString("Nombre_Equipo"));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al cargar equipos: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		actualizarEquipoA();
		actualizarEquipoB();
	}

	private void actualizarEquipoA() {
		String equipoSeleccionadoA = (String) teamAcb.getSelectedItem();
		String equipoSeleccionadoB = (String) teamBcb.getSelectedItem();

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		model.addElement("<Seleccione>");

		for (String equipo : todosLosEquipos) {
			if (!equipo.equals(equipoSeleccionadoB) || "<Seleccione>".equals(equipoSeleccionadoB)) {
				model.addElement(equipo);
			}
		}

		teamAcb.removeActionListener(teamAcb.getActionListeners()[0]);
		teamAcb.setModel(model);
		if (equipoSeleccionadoA != null && !equipoSeleccionadoA.equals(equipoSeleccionadoB)) {
			teamAcb.setSelectedItem(equipoSeleccionadoA);
		}
		teamAcb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarEquipoB();
			}
		});
	}

	private void actualizarEquipoB() {
		String equipoSeleccionadoB = (String) teamBcb.getSelectedItem();
		String equipoSeleccionadoA = (String) teamAcb.getSelectedItem();

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		model.addElement("<Seleccione>");

		for (String equipo : todosLosEquipos) {
			if (!equipo.equals(equipoSeleccionadoA) || "<Seleccione>".equals(equipoSeleccionadoA)) {
				model.addElement(equipo);
			}
		}

		teamBcb.removeActionListener(teamBcb.getActionListeners()[0]);
		teamBcb.setModel(model);
		if (equipoSeleccionadoB != null && !equipoSeleccionadoB.equals(equipoSeleccionadoA)) {
			teamBcb.setSelectedItem(equipoSeleccionadoB);
		}
		teamBcb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarEquipoA();
			}
		});
	}

	private void registrarJuego() {
		try {
			String equipoLocal = (String) teamAcb.getSelectedItem();
			String equipoVisitante = (String) teamBcb.getSelectedItem();
			String descripcion = desctxt.getText().trim();
			Date fechaHora = (Date) spinner.getValue();

			if (equipoLocal == null || equipoLocal.equals("<Seleccione>")) {
				JOptionPane.showMessageDialog(this, "Debe seleccionar el equipo local", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (equipoVisitante == null || equipoVisitante.equals("<Seleccione>")) {
				JOptionPane.showMessageDialog(this, "Debe seleccionar el equipo visitante", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (descripcion.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Debe ingresar una descripción", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			int idEquipoLocal = obtenerIdEquipo(equipoLocal);
			int idEquipoVisitante = obtenerIdEquipo(equipoVisitante);

			if (idEquipoLocal == -1 || idEquipoVisitante == -1) {
				JOptionPane
						.showMessageDialog(this,
								"Error al obtener los IDs de los equipos\n" + "Equipo Local ID: " + idEquipoLocal + "\n"
										+ "Equipo Visitante ID: " + idEquipoVisitante,
								"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Juego nuevoJuego = new Juego(0, descripcion, idEquipoLocal, idEquipoVisitante, fechaHora);

			Controladora controladora = Controladora.getInstance();
			controladora.insertarJuego(nuevoJuego);

			JOptionPane.showMessageDialog(this, "Juego registrado exitosamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);

			limpiarFormulario();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al registrar el juego: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private int obtenerIdEquipo(String nombreEquipo) {
		try (Connection connection = SQLConnection.getConnection();
				PreparedStatement stmt = connection
						.prepareStatement("SELECT IdEquipo FROM Equipo WHERE Nombre_Equipo = ?")) {

			stmt.setString(1, nombreEquipo);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("IdEquipo");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private void limpiarFormulario() {
		teamAcb.setSelectedIndex(0);
		teamBcb.setSelectedIndex(0);
		desctxt.setText("");
		spinner.setValue(new Date());

		actualizarEquipoA();
		actualizarEquipoB();
	}
}
