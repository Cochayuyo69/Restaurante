package Controlador;

import DAO.LoginDao;
import Vista.Sistema;
import Modelo.login;
import javax.swing.JOptionPane;

public class UsuariosControlador {

    private final Sistema sistema;
    LoginDao lgDao = new LoginDao();
    
    public UsuariosControlador(Sistema sistema) {
        this.sistema = sistema;
        inicializarEventos();
    }

    private void inicializarEventos() {
        sistema.btnIniciar.addActionListener(e -> registrarUsuario());
    }

    private void registrarUsuario() {
        if (sistema.txtNombre.getText().equals("") || sistema.txtCorreo.getText().equals("") || 
            sistema.txtPass.getPassword().equals("")) {
            JOptionPane.showMessageDialog(null, "Todos los campos son requeridos");
        } else {
            login lg = new login();
            String correo = sistema.txtCorreo.getText();
            String pass = String.valueOf(sistema.txtPass.getPassword());
            String nom = sistema.txtNombre.getText();
            String rol = sistema.cbxRol.getSelectedItem().toString();

            lg.setNombre(nom);
            lg.setCorreo(correo);
            lg.setPass(pass);
            lg.setRol(rol);

            lgDao.Registrar(lg);

            JOptionPane.showMessageDialog(null, "Usuario Registrado");
        }
    }
}

