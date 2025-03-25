public class BCP {
    private Processo processo;
    private String programa; //nome do programa, se é teste01, teste 02, etc.
    private String estado; //Pronto, Executando ou Bloqueado
    private String contadorDePrograma; // Índice começa em 1, pois a primeira linha já foi utilizada (nome do programa).
    private Integer registradorX;
    private Integer registradorY;
    private Integer bloqueado; //controla o tempo que cada processo fica na fila de bloqueado
    private Integer cp; //atualiza o índice para que o Contador de Programa busque o próximo processo da variável do tipo Processo

    public BCP(Processo processo) {
        this.processo = processo;
        this.programa = processo.getLinhas().get(0);
        this.estado = "Pronto";
        this.contadorDePrograma = processo.getLinhas().get(1); 
        this.registradorX = 0;
        this.registradorY = 0;
        this.bloqueado = 0;
        this.cp = 2;
    }

    public Integer getCp() {
        return cp;
    }

    public Processo getProcesso() {
        return processo;
    }

    public String getPrograma() {
        return programa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getContadorDePrograma() {
        return contadorDePrograma;
    }

    public void setContadorDePrograma() {
        this.contadorDePrograma = processo.getLinhas().get(cp);
        cp++;
    }

    public Integer getRegistradorX() {
        return registradorX;
    }

    public void setRegistradorX(int registradorX) {
        this.registradorX = registradorX;
    }

    public Integer getRegistradorY() {
        return registradorY;
    }

    public void setRegistradorY(int registradorY) {
        this.registradorY = registradorY;
    }

    public Integer getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(int bloqueado) {
        this.bloqueado = bloqueado;
    }
}