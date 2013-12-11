/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/08/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api;

import java.net.URL;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONObject;

import com.gocoin.api.impl.SimpleHTTPClient;
import com.gocoin.api.pojo.ExchangeRates;
import com.gocoin.api.pojo.Token;
import com.gocoin.api.services.AccountService;
import com.gocoin.api.services.DepositAddressService;
import com.gocoin.api.services.InvoiceService;
import com.gocoin.api.services.MerchantService;
import com.gocoin.api.services.MerchantUserService;
import com.gocoin.api.services.UserService;
import com.gocoin.api.services.impl.HTTPUserService;
import com.gocoin.api.services.impl.HTTPDepositAddressService;
import com.gocoin.api.services.impl.HTTPInvoiceService;
import com.gocoin.api.services.impl.HTTPMerchantService;
import com.gocoin.api.services.impl.HTTPMerchantUserService;
import com.gocoin.api.services.impl.HTTPAccountService;

/**
 * a simple class for utility methods
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public final class GoCoin
{
  //use coordinated univeral time format
  public static final String TIMESTAMP_PATTERN_CUT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  public static final String TIMESTAMP_PATTERN_CUT_SERVER = "yyyy-MM-dd'T'HH:mm:ss'Z'";

  //our date formatter
  public static final DateFormat df = new SimpleDateFormat(TIMESTAMP_PATTERN_CUT);

  //the exchange service url to use
  public static final String EXCHANGE_HOST = "x.g0cn.com";
  public static final String EXCHANGE_PATH = "/prices";

  /**
   * play the role of a factory and just return our simple client
   * there's no need to create a full fledged Factory pattern at this point
   * @return an HTTPClient
   */
  static public HTTPClient getHTTPClient()
  {
    return new SimpleHTTPClient();
  }

  /**
   * play the role of a factory and just return our http service
   * @return a UserService
   */
  static public UserService getUserService()
  {
    //TODO: support singletons
    return new HTTPUserService();
  }

  /**
   * play the role of a factory and just return our http service
   * @return an AccountService
   */
  static public AccountService getAccountService()
  {
    //TODO: support singletons
    return new HTTPAccountService();
  }

  /**
   * play the role of a factory and just return our http service
   * @return a InvoiceService
   */
  static public InvoiceService getInvoiceService()
  {
    //TODO: support singletons
    return new HTTPInvoiceService();
  }

  /**
   * play the role of a factory and just return our http service
   * @return a MerchantService
   */
  static public MerchantService getMerchantService()
  {
    //TODO: support singletons
    return new HTTPMerchantService();
  }

  /**
   * play the role of a factory and just return our http service
   * @return a MerchantUserService
   */
  static public MerchantUserService getMerchantUserService()
  {
    //TODO: support singletons
    return new HTTPMerchantUserService();
  }

  /**
   * play the role of a factory and just return our http service
   * @return a DepositAddressService
   */
  static public DepositAddressService getDepositAddressService()
  {
    //TODO: support singletons
    return new HTTPDepositAddressService();
  }

  /**
   * @return an authorization url
   */
  static public URL getAuthURL(String clientId, String redirectUri, String scope, String state)
  {
    //get a new http client and set the options
    HTTPClient client = getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/auth");
    //set parameters
    client.setRequestParameter(HTTPClient.KEY_PARAM_RESPONSE_TYPE,"code");
    client.setRequestParameter(HTTPClient.KEY_PARAM_CLIENT_ID,clientId);
    client.setRequestParameter(HTTPClient.KEY_PARAM_REDIRECT_URI,redirectUri);
    client.setRequestParameter(HTTPClient.KEY_PARAM_SCOPE,scope);
    client.setRequestParameter(HTTPClient.KEY_PARAM_STATE,state);
    //create the url
    return client.createURL(HTTPClient.URL_TYPE_DASH);
  }

  /**
   * @return an auth token for future API calls
   */
  static public Token getAuthToken(String code, String clientId, String clientSecret, String redirectUri)
  {
    //get a new http client and set the options
    //NOTE: since its a post request, the parameters get converted into JSON
    HTTPClient client = getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,"/oauth/token");
    client.setRequestOption(HTTPClient.KEY_OPTION_METHOD,HTTPClient.METHOD_POST);
    //set parameters
    client.setRequestParameter(HTTPClient.KEY_PARAM_GRANT_TYPE,"authorization_code");
    client.setRequestParameter(HTTPClient.KEY_PARAM_CODE,code);
    client.setRequestParameter(HTTPClient.KEY_PARAM_CLIENT_ID,clientId);
    client.setRequestParameter(HTTPClient.KEY_PARAM_CLIENT_SECRET,clientSecret);
    client.setRequestParameter(HTTPClient.KEY_PARAM_REDIRECT_URI,redirectUri);
    try
    {
      //make the POST request
      client.doPOST(client.createURL(HTTPClient.URL_TYPE_API));
      return new Token(client.getResponse());
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return null;
    }
  }

  /**
   * get xrate
   *
   * Gets the xrate - aka current btc exchange rate in US Dollars
   *
   * @return the exchange rate
   */
  static public ExchangeRates getExchangeRates()
  {
    HTTPClient client = getHTTPClient();
    client.setRequestOption(HTTPClient.KEY_OPTION_HOST,EXCHANGE_HOST);
    client.setRequestOption(HTTPClient.KEY_OPTION_PATH,EXCHANGE_PATH);
    client.setRequestOption(HTTPClient.KEY_OPTION_METHOD,HTTPClient.METHOD_GET);
    try
    {
      //make the GET request
      client.doGET(client.createURL(null));
      return new ExchangeRates(client.getResponse());
    }
    catch (Exception e)
    {
      GoCoin.log(e);
      return null;
    }
  }

  /**
   * check the http client response, typically for 200s
   */
  static public void checkResponse(HTTPClient client) throws Exception
  {
    final Integer[] codes = new Integer[] {
      Integer.valueOf(200), Integer.valueOf(201), Integer.valueOf(204)
    };
    final Collection<Integer> validCodes = Arrays.asList(codes);

    if (!validCodes.contains(Integer.valueOf(client.getResponseCode())))
    {
      throw new Exception("[ERROR]: "+client.getResponseCode()+" - "+client.getResponseMsg());
    }
  }

  /**
   * a simple convenience method to see a bunch of objects have value
   */
  static public boolean hasValue(Object... objects)
  {
    for(Object o : objects)
    {
      boolean has_value = true;
      if (o == null)                        { return false; }
      else if (o instanceof String)         { has_value = !"".equals(o.toString().trim()); }
      else if (o instanceof Collection<?>)  { has_value = !((Collection<?>)o).isEmpty(); }
      else if (o instanceof Map<?,?>)       { has_value = ((Map<?,?>)o).size() > 0; }
      if (!has_value) { return false; }
    }
    //congratulations, we made it here -- all the objects have value
    return true;
  }

  /**
   * @return a CUT formatted timestamp
   */
  static public String formatTimestamp(Calendar c)
  {
    return df.format(c.getTime());
  }

  /**
   * @return a Calendar object after parsing a CUT formatted timestamp
   */
  static public Calendar parseTimestamp(String s)
  {
    Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    try
    {
      Date d = df.parse(s);
      c.setTimeInMillis(d.getTime());
      return c;
    }
    catch (ParseException e)
    {
      return null;
    }
  }

  /**
   * @return a Calendar object after parsing a CUT formatted timestamp
   */
  static public Calendar parseTimestamp(String s, String format)
  {
    Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    try
    {
      DateFormat custom = new SimpleDateFormat(format);
      Date d = custom.parse(s);
      c.setTimeInMillis(d.getTime());
      return c;
    }
    catch (ParseException e)
    {
      return null;
    }
  }

  static public String toJSON(Map<String,?> map)
  {
    if (GoCoin.hasValue(map))
    {
      JSONObject json = new JSONObject(map);
      return json.toString(2);
    }
    return "";
/*
    //system newline
    final String NEW_LINE = System.getProperty("line.separator");

    //our builder for json
    StringBuilder json = new StringBuilder();
    json.append("{").append(NEW_LINE);

    for(Map.Entry<String,?> param : map.entrySet())
    {
      //append the name : value json pair
      json.append("  \"").append(param.getKey()).append("\"");
      json.append(" : ");
      json.append("\"").append(param.getValue()).append("\"");
      //append a comma if its not the last param
      if (index++ < paramSize-1) { json.append(","); }
      //append the new line
      json.append(NEW_LINE);
    }

    json.append("}").append(NEW_LINE);

    //return our json
    return json.toString();
*/
  }

  /**
   * simple logging abstraction for log messages
   * NOTE: the goal is to have minimum dependencies
   * TODO: add better support for plugging in logging frameworks, ie: logback
   */
  static public void print(String s)
  {
    System.out.println(s);
  }

  /**
   * simple logging abstraction for log messages
   * NOTE: the goal is to have minimum dependencies
   * TODO: add better support for plugging in logging frameworks, ie: logback
   */
  static public void printf(String s, Object... objects)
  {
    System.out.printf(s+"%n",objects);
  }

  /**
   * simple logging abstraction for stack traces
   * NOTE: the goal is to have minimum dependencies
   * TODO: add better support for plugging in logging frameworks, ie: logback
   */
  static public void log(Throwable t)
  {
    t.printStackTrace();
  }
}