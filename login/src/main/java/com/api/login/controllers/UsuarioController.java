package com.api.login.controllers;

import com.api.login.controllers.entities.Status;
import com.api.login.controllers.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UsuarioController {
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @PostMapping("/usuarios/registro")
    public Status registraUsuario(@Valid @RequestBody Usuario novoUsuario) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();

        System.out.println("Novo Usuario: " + novoUsuario.toString());

        for (Usuario Usuario : usuarios) {
            System.out.println("Registered Usuario: " + novoUsuario.toString());

            if (Usuario.equals(novoUsuario)) {
                System.out.println("Usuario já existe!");
                return Status.USUARIO_JA_EXISTE;
            }
        }

        usuarioRepositorio.save(novoUsuario);
        return Status.SUCESSO;
    }

    @PostMapping("/usuarios/login")
    public Status loginUsuario(@Valid @RequestBody Usuario usuario) {
        List<Usuario> Usuarios = usuarioRepositorio.findAll();

        for (Usuario other : Usuarios) {
            if (other.equals(usuario)) {
                usuario.setLogado(true);
                usuarioRepositorio.save(usuario);
                return Status.SUCESSO;
            }
        }

        return Status.FALHA;
    }

    @PostMapping("/usuarios/logout")
    public Status logoutUsuario(@Valid @RequestBody Usuario usuario) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();

        for (Usuario other : usuarios) {
            if (other.equals(usuario)) {
                usuario.setLogado(false);
                usuarioRepositorio.save(usuario);
                return Status.SUCESSO;
            }
        }

        return Status.FALHA;
    }

    @DeleteMapping("/usuarios/todos")
    public Status deleteUsuarios() {
        usuarioRepositorio.deleteAll();
        return Status.SUCESSO;
    }
}