package compilador;
import java.io.*;
import java.awt.*;
import java.util.*;
import java.util.ArrayList;

public class AnalisadorLexico {
    private Arquivo arq;
    private Tabela tab;
    private boolean continua;
    private ArrayList codigo;
 
    public AnalisadorLexico() {
      tab= new Tabela();
      arq = new Arquivo();
      codigo = new ArrayList();
      continua=true;
    }

    public void abreArquivo(String arquivo) {
        arq.abreArquivo(arquivo);

    }

    public Tabela getTabela() {
        return tab;
    }

    public void criaArquivo(String arquivo, String conteudoArquivo) {

        arq.criaArquivo(arquivo, conteudoArquivo);

    }

    public String preencheTextArea(){
    	return arq.preencheTextArea();
    }

    public int getLinhaArquivo() {
        return arq.getLinha();
    }
    
    /*public ArrayList getCodigo() 
    {
    int i = 0;
    do {
      System.out.println(codigo.get(i));
      i++;
    } while (i < codigo.size());    
      return codigo;
    }*/
    
    public ArrayList getCodigo()
    {
      return codigo;
    }

/*    public String exibir(){
    	String mostrar="";
    	while(!arq.getEOF()){
    	  mostrar+= lexico()+" \n";
    	}

    	return mostrar;
    }*/

