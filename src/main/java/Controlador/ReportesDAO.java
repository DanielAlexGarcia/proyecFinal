/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.ResultSet;

/**
 *
 * @author daniel
 */
public class ReportesDAO extends AbstracDAO {
    
    public ResultSet RegistrosAltas (){
        String sql = "select * from ReportsAltas";
        StatementSetter setter = (stmt) -> {
                };
        return ejecutarQueryTemplate(sql, setter, "consultar reportes altas");
    }
    
    public ResultSet RegistrosBajas (){
        String sql = "select * from ReportsBajas";
        StatementSetter setter = (stmt) -> {
                };
        return ejecutarQueryTemplate(sql, setter, "consultar reportes bajas");
    }
    
    public ResultSet RegistrosCambios (){
        String sql = "select * from ReportsCambios";
        StatementSetter setter = (stmt) -> {
                };
        return ejecutarQueryTemplate(sql, setter, "consultar reportes bajas");
    }
    
}
