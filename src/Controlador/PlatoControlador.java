package Controlador;

import Modelo.Eventos;
import Vista.Sistema;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class PlatoControlador {

    private final Sistema sistema;
    private DefaultTableModel tmp;
    private int item = 0;
    Eventos event = new Eventos();

    public PlatoControlador(Sistema sistema) {
        this.sistema = sistema;
        inicializarEventos();
    }

    private void inicializarEventos() {
        sistema.jButton2.addActionListener(evt -> agregarComentario());
        sistema.btnEliminarTempPlato.addActionListener(evt -> eliminarPlatoTemp());
        sistema.btnAddPlato.addActionListener(evt -> agregarPlato());
        sistema.btnGenerarPedido.addActionListener(evt -> generarPedido());
        sistema.txtBuscarPlato.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarPlatoKeyReleased(evt);
            }
        });
    }

    private void txtBuscarPlatoKeyReleased(java.awt.event.KeyEvent evt) {
        event.numberDecimalKeyPress(evt, sistema.txtPrecioPlato);
    }
        
    private void agregarComentario() {
        if (sistema.txtComentario.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
        } else if (sistema.tableMenu.getSelectedRow() >= 0) {
            int id = Integer.parseInt(sistema.tableMenu.getValueAt(sistema.tableMenu.getSelectedRow(), 0).toString());
            for (int i = 0; i < sistema.tableMenu.getRowCount(); i++) {
                if (sistema.tableMenu.getValueAt(i, 0).equals(id)) {
                    tmp = (DefaultTableModel) sistema.tableMenu.getModel();
                    tmp.setValueAt(sistema.txtComentario.getText(), i, 5);
                    sistema.txtComentario.setText("");
                    sistema.tableMenu.clearSelection();
                    return;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "NO HAY UNA FILA SELECCIONADA");
        }
    }

    private void eliminarPlatoTemp() {
        if (sistema.tableMenu.getSelectedRow() >= 0) {
            DefaultTableModel modelo = (DefaultTableModel) sistema.tableMenu.getModel();
            modelo.removeRow(sistema.tableMenu.getSelectedRow());
            totalPagar(sistema.tableMenu, sistema.totalMenu);
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UN PLATO A ELIMINAR");
        }
    }

    private void agregarPlato() {
        if (sistema.tblTemPlatos.getSelectedRow() >= 0) {
            int id = Integer.parseInt(sistema.tblTemPlatos.getValueAt(sistema.tblTemPlatos.getSelectedRow(), 0).toString());
            String descripcion = sistema.tblTemPlatos.getValueAt(sistema.tblTemPlatos.getSelectedRow(), 1).toString();
            double precio = Double.parseDouble(sistema.tblTemPlatos.getValueAt(sistema.tblTemPlatos.getSelectedRow(), 2).toString());
            double total = 1 * precio;

            tmp = (DefaultTableModel) sistema.tableMenu.getModel();
            for (int i = 0; i < sistema.tableMenu.getRowCount(); i++) {
                if (sistema.tableMenu.getValueAt(i, 0).equals(id)) {
                    int cantActual = Integer.parseInt(sistema.tableMenu.getValueAt(i, 2).toString());
                    int nuevaCantidad = cantActual + 1;
                    double nuevoSubTotal = precio * nuevaCantidad;
                    tmp.setValueAt(nuevaCantidad, i, 2);
                    tmp.setValueAt(nuevoSubTotal, i, 4);
                    totalPagar(sistema.tableMenu, sistema.totalMenu);
                    return;
                }
            }

            item++;
            ArrayList<Object> lista = new ArrayList<>();
            lista.add(id); // ID
            lista.add(descripcion); // Descripción
            lista.add(1); // Cantidad
            lista.add(precio); // Precio
            lista.add(total); // Total
            lista.add(""); // Comentario

            Object[] fila = new Object[6];
            fila[0] = lista.get(0);
            fila[1] = lista.get(1);
            fila[2] = lista.get(2);
            fila[3] = lista.get(3);
            fila[4] = lista.get(4);
            fila[5] = lista.get(5);

            tmp.addRow(fila);
            sistema.tableMenu.setModel(tmp);
            totalPagar(sistema.tableMenu, sistema.totalMenu);
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONA UNA FILA");
        }
    }

    private void generarPedido() {
        ControladorPrincipal con = new ControladorPrincipal(sistema);
        if (sistema.tableMenu.getRowCount() > 0) {
            con.RegistrarPedido();
            con.detallePedido();
            limpiarTableMenu();
            JOptionPane.showMessageDialog(null, "PEDIDO REGISTRADO");
            sistema.jTabbedPane1.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(null, "NO HAY PRODUCTOS EN EL PEDIDO");
        }
    }
    
    private void limpiarTableMenu() {
        DefaultTableModel modelo = (DefaultTableModel) sistema.tableMenu.getModel();
        modelo.setRowCount(0);
    }

    private void totalPagar(JTable table, javax.swing.JLabel labelTotal) {
        double total = 0.0;
        for (int i = 0; i < table.getRowCount(); i++) {
            total += Double.parseDouble(table.getValueAt(i, 4).toString());
        }
        labelTotal.setText(String.format("%.2f", total));
    }
}