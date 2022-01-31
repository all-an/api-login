package com.api.login.tests;

import com.api.login.controllers.UsuarioController;
import com.api.login.entities.Usuario;
import com.api.login.repositories.UsuarioRepositorio;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.easymock.EasyMock.verify;

@WebMvcTest
public class UsuarioControllerTest {

    private static final String NOME_ADMIN = "Allan";
    private static final String SENHA_ADMIN = "SenhaAllan";
    private static final String FUNCAO_ADMIN = "admin";

    private static final String NOME_COMUM = "Maria";
    private static final String SENHA_COMUM = "SenhaMaria";
    private static final String FUNCAO_COMUM = "comum";

    private UsuarioRepositorio usuarioRepositorio;

    @MockBean
    @Autowired
    private UsuarioController usuarioController;

    private Usuario admin;
    private Usuario funcionarioComum;

    @Before
    public void setUp() throws Exception {

        admin = new Usuario();
        admin.setUsername(NOME_ADMIN);
        admin.setSenha(SENHA_ADMIN);
        admin.setFuncao(FUNCAO_ADMIN);

        funcionarioComum = new Usuario();
        funcionarioComum.setNomeUsuario(NOME_COMUM);
        funcionarioComum.setSenha(SENHA_COMUM);
        funcionarioComum.setFuncao(FUNCAO_COMUM);
    }

    @Test
    public void deveRetornarSucesso_QuandoBuscarTodosUsuarios() throws Exception {
        usuarioController.visualizaTodosUsuarios(admin);

        RestAssuredMockMvc.standaloneSetup();
        verify(usuarioRepositorio);
    }
}
