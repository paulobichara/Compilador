package compilador;

import java.util.*;

public class AnalisadorSintatico {
    AnalisadorLexico lexico = null;
    Tabela tab = null;
    int enderToken;
    String classeToken = null;
    String nomeToken = null;
    String erro = null;

    public AnalisadorSintatico() {
        lexico = new AnalisadorLexico();
        tab = lexico.getTabela();
    }
    
    public Tabela getTabela() 
    {
      return tab;
    }
    
    public ArrayList getCodigo() 
    {
      return lexico.getCodigo();
    }

    
    public String reconhece(){
        System.out.println("ENTREI NO RECONHECE()");
        enderToken = lexico.getToken();
        if (enderToken < 0) {
          nomeToken = null;
          if (erro == null) 
          {
            erro = new String();
          } else 
          {
            erro = erro + "\n\n";
          }
          if (enderToken == -1){
            nomeToken = null;
            classeToken = null;
            System.out.println("Fim de arquivo");
            erro = erro + "Fim de arquivo";
          }
           if (enderToken == -2){
             erro = erro + "Erro lexico na linha "+lexico.getLinhaArquivo()+"\n";
             erro = erro + "Finalizador constante alfanumerica";
           }
           if (enderToken == -3){
              erro = erro + "Erro lexico na linha "+lexico.getLinhaArquivo()+"\n";
              erro = erro + "Formatação do sinal";
           }
           if (enderToken == -4){
              erro = erro + "Erro lexico na linha "+lexico.getLinhaArquivo()+"\n";
              erro = erro + "Caracter ou token inválido";
           }
           if (enderToken == -5){
              erro = erro + "Erro lexico na linha "+lexico.getLinhaArquivo()+"\n";
              erro = erro + "Erro na formatação do identificador";
           }
        } else{
              classeToken = tab.getClasse(enderToken);
              nomeToken = tab.getNomeToken(enderToken);
         }
         return erro;
     }


    public void abreArquivo(String nomeArq)
    {
      lexico.abreArquivo(nomeArq);
    }

    public void criaArquivo(String arquivo, String conteudoArquivo)
    {
      lexico.criaArquivo(arquivo, conteudoArquivo);
    }

    public String preencheTextArea()
    {
      return lexico.preencheTextArea();
    }

    public String s() {
        System.out.println("ENTREI NO S()");
            erro = reconhece();
            if (erro == null) {
              erro = definicoes();
              if (erro == null) {
                  erro = bloco();
                  if (erro.equals("Fim de arquivo")) {
                      return "Análise Sintática efetuada com sucesso!";
                  }
              }
            }
        
        return erro;
    }

    public String definicoes(){
        System.out.println("ENTREI NO DEFINICOES()");
        if (nomeToken.equalsIgnoreCase("constante")) {
          erro = def_const();
          if (erro == null) {
              erro = definicoes();
          }
        }else{
          if (nomeToken.equalsIgnoreCase("declare")) {
            erro = def_var();
            if (erro == null) {
                erro = definicoes();
            }
          }else{
            if (nomeToken.equalsIgnoreCase("funcao")) {
              erro = def_funcoes();
              if (erro == null) {
                  erro = definicoes();
              }
            }else{
              if (nomeToken.equalsIgnoreCase("procedimento")) {
                erro = def_procedimentos();
                if (erro == null) {
                    erro = definicoes();
                }
              }
            }
          }
        }
        return erro;
    }

    public String def_const(){
        System.out.println("ENTREI NO DEF_CONST()");
        erro = reconhece();
        if (erro == null)
          erro = lista_const();
        return erro;
    }

    public String lista_const(){
        System.out.println("ENTREI NO LISTA_CONST()");
        if (classeToken.equalsIgnoreCase("identificador")){
            erro = reconhece();
            if (erro == null){
                if (nomeToken.equalsIgnoreCase("=")) {
                    erro = reconhece();
                    if (erro == null){
                        erro = valor();
                        if (erro == null){
                            if (nomeToken.equalsIgnoreCase(";")) {
                                erro = reconhece();
                                if (erro == null){
                                  erro = lista_const1();
                                }
                            } else {
                                erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                                erro = erro + "Esperado: ';' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
                            }
                        }
                    }
                } else {
                    erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                    erro = erro + "Esperado: '=' (operador relacional de atribuição), Presente: "+nomeToken+" ("+classeToken+")";
                }
            }
        }else {
            erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
            erro = erro + "Esperado: (identificador), Presente: "+nomeToken+" ("+classeToken+")";
        }
        return erro;
    }

    public String lista_const1(){
        System.out.println("ENTREI NO LISTA_CONST1()");
      if (classeToken.equalsIgnoreCase("identificador"))
        erro = lista_const();
      return erro;
    }

