/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/08/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.services.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.LinkedHashMap;

import org.json.JSONArray;

import com.gocoin.api.GoCoin;
import com.gocoin.api.HTTPClient;
import com.gocoin.api.pojo.Application;
import com.gocoin.api.pojo.Token;
import com.gocoin.api.pojo.User;

import com.gocoin.api.services.UserService;

/**
 * a service that knows how to perform operations for users
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class HTTPUserService implements UserService
{
  /**
   * @return the User pojo after making an API lookup call for resource owner
   */
  public User getResourceOwner(Token t)
  {
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/user");
    client.addAuthorizationHeader(t);
    try
    {
      //make the GET request
      client.doGET(client.createURL(HTTPClient.URL_TYPE_API));
      //check the response
      GoCoin.checkResponse(client);
      //return our user
      return new User(client.getResponse());
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return null;
    }
  }

  /**
   * @return the User pojo after making an API lookup call on the given user id
   */
  public User getUser(Token t, String userId)
  {
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/users/"+userId);
    client.addAuthorizationHeader(t);
    try
    {
      //make the GET request
      client.doGET(client.createURL(HTTPClient.URL_TYPE_API));
      //check the response
      GoCoin.checkResponse(client);
      //return our user
      return new User(client.getResponse());
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return null;
    }
  }

  /**
   * @return a java.util.Collection of users
   */
  public Collection<User> listUsers(Token t, Map<String,String> parameters)
  {
    return null;
  }

  /**
   * @return the User pojo after creating it via the API
   */
  public User createUser(Token t, User u, Map<String,String> parameters)
  {
    return null;
  }

  /**
   * @return the User pojo after updating it via the API
   */
  public User updateUser(Token t, User u, Map<String,String> parameters)
  {
    //get a new http client and set the options
    //NOTE: since its a post request, the parameters get converted into JSON
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/users/"+u.getId());
    client.setRequestOption(HTTPClient.KEY_OPTION_METHOD,HTTPClient.METHOD_PUT);
    client.addAuthorizationHeader(t);
    client.setRequestBody(u.toJSON());
    try
    {
      //make the PUT request
      client.doPUT(client.createURL(HTTPClient.URL_TYPE_API));
      //check the response
      GoCoin.checkResponse(client);
      //return the updated user
      return new User(client.getResponse());
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return null;
    }
  }

  /**
   * @return a boolean representing whether or not the User was deleted
   */
  public boolean deleteUser(Token t, User u, Map<String,String> parameters)
  {
    return false;
  }

  /**
   * @return a boolean representing whether or not the password was updated
   */
  public boolean updatePassword(Token t, User u, Map<String,String> parameters)
  {
    if (!parameters.containsKey("current_password") || !parameters.containsKey("password"))
    {
      GoCoin.log(new Exception("Invalid parameters for resetPasswordWithToken!"));
      return false;
    }

//TODO: pull out map keys as constants
    String path = "/users/"+u.getId()+"/password";

    //get a new http client and set the options
    //NOTE: since its a post request, the parameters get converted into JSON
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,path);
    client.setRequestOption(HTTPClient.KEY_OPTION_METHOD,HTTPClient.METHOD_PUT);
    client.addAuthorizationHeader(t);
    //create the json map
    Map<String,String> body = new LinkedHashMap<String,String>();
    body.put("current_password",parameters.get("current_password"));
    body.put("currentpassword",parameters.get("current_password"));
    body.put("password",parameters.get("password"));
    body.put("newpassword",parameters.get("password"));
    //set the body
    client.setRequestBody(GoCoin.toJSON(body));
    try
    {
      //make the PUT request
      client.doPUT(client.createURL(HTTPClient.URL_TYPE_API));
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

  /**
   * @return a boolean representing whether or not the password was reset
   */
  public boolean resetPassword(Token t, User u)
  {
    //get a new http client and set the options
    //NOTE: since its a post request, the parameters get converted into JSON
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/users/request_password_reset");
    client.setRequestOption(HTTPClient.KEY_OPTION_METHOD,HTTPClient.METHOD_POST);
    client.addAuthorizationHeader(t);
    //create the json map
    Map<String,String> body = new LinkedHashMap<String,String>();
    body.put("email",u.getEmail());
    //set the body
    client.setRequestBody(GoCoin.toJSON(body));
    try
    {
      //make the POST request
      client.doPOST(client.createURL(HTTPClient.URL_TYPE_API));
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

  /**
   * @return a boolean representing whether or not the password was reset
   */
  public boolean resetPasswordWithToken(Token t, User u, Map<String,String> parameters)
  {
    if (!parameters.containsKey("password") || !parameters.containsKey("reset_token"))
    {
      GoCoin.log(new Exception("Invalid parameters for resetPasswordWithToken!"));
      return false;
    }

//TODO: pull out map keys as constants
    String path = "/users/"+u.getId()+"/reset_password/"+parameters.get("reset_token");

    //get a new http client and set the options
    //NOTE: since its a post request, the parameters get converted into JSON
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,path);
    client.setRequestOption(HTTPClient.KEY_OPTION_METHOD,HTTPClient.METHOD_PUT);
    client.addAuthorizationHeader(t);
    //create the json map
    Map<String,String> body = new LinkedHashMap<String,String>();
    body.put("password",parameters.get("password"));
    body.put("password_confirmation",parameters.get("password"));
    //set the body
    client.setRequestBody(GoCoin.toJSON(body));
    try
    {
      //make the PUT request
      client.doPUT(client.createURL(HTTPClient.URL_TYPE_API));
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

  /**
   * @return a boolean representing whether or not the confirmation email was sent
   */
  public boolean requestConfirmationEmail(Token t, User u)
  {
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/users/request_new_confirmation_email");
    client.setRequestOption(HTTPClient.KEY_OPTION_METHOD,HTTPClient.METHOD_POST);
    client.addAuthorizationHeader(t);
    //create the json map
    Map<String,String> body = new LinkedHashMap<String,String>();
    body.put("email",u.getEmail());
    //set the body
    client.setRequestBody(GoCoin.toJSON(body));
    try
    {
      //make the POST request
      client.doPOST(client.createURL(HTTPClient.URL_TYPE_API));
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

  /**
   * @return a boolean representing whether or not the user was confirmed
   */
  public boolean confirmUser(Token t, User u, Map<String,String> parameters)
  {
    if (!parameters.containsKey("confirmation_token"))
    {
      GoCoin.log(new Exception("Invalid parameters for confirmUser!"));
      return false;
    }

//TODO: pull out map keys as constants
    String path = "/users/"+u.getId()+"/confirm_account/"+parameters.get("confirmation_token");

    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,path);
    client.setRequestOption(HTTPClient.KEY_OPTION_METHOD,HTTPClient.METHOD_POST);
    client.addAuthorizationHeader(t);
    try
    {
      //make the GET request
      client.doGET(client.createURL(HTTPClient.URL_TYPE_API));
      //check the response
      GoCoin.checkResponse(client);
      //return true if we got a 301
      return true;
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return false;
    }
  }

  /**
   * @return a java.util.Collection of applications given a user
   */
  public Collection<Application> getUserApplications(Token t, User u)
  {
    HTTPClient client = GoCoin.getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/users/"+u.getId()+"/applications");
    client.addAuthorizationHeader(t);
    try
    {
      //make the GET request
      client.doGET(client.createURL(HTTPClient.URL_TYPE_API));
      //check the response
      GoCoin.checkResponse(client);
      //return our applications
      Collection<Application> apps = new LinkedList<Application>();
      //get the json array
      JSONArray array = new JSONArray(client.getResponse());
      //add each application in the array
      if (array != null && array.length() > 0)
      {
        for(int i=0; i<array.length(); i++)
        {
          apps.add(new Application(array.getJSONObject(i)));
        }
      }
      //return the applications
      return apps;
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return null;
    }
  }
}
