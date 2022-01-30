package com.api.login.controllers.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    @NonNull
    private @Id @GeneratedValue long id;
    @NonNull
    private @NotBlank String nomeUsuario;
    @NonNull
    private @NotBlank String senha;
    @NonNull
    private @NotBlank boolean logado;
    private @NotBlank String funcao;

    public long getId() {
        return id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setUsername(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario user = (Usuario) o;
        return Objects.equals(nomeUsuario, user.nomeUsuario) &&
                Objects.equals(senha, user.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeUsuario, senha,
                logado);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", senha='" + senha + '\'' +
                ", logado=" + logado +
                ", cargo='" + funcao + '\'' +
                '}';
    }
}
