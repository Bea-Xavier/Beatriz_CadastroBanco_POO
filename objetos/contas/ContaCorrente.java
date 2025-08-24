package objetos.contas;

public class ContaCorrente extends Conta {
    private String tipoConta = "Conta Corrente";

    // Construtor
    public ContaCorrente(String agencia, String numero, double saldo) {
        super(agencia, numero, saldo);
    }
    
}
