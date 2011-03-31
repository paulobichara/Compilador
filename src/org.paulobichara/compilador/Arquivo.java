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
import java.io.*;
import java.io.FileReader;

public class Arquivo {

	private int posicao;// posicao do caracter a ser lido na linha
	private int linha;

	private String conteudoArq;
	private ArrayList arrayLinha; //Cada posicao desse ArrayList corresponde a uma linha do arq
	private String linAtual;

	private boolean EOF;

	FileWriter arquivo;


	public Arquivo(){
		this.posicao=-1;
		this.linha=0;
		this.EOF=false;
		this.conteudoArq="";
		this.arrayLinha= new ArrayList();

	}

	public void abreArquivo(String nomeArq){
		try{ // Tem que mudar o endereco do arquivo p z:/!!

			FileReader file = new FileReader(nomeArq);
			BufferedReader buff = new BufferedReader(file);
			System.out.println("leu arquivo");

			boolean continua = true;
			while(continua){
				String linArq = buff.readLine();
				if (linArq == null) continua = false;
				else {
					arrayLinha.add(linArq);
					this.conteudoArq += linArq + "\n"; //Preenche uma variavel com o conteudo do arq p colocar no texArea.
				}
			}
			System.out.println("tamArray: "+ arrayLinha.size() );
			buff.close();
		}
		catch (IOException e){
			System.out.println("Ocorreu uma exceção na leitura do arquivo: " + e.getMessage());
		}
	}

	public String preencheTextArea(){
		return this.conteudoArq;
	}

	public void criaArquivo(String nomeArq, String conteudoArquivo){
		try{
			System.out.println("entrei no arqcria");  	     
			arquivo = new FileWriter(nomeArq);
			salvaArquivo(conteudoArquivo);
			System.out.println("sai no arqcriar");
		}
		catch (IOException e){
			System.out.println("Ocorreu uma exceção na criacao do arquivo: " + e.getMessage());
		}

	}

	public void salvaArquivo(String conteudoArquivo){
		try{
			System.out.println("Entrei no salvaArquivo");
			arquivo.write(conteudoArquivo);
			arquivo.close();
		}
		catch (IOException e){
			System.out.println("Ocorreu uma exceção na escrita do arquivo: " + e.getMessage());
		}
	}

	public char proxCaracter(){

		char caracter=' ';
		int tamArray= arrayLinha.size()-1;
		int tamLinha;
		if(!arrayLinha.isEmpty()){
			if(linha==0) linAtual=(String) arrayLinha.get(linha);
			posicao++;
			tamLinha=linAtual.length()-1;
			if(posicao>tamLinha){
				linha++;
				if(linha>tamArray){
					EOF= true;
					caracter='\0';
				}
				else{
					linAtual=(String) arrayLinha.get(linha);
					posicao=-1;
					caracter='\n';
				}
			}
			else caracter= linAtual.charAt(posicao);
		}else{
			caracter='\0' ;
			EOF=true;
		}
		return caracter;
	}

	public void retroCaracter(int num){
		if (num > 0) {
			EOF = false;
		}
		if(posicao-num>=0) { 
			posicao-=num;
		}else{
			linha--;
			linAtual=(String) arrayLinha.get(linha);
			if(posicao==0){
				posicao= linAtual.length()-1-num;
			}
			else{
				posicao= linAtual.length()-1;
			}
		}

	}

	public void desprezaLinha(){
		linha++;
		if(linha>arrayLinha.size()-1) EOF=true;
		else{
			linAtual= (String) arrayLinha.get(linha);
			posicao=-1;
		}
	}

	public int getPosicao(){ //retorna a posicao q corresponde a coluna e a linha
		return posicao;        //para serem utilizadas no metodo q armazena o erro
	}

	public int getTamLinha() {
		return arrayLinha.size();
	}

	public int getLinha(){
		return linha;
	}

	public boolean getEOF(){
		return EOF;  
	}

	public String getConteudo(){
		return conteudoArq;
	}
}