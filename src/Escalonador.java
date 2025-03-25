import java.util.List;

public class Escalonador {

    private static Float mediainstrucoes = (float) 0; //valor que irá calcular a média de instruções
    private static Float mediatroca = (float) 0; // valor que irá calcular a média de trocas
    private static Integer quant = 0; // quantidade de quanta para calcularmos a média de instruções
    private static Integer q = 0;  // o valor do quantum que será atualizado a cada troca efetuado pelo escalonador
    private static Integer processos;
    private static Integer quantum;
    private static Leitura leitura = new Leitura("C:/programas");
    private static LogFile logfile = new LogFile("C:/programas", "log0" + leitura.getQuantum());

    public static void main(String[] args) {
        quantum = leitura.getQuantum(); //recebe o valor do quantum extraído a partir da classe Leitura
        TabelaDeProcessos tabeladeprocessos = new TabelaDeProcessos(); //inicialização de todas as classes necessárias para o funcionamento do escalonador e da montagem do logfile
        ListaDeProcessosProntos listadeprocessosprontos = new ListaDeProcessosProntos();
        ListaDeProcessosBloqueados listadeprocessosbloqueados = new ListaDeProcessosBloqueados();
        List<BCP> bcpList = leitura.getBCPList();
        List<BCP> processosProntos = listadeprocessosprontos.getProcessosProntos();
        List<BCP> processosBloqueados = listadeprocessosbloqueados.getProcessosBloqueados();
        List<BCP> tabelaProcessos = tabeladeprocessos.getTabela();

        for (BCP bcp : bcpList) {   // escalonador carrega os blocos de comando na memória, posicionando o seu BCP na tabela de processos e na fila de processos prontos
            tabelaProcessos.add(bcp);
            processosProntos.add(bcp);
            logfile.escrever("Carregando " + bcp.getPrograma());    
        }

        processos = tabelaProcessos.size();  //valor que irá calcular a média de trocas 

        while (tabelaProcessos.isEmpty() != true) {  // aqui começa o escalonador que só irá parar quando a tabela de processos estiver vazia

            while(processosProntos.isEmpty()){    
                listaProcessoBloqueado(processosBloqueados, processosProntos);
            }
            
            q = quantum;
            processosProntos.get(0).setEstado("Executando");
            logfile.escrever("Executando " + processosProntos.get(0).getPrograma());

            while (q != 0) {  // início do quantum
                //logfile.escrever("Contador de programa:  " + processosProntos.get(0).getContadorDePrograma() + "   " + processosProntos.get(0).getPrograma());
                if (!processosProntos.get(0).getContadorDePrograma().equals("E/S") &&
                        !processosProntos.get(0).getContadorDePrograma().equals("SAIDA")) { // bloco de comandos caso a instrução seja "COM" ou X= ou Y=
                    q--;
                    executando(processosProntos.get(0));
                    mediainstrucoes++;
                    if (q == 0) {
                        logfile.escrever("Interrompendo " + processosProntos.get(0).getPrograma() + " após " + (quantum - q) + " instruções");
                        processosProntos.get(0).setEstado("Pronto");
                        processosProntos.add(processosProntos.get(0));
                        processosProntos.remove(0);
                        mediatroca++;
                        break;
                    }
                }
                if (processosProntos.get(0).getContadorDePrograma().equals("E/S")) {  // bloco de comandos caso a instrução seja uma E/S
                    q--;
                    logfile.escrever("E/S iniciada em " + processosProntos.get(0).getPrograma());
                    logfile.escrever("Interrompendo " + processosProntos.get(0).getPrograma() + " após " + (quantum - q) + " instruções");
                    processosProntos.get(0).setContadorDePrograma();
                    processosProntos.get(0).setEstado("Bloqueado");
                    processosProntos.get(0).setBloqueado(2);
                    processosBloqueados.add(processosProntos.get(0));
                    processosProntos.remove(0);
                    mediatroca++;
                    mediainstrucoes++;
                    break;
                } 
                if(processosProntos.get(0).getContadorDePrograma().equals("SAIDA")) {  // bloco de comandos caso a instrução seja uma SAIDA
                    q--;
                    logfile.escrever("Interrompendo " + processosProntos.get(0).getPrograma() + " após " + (quantum - q) + " instruções");
                    logfile.escrever(processosProntos.get(0).getPrograma() + " terminado. X=" + processosProntos.get(0).getRegistradorX() + ". Y=" + processosProntos.get(0).getRegistradorY());
                    tabelaProcessos.removeIf(n -> (n == processosProntos.get(0)));
                    processosProntos.remove(processosProntos.get(0));
                    mediatroca++;
                    mediainstrucoes++;
                    break;
                }
            }
            quant++;

            if(processosBloqueados.isEmpty() != true){  // tratamento da lista de bloqueados caso ela possua algum elemento
                listaProcessoBloqueado(processosBloqueados, processosProntos);
            }
        }
        
        print();
    }

    private static void listaProcessoBloqueado(List<BCP> processosbloqueados, List<BCP> processosprontos) {  //método que trata a fila de processos bloqueados

        BCP[] bcpestatico;
        bcpestatico = new BCP[processosbloqueados.size()];

        for(int i = 0; i < bcpestatico.length; i++){
            bcpestatico[i] = processosbloqueados.get(i);
        }
        
        processosbloqueados.clear();

        for(int i = 0; i < bcpestatico.length; i++) {

            if (bcpestatico[i].getBloqueado() == 0) {
                bcpestatico[i].setEstado("Pronto");
                processosprontos.add(bcpestatico[i]);    
            }

            else{
                bcpestatico[i].setBloqueado(bcpestatico[i].getBloqueado() - 1);
                processosbloqueados.add(bcpestatico[i]);
            }
        }    
    }

    private static void executando(BCP bcp) {  //método que trata o contador de programa para instruções do tipo COM, X= ou Y=

        if (bcp.getContadorDePrograma().equals("COM")) {
            bcp.setContadorDePrograma();
            return;
        }
        if (bcp.getContadorDePrograma().contains("X=")) {
            int x = Integer.parseInt(bcp.getContadorDePrograma().substring(2));
            bcp.setRegistradorX(x);
            bcp.setContadorDePrograma();
            return;
        }
        if (bcp.getContadorDePrograma().contains("Y=")) {
            int y = Integer.parseInt(bcp.getContadorDePrograma().substring(2));
            bcp.setRegistradorY(y);
            bcp.setContadorDePrograma();
            return;
        }
    }
    
    public static void print(){  //método que calcula, imprime as médias e fecha o arquivo logfile
        logfile.escrever("MEDIA DE TROCAS: " +  mediatroca / processos);
        logfile.escrever("MEDIA DE INSTRUCOES: " + mediainstrucoes / quant);
        logfile.escrever("QUANTUM: " + quantum);
        logfile.fechar();
    }
  
}