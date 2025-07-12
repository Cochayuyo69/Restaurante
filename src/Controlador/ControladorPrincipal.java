package Controlador;

import DAO.LoginDao;
import DAO.PedidosDao;
import DAO.PlatosDao;
import DAO.SalasDao;
import Modelo.Config;
import Modelo.DetallePedido;
import Modelo.Pedidos;
import Modelo.Platos;
import Modelo.Salas;
import Modelo.Tables;
import Modelo.login;
import Vista.Sistema;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ControladorPrincipal {

    private final Sistema sistema;
    SalasDao slDao = new SalasDao();
    Config conf = new Config();

    PlatosDao plaDao = new PlatosDao();

    Pedidos ped = new Pedidos();
    PedidosDao pedDao = new PedidosDao();
    DetallePedido detPedido = new DetallePedido();

    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp = new DefaultTableModel();

    LoginDao lgDao = new LoginDao();
    double Totalpagar = 0.00;


    public ControladorPrincipal(Sistema sistema) {
        this.sistema = sistema;
        inicializarEventos();
    }

    private void inicializarEventos() {
        ImageIcon img = new ImageIcon(getClass().getResource("/Img/logo.png"));
        Image igmEscalada = img.getImage().getScaledInstance(sistema.labelLogo.getWidth(), sistema.labelLogo.getHeight(), Image.SCALE_SMOOTH);
        Icon icono = new ImageIcon(igmEscalada);
        sistema.labelLogo.setIcon(icono);
        sistema.setIconImage(img.getImage());
        sistema.setLocationRelativeTo(null);
        sistema.txtIdHistorialPedido.setVisible(false);
        sistema.txtIdConfig.setVisible(false);
        sistema.txtIdConfig.setVisible(false);
        sistema.txtIdHistorialPedido.setVisible(false);
        sistema.txtIdPedido.setVisible(false);
        sistema.txtIdPlato.setVisible(false);
        sistema.txtIdSala.setVisible(false);
        sistema.txtTempIdSala.setVisible(false);
        sistema.txtTempNumMesa.setVisible(false);
        sistema.jTabbedPane1.setEnabled(false);
        
        
        ListarPlatos(sistema.TablePlatos);
        LimpiarPlatos();
        panelSalas();
        
        sistema.labelLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelLogoClicked(evt);
            }
        });

        sistema.btnSala.addActionListener(evt -> btnSalaActionPerformed(evt));
        sistema.btnVentas.addActionListener(evt -> btnVentasActionPerformed(evt));
        sistema.btnConfig.addActionListener(evt -> btnConfigActionPerformed(evt));
        sistema.btnUsuarios.addActionListener(evt -> btnUsuariosActionPerformed(evt));
        sistema.btnPlatos.addActionListener(evt -> btnPlatosActionPerformed(evt));
    }

    private void labelLogoClicked(java.awt.event.MouseEvent evt) {
        JTabbedPane tabbedPane = sistema.jTabbedPane1;
        tabbedPane.setSelectedIndex(0);
        JPanel panelSalas = sistema.PanelSalas;
        panelSalas.removeAll();
        panelSalas.repaint();
        panelSalas.revalidate();
        panelSalas();
    }

    private void btnSalaActionPerformed(java.awt.event.ActionEvent evt) {
        LimpiarTable();
        ListarSalas();
        sistema.jTabbedPane1.setSelectedIndex(1);
    }

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {
        LimpiarTable();
        ListarPedidos();
        sistema.jTabbedPane1.setSelectedIndex(5);
    }

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {
        sistema.jTabbedPane1.setSelectedIndex(6);
        ListarConfig();
    }

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {
        LimpiarTable();
        ListarUsuarios();
        sistema.jTabbedPane1.setSelectedIndex(7);
    }

    private void btnPlatosActionPerformed(java.awt.event.ActionEvent evt) {
        sistema.jTabbedPane1.setSelectedIndex(8);
        LimpiarTable();
        ListarPlatos(sistema.TablePlatos);
    }
    
        public void TotalPagar(JTable tabla, JLabel label) {
        Totalpagar = 0.00;
        int numFila = tabla.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(tabla.getModel().getValueAt(i, 4)));
            Totalpagar += cal;
        }
        label.setText(String.format("%.2f", Totalpagar));
    }

    public void LimpiarTableMenu() {
        tmp = (DefaultTableModel) sistema.tableMenu.getModel();
        int fila = sistema.tableMenu.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }

    public void ListarConfig() {
        conf = lgDao.datosEmpresa();
        sistema.txtIdConfig.setText("" + conf.getId());
        sistema.txtRucConfig.setText("" + conf.getRuc());
        sistema.txtNombreConfig.setText("" + conf.getNombre());
        sistema.txtTelefonoConfig.setText("" + conf.getTelefono());
        sistema.txtDireccionConfig.setText("" + conf.getDireccion());
        sistema.txtMensaje.setText("" + conf.getMensaje());
    }

    public void ListarPedidos() {
        Tables color = new Tables();
        List<Pedidos> Listar = pedDao.listarPedidos();
        modelo = (DefaultTableModel) sistema.TablePedidos.getModel();
        Object[] ob = new Object[7];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getSala();
            ob[2] = Listar.get(i).getUsuario();
            ob[3] = Listar.get(i).getNum_mesa();
            ob[4] = Listar.get(i).getFecha();
            ob[5] = Listar.get(i).getTotal();
            ob[6] = Listar.get(i).getEstado();
            modelo.addRow(ob);
        }
        colorHeader(sistema.TablePedidos);
        sistema.TablePedidos.setDefaultRenderer(Object.class, color);
    }

    public void LimpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    public void ListarUsuarios() {
        List<login> Listar = lgDao.ListarUsuarios();
        modelo = (DefaultTableModel) sistema.TableUsuarios.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre();
            ob[2] = Listar.get(i).getCorreo();
            ob[3] = Listar.get(i).getRol();
            modelo.addRow(ob);
        }
        colorHeader(sistema.TableUsuarios);
    }

    public void ListarSalas() {
        List<Salas> Listar = slDao.Listar();
        modelo = (DefaultTableModel) sistema.tableSala.getModel();
        Object[] ob = new Object[3];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre();
            ob[2] = Listar.get(i).getMesas();
            modelo.addRow(ob);
        }
        colorHeader(sistema.tableSala);

    }

    public void colorHeader(JTable tabla) {
        tabla.setModel(modelo);
        JTableHeader header = tabla.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(0, 110, 255));
        header.setForeground(Color.white);
    }

    public void LimpiarSala() {
        sistema.txtIdSala.setText("");
        sistema.txtNombreSala.setText("");
        sistema.txtMesas.setText("");
    }

    public void LimpiarPlatos() {
        sistema.txtIdPlato.setText("");
        sistema.txtNombrePlato.setText("");
        sistema.txtPrecioPlato.setText("");
    }
    
        // platos
    public void ListarPlatos(JTable tabla) {
        List<Platos> Listar = plaDao.Listar(sistema.txtBuscarPlato.getText());
        modelo = (DefaultTableModel) tabla.getModel();
        Object[] ob = new Object[3];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre();
            ob[2] = Listar.get(i).getPrecio();
            modelo.addRow(ob);
        }
        colorHeader(tabla);
    }
    
    public void panelSalas() {
        List<Salas> Listar = slDao.Listar();
        for (int i = 0; i < Listar.size(); i++) {
            int id = Listar.get(i).getId();
            int cantidad = Listar.get(i).getMesas();
            JButton boton = new JButton(Listar.get(i).getNombre(), new ImageIcon(getClass().getResource("/Img/salas.png")));
            boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            boton.setHorizontalTextPosition(JButton.CENTER);
            boton.setVerticalTextPosition(JButton.BOTTOM);
            boton.setBackground(new Color(204, 204, 204));
            sistema.PanelSalas.add(boton);
            boton.addActionListener((ActionEvent e) -> {
                LimpiarTable();
                sistema.PanelMesas.removeAll();
                panelMesas(id, cantidad);
                sistema.jTabbedPane1.setSelectedIndex(2);
            });
        }
    }

    //crear mesas
    public void panelMesas(int id_sala, int cant) {
        for (int i = 1; i <= cant; i++) {
            int num_mesa = i;
            //verificar estado
            JButton boton = new JButton("MESA N°: " + i, new ImageIcon(getClass().getResource("/Img/mesa.png")));
            boton.setHorizontalTextPosition(JButton.CENTER);
            boton.setVerticalTextPosition(JButton.BOTTOM);
            int verificar = pedDao.verificarStado(num_mesa, id_sala);
            if (verificar > 0) {
                boton.setBackground(new Color(255, 51, 51));
            } else {
                boton.setBackground(new Color(0, 102, 102));
            }
            boton.setForeground(Color.WHITE);
            boton.setFocusable(false);
            boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            sistema.PanelMesas.add(boton);
            boton.addActionListener((ActionEvent e) -> {
                if (verificar > 0) {
                    LimpiarTable();
                    verPedido(verificar);
                    verPedidoDetalle(verificar);
                    sistema.btnFinalizar.setEnabled(true);
                    sistema.btnPdfPedido.setEnabled(false);
                    sistema.jTabbedPane1.setSelectedIndex(4);
                } else {
                    LimpiarTable();
                    ListarPlatos(sistema.tblTemPlatos);
                    sistema.jTabbedPane1.setSelectedIndex(3);
                    sistema.txtTempIdSala.setText("" + id_sala);
                    sistema.txtTempNumMesa.setText("" + num_mesa);
                    if (sistema.tableMenu.getRowCount() == 0){
                        sistema.totalMenu.setText("0.00");
                    }
                }
            });
        }
    }
    
    //registrar pedido
    public void RegistrarPedido() {
        int id_sala = Integer.parseInt(sistema.txtTempIdSala.getText());
        int num_mesa = Integer.parseInt(sistema.txtTempNumMesa.getText());
        double monto = Totalpagar;
        ped.setId_sala(id_sala);
        ped.setNum_mesa(num_mesa);
        ped.setTotal(monto);
        ped.setUsuario(sistema.LabelVendedor.getText());
        pedDao.RegistrarPedido(ped);
    }

    public void detallePedido() {
        int id = pedDao.IdPedido();
        for (int i = 0; i < sistema.tableMenu.getRowCount(); i++) {
            String nombre = sistema.tableMenu.getValueAt(i, 1).toString();
            int cant = Integer.parseInt(sistema.tableMenu.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(sistema.tableMenu.getValueAt(i, 3).toString());
            detPedido.setNombre(nombre);
            detPedido.setCantidad(cant);
            detPedido.setPrecio(precio);
            detPedido.setComentario(sistema.tableMenu.getValueAt(i, 5).toString());
            detPedido.setId_pedido(id);
            pedDao.RegistrarDetalle(detPedido);

        }
    }

    public void verPedidoDetalle(int id_pedido) {
        List<DetallePedido> Listar = pedDao.verPedidoDetalle(id_pedido);
        modelo = (DefaultTableModel) sistema.tableFinalizar.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre();
            ob[2] = Listar.get(i).getCantidad();
            ob[3] = Listar.get(i).getPrecio();
            ob[4] = Listar.get(i).getCantidad() * Listar.get(i).getPrecio();
            ob[5] = Listar.get(i).getComentario();
            modelo.addRow(ob);
        }
        colorHeader(sistema.tableFinalizar);
    }

    public void verPedido(int id_pedido) {
        ped = pedDao.verPedido(id_pedido);
        sistema.totalFinalizar.setText("" + ped.getTotal());
        sistema.txtFechaHora.setText("" + ped.getFecha());
        sistema.txtSalaFinalizar.setText("" + ped.getSala());
        sistema.txtNumMesaFinalizar.setText("" + ped.getNum_mesa());
        sistema.txtIdPedido.setText("" + ped.getId());
    }
}
