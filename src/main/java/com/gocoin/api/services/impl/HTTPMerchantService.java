/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/10/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.services.impl;

import java.util.Collection;
import java.util.LinkedList;

import org.json.JSONArray;

import com.gocoin.api.GoCoin;
import com.gocoin.api.HTTPClient;
import com.gocoin.api.pojo.Merchant;
import com.gocoin.api.pojo.Token;

import com.gocoin.api.services.MerchantService;

/**
 * a service that knows how to perform operations for merchants
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class HTTPMerchantService implements MerchantService
{
  /**
   * NOTE: admin only
   * @return a collection of merchants
   */
  public Collection<Merchant> getMerchants(Token t)
  {
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/merchants/");
    client.addAuthorizationHeader(t);
    try
    {
      //make the GET request
      client.doGET(client.createURL(HTTPClient.URL_TYPE_API));
      //check the response
      GoCoin.checkResponse(client);
      //return our merchants
      Collection<Merchant> merchants = new LinkedList<Merchant>();
      //get the json array
      JSONArray array = new JSONArray(client.getResponse());
      //add each merchant in the array
      if (array != null && array.length() > 0)
      {
        for(int i=0; i<array.length(); i++)
        {
          merchants.add(new Merchant(array.getJSONObject(i)));
        }
      }
      //return the merchants
      return merchants;
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return null;
    }
  }

  /**
   * @return a merchant by looking it up by id
   */
  public Merchant getMerchant(Token t, String merchantId)
  {
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/merchants/"+merchantId);
    client.addAuthorizationHeader(t);
    try
    {
      //make the GET request
      client.doGET(client.createURL(HTTPClient.URL_TYPE_API));
      //check the response
      GoCoin.checkResponse(client);
      //return our merchant
      return new Merchant(client.getResponse());
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return null;
    }
  }

  /**
   * NOTE: admin only
   * @return add a new merchant
   */
  public Merchant addMerchant(Token t, Merchant m)
  {
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/merchants");
    client.setRequestOption(HTTPClient.KEY_OPTION_METHOD,HTTPClient.METHOD_POST);
    client.addAuthorizationHeader(t);
    client.setRequestBody(m.toJSON());
    try
    {
      //make the POST request
      client.doPOST(client.createURL(HTTPClient.URL_TYPE_API));
      //check the response
      GoCoin.checkResponse(client);
      //return the new merchant
      return new Merchant(client.getResponse());
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return null;
    }
  }

  /**
   * @return an updated merchant
   */
  public Merchant updateMerchant(Token t, Merchant m)
  {
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/merchants/"+m.getId());
    client.setRequestOption(HTTPClient.KEY_OPTION_METHOD,HTTPClient.METHOD_PUT);
    client.addAuthorizationHeader(t);
    client.setRequestBody(m.toJSON());
    try
    {
      //make the PUT request
      client.doPUT(client.createURL(HTTPClient.URL_TYPE_API));
      //check the response
      GoCoin.checkResponse(client);
      //return the updated merchant
      return new Merchant(client.getResponse());
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return null;
    }
  }

  /**
   * NOTE: admin only
   * @return a boolean representing whether or not the merchant was deleted
   */
  public boolean deleteMerchant(Token t, Merchant m)
  {
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/merchants/"+m.getId());
    client.setRequestOption(HTTPClient.KEY_OPTION_METHOD,HTTPClient.METHOD_DELETE);
    client.addAuthorizationHeader(t);
    try
    {
      //make the DELETE request
      client.doDELETE(client.createURL(HTTPClient.URL_TYPE_API));
      //check the response
      GoCoin.checkResponse(client);
      //return true if we got a 204
      return true;
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return false;
    }
  }
}
