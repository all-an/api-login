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

    @PostMapping("/usuarios/registrar")
    public Map<Status, Usuario> adminRegistraUsuario(@Valid @RequestBody List<Usuario> adminNovoUsuario) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();

        Map<Status, Usuario> retorno = new HashMap<>();

        for (Usuario esteUsuario : usuarios) {
            if (usuarios.contains(adminNovoUsuario.get(0)) && esteUsuario.getFuncao().equals("admin") &&
                    esteUsuario.equals(adminNovoUsuario.get(0))) {
                esteUsuario.setLogado(true);
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

        adminNovoUsuario.get(0).setFuncao("comum(investigar)");
        adminNovoUsuario.get(1).setFuncao("comum(investigar)");
        retorno.put(Status.USUARIO_NAO_POSSUI_NIVEL_DE_ACESSO, null);
        usuarioRepositorio.save(adminNovoUsuario.get(0));
        usuarioRepositorio.save(adminNovoUsuario.get(1));
        return retorno;
    }

    @PostMapping("/usuarios/login")
    public Status loginUsuario(@Valid @RequestBody Usuario usuario) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();

        for (Usuario outro : usuarios) {
            if (usuarios.contains(usuario) && outro.getFuncao().equals(usuario)
                    && outro.getFuncao().equals("comum") || outro.getFuncao().equals("admin")) {
                outro.setLogado(true);
                usuarioRepositorio.save(usuario);
                System.out.println(usuario);
                return Status.SUCESSO;
            }
        }
        usuario.setFuncao("comum(investigar)");
        usuarioRepositorio.save(usuario);
        return Status.FALHA;
    }

    //@PostMapping("/usuarios/admin")
    @RequestMapping(value = "/usuarios/", method = RequestMethod.GET)
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
    public Status deletaTodosUsuarios(@Valid @RequestBody Usuario usuario) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        for (Usuario esteUsuario : usuarios) {
            if (usuarios.contains(usuario) && esteUsuario.getFuncao().equals("admin") &&
                    esteUsuario.equals(usuario)) {
                esteUsuario.setLogado(true);
                usuarioRepositorio.save(usuario);
                usuarioRepositorio.deleteAll();
                return Status.SUCESSO;
            }
        }
        usuario.setFuncao("comum(investigar)");
        usuarioRepositorio.save(usuario);
        return Status.USUARIO_NAO_POSSUI_NIVEL_DE_ACESSO;
    }

    @RequestMapping(value = "/usuarios/", method = RequestMethod.DELETE)
    public Map<Status, Usuario> deletarUmUsuario(@Valid @RequestBody List<Usuario> adminNovoUsuario ) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();

        Map<Status, Usuario> retorno = new HashMap<>();

        for (Usuario esteUsuario : usuarios) {
            if (usuarios.contains(adminNovoUsuario.get(0)) && esteUsuario.getFuncao().equals("admin") &&
                    esteUsuario.equals(adminNovoUsuario.get(0))) {
                esteUsuario.setLogado(true);
                usuarioRepositorio.save(adminNovoUsuario.get(0));
                //for (Usuario usuario : usuarios) {
                    if (!usuarios.contains(adminNovoUsuario.get(1))) {
                        retorno.put(Status.USUARIO_NAO_EXISTE, null);
                        System.out.println("Usuario não existe!");
                        return retorno;
                    }else{
                    retorno.put(Status.SUCESSO, adminNovoUsuario.get(1));
                    usuarioRepositorio.delete(adminNovoUsuario.get(1));
                    return retorno;
                }
            }
        }

        adminNovoUsuario.get(0).setFuncao("comum(investigar)");
        adminNovoUsuario.get(1).setFuncao("comum(investigar)");
        retorno.put(Status.USUARIO_NAO_POSSUI_NIVEL_DE_ACESSO, null);
        usuarioRepositorio.save(adminNovoUsuario.get(0));
        usuarioRepositorio.save(adminNovoUsuario.get(1));
        return retorno;
    }

    @RequestMapping(value = "/usuarios/", method = RequestMethod.PUT)
    public Map<Status, Usuario> atualizaUmUsuario(@Valid @RequestBody List<Usuario> adminNovoUsuario ) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();

        Map<Status, Usuario> retorno = new HashMap<>();

        for (Usuario esteUsuario : usuarios) {
            if (usuarios.contains(adminNovoUsuario.get(0)) && esteUsuario.getFuncao().equals("admin") &&
                    esteUsuario.equals(adminNovoUsuario.get(0))) {
                esteUsuario.setLogado(true);
                usuarioRepositorio.save(adminNovoUsuario.get(0));
                //for (Usuario usuario : usuarios) {
                    retorno.put(Status.SUCESSO, adminNovoUsuario.get(1));
                    usuarioRepositorio.save(adminNovoUsuario.get(1));
                    return retorno;

            }
        }

        adminNovoUsuario.get(0).setFuncao("comum(investigar)");
        adminNovoUsuario.get(1).setFuncao("comum(investigar)");
        retorno.put(Status.USUARIO_NAO_POSSUI_NIVEL_DE_ACESSO, null);
        usuarioRepositorio.save(adminNovoUsuario.get(0));
        usuarioRepositorio.save(adminNovoUsuario.get(1));
        return retorno;
    }

    @PostMapping("/usuarios/primeiroRegistro")
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

    /*
    @DeleteMapping("/usuarios/todos")
    public Status deleteUsuarios() {
        usuarioRepositorio.deleteAll();
        return Status.SUCESSO;
    }*/
}