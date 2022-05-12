package br.com.alura.sevendaysofcodeandroid;

import java.util.Locale;

public class Pessoa {

    private String nome;

    public Pessoa(String nome){
        this.nome = nome.toUpperCase();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
