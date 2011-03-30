package compilador;

public class Equivalencia {
    
    private String pseudocodigo;
    private String pascal;
    
    public Equivalencia(String pseudo, String pasc) {
       this.pseudocodigo = pseudo;
       this.pascal = pasc;
    }
    
    public String getPseudocodigo(){
       return pseudocodigo;
    }
    
    public String getPascal(){
       return pascal;
    }
}
