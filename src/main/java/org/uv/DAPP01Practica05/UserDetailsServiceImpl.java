/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.DAPP01Practica05;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    
        @Autowired
    private EmpleadoRepository repositoryEmpleado;
    

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Empleado empleado = repositoryEmpleado.findByEmail(email);
        
        
        if (empleado == null) {
            throw new UsernameNotFoundException("Empleado con correo electrónico: " + email + " no encontrado");
        }
        
        // Create and return the Spring Security UserDetails object
        return org.springframework.security.core.userdetails.User.builder()
                .username(empleado.getEmail())
                .password(empleado.getPassword())
                .roles("ADMIN")
                .build();
    }
}
