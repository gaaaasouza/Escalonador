import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*Classe que lê os arquivos txt e carrega cada arquivo em um processo diferente.
 * Após carregar os arquivos nos processos, gera um BCP e atribui cada processo 
 * a um BCP diferente
 * Também é responsável por extrair o valor do quantum e transformá-lo num inteiro
*/

public class Leitura {
    private Integer quantum;
    public List<BCP> bcpList;

    public Leitura(String diretorio) {
        quantum = 0;
        bcpList = new ArrayList<>();
        lerArquivos(diretorio);
    }

    private void lerArquivos(String diretorio) {
        File dir = new File(diretorio);

        if (dir.exists() && dir.isDirectory()) {
            File[] arquivos = dir.listFiles();

            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    if (arquivo.getName().matches("\\d+\\.txt")) {
                        Processo processo = criarProcesso(arquivo);
                        BCP bcp = new BCP(processo);
                        bcpList.add(bcp);
                    } else if (arquivo.getName().equals("quantum.txt")) {
                        lerQuantum(arquivo);
                    }
                }
            }
        }
    }

    private Processo criarProcesso(File arquivo) {
        Processo processo = new Processo();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                processo.adicionarLinha(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return processo;
    }

    private void lerQuantum(File arquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String quant = br.readLine();
            quantum = Integer.parseInt(quant);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int getQuantum() {
        return quantum;
    }

    public List<BCP> getBCPList() {
        return bcpList;
    }
}