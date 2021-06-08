package com.example.siontravel.Controller;

import com.example.siontravel.Model.Entity.Areas;
import com.example.siontravel.Model.Entity.Booking;
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
    List roundtrip;

    @Autowired
    private AreasInterfacesServices areasInterfacesServices;


    @GetMapping("/")
    public String index(Model model){
        List<Areas> listaAreas = areasInterfacesServices.listar();
        Booking booking = new Booking();
        model.addAttribute("areasLista", listaAreas);
        model.addAttribute("booking", booking);
        model.addAttribute("titulo", "Formulario de Booking");
        return "home";
    }

    @PostMapping("/booking")
    public String booking(@ModelAttribute Booking booking,Model model) {
        Reservas reservas = new Reservas();
        model.addAttribute("titulo", "Lista de rutas para su fecha de viaje");
        model.addAttribute("booking", booking);
        model.addAttribute("reservas", reservas);
        model.addAttribute("onewaytrip", areasInterfacesServices.oneWayTrip(booking.getAreas_from().getId(), booking.getAreas_to().getId(), booking.getFecha_salida()));
        model.addAttribute("roundtrip", areasInterfacesServices.roundTrip(booking.getAreas_from().getId(), booking.getAreas_to().getId(), booking.getFecha_salida()));
        return "/booking";
    }

    @PostMapping("/confirm")
    public String bookingConfirm(@ModelAttribute Reservas reservas, Model model){
        model.addAttribute("titulo", "Estos son los datos de su reserva");
        model.addAttribute("reservas", reservas);
        return "/confirm";
    }


    @GetMapping("/admin/inicio")
    public String indexAdmin(){
        return "admin/inicio";
    }

}
