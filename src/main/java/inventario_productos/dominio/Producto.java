package inventario_productos.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Producto implements Serializable {
    private int sku;
    private String nombre;
    private BigDecimal precio;

    public Producto() {}

    public Producto(int sku) {
        this.sku = sku;
    }

    public Producto(String nombre, BigDecimal precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto(int sku, String nombre, BigDecimal precio) {
        this(nombre, precio);
        this.sku = sku;
    }

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "sku=" + sku +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return sku == producto.sku && Objects.equals(nombre, producto.nombre) && Objects.equals(precio, producto.precio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku, nombre, precio);
    }
}
