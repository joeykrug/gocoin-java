/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/09/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.services.impl;

import com.gocoin.api.GoCoin;
import com.gocoin.api.HTTPClient;
import com.gocoin.api.pojo.Token;

import com.gocoin.api.services.DepositAddressService;

/**
 * a service that knows how to perform operations for deposit addresses
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class HTTPDepositAddressService implements DepositAddressService
{
  /**
   * @return a boolean representing whether or not the deposit address was generated
   */
  public boolean generateDepositAddress(Token t, String accountId)
  {
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/accounts/"+accountId+"/deposit_address");
    client.setRequestOption(HTTPClient.KEY_OPTION_METHOD,HTTPClient.METHOD_POST);
    client.addAuthorizationHeader(t);
    try
    {
      //make the POST request
      client.doPOST(client.createURL(HTTPClient.URL_TYPE_API));
      //check the response
      GoCoin.checkResponse(client);
      //return true if we got a 200
      return true;
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return false;
    }
  }
}
