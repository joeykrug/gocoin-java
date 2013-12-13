/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/08/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.MalformedURLException;

//import javax.net.ssl.HttpsURLConnection;

import java.util.Map;
import java.util.LinkedHashMap;

import com.gocoin.api.GoCoin;
import com.gocoin.api.HTTPClient;
import com.gocoin.api.pojo.Token;

/**
 * an abstract class to do most of the legwork for making HTTP calls
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
abstract public class AbstractHTTPClient implements HTTPClient
{
  /** The request options map */
  protected Map<String, String> options = new LinkedHashMap<String, String>();

  /** The request parameters map */
  protected Map<String, String> parameters = new LinkedHashMap<String, String>();

  /** The default headers map */
  protected Map<String, String> headers = new LinkedHashMap<String, String>();

  /**
   * add our defaults during construction
   */
  protected AbstractHTTPClient()
  {
    //default options
    options.put(HTTPClient.KEY_OPTION_HOST, HTTPClient.DEFAULT_OPTION_HOST);
    options.put(HTTPClient.KEY_OPTION_DASHBOARD_HOST, HTTPClient.DEFAULT_OPTION_DASHBOARD_HOST);
    options.put(HTTPClient.KEY_OPTION_PORT, HTTPClient.DEFAULT_OPTION_PORT);
    options.put(HTTPClient.KEY_OPTION_API_PATH, HTTPClient.DEFAULT_OPTION_API_PATH);
    options.put(HTTPClient.KEY_OPTION_API_VERSION, HTTPClient.DEFAULT_OPTION_API_VERSION);
    options.put(HTTPClient.KEY_OPTION_PATH, HTTPClient.DEFAULT_OPTION_PATH);
    options.put(HTTPClient.KEY_OPTION_SECURE, HTTPClient.DEFAULT_OPTION_SECURE);
    options.put(HTTPClient.KEY_OPTION_METHOD, HTTPClient.DEFAULT_OPTION_METHOD);
    options.put(HTTPClient.KEY_OPTION_REQUEST_ID, HTTPClient.DEFAULT_OPTION_REQUEST_ID);
    options.put(HTTPClient.KEY_OPTION_REDIRECT_URI,HTTPClient.DEFAULT_OPTION_REDIRECT_URI);

    //default parameters
    //parameters.put(HTTPClient.KEY_OPTION_CLIENT_ID, HTTPClient.DEFAULT_OPTION_CLIENT_ID);
    //parameters.put(HTTPClient.KEY_OPTION_CLIENT_SECRET, HTTPClient.DEFAULT_OPTION_CLIENT_SECRET);

    //default headers
    headers.put(HTTPClient.KEY_HEADER_CONTENT_TYPE, HTTPClient.DEFAULT_HEADER_CONTENT_TYPE);
  }

  /** the oauth token */
  protected String token = null;

  /** the error string */
  protected String error = null;

  /** the response string */
  protected String response = null;

  /** the response code */
  protected int responseCode = -1;

  /** the response code */
  protected String responseMsg = null;

  /** the request string */
  protected String request = null;

  /**
   * add the authorization header
   */
  public void addAuthorizationHeader(Token t)
  {
    this.headers.put("Authorization","Bearer "+t.getToken());
  }

  /**
   * add/set a request header
   */
  public void setRequestHeader(String name, String value)
  {
    this.headers.put(name,value);
  }

  /**
   * set the request headers
   */
  public void setRequestHeaders(Map<String,String> headers)
  {
    this.headers = headers;
  }

  /**
   * add/set a request option
   */
  public void setRequestOption(String name, String value)
  {
    this.options.put(name,value);
  }

  /**
   * set the request options, a.k.a. host/port/path
   */
  public void setRequestOptions(Map<String,String> options)
  {
    this.options = options;
  }

  /**
   * @return a request option, ie: host, path, etc.
   */
  protected String getRequestOption(String key)
  {
    if (this.options != null && this.options.containsKey(key))
    {
      String param = this.options.get(key);
      if (GoCoin.hasValue(param)) { return param; }
    }
    //default to return null
    return null;
  }

  /**
   * add/set a request parameter
   */
  public void setRequestParameter(String name, String value)
  {
    this.parameters.put(name,value);
  }

  /**
   * set the request parameters, ie: query string params
   */
  public void setRequestParameters(Map<String,String> parameters)
  {
    this.parameters = parameters;
  }

  /**
   * @return java.net.URL given our options/parameters
   */
  public URL createURL(String type)
  {
    //get our options/parameters
    String host       = getRequestOption(HTTPClient.KEY_OPTION_HOST);
    String dash_host  = getRequestOption(HTTPClient.KEY_OPTION_DASHBOARD_HOST);
    String port       = getRequestOption(HTTPClient.KEY_OPTION_PORT);
    String path       = getRequestOption(HTTPClient.KEY_OPTION_PATH);
    String secure     = getRequestOption(HTTPClient.KEY_OPTION_SECURE);
    String method     = getRequestOption(HTTPClient.KEY_OPTION_METHOD);

    //secure url / https
    boolean https = "true".equalsIgnoreCase(secure);

    //our string builder
    StringBuilder sb = new StringBuilder();

    //if no host is set, get outta here
    if (!GoCoin.hasValue(host) && !GoCoin.hasValue(dash_host))
    {
      return null;
    }

    //set a default port
    if (!GoCoin.hasValue(port))
    {
      if (https) { port = "443"; }
      else       { port = "80"; }
    }

    //support https or http
    if (https) { sb.append("https://"); }
    else       { sb.append("http://"); }

    //for dashboard types, use the dash host
    if (HTTPClient.URL_TYPE_DASH.equalsIgnoreCase(type))
    {
      sb.append(dash_host).append(":").append(port);
    }
    //otherwise, use the given host
    else
    {
      sb.append(host).append(":").append(port);
    }

    //for api types, include the api path and version
    if (HTTPClient.URL_TYPE_API.equalsIgnoreCase(type))
    {
      String api_path    = getRequestOption(HTTPClient.KEY_OPTION_API_PATH);
      String api_version = getRequestOption(HTTPClient.KEY_OPTION_API_VERSION);
      //if api path and version aren't set, we have a problem
      if (!GoCoin.hasValue(api_path,api_version)) { return null; }
      sb.append(api_path).append(api_version);
    }

    //append the path
    if (GoCoin.hasValue(path)) { sb.append(path); }

    //append any parameters if its a GET request
    if (GoCoin.hasValue(this.parameters) && HTTPClient.METHOD_GET.equals(method))
    {
      sb.append("?");
      for(Map.Entry<String,String> param : parameters.entrySet())
      {
        try
        {
          sb.append(param.getKey());
          sb.append("=");
          sb.append(URLEncoder.encode(param.getValue(),"UTF-8"));
          sb.append("&");
        }
        //this really shouldn't ever happen, but, get out if it does
        catch (UnsupportedEncodingException uee)
        {
          return null;
        }
      }
      //delete the trailing &
      sb.deleteCharAt(sb.length()-1);
    }

    try { return new URL(sb.toString()); }
    catch (MalformedURLException e) { GoCoin.log(e); }

    return null;
  }

  /**
   * set the request body, ie: JSON
   */
  public void setRequestBody(String body)
  {
    this.request = body;
  }

  /**
   * @return the request body, ie: JSON
   */
  public String getRequestBody()
  {
    //get the request method
    String method = getRequestOption(HTTPClient.KEY_OPTION_METHOD);

    //sanity check, who's calling me via GET?
    if (HTTPClient.METHOD_GET.equals(method)) { return null; }

    if (GoCoin.hasValue(this.request))
    {
      return this.request;
    }
    else
    {
      return GoCoin.toJSON(this.parameters);
    }
  }

  /**
   * @return the response after making a request
   */
  public String getResponse()
  {
    return this.response;
  }

  /**
   * @return the response code after making a request
   */
  public int getResponseCode()
  {
    return this.responseCode;
  }

  /**
   * @return the response msg after making a request
   */
  public String getResponseMsg()
  {
    return this.responseMsg;
  }

  /**
   * send an HTTP GET request
   */
  abstract public void doGET(URL u) throws Exception;

  /**
   * send an HTTP POST request
   */
  abstract public void doPOST(URL u) throws Exception;

  /**
   * send an HTTP PUT request
   */
  abstract public void doPUT(URL u) throws Exception;

  /**
   * send an HTTP DELETE request
   */
  abstract public void doDELETE(URL u) throws Exception;

  /**
   * send an HTTP request
   */
  protected void doREQUEST(URL u) throws Exception
  {
    InputStream is = null;

    try
    {
      //get the request method
      String method = getRequestOption(HTTPClient.KEY_OPTION_METHOD);

      //debug
      if (GoCoin.DEBUG) { GoCoin.printf("[WARNING]: Sending %s request to URL [%s]...", method, u); }

      //open the URL connection
      HttpURLConnection conn = (HttpURLConnection)u.openConnection();

      //set the request method
      conn.setRequestMethod(method);

      //add request header
      conn.setRequestProperty("User-Agent", HTTPClient.USER_AGENT);

      //append any headers
      if (GoCoin.hasValue(this.headers))
      {
        for(Map.Entry<String,String> header : headers.entrySet())
        {
          if (GoCoin.VERBOSE) { GoCoin.printf("[DEBUG]: Adding request header [%s]=>[%s]", header.getKey(), header.getValue()); }
          conn.setRequestProperty(header.getKey(),header.getValue());
        }
      }

      //send post data if its not a get/delete/etc.
      if (!HTTPClient.METHOD_GET.equals(method))
      {
        String body = getRequestBody();
        if (GoCoin.VERBOSE) { GoCoin.printf("[DEBUG]: Request body: %n%s", body); }
        conn.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(body);
        wr.flush();
        wr.close();
      }

      //get the response code
      this.responseCode = conn.getResponseCode();
      this.responseMsg = conn.getResponseMessage();

      if (GoCoin.DEBUG) { GoCoin.printf("[DEBUG]: Response Code: [%d]", responseCode); }
      if (GoCoin.DEBUG) { GoCoin.printf("[DEBUG]: Response Message: [%s]", responseMsg); }

      //get our response input stream
      if (responseCode >= 200 && responseCode <= 299)
      {
        is = conn.getInputStream();
      }
      //anything not in the 200 range is an error. so, use the error stream
      else
      {
        is = conn.getErrorStream();
      }

      if (is != null)
      {
        //read the input stream
        //TODO: this should read in bytes for better performance instead of line by line
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = "";
        while ((line = in.readLine()) != null)
        {
          sb.append(line);
        }

        //close our stream
        in.close();

        //get our response
        this.response = sb.toString();
      }

      //print result
      if (GoCoin.VERBOSE)
      {
        GoCoin.printf("[DEBUG]: Response Body:");
        GoCoin.print(getResponse());
      }
    }
    catch (Exception e)
    {
      throw e;
    }
    finally
    {
      if (is != null)
      {
        //try to close the is and clean up after ourselves, do nothing if it fails
        try { is.close(); }
        catch (Exception e) { }
      }
    }
  }

}