    public String valor(){
        System.out.println("ENTREI NO VALOR()");
      if (classeToken.equalsIgnoreCase("constante inteira") || classeToken.equalsIgnoreCase("constante real") || classeToken.equalsIgnoreCase("constante alfanumérica") || nomeToken.equalsIgnoreCase("verdadeiro")      || nomeToken.equalsIgnoreCase("falso")){
          erro = reconhece();
      }else{
          erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
          erro = erro + "Esperado: constante (inteira | real | alfanumérica) ou 'verdadeiro' ou 'falso' (palavra reservada), Presente: "+nomeToken+" ("+classeToken+")";
      }
      return erro;
    }

    public String def_var(){
        System.out.println("ENTREI NO DEF_VAR()");
          erro = reconhece();
          if (erro == null) {
            erro = lista_var();
          }
        return erro;
    }

    public String lista_var(){
        System.out.println("ENTREI NO LISTA_VAR()");
        if (classeToken.equalsIgnoreCase("identificador")){
            erro = lista_id();
            if (erro == null){
                if (nomeToken.equals(":")) {
                    erro = reconhece();
                    if (erro == null){
                        erro = tipo();
                        if (erro == null){
                            if (nomeToken.equals(";")) {
                                erro = reconhece();
                                if (erro == null){
                                  erro = lista_var1();
                                }
                            } else {
                                erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                                erro = erro + "Esperado: ';' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
                            }
                        }
                    }
                } else {
                    erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                    erro = erro + "Esperado: ':' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
                }
            }
        }else{
          erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
          erro = erro + "Esperado: (identificador), Presente: "+nomeToken+" ("+classeToken+")";
        }
        return erro;
    }

    public String lista_var1(){
        System.out.println("ENTREI NO LISTA_VAR1()");
      if (classeToken.equalsIgnoreCase("identificador"))
        erro = lista_var();
      return erro;
    }

    public String lista_id(){
        System.out.println("ENTREI NO LISTA_ID()");
        if (classeToken.equals("identificador")) {
            erro = reconhece();
            if (erro == null){
              erro = lista_id1();
            }
        } else {
            erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
            erro = erro + "Esperado: (identificador), Presente: "+nomeToken+" ("+classeToken+")";
        }
        return erro;
    }

    public String lista_id1(){
        System.out.println("ENTREI NO LISTA_ID1()");
      if (nomeToken.equalsIgnoreCase(",")) {
        erro = reconhece();
        if (erro == null){
          erro = lista_id();
        }
      }
      return erro;
    }

    public String tipo(){
        System.out.println("ENTREI NO TIPO()");
      if (((nomeToken.equalsIgnoreCase("inteiro")) || (nomeToken.equalsIgnoreCase("real")) || (nomeToken.equalsIgnoreCase("caracter")) || (nomeToken.equalsIgnoreCase("logico")))) {
        erro = reconhece();
      } else{
        if (nomeToken.equalsIgnoreCase("vetor")) {
          erro = reconhece();
          if (erro == null){
              if (nomeToken.equalsIgnoreCase("[")) {
                erro = reconhece();
                if (erro == null){
                    erro = faixa();
                    if (erro == null){
                        if (nomeToken.equalsIgnoreCase("]")) {
                          erro = reconhece();
                          if (erro == null){
                              if (nomeToken.equalsIgnoreCase("de")) {
                                erro = reconhece();
                                if (erro == null){
                                  erro = tipo_simples();
                                }
                              } else {
                                  erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                                  erro = erro + "Esperado: 'de' (palavra reservada), Presente: "+nomeToken+" ("+classeToken+")";
                              }
                          }
                        } else {
                            erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                            erro = erro + "Esperado: ']' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
                        }
                    }
                }
              } else {
                  erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                  erro = erro + "Esperado: '[' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
              }
          }
        } else {
            erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
            erro = erro + "Esperado: 'inteiro' | 'real' | 'caracter' | 'logico' | 'vetor' (palavra reservada), Presente: "+nomeToken+" ("+classeToken+")";
        }
      }
      return erro;
    }

    public String faixa(){
        System.out.println("ENTREI NO FAIXA()");
      if (classeToken.equalsIgnoreCase("constante inteira")){
        erro = reconhece();
        if (erro == null){
            if (nomeToken.equalsIgnoreCase("..")) {
              erro = reconhece();
              if (erro == null){
                  if (classeToken.equalsIgnoreCase("constante inteira")){
                    erro = reconhece();
                    if (erro == null){
                      erro = faixa1();
                    }
                  } else {
                      erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                      erro = erro + "Esperado: (constante inteira), Presente: "+nomeToken+" ("+classeToken+")";
                  }
              }
            } else {
                erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                erro = erro + "Esperado: '..' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
            }
        }
      } else {
          erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
          erro = erro + "Esperado: (constante inteira), Presente: "+nomeToken+" ("+classeToken+")";
      }
      return erro;
    }

