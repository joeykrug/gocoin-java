/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/10/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.services.impl;

import com.gocoin.api.GoCoin;
import com.gocoin.api.HTTPClient;
import com.gocoin.api.pojo.Invoice;
import com.gocoin.api.pojo.InvoiceSearchResult;
import com.gocoin.api.pojo.SearchCriteria;
import com.gocoin.api.pojo.Token;

import com.gocoin.api.services.InvoiceService;

/**
 * a service that knows how to perform operations for invoices
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class HTTPInvoiceService implements InvoiceService
{
  /**
   * @return a collection of invoices
   */
  public InvoiceSearchResult searchInvoices(Token t, SearchCriteria criteria)
  {
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/invoices/search");
    client.addAuthorizationHeader(t);
    //add the search criteria
    client.setRequestParameters(criteria.toParameterMap());
    if (GoCoin.DEBUG) { System.out.println("[DEBUG]: SEARCH PARAMETERS:\n"+criteria.toParameterMap()); }
    try
    {
      //make the GET request
      client.doGET(client.createURL(HTTPClient.URL_TYPE_API));
      //check the response
      GoCoin.checkResponse(client);
      //return the invoice search result
      return new InvoiceSearchResult(client.getResponse());
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return null;
    }
  }

  /**
   * @return an invoice by looking it up by id
   */
  public Invoice getInvoice(Token t, String invoiceId)
  {
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/invoices/"+invoiceId);
    client.addAuthorizationHeader(t);
    try
    {
      //make the GET request
      client.doGET(client.createURL(HTTPClient.URL_TYPE_API));
      //check the response
      GoCoin.checkResponse(client);
      //return our invoice
      return new Invoice(client.getResponse());
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return null;
    }
  }

  /**
   * @return a new invoice after creating it
   */
  public Invoice createInvoice(Token t, String merchantId, Invoice i)
  {
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/merchants/"+merchantId+"/invoices");
    client.setRequestOption(HTTPClient.KEY_OPTION_METHOD,HTTPClient.METHOD_POST);
    client.addAuthorizationHeader(t);
    client.setRequestBody(i.toJSON());
    try
    {
      //make the POST request
      client.doPOST(client.createURL(HTTPClient.URL_TYPE_API));
      //check the response
      GoCoin.checkResponse(client);
      //return the new invoice
      return new Invoice(client.getResponse());
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return null;
    }
  }
}
