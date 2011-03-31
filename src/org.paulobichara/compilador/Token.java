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