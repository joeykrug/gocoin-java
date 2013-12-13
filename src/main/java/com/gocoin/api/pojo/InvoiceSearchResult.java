/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/08/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.pojo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.LinkedHashMap;

import org.json.JSONObject;
import org.json.JSONArray;

import com.gocoin.api.GoCoin;
import com.gocoin.api.JSON;

/**
 * a simple POJO class to represent an invoice search result
 *
 *
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class InvoiceSearchResult
{
  private Collection<Invoice> invoices = null;
  private String status = null;
  private int total = 0;
  private int page = 0;
  private int perPage = 0;

  public InvoiceSearchResult(String jsonText)
  {
    this(new JSONObject(jsonText));
  }

  public InvoiceSearchResult(JSONObject json)
  {
    Object status = json.opt("status");
    if (GoCoin.hasValue(status)) { this.status = status.toString(); }

    JSONArray invoices = json.optJSONArray("invoices");
    if (invoices != null)
    {
      for(int i=0; i<invoices.length(); i++)
      {
        addInvoice(new Invoice(invoices.getJSONObject(i)));
      }
    }

    JSONObject pagingInfo = json.optJSONObject("paging_info");
    if (pagingInfo != null)
    {
      Object total = pagingInfo.opt("total");
      if (GoCoin.hasValue(total))
      {
        try { this.total = Integer.valueOf(total.toString()).intValue(); }
        catch (Exception e) { }
      }

      Object page = pagingInfo.opt("page");
      if (GoCoin.hasValue(page))
      {
        try { this.page = Integer.valueOf(page.toString()).intValue(); }
        catch (Exception e) { }
      }

      Object perPage = pagingInfo.opt("per_page");
      if (GoCoin.hasValue(perPage))
      {
        try { this.perPage = Integer.valueOf(perPage.toString()).intValue(); }
        catch (Exception e) { }
      }
    }
  }

  public String getStatus() { return this.status; }
  public void setStatus(String s) { this.status = s; }

  public int getTotal() { return this.total; }
  public void setTotal(int i) { this.total = i; }

  public int getPage() { return this.page; }
  public void setPage(int i) { this.page = i; }

  public int getPerPage() { return this.perPage; }
  public void setPerPage(int i) { this.perPage = i; }

  public Collection<Invoice> getInvoices() { return this.invoices; }
  public void setInvoices(Collection<Invoice> invoices) { this.invoices = invoices; }
  public void addInvoice(Invoice i)
  {
    if (this.invoices == null) { this.invoices = new LinkedList<Invoice>(); }
    this.invoices.add(i);
  }

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Invoice Search Result [");
    sb.append("status=").append(getStatus());
    sb.append(",total=").append(getTotal());
    sb.append(",page=").append(getPage());
    sb.append(",perPage=").append(getPerPage());
    sb.append(",invoices=").append(getInvoices());
    sb.append("]");
    return sb.toString();
  }

  public String toJSON()
  {
    Map<String,Object> parameters = new LinkedHashMap<String,Object>();
    if (GoCoin.hasValue(getStatus()))
    {
      parameters.put("status",getStatus());
    }
    if (GoCoin.hasValue(getTotal()))
    {
      parameters.put("total",getTotal());
    }
    if (GoCoin.hasValue(getPage()))
    {
      parameters.put("page",getPage());
    }
    if (GoCoin.hasValue(getPerPage()))
    {
      parameters.put("perPage",getPerPage());
    }
    //this one is a little special because we
    //want the invoices to have their parameters ordered how we want em
    Collection<JSONObject> collection = new LinkedList<JSONObject>();
    if (GoCoin.hasValue(getInvoices()))
    {
      for(Invoice i : getInvoices())
      {
        collection.add(new JSON(i.getJSONMap()));
      }
    }
    parameters.put("invoices",collection);
    JSON json = new JSON(parameters);
    return json.toString(2);
  }
}