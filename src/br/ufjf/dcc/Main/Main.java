
import br.ufjf.dcc.mao_baralho.Carta;
import br.ufjf.dcc.mao_baralho.Mao;
import br.ufjf.dcc.mao_baralho.Baralho;
import br.ufjf.dcc.Replay.Replay;
void main() {
    Mao m1 = new Mao();
    m1.selecaoAutomatica();
    m1.exibirMao();
    Replay.carregarConfig();
    Replay.registrar("Mão inicial do jogador 1: " + m1);
    Replay.mostrarReplay();
}
