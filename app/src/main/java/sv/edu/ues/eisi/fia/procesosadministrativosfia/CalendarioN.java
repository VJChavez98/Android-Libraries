package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class CalendarioN {

    private String objetoFecha;
    private String evento;


    public CalendarioN() {
    }

    public CalendarioN(String objetoFecha, String evento) {
        this.objetoFecha = objetoFecha;
        this.evento = evento;
    }

    public String getObjetoFecha() {
        return objetoFecha;
    }

    public String getEvento() {
        return evento;
    }

    public void setObjetoFecha(String objetoFecha) {
        this.objetoFecha = objetoFecha;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }
}

