/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/10/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.services;

import com.gocoin.api.pojo.Invoice;
import com.gocoin.api.pojo.InvoiceSearchResult;
import com.gocoin.api.pojo.SearchCriteria;
import com.gocoin.api.pojo.Token;

/**
 * a service that knows how to perform operations for invoices
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public interface InvoiceService
{
  /**
   * @return a collection of invoices
   */
  public InvoiceSearchResult searchInvoices(Token t, SearchCriteria criteria);

  /**
   * @return an invoice by looking it up by id
   */
  public Invoice getInvoice(Token t, String invoiceId);

  /**
   * @return a new invoice after creating it
   */
  public Invoice createInvoice(Token t, String merchantId, Invoice i);
}
