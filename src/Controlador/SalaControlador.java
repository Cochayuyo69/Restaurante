package Controlador;

import Modelo.Salas;
import DAO.SalasDao;
import Vista.Sistema;
import javax.swing.JOptionPane;

public class SalaControlador {

    private final Sistema sistema;
    private final Salas sl;
    private final SalasDao slDao = new SalasDao();
    
    public SalaControlador(Sistema sistema) {
        this.sistema = sistema;
        this.sl = new Salas();
        inicializarEventos();
    }

    private void inicializarEventos() {
        sistema.btnRegistrarSala.addActionListener(evt -> registrarSala());
        sistema.btnActualizarSala.addActionListener(evt -> actualizarSala());
        sistema.btnEliminarSala.addActionListener(evt -> eliminarSala());
        sistema.btnNuevoSala.addActionListener(evt -> limpiarSala());
        sistema.tableSala.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDetallesPedido(evt);
            }
        });
        
        
    }
    
    private void verDetallesPedido(java.awt.event.MouseEvent evt) {
        int fila = sistema.tableSala.rowAtPoint(evt.getPoint());
        sistema.txtIdSala.setText(sistema.tableSala.getValueAt(fila, 0).toString());
        sistema.txtNombreSala.setText(sistema.tableSala.getValueAt(fila, 1).toString());
        sistema.txtMesas.setText(sistema.tableSala.getValueAt(fila, 2).toString());
    }
    
    private void registrarSala() {
        ControladorPrincipal con = new ControladorPrincipal(sistema);
        if (sistema.txtNombreSala.getText().equals("") || sistema.txtMesas.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Los campos están vacíos");
        } else {
            sl.setNombre(sistema.txtNombreSala.getText());
            sl.setMesas(Integer.parseInt(sistema.txtMesas.getText()));
            slDao.RegistrarSala(sl);
            JOptionPane.showMessageDialog(null, "Sala registrada");
            limpiarSala();
            con.LimpiarTable();
            con.ListarSalas();
        }
    }

    private void actualizarSala() {
        ControladorPrincipal con = new ControladorPrincipal(sistema);
        if (sistema.txtIdSala.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        } else {
            if (!sistema.txtNombreSala.getText().equals("")) {
                sl.setNombre(sistema.txtNombreSala.getText());
                sl.setId(Integer.parseInt(sistema.txtIdSala.getText()));
                sl.setMesas(Integer.parseInt(sistema.txtMesas.getText()));
                slDao.Modificar(sl);
                JOptionPane.showMessageDialog(null, "Sala modificada");
                limpiarSala();
                con.LimpiarTable();
                con.ListarSalas();
            }
        }
    }

    private void eliminarSala() {
        ControladorPrincipal con = new ControladorPrincipal(sistema);
        if (!sistema.txtIdSala.getText().equals("")) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar?");
            if (pregunta == 0) {
                int id = Integer.parseInt(sistema.txtIdSala.getText());
                slDao.Eliminar(id);
                limpiarSala();
                con.LimpiarTable();
                con.ListarSalas();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }

    private void limpiarSala() {
        sistema.txtIdSala.setText("");
        sistema.txtNombreSala.setText("");
        sistema.txtMesas.setText("");
    }
}
