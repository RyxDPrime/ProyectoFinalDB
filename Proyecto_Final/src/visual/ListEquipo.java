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

import logico.RoundedBorder;
import server.SQLConnection;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;

public class ListEquipo extends JDialog {

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
	private static Object row[];
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private JTable table;
	private String equipo = "";
	private JPanel panel;
	private JButton searchbtn;
	private JButton returnbtn;
	private JButton deletebtn;
	private JButton updatebtn;
	private JButton addbtn;
	private JTextField equpotxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListEquipo dialog = new ListEquipo();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
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
					}
				}
			});
			table.setFont(new Font("Century Gothic", Font.PLAIN, 14));
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			String[] header = {"Equipo, Ciudad"};
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
		}
		{
			panel = new JPanel();
			panel.setBackground(PrimaryC);
			panel.setBounds(1118, 0, 213, 704);
			contentPanel.add(panel);
			panel.setLayout(null);
			{
				equpotxt = new JTextField();
				equpotxt.setBorder(new CompoundBorder(new RoundedBorder(PrimaryC, 1, 15), new EmptyBorder(0, 10, 0, 10)));
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
			}
			{
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
		}
	}
}
