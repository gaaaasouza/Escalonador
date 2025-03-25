import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/* Classe que gera os arquivos do tipo "log + quantum" */

public class LogFile {
    private String nomeArquivo;
    private BufferedWriter writer;

    public LogFile(String diretorio, String titulo) {
        this.nomeArquivo = diretorio + "/" + titulo + ".txt";
        try {
            writer = new BufferedWriter(new FileWriter(nomeArquivo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void escrever(String conteudo) {
        try {
            writer.write(conteudo);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fechar() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
