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
import logico.Tienda;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.HttpRetryException;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
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
	private String cod = "";
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
		setTitle("Lista de Jugadores");
		setBounds(100, 100, 1349, 751);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1116, 685);
		contentPanel.add(scrollPane);

		model = new DefaultTableModel();
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int ind = table.getSelectedRow();
				if (ind >= 0) {
					NumJugador = (int) table.getValueAt(ind, 0);
					
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
				searchbtn.setBorder(new RoundedBorder(HoverEffectColor, 1, 20));
				searchbtn.setForeground(HoverEffectColor);
				searchbtn.setOpaque(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				searchbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
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
				returnbtn.setBorder(new RoundedBorder(HoverEffectColor, 1, 20));
				returnbtn.setForeground(HoverEffectColor);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				returnbtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
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
				//Logica de eliminar aqui, buscando e eliminando del arraylist de la controladora
			}
		});
		deleteBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				deleteBtn.setBorder(new RoundedBorder(HoverEffectColor, 1, 20));
				deleteBtn.setForeground(HoverEffectColor);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				deleteBtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
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
				//Logica para la update de informaciones
			}
		});
		updateBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				updateBtn.setBorder(new RoundedBorder(HoverEffectColor, 1, 20));
				updateBtn.setForeground(HoverEffectColor);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				updateBtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
				updateBtn.setForeground(SecondaryC);
			}
		});
		updateBtn.setBorder(new RoundedBorder(SecondaryC, 1, 20));
		updateBtn.setForeground(SecondaryC);
		updateBtn.setBackground(new Color(248, 248, 248));
		updateBtn.setOpaque(true);
		updateBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		updateBtn.setEnabled(false);
		updateBtn.setBounds(22, 556, 169, 40);
		panel.add(updateBtn);
		
		loadJugadores();
	}
	
	public void loadJugadores(){
		Controladora controladora = Controladora.getInstance();
		ArrayList<Jugador> aux = controladora.getMisJugadores();
		model.setRowCount(0);
		row = new Object[table.getColumnCount()];
		Ciudad ciudad = null;
		Equipo equipo = null;
		int idCiudad;
		int idEquipo;
		for(Jugador jugador : aux) {
			idCiudad = jugador.getIdCiudad();
			idEquipo = jugador.getIdEquipo();
			
			ciudad = controladora.searchCiudadByCod(idCiudad);
			equipo = controladora.searchEquipoByCod(idEquipo);
			
			row[0] = jugador.getNumeroJugador();
			row[1] = jugador.getNombre();
			row[2] = ciudad.getNombre();
			row[3] = equipo.getIdEquipo();
			row[4] = jugador.getFechaNacimiento();
			
		}
		
		updateBtn.setEnabled(false);
		deleteBtn.setEnabled(false);
	}
}
