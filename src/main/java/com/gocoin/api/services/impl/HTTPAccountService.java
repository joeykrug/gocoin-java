/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/09/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.services.impl;

import java.util.Collection;
import java.util.LinkedList;

import org.json.JSONArray;

import com.gocoin.api.GoCoin;
import com.gocoin.api.HTTPClient;
import com.gocoin.api.pojo.Account;
import com.gocoin.api.pojo.Token;

import com.gocoin.api.services.AccountService;

/**
 * a service that knows how to perform operations for accounts
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class HTTPAccountService implements AccountService
{
  /**
   * @return a java.util.Collection of accounts for a given merchant id
   */
  public Collection<Account> getAccounts(Token t, String merchantId)
  {
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/merchants/"+merchantId+"/accounts");
    client.addAuthorizationHeader(t);
    try
    {
      //make the GET request
      client.doGET(client.createURL(HTTPClient.URL_TYPE_API));
      //check the response
      GoCoin.checkResponse(client);
      //return our accounts
      Collection<Account> accounts = new LinkedList<Account>();
      //get the json array
      JSONArray array = new JSONArray(client.getResponse());
      //add each account in the array
      if (array != null && array.length() > 0)
      {
        for(int i=0; i<array.length(); i++)
        {
          accounts.add(new Account(array.getJSONObject(i)));
        }
      }
      //return the accounts
      return accounts;
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return null;
    }
  }
}
