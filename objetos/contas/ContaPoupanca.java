package objetos.contas;

public class ContaPoupanca extends Conta {
    private String tipoConta = "Conta Poupança";

    // Construtor
    public ContaPoupanca(String agencia, String numero, double saldo) {
        super(agencia, numero, saldo);
    }
}
