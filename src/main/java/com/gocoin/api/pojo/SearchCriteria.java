/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/08/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.pojo;

import java.util.Calendar;
import java.util.Map;
import java.util.LinkedHashMap;

import com.gocoin.api.GoCoin;

/**
 * a simple POJO class to represent search criteria
 *
 * ?merchant_id=:merchant_id&status=:status&start_time=:start_time&end_time=:end_time&page=:page_number&per_page=:per_page
 *
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class SearchCriteria
{
  private String merchantId = null;
  private String status = null;
  private Calendar startTs = null;
  private Calendar endTs = null;
  private String pageNumber = null;
  private String perPage = null;

  public SearchCriteria()
  {
  }

  public SearchCriteria(String merchantId)
  {
    this(merchantId, null);
  }

  public SearchCriteria(String merchantId, String status)
  {
    this(merchantId, status, null, null);
  }

  public SearchCriteria(String merchantId, String status, Calendar startTs, Calendar endTs)
  {
    this.merchantId = merchantId;
    this.status = status;
    this.startTs = startTs;
    this.endTs = endTs;
  }

  public String getMerchantId() { return this.merchantId; }
  public void setMerchantId(String s) { this.merchantId = s; }

  public String getStatus() { return this.status; }
  public void setStatus(String s) { this.status = s; }

  public String getPageNumber() { return this.pageNumber; }
  public void setPageNumber(String s) { this.pageNumber = s; }

  public String getPerPage() { return this.perPage; }
  public void setPerPage(String s) { this.perPage = s; }

  public Calendar getStartTs() { return this.startTs; }
  public String getStartTsString()
  {
    if (GoCoin.hasValue(this.startTs)) { return GoCoin.formatTimestamp(this.startTs); }
    return "";
  }
  public void setStartTs(Calendar c) { this.startTs = c; }
  public void setStartTs(String s)
  {
    this.startTs = GoCoin.parseTimestamp(s);
  }

  public Calendar getEndTs() { return this.endTs; }
  public String getEndTsString()
  {
    if (GoCoin.hasValue(this.endTs)) { return GoCoin.formatTimestamp(this.endTs); }
    return "";
  }
  public void setEndTs(Calendar c) { this.endTs = c; }
  public void setEndTs(String s)
  {
    this.endTs = GoCoin.parseTimestamp(s);
  }

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Search Criteria [");
    sb.append(getMerchantId());
    sb.append(",status=").append(getStatus());
    sb.append(",pageNumber=").append(getPageNumber());
    sb.append(",perPage=").append(getPerPage());
    sb.append(",startTs=").append(getStartTsString());
    sb.append(",endTs=").append(getEndTsString());
    sb.append("]");
    return sb.toString();
  }

  public Map<String,String> toParameterMap()
  {
    Map<String,String> parameters = new LinkedHashMap<String,String>();
    parameters.put("merchant_id",getMerchantId());
    if (GoCoin.hasValue(getStatus()))
    {
      parameters.put("status",getStatus());
    }
    if (GoCoin.hasValue(getStartTs()))
    {
      parameters.put("start_time",getStartTsString());
    }
    if (GoCoin.hasValue(getEndTs()))
    {
      parameters.put("end_time",getEndTsString());
    }
    if (GoCoin.hasValue(getPageNumber()))
    {
      parameters.put("page_number",getPageNumber());
    }
    if (GoCoin.hasValue(getPerPage()))
    {
      parameters.put("per_page",getPerPage());
    }
   return parameters;
  }
}