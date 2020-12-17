package br.com.brasiltelecom.oms.xmlapi.baca;

import javax.swing.JTextArea;

public class ConsoleLogger
{
  JTextArea area;
  
  public ConsoleLogger(JTextArea area)
  {
    this.area = area;
  }
  
  public void println(String line)
  {
    this.area.append(line + "\n");
    this.area.setCaretPosition(this.area.getText().length() - 1);
  }
  
  public void print(String text)
  {
    this.area.append(text);
    this.area.setCaretPosition(this.area.getText().length() - 1);
  }
  
  public void clear()
  {
    this.area.setText("");
  }
  
  public String getText()
  {
    return this.area.getText();
  }
}
