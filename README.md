# HackWar

Jogo de carta com 3 tipos csv de carta s: Ataque, Defesa e Suporte.
Objetos iniciais: carta, baralho, mão.
Após esses avaliar jogador, bot, main(hub com a interação das funções)
hub no terminal.
Cada jogador 10 cartas com 4 ataque, 4 defesa e 2 suporte.
100 vida com 10 energia sendo que cada carta gasta x energia e cada round recebe +1 de energia.
Baralho com 50 cartas.
Tem que ter um replay escrevendo em um arquivo txt todas as jogadas.
Identificador dos jogadores: matrícula
    do bot: 202565001
Carta deve apresentar os atributos:
    
    Nome
    Poder
    Tipo (Ataque, Defesa, Suporte)
    Custo de energia
    Descrição

Funções da carta:

    Getter e setter

## Cálculo de dano:
    Dano final = ataque - defesa
    Se def e def : atk = 0
    Se Atq e Atq : dano correspondente para cada um

## Modificador de ataque e suporte:
Se um hacker usar uma carta de suporte de aumento de ataque junto
com uma carta de ataque:
    
    ATKtotal= ATK ×(1+SUPatk+)

As demais regras de dano permanecem as mesmas.


Se o hacker usar duas ou mais cartas de ataque e uma carta de
suporte de aumento de ataque (SUPatk+), o bônus se aplica apenas à
carta de maior ataque.

    ATKtotal=[ATKmaior×(1+SUPatk+)]+ ATKmenor


Se o hacker usar uma carta de suporte de diminuição do ataque do
oponente (SUPatk-), a redução é aplicada sobre o ataque total:

    ATKtotal= ATKtotal−(ATKtotal× SUPatk-)


Se o hacker usar uma carta de suporte de aumento de vida (SUPvida+),
o ganho é aplicado antes de receber o ataque.
Nesse caso, a vida pode temporariamente ultrapassar 100, mas ao
final da consolidação o valor é limitado a 100.


Após a consolidação, a vida dos hackers deverá ser arredondada
para o múltiplo de 10 mais próximo.

    ○ último dígito de vida < 5, o valor é arredondado para baixo.
    ○último dígito de vida >= 5, o valor é arredondado para cima.


## Cálculo de energia:

● Para calcular a energia final (ENERfinal) utilizamos a seguinte
fórmula:

    ENERfinal = 1 + (ENERinicial - ENERutilizada)

Onde, a ENERutilizada é a soma das energias gastas por cada carta
utilizada

● Se a energia final > 10 pontos - por exemplo, passou a
vez na primeira jogada - a energia final  = 10.

## Regras Gerais:

O programa não deverá permitir que os pontos de vida e de energia sejam
alterados diretamente. Ou seja, apenas as ações do jogo (consolidação) podem
alterar esses valores.

O programa não deverá permitir que ações inválidas sejam executadas. Isso
inclui:

    ● Quaisquer outras entradas que não estejam de acordo com as regras ou
    formatos esperados (ex.: escolher uma opção de menu que não existe) devem
    ser tratadas.
    ● Quaisquer outras entradas inválidas devem ser tratadas sem interromper a
    execução do programa. O sistema deve informar ao usuário o motivo da
    invalidez e permitir que ele tente novamente.
    ● Atualização do Jogo: O programa deverá apresentar (imprimir no terminal)
    o estado atual do jogo após cada ação para os hackers poderem acompanhar
    o progresso. Isso inclui todos os atributos dos personagens.
    ● Etc

Será necessária a utilização de múltiplas classes, ao menos dois pacotes (um deles
deverá ter a classe principal do jogo) métodos estáticos e encapsulamento.

O programa deverá utilizar apenas bibliotecas nativas do Java a não ser que
vocês decidam implementar algo mais criativo que realmente precise instalar
alguma biblioteca específica. Nesse caso, vocês precisarão aprender como
incluir essas bibliotecas.

Os alunos poderão utilizar conceitos mais complexos que não os estudados dentro
de sala de aula. Todavia, deverão explicar o motivo de sua utilização.

Se for utilizar auxílio de IA na codificação tenha consciência de que você
compreende bem o que ela está te respondendo.

O zip final segue a estrutura "Matricua1_Matricula2.zip".