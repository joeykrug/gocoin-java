/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/08/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.impl;

import java.net.URL;

/**
 * a simple http client
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class SimpleHTTPClient extends AbstractHTTPClient
{
  /**
   * send an HTTP GET request
   */
  public void doGET(URL u) throws Exception
  {
    doREQUEST(u);
  }

  /**
   * send an HTTP POST request
   */
  public void doPOST(URL u) throws Exception
  {
    doREQUEST(u);
  }

  /**
   * send an HTTP PUT request
   */
  public void doPUT(URL u) throws Exception
  {
    doREQUEST(u);
  }
}