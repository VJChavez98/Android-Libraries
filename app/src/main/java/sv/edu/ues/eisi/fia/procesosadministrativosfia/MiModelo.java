package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class MiModelo {


    private int valor;
    private String texto;

    public MiModelo() {
    }

    public MiModelo(int valor, String texto) {
        this.valor = valor;
        this.texto = texto;
    }



    public int getValor() {
        return valor;
    }

    public String toString() {
        return texto;
    }


    public void setValor(int valor) {
        this.valor = valor;
    }

    public void setTexto(String text) {
        this.texto = text;
    }
}
