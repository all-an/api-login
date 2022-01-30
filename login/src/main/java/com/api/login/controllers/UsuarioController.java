package com.api.login.controllers;

import com.api.login.controllers.entities.Status;
import com.api.login.controllers.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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

    @PostMapping("/usuarios/registrar")
    public Map<Status, Usuario> adminRegistraUsuario(@Valid @RequestBody List<Usuario> adminNovoUsuario) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();

        Map<Status, Usuario> retorno = new HashMap<>();

        for (Usuario admin : usuarios) {
            if (admin.equals(adminNovoUsuario.get(0)) && admin.getFuncao().equals("admin")) {
                adminNovoUsuario.get(0).setLogado(true);
                usuarioRepositorio.save(adminNovoUsuario.get(0));
                for (Usuario usuario : usuarios) {
                    if (usuario.equals(adminNovoUsuario.get(1))) {
                        retorno.put(Status.USUARIO_JA_EXISTE, null);
                        System.out.println("Usuario já existe!");
                        return retorno;
                    }
                    retorno.put(Status.SUCESSO, adminNovoUsuario.get(1));
                    usuarioRepositorio.save(adminNovoUsuario.get(1));
                    return retorno;
                }
            }
        }
        retorno.put(Status.USUARIO_NAO_POSSUI_NIVEL_DE_ACESSO, null);
        usuarioRepositorio.save(adminNovoUsuario.get(0));
        return retorno;
    }

    @PostMapping("/usuarios/login")
    public Status loginUsuario(@Valid @RequestBody Usuario usuario) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();

        for (Usuario other : usuarios) {
            if (other.equals(usuario)) {
                usuario.setLogado(true);
                usuarioRepositorio.save(usuario);
                System.out.println(usuario);
                return Status.SUCESSO;
            }
        }
        return Status.FALHA;
    }

    @PostMapping("/usuarios/admin")
    public Map<Status, List<Usuario>> acessoRestritoAoAdmin(@Valid @RequestBody Usuario usuario) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        Map<Status, List<Usuario>> retorno = new HashMap<>();
        for (Usuario esteUsuario : usuarios) {
            if (esteUsuario.equals(usuario) && esteUsuario.getFuncao().equals("admin")) {
                usuario.setLogado(true);
                usuarioRepositorio.save(usuario);
                retorno.put(Status.SUCESSO, usuarios);
                return retorno;
            }
        }
        retorno.put(Status.USUARIO_NAO_POSSUI_NIVEL_DE_ACESSO, null);
        usuarioRepositorio.save(usuario);
        return retorno;
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