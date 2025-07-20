package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyledEditorKit.BoldAction;

import logico.RoundedBorder;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class RegJugador extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static final Color SecondaryC = new Color(3, 104, 196);
	private static final Color hoverEffectColor = new Color(3, 135, 255);
	private JTextField numtxt;
	private JLabel numlabel;
	private JLabel namelabel;
	private JTextField nametxt;
	private JComboBox teamcb;
	private JComboBox citycb;
	private JLabel citylabel;
	private JLabel teamlabel;
	private JLabel birthdaylabel;
	private JButton regbtn;
	private JButton returnbtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegJugador dialog = new RegJugador();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegJugador() {
		setUndecorated(true);
		setModal(true);
		setBounds(100, 100, 450, 350);
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
			line.setBackground(SecondaryC);
			line.setBounds(0, 0, 450, 10);
			panel.add(line);
			
			JLabel titletxt = new JLabel("Registrar Jugador");
			titletxt.setFont(new Font("Century gothic", Font.BOLD, 24));
			titletxt.setBounds(111, 16, 210, 41);
			panel.add(titletxt);
			
			numlabel = new JLabel("Numero: ");
			numlabel.setFont(new Font("century gothic", Font.BOLD, 16));
			numlabel.setBounds(15, 75, 96, 20);
			panel.add(numlabel);
			
			numtxt = new JTextField();
			numtxt.setFont(new Font("century gothic", Font.BOLD, 16));
			numtxt.setBounds(133, 73, 164, 26);
			panel.add(numtxt);
			numtxt.setColumns(10);
			
			namelabel = new JLabel("Nombre:");
			namelabel.setFont(new Font("century gothic", Font.BOLD, 16));
			namelabel.setBounds(15, 113, 103, 20);
			panel.add(namelabel);
			
			nametxt = new JTextField();
			nametxt.setFont(new Font("century gothic", Font.BOLD, 16));
			nametxt.setBounds(133, 109, 279, 26);
			panel.add(nametxt);
			nametxt.setColumns(10);
			
			teamlabel = new JLabel("Equipo:");
			teamlabel.setFont(new Font("century gothic", Font.BOLD, 16));
			teamlabel.setBounds(15, 189, 103, 20);
			panel.add(teamlabel);
			
			teamcb = new JComboBox();
			teamcb.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>"}));
			teamcb.setFont(new Font("century gothic", Font.BOLD, 16));
			teamcb.setBounds(133, 187, 164, 26);
			panel.add(teamcb);
			
			citylabel = new JLabel("Ciudad:");
			citylabel.setBounds(15, 151, 69, 20);
			citylabel.setFont(new Font("century gothic", Font.BOLD, 16));
			panel.add(citylabel);
			
			citycb = new JComboBox();
			citycb.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>"}));
			citycb.setFont(new Font("century gothic", Font.BOLD, 16));
			citycb.setBounds(133, 145, 164, 26);
			panel.add(citycb);
			
			birthdaylabel = new JLabel("Fecha de nac.:");
			birthdaylabel.setFont(new Font("century gothic", Font.BOLD, 16));
			birthdaylabel.setBounds(15, 227, 192, 20);
			panel.add(birthdaylabel);
			
			JSpinner spinner = new JSpinner();
			spinner.setFont(new Font("century Gothic", Font.BOLD, 16));
			spinner.setModel(new SpinnerDateModel(new Date(1752253020000L), null, null, Calendar.DAY_OF_MONTH));
			JSpinner.DateEditor dateEditor = new DateEditor(spinner, "dd/MM/yyyy");
			spinner.setEditor(dateEditor);
			spinner.setBounds(133, 226, 113, 26);
			panel.add(spinner);
			
			regbtn = new JButton("Registrar");
			regbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Logica para guardar los datos, buscar si hay una funcion de buscar equipo y ciudad por nombre para tener los id
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
			regbtn.setBounds(15, 280, 130, 40);
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
			returnbtn.setBounds(300, 280, 130, 40);
			panel.add(returnbtn);
		}
	}
}
