package inventario_productos.datos;

import inventario_productos.dominio.Producto;

import java.util.List;

public interface IProductoDAO {
    List<Producto> listarProductos();

    Producto buscarProductoSku(int sku);

    boolean agregarProducto(Producto producto);

    boolean modificarProducto(Producto producto);

    boolean eliminarProducto(int sku);
}
