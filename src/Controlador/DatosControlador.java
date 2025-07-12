package Controlador;

import DAO.LoginDao;
import Modelo.Config;
import Vista.Sistema;
import javax.swing.JOptionPane;

public class DatosControlador {

    private final Sistema sistema;
    Config conf = new Config();
    LoginDao lgDao = new LoginDao();
        
    public DatosControlador(Sistema sistema) {
        this.sistema = sistema;
        inicializarEventos();
    }

    private void inicializarEventos() {
        sistema.btnActualizarConfig.addActionListener(e -> actualizarConfig());
    }

    private void actualizarConfig() {
        // Verificar que los campos no estén vacíos
        if (!"".equals(sistema.txtRucConfig.getText()) || !"".equals(sistema.txtNombreConfig.getText()) || 
            !"".equals(sistema.txtTelefonoConfig.getText()) || !"".equals(sistema.txtDireccionConfig.getText())) {
            
            // Crear el objeto de configuración con los datos de los campos
            conf.setRuc(sistema.txtRucConfig.getText());
            conf.setNombre(sistema.txtNombreConfig.getText());
            conf.setTelefono(sistema.txtTelefonoConfig.getText());
            conf.setDireccion(sistema.txtDireccionConfig.getText());
            conf.setMensaje(sistema.txtMensaje.getText());
            conf.setId(Integer.parseInt(sistema.txtIdConfig.getText()));

            lgDao.ModificarDatos(conf);

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, "Datos de la empresa modificados");

            // Si deseas llamar a ListarConfig, puedes hacerlo aquí:
            // ListarConfig();
        } else {
            // Si alguno de los campos está vacío
            JOptionPane.showMessageDialog(null, "Los campos están vacíos");
        }
    }
}
