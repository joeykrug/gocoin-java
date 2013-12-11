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

import org.json.JSONObject;

import com.gocoin.api.GoCoin;

/**
 * a simple POJO class to represent a merchant
 *
 * {
 *   "id": "08d3bedf-7cb3-4ccc-9d20-cd221df9443d",
 *   "name": "BlingBling",
 *   "address_1": "100 Main St.",
 *   "address_2": null,
 *   "city": "Venice",
 *   "region": "CA",
 *   "country_code": "US",
 *   "postal_code": "90291",
 *   "contact_name": null,
 *   "phone": "555-555-1234",
 *   "website": "http://merchant.com",
 *   "description": null,
 *   "tax_id": null,
 *   "logo_url":null,
 *   "btc_payout_split":100,
 *   "usd_payout_split",
 *   "created_at": "2013-08-13T04:07:43.867Z",
 *   "updated_at": "2013-08-13T04:10:49.785Z"
 * }
 *
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class Merchant
{
  private String id = null;
  private String name = null;
  private String address1 = null;
  private String address2 = null;
  private String city = null;
  private String region = null;
  private String countryCode = null;
  private String postalCode = null;
  private String contactName = null;
  private String phone = null;
  private String website = null;
  private String description = null;
  private String taxId = null;
  private String logoUrl = null;
  private Double btcPayoutSplit = null;
  private Double usdPayoutSplit = null;
  private Calendar createTs = null;
  private Calendar updateTs = null;

  public Merchant(String jsonText)
  {
    this(new JSONObject(jsonText));
  }

  public Merchant(JSONObject json)
  {
    this(
      json.getString("id"), json.getString("name"),
      json.getString("created_at"), json.getString("updated_at")
    );

    Object addr1 = json.get("address_1");
    if (GoCoin.hasValue(addr1)) { this.address1 = addr1.toString(); }

    Object addr2 = json.get("address_2");
    if (GoCoin.hasValue(addr2)) { this.address2 = addr2.toString(); }

    Object city = json.get("city");
    if (GoCoin.hasValue(city)) { this.city = city.toString(); }

    Object region = json.get("region");
    if (GoCoin.hasValue(region)) { this.region = region.toString(); }

    Object country = json.get("country_code");
    if (GoCoin.hasValue(country)) { this.countryCode = country.toString(); }

    Object postal = json.get("postal_code");
    if (GoCoin.hasValue(postal)) { this.postalCode = postal.toString(); }

    Object contact = json.get("contact_name");
    if (GoCoin.hasValue(contact)) { this.contactName = contact.toString(); }

    Object phone = json.get("phone");
    if (GoCoin.hasValue(phone)) { this.phone = phone.toString(); }

    Object website = json.get("website");
    if (GoCoin.hasValue(website)) { this.website = website.toString(); }

    Object desc = json.get("description");
    if (GoCoin.hasValue(desc)) { this.description = desc.toString(); }

    Object tid = json.get("tax_id");
    if (GoCoin.hasValue(tid)) { this.taxId = tid.toString(); }

    Object logo = json.get("logo_url");
    if (GoCoin.hasValue(logo)) { this.logoUrl = logo.toString(); }

    Object btcSplit = json.get("btc_payout_split");
    if (GoCoin.hasValue(btcSplit))
    {
      try { this.btcPayoutSplit = Double.valueOf(btcSplit.toString()); }
      catch (NumberFormatException nfe) { }
    }

    Object usdSplit = json.get("usd_payout_split");
    if (GoCoin.hasValue(usdSplit))
    {
      try { this.usdPayoutSplit = Double.valueOf(usdSplit.toString()); }
      catch (NumberFormatException nfe) { }
    }
  }

  public Merchant(String id, String name, String createTs, String updateTs)
  {
    this.id = id;
    this.name = name;
    setCreateTs(createTs);
    setUpdateTs(updateTs);
  }

  public String getId() { return this.id; }
  public void setId(String s) { this.id = s; }

  public String getName() { return this.name; }
  public void setName(String s) { this.name = s; }

  public String getAddress1() { return this.address1; }
  public void setAddress1(String s) { this.address1 = s; }

  public String getAddress2() { return this.address2; }
  public void setAddress2(String s) { this.address2 = s; }

  public String getCity() { return this.city; }
  public void setCity(String s) { this.city = s; }

  public String getRegion() { return this.region; }
  public void setRegion(String s) { this.region = s; }

  public String getCountryCode() { return this.countryCode; }
  public void setCountryCode(String s) { this.countryCode = s; }

  public String getPostalCode() { return this.postalCode; }
  public void setPostalCode(String s) { this.postalCode = s; }

  public String getContactName() { return this.contactName; }
  public void setContactName(String s) { this.contactName = s; }

  public String getPhone() { return this.phone; }
  public void setPhone(String s) { this.phone = s; }

  public String getWebsite() { return this.website; }
  public void setWebsite(String s) { this.website = s; }

  public String getDescription() { return this.description; }
  public void setDescription(String s) { this.description = s; }

  public String getTaxId() { return this.taxId; }
  public void setTaxId(String s) { this.taxId = s; }

  public String getLogoUrl() { return this.logoUrl; }
  public void setLogoUrl(String s) { this.logoUrl = s; }

  public Double getBtcPayoutSplit() { return this.btcPayoutSplit; }
  public void setBtcPayoutSplit(Double d) { this.btcPayoutSplit = d; }

  public Double getUsdPayoutSplit() { return this.usdPayoutSplit; }
  public void setUsdPayoutSplit(Double d) { this.usdPayoutSplit = d; }

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

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Merchant [");
    sb.append(getId());
    sb.append(",name=").append(getName());
    sb.append(",address1=").append(getAddress1());
    sb.append(",address2=").append(getAddress2());
    sb.append(",city=").append(getCity());
    sb.append(",region=").append(getRegion());
    sb.append(",countryCode=").append(getCountryCode());
    sb.append(",postalCode=").append(getPostalCode());
    sb.append(",contactName=").append(getContactName());
    sb.append(",phone=").append(getPhone());
    sb.append(",website=").append(getWebsite());
    sb.append(",description=").append(getDescription());
    sb.append(",taxId=").append(getTaxId());
    sb.append(",logoUrl=").append(getLogoUrl());
    sb.append(",btcPayoutSplit=").append(getBtcPayoutSplit());
    sb.append(",usdPayoutSplit=").append(getUsdPayoutSplit());
    sb.append(",createTs=").append(getCreateTsString());
    sb.append(",updateTs=").append(getUpdateTsString());
    sb.append("]");
    return sb.toString();
  }

  public String toJSON()
  {
    Map<String,Object> parameters = new LinkedHashMap<String,Object>();
    if (GoCoin.hasValue(getId()))
    {
      parameters.put("id",getId());
    }
    if (GoCoin.hasValue(getName()))
    {
      parameters.put("name",getName());
    }
    if (GoCoin.hasValue(getAddress1()))
    {
      parameters.put("address_1",getAddress1());
    }
    if (GoCoin.hasValue(getAddress2()))
    {
      parameters.put("address_2",getAddress2());
    }
    if (GoCoin.hasValue(getCity()))
    {
      parameters.put("city",getCity());
    }
    if (GoCoin.hasValue(getRegion()))
    {
      parameters.put("region",getRegion());
    }
    if (GoCoin.hasValue(getCountryCode()))
    {
      parameters.put("country_code",getCountryCode());
    }
    if (GoCoin.hasValue(getPostalCode()))
    {
      parameters.put("postal_code",getPostalCode());
    }
    if (GoCoin.hasValue(getContactName()))
    {
      parameters.put("contact_name",getContactName());
    }
    if (GoCoin.hasValue(getPhone()))
    {
      parameters.put("phone",getPhone());
    }
    if (GoCoin.hasValue(getWebsite()))
    {
      parameters.put("website",getWebsite());
    }
    if (GoCoin.hasValue(getDescription()))
    {
      parameters.put("description",getDescription());
    }
    if (GoCoin.hasValue(getTaxId()))
    {
      parameters.put("tax_id",getTaxId());
    }
    if (GoCoin.hasValue(getLogoUrl()))
    {
      parameters.put("logo_url",getLogoUrl());
    }
    if (GoCoin.hasValue(getBtcPayoutSplit()))
    {
      parameters.put("btc_payout_split",getBtcPayoutSplit());
    }
    if (GoCoin.hasValue(getUsdPayoutSplit()))
    {
      parameters.put("usd_payout_split",getUsdPayoutSplit());
    }
    return GoCoin.toJSON(parameters);
  }
}