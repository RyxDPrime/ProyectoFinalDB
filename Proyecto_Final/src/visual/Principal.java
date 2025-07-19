package visual;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.SwingPropertyChangeSupport;

import logico.AnimationType;
import logico.MoveToXY;
import logico.RoundedBorder;

import java.awt.Window.Type;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Principal extends JFrame {

	private Dimension dim;
	private final int PANELS_TO_SHOW = 4;
	private final int PANEL_WIDTH = 350;
	private final int PANEL_HEIGHT = 350;
	private final int PANEL_GAP = 24;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton jugador;
	private JButton Teambttn;
	private JButton regJugadobttn;
	private JButton listarJugadoresbttn;
	private JButton Estadisticabttn;
	private JButton ciudadbttn;
	private JPanel panelEquipos;
	private JButton btnListEquipo;
	private JButton regEquipobttn;
	private JPanel panelEstadistica;
	private JButton regEstadistica;
	private JButton listaEstadisticabttn;
	private JPanel panelCiudad;
	private JButton regCiudadbttn;
	private JButton btnListCiudad;
	
	private static final Color PrimaryC = new Color(3, 88, 157);
	private static final Color SecondaryC = new Color(213, 234, 255);
	private static final Color ThirdC = new Color(247, 251, 255);
	private static final Color AccentColor = new Color(247, 109, 71); //255, 150, 95
	private static final Color AccentHoverColor = new Color(255, 136, 73);
	private static final Color BGC = new Color(236, 240, 241);
	private static final Color TextColor = new Color(52, 73, 94);
	private static final Color WTextColor = new Color(255, 255, 255);
	private static final Color ButtonColor = new Color(42, 145, 230);
	private static final Color ButtonBorderColor = new Color(42, 145, 230);
	private static final Color HoverEffevtColor = new Color(71, 168, 247);
	
	private Boolean menuJugador = false;
	private Boolean menuEquipo = false;
	private Boolean menuEstadistica = false;
	private Boolean menuCiudad = false;
	private Boolean menuJuego = false;
	private JButton Juegobttn;
	private JPanel panelJuego;
	private JButton listJuegobttn;
	private JButton RegJuegobttn;
	private JPanel panelJugadores;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
	
		setType(Type.UTILITY);
		
		setResizable(false);
		setBounds(100, 100, 1918, 991);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		dim = getToolkit().getScreenSize();
		setSize(1933, 1080);
		
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new BorderLayout());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 11, 1914, 1045);
		panel.setBackground(Color.WHITE);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel PanelPrincipal = new JPanel();
		PanelPrincipal.setBounds(0, 0, 356, 1040);
		PanelPrincipal.setBackground(SecondaryC);
		panel.add(PanelPrincipal);
		PanelPrincipal.setLayout(null);
		
		panelJugadores = new JPanel();
		panelJugadores.setBounds(0, 198, 344, 152);
		MoveToXY panelJugadoresHide = new MoveToXY(panelJugadores, 0, panelJugadores.getY(),  0.5f, AnimationType.EASE_OUT);
		MoveToXY panelJugadoresShow = new MoveToXY(panelJugadores, 354, panelJugadores.getY(), 0.5f, AnimationType.EASE_IN);
		panelJugadores.setVisible(false);
		panelJugadores.setBackground(ButtonColor);
		panelJugadores.setBorder(new RoundedBorder(ButtonColor, 1, 20));
		panel.add(panelJugadores);
		panelJugadores.setLayout(null);
		
		panelEquipos = new JPanel();
		panelEquipos.setLayout(null);
		MoveToXY panelEquipoHide = new MoveToXY(panelEquipos, 0, 287, 0.5f, AnimationType.EASE_OUT);
		MoveToXY panelEquipoShow = new MoveToXY(panelEquipos, 354, 287, 0.5f, AnimationType.EASE_IN); 
		panelEquipos.setVisible(false);
		panelEquipos.setBackground(ButtonColor);
		panelEquipos.setBorder(new RoundedBorder(ButtonColor, 1, 20));
		panelEquipos.setBounds(0, 287, 344, 152);
		panel.add(panelEquipos);
		
		panelCiudad = new JPanel();
		panelCiudad.setLayout(null);
		panelCiudad.setBounds(0, 465, 344, 152);
		MoveToXY panelCiudadHide = new MoveToXY(panelCiudad, 0, panelCiudad.getY(), 0.5f, AnimationType.EASE_OUT);
		MoveToXY panelCiudadShow = new MoveToXY(panelCiudad, 354, panelCiudad.getY(), 0.5f, AnimationType.EASE_IN);
		panelCiudad.setVisible(false);
		panelCiudad.setBackground(ButtonColor);
		panelCiudad.setBorder(new RoundedBorder(ButtonColor, 1, 20));
		panel.add(panelCiudad);
		
		panelJuego = new JPanel();
		panelJuego.setBounds(0, 542, 344, 152);
		panel.add(panelJuego);
		MoveToXY panelJuegoHide = new MoveToXY(panelJuego, 0, panelJuego.getY(), 0.5f, AnimationType.EASE_OUT);
		MoveToXY panelJuegoShow = new MoveToXY(panelJuego, 354, panelJuego.getY(), 0.5f, AnimationType.EASE_IN);
		panelJuego.setVisible(false);
		panelJuego.setBackground(ButtonColor);
		panelJuego.setBorder(new RoundedBorder(ButtonColor, 1, 20));
		panelJuego.setLayout(null);
		
		panelEstadistica = new JPanel();
		panelEstadistica.setBounds(0, 369, 344, 152);
		panel.add(panelEstadistica);
		MoveToXY panelEstadisticaHide = new MoveToXY(panelEstadistica, 0, panelEstadistica.getY(), 0.5f, AnimationType.EASE_OUT);
		MoveToXY panelEstadisticaShow = new MoveToXY(panelEstadistica, 354, panelEstadistica.getY(), 0.5f, AnimationType.EASE_IN);
		panelEstadistica.setVisible(false);
		panelEstadistica.setBackground(ButtonColor);
		panelEstadistica.setBorder(new RoundedBorder(ButtonColor, 1, 20));
		panelEstadistica.setLayout(null);
		
		
		jugador = new JButton("Jugadores");
		jugador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				jugador.setBackground(HoverEffevtColor);
				jugador.setBorder(new CompoundBorder(new RoundedBorder(HoverEffevtColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				jugador.setBackground(ButtonColor);
				jugador.setBorder(new CompoundBorder(new RoundedBorder(ButtonColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
		});
		jugador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!menuJugador) {
					abrirMenuJugador(panelJugadoresShow, panelEquipoHide, panelJuegoHide, panelEstadisticaHide, panelCiudadHide);
				}
				else {
					cerrarMenuJugador(panelJugadoresHide);
				}
			}
		});
		jugador.setBounds(12, 191, 332, 76);
		jugador.setFocusPainted(false);
		jugador.setBackground(ButtonColor);
		jugador.setForeground(Color.WHITE);
		jugador.setFont(new Font("Tahoma", Font.BOLD, 24));
		jugador.setBorder(new CompoundBorder(new RoundedBorder(ButtonBorderColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
		PanelPrincipal.add(jugador);
		
		Teambttn = new JButton("Equipos");
		Teambttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!menuEquipo) {
					abrirMenuEquipos(panelJugadoresHide, panelEquipoShow, panelJuegoHide, panelEstadisticaHide, panelCiudadHide);
				} else {
					cerrarMenuEquipo(panelEquipoHide);
				}
			}
		});
		Teambttn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Teambttn.setBackground(HoverEffevtColor);
				Teambttn.setBorder(new CompoundBorder(new RoundedBorder(HoverEffevtColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Teambttn.setBackground(ButtonColor);
				Teambttn.setBorder(new CompoundBorder(new RoundedBorder(ButtonColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
		});
		Teambttn.setFocusPainted(false);
		Teambttn.setBackground(ButtonColor);
		Teambttn.setForeground(Color.WHITE);
		Teambttn.setHorizontalAlignment(SwingConstants.CENTER);
		Teambttn.setFont(new Font("Tahoma", Font.BOLD, 24));
		Teambttn.setBorder(new CompoundBorder(new RoundedBorder(ButtonBorderColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
		Teambttn.setBounds(12, 280, 332, 76);
		PanelPrincipal.add(Teambttn);
		
		ciudadbttn = new JButton("Ciudad");
		ciudadbttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!menuCiudad) {
					abrirMenuCiudad(panelJugadoresHide, panelEquipoHide, panelJuegoHide, panelEstadisticaHide, panelCiudadShow);
				} else {
					cerrarMenuCiudad(panelCiudadHide);
				}
				
			}
		});
		ciudadbttn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ciudadbttn.setBackground(HoverEffevtColor);
				ciudadbttn.setBorder(new CompoundBorder(new RoundedBorder(HoverEffevtColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				ciudadbttn.setBackground(ButtonColor);
				ciudadbttn.setBorder(new CompoundBorder(new RoundedBorder(ButtonColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));

			}
		});
		ciudadbttn.setBackground(ButtonColor);
		ciudadbttn.setForeground(Color.white);
		ciudadbttn.setHorizontalAlignment(SwingConstants.CENTER);
		ciudadbttn.setFont(new Font("Tahoma", Font.BOLD, 24));
		ciudadbttn.setBorder(new CompoundBorder(new RoundedBorder(ButtonBorderColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
		ciudadbttn.setBounds(12, 458, 332, 76);
		PanelPrincipal.add(ciudadbttn);
		
		Estadisticabttn = new JButton("Estadisticas");
		Estadisticabttn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Estadisticabttn.setBackground(HoverEffevtColor);
				Estadisticabttn.setBorder(new CompoundBorder(new RoundedBorder(HoverEffevtColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Estadisticabttn.setBackground(ButtonColor);
				Estadisticabttn.setBorder(new CompoundBorder(new RoundedBorder(ButtonColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
		});
		Estadisticabttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!menuEstadistica) {
					abrirMenuEstadistica(panelJugadoresHide, panelEquipoHide, panelJuegoHide, panelEstadisticaShow, panelCiudadHide);
				}
				else {
					cerrarMenuEstadistica(panelEstadisticaHide);
				}
			}
		});
		Estadisticabttn.setBackground(ButtonColor);
		Estadisticabttn.setForeground(Color.white);
		Estadisticabttn.setHorizontalAlignment(SwingConstants.CENTER);
		Estadisticabttn.setFont(new Font("Tahoma", Font.BOLD, 24));
		Estadisticabttn.setBorder(new CompoundBorder(new RoundedBorder(ButtonBorderColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
		Estadisticabttn.setBounds(12, 369, 332, 76);
		PanelPrincipal.add(Estadisticabttn);
		
		Juegobttn = new JButton("Juego");
		Juegobttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!menuJuego) {
					abrirMenuJuego(panelJugadoresHide, panelEquipoHide, panelJuegoShow, panelEstadisticaHide, panelCiudadHide);
				} else {
					cerrarMenuJuego(panelJuegoHide);
				}
			}
		});
		Juegobttn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Juegobttn.setBackground(HoverEffevtColor);
				Juegobttn.setBorder(new CompoundBorder(new RoundedBorder(HoverEffevtColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Juegobttn.setBackground(ButtonColor);
				Juegobttn.setBorder(new CompoundBorder(new RoundedBorder(ButtonColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
		});
		Juegobttn.setHorizontalAlignment(SwingConstants.CENTER);
		Juegobttn.setForeground(Color.WHITE);
		Juegobttn.setFont(new Font("Tahoma", Font.BOLD, 24));
		Juegobttn.setBorder(new CompoundBorder(new RoundedBorder(ButtonBorderColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
		Juegobttn.setBackground(new Color(42, 145, 230));
		Juegobttn.setBounds(12, 545, 332, 76);
		PanelPrincipal.add(Juegobttn);
		
		regJugadobttn = new JButton("Reg. Jugador");
		regJugadobttn.setBounds(10, 0, 320, 76);
		regJugadobttn.setBackground(ButtonColor);
		regJugadobttn.setForeground(Color.WHITE);
		regJugadobttn.setFont(new Font("Tahoma", Font.BOLD, 20));
		regJugadobttn.setBorder(new RoundedBorder(ButtonColor, 1, 20));
		regJugadobttn.setFocusPainted(false);
		panelJugadores.add(regJugadobttn);
		
		regJugadobttn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				regJugadobttn.setBackground(HoverEffevtColor);
				regJugadobttn.setBorder(new CompoundBorder(new RoundedBorder(HoverEffevtColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				regJugadobttn.setBackground(ButtonColor);
				regJugadobttn.setBorder(new CompoundBorder(new RoundedBorder(ButtonColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
		});
		listarJugadoresbttn = new JButton("List. Jugador");
		listarJugadoresbttn.setBounds(10, 76, 320, 76);
		listarJugadoresbttn.setBackground(ButtonColor);
		listarJugadoresbttn.setForeground(Color.WHITE);
		listarJugadoresbttn.setFont(new Font("Tahoma", Font.BOLD, 20));
		listarJugadoresbttn.setBorder(new RoundedBorder(ButtonColor, 1, 20));
		listarJugadoresbttn.setFocusPainted(false);
		panelJugadores.add(listarJugadoresbttn);
		
		listarJugadoresbttn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				listarJugadoresbttn.setBackground(HoverEffevtColor);
				listarJugadoresbttn.setBorder(new CompoundBorder(new RoundedBorder(HoverEffevtColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				listarJugadoresbttn.setBackground(ButtonColor);
				listarJugadoresbttn.setBorder(new CompoundBorder(new RoundedBorder(ButtonColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
		});
		
		regEquipobttn = new JButton("Reg. Equipo");
		regEquipobttn.setBounds(10, 0, 320, 76);
		regEquipobttn.setBackground(ButtonColor);
		regEquipobttn.setForeground(Color.WHITE);
		regEquipobttn.setFont(new Font("Tahoma", Font.BOLD, 20));
		regEquipobttn.setBorder(new RoundedBorder(ButtonColor, 1, 20));
		regEquipobttn.setFocusPainted(false);
		panelEquipos.add(regEquipobttn);
		
		regEquipobttn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				regEquipobttn.setBackground(HoverEffevtColor);
				regEquipobttn.setBorder(new CompoundBorder(new RoundedBorder(HoverEffevtColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				regEquipobttn.setBackground(ButtonColor);
				regEquipobttn.setBorder(new CompoundBorder(new RoundedBorder(ButtonColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
		});
		
		btnListEquipo = new JButton("List. Equipo");
		btnListEquipo.setBounds(10, 76, 320, 76);
		btnListEquipo.setBackground(ButtonColor);
		btnListEquipo.setForeground(Color.WHITE);
		btnListEquipo.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnListEquipo.setBorder(new RoundedBorder(ButtonColor, 1, 20));
		btnListEquipo.setFocusPainted(false);
		panelEquipos.add(btnListEquipo);
		
		btnListEquipo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnListEquipo.setBackground(HoverEffevtColor);
				btnListEquipo.setBorder(new CompoundBorder(new RoundedBorder(HoverEffevtColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnListEquipo.setBackground(ButtonColor);
				btnListEquipo.setBorder(new CompoundBorder(new RoundedBorder(ButtonColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
		});
		
		regCiudadbttn = new JButton("Reg. Ciudad");
		regCiudadbttn.setBounds(10, 0, 326, 76);
		regCiudadbttn.setBackground(ButtonColor);
		regCiudadbttn.setForeground(Color.WHITE);
		regCiudadbttn.setFont(new Font("Tahoma", Font.BOLD, 20));
		regCiudadbttn.setBorder(new RoundedBorder(ButtonColor, 1, 20));
		regCiudadbttn.setFocusPainted(false);
		panelCiudad.add(regCiudadbttn);
		
		regCiudadbttn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				regCiudadbttn.setBackground(HoverEffevtColor);
				regCiudadbttn.setBorder(new CompoundBorder(new RoundedBorder(HoverEffevtColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				regCiudadbttn.setBackground(ButtonColor);
				regCiudadbttn.setBorder(new CompoundBorder(new RoundedBorder(ButtonColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
		});
		
		btnListCiudad = new JButton("List. Ciudad");
		btnListCiudad.setBounds(10, 76, 326, 76);
		btnListCiudad.setBackground(ButtonColor);
		btnListCiudad.setForeground(Color.WHITE);
		btnListCiudad.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnListCiudad.setBorder(new RoundedBorder(ButtonColor, 1, 20));
		panelCiudad.add(btnListCiudad);
		
		btnListCiudad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnListCiudad.setBackground(HoverEffevtColor);
				btnListCiudad.setBorder(new CompoundBorder(new RoundedBorder(HoverEffevtColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnListCiudad.setBackground(ButtonColor);
				btnListCiudad.setBorder(new CompoundBorder(new RoundedBorder(ButtonColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
		});
	
		regEstadistica = new JButton("Reg. Estadistica");
		regEstadistica.setBounds(10, 0, 326, 76);
		regEstadistica.setBackground(ButtonColor);
		regEstadistica.setForeground(Color.WHITE);
		regEstadistica.setFont(new Font("Tahoma", Font.BOLD, 20));
		regEstadistica.setBorder(new RoundedBorder(ButtonColor, 1, 20));
		panelEstadistica.add(regEstadistica);
		
		regEstadistica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				regEstadistica.setBackground(HoverEffevtColor);
				regEstadistica.setBorder(new CompoundBorder(new RoundedBorder(HoverEffevtColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				regEstadistica.setBackground(ButtonColor);
				regEstadistica.setBorder(new CompoundBorder(new RoundedBorder(ButtonColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
		});
		
		listaEstadisticabttn = new JButton("List. Estadistica");
		listaEstadisticabttn.setBounds(10, 76, 326, 76);
		listaEstadisticabttn.setBackground(ButtonColor);
		listaEstadisticabttn.setForeground(Color.WHITE);
		listaEstadisticabttn.setFont(new Font("Tahoma", Font.BOLD, 20));
		listaEstadisticabttn.setBorder(new RoundedBorder(ButtonColor, 1, 20));
		panelEstadistica.add(listaEstadisticabttn);

		listaEstadisticabttn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				listaEstadisticabttn.setBackground(HoverEffevtColor);
				listaEstadisticabttn.setBorder(new CompoundBorder(new RoundedBorder(HoverEffevtColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				listaEstadisticabttn.setBackground(ButtonColor);
				listaEstadisticabttn.setBorder(new CompoundBorder(new RoundedBorder(ButtonColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
		});
		
		RegJuegobttn = new JButton("Reg. Juego");
		RegJuegobttn.setBounds(10, 0, 326, 76);
		RegJuegobttn.setBackground(ButtonColor);
		RegJuegobttn.setForeground(Color.WHITE);
		RegJuegobttn.setFont(new Font("Tahoma", Font.BOLD, 20));
		RegJuegobttn.setBorder(new RoundedBorder(ButtonColor, 1, 20));
		panelJuego.add(RegJuegobttn);
		
		RegJuegobttn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				RegJuegobttn.setBackground(HoverEffevtColor);
				RegJuegobttn.setBorder(new CompoundBorder(new RoundedBorder(HoverEffevtColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				RegJuegobttn.setBackground(ButtonColor);
				RegJuegobttn.setBorder(new CompoundBorder(new RoundedBorder(ButtonColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
		});
		
		listJuegobttn = new JButton("List. Juego");
		listJuegobttn.setBounds(10, 76, 326, 76);
		listJuegobttn.setBackground(ButtonColor);
		listJuegobttn.setForeground(Color.WHITE);
		listJuegobttn.setFont(new Font("Tahoma", Font.BOLD, 20));
		listJuegobttn.setBorder(new RoundedBorder(ButtonColor, 1, 20));
		panelJuego.add(listJuegobttn);

		listJuegobttn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				listJuegobttn.setBackground(HoverEffevtColor);
				listJuegobttn.setBorder(new CompoundBorder(new RoundedBorder(HoverEffevtColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				listJuegobttn.setBackground(ButtonColor);
				listJuegobttn.setBorder(new CompoundBorder(new RoundedBorder(ButtonColor, 1, 20), new EmptyBorder(0, 10, 0, 10)));
			}
		});
	}

	private void abrirMenuJugador(MoveToXY panelJugadorShow, MoveToXY panelEquipoHide, MoveToXY panelJuegoHide, MoveToXY panelEstadisticaHide, MoveToXY panelCiudadHide) {
		regJugadobttn.setVisible(true);
		listarJugadoresbttn.setVisible(true);
		panelJugadores.setVisible(true);
		panelJugadorShow.start();
		cerrarMenuCiudad(panelCiudadHide);
		cerrarMenuEquipo(panelEquipoHide);
		cerrarMenuEstadistica(panelEstadisticaHide);
		cerrarMenuJuego(panelJuegoHide);
		menuJugador = true;
		menuEquipo = false;
		menuCiudad = false;
		menuEstadistica = false;
		menuJuego = false;
	}
	
	private void cerrarMenuJugador(MoveToXY panelJugadorHide) {
		menuJugador = false;
		regJugadobttn.setVisible(false);
		listarJugadoresbttn.setVisible(false);
		panelJugadorHide.start();
		
		Timer timer = new Timer(750, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelJugadores.setVisible(false);
				((Timer) e.getSource()).stop();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	private void abrirMenuEquipos(MoveToXY panelJugadorhide, MoveToXY panelEquipoShow, MoveToXY panelJuegoHide, MoveToXY panelEstadisticaHide, MoveToXY panelCiudadShow) {
		regEquipobttn.setVisible(true);
		btnListEquipo.setVisible(true);
		panelEquipos.setVisible(true);
		panelEquipoShow.start();
		cerrarMenuCiudad(panelCiudadShow);
		cerrarMenuEstadistica(panelEstadisticaHide);
		cerrarMenuJuego(panelJuegoHide);
		cerrarMenuJugador(panelJugadorhide);
		menuEquipo = true;
		menuJugador = false;
		menuCiudad = false;
		menuEstadistica = false;
		menuJuego = false;
	}
	
	private void cerrarMenuEquipo(MoveToXY panelEquipoHide) {
		menuEquipo = false;
		panelEquipoHide.start();
		
		Timer timer = new Timer(750, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelEquipos.setVisible(false);
				regEquipobttn.setVisible(false);
				btnListEquipo.setVisible(false);
				((Timer) e.getSource()).stop();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	private void abrirMenuEstadistica(MoveToXY panelJugadorhide, MoveToXY panelEquipoHide, MoveToXY panelJuegoHide, MoveToXY panelEstadisticaShow, MoveToXY panelCiudadShow) {
		regEstadistica.setVisible(true);
		listaEstadisticabttn.setVisible(true);
		panelEstadistica.setVisible(true);
		panelEstadisticaShow.start();
		cerrarMenuCiudad(panelCiudadShow);
		cerrarMenuEquipo(panelEquipoHide);
		cerrarMenuJuego(panelJuegoHide);
		cerrarMenuJugador(panelJugadorhide);
		menuCiudad = false;
		menuEquipo = false;
		menuEstadistica = true;
		menuJuego = false;
		menuJugador = false;
	}
	
	private void cerrarMenuEstadistica(MoveToXY panelEstadisticaHide) {
		menuEstadistica = false;
		panelEstadisticaHide.start();
		
		Timer timer = new Timer(750, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelEstadistica.setVisible(false);
				regEstadistica.setVisible(false);
				listaEstadisticabttn.setVisible(false);
				((Timer) e.getSource()).stop();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	private void abrirMenuJuego(MoveToXY panelJugadorHide, MoveToXY panelEquipoHide, MoveToXY panelJuegoShow, MoveToXY panelEstadisticaHide, MoveToXY panelCiudadHide) {
		RegJuegobttn.setVisible(true);
		listJuegobttn.setVisible(true);
		panelJuego.setVisible(true);
		panelJuegoShow.start();
		cerrarMenuCiudad(panelCiudadHide);
		cerrarMenuEquipo(panelEquipoHide);
		cerrarMenuEstadistica(panelEstadisticaHide);
		cerrarMenuJugador(panelJugadorHide);
		menuCiudad = false;
		menuEquipo = false;
		menuEstadistica = false;
		menuJuego = true;
		menuJugador = false;
	}
	
	private void cerrarMenuJuego(MoveToXY panelJuegoHide) {
		menuJuego = false;
		panelJuegoHide.start();
		
		Timer timer = new Timer(750, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panelJuego.setVisible(false);
				RegJuegobttn.setVisible(false);
				listJuegobttn.setVisible(false);
				((Timer)e.getSource()).stop();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	private void abrirMenuCiudad(MoveToXY panelJugadorHide, MoveToXY panelEquipoHide, MoveToXY panelJuegoHide, MoveToXY panelEstadisticaHide, MoveToXY panelCiudadShow) {
		regCiudadbttn.setVisible(true);
		btnListCiudad.setVisible(true);
		panelCiudad.setVisible(true);
		panelCiudadShow.start();
		cerrarMenuEquipo(panelEquipoHide);
		cerrarMenuEstadistica(panelEstadisticaHide);
		cerrarMenuJuego(panelJuegoHide);
		cerrarMenuJugador(panelJugadorHide);
		menuCiudad = true;
		menuEquipo = false;
		menuEstadistica = false;
		menuJuego = false;
		menuJugador = false;
	}
	
	private void cerrarMenuCiudad(MoveToXY panelCiudadHide) {
		menuCiudad = false;
		panelCiudadHide.start();
		
		Timer timer = new Timer(750, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panelCiudad.setVisible(false);
				regCiudadbttn.setVisible(false);
				btnListCiudad.setVisible(false);
				((Timer)e.getSource()).stop();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
}