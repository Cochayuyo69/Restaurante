package Controlador;

import DAO.PlatosDao;
import Vista.Sistema;
import Modelo.Platos;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PlatosControlador {

    private final Sistema sistema;
    Platos pla = new Platos();
    PlatosDao plaDao = new PlatosDao();
    Date fechaActual = new Date();
    String fechaFormato = new SimpleDateFormat("yyyy-MM-dd").format(fechaActual);
    
    public PlatosControlador(Sistema sistema) {
        this.sistema = sistema;
        inicializarEventos();
    }

    private void inicializarEventos() {
        sistema.btnGuardarPlato.addActionListener(e -> guardarPlato());
        sistema.btnEditarPlato.addActionListener(e -> editarPlato());
        sistema.btnEliminarPlato.addActionListener(e -> eliminarPlato());
        sistema.TablePlatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePlatosMouseClicked(evt);
            }
        });
        sistema.txtPrecioPlato.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPrecioPlatoKeyReleased(evt);
            }
        });
    }
    
    private void txtPrecioPlatoKeyReleased(java.awt.event.KeyEvent evt) {
        ControladorPrincipal con = new ControladorPrincipal(sistema);
        con.LimpiarTable();
        con.ListarPlatos(sistema.tblTemPlatos);
    }
        
    private void guardarPlato() {
        if (!"".equals(sistema.txtNombrePlato.getText()) && !"".equals(sistema.txtPrecioPlato.getText())) {
            pla.setNombre(sistema.txtNombrePlato.getText());
            pla.setPrecio(Double.parseDouble(sistema.txtPrecioPlato.getText()));
            pla.setFecha(fechaFormato);
            ControladorPrincipal con = new ControladorPrincipal(sistema);

            // Registrar el plato en la base de datos
            if (plaDao.Registrar(pla)) {
                JOptionPane.showMessageDialog(null, "Plato Registrado");
                LimpiarTable();
                con.ListarPlatos(sistema.TablePlatos);
                LimpiarPlatos();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Los campos están vacíos");
        }
    }

    private void editarPlato() {
        if ("".equals(sistema.txtIdPlato.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        } else {
            if (!"".equals(sistema.txtNombrePlato.getText()) && !"".equals(sistema.txtPrecioPlato.getText())) {
                pla.setNombre(sistema.txtNombrePlato.getText());
                pla.setPrecio(Double.parseDouble(sistema.txtPrecioPlato.getText()));
                pla.setId(Integer.parseInt(sistema.txtIdPlato.getText()));
                ControladorPrincipal con = new ControladorPrincipal(sistema);
                
                // Modificar el plato en la base de datos
                if (plaDao.Modificar(pla)) {
                    JOptionPane.showMessageDialog(null, "Plato Modificado");
                    LimpiarTable();
                    con.ListarPlatos(sistema.TablePlatos);
                    LimpiarPlatos();
                }
            }
        }
    }

    private void eliminarPlato() {
        ControladorPrincipal con = new ControladorPrincipal(sistema);
        if (!"".equals(sistema.txtIdPlato.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar?");
            if (pregunta == 0) {
                int id = Integer.parseInt(sistema.txtIdPlato.getText());
                plaDao.Eliminar(id);
                LimpiarTable();
                LimpiarPlatos();
                con.ListarPlatos(sistema.TablePlatos);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }

    private void tablePlatosMouseClicked(java.awt.event.MouseEvent evt) {
        int fila = sistema.TablePlatos.rowAtPoint(evt.getPoint());
        sistema.txtIdPlato.setText(sistema.TablePlatos.getValueAt(fila, 0).toString());
        sistema.txtNombrePlato.setText(sistema.TablePlatos.getValueAt(fila, 1).toString());
        sistema.txtPrecioPlato.setText(sistema.TablePlatos.getValueAt(fila, 2).toString());
    }

    private void LimpiarTable() {
        DefaultTableModel model = (DefaultTableModel) sistema.TablePlatos.getModel();
        model.setRowCount(0);
    }

    private void LimpiarPlatos() {
        sistema.txtIdPlato.setText("");
        sistema.txtNombrePlato.setText("");
        sistema.txtPrecioPlato.setText("");
    }
}
