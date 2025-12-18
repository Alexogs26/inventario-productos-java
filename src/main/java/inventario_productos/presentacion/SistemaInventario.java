package inventario_productos.presentacion;

import inventario_productos.datos.IProductoDAO;
import inventario_productos.datos.ProductoDAO;
import inventario_productos.dominio.Producto;

import java.math.BigDecimal;
import java.util.Scanner;

public class SistemaInventario {
    public static void main(String[] args) {
        sistemaInventario();
    }

    private static void sistemaInventario() {
        var consola = new Scanner(System.in);
        IProductoDAO productoDAO = new ProductoDAO();
        System.out.println("*** Inventario de Productos ***\n");
        int opcion = 0;
        System.out.println(productoDAO.listarProductos());

        do {
            try {
                opcion = mostrarMenu(consola);
                ejecutarOpcion(opcion, consola, productoDAO);
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese solo numeros" + e.getMessage());
            }
        } while (opcion != 5);

    }

    private static int mostrarMenu(Scanner consola) {
        System.out.print("""
                \nMenu:
                1. Listar Productos
                2. Agregar Productos
                3. Modificar productos
                4. Eliminar productos
                5. Salir del sistema
                Elije una opcion:\s""");
        return Integer.parseInt(consola.nextLine());
    }

    private static void ejecutarOpcion(int opcion, Scanner consola,
                                       IProductoDAO productoDAO) {
        switch (opcion) {
            case 1 -> listarProductos(consola, productoDAO);
            case 2 -> agregarProducto(consola, productoDAO);
            case 3 -> modificarProducto(consola, productoDAO);
            case 4 -> eliminarProducto(consola, productoDAO);
            case 5 -> System.out.println("Saliendo del sistema...");
            default -> System.out.println("Opcion invalida");
        }
    }

    private static void listarProductos(Scanner consola, IProductoDAO productoDAO) {
        System.out.print("""
                \n1. Listar todos los productos
                2. Buscar un producto por sku
                Elije una opcion:\s""");
        int opcion = Integer.parseInt(consola.nextLine());

        switch (opcion) {
            case 1 -> {
                var productos = productoDAO.listarProductos();
                productos.forEach(System.out::println);
            }
            case 2 -> {
                System.out.print("Introduce el sku del producto a buscar: ");
                int sku = Integer.parseInt(consola.nextLine());
                var producto = productoDAO.buscarProductoSku(sku);
                if (producto != null) {
                    System.out.println("Producto encontrado: " + producto);
                } else {
                    System.out.println("Producto con sku " + sku + " no encontrado");
                }
            }
            default -> System.out.println("Opcion invalida");
        }
    }

    private static void agregarProducto(Scanner consola, IProductoDAO productoDAO) {
        System.out.print("Introduce el nombre de el producto: ");
        String nombre = consola.nextLine();
        System.out.print("Introduce el precio de el producto: ");
        BigDecimal precio = new BigDecimal(consola.nextLine());

        Producto producto = new Producto(nombre, precio);

        if (productoDAO.agregarProducto(producto)) {
            System.out.println("Producto agregado: " + producto);
        } else {
            System.out.println("No se pudo agregar el producto");
        }
    }

    private static void modificarProducto(Scanner consola, IProductoDAO productoDAO) {
        System.out.print("Ingresa el sku del producto a modificar: ");
        Integer sku = Integer.parseInt(consola.nextLine());
        System.out.print("Introduce el nombre nuevo: ");
        String nombre = consola.nextLine();
        System.out.print("Introduce el precio nuevo: ");
        BigDecimal precio = new BigDecimal(consola.nextLine());

        Producto producto = new Producto(sku, nombre, precio);

        if (productoDAO.modificarProducto(producto)) {
            System.out.println("Producto modificado: " + producto);
        } else {
            System.out.println("No se pudo modificar el producto");
        }
    }

    private static void eliminarProducto(Scanner consola, IProductoDAO productoDAO) {
        System.out.print("Ingresa el sku del producto que deseas borrar: ");
        Integer sku = Integer.parseInt(consola.nextLine());

        if (productoDAO.eliminarProducto(sku)) {
            System.out.println("Producto eliminado");
        } else {
            System.out.println("No se pudo eliminar el producto");
        }
    }
}
