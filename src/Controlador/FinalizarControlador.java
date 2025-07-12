package Controlador;

import DAO.PedidosDao;
import Vista.Sistema;
import javax.swing.JOptionPane;

public class FinalizarControlador {

    private final Sistema sistema;
    PedidosDao pedDao = new PedidosDao();
    public FinalizarControlador(Sistema sistema) {
        this.sistema = sistema;
        inicializarEventos();
    }

    private void inicializarEventos() {
        sistema.btnPdfPedido.addActionListener(evt -> generarPdfPedido());
        sistema.btnFinalizar.addActionListener(evt -> finalizarPedido());
    }

    private void generarPdfPedido() {
        if (sistema.txtIdHistorialPedido.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        } else {
            int idHistorialPedido = Integer.parseInt(sistema.txtIdHistorialPedido.getText());
            pedDao.pdfPedido(idHistorialPedido);
            sistema.txtIdHistorialPedido.setText("");
        }
    }

    private void finalizarPedido() {
        ControladorPrincipal con = new ControladorPrincipal(sistema);
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de finalizar?");
        if (confirmacion == 0) {
            int idPedido = Integer.parseInt(sistema.txtIdPedido.getText());
            if (pedDao.actualizarEstado(idPedido)) {
                pedDao.pdfPedido(idPedido);
                sistema.jTabbedPane1.setSelectedIndex(0);
                sistema.PanelSalas.removeAll();
                con.panelSalas();
                sistema.totalMenu.setText("0");
            }
        }
    }
}
