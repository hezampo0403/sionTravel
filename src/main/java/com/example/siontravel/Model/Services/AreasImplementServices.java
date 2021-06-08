package com.example.siontravel.Model.Services;

import com.example.siontravel.Model.Entity.Areas;
import com.example.siontravel.Model.Repository.AreasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreasImplementServices implements AreasInterfacesServices{

    @Autowired
    private AreasRepository areasRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    List rutaLista;
    List onwaytrip;
    List roundtrip;

    @Override
    public List<Areas> listar() {
        return (List<Areas>) areasRepository.findAll();
    }

    @Override
    public void guardar(Areas areas) {
        areasRepository.save(areas);
    }

    @Override
    public Areas buscarById(int id) {
        return (Areas) areasRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(int id) {
        areasRepository.deleteById(id);
    }

    @Override
    public List listaRutas(int id) {
        var sql = "SELECT DISTINCT r.id_to, a1.nombre FROM area a LEFT JOIN rutas r ON (a.id = r.id_from) LEFT JOIN area a1 ON (r.id_to = a1.id) WHERE r.id_from ="+id;
        rutaLista = this.jdbcTemplate.queryForList(sql);
        return rutaLista;
    }

    @Override
    public List oneWayTrip(int id_from, int id_to, String fecha_salida) {
        var sql = "SELECT DISTINCT r.id AS id_ruta, r.precio_adulto, r.precio_infante, r.precio_equipaje, a.nombre AS origen, a1.nombre AS destino, b.hora_salida, b.hora_llegada, b.id AS id_bus, b.bus, b.id_from, b1.id_to FROM rutas r LEFT JOIN area a ON (r.id_from = a.id) LEFT JOIN area a1 ON (r.id_to = a1.id) LEFT JOIN bus b ON (a.id = b.id_from) LEFT JOIN bus b1 ON (a1.id = b1.id_to) WHERE r.id_from = "+id_from+" AND r.id_to = "+id_to+" AND b.id_from = "+id_from+" AND b.id_to = "+id_to+" AND r.fecha_inicio <= '"+fecha_salida+"' AND r.fecha_final >= '"+fecha_salida+"' ";
        onwaytrip = this.jdbcTemplate.queryForList(sql);
        return onwaytrip;
    }

    @Override
    public List roundTrip(int id_from, int id_to, String fecha_llegada) {
        var sql = "SELECT DISTINCT r.id AS id_ruta, r.precio_adulto, r.precio_infante, r.precio_equipaje, a.nombre AS origen, a1.nombre AS destino, b.hora_salida, b.hora_llegada, b.id AS id_bus, b.bus, b.id_from, b1.id_to FROM rutas r LEFT JOIN area a ON (r.id_from = a.id) LEFT JOIN area a1 ON (r.id_to = a1.id) LEFT JOIN bus b ON (a.id = b.id_from) LEFT JOIN bus b1 ON (a1.id = b1.id_to) WHERE r.id_to = "+id_from+" AND r.id_from = "+id_to+" AND b.id_to = "+id_from+" AND b.id_from = "+id_to+" AND r.fecha_inicio <= '"+fecha_llegada+"' AND r.fecha_final >= '"+fecha_llegada+"' ";
        roundtrip = this.jdbcTemplate.queryForList(sql);
        return roundtrip;
    }


}
