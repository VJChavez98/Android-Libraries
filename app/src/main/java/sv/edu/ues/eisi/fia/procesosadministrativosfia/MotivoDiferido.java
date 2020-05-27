package sv.edu.ues.eisi.fia.procesosadministrativosfia;

class MotivoDiferido {
    private int idMotidoDiferido;
    private String nombreMotivoDiferido;


    public String getNombreMotivoDiferido() {
        return nombreMotivoDiferido;
    }

    public void setNombreMotivoDiferido(String nombreMotivoDiferido) {
        this.nombreMotivoDiferido = nombreMotivoDiferido;
    }

    public int getIdMotidoDiferido() {
        return idMotidoDiferido;
    }

    public void setIdMotidoDiferido(String NombreMotivo) {
        int id;
        switch (NombreMotivo){
            case "Salud":
               id=1;
               this.idMotidoDiferido = id;
               break;
            case "Trabajo":
                id=2;
                this.idMotidoDiferido=id;
                break;
            case "Interferencia":
                id=3;
                this.idMotidoDiferido=id;
                break;
            case "Viaje":
                id=4;
                this.idMotidoDiferido=id;
                break;
            case "Duelo":
                id=5;
                this.idMotidoDiferido=id;
                break;
            case "Otro":
                id=6;
                this.idMotidoDiferido=id;
                break;

        }
    }
}