    public String faixa1(){
      System.out.println("ENTREI NO FAIXA1()");
      if (nomeToken.equalsIgnoreCase(",")) {
        erro = reconhece();
        if (erro == null){
          faixa();
        }
      }
      return erro;
    }

    public String bloco(){
        System.out.println("ENTREI NO BLOCO()");
      if (nomeToken.equalsIgnoreCase("inicio")) {
        erro = reconhece();
        if (erro == null){
            erro = lista_cmd();
            if (erro == null){
              if (nomeToken.equalsIgnoreCase("fim")) {
                erro = reconhece();
               } else {
                  erro = "Erro sintático (token inesperado) na linha "+lexico.getLinhaArquivo()+"\n";
                  erro = erro + "Esperado: lista de comandos, Presente: "+nomeToken+" ("+classeToken+")";
              }
            } else 
            {
              if (erro.equals("Fim de arquivo")) {
                erro = "Fim Inesperado do código! O bloco nao foi finalizado!";
              }
            }
        } else 
        {
          if (erro.equals("Fim de arquivo")) 
          {
            erro = "Fim Inesperado do código! O bloco nao foi finalizado!";
          }
        }
      } else {
          erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
          erro = erro + "Esperado: 'inicio' (palavra reservada), Presente: "+nomeToken+" ("+classeToken+")";
      }
      return erro;
    }

    public String lista_cmd(){
        System.out.println("ENTREI NO LISTA_CMD()");
         erro = cmd();
         if (erro == null){
           if (nomeToken.equalsIgnoreCase(";")) {
            erro = reconhece();
            if (erro == null) {
              erro = lista_cmd1();
            }
           } else 
           {
              erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
              erro = erro + "Esperado: ';' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
           }
         }
       return erro;
    }


    public String lista_cmd1(){
        System.out.println("ENTREI NO LISTA_CMD1()");
        if ((nomeToken.equalsIgnoreCase("se")) || (nomeToken.equalsIgnoreCase("para")) || (nomeToken.equalsIgnoreCase("repita")) || (nomeToken.equalsIgnoreCase("enquanto")) || (nomeToken.equalsIgnoreCase("leia")) || (nomeToken.equalsIgnoreCase("escreva")) || (classeToken.equalsIgnoreCase("identificador"))) {
          erro = lista_cmd();
        }
        return erro;
    }

    public String cmd(){
        System.out.println("ENTREI NO CMD()");
      if (nomeToken.equalsIgnoreCase("se")) {
        erro = cmd_se();
      }else{
        if (nomeToken.equalsIgnoreCase("para")) {
          erro = cmd_para();
        }else{
          if (nomeToken.equalsIgnoreCase("repita")) {
            erro = cmd_repita();
          }else{
            if (nomeToken.equalsIgnoreCase("enquanto")) {
              erro = cmd_enquanto();
            }else{
              if (nomeToken.equalsIgnoreCase("leia")) {
                 erro = cmd_leia();
              }else{
                if (nomeToken.equalsIgnoreCase("escreva")) {
                   erro = cmd_escreva();
                }else{
                  if (classeToken.equalsIgnoreCase("identificador")){
                     erro = reconhece();
                     if (erro == null){
                        erro = cmd1();  
                     }
                  } else {
                      erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                      erro = erro + "Esperado: 'se' ou 'para' ou 'repita' ou 'enquanto' ou 'leia' ou 'escreva' (palavra reservada) ou (identificador), Presente: "+nomeToken+" ("+classeToken+")";
                  }
                }
              }
            }
          }
        }
      }
      return erro;
    }

    public String cmd1(){
        System.out.println("ENTREI NO CMD1()");
      if ((nomeToken.equalsIgnoreCase("[")) || (nomeToken.equalsIgnoreCase("<-"))) {
        erro = cmd_atr();
      } else {
        if (nomeToken.equalsIgnoreCase("(")) {
          erro = cmd_chamada();
        }// else {
           // erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
           // erro = erro + "Esperado: '[', '<-' ou '(' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
        //}
      }
      return erro;
    }

