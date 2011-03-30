package compilador;

public class Token {

  private String classe;
  private String lexema;

  public Token(String l, String c) {
    this.lexema = l;
    this.classe = c;
  }

  public String getLexema(){
    return this.lexema;
  }

  public String getClasse(){
    return this.classe;
  }
}