/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/08/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.pojo;

import java.net.URL;
import java.net.MalformedURLException;

import java.util.Calendar;
import java.util.Map;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.gocoin.api.GoCoin;

/**
 * a simple POJO class to represent a user
 *
 * {
 *   "id":"9127be09-24d0-4989-bc45-eb143c65506d",
 *   "email":"aaronlabella@gmail.com",
 *   "first_name":"Aaron",
 *   "last_name":"Labella",
 *   "created_at":"2013-12-05T05:57:19.548Z",
 *   "updated_at":"2013-12-05T12:20:00.593Z",
 *   "image_url":null,
 *   "confirmed":true,
 *   "merchant_id":"81d9b056-6351-4ca9-950c-2e7932db0aec"
 * }
 *
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class User
{
  private String id = null;
  private String firstName = null;
  private String lastName = null;
  private String email = null;
  private Calendar createTs = null;
  private Calendar updateTs = null;
  private URL imageURL = null;
  private boolean confirmed = false;
  private String merchantId = null;


  public User(String jsonText)
  {
    this(new JSONObject(jsonText));
  }

  public User(JSONObject json)
  {
    this(
      json.getString("id"), json.getString("first_name"), json.getString("last_name"),
      json.getString("email"), json.getString("created_at"), json.getString("updated_at"),
      null, json.getBoolean("confirmed"), json.getString("merchant_id")
    );
    //try to set the image url
    Object url = json.get("image_url");
    if (url != null) { setImageURL(url.toString()); }
  }

  public User(String id, String firstName, String lastName, String email,
    String createTs, String updateTs, String imageURL, boolean confirmed, String merchantId)
  {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    setCreateTs(createTs);
    setUpdateTs(updateTs);
    setImageURL(imageURL);
    this.confirmed = confirmed;
    this.merchantId = merchantId;
  }

  public String getId() { return this.id; }
  public void setId(String s) { this.id = s; }

  public String getFirstName() { return this.firstName; }
  public void setFirstName(String s) { this.firstName = s; }

  public String getLastName() { return this.lastName; }
  public void setLastName(String s) { this.lastName = s; }

  public String getEmail() { return this.email; }
  public void setEmail(String s) { this.email = s; }

  public Calendar getCreateTs() { return this.createTs; }
  public String getCreateTsString()
  {
    if (GoCoin.hasValue(this.createTs)) { return GoCoin.formatTimestamp(this.createTs); }
    return "";
  }
  public void setCreateTs(Calendar c) { this.createTs = c; }
  public void setCreateTs(String s)
  {
    this.createTs = GoCoin.parseTimestamp(s);
  }

  public Calendar getUpdateTs() { return this.updateTs; }
  public String getUpdateTsString()
  {
    if (GoCoin.hasValue(this.createTs)) { return GoCoin.formatTimestamp(this.updateTs); }
    return "";
  }
  public void setUpdateTs(Calendar c) { this.updateTs = c; }
  public void setUpdateTs(String s)
  {
    this.updateTs = GoCoin.parseTimestamp(s);
  }

  public URL getImageURL() { return this.imageURL; }
  public void setImageURL(String s)
  {
    if (GoCoin.hasValue(s))
    {
      try { this.imageURL = new URL(s); }
      catch (MalformedURLException e) { }
    }
  }

  public boolean getConfirmed() { return this.confirmed; }
  public void setConfirmed(boolean tf) { this.confirmed = tf; }

  public String getMerchantId() { return this.merchantId; }
  public void setMerchantId(String s) { this.merchantId = s; }

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("User [").append(getId()).append(",first=").append(getFirstName());
    sb.append(",last=").append(getLastName()).append(",email=").append(getEmail());
    sb.append(",createTs=").append(getCreateTsString()).append(",updateTs=").append(getUpdateTsString());
    sb.append(",image=").append(getImageURL()).append(",confirmed=").append(getConfirmed());
    sb.append(",merchantId=").append(getMerchantId()).append("]");
    return sb.toString();
  }

  public String toJSON()
  {
    Map<String,String> parameters = new LinkedHashMap<String,String>();
    if (GoCoin.hasValue(getId()))
    {
      parameters.put("id",getId());
    }
    if (GoCoin.hasValue(getFirstName()))
    {
      parameters.put("first_name",getFirstName());
    }
    if (GoCoin.hasValue(getLastName()))
    {
      parameters.put("last_name",getLastName());
    }
    if (GoCoin.hasValue(getEmail()))
    {
      parameters.put("email",getEmail());
    }
    if (GoCoin.hasValue(getImageURL()))
    {
      parameters.put("image_url",getImageURL().toString());
    }
    parameters.put("confirmed",String.valueOf(getConfirmed()));
    if (GoCoin.hasValue(getMerchantId()))
    {
      parameters.put("merchant_id",getMerchantId().toString());
    }
    return GoCoin.toJSON(parameters);
  }
}