    public String def_funcoes(){
        System.out.println("ENTREI NO DEF_FUNCOES()");
        if (nomeToken.equalsIgnoreCase("funcao")) {
            erro = reconhece();
            if (erro == null){
                if (classeToken.equalsIgnoreCase("identificador")){
                    erro = reconhece();
                    if (erro == null){
                        erro = def_funcoes1();
                        if (erro == null){
                            if (nomeToken.equalsIgnoreCase(":")) {
                                erro = reconhece();
                                if (erro == null){
                                    erro = tipo_simples();
                                    if (erro == null){
                                      erro = def_var1();
                                      if (erro == null){
                                        erro = bloco();
                                      }
                                    }
                                }
                            } else {
                                erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                                erro = erro + "Esperado: ':' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
                            }
                        }
                    }
                } else {
                    erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                    erro = erro + "Esperado: (identificador), Presente: "+nomeToken+" ("+classeToken+")";
    
                }
            }
        } else {
            erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
            erro = erro + "Esperado: 'funcao' (palavra reservada), Presente: "+nomeToken+" ("+classeToken+")";
        }
        return erro;
    }

    public String def_funcoes1(){
        System.out.println("ENTREI NO DEF_FUNCOES1()");
        if (nomeToken.equalsIgnoreCase("(")) {
            erro = reconhece();
            if (erro == null){
                erro = lista_par_def();
                if (erro == null){
                    if (nomeToken.equalsIgnoreCase(")")) {
                        erro = reconhece();
                    }
                }
            }
        }
        return erro;
    }

    public String tipo_simples(){
        System.out.println("ENTREI NO TIPO_SIMPLES()");
      if ((nomeToken.equalsIgnoreCase("inteiro")) || (nomeToken.equalsIgnoreCase("real")) || (nomeToken.equalsIgnoreCase("caracter")) || (nomeToken.equalsIgnoreCase("logico"))) {
        erro = reconhece();
      }else{
          erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
          erro = erro + "Esperado: 'inteiro' ou 'real' ou 'caracter' ou 'logico' (palavra reservada), Presente: "+nomeToken+" ("+classeToken+")";
      }
      return erro;
    }

    public String lista_par_def(){
        System.out.println("ENTREI NO LISTA_PAR_DEF()");
      if (classeToken.equalsIgnoreCase("identificador")){
          erro = lista_id();
          if (erro == null){
              if (nomeToken.equalsIgnoreCase(":")) {
                erro = reconhece();
                if (erro == null){
                    erro = tipo_simples();
                    if (erro == null){
                        if (nomeToken.equalsIgnoreCase(";")) {
                          erro = reconhece();
                          if (erro == null){
                            erro = lista_par_def1();
                          }
                        } else {
                            erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                            erro = erro + "Esperado: ';' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
                        }
                    }
                }
              } else {
                  erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                  erro = erro + "Esperado: ':' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
              }
          }
      }else{
          erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
          erro = erro + "Esperado: (identificador), Presente: "+nomeToken+" ("+classeToken+")";
      }
      return erro;
    }

    public String lista_par_def1(){
        System.out.println("ENTREI NO LISTA_PAR_DEF1()");
      if (classeToken.equalsIgnoreCase("identificador")){
        erro = lista_par_def();
      }
      return erro;
    }

    public String def_procedimentos(){
        System.out.println("ENTREI NO DEF_PROCEDIMENTOS()");
      if (nomeToken.equalsIgnoreCase("procedimento")) {
        erro = reconhece();
        if (erro == null){
            if (classeToken.equalsIgnoreCase("identificador")){
              erro = reconhece();
              if (erro == null){
                  erro = def_funcoes1();
                  if (erro == null){
                    erro = def_var1();
                    if (erro == null){
                      erro = bloco();
                    }
                  }
              }
            } else {
                erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                erro = erro + "Esperado: (identificador), Presente: "+nomeToken+" ("+classeToken+")";
            }
        }
      } else {
          erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
          erro = erro + "Esperado: 'procedimento' (palavra reservada), Presente: "+nomeToken+" ("+classeToken+")";
      }
      return erro;
    }

    public String def_var1(){
        System.out.println("ENTREI NO DEF_VAR1()");    
      if (nomeToken.equalsIgnoreCase("declare")) {
        erro = def_var();
      }
      return erro;
    }

    public String cmd_leia(){
        System.out.println("ENTREI NO CMD_LEIA()");
      if (nomeToken.equalsIgnoreCase("leia")) {
        erro = reconhece();
        if (erro == null){
            if (nomeToken.equalsIgnoreCase("(")) {
              erro = reconhece();
              if (erro == null){
                  erro = lista_id();
                  if (erro == null){
                      if (nomeToken.equalsIgnoreCase(")")) {
                        erro = reconhece();
                      } else {
                          erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                          erro = erro + "Esperado: ')' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
                      }
                  }
              }
            } else {
                erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                erro = erro + "Esperado: '(' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
            }
        }
      } else {
          erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
          erro = erro + "Esperado: 'leia' (palavra reservada), Presente: "+nomeToken+" ("+classeToken+")";
      }
      return erro;
    }

