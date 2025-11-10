package br.ufjf.dcc.maoBaralho;

public class Carta {
    String nome, tipo, descricao, efeito;
    double poder;
    int custo;

    public Carta(String nome, String tipo, String descricao, double poder, int custo, String efeito) {
        this.nome = nome;
        this.tipo = tipo;
        this.descricao = descricao;
        this.poder = poder;
        this.custo = custo;
        this.efeito = efeito;
    }

    public void imprimirCarta() {
        System.out.print("Nome: " + this.nome + ", ");
        System.out.print("Tipo: " + this.tipo + ", ");
        System.out.print("Poder: " + this.poder + ", ");
        System.out.print("Custo: " + this.custo+ ", ");
        if(!this.efeito.equals( "Não tem efeito") ) {
            System.out.print("Efeito: " + this.efeito + ", ");
        }
        System.out.print("Descricao: " + this.descricao+ ";\n");
        System.out.println("-----------------------------------------------------------------------------------"
                + "-------------------------------------------------------------------------------------------");
    }

    public String getTipo() {
        return this.tipo;
    }

    public double getPoder() {
        return this.poder;
    }

    public String getReplayInfo() {
        String infoEfeito = this.efeito.equals("Não tem efeito") ? "" : ", Efeito: " + this.efeito;
        return String.format(
                "| %s | Tipo: %s | Poder: %.0f | Custo: %d%s |",
                this.nome, this.tipo, this.poder, this.custo, infoEfeito
        );
    }

    public int getCusto() {
        return this.custo;
    }

    public String getEfeito() {
        return this.efeito;
    }

    public String getNome() {
        return this.nome;
    }
}
