package org.uv.DAPP01Practica05;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1")
public class EmpleadosController {
     private final PasswordEncoder passwordEncoder;
    
    public EmpleadosController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    @Autowired
    private EmpleadoRepository repositoryEmpleado;
    
    @GetMapping("/hola/")
    public String sayHola() {
        return "Hola mundo";
    }
    
    @GetMapping("/empleado/")
    public List<Empleado> list() {
        return repositoryEmpleado.findAll();
    }
    
    @GetMapping("/empleado/{id}")
    public Empleado get(@PathVariable Long id) {
        Optional<Empleado> optEmpleado = repositoryEmpleado.findById(id);
        if (optEmpleado.isPresent())
            return optEmpleado.get();
        else
            return null;
    }
    
    @PutMapping("/empleado/{id}")
    public ResponseEntity<Empleado> put(@PathVariable Long id, @RequestBody Empleado newEmpleadoData) {
        Optional<Empleado> optEmpleado = repositoryEmpleado.findById(id);
            if (optEmpleado.isPresent()) {
                Empleado empleado = optEmpleado.get();
                empleado.setNombre(newEmpleadoData.getNombre());
                empleado.setDireccion(newEmpleadoData.getDireccion());
                empleado.setTelefono(newEmpleadoData.getTelefono());
                empleado.setEmail(newEmpleadoData.getEmail());
                empleado.setPassword(passwordEncoder.encode(newEmpleadoData.getPassword()));
                repositoryEmpleado.save(empleado);
                return ResponseEntity.ok(empleado);
            } else {
                return ResponseEntity.notFound().build();
            }
    }

    @PostMapping("/empleado/")
    public ResponseEntity<Empleado> post(@RequestBody Empleado empleado) {
        empleado.setPassword(passwordEncoder.encode(empleado.getPassword()));
        repositoryEmpleado.save(empleado);
        return ResponseEntity.ok(empleado); 
    }
    
    @DeleteMapping("/empleado/{id}")
    public ResponseEntity<Empleado> delete(@PathVariable Long id) {
        Optional<Empleado> optEmpleado = repositoryEmpleado.findById(id);
        if (optEmpleado.isPresent()) {
            repositoryEmpleado.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
 
}