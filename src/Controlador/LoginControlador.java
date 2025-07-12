package Controlador;

import Vista.FrmLogin;
import DAO.LoginDao;
import Modelo.login;
import Vista.Sistema;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginControlador {

    private final FrmLogin frmLogin;
    private Timer tiempo;
    private int contador = -1;
    private final int segundos = 30;
    login lg = new login();
    LoginDao login = new LoginDao();

    public LoginControlador(FrmLogin frmLogin) {
        this.frmLogin = frmLogin;
        inicializarEventos();
    }

    private void inicializarEventos() {
        frmLogin.btnIniciar.addActionListener(e -> validar());
        frmLogin.jButton1.addActionListener(e -> salir());
    }

    private void validar() {
        String correo = frmLogin.txtCorreo.getText();
        String pass = String.valueOf(frmLogin.txtPass.getPassword());
        if (!"".equals(correo) || !"".equals(pass)) {
            
            lg = login.log(correo, pass);
            if (lg.getCorreo()!= null && lg.getPass() != null) {
                frmLogin.barra.setVisible(true);
                contador = -1;
                frmLogin.barra.setValue(0);
                frmLogin.barra.setStringPainted(true);
                tiempo = new Timer(segundos, new BarraProgreso());
                tiempo.start();
            }else{
                JOptionPane.showMessageDialog(null, "Correo o la Contraseña incorrecta");
            }
        }
    }

    private void salir() {
        System.exit(0);
    }

    private void iniciarBarra() {
        tiempo = new Timer(segundos, new BarraProgreso());
        tiempo.start();
    }

    // Clase para el manejo del progreso de la barra
    public class BarraProgreso implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            contador++;
            frmLogin.barra.setValue(contador);
            if (contador == 100) {
                tiempo.stop();
                if (frmLogin.barra.getValue() == 100) {
                    Sistema sis = new Sistema(lg);
                    sis.setVisible(true);
                    frmLogin.dispose();
                }
            }
        }
    }
}
