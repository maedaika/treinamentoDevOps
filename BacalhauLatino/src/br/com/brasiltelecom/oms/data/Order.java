package br.com.brasiltelecom.oms.data;

import org.w3c.dom.Element;

public class Order
{
  private int id;
  private long hist;
  private String state;
  private Element root;
  
  public Order(String id, String hist, String state, Element root)
  {
    this.id = Integer.parseInt(id);
    this.hist = Long.parseLong(hist);
    this.state = state;
    this.root = root;
  }
  
  public Order(String id, String hist, String state)
  {
    this(id, hist, state, null);
  }
  
  public Order(String id, String hist)
  {
    this(id, hist, "null");
  }
  
  public Order(String id, String hist, Element root)
  {
    this(id, hist, "UNKNOWN", root);
  }
  
  public Order(int id, long hist, String state)
  {
    this(id, hist, state, null);
  }
  
  public Order(int id, long hist, String state, Element root)
  {
    this.id = id;
    this.hist = hist;
    this.state = state;
    this.root = root;
  }
  
  public Order() {}
  
  public long getHist()
  {
    return this.hist;
  }
  
  public void setHist(long hist)
  {
    this.hist = hist;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getState()
  {
    return this.state;
  }
  
  public void setState(String state)
  {
    this.state = state;
  }
  
  public Element getRoot()
  {
    return this.root;
  }
  
  public void setRoot(Element root)
  {
    this.root = root;
  }
  
  public String toString()
  {
    return "Order: " + this.id + ";" + this.hist;
  }
}
