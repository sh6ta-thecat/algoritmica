import java.util.*;
import java.text.SimpleDateFormat;

public class ControladorVentas {
    private ArrayList<Producto> inventario;
    private ArrayList<Venta> ventas;
    private ArrayList<String[]> carrito; // Estructura de Pila: [id, nombre, cantidad, precio]
    private int contadorVentas;
    
    public ControladorVentas() {
        this.inventario = Producto.cargarInventario();
        this.ventas = Venta.cargarVentas();
        this.carrito = new ArrayList<>();
        this.contadorVentas = ventas.size() + 1;
        
        // Si no hay inventario, crear uno por defecto
        if (inventario.isEmpty()) {
            crearInventarioInicial();
        }
    }
    
    private void crearInventarioInicial() {
        inventario.add(new Producto("P001", "Aceite Motor", 25.50, 10));
        inventario.add(new Producto("P002", "Filtro Aire", 15.00, 5));
        inventario.add(new Producto("P003", "Bujía", 8.75, 20));
        inventario.add(new Producto("P004", "Pastilla Freno", 45.00, 8));
        Producto.guardarInventario(inventario);
    }
    
    // MOSTRAR INVENTARIO
    public void mostrarInventario() {
        System.out.println("\n=== INVENTARIO PSP AUTOMOTRIZ ===");
        System.out.println("ID\tNombre\t\t\tPrecio\tStock");
        System.out.println("----------------------------------------");
        
        for (Producto producto : inventario) {
            System.out.printf("%s\t%-20s\t%.2f\t%d\n", 
                producto.id, producto.nombre, producto.precio, producto.stock);
        }
    }
    
    // AGREGAR PRODUCTO AL CARRITO (PUSH a la pila)
    public void agregarAlCarrito(String idProducto, int cantidad) {
        Producto producto = Producto.buscarPorId(idProducto, inventario);
        
        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        
        if (producto.stock < cantidad) {
            System.out.println("Stock insuficiente. Stock disponible: " + producto.stock);
            return;
        }
        
        // Agregar al carrito (simulando PUSH)
        String[] item = {
            producto.id,
            producto.nombre,
            String.valueOf(cantidad),
            String.valueOf(producto.precio)
        };
        carrito.add(item);
        
        System.out.println("Producto agregado al carrito: " + producto.nombre + " x " + cantidad);
    }
    
    // DESHACER ÚLTIMO PRODUCTO (POP de la pila)
    public void deshacerUltimoProducto() {
        if (carrito.isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }
        
        // Eliminar el último elemento (POP)
        String[] ultimoProducto = carrito.remove(carrito.size() - 1);
        System.out.println("Producto eliminado: " + ultimoProducto[1] + " x " + ultimoProducto[2]);
    }
    
    // MOSTRAR CARRITO
    public void mostrarCarrito() {
        if (carrito.isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }
        
        System.out.println("\n=== CARRITO DE COMPRAS ===");
        double total = 0;
        
        for (int i = 0; i < carrito.size(); i++) {
            String[] item = carrito.get(i);
            String id = item[0];
            String nombre = item[1];
            int cantidad = Integer.parseInt(item[2]);
            double precio = Double.parseDouble(item[3]);
            double subtotal = cantidad * precio;
            total += subtotal;
            
            System.out.printf("%d. %s - %d x %.2f = %.2f\n", 
                i + 1, nombre, cantidad, precio, subtotal);
        }
        
        System.out.println("TOTAL: " + total);
    }
    
    // REALIZAR VENTA
    public void realizarVenta() {
        if (carrito.isEmpty()) {
            System.out.println("No hay productos en el carrito.");
            return;
        }
        
        // Validar stock antes de proceder
        for (String[] item : carrito) {
            String id = item[0];
            int cantidad = Integer.parseInt(item[2]);
            Producto producto = Producto.buscarPorId(id, inventario);
            
            if (producto.stock < cantidad) {
                System.out.println("Error: Stock insuficiente para " + producto.nombre);
                return;
            }
        }
        
        // Calcular total (Acumulador)
        double total = 0;
        for (String[] item : carrito) {
            int cantidad = Integer.parseInt(item[2]);
            double precio = Double.parseDouble(item[3]);
            total += cantidad * precio;
        }
        
        // Actualizar stock
        for (String[] item : carrito) {
            String id = item[0];
            int cantidad = Integer.parseInt(item[2]);
            Producto producto = Producto.buscarPorId(id, inventario);
            producto.stock -= cantidad;
        }
        
        // Crear venta
        String idVenta = "V" + String.format("%03d", contadorVentas);
        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        
        Venta nuevaVenta = new Venta(idVenta, fecha, new ArrayList<>(carrito), total);
        ventas.add(nuevaVenta);
        contadorVentas++;
        
        // Guardar cambios
        Producto.guardarInventario(inventario);
        Venta.guardarVentas(ventas);
        
        System.out.println("\n=== VENTA REALIZADA ===");
        System.out.println("ID Venta: " + idVenta);
        System.out.println("Fecha: " + fecha);
        System.out.println("Total: " + total);
        
        // Limpiar carrito
        carrito.clear();
    }
    
    // GENERAR REPORTE DE VENTAS
    public void generarReporte() {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        
        System.out.println("\n=== REPORTE DE VENTAS ===");
        double totalGeneral = 0;
        int totalProductos = 0;
        
        for (Venta venta : ventas) {
            System.out.println("\nVenta: " + venta.idVenta + " - Fecha: " + venta.fecha);
            System.out.println("Productos:");
            
            for (String[] producto : venta.productos) {
                System.out.println("  - " + producto[1] + " x " + producto[2] + " = " + producto[3]);
                totalProductos += Integer.parseInt(producto[2]);
            }
            
            System.out.println("Total Venta: " + venta.total);
            totalGeneral += venta.total;
        }
        
        System.out.println("\n=== RESUMEN GENERAL ===");
        System.out.println("Total de ventas: " + ventas.size());
        System.out.println("Total de productos vendidos: " + totalProductos);
        System.out.println("Ingreso total: " + totalGeneral);
    }
}
