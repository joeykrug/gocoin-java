/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/08/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api;

//import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import java.net.URL;

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.LinkedHashMap;

import com.gocoin.api.GoCoin;
import com.gocoin.api.Scope;
import com.gocoin.api.HTTPClient;
import com.gocoin.api.pojo.Account;
import com.gocoin.api.pojo.Application;
import com.gocoin.api.pojo.Token;
import com.gocoin.api.pojo.User;

/**
 * a simple test case to test the api
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class GoCoinApiTestCase
{
  private final String MERCHANT_ID    = "81d9b056-6351-4ca9-950c-2e7932db0aec";
  private final String ACCOUNT_ID     = "e6634e90-8ae2-4fc1-97a2-262990c755f4"; //4e4f0ee1-db6c-4abe-8655-044b59282bec
  private final String USER_ID        = "9127be09-24d0-4989-bc45-eb143c65506d";
  private final String CLIENT_ID      = "661b989eda4e39e65456646f3a214e35039a8823666916dac717f746afa34018";
  private final String CLIENT_SECRET  = "977691490acff424973dfcf3fa32ba5161c7cda673af7b69a82c232e943f668b";
  private final String CODE           = "8e02fd17703b938ab1556e6b734e493e561242f0353c4d09e8ae0a94dd2de740";
  private final String REDIRECT_URI   = "http://www.google.com";

  private final String TOKEN_USER_READ            = "034d184fea2173681d74ef544d73c19caca2f2b567e411920a27823c111e97b8";
  private final String TOKEN_USER_READ_WRITE      = "5c2e117d093fed1e295c862173f2ae5f385f29002c6fae61fee14962d0ca6b00";
  private final String TOKEN_USER_PASSWORD_WRITE  = "74916ff907f3ea3c57e7795712971340ef172052cee047d06e186302331e8bb4";
  private final String TOKEN_ALL_SCOPES           = "47dcb64ebb5f329c1fbf358def3693b803d2943db7d366fc623fc265895de2c3";

  private final String TOKEN_CONFIRM_USER         = "6f1ba10b-b241-441d-bad7-14afa9a3ec10";
  private final String TOKEN_RESET_PASSWORD       = "7286b257-10ef-42ba-bbef-4d2b12d490cc";

//TODO: move scope into an enum
//TODO: move state into an enum

  /**
   * test the json parser
   */
  //@Test
  public void testJson()
  {
    String jsonText = "{\"access_token\":\"034d184fea2173681d74ef544d73c19caca2f2b567e411920a27823c111e97b8\",\"token_type\":\"bearer\",\"scope\":\"user_read\"}";
    System.out.println("[DEBUG]: JSON HERE:\n"+jsonText);
    Token t = new Token(jsonText);
    System.out.printf("[DEBUG]: TOKEN %s%n",t);
  }

  /**
   * test the timestamp stuff
   */
  //@Test
  public void testTimestamps()
  {
    String cutTimestamp = "2013-12-05T05:57:19.548Z";
    Calendar c = GoCoin.parseTimestamp(cutTimestamp);
    String timestamp = GoCoin.formatTimestamp(c);

    System.out.println("[DEBUG]: TIMESTAMP ["+timestamp+"]");
    System.out.println("[DEBUG]: CALENDAR ["+c+"]");
    System.out.println("[DEBUG]: TIMESTAMP ["+cutTimestamp+"]");
  }

  /**
   * test the account service
   */
  //@Test
  public void testAccountService()
  {
    Token t = new Token(TOKEN_ALL_SCOPES,"","");
    Collection<Account> accounts = GoCoin.getAccountService().getAccounts(t,MERCHANT_ID);
    System.out.println("[DEBUG]: accounts\n"+accounts);
  }

  /**
   * test the merchant user service
   */
  //@Test
  public void testMerchantUserService()
  {
    Token t = new Token(TOKEN_ALL_SCOPES,"","");
    Collection<User> users = GoCoin.getMerchantUserService().getMerchantUsers(t,MERCHANT_ID);
    System.out.println("[DEBUG]: merchant users\n"+users);
  }

  /**
   * test the merchant user service
   */
  //@Test
  public void testDepositAddressService()
  {
    Token t = new Token(TOKEN_ALL_SCOPES,"","");
    boolean generated = GoCoin.getDepositAddressService().generateDepositAddress(t,ACCOUNT_ID);
    System.out.println("[DEBUG]: generated:"+generated);
  }

  /**
   * test the user service
   */
  //@Test
  public void testUserService()
  {
    Token t = new Token(TOKEN_USER_READ,"","");
    //test resource owner
    User u = GoCoin.getUserService().getResourceOwner(t);
    System.out.println("[DEBUG]: Resource owner: "+u);
    boolean TEST_GET_USER = false;
    //test get user
    if (TEST_GET_USER)
    {
      u = GoCoin.getUserService().getUser(t,USER_ID);
      System.out.println("[DEBUG]: Found: "+u);
      System.out.println("[DEBUG]: \n"+u.toJSON());
    }
    boolean TEST_UPDATE = false;
    if (TEST_UPDATE)
    {
      u.setLastName("LaBella");
      t = new Token(TOKEN_USER_READ_WRITE,"","");
      //test update user
      u = GoCoin.getUserService().updateUser(t,u, null);
      System.out.println("[DEBUG]: "+u);
    }
    boolean TEST_APPS = false;
    //test the user applications
    if (TEST_APPS)
    {
      Collection<Application> apps = GoCoin.getUserService().getUserApplications(t,u,null);
      System.out.println("[DEBUG]: applications\n"+apps);
    }
    boolean TEST_RESET_PW = false;
    if (TEST_RESET_PW)
    {
      boolean reset = GoCoin.getUserService().resetPassword(t,u,null);
      System.out.println("[DEBUG]: Reset password result: ["+reset+"]");
    }
    boolean TEST_RESET_PW_WITH_TOKEN = false;
    if (TEST_RESET_PW_WITH_TOKEN)
    {
      t = new Token(TOKEN_USER_PASSWORD_WRITE,"","");
      Map<String,String> parameters = new LinkedHashMap<String,String>();
      parameters.put("reset_token",TOKEN_RESET_PASSWORD);
      parameters.put("password","passw0rd1");
      boolean reset = GoCoin.getUserService().resetPasswordWithToken(t,u,parameters);
      System.out.println("[DEBUG]: Reset password with TOKEN result: ["+reset+"]");
    }
    boolean TEST_CONFIRMATION_EMAIL = false;
    if (TEST_CONFIRMATION_EMAIL)
    {
      t = new Token(TOKEN_ALL_SCOPES,"","");
      boolean email = GoCoin.getUserService().requestConfirmationEmail(t,u,null);
      System.out.println("[DEBUG]: Confirmation email result: ["+email+"]");
    }
    boolean TEST_CONFIRM_USER = false;
    if (TEST_CONFIRM_USER)
    {
      t = new Token(TOKEN_ALL_SCOPES,"","");
      Map<String,String> parameters = new LinkedHashMap<String,String>();
      parameters.put("confirmation_token",TOKEN_CONFIRM_USER);
      boolean confirmed = GoCoin.getUserService().confirmUser(t,u,parameters);
      System.out.println("[DEBUG]: Confirm user result: ["+confirmed+"]");
    }
  }

  /**
   * test the scope enum
   */
  //@Test
  public void testScopes()
  {
    System.out.println("[DEBUG]: all auth scopes: ["+Scope.getAllScopes()+"]");
    System.out.println("[DEBUG]: specific scopes: ["+
      Scope.getScope(Scope.USER_READ,Scope.USER_READ_WRITE,Scope.USER_PASSWORD_WRITE)+"]"
    );
  }

  /**
   * test the api
   */
  //@Test
  public void testApiCodeAndToken() throws Exception
  {
    System.out.println("[DEBUG]: running testApiCodeAndToken test case...");
    try
    {
      HTTPClient client = GoCoin.getHTTPClient();
      //test api url
      URL api_url   = client.createURL(HTTPClient.URL_TYPE_API);
      //test dash url
      URL dash_url  = client.createURL(HTTPClient.URL_TYPE_DASH);
      //test an auth url
      URL auth_url  = GoCoin.getAuthURL(
        CLIENT_ID,                                  //client/appplication id
        REDIRECT_URI,                               //redirect uri
        Scope.getAllScopes(),                       //scope, ie: Scope.getAllScopes()
        //Scope.getScope(Scope.USER_READ_WRITE, Scope.USER_PASSWORD_WRITE),  //scope, ie: Scope.getAllScopes()
        "optional"                                  //state
      );

      System.out.println("[DEBUG]: using auth URL ["+auth_url+"]");
      System.out.println("[DEBUG]: using api URL  ["+api_url+"]");
      System.out.println("[DEBUG]: using dash URL ["+dash_url+"]");

      boolean TEST_TOKEN = false;

      if (TEST_TOKEN)
      {
        //get a new oauth token
        Token token = GoCoin.getAuthToken(
          CODE,           //code
          CLIENT_ID,      //client/appplication id
          CLIENT_SECRET,  //client secret
          REDIRECT_URI    //redirect uri
        );

        System.out.println("[WARNING]: auth token ["+token+"]");
      }
      //TODO: add assertions
    }
    catch (Throwable t)
    {
      t.printStackTrace();
    }
  }
}
