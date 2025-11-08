
import br.ufjf.dcc.Usuarios.Bot;
import br.ufjf.dcc.mao_baralho.Carta;
import br.ufjf.dcc.mao_baralho.Mao;
import br.ufjf.dcc.mao_baralho.Baralho;
import br.ufjf.dcc.Replay.Replay;
void main() {
    Replay.carregarConfig();
    Mao m1 = new Mao();
    Bot a =new Bot();
    m1.escolherCartas();
    m1.exibirMao();
    a.escolherCartaReacao(m1.getCarta(1));

}
