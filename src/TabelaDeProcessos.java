import java.util.ArrayList;
import java.util.List;

/*Classe que cria um arraylist para objetos do tipo BCP que 
    representa a tabela de processos
  */

public class TabelaDeProcessos {
    public List<BCP> tabela;

    public TabelaDeProcessos() {
        tabela = new ArrayList<>();
    }

    public void adicionarBCP(BCP bcp) {
        tabela.add(bcp);
    }

    public List<BCP> getTabela() {
        return tabela;
    }
}
