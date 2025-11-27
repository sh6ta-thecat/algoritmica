import java.util.*;

public class Main {
    public static void main(String[] args) {
        ControladorVentas controlador = new ControladorVentas();
        Scanner scanner = new Scanner(System.in);
        int opcion;
        
        System.out.println("=== SISTEMA DE PUNTO DE VENTA - PSP AUTOMOTRIZ ===");
        
        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Ver Inventario");
            System.out.println("2. Agregar Producto al Carrito");
            System.out.println("3. Ver Carrito");
            System.out.println("4. Deshacer Último Producto");
            System.out.println("5. Realizar Venta");
            System.out.println("6. Generar Reporte de Ventas");
            System.out.println("7. Ordenar Inventario por Nombre");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            switch (opcion) {
                case 1:
                    controlador.mostrarInventario();
                    break;
                    
                case 2:
                    System.out.print("Ingrese ID del producto: ");
                    String id = scanner.nextLine();
                    System.out.print("Ingrese cantidad: ");
                    int cantidad = scanner.nextInt();
                    controlador.agregarAlCarrito(id, cantidad);
                    break;
                    
                case 3:
                    controlador.mostrarCarrito();
                    break;
                    
                case 4:
                    controlador.deshacerUltimoProducto();
                    break;
                    
                case 5:
                    controlador.realizarVenta();
                    break;
                    
                case 6:
                    controlador.generarReporte();
                    break;
                    
                case 7:
                    // Ordenar inventario usando el algoritmo de burbuja
                    ArrayList<Producto> inventario = Producto.cargarInventario();
                    Producto.ordenarPorNombre(inventario);
                    Producto.guardarInventario(inventario);
                    System.out.println("Inventario ordenado por nombre.");
                    controlador.mostrarInventario();
                    break;
                    
                case 0:
                    System.out.println("¡Gracias por usar el sistema PSP Automotriz!");
                    break;
                    
                default:
                    System.out.println("Opción no válida.");
            }
            
        } while (opcion != 0);
        
        scanner.close();
    }
}
