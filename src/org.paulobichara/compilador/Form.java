package compilador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JPasswordField;
import java.awt.Font;

public class Form extends JFrame implements ActionListener
{
  private JButton Compilar = new JButton();
  private JButton Sair = new JButton();
  private JButton Abrir = new JButton();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JTextField Arquivo = new JTextField();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTextArea TextEntrada = new JTextArea();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JTextArea TextLog = new JTextArea();
  private JButton Traduzir = new JButton();
  AnalisadorSintatico sintatico;
  Tradutor tradutor = null;

  public Form()
  {
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    
        Sair.addActionListener(this);
        Traduzir.addActionListener(this);
        Abrir.addActionListener(this);
        Compilar.addActionListener(this);

  }

  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(null);
    this.setSize(new Dimension(812, 679));
    this.setTitle("Compilador Turbo Analyzer");
    Compilar.setText("Compilar");
    Compilar.setBounds(new Rectangle(320, 575, 130, 35));
    Compilar.setFont(new Font("Tahoma", 1, 13));
    Sair.setText("Sair");
    Sair.setBounds(new Rectangle(540, 575, 125, 35));
    Sair.setFont(new Font("Tahoma", 1, 13));
    Abrir.setText("Abrir");
    Abrir.setBounds(new Rectangle(355, 45, 125, 40));
    Abrir.setFont(new Font("Tahoma", 1, 13));
    jLabel1.setText("Arquivo:");
    jLabel1.setBounds(new Rectangle(35, 50, 155, 25));
    jLabel1.setFont(new Font("Tahoma", 1, 13));
    jLabel2.setText("Entrada:");
    jLabel2.setBounds(new Rectangle(35, 110, 155, 25));
    jLabel2.setFont(new Font("Tahoma", 1, 13));
    jLabel3.setText("Log:");
    jLabel3.setBounds(new Rectangle(390, 115, 155, 25));
    jLabel3.setFont(new Font("Tahoma", 1, 13));
    Arquivo.setBounds(new Rectangle(100, 50, 215, 25));
    jScrollPane1.setBounds(new Rectangle(35, 145, 345, 405));
    jScrollPane2.setBounds(new Rectangle(390, 145, 345, 405));
    Traduzir.setText("Traduzir");
    Traduzir.setBounds(new Rectangle(115, 575, 125, 35));
    Traduzir.setFont(new Font("Tahoma", 1, 13));
    jScrollPane2.getViewport().add(TextLog, null);
    this.getContentPane().add(Traduzir, null);
    this.getContentPane().add(jScrollPane2, null);
    jScrollPane1.getViewport().add(TextEntrada, null);
    this.getContentPane().add(jScrollPane1, null);
    this.getContentPane().add(Arquivo, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(Abrir, null);
    this.getContentPane().add(Sair, null);
    this.getContentPane().add(Compilar, null);
    sintatico = new AnalisadorSintatico ();
  }
  public void actionPerformed (ActionEvent aeEvento) {
    Object obSource = aeEvento.getSource();
    	if(obSource ==Abrir) {
    		TextEntrada.removeAll();
    		TextLog.removeAll();

    		String nomeArq= Arquivo.getText()+".txt";

    		this.setTitle(nomeArq);
            sintatico.abreArquivo(nomeArq);
          	TextEntrada.setText(sintatico.preencheTextArea());
    	}
    	else if(obSource ==Sair){
    		System.exit(0);
    	}
    	else if(obSource ==Compilar){
         sintatico = new AnalisadorSintatico();
         sintatico.criaArquivo(Arquivo.getText()+".txt", TextEntrada.getText());
    		 sintatico.abreArquivo(Arquivo.getText()+".txt");
         TextLog.removeAll(); 
    		 this.setTitle(Arquivo.getText()+".txt");
    	   String msg = sintatico.s();
         TextLog.setText(msg);
         tradutor = new Tradutor(sintatico.getCodigo(), sintatico.getTabela());
    	} else 
      {
        if (obSource == Traduzir) 
        {
          TextLog.removeAll();
          TextLog.setText(tradutor.traduzir());
        }
      }
    	
    }




    public static void main(String args[]) {

        Form obj = new Form();
        obj.addWindowListener(new WindowAdapter(){
        public void windowClosing (WindowEvent e) {
          System.exit(0);
        }
        });
        obj.pack();
        obj.setBounds(70,50,800,670);
        obj.setVisible(true);

   }



}