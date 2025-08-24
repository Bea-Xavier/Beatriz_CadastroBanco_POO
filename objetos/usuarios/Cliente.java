package objetos.usuarios;

import objetos.contas.Conta;

public class Cliente extends Pessoa{
    private Conta conta;

    // Construtor
    public Cliente(String nome, String endereco, String telefone, String cpf, Conta conta) {
        super(nome, endereco, telefone, cpf);
        this.conta = conta;
    }

    // Getters e Setters
    public Conta getConta() {  
        return conta;
    }
    public void setConta(Conta conta) {
        this.conta = conta;
    }
}
