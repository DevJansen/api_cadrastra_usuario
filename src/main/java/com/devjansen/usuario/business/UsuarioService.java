package com.devjansen.usuario.business;

import com.devjansen.usuario.infrastructure.entity.Usuario;
import com.devjansen.usuario.infrastructure.exception.ConflictException;
import com.devjansen.usuario.infrastructure.respository.UsuarioRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class UsuarioService {

    private final UsuarioRespository usuarioRespository;
    private final PasswordEncoder passwordEncoder;

    public Usuario salvaUsuario(Usuario usuario){
        emailExiste(usuario.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRespository.save(usuario);
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRespository.findByEmail(email).orElseThrow(
                () -> new RequestRejectedException("Email não existente!!!" + email)
        );
    }

    public void emailExiste(String email){
        boolean existe = usuarioRespository.existsByEmail(email);
        if(existe){
           throw new ConflictException("Email já cadastrado: " + email);
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRespository.existsByEmail(email);
    }

    public void delataUsuarioPorEmail(String email){
        usuarioRespository.deleteByEmail(email);
    }

}