    public String cmd_escreva(){
      System.out.println("ENTREI NO CMD_ESCREVA()");
      if (nomeToken.equalsIgnoreCase("escreva")) {
        erro = reconhece();
        if (erro == null){
            if (nomeToken.equalsIgnoreCase("(")) {
                erro = reconhece();
                if (erro == null){
                    erro = cmd_escreva1();
                    if (erro == null){
                        if (nomeToken.equalsIgnoreCase(")")) {
                          erro = reconhece();
                        } else {
                            erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                            erro = erro + "Esperado: ')' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
                        }
                    }
                }
              } else {
                  erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                  erro = erro + "Esperado: '(' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
              }
          }
      } else {
            erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
            erro = erro + "Esperado: 'escreva' (palavra reservada), Presente: "+nomeToken+" ("+classeToken+")";
      }
      return erro;
    }

    public String cmd_escreva1(){
        System.out.println("ENTREI NO CMD_ESCREVA1()");
        if (classeToken.equalsIgnoreCase("constante alfanumérica")) {
          erro = reconhece();
          if (erro == null){
            erro = cmd_escreva2();
          }
        } else {
            if (classeToken.equals("identificador")) {
                erro = lista_id();
            } else {
                erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                erro = erro + "Esperado: (constante alfanumérica | identificador), Presente: "+nomeToken+" ("+classeToken+")";
            }
        }
        return erro;
    }

    public String cmd_escreva2(){
        System.out.println("ENTREI NO CMD_ESCREVA2()");
        if (nomeToken.equalsIgnoreCase(",")) {
            erro = reconhece();
            if (erro == null){
              erro = lista_id();
            }
        }
        return erro;
    }

    public String cmd_atr(){
        System.out.println("ENTREI NO CMD_ATR()");
        erro= ind();
        if (erro == null){
            if (nomeToken.equalsIgnoreCase("<-")) {
              erro = reconhece();
              if (erro == null){
                erro = cmd_atr1();
              }
            } else {
                erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                erro = erro + "Esperado: '<-' (operador relacional), Presente: "+nomeToken+" ("+classeToken+")";
            }
        }
        return erro;
    }

    public String cmd_atr1(){
        System.out.println("ENTREI NO CMD_ATR1()");    
      if (((nomeToken.equalsIgnoreCase("("))) || (classeToken.equalsIgnoreCase("identificador")) || (classeToken.equalsIgnoreCase("constante inteira")) || (classeToken.equalsIgnoreCase("constante real"))){
        erro = ea();
      }else{
        if ((nomeToken.equalsIgnoreCase("verdadeiro")) || (nomeToken.equalsIgnoreCase("falso")) || (classeToken.equalsIgnoreCase("constante alfanumérica"))) {
          erro = reconhece();
        } else {
            erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
            erro = erro + "Esperado: 'verdadeiro' ou 'falso' (palavra reservada) ou '(' (sinal) ou (constante inteira | constante real | constante alfanumérica | identificador), Presente: "+nomeToken+" ("+classeToken+")";
        }
      }
      return erro;
    }

    public String ind(){
        System.out.println("ENTREI NO IND()");
      if (nomeToken.equalsIgnoreCase("[")) {
        erro = reconhece();
        if (erro == null){
            erro = lind();
            if (erro == null){
                if (nomeToken.equalsIgnoreCase("]")) {
                  erro = reconhece();
                } else {
                    erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                    erro = erro + "Esperado: ']' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
                }
            }
        }
      }/* else {
          erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
          erro = erro + "Esperado: '[' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
      }*/
      return erro;
    }

    public String lind(){
        System.out.println("ENTREI NO LIND()");
      if ((classeToken.equalsIgnoreCase("identificador")) || (classeToken.equalsIgnoreCase("constante inteira"))){
        erro = i();
        if (erro == null)
          erro = lind1();
      }else{
        erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
        erro = erro + "Esperado: (identificador) ou (constante inteira), Presente: "+nomeToken+" ("+classeToken+")";
      }
      return erro;
    }

    public String lind1(){
        System.out.println("ENTREI NO LIND1()");
      if (nomeToken.equalsIgnoreCase(",")) {
        erro = reconhece();
        if (erro == null)
          erro = lind();
      }
      return erro;
    }

    public String i(){
        System.out.println("ENTREI NO I()");
      if ((classeToken.equalsIgnoreCase("identificador")) || (classeToken.equalsIgnoreCase("constante inteira"))){
        erro = reconhece();
      } else {
          erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
          erro = erro + "Esperado: (identificador) ou (constante inteira), Presente: "+nomeToken+" ("+classeToken+")";
      }
      return erro;
    }

