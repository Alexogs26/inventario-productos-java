package inventario_productos.datos;

import inventario_productos.dominio.Producto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static inventario_productos.conexion.Conexion.getConexion;


public class ProductoDAO implements IProductoDAO {

    public static void main(String[] args) {
        // Listar clientes
        IProductoDAO productoDAO = new ProductoDAO();
        var productos = productoDAO.listarProductos();
        productos.forEach(System.out::println);

        // Buscar por sku
        int skuBuscado = 1;
        Producto producto = productoDAO.buscarProductoSku(skuBuscado);

        if (producto != null) {
            System.out.println("Encontrado: " + producto);
        } else {
            System.out.println("Producto con sku: " + skuBuscado + " no se encontro");
        }

        // Agregar un producto
        Producto producto2 = new Producto("Sensor DSC", new BigDecimal("399.00"));

        // productoDAO.agregarProducto(producto2);

        // Modificar un producto
        Producto producto1 = new Producto(2, "Camara 2MPX DAHUA", new BigDecimal("599.00"));
        // productoDAO.modificarProducto(producto1);

        // Eliminar un Producto
        productoDAO.eliminarProducto(2);
    }

    @Override
    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();
        var sql = "SELECT * FROM producto ORDER BY sku;";

        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                var producto = new Producto();
                producto.setSku(rs.getInt("sku"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getBigDecimal("precio"));
                productos.add(producto);
            }
        } catch (Exception e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        return productos;
    }


    @Override
    public Producto buscarProductoSku(int sku) {
        var sql = "SELECT * FROM producto WHERE sku = ?";
        Producto productoEncontrado = null;

        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sku);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    BigDecimal precio = rs.getBigDecimal("precio");

                    productoEncontrado = new Producto(sku, nombre, precio);
                }
            }

        } catch (Exception e) {
            System.out.println("Error al recuperar producto por sku: " + e.getMessage());
        }

        return productoEncontrado;
    }

    @Override
    public boolean agregarProducto(Producto producto) {
        var sql = "INSERT INTO producto(nombre, precio) VALUES(?, ?)";

        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setBigDecimal(2,producto.getPrecio());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean modificarProducto(Producto producto) {
        String sql = "UPDATE producto SET nombre=?, precio=? WHERE sku = ?";

        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setBigDecimal(2, producto.getPrecio());
            ps.setInt(3, producto.getSku());
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (Exception e) {
            System.out.println("Error al modificar el producto: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean eliminarProducto(int sku) {
        String sql = "DELETE FROM producto WHERE sku = ?";

        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, sku);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (Exception e) {
            System.out.println("Error al eliminar el producto " + e.getMessage());
        }

        return false;
    }
}
