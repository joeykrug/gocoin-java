/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/08/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.pojo;

import java.util.Map;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.gocoin.api.GoCoin;

/**
 * a simple POJO class to represent an account
 *
 * {
 *   "id":"e6634e90-8ae2-4fc1-97a2-262990c755f4",
 *   "currency_code":"BTC",
 *   "balance":"0.00000000"
 * }
 *
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class Account
{
  private String id = null;
  private String currency = null;
  private String balance = null;

  public Account(String jsonText)
  {
    this(new JSONObject(jsonText));
  }

  public Account(JSONObject json)
  {
    this(
      json.getString("id"),
      json.getString("currency_code"),
      json.getString("balance")
    );
  }

  public Account(String id, String currency, String balance)
  {
    this.id = id;
    this.currency = currency;
    this.balance = balance;
  }

  public String getId() { return this.id; }
  public void setId(String s) { this.id = s; }

  public String getCurrency() { return this.currency; }
  public void setCurrency(String s) { this.currency = s; }

  public String getBalance() { return this.balance; }
  public void setBalance(String s) { this.balance = s; }

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Account [");
    sb.append(getId());
    sb.append(",currency=").append(getCurrency());
    sb.append(",balance=").append(getBalance());
    sb.append("]");
    return sb.toString();
  }

  public String toJSON()
  {
    Map<String,Object> parameters = new LinkedHashMap<String,Object>();
    parameters.put("id",getId());
    if (GoCoin.hasValue(getCurrency()))
    {
      parameters.put("currency_code",getCurrency());
    }
    if (GoCoin.hasValue(getBalance()))
    {
      parameters.put("balance",getBalance());
    }
   return GoCoin.toJSON(parameters);
  }
}