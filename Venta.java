import java.io.*;
import java.util.*;

public class Venta {
    public String idVenta;
    public String fecha;
    public ArrayList<String[]> productos; // [id, nombre, cantidad, precio]
    public double total;
    
    public Venta(String idVenta, String fecha, ArrayList<String[]> productos, double total) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.productos = productos;
        this.total = total;
    }
    
    // Convertir venta a formato texto para guardar
    public String aTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append(idVenta).append("|").append(fecha).append("|").append(total).append("|");
        
        for (String[] producto : productos) {
            sb.append(producto[0]).append(",").append(producto[1]).append(",")
              .append(producto[2]).append(",").append(producto[3]).append(";");
        }
        
        return sb.toString();
    }
    
    // Crear venta desde texto
    public static Venta desdeTexto(String texto) {
        String[] partes = texto.split("\\|");
        if (partes.length >= 4) {
            String idVenta = partes[0];
            String fecha = partes[1];
            double total = Double.parseDouble(partes[2]);
            
            ArrayList<String[]> productos = new ArrayList<>();
            String[] productosTexto = partes[3].split(";");
            
            for (String prodTexto : productosTexto) {
                if (!prodTexto.isEmpty()) {
                    String[] prodPartes = prodTexto.split(",");
                    if (prodPartes.length == 4) {
                        productos.add(prodPartes);
                    }
                }
            }
            
            return new Venta(idVenta, fecha, productos, total);
        }
        return null;
    }
    
    // Cargar ventas desde archivo
    public static ArrayList<Venta> cargarVentas() {
        ArrayList<Venta> ventas = new ArrayList<>();
        try {
            File archivo = new File("ventas.txt");
            if (archivo.exists()) {
                Scanner scanner = new Scanner(archivo);
                while (scanner.hasNextLine()) {
                    String linea = scanner.nextLine();
                    Venta venta = Venta.desdeTexto(linea);
                    if (venta != null) {
                        ventas.add(venta);
                    }
                }
                scanner.close();
            }
        } catch (IOException e) {
            System.out.println("Error al cargar ventas: " + e.getMessage());
        }
        return ventas;
    }
    
    // Guardar ventas en archivo
    public static void guardarVentas(ArrayList<Venta> ventas) {
        try {
            PrintWriter writer = new PrintWriter("ventas.txt");
            for (Venta venta : ventas) {
                writer.println(venta.aTexto());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error al guardar ventas: " + e.getMessage());
        }
    }
}
