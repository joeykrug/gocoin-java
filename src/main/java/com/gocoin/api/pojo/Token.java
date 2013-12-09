/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/08/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.pojo;

import org.json.JSONObject;

/**
 * a simple POJO class to represent a token
 *
 * {
 *   "access_token":"ae1b4f36a6eef7021318a0edd73daaa5fd0f32b07b14b0236956edcee61619ba",
 *   "token_type":"bearer",
 *   "scope":"user_read"
 * }
 *
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class Token
{
  private String token = null;
  private String type = null;
  private String scope = null;

  public Token(String jsonText)
  {
    this(new JSONObject(jsonText));
  }

  public Token(JSONObject json)
  {
    this(
      json.getString("access_token"), json.getString("token_type"), json.getString("scope")
    );
  }

  public Token(String token, String type, String scope)
  {
    this.token = token;
    this.type = type;
    this.scope = scope;
  }

  public String getToken() { return this.token; }
  public void setToken(String s) { this.token = s; }

  public String getType() { return this.type; }
  public void setType(String s) { this.type = s; }

  public String getScope() { return this.scope; }
  public void setScope(String s) { this.scope = s; }

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Token [").append(getToken()).append(",type=").append(getType());
    sb.append(",scope=").append(getScope()).append("]");
    return sb.toString();
  }

}