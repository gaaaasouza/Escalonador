import java.util.ArrayList;
import java.util.List;

/*Classe que cria um arraylist de processos extraídos dos arquivos txt
 * Cada linha do arquivo é um espaço na memória do arraylist.
 * Cada arquivo é um objeto do tipo Processo.
 */

public class Processo {
    private List<String> linhas;
    private static int contadorDeProcessos = 0;

    public Processo() {
        linhas = new ArrayList<>();
        contadorDeProcessos++;
    }

    public void adicionarLinha(String linha) {
        linhas.add(linha);
    }

    public List<String> getLinhas() {
        return linhas;
    }

    public static int getContadorDeProcessos() {
        return contadorDeProcessos;
    }
}