package inventario_productos.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Conexion {
    public static Connection getConexion() {
        Connection conexion = null;
        Properties props = new Properties();

        try {
            props.load(Conexion.class.getResourceAsStream("/config.properties"));
            String password = props.getProperty("db.password");
            String usuario = props.getProperty("db.usuario");
            String url = props.getProperty("db.url");

            conexion = DriverManager.getConnection(url, usuario, password);
        } catch (Exception e) {
            System.out.println("Error: Base de datos o properties." + e.getMessage());
        }

        return conexion;
    }

    public static void main(String[] args) {
        var conexion = Conexion.getConexion();
        if (conexion != null) {
            System.out.println("Conexion exitosa" + conexion);
        } else {
            System.out.println("fallo al conectar");
        }
    }
}
