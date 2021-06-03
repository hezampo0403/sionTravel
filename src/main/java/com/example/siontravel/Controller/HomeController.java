package com.example.siontravel.Controller;

import com.example.siontravel.Model.Entity.Areas;
import com.example.siontravel.Model.Entity.Reservas;
import com.example.siontravel.Model.Services.AreasInterfacesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    List onwaytrip, roundtrip;

    @Autowired
    private AreasInterfacesServices areasInterfacesServices;


    @GetMapping("/")
    public String index(Model model){
        List<Areas> listaAreas = areasInterfacesServices.listar();
        Reservas reservas = new Reservas();
        model.addAttribute("areasLista", listaAreas);
        model.addAttribute("reservas", reservas);
        model.addAttribute("titulo", "Formulario de Booking");
        return "home";
    }

    @PostMapping("/booking")
    public String booking(@ModelAttribute Reservas reservas,Model model) {
        model.addAttribute("titulo", "Lista de rutas para su fecha de viaje");
        model.addAttribute("reservas", reservas);
        model.addAttribute("onewaytrip", this.onWayTrip(reservas.getAreas_from().getId(), reservas.getAreas_to().getId(), reservas.getFecha_salida()));
        model.addAttribute("roundtrip", this.roundTrip(reservas.getAreas_from().getId(), reservas.getAreas_to().getId(), reservas.getFehca_llegada()));
        return "/booking";
    }

    @PostMapping("/confirm")
    public String bookingConfirm(Model model){
        Reservas reservasBooking = new Reservas();
        model.addAttribute("titulo", "Estos son los datos de su reserva");
        model.addAttribute("fecha_salida", reservasBooking);
        return "/confirm";
    }

    public List onWayTrip(int id_from, int id_to, String fecha_salida){
        var sql = "SELECT DISTINCT r.id AS id_ruta, r.precio_adulto, r.precio_infante, r.precio_equipaje, a.nombre AS origen, a1.nombre AS destino, b.hora_salida, b.hora_llegada, b.id AS id_bus, b.bus, b.id_from, b1.id_to FROM rutas r LEFT JOIN area a ON (r.id_from = a.id) LEFT JOIN area a1 ON (r.id_to = a1.id) LEFT JOIN bus b ON (a.id = b.id_from) LEFT JOIN bus b1 ON (a1.id = b1.id_to) WHERE r.id_from = "+id_from+" AND r.id_to = "+id_to+" AND b.id_from = "+id_from+" AND b.id_to = "+id_to+" AND r.fecha_inicio <= '"+fecha_salida+"' AND r.fecha_final >= '"+fecha_salida+"' ";
        onwaytrip = this.jdbcTemplate.queryForList(sql);
        return onwaytrip;
    }

    public List roundTrip(int id_from, int id_to, String fecha_llegada){
        var sql = "SELECT DISTINCT r.id AS id_ruta, r.precio_adulto, r.precio_infante, r.precio_equipaje, a.nombre AS origen, a1.nombre AS destino, b.hora_salida, b.hora_llegada, b.id AS id_bus, b.bus, b.id_from, b1.id_to FROM rutas r LEFT JOIN area a ON (r.id_from = a.id) LEFT JOIN area a1 ON (r.id_to = a1.id) LEFT JOIN bus b ON (a.id = b.id_from) LEFT JOIN bus b1 ON (a1.id = b1.id_to) WHERE r.id_to = "+id_from+" AND r.id_from = "+id_to+" AND b.id_to = "+id_from+" AND b.id_from = "+id_to+" AND r.fecha_inicio <= '"+fecha_llegada+"' AND r.fecha_final >= '"+fecha_llegada+"' ";
        roundtrip = this.jdbcTemplate.queryForList(sql);
        return roundtrip;
    }

    @GetMapping("/admin/inicio")
    public String indexAdmin(){
        return "admin/inicio";
    }

}
