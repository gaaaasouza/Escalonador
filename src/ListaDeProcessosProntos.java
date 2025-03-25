import java.util.ArrayList;
import java.util.List;

/*Classe que cria um arraylist para objetos do tipo BCP que representa a fila de 
 * processos prontos
  */

public class ListaDeProcessosProntos {
    private List<BCP> processosProntos;

    public ListaDeProcessosProntos() {
        processosProntos = new ArrayList<>();
    }

    public void adicionarBCP(BCP bcp) {
        processosProntos.add(bcp);
    }

    public List<BCP> getProcessosProntos() {
        return processosProntos;
    }

}
