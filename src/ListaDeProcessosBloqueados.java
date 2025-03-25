import java.util.ArrayList;
import java.util.List;

/*Classe que cria um arraylist para objetos do tipo BCP que representa a fila de 
 * processos bloqueados
  */

public class ListaDeProcessosBloqueados {
    private List<BCP> processosBloqueados;

    public ListaDeProcessosBloqueados() {
        processosBloqueados = new ArrayList<>();
    }

    public void adicionarBCP(BCP bcp) {
        processosBloqueados.add(bcp);
    }

    public List<BCP> getProcessosBloqueados() {
        return processosBloqueados;
    }
}