    public String ea(){
        System.out.println("ENTREI NO EA()");    
        erro = t();
        if (erro == null)
          erro = ea1();
        return erro;
    }

    public String ea1(){
        System.out.println("ENTREI NO EA1()");
      if ((nomeToken.equalsIgnoreCase("+")) || (nomeToken.equalsIgnoreCase("-"))){
        erro = reconhece();
        if (erro == null){
          erro = t();
          if (erro == null){
            erro = ea1();
          }
        }
      }
      return erro;
    }

    public String t(){
        System.out.println("ENTREI NO T()");    
        erro = f();
        if (erro == null)
          erro = t1();
        return erro;
    }

    public String t1(){
        System.out.println("ENTREI NO T1()");
      if ((nomeToken.equalsIgnoreCase("*")) || (nomeToken.equalsIgnoreCase("/"))){
        erro = reconhece();
        if (erro == null){
          erro = f();
          if (erro == null){
            erro = t1();
          }
        }
      }
      return erro;
    }

    public String f(){
        System.out.println("ENTREI NO F()");
      if (nomeToken.equalsIgnoreCase("(")) {
        erro = reconhece();
        if (erro == null){
            erro = ea();
            if (erro == null){
                if (nomeToken.equalsIgnoreCase(")")) {
                  erro = reconhece();
                } else {
                    erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                    erro = erro + "Esperado: ')' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
                }
            }
        }
      }else{
        if (classeToken.equalsIgnoreCase("identificador")){
          erro = reconhece();
          if (erro == null){
            erro = f1();
          }
        }else{
          if ((classeToken.equalsIgnoreCase("constante inteira")) || (classeToken.equalsIgnoreCase("constante real"))){
            erro = reconhece();
          } else {
              erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
              erro = erro + "Esperado: '(' (sinal) ou (identificador), Presente: "+nomeToken+" ("+classeToken+")";
          }
        }
      }
      return erro;
    }

    public String f1(){
        System.out.println("ENTREI NO F1()");    
      if (nomeToken.equalsIgnoreCase("[")) {
        erro = ind();
      }else{
        if (nomeToken.equalsIgnoreCase("(")){
          erro = reconhece();
          if (erro == null){
              erro = lp();
              if (erro == null){
                  if (nomeToken.equalsIgnoreCase(")")) {
                    erro = reconhece();
                  } else {
                      erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                      erro = erro + "Esperado: ')' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
                  }
              }
          }
        }
      }
      return erro;
    }

    public String cmd_chamada(){
      System.out.println("ENTREI NO CMD_CHAMADA()");
      if (nomeToken.equalsIgnoreCase("(")) {
        erro = reconhece();
        if (erro == null){
          erro = lp();
        }
        if (nomeToken.equalsIgnoreCase(")")) {
          erro = reconhece();
        }else{
             erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
             erro = erro + "Esperado: '(' ou ')' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
        }
      }
      return erro;
    }

    public String lp(){
      System.out.println("ENTREI NO LP()");
      if (classeToken.equalsIgnoreCase("constante inteira") || classeToken.equalsIgnoreCase("constante real") || nomeToken.equalsIgnoreCase("verdadeiro") || nomeToken.equalsIgnoreCase("falso") || classeToken.equalsIgnoreCase("constante alfanumérica")) {
        erro = valor();
        if (erro == null){
          erro = lp1();  
        }
      }else{
        if (classeToken.equalsIgnoreCase("identificador")){
          erro = reconhece();
          if (erro == null){  
            erro = lp1();
          }
        }else{
          erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
          erro = erro + "Esperado: (constante inteira), (constante real), (verdadeiro), (falso) ou (identificador), Presente: "+nomeToken+" ("+classeToken+")";
        }
      }
      return erro;
    }

    public String lp1(){
      System.out.println("ENTREI NO LP1()");
      if (nomeToken.equalsIgnoreCase(",")) {
        erro = reconhece();
        if (erro == null){
          erro = lp();
        }
      }
      return erro;
    }

