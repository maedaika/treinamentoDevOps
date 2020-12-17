package br.com.brasiltelecom.oms.xmlapi.baca.acao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
import br.com.brasiltelecom.oms.xmlapi.baca.BacaGUI;
import br.com.brasiltelecom.oms.xmlapi.baca.ConsoleLogger;

public abstract class Acao
  implements ActionListener, Runnable
{
  ConsoleLogger console;
  OMSConnection connection;
  BacaGUI baca = BacaGUI.getInstance();
  ArrayList sucessos = new ArrayList();
  ArrayList falhas = new ArrayList();
  private int quantidade = 0;
  Date termino;
  Date inicio;
  String separator = ";";
  
  public Acao()
  {
    this.console = this.baca.getConsole();
    this.separator = this.baca.getServerConfig().getProperty("separator");
    this.connection = this.baca.createConnection();
  }
  
  public void actionPerformed(ActionEvent e)
  {
    new Thread(this).start();
  }
  
  public void run()
  {
    boolean flag = inicializa();
    if (flag) {
      processa();
    }
  }
  
  protected boolean inicializa()
  {
    return true;
  }
  
  public void processa()
  {
    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    this.quantidade = 0;
    this.sucessos.clear();
    this.falhas.clear();
    this.inicio = new Date();
    this.console.println("");
    this.console.println("Iniciando processamento - " + format.format(this.inicio));
    String ordens = this.baca.getOrders();
    StringTokenizer tokenizer = new StringTokenizer(ordens, "\n");
    int total = tokenizer.countTokens();
    while (tokenizer.hasMoreTokens())
    {
      this.quantidade += 1;
      String ordem = tokenizer.nextToken().trim();
      StringTokenizer parametros = new StringTokenizer(ordem, this.separator);
      String[] args = new String[parametros.countTokens()];
      for (int i = 0; parametros.hasMoreTokens(); i++) {
        args[i] = parametros.nextToken();
      }
      try
      {
        this.console.println("# " + this.quantidade + "/" + total);
        processa(args);
        this.sucessos.add(args);
      }
      catch (Exception ex)
      {
        this.console.println("Erro: " + ex.getMessage());
        this.falhas.add(args);
        ex.printStackTrace();
      }
    }
    this.termino = new Date();
    geraRelatorio();
  }
  
  public void geraRelatorio()
  {
    this.console.println("Fim da execução");
    this.console.println("----------------- RELATÓRIO -----------------");
    this.console.println("- Duração do script:....... " + (this.termino.getTime() - this.inicio.getTime()) + " ms");
    this.console.println("- No. acões processadas:... " + this.quantidade);
    this.console.println("- No. de sucessos:......... " + this.sucessos.size() + " (" + 100 * this.sucessos.size() / this.quantidade + "%)");
    this.console.println("- No. de falhas:........... " + this.falhas.size() + " (" + 100 * this.falhas.size() / this.quantidade + "%)");
    if (this.falhas.size() > 0) {
      geraRelatorioFalhas();
    }
    this.console.println("------------------- F I M -------------------");
    this.console.println("");
  }
  
  public void geraRelatorioFalhas()
  {
    char separator = this.separator.charAt(0);
    StringBuffer buffer = new StringBuffer();
    this.console.println("- Açoes com Falha:");
    for (int i = 0; i < this.falhas.size(); i++)
    {
      String[] args = (String[])this.falhas.get(i);
      for (int j = 0; j < args.length; j++) {
        buffer.append(args[j]).append(separator);
      }
      buffer.deleteCharAt(buffer.length() - 1);
      buffer.append('\n');
    }
    this.console.println(buffer.toString());
  }
  
  public abstract void processa(String[] paramArrayOfString)
    throws Exception;
}
