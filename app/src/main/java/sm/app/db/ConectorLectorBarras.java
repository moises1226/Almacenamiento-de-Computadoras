package sm.app.db;

import java.util.Scanner;

public class ConectorLectorBarras {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Por favor, escanea el código de barras:");
        String codigoBarras = scanner.nextLine(); // Captura la entrada del lector

        // Muestra el código escaneado
        System.out.println("Código de barras escaneado: " + codigoBarras);

        // Aquí puedes guardar el código de barras en la base de datos, etc.
    }
}