    public String cmd_para(){
      System.out.println("ENTREI NO CMD_PARA()");    
      if (nomeToken.equalsIgnoreCase("para")) {
        erro = reconhece();
        if (erro == null){
            if (classeToken.equalsIgnoreCase("identificador")){
              erro = reconhece();
              if (erro == null){
                  erro = ind();
                  if (erro == null){
                      if (nomeToken.equalsIgnoreCase("de")) {
                        erro = reconhece();
                        if (erro == null){
                            erro = i();
                            if (erro == null){
                                if (nomeToken.equalsIgnoreCase("ate")) {
                                  erro = reconhece();
                                  if (erro == null){
                                      erro = i();
                                      if (erro == null){
                                          if (nomeToken.equalsIgnoreCase("faca")) {
                                              erro = reconhece();
                                              if (erro == null){
                                                  erro = d();
                                              }
                                          }else{
                                            erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                                            erro = erro + "Esperado: 'faca' (palavra reservada), Presente: "+nomeToken+" ("+classeToken+")";      
                                          }
                                      }
                                  }
                                } else 
                                {
                                  erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                                  erro = erro + "Esperado: 'ate' (palavra reservada), Presente: "+nomeToken+" ("+classeToken+")";
                                }
                            }
                        }
                      }else
                      {
                        erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                        erro = erro + "Esperado: 'de' (palavra reservada), Presente: "+nomeToken+" ("+classeToken+")";
                      }
                  }
              }
            }else
            {
              erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
              erro = erro + "Esperado:(identificador), Presente: "+nomeToken+" ("+classeToken+")";
            }
        }
      }
      return erro;
    }

    public String cmd_se(){
          System.out.println("ENTREI NO CMD_SE()");
      if (nomeToken.equalsIgnoreCase("se")) {
        erro = reconhece();
        if (erro == null){
          erro = el();
          if (erro == null) {
            if (nomeToken.equalsIgnoreCase("entao")) {
              erro = reconhece();
              if (erro == null){
                erro = d();
                  if (erro == null){
                    erro = cmd_se1();
                  }
              }
            }else{
                erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                erro = erro + "Esperado: 'entao' (palavra reservada), Presente: "+nomeToken+" ("+classeToken+")";
            }
          }
        }
      }else{
          erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
          erro = erro + "Esperado: 'se' (palavra reservada), Presente: "+nomeToken+" ("+classeToken+")";  
      }
      return erro;
    }

    public String cmd_se1(){
      System.out.println("ENTREI NO CMD_SE1()");
      if (nomeToken.equalsIgnoreCase("senao")) {
        erro = reconhece();
        if (erro == null){
          erro = d();
        }
      }
      return erro;
    }

    public String d(){
      System.out.println("ENTREI NO D()");
      if (nomeToken.equalsIgnoreCase("inicio")) {
        erro = bloco();
      }else{
        if ((nomeToken.equalsIgnoreCase("se")) || (nomeToken.equalsIgnoreCase("para")) || (nomeToken.equalsIgnoreCase("repita")) || (nomeToken.equalsIgnoreCase("enquanto")) || (nomeToken.equalsIgnoreCase("leia")) || (nomeToken.equalsIgnoreCase("escreva")) || (classeToken.equalsIgnoreCase("identificador"))) {
          erro = cmd();
        } else 
        {
          erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
          erro = erro + "Esperado: 'se' ou 'para' ou 'repita' ou 'enquanto' ou 'leia' ou 'escreva' (palavra reservada) ou (identificador), Presente: "+nomeToken+" ("+classeToken+")";  
        }
      }
      return erro;
    }

    public String cmd_repita(){
      System.out.println("ENTREI NO CMD_REPITA()");
      if (nomeToken.equalsIgnoreCase("repita")) {
        erro = reconhece();
        if (erro == null){
          erro = d();
          if (erro == null) {
            if (nomeToken.equalsIgnoreCase("ate")) {
              erro = reconhece();
              if (erro == null){
                erro = el();
              }
            }else{
              erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
              erro = erro + "Esperado: 'ate' (palavra reservada): "+nomeToken+" ("+classeToken+")";  
            }
          }
        }
      }else{
        erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
        erro = erro + "Esperado: 'repita' (palavra reservada): "+nomeToken+" ("+classeToken+")";  
      }
      return erro;
    }

    public String cmd_enquanto(){
      System.out.println("ENTREI NO CMD_ENQUANTO()");
      if (nomeToken.equalsIgnoreCase("enquanto")) {
        erro = reconhece();
        if (erro == null){
          erro = el();
          if (erro == null) {
            if (nomeToken.equalsIgnoreCase("faca")) {
              erro = reconhece();
              if (erro == null){
                erro = d();
              }
            }else{
              erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
              erro = erro + "Esperado: 'faca' (palavra reservada): "+nomeToken+" ("+classeToken+")";
            }
          }
        }

      }else{
        erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
        erro = erro + "Esperado: 'enquanto' (palavra reservada): "+nomeToken+" ("+classeToken+")";
      }
      return erro;
    }

