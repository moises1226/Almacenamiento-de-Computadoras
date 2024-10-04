package sm.app.controller.conectorBarras;

import java.util.Scanner;

public class ConectorLectorBarras implements Runnable {
    private OnBarcodeReadListener listener;

    // Interface para notificar cuando se lee un código de barras
    public interface OnBarcodeReadListener {
        void onBarcodeRead(String codigoBarras);
    }

    // Constructor para recibir el listener
    public ConectorLectorBarras(OnBarcodeReadListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String codigoBarras = scanner.nextLine();  // Captura el código de barras
            if (listener != null) {
                listener.onBarcodeRead(codigoBarras);  // Notifica al controlador
            }
        }
    }
}
