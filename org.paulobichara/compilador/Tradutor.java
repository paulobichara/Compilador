package compilador;

import java.util.*;

public class Tradutor {
  private Dicionario dicionario = null;
  private ArrayList texto,aux_texto = null;
  private String traduzido = null;
  private Tabela tab = null;

  public Tradutor(ArrayList txt, Tabela t) 
  {
    texto = txt;
    dicionario = new Dicionario();
    traduzido = new String();
    tab = t;
  }
  
  /*public String traduzir() 
  {
    String termo = null;
    String msg = null;
    int i = 0;
    do {
      aux_texto = (ArrayList)texto.get(i);
      //termo = (String)texto.get(i);
      termo = aux_texto.toString();
      System.out.println("------------------------\n" +termo);
      msg = this.traduzirTermo(termo);
      i++;
    } while ((i < texto.size()) && (msg != null));
    return traduzido;
    
  }*/
  
  public String traduzir()
  {
    String token;
    String traducao;
    int indice = 0;
    while(indice < texto.size()) {
      token = texto.get(indice).toString();
      traducao = dicionario.getTraducao(token);
      if (traducao == null){
        traduzido = traduzido + token;  
      }else{ 
        traduzido = traduzido + traducao;
      }
      indice++;
    }
    return traduzido;
  }
  
  /*public String traduzirTermo(String t) 
  {
    String termo = t;
    System.out.println("a bosta do termo ehg:  " +termo);
    String classe = tab.getClasse(t);
    if (termo.equals("\n")) 
    {
      traduzido = traduzido + "\n";
    } else 
    {
      if (termo.equals("Fim de arquivo")) 
      {
        return termo;
      }else 
      {
        if (classe.equals("palavra reservada")) 
        {
          String traducao = dicionario.getTraducao(termo);
          traduzido = traduzido + traducao + " ";
        } else 
        {
          traduzido = traduzido + termo + " ";
        }
      }
    }
    return null;

  }*/
}