    public int getToken(){    //Legenda: -1 (fim de arquivo), ,  n>=0 (endereco na tabela)
        String lexema="";   //         -2 (finalizador constante alfanumerica)
        char c =' ';        //         -3 (formatacao do sinal)
        int estado=0;       //         -4 (caracter ou token invalido)
        int ender=-2;       //         -5 (erro na formatacao do identificador)
        boolean inteira = true;

        while (continua){
          int num=0;
          char aux=' ';
          switch (estado){
              case 0:
                        System.out.println("Entrei no 0");
                        System.out.println("Lexema: "+lexema+" Caracter: "+c);
                        c=arq.proxCaracter();
                        lexema+=c;
                        System.out.println("Lexema: "+lexema+" Caracter: "+c);
                        if(c=='<') estado=1;
                        else if(c=='=') estado=6;
                        else if(c=='>') estado=7;
                        else if(c == ' '||c=='\n'){
                           if (c == '\n') {
                            String s = new String("\n");
                            codigo.add(s);
                        	 }
                           estado=0;
                        	 lexema="";
                        }
                        else if(c=='\0') {
                          //String s = new String("Fim de arquivo");
                          //codigo.add(s);
                          return -1;
                        }
                        else estado=10;
                        break;

              case 1:   c = arq.proxCaracter(); // operador logico
                        lexema = lexema + c;
                        if (c == '-') {
                            estado = 2;
                        } else {
                            if (c == '=') {
                                estado = 3;
                            } else {
                                if (c == '>') {
                                    estado = 4;
                                } else {
                                    estado = 5;
                                }
                            }
                        }
                        break;

              case 2:
                       ender = tab.pesqTab(lexema);
                       codigo.add(lexema);
                       return ender;

              case 3:
                       ender = tab.pesqTab(lexema);
                       codigo.add(lexema);
                       return ender;

              case 4:
                       ender = tab.pesqTab(lexema);
                       codigo.add(lexema);
                       return ender;

              case 5:  arq.retroCaracter(1);
                  lexema = lexema.substring(0, lexema.length()-1);
                  c = lexema.charAt(lexema.length()-1);
                  System.out.println("Case 05 posicao: "+arq.getPosicao()+" "+arq.getLinha());
                  ender = tab.pesqTab(lexema);
                  codigo.add(lexema);
                  return ender;


              case 6:
                  ender = tab.pesqTab(lexema);
                  codigo.add(lexema);
                  return ender;


              case 7:  c = arq.proxCaracter();
                  lexema += c;
                  if (c == '=') {
                      estado = 8;
                  } else {
                      estado = 9;
                  }
                  break;

              case 8:
                  ender = tab.pesqTab(lexema);
                  codigo.add(lexema);
                  return ender;

              case 9: arq.retroCaracter(1);
                  lexema = lexema.substring(0, lexema.length()-1);
                  c = lexema.charAt(lexema.length()-1);
                  ender = tab.pesqTab(lexema);
                  codigo.add(lexema);
                  return ender;

              case 10:     // identificador
                  System.out.println("Entrei no 10");
                  System.out.println("Lexema: "+lexema+" Caracter: "+c);
                  if (Character.isLetter(c)) {
                      estado = 11;
                  } else {
                      estado = 13;
                  }
                  break;

              case 11: c = arq.proxCaracter();
                  System.out.println("Entrei no 11");
                  lexema += c;
                  if (Character.isLetterOrDigit(c)) {
                      estado = 11;
                  } else {
                      if (c == '_') {
                          estado = 12;
                      } else {
                          arq.retroCaracter(1);
                          lexema = lexema.substring(0, lexema.length()-1);
                          c = lexema.charAt(lexema.length()-1);
                          ender = tab.pesqTab(lexema);
                          if (ender != -1) {
                              codigo.add(lexema);
                              return ender;
                          } else {
                              tab.insereSimb(lexema,"identificador");
                              ender = tab.pesqTab(lexema);
                              codigo.add(lexema);
                              return ender;
                          }
                      }
                  }
                  break;

              case 12: c = arq.proxCaracter();
                  lexema += c;
                  if (Character.isLetterOrDigit(c)) {
                      estado = 11;
                  } else {
                      lexema = lexema.substring(0, lexema.length()-1);
                      arq.retroCaracter(1);
                      c = lexema.charAt(lexema.length()-1);
                      return -5;
                  }
                  break;

              case 13: // constante numerica
                  System.out.println("Entrei no 13");
                  System.out.println("Lexema: "+lexema+" Caracter: "+c);
                  if (Character.isDigit(c)) {
                      estado = 14;
                  } else {
                      estado = 18;
                  }
                  break;

              case 14: c = arq.proxCaracter();
                  lexema += c;
                  System.out.println("Entrei no 14");
                  System.out.println("Lexema: "+lexema+" Caracter: "+c);
                  if (Character.isDigit(c)) {
                      estado = 14;
                  } else {
                      if (c == '.') {
                          estado = 15;
                      } else {
                          arq.retroCaracter(1);
                          lexema = lexema.substring(0, lexema.length()-1);
                          c = lexema.charAt(lexema.length()-1);
                          estado = 17;
                      }
                  }
                  break;

             case 15: c = arq.proxCaracter();
                  lexema += c;
                  System.out.println("Entrei no 15");
                  System.out.println("Lexema: "+lexema+" Caracter: "+c);
                  if (!Character.isDigit(c)) {
                      arq.retroCaracter(2);
                      lexema = lexema.substring(0, lexema.length()-2);
                      c = lexema.charAt(lexema.length()-1);
                      System.out.println("Case 15 Lexema: "+lexema+" Caracter: "+c);
                      estado = 17;
                  } else {
                      inteira = false;
                      estado = 16;
                  }
                  break;

             case 16: c = arq.proxCaracter();
                  System.out.println("Case 16 Lexema: "+lexema+" Caracter: "+c);
                 lexema += c;
                 if (Character.isDigit(c)) {
                     estado = 16;
                 } else {
                     arq.retroCaracter(1);
                     lexema = lexema.substring(0, lexema.length()-1);
                     c = lexema.charAt(lexema.length()-1);
                     estado = 17;
                 }
                 break;

             case 17:
                 System.out.println("Case 17 Lexema: "+lexema+" Caracter: "+c);
                 ender = tab.pesqTab(lexema);
                 if (ender != -1) {
                     codigo.add(lexema);
                     return ender;
                 } else {
                     if (inteira) {
                         tab.insereSimb(lexema, "constante inteira");
                     } else {
                         tab.insereSimb(lexema, "constante real");
                         inteira = true;
                     }
                     ender = tab.pesqTab(lexema);
                     codigo.add(lexema);
                     return ender;
                 }

             case 18: // delimitadores
                 System.out.println("Entrei no 18");
                 if ((c == '\n') || (c == ' ') || (c == '\t')) {
                     estado = 19;
                 } else {
                     estado = 20;
                 }
                 break;

              case 19:
                     estado = 0;
                     lexema = "";
                 break;

             case 20: // constante alfanumerica
                 System.out.println("Entrei no 20");
                 if (Character.toString(c).equals("'")) {
                     estado = 21;
                 } else {
                     estado = 23;
                 }
                 break;


             case 21: c = arq.proxCaracter();
                 lexema += c;
                 if (Character.toString(c).equals("'")) {
                     estado = 22;
                 } else {
                     if ((c == '\n') || (c == '\0')) {
                         arq.retroCaracter(1);
                         lexema = lexema.substring(0, lexema.length()-1);
                         c = lexema.charAt(lexema.length()-1);
                         return -2;
                     } else {
                         estado = 21;
                     }
                 }
                 break;

             case 22:
                 ender = tab.pesqTab(lexema);
                 if (ender != -1) {
                     codigo.add(lexema);
                     return ender;
                 } else {
                     tab.insereSimb(lexema, "constante alfanumérica");
                     ender = tab.pesqTab(lexema);
                     codigo.add(lexema);
                     return ender;
                 }


             case 23: // comentario
                 System.out.println("Entrei no 23");
                 if (c == '/') {
                    estado = 24;
                 } else {
                    estado = 29;
                 }
                 break;

            case 24:
                  System.out.println("Entrei no 24");
                  c = arq.proxCaracter();
                  lexema += c;
                  if (c == '/') {
                      estado = 25;
                  } else {
                      if (Character.toString(c).equals("*")) {
                          estado = 26;
                      } else {
                          arq.retroCaracter(1);
                          lexema = lexema.substring(0, lexema.length() -1);
                          c = lexema.charAt(lexema.length()-1);
                          estado = 29;
                      }
                  }
                  break;

             case 25: arq.desprezaLinha();
                 arq.retroCaracter(2);
                 System.out.println("Entrei no 25");
                 lexema = "";
                 estado = 0;
                 break;

             case 26:
                 System.out.println("Entrei no 26");
                 c = arq.proxCaracter();
                 lexema += c;
                 if (c == '*') {
                    c = arq.proxCaracter();
                    lexema += c;
                    if (c == '/')  {
                        estado = 27;
                    } else {
                      if (c != '\0') {
                        estado = 26;
                      } else {
                        lexema = "";
                        estado = 0;
                      }
                    }
                 } else {
                    if (c != '\0') {
                      estado = 26;
                    } else {
                        lexema = "";
                        estado = 0;
                    }
                 }
                 break;

             case 27:
                System.out.println("Entrei no 27");
                if (Character.toString(c).equals("/")) {
                    estado = 28;
                }
                break;

              case 28:
                System.out.println("Entrei no 28");
                estado = 0;
                lexema = "";
                break;

              case 29:
                  System.out.println("Entrei no 29");
                  System.out.println("Caracter: "+c+" Lexema: "+lexema);
                 if ((c == '+') || (c == '-') || (c == '/') || (c == '*')) {
                     estado = 30;
                 } else {
                     estado = 31;
                 }
                 break;

            case 30:
                  System.out.println("Entrei no 30");
                  ender = tab.pesqTab(lexema);
                  codigo.add(lexema);
                  return ender;

             case 31:
                 System.out.println("Entrei no 31");
                 if ((c == '(') || (c == ')') || (c == ';') || (c == ',') || (c == ':') || (c == '[') || (c == ']')) {
                     estado = 32;
                 } else {
                     if (c == '.') {
                         estado = 33;
                     } else {
                         estado = 100;
                     }
                 }
                 break;


             case 32:
                 ender = tab.pesqTab(lexema);
                 codigo.add(lexema);
                 return ender;

             case 33: c = arq.proxCaracter();
                  System.out.println("Entrei no 33");
                 lexema += c;
                 if (c == '.') {
                     estado = 34;
                 } else {
                     arq.retroCaracter(1);
                     lexema = lexema.substring(0, lexema.length()-1);
                     c = lexema.charAt(lexema.length()-1);
                     return -3;
                 }

            case 34:
                ender = tab.pesqTab(lexema);
                codigo.add(lexema);
                return ender;

             default:
                System.out.println("Caracter: "+c+" Lexema: "+lexema);
                 return -4;

        }
      }
         return -5;//verificar retorno do metodo
    }

    }
