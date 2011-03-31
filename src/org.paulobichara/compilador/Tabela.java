/*
   Copyright 2011 Paulo Augusto Dacach Bichara

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package compilador;

import java.util.ArrayList;

public class Tabela {

  private Token tk;
  private ArrayList tabela;

  public Tabela() {
    tabela= new ArrayList();
    preencheTab();
    System.out.println(tabela.size()+"");
  }

  public void preencheTab(){
        tabela.add( new Token("inicio","palavra reservada"));
        tabela.add( new Token("fim","palavra reservada"));
        tabela.add( new Token("procedimento","palavra reservada"));
        tabela.add( new Token("declare","palavra reservada"));
        tabela.add( new Token("constante","palavra reservada"));
        tabela.add( new Token("funcao","palavra reservada"));
        tabela.add( new Token("inteiro","palavra reservada"));
        tabela.add( new Token("real","palavra reservada"));
        tabela.add( new Token("caracter","palavra reservada"));
        tabela.add( new Token("logico","palavra reservada"));
        tabela.add( new Token("vetor", "palavra reservada"));
        tabela.add( new Token("de", "palavra reservada"));
        tabela.add( new Token("se", "palavra reservada"));
        tabela.add( new Token("senao", "palavra reservada"));
        tabela.add( new Token("entao", "palavra reservada"));
        tabela.add( new Token("repita", "palavra reservada"));
        tabela.add( new Token("enquanto", "palavra reservada"));
        tabela.add( new Token("faca", "palavra reservada"));
        tabela.add( new Token("falso", "palavra reservada"));
        tabela.add( new Token("verdadeiro", "palavra reservada"));
        tabela.add( new Token("ate", "palavra reservada"));
        tabela.add( new Token("para", "palavra reservada"));
        tabela.add( new Token("escreva", "palavra reservada"));
        tabela.add( new Token("leia", "palavra reservada"));
        tabela.add( new Token("e", "operador logico"));
        tabela.add( new Token("nao", "operador logico"));
        tabela.add( new Token("ou", "operador logico"));
        tabela.add( new Token("+", "operador aritmetico"));
        tabela.add( new Token("-", "operador aritmetico"));
        tabela.add( new Token("/", "operador aritmetico"));
        tabela.add( new Token("*", "operador aritmetico"));
        tabela.add( new Token("<", "operador relacional"));
        tabela.add( new Token(">", "operador relacional"));
        tabela.add( new Token("<=", "operador relacional"));
        tabela.add( new Token(">=", "operador relacional"));
        tabela.add( new Token("<>", "operador relacional"));
        tabela.add( new Token("=", "operador relacional"));
        tabela.add( new Token("<-", "operador relacional"));
        tabela.add( new Token("(", "sinal"));
        tabela.add( new Token(")", "sinal"));
        tabela.add( new Token(";", "sinal"));
        tabela.add( new Token(":", "sinal"));
        tabela.add( new Token(",", "sinal"));
        tabela.add( new Token("..", "sinal"));
        tabela.add( new Token("[", "sinal"));
        tabela.add( new Token("]", "sinal"));
    }

    public int pesqTab(String t){
        int ender= -1;
        if(!tabela.isEmpty()){
            for(int i=0;i<tabela.size();i++){
                tk= (Token)tabela.get(i);
                if((tk.getLexema()).equalsIgnoreCase(t)){
                    ender= i;
                }
            }
        }
        return ender;
    }
    

    public void insereSimb(String l, String c){
        tabela.add(new Token(l,c));
    }

    public String getClasse(int ind){
    	return (String)((Token)tabela.get(ind)).getClasse();
    }
    
    public String getClasse(String t){
      int i = pesqTab(t);
    	return (String)((Token)tabela.get(i)).getClasse();
    }
    
    public String getNomeToken(int ind){
    	return (String)((Token)tabela.get(ind)).getLexema();
    }

    public void limparTab(){
        tabela.clear();
    }
}
