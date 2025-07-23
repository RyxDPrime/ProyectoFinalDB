package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Component;

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
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.SQLConnection;
import logico.Ciudad;
import logico.Controladora;
import logico.Equipo;
import logico.Jugador;

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
	private boolean modoActualizacion = false;
    private int jugadorIdActual = 0;
    private JSpinner spinner;

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
			
			spinner = new JSpinner();
			spinner.setFont(new Font("century Gothic", Font.BOLD, 16));
			spinner.setModel(new SpinnerDateModel(new Date(1752253020000L), null, null, Calendar.DAY_OF_MONTH));
			DateEditor dateEditor = new DateEditor(spinner, "dd/MM/yyyy");
			spinner.setEditor(dateEditor);
			spinner.setBounds(133, 226, 113, 26);
			panel.add(spinner);
			
			regbtn = new JButton("Registrar");
			regbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					registrarOActualizarJugador();
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
		SwingUtilities.invokeLater(() -> cargarDatosComboBoxes());
	}
	
	public void configurarModoActualizacion(int idJugador, String nombre, String ciudad, 
                                      java.util.Date fechaNacimiento, int Numero_Jugador, String equipo) {
    this.modoActualizacion = true;
    this.jugadorIdActual = idJugador;
    
    // Recargar ComboBoxes para asegurar datos actualizados
    cargarDatosComboBoxes();
    
    // Cambiar título de la ventana
    Component[] components = getContentPane().getComponents();
    buscarYCambiarTitulo(components, "Actualizar Jugador");
    
    // Cargar los datos en los campos
    nametxt.setText(nombre != null ? nombre : "");
    numtxt.setText(String.valueOf(Numero_Jugador));
    
    // Configurar fecha
    if (fechaNacimiento != null) {
        spinner.setValue(fechaNacimiento);
    }
    
    // Seleccionar ciudad en ComboBox
    if (ciudad != null && !ciudad.trim().isEmpty()) {
        boolean ciudadEncontrada = false;
        for (int i = 0; i < citycb.getItemCount(); i++) {
            String itemCiudad = citycb.getItemAt(i).toString().trim();
            if (itemCiudad.equalsIgnoreCase(ciudad.trim())) {
                citycb.setSelectedIndex(i);
                ciudadEncontrada = true;
                break;
            }
        }
        if (!ciudadEncontrada) {
            System.out.println("Ciudad no encontrada en ComboBox: '" + ciudad + "'");
        }
    }
    
    // Seleccionar equipo en ComboBox
    if (equipo != null && !equipo.trim().isEmpty()) {
        boolean equipoEncontrado = false;
        for (int i = 0; i < teamcb.getItemCount(); i++) {
            String itemEquipo = teamcb.getItemAt(i).toString().trim();
            if (itemEquipo.equalsIgnoreCase(equipo.trim())) {
                teamcb.setSelectedIndex(i);
                equipoEncontrado = true;
                break;
            }
        }
        if (!equipoEncontrado) {
            System.out.println("Equipo no encontrado en ComboBox: '" + equipo + "'");
        }
    }
    
    // HABILITAR los ComboBox en modo actualización (CAMBIO PRINCIPAL)
    citycb.setEnabled(true);
    teamcb.setEnabled(true);
    
    // Cambiar texto del botón registrar a "Guardar"
    regbtn.setText("Guardar");
    
    System.out.println("Modo actualización configurado para jugador ID: " + idJugador);
    System.out.println("Datos cargados - Nombre: " + nombre + ", Ciudad: " + ciudad + ", Equipo: " + equipo);
    System.out.println("ComboBoxes HABILITADOS para actualización");
}
    
    private void buscarYCambiarTitulo(Component[] components, String nuevoTitulo) {
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                buscarYCambiarTitulo(((JPanel) comp).getComponents(), nuevoTitulo);
            } else if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getText().contains("Registrar")) {
                    label.setText(nuevoTitulo);
                    break;
                }
            }
        }
    }
    
    private void buscarYCambiarBoton(Component[] components, String nuevoTexto) {
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                buscarYCambiarBoton(((JPanel) comp).getComponents(), nuevoTexto);
            } else if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                if (button.getText().equals("Registrar")) {
                    button.setText(nuevoTexto);
                    break;
                }
            }
        }
    }
    
    private void registrarOActualizarJugador() {
    try {
        // Obtener y validar los datos del formulario
        String nombre = nametxt.getText().trim();
        String numeroText = numtxt.getText().trim();
        Date fechaNacimiento = (Date) spinner.getValue();
        
        // Validaciones básicas
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el nombre del jugador", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (numeroText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el número del jugador", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int Numero_Jugador;
        try {
            Numero_Jugador = Integer.parseInt(numeroText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El número debe ser un valor numérico válido", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (modoActualizacion) {
            // Modo actualización - ahora permite cambiar ciudad y equipo también
            String ciudadSeleccionada = (String) citycb.getSelectedItem();
            String equipoSeleccionado = (String) teamcb.getSelectedItem();
            
            if (ciudadSeleccionada == null || ciudadSeleccionada.equals("<Seleccione>")) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una ciudad", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (equipoSeleccionado == null || equipoSeleccionado.equals("<Seleccione>")) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un equipo", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Obtener IDs de ciudad y equipo desde la base de datos
            int idCiudad = obtenerIdCiudadPorNombre(ciudadSeleccionada);
            int idEquipo = obtenerIdEquipoPorNombre(equipoSeleccionado);
            
            if (idCiudad == -1) {
                JOptionPane.showMessageDialog(this, "Ciudad seleccionada no válida", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (idEquipo == -1) {
                JOptionPane.showMessageDialog(this, "Equipo seleccionado no válido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            actualizarJugadorCompleto(jugadorIdActual, nombre, idCiudad, fechaNacimiento, Numero_Jugador, idEquipo);
        } else {
            // Modo registro
            String ciudadSeleccionada = (String) citycb.getSelectedItem();
            String equipoSeleccionado = (String) teamcb.getSelectedItem();
            
            if (ciudadSeleccionada == null || ciudadSeleccionada.equals("<Seleccione>")) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una ciudad", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (equipoSeleccionado == null || equipoSeleccionado.equals("<Seleccione>")) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un equipo", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Obtener IDs para registro
            int idCiudad = obtenerIdCiudadPorNombre(ciudadSeleccionada);
            int idEquipo = obtenerIdEquipoPorNombre(equipoSeleccionado);
            
            if (idCiudad == -1 || idEquipo == -1) {
                JOptionPane.showMessageDialog(this, "Ciudad o equipo seleccionado no válido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            registrarNuevoJugador(nombre, idCiudad, fechaNacimiento, Numero_Jugador, idEquipo);
        }
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}

    // Agregar método para obtener el número actual del jugador:
    private int obtenerNumeroActualJugador(int idJugador) {
        try (Connection connection = SQLConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                 "SELECT Numero_Jugador FROM Jugador WHERE idJugador = ?")) {

        stmt.setInt(1, idJugador);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            return rs.getInt("Numero_Jugador");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // Si no se encuentra, retorna -1
}

    private void actualizarJugadorDirectoBD(int idJugador, String nombre, int idCiudad, 
                                       Date fechaNacimiento, int Numero_Jugador, int idEquipo) throws SQLException {
        
        // Obtener el número actual del jugador
        int numeroActual = obtenerNumeroActualJugador(idJugador);
        
        // Solo verificar duplicados si el número ha cambiado
        if (numeroActual != Numero_Jugador) {
            try (Connection connection = SQLConnection.getConnection();
                 PreparedStatement checkStmt = connection.prepareStatement(
                 "SELECT COUNT(*) FROM Jugador WHERE Numero_Jugador = ? AND idJugador != ?")) {

            checkStmt.setInt(1, Numero_Jugador);
            checkStmt.setInt(2, idJugador);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            
            if (rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Ya existe otro jugador con el número " + Numero_Jugador, 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        try (Connection connection = SQLConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
             "UPDATE Jugador SET Nombre = ?, IdCiudad = ?, Fecha_Nacimiento = ?, " +
             "Numero_Jugador = ?, IdEquipo = ? WHERE idJugador = ?")) {
        
        stmt.setString(1, nombre);
        stmt.setInt(2, idCiudad);
        stmt.setDate(3, new java.sql.Date(fechaNacimiento.getTime()));
        stmt.setInt(4, Numero_Jugador);
        stmt.setInt(5, idEquipo);
        stmt.setInt(6, idJugador);
        
        int rowsAffected = stmt.executeUpdate();
        
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Jugador actualizado exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            limpiarFormulario();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el jugador", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        }
        }
    }
    
    // Nuevo método para obtener ID de ciudad por nombre
    private int obtenerIdCiudadPorNombre(String nombreCiudad) {
    try (Connection connection = SQLConnection.getConnection();
         PreparedStatement stmt = connection.prepareStatement(
             "SELECT IdCiudad FROM Ciudad WHERE TRIM(Nombre_Ciudad) = ?")) {
        
        stmt.setString(1, nombreCiudad.trim());
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            return rs.getInt("IdCiudad");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1;
}

// Nuevo método para obtener ID de equipo por nombre
private int obtenerIdEquipoPorNombre(String nombreEquipo) {
    try (Connection connection = SQLConnection.getConnection();
         PreparedStatement stmt = connection.prepareStatement(
             "SELECT IdEquipo FROM Equipo WHERE TRIM(Nombre_Equipo) = ?")) {
        
        stmt.setString(1, nombreEquipo.trim());
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            return rs.getInt("IdEquipo");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1;
}

// Nuevo método para actualización completa del jugador
private void actualizarJugadorCompleto(int idJugador, String nombre, int idCiudad, 
                                     Date fechaNacimiento, int Numero_Jugador, int idEquipo) {
    try {
        // Obtener datos actuales del jugador
        int numeroActual = obtenerNumeroActualJugador(idJugador);
        int equipoActual = obtenerEquipoActualJugador(idJugador);
        
        System.out.println("=== INICIANDO ACTUALIZACIÓN ===");
        System.out.println("ID Jugador a actualizar: " + idJugador);
        System.out.println("Número actual: " + numeroActual + " -> Nuevo: " + Numero_Jugador);
        System.out.println("Equipo actual: " + equipoActual + " -> Nuevo: " + idEquipo);
        
        // Solo verificar duplicados si el número ha cambiado O si cambió de equipo
        if (numeroActual != Numero_Jugador || equipoActual != idEquipo) {
            // Formatear el ID del equipo con ceros a la izquierda
            String idEquipoFormatted = String.format("%02d", idEquipo);
            
            // Verificar si el número ya existe EN EL EQUIPO DESTINO
            try (Connection connection = SQLConnection.getConnection();
                 PreparedStatement checkStmt = connection.prepareStatement(
                     "SELECT COUNT(*) FROM Jugador WHERE Numero_Jugador = ? AND IdEquipo = ? AND idJugador != ?")) {
                
                checkStmt.setInt(1, Numero_Jugador);
                checkStmt.setString(2, idEquipoFormatted);
                checkStmt.setInt(3, idJugador);
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                
                if (rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Ya existe otro jugador con el número " + Numero_Jugador + " en el equipo seleccionado", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        
        // Formatear los IDs con ceros a la izquierda
        String idCiudadFormatted = String.format("%02d", idCiudad);
        String idEquipoFormatted = String.format("%02d", idEquipo);
        
        System.out.println("Ejecutando UPDATE para jugador ID: " + idJugador);
        System.out.println("Datos formateados - Ciudad: " + idCiudadFormatted + ", Equipo: " + idEquipoFormatted);
        
        // ACTUALIZAR (no insertar) jugador con todos los campos
        try (Connection connection = SQLConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
             "UPDATE Jugador SET Nombre = ?, IdCiudad = ?, Fecha_Nacimiento = ?, " +
             "Numero_Jugador = ?, IdEquipo = ? WHERE idJugador = ?")) {
        
            stmt.setString(1, nombre);
            stmt.setString(2, idCiudadFormatted);
            stmt.setDate(3, new java.sql.Date(fechaNacimiento.getTime()));
            stmt.setInt(4, Numero_Jugador);
            stmt.setString(5, idEquipoFormatted);
            stmt.setInt(6, idJugador); // CLAVE: usar el ID del jugador existente
            
            System.out.println("SQL UPDATE ejecutándose:");
            System.out.println("  WHERE idJugador = " + idJugador);
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Filas afectadas por UPDATE: " + rowsAffected);
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Jugador actualizado exitosamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                limpiarFormulario();
                dispose();
                System.out.println("=== ACTUALIZACIÓN COMPLETADA ===");
            } else {
                System.out.println("ERROR: No se actualizó ninguna fila");
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el jugador. Verifique que el jugador existe.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    } catch (Exception ex) {
        System.out.println("ERROR en actualizarJugadorCompleto: " + ex.getMessage());
        JOptionPane.showMessageDialog(this, "Error al actualizar jugador: " + ex.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}

private void registrarNuevoJugador(String nombre, int idCiudad, Date fechaNacimiento, 
                                 int Numero_Jugador, int idEquipo) {
    try {
        // Debug inicial
        System.out.println("=== INICIANDO REGISTRO DE JUGADOR ===");
        System.out.println("Nombre: " + nombre);
        System.out.println("IdCiudad: " + idCiudad);
        System.out.println("IdEquipo: " + idEquipo);
        System.out.println("Número: " + Numero_Jugador);
        System.out.println("Fecha: " + fechaNacimiento);
        
        // Verificar si el número ya existe EN EL EQUIPO
        try (Connection connection = SQLConnection.getConnection();
             PreparedStatement checkStmt = connection.prepareStatement(
                 "SELECT COUNT(*) FROM Jugador WHERE Numero_Jugador = ? AND IdEquipo = ?")) {
            
            String idEquipoFormatted = String.format("%02d", idEquipo);
            checkStmt.setInt(1, Numero_Jugador);
            checkStmt.setString(2, idEquipoFormatted);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            
            if (rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Ya existe un jugador con el número " + Numero_Jugador + " en el equipo seleccionado", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        System.out.println("Verificación de duplicados completada - OK");
        
        // Crear objeto Jugador (con ID temporal, será actualizado por Controladora)
        Jugador nuevoJugador = new Jugador(0, nombre, idCiudad, fechaNacimiento, Numero_Jugador, idEquipo);
        
        System.out.println("Jugador creado, llamando a Controladora.insertarJugador()...");
        
        // Usar la función de Controladora
        Controladora controladora = Controladora.getInstance();
        controladora.insertarJugador(nuevoJugador);
        
        System.out.println("Controladora.insertarJugador() completado exitosamente");
        
        JOptionPane.showMessageDialog(this, "Jugador registrado exitosamente", 
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
        
        limpiarFormulario();
        System.out.println("=== REGISTRO COMPLETADO EXITOSAMENTE ===");
        
    } catch (RuntimeException ex) {
        System.out.println("ERROR desde Controladora: " + ex.getMessage());
        JOptionPane.showMessageDialog(this, "Error al registrar jugador: " + ex.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException ex) {
        System.out.println("ERROR SQL en verificación: " + ex.getMessage());
        JOptionPane.showMessageDialog(this, "Error al verificar duplicados: " + ex.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    } catch (Exception ex) {
        System.out.println("ERROR GENERAL en registrarNuevoJugador: " + ex.getMessage());
        JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}

// Agregar método para limpiar formulario
private void limpiarFormulario() {
    // Asumiendo que tienes estos campos en tu formulario
    // Ajusta los nombres según tus componentes reales
    if (nametxt != null) nametxt.setText("");
    if (citycb != null) citycb.setSelectedIndex(0);
    if (teamcb != null) teamcb.setSelectedIndex(0);
    if (numtxt != null) numtxt.setText("");
    if (spinner != null) spinner.setValue(new Date());
}

    // Agregar método para cargar datos de ComboBoxes:
    public void cargarDatosComboBoxes() {
        cargarCiudadesComboBox();
        cargarEquiposComboBox();
    }

    private void cargarCiudadesComboBox() {
        citycb.removeAllItems();
        citycb.addItem("<Seleccione>");
        
        try (Connection connection = SQLConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT TRIM(Nombre_Ciudad) as Nombre_Ciudad FROM Ciudad ORDER BY Nombre_Ciudad");
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                citycb.addItem(rs.getString("Nombre_Ciudad"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar ciudades: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void cargarEquiposComboBox() {
        teamcb.removeAllItems();
        teamcb.addItem("<Seleccione>");
        
        try (Connection connection = SQLConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT TRIM(Nombre_Equipo) as Nombre_Equipo FROM Equipo ORDER BY Nombre_Equipo");
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                teamcb.addItem(rs.getString("Nombre_Equipo"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar equipos: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private void actualizarJugadorSoloBasicos(int idJugador, String nombre, Date fechaNacimiento, int Numero_Jugador) {
        try {
            // Obtener el número actual del jugador y su equipo
            int numeroActual = obtenerNumeroActualJugador(idJugador);
            int equipoActual = obtenerEquipoActualJugador(idJugador);
            
            // Solo verificar duplicados si el número ha cambiado
            if (numeroActual != Numero_Jugador) {
                // Verificar si el número ya existe EN EL MISMO EQUIPO
                try (Connection connection = SQLConnection.getConnection();
                     PreparedStatement checkStmt = connection.prepareStatement(
                         "SELECT COUNT(*) FROM Jugador WHERE Numero_Jugador = ? AND IdEquipo = ? AND idJugador != ?")) {
                    
                    checkStmt.setInt(1, Numero_Jugador);
                    checkStmt.setInt(2, equipoActual);
                    checkStmt.setInt(3, idJugador);
                    ResultSet rs = checkStmt.executeQuery();
                    rs.next();
                    
                    if (rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(this, 
                            "Ya existe otro jugador con el número " + Numero_Jugador + " en el mismo equipo", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
            
            // Actualizar solo nombre, número y fecha de nacimiento (NO ciudad ni equipo)
            try (Connection connection = SQLConnection.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(
                 "UPDATE Jugador SET Nombre = ?, Fecha_Nacimiento = ?, Numero_Jugador = ? WHERE idJugador = ?")) {
            
                stmt.setString(1, nombre);
                stmt.setDate(2, new java.sql.Date(fechaNacimiento.getTime()));
                stmt.setInt(3, Numero_Jugador);
                stmt.setInt(4, idJugador);
                
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Jugador actualizado exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                    limpiarFormulario();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo actualizar el jugador", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar jugador: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private int obtenerEquipoActualJugador(int idJugador) {
        try (Connection connection = SQLConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                 "SELECT CAST(IdEquipo AS INT) as IdEquipo FROM Jugador WHERE idJugador = ?")) {

            stmt.setInt(1, idJugador);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("IdEquipo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Si no se encuentra, retorna -1
    }

}
