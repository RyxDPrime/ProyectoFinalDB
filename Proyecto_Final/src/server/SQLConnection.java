package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class SQLConnection {
    private static Connection cn;
    
    public static Connection getConnection() {
        try {
            // Verificar si ya existe una conexión válida
            if (cn != null && !cn.isClosed()) {
                return cn;
            }
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            // URL corregida - nota el cambio de "integratedSecurity" (había un typo)
            String url = "jdbc:sqlserver://localhost:1433;"
                       + "databaseName=Torneo(YXYK0001);"
                       + "integratedSecurity=true;"
                       + "encrypt=true;"
                       + "trustServerCertificate=true;";
            
            cn = DriverManager.getConnection(url);
            
            System.out.println("Conexión establecida correctamente");
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, 
                "Error: No se encontró el driver JDBC para SQL Server.\n"
                + "Asegúrate de tener el archivo mssql-jdbc.jar en tu classpath.",
                "Error de Driver", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al conectar con SQL Server:\n" + e.getMessage(),
                "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error inesperado: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        return cn;
    }
}