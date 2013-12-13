/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/13/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.pojo;

import java.util.Map;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.gocoin.api.GoCoin;

/**
 * a simple POJO class to represent an authorization code request
 *
 *  {
 *   "grant_type"    : "authorization_code",
 *   "code"          : "8240c24e6523d12eb6b3af0eceef9f9054f5f2bd2b849a9be419d756c919810b",
 *   "client_id"     : "661b989eda4e39e65456646f3a214e35039a8823666916dac717f746afa34018",
 *   "client_secret" : "977691490acff424973dfcf3fa32ba5161c7cda673af7b69a82c232e943f668b",
 *   "redirect_uri"  : "http://www.google.com"
 *  }
 *
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class AuthCode
{
  private String grantType = "authorization_code";
  private String code = null;
  private String clientId = null;
  private String clientSecret = null;
  private String redirectUri = null;

  public AuthCode(String jsonText)
  {
    this(new JSONObject(jsonText));
  }

  public AuthCode(JSONObject json)
  {
    this(
      json.optString("grant_type"),
      json.optString("code"),
      json.optString("client_id"),
      json.optString("client_secret"),
      json.optString("redirect_uri")
    );
  }

  public AuthCode(String grantType, String code, String clientId, String clientSecret, String redirectUri)
  {
    if (GoCoin.hasValue(grantType)) { this.grantType = grantType; }
    this.code = code;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.redirectUri = redirectUri;
  }

  public String getGrantType() { return this.grantType; }
  public void setGrantType(String s) { this.grantType = s; }

  public String getCode() { return this.code; }
  public void setCode(String s) { this.code = s; }

  public String getClientId() { return this.clientId; }
  public void setClientId(String s) { this.clientId = s; }

  public String getClientSecret() { return this.clientSecret; }
  public void setClientSecret(String s) { this.clientSecret = s; }

  public String getRedirectUri() { return this.redirectUri; }
  public void setRedirectUri(String s) { this.redirectUri = s; }

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Auth Code [");
    sb.append(getGrantType());
    sb.append(",code=").append(getCode());
    sb.append(",clientId=").append(getClientId());
    sb.append(",clientSecret=").append(getClientSecret());
    sb.append(",redirectUri=").append(getRedirectUri());
    sb.append("]");
    return sb.toString();
  }

  public String toJSON()
  {
    Map<String,Object> parameters = new LinkedHashMap<String,Object>();
    parameters.put("grant_type",getGrantType());
    if (GoCoin.hasValue(getCode()))
    {
      parameters.put("code",getCode());
    }
    if (GoCoin.hasValue(getClientId()))
    {
      parameters.put("client_id",getClientId());
    }
    if (GoCoin.hasValue(getClientSecret()))
    {
      parameters.put("client_secret",getClientSecret());
    }
    if (GoCoin.hasValue(getRedirectUri()))
    {
      parameters.put("redirect_uri",getRedirectUri());
    }
    return GoCoin.toJSON(parameters);
  }
}