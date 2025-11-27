import java.io.*;
import java.util.*;

public class Producto {
    public String id;
    public String nombre;
    public double precio;
    public int stock;
    
    public Producto(String id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }
    
    // Convertir producto a formato texto para guardar
    public String aTexto() {
        return id + "," + nombre + "," + precio + "," + stock;
    }
    
    // Crear producto desde texto (simulando JSON)
    public static Producto desdeTexto(String texto) {
        String[] partes = texto.split(",");
        if (partes.length == 4) {
            String id = partes[0];
            String nombre = partes[1];
            double precio = Double.parseDouble(partes[2]);
            int stock = Integer.parseInt(partes[3]);
            return new Producto(id, nombre, precio, stock);
        }
        return null;
    }
    
    // Cargar inventario desde archivo
    public static ArrayList<Producto> cargarInventario() {
        ArrayList<Producto> inventario = new ArrayList<>();
        try {
            File archivo = new File("inventario.txt");
            if (archivo.exists()) {
                Scanner scanner = new Scanner(archivo);
                while (scanner.hasNextLine()) {
                    String linea = scanner.nextLine();
                    Producto producto = Producto.desdeTexto(linea);
                    if (producto != null) {
                        inventario.add(producto);
                    }
                }
                scanner.close();
            }
        } catch (IOException e) {
            System.out.println("Error al cargar inventario: " + e.getMessage());
        }
        return inventario;
    }
    
    // Guardar inventario en archivo
    public static void guardarInventario(ArrayList<Producto> inventario) {
        try {
            PrintWriter writer = new PrintWriter("inventario.txt");
            for (Producto producto : inventario) {
                writer.println(producto.aTexto());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error al guardar inventario: " + e.getMessage());
        }
    }
    
    // Buscar producto por ID (Búsqueda Lineal)
    public static Producto buscarPorId(String id, ArrayList<Producto> inventario) {
        for (Producto producto : inventario) {
            if (producto.id.equals(id)) {
                return producto;
            }
        }
        return null;
    }
    
    // Ordenar inventario por nombre (Ordenamiento Burbuja)
    public static void ordenarPorNombre(ArrayList<Producto> inventario) {
        for (int i = 0; i < inventario.size() - 1; i++) {
            for (int j = 0; j < inventario.size() - i - 1; j++) {
                if (inventario.get(j).nombre.compareTo(inventario.get(j + 1).nombre) > 0) {
                    // Intercambiar productos
                    Producto temp = inventario.get(j);
                    inventario.set(j, inventario.get(j + 1));
                    inventario.set(j + 1, temp);
                }
            }
        }
    }
}
