package com.devjansen.usuario.business;

import com.devjansen.usuario.infrastructure.entity.Usuario;
import com.devjansen.usuario.infrastructure.exception.ConflictException;
import com.devjansen.usuario.infrastructure.respository.UsuarioRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class UsuarioService {

    private final UsuarioRespository usuarioRespository;

    public Usuario salvaUsuario(Usuario usuario){
        emailExiste(usuario.getEmail());
        return usuarioRespository.save(usuario);
    }

    public void emailExiste(String email){
        if(verificaEmailExistente(email)){
           throw new ConflictException("Email já cadastrado: " + email);
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRespository.existsByEmail(email);
    }

}
