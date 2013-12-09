/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/08/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api;

import java.util.Map;
import java.net.URL;

import com.gocoin.api.pojo.Token;

/**
 * a simple interface for primitive HTTP/REST calls
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public interface HTTPClient
{
  public static final String USER_AGENT = "Mozilla/5.0";

  //header keys
  public static final String KEY_HEADER_CONTENT_TYPE  = "Content-Type";

  //default header values
  public static final String DEFAULT_HEADER_CONTENT_TYPE  = "application/json";

  //option keys
  public static final String KEY_OPTION_CLIENT_ID       = "client_id";
  public static final String KEY_OPTION_CLIENT_SECRET   = "client_secret";
  public static final String KEY_OPTION_HOST            = "host";
  public static final String KEY_OPTION_DASHBOARD_HOST  = "dashboard_host";
  public static final String KEY_OPTION_PORT            = "port";
  public static final String KEY_OPTION_API_PATH        = "api_path";
  public static final String KEY_OPTION_API_VERSION     = "api_version";
  public static final String KEY_OPTION_PATH            = "path";
  public static final String KEY_OPTION_SECURE          = "secure";
  public static final String KEY_OPTION_METHOD          = "method";
  public static final String KEY_OPTION_REQUEST_ID      = "request_id";
  public static final String KEY_OPTION_REDIRECT_URI    = "redirect_uri";

  //default option values
  public static final String DEFAULT_OPTION_HOST            = "api.llamacoin.com";
  public static final String DEFAULT_OPTION_DASHBOARD_HOST  = "dashboard.llamacoin.com";
  public static final String DEFAULT_OPTION_PORT            = "443";
  public static final String DEFAULT_OPTION_API_PATH        = "/api";
  public static final String DEFAULT_OPTION_API_VERSION     = "/v1";
  public static final String DEFAULT_OPTION_PATH            = "";
  public static final String DEFAULT_OPTION_SECURE          = "true";
  public static final String DEFAULT_OPTION_METHOD          = HTTPClient.METHOD_GET;
  public static final String DEFAULT_OPTION_REQUEST_ID      = "";
  public static final String DEFAULT_OPTION_REDIRECT_URI    = "";

  //parameter keys

  //auth url keys
  public static final String KEY_PARAM_RESPONSE_TYPE  = "response_type";
  public static final String KEY_PARAM_CLIENT_ID      = "client_id";
  public static final String KEY_PARAM_REDIRECT_URI   = "redirect_uri";
  public static final String KEY_PARAM_SCOPE          = "scope";
  public static final String KEY_PARAM_STATE          = "state";
  //token request keys
  public static final String KEY_PARAM_GRANT_TYPE     = "grant_type";
  public static final String KEY_PARAM_CODE           = "code";
  public static final String KEY_PARAM_CLIENT_SECRET  = "client_secret";

  //url types
  public static final String URL_TYPE_API   = "api";
  public static final String URL_TYPE_DASH  = "dash";

  //method types
  public static final String METHOD_GET   = "GET";
  public static final String METHOD_POST  = "POST";
  public static final String METHOD_PUT   = "PUT";

  /**
   * add the authorization header
   */
  public void addAuthorizationHeader(Token t);

  /**
   * add/set a request header
   */
  public void setRequestHeader(String name, String value);

  /**
   * set the request headers
   */
  public void setRequestHeaders(Map<String,String> headers);

  /**
   * add/set a request option
   */
  public void setRequestOption(String name, String value);

  /**
   * set the request options, a.k.a. host/port/path
   */
  public void setRequestOptions(Map<String,String> options);

  /**
   * add/set a request parameter
   */
  public void setRequestParameter(String name, String value);

  /**
   * set the request parameters, ie: query string params
   */
  public void setRequestParameters(Map<String,String> parameters);

  /**
   * @return java.net.URL representing a GET URL
   */
  public URL createURL(String type);

  /**
   * set the request body, ie: JSON
   */
  public void setRequestBody(String body);

  /**
   * @return the request body, ie: JSON
   */
  public String getRequestBody();

  /**
   * send an HTTP GET request
   */
  public void doGET(URL u) throws Exception;

  /**
   * send an HTTP POST request
   */
  public void doPOST(URL u) throws Exception;

  /**
   * send an HTTP PUT request
   */
  public void doPUT(URL u) throws Exception;

  /**
   * @return the response after making a request
   */
  public String getResponse();

  /**
   * @return the response code after making a request
   */
  public int getResponseCode();

  /**
   * @return the response msg after making a request
   */
  public String getResponseMsg();

}