    public String el(){
      System.out.println("ENTREI NO EL()");
        if (classeToken.equalsIgnoreCase("identificador")){
            erro = reconhece();
            if (erro == null){
              erro = er();
            }
        } else {
            if ((nomeToken.equalsIgnoreCase("nao")) || (nomeToken.equalsIgnoreCase("("))) {
              erro = n();
              if (erro == null){
                  if (nomeToken.equalsIgnoreCase("(")) {
                      erro = reconhece();
                      if (erro == null){
                        erro = el1();
                      }
                  }else{
                    erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                    erro = erro + "Esperado: '(' (sinal), Presente: "+nomeToken+" ("+classeToken+")";
                  }
              }
            } else 
            {
              erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
              erro = erro + "Esperado: '(' (sinal) ou 'nao' (palavra reservada) ou (identificador), Presente: "+nomeToken+" ("+classeToken+")";
            }
        }
        return erro;
    }

    public String el1(){
      System.out.println("ENTREI NO EL1()");
      if ((nomeToken.equalsIgnoreCase("[")) || (nomeToken.equalsIgnoreCase("<")) || (nomeToken.equalsIgnoreCase("<=")) || (nomeToken.equalsIgnoreCase(">")) || (nomeToken.equalsIgnoreCase(">=")) || (nomeToken.equalsIgnoreCase("=")) || (nomeToken.equalsIgnoreCase("<>"))) {
        erro = er();
        if (erro == null){
            if (nomeToken.equalsIgnoreCase(")")) {
              erro = reconhece();
              if (erro == null){
                  erro = opl();
                  if (erro == null){
                      erro = n();
                      if (nomeToken.equalsIgnoreCase("(")) {
                        erro = reconhece();
                        if (erro == null){
                            erro = er();
                            if (nomeToken.equalsIgnoreCase(")")) {
                                erro = reconhece();
                            }else{
                              erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                              erro = erro + "Esperado: ')' (sinal): "+nomeToken+" ("+classeToken+")";
                            }
                        }
                      }else{
                        erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
                        erro = erro + "Esperado: '(' (sinal): "+nomeToken+" ("+classeToken+")";  
                      }
                  }
              }
            }else{
              erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
              erro = erro + "Esperado: ')' (sinal): "+nomeToken+" ("+classeToken+")";
            }
        }
      } else {
          if ((classeToken.equalsIgnoreCase("identificador")) || (nomeToken.equalsIgnoreCase("nao")) || (nomeToken.equals("("))) {
              erro = el();
              if (erro == null){
                  if (nomeToken.equalsIgnoreCase(")")) {
                      erro = reconhece();
                  }
              }
          }else{
            erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
            erro = erro + "Esperado: '[' ou '(' (sinal), (identificador) ou 'nao' (palavra reservada): "+nomeToken+" ("+classeToken+")";
          }
      }
      return erro;
    }

    public String er(){
        System.out.println("ENTREI NO ER()");
        erro = ind();
        if (erro == null){
          erro = opr();
          if (erro == null){
            erro = termo();
          }
        }
        return erro;
    }

    public String opr(){
      System.out.println("ENTREI NO OPR()");
        if ((nomeToken.equals("<")) || (nomeToken.equals("<=")) || (nomeToken.equals(">")) || (nomeToken.equals(">=")) || (nomeToken.equals("=")) || (nomeToken.equals("<>"))) {
            erro = reconhece();
        }else{
          erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
          erro = erro + "Esperado: '<', '<=', '>' ou '>=' (sinal): "+nomeToken+" ("+classeToken+")";
        }
        return erro;
    }

    public String opl(){
      System.out.println("ENTREI NO OPL()");
        if ((nomeToken.equalsIgnoreCase("e")) || (nomeToken.equalsIgnoreCase("ou"))) {
            erro = reconhece();
        }else{
          erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
          erro = erro + "Esperado: 'e' ou 'ou' (palavra reservada): "+nomeToken+" ("+classeToken+")";
        }
        return erro;
    }

    public String n(){
      System.out.println("ENTREI NO N()");
        if (nomeToken.equalsIgnoreCase("nao")) {
            erro = reconhece();
        }
        return erro;
    }

    public String termo(){
        System.out.println("ENTREI NO TERMO()");
        if ((classeToken.equals("constante alfanumérica")) || (classeToken.equals("constante inteira"))) {
            erro = reconhece();
        } else {
            if ((classeToken.equals("identificador")) || (classeToken.equals("constante inteira")) || (classeToken.equals("constante real")) || (nomeToken.equals("("))) {
                erro = ea();
            }else{
              erro = "Erro sintático na linha "+lexico.getLinhaArquivo()+"\n";
              erro = erro + "Esperado: (constante alfanumérica), (constante inteira), (identificador) ou (constante real): "+nomeToken+" ("+classeToken+")";
            }
        }
        return erro;
    }
    
}
