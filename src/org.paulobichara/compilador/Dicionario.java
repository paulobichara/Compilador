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

public class Dicionario {
    
    public ArrayList dicionario = new ArrayList();
    
    public Dicionario() {
       Equivalencia equiv = new Equivalencia("declare", "declare");
       dicionario.add(equiv);
       equiv = new Equivalencia("inicio", "begin");
       dicionario.add(equiv);
       equiv = new Equivalencia("fim", "end");
       dicionario.add(equiv);
       equiv = new Equivalencia("se", "if");
       dicionario.add(equiv);
       equiv = new Equivalencia("entao", "then");
       dicionario.add(equiv);
       equiv = new Equivalencia("senao", "else");
       dicionario.add(equiv);
       equiv = new Equivalencia("conequivante", "conequivant");
       dicionario.add(equiv);
       equiv = new Equivalencia("vetor", "array");
       dicionario.add(equiv);
       equiv = new Equivalencia("inteiro", "integer");
       dicionario.add(equiv);
       equiv = new Equivalencia("real", "real");
       dicionario.add(equiv);
       equiv = new Equivalencia("caracter", "char");
       dicionario.add(equiv);
       equiv = new Equivalencia("logico", "boolean");
       dicionario.add(equiv);
       equiv = new Equivalencia("enquanto", "while");
       dicionario.add(equiv);
       equiv = new Equivalencia("faca", "do");
       dicionario.add(equiv);
       equiv = new Equivalencia("de", "to");
       dicionario.add(equiv);
       equiv = new Equivalencia("ate", "until");
       dicionario.add(equiv);
       equiv = new Equivalencia("para", "for");
       dicionario.add(equiv);
       equiv = new Equivalencia("repita", "repeat");
       dicionario.add(equiv);
       equiv = new Equivalencia("e", "and");
       dicionario.add(equiv);
       equiv = new Equivalencia("ou", "or");
       dicionario.add(equiv);
       equiv = new Equivalencia("nao", "not");
       dicionario.add(equiv);
       equiv = new Equivalencia("escreva", "write");
       dicionario.add(equiv);
       equiv = new Equivalencia("leia", "read");
       dicionario.add(equiv);
       equiv = new Equivalencia("funcao", "function");
       dicionario.add(equiv);
       equiv = new Equivalencia("procedimento", "procedure");
       dicionario.add(equiv);
       equiv = new Equivalencia("verdadeiro", "true");
       dicionario.add(equiv);
       equiv = new Equivalencia("falso", "false");
       dicionario.add(equiv);
       equiv = new Equivalencia("<-", ":=");
       dicionario.add(equiv);
    }
    
    
    public String getTraducao(String t) 
    {
      int i = 0;
      boolean achou = false;
      String traducao = null;
      do 
      {
        Equivalencia eq = (Equivalencia)dicionario.get(i);
        if (eq.getPseudocodigo().equalsIgnoreCase(t)) 
        {
          achou = true;
          traducao = eq.getPascal();
        }
        i++;
      } while ((achou == false) && (i < dicionario.size()));
      if (achou == false) 
      {
        return null;
      }else{
        return traducao;
      }
    }

}