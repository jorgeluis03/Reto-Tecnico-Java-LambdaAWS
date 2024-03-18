package com.example.retotecnico.controller;

import com.example.retotecnico.dao.SwapiDao;
import com.example.retotecnico.entity.EmpleadoEntity;
import com.example.retotecnico.repository.EmpleadoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
public class EmpleadoController {

    @Autowired
    EmpleadoRepository empleadoRepository;
    @Autowired
    SwapiDao swapiDao;

    @GetMapping("/empleados")
    public ResponseEntity<HashMap<String,Object>> obtenerEmpleados(){
        HashMap<String,Object> responseJson = new HashMap<>();

        responseJson.put("result","success");
        responseJson.put("empleados",empleadoRepository.findAll());

        return ResponseEntity.ok(responseJson);
    }

    @PostMapping("empleados")
    public ResponseEntity<HashMap<String,Object>> crearEmpleado(@RequestBody EmpleadoEntity empleado){
        HashMap<String,Object> responseJson = new HashMap<>();

        empleadoRepository.save(empleado);
        responseJson.put("result","created");
        responseJson.put("Id nuevo",empleado.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }


    @GetMapping("/swapiPlanetas/{id}")
    public ResponseEntity<HashMap<String,Object>> obtenerPlaneta(@PathVariable("id") String id){
        return ResponseEntity.ok(swapiDao.obtenerPlaneta(id));
    }


    //Excepcion que aparece al no poder mapear el json del cuarpo del mensaje en un POST
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,Object>> gestionExcepcion (HttpServletRequest request){
        HashMap<String,Object> responseJson = new HashMap<>();

        if(request.getMethod().equals("POST")){
            responseJson.put("estado","error");
            responseJson.put("msg","Debe enviar un producto");
        }
        return ResponseEntity.badRequest().body(responseJson);
    }
}
