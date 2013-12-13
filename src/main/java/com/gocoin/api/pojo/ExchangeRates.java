/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/10/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.pojo;

import java.util.Calendar;
import java.util.Map;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gocoin.api.GoCoin;

/**
 * a simple POJO class to represent a set of exchange rates
 *
 * {
 *   "timestamp":"2013-12-11T11:25:33.043Z",
 *   "prices":{"BTC":{"USD":"889.11"}}
 * }
 *
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class ExchangeRates
{
  private Calendar serverTs = null;
  private Map<String,Map<String,String>> exchangeRates = null;

  public ExchangeRates(String jsonText)
  {
    this(new JSONObject(jsonText));
  }

  public ExchangeRates(JSONObject json)
  {
    this(
      json.optString("timestamp"), null
    );

    //add the exchange rates
    JSONObject prices = json.optJSONObject("prices");

    if (GoCoin.hasValue(prices))
    {
      //get the array of exchanges
      JSONArray exchangeKeys = prices.names();
      if (exchangeKeys != null)
      {
        for(int i=0; i<exchangeKeys.length(); i++)
        {
          //get the exchange key and exchange object
          String exchangeKey = exchangeKeys.getString(i);
          JSONObject exchange = prices.getJSONObject(exchangeKey);
          //get all the exchange rates
          JSONArray rateKeys = exchange.names();
          if (rateKeys != null)
          {
            for(int j=0; j<rateKeys.length(); j++)
            {
              //get the rate key and value
              String rateKey = rateKeys.getString(j);
              String rate = exchange.getString(rateKey);
              //add the exchange rate
              addExchangeRate(exchangeKey,rateKey,rate);
            }
          }
        }
      }
    }
  }

  public ExchangeRates(String serverTs, Map<String,Map<String,String>> exchangeRates)
  {
    setServerTs(serverTs);
  }

  public Map<String,Map<String,String>> getExchangeRates() { return this.exchangeRates; }
  public void setExchangeRates(Map<String,Map<String,String>> m) { this.exchangeRates = m; }
  public void addExchangeRate(String key, Map<String,String> rate)
  {
    if (this.exchangeRates == null)
    {
      this.exchangeRates = new LinkedHashMap<String,Map<String,String>>();
    }
    //see if there is a map for this key already
    Map<String,String> m = this.exchangeRates.get(key);
    //if not, create it
    if (m == null) { m = new LinkedHashMap<String,String>(); }
    //add all the rates
    m.putAll(rate);
    //store it back
    this.exchangeRates.put(key,m);
  }
  public void addExchangeRate(String key, String exchange, String value)
  {
    Map<String,String> exchangeRate = new LinkedHashMap<String,String>();
    exchangeRate.put(exchange,value);
    addExchangeRate(key,exchangeRate);
  }
  public String getExchangeRate(String key, String exchange)
  {
    if (this.exchangeRates != null && this.exchangeRates.containsKey(key))
    {
      Map<String,String> exchanges = this.exchangeRates.get(key);
      if (exchanges.containsKey(exchange))
      {
        return exchanges.get(exchange);
      }
    }
    return null;
  }

  public Calendar getServerTs() { return this.serverTs; }
  public String getServerTsString()
  {
    if (GoCoin.hasValue(this.serverTs)) { return GoCoin.formatTimestamp(this.serverTs); }
    return "";
  }
  public void setServerTs(Calendar c) { this.serverTs = c; }
  public void setServerTs(String s)
  {
    this.serverTs = GoCoin.parseTimestamp(s);
  }

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Exchange Rate [");
    sb.append("timestamp=").append(getServerTsString());
    sb.append(",exchangeRates=").append(getExchangeRates());
    sb.append("]");
    return sb.toString();
  }

  public String toJSON()
  {
    Map<String,Object> parameters = new LinkedHashMap<String,Object>();
    if (GoCoin.hasValue(getServerTs()))
    {
      parameters.put("timestamp",getServerTsString());
    }
    if (GoCoin.hasValue(getExchangeRates()))
    {
      parameters.put("prices",getExchangeRates());
    }
    return GoCoin.toJSON(parameters);
  }
}