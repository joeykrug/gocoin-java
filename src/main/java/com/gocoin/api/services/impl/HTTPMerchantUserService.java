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
import com.gocoin.api.pojo.Token;
import com.gocoin.api.pojo.User;

import com.gocoin.api.services.MerchantUserService;

/**
 * a service that knows how to perform operations for merchant users
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class HTTPMerchantUserService implements MerchantUserService
{
  /**
   * @return a java.util.Collection of users for a given merchant id
   */
  public Collection<User> getMerchantUsers(Token t, String merchantId)
  {
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/merchants/"+merchantId+"/users");
    client.addAuthorizationHeader(t);
    try
    {
      //make the GET request
      client.doGET(client.createURL(HTTPClient.URL_TYPE_API));
      //check the response
      GoCoin.checkResponse(client);
      //return our users
      Collection<User> users = new LinkedList<User>();
      //get the json array
      JSONArray array = new JSONArray(client.getResponse());
      //add each user in the array
      if (array != null && array.length() > 0)
      {
        for(int i=0; i<array.length(); i++)
        {
          users.add(new User(array.getJSONObject(i)));
        }
      }
      //return the users
      return users;
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return null;
    }
  }

  /**
   * @return a boolean representing whether or not the merchant user was added
   */
  public boolean addMerchantUser(Token t, String merchantId, User u)
  {
    return false;
  }

  /**
   * @return a boolean representing whether or not the merchant user was deleted
   */
  public boolean deleteMerchantUser(Token t, String merchantId, User u)
  {
    return false;
  }
}
