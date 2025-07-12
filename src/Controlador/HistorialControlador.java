package Controlador;

import Vista.Sistema;

public class HistorialControlador {

    private final Sistema sistema;

    public HistorialControlador(Sistema sistema) {
        this.sistema = sistema;
        inicializarEventos();
    }

    private void inicializarEventos() {
        sistema.TablePedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDetallesPedido(evt);
            }
        });
    }

    private void verDetallesPedido(java.awt.event.MouseEvent evt) {
        ControladorPrincipal con = new ControladorPrincipal(sistema);
        int fila = sistema.TablePedidos.rowAtPoint(evt.getPoint());
        int id_pedido = Integer.parseInt(sistema.TablePedidos.getValueAt(fila, 0).toString());
        
        con.LimpiarTable();
        con.verPedido(id_pedido);
        con.verPedidoDetalle(id_pedido);
        
        sistema.jTabbedPane1.setSelectedIndex(4);
        
        sistema.btnFinalizar.setEnabled(false);
        
        sistema.txtIdHistorialPedido.setText(String.valueOf(id_pedido));
    }
}
