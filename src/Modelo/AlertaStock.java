/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.*;
import javax.swing.JOptionPane;
public class AlertaStock {
    public static void mostrarProductosPorAgotarse() {
        Connection con = Conexion.getConnection();
        String sql = "SELECT nombre, stock FROM Productos WHERE stock <= 10";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            StringBuilder mensaje = new StringBuilder("Productos próximos a agotarse:\n\n");
            boolean hayProductos = false;

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int stock = rs.getInt("stock");
                mensaje.append("- ").append(nombre).append(" → Stock: ").append(stock).append("\n");
                hayProductos = true;
            }

            con.close();

            if (hayProductos) {
                JOptionPane.showMessageDialog(null, mensaje.toString(), "Advertencia de Stock Bajo", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar productos con stock bajo: " + e);
            JOptionPane.showMessageDialog(null, "Error al verificar stock", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
