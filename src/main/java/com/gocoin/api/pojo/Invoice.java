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
 * a simple POJO class to represent an invoice
 *
 * {
 *   "id": "84c4fc04-66f2-49a5-a12a-36baf7f9f450",
 *   "status": "new",
 *   "payment_address": "1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
 *   "price": "1.00000000",
 *   "price_currency": "BTC",
 *   "base_price": "134.00",
 *   "base_price_currency": "USD",
 *   "btc_spot_rate": "0.00746268656716",
 *   "usd_spot_rate": "1.0",
 *   "confirmations_required": 6,
 *   "notification_level": "all",
 *   "callback_url": "https://www.example.com/gocoin/callback",
 *   "redirect_url": "http://www.example.com/redirect",
 *   "order_id": null,
 *   "item_name": null,
 *   "item_sku": null,
 *   "item_description": null,
 *   "physical": null,
 *   "customer_name": null,
 *   "customer_address_1": null,
 *   "customer_address_2": null,
 *   "customer_city": null,
 *   "customer_region": null,
 *   "customer_country": null,
 *   "customer_postal_code": null,
 *   "customer_email": null,
 *   "customer_phone": null,
 *   "user_defined_1": null,
 *   "user_defined_2": null,
 *   "user_defined_3": null,
 *   "user_defined_4": null,
 *   "user_defined_5": null,
 *   "user_defined_6": null,
 *   "user_defined_7": null,
 *   "user_defined_8": null,
 *   "data": null,
 *   "expires_at": "2013-10-01T18:47:45.150Z",
 *   "created_at": "2013-10-01T18:32:45.153Z",
 *   "updated_at": "2013-10-01T18:32:45.153Z",
 *   "server_time":"2013-12-11T10:53:14Z"
 *   "merchant_id": "63d3cd4c-1514-11e3-a3f0-080027fd9579",
 * }
 *
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class Invoice
{
  private String id = null;
  private String merchantId = null;
  private String status = null;
  private String paymentAddress = null;
  private String price = null;
  private String priceCurrency = null;
  private String basePrice = null;
  private String basePriceCurrency = null;
  private String btcSpotRate = null;
  private String usdSpotRate = null;
  private int confirmationsRequired = 6;
  private String notificationLevel = null;
  private String callbackUrl = null;
  private String redirectUrl = null;
  private String orderId = null;
  private String itemName = null;
  private String itemSku = null;
  private String itemDescription = null;
  private String physical = null;
  private String customerName = null;
  private String customerAddress1 = null;
  private String customerAddress2 = null;
  private String customerCity = null;
  private String customerRegion = null;
  private String customerCountry = null;
  private String customerPostalCode = null;
  private String customerEmail = null;
  private String customerPhone = null;
  private String userDefined1 = null;
  private String userDefined2 = null;
  private String userDefined3 = null;
  private String userDefined4 = null;
  private String userDefined5 = null;
  private String userDefined6 = null;
  private String userDefined7 = null;
  private String userDefined8 = null;
  private Object data = null;
  private Calendar createTs = null;
  private Calendar updateTs = null;
  private Calendar expireTs = null;
  private Calendar serverTs = null;

  public Invoice()
  {
  }

  public Invoice(String jsonText)
  {
    this(new JSONObject(jsonText));
  }

  public Invoice(JSONObject json)
  {
    this(
      json.optString("id"), json.optString("merchant_id"),
      json.optString("status"), json.optString("payment_address"),
      json.optString("price"), json.optString("price_currency"),
      json.optString("base_price"), json.optString("base_price_currency"),
      json.optString("created_at"), json.optString("updated_at"),
      json.optString("expires_at"), json.optString("server_time")
    );

    Object btcRate = json.opt("btc_spot_rate");
    if (GoCoin.hasValue(btcRate)) { this.btcSpotRate = btcRate.toString(); }

    Object usdRate = json.opt("usd_spot_rate");
    if (GoCoin.hasValue(usdRate)) { this.usdSpotRate = usdRate.toString(); }

    Object confirmations = json.opt("confirmations_required");
    if (GoCoin.hasValue(confirmations))
    {
      try { this.confirmationsRequired = Integer.valueOf(confirmations.toString()); }
      catch (NumberFormatException nfe) { }
    }

    Object level = json.opt("notification_level");
    if (GoCoin.hasValue(level)) { this.notificationLevel = level.toString(); }

    Object callback = json.opt("callback_url");
    if (GoCoin.hasValue(callback)) { this.callbackUrl = callback.toString(); }

    Object redirect = json.opt("redirect_url");
    if (GoCoin.hasValue(redirect)) { this.redirectUrl = redirect.toString(); }

    Object order = json.opt("order_id");
    if (GoCoin.hasValue(order)) { this.orderId = order.toString(); }

    Object name = json.opt("item_name");
    if (GoCoin.hasValue(name)) { this.itemName = name.toString(); }

    Object sku = json.opt("item_sku");
    if (GoCoin.hasValue(sku)) { this.itemSku = sku.toString(); }

    Object desc = json.opt("item_description");
    if (GoCoin.hasValue(desc)) { this.itemDescription = desc.toString(); }

    Object physical = json.opt("physical");
    if (GoCoin.hasValue(physical)) { this.physical = physical.toString(); }

    Object customer = json.opt("customer_name");
    if (GoCoin.hasValue(customer)) { this.customerName = customer.toString(); }

    Object addr1 = json.opt("customer_address_1");
    if (GoCoin.hasValue(addr1)) { this.customerAddress1 = addr1.toString(); }

    Object addr2 = json.opt("customer_address_2");
    if (GoCoin.hasValue(addr2)) { this.customerAddress2 = addr2.toString(); }

    Object city = json.opt("customer_city");
    if (GoCoin.hasValue(city)) { this.customerCity = city.toString(); }

    Object region = json.opt("customer_region");
    if (GoCoin.hasValue(region)) { this.customerRegion = region.toString(); }

    Object country = json.opt("customer_country");
    if (GoCoin.hasValue(country)) { this.customerCountry = country.toString(); }

    Object postal = json.opt("customer_postal_code");
    if (GoCoin.hasValue(postal)) { this.customerPostalCode = postal.toString(); }

    Object email = json.opt("customer_email");
    if (GoCoin.hasValue(email)) { this.customerEmail = email.toString(); }

    Object phone = json.opt("customer_phone");
    if (GoCoin.hasValue(phone)) { this.customerPhone = phone.toString(); }

    Object user1 = json.opt("user_defined_1");
    if (GoCoin.hasValue(user1)) { this.userDefined1 = user1.toString(); }

    Object user2 = json.opt("user_defined_2");
    if (GoCoin.hasValue(user2)) { this.userDefined2 = user2.toString(); }

    Object user3 = json.opt("user_defined_3");
    if (GoCoin.hasValue(user3)) { this.userDefined3 = user3.toString(); }

    Object user4 = json.opt("user_defined_4");
    if (GoCoin.hasValue(user4)) { this.userDefined4 = user4.toString(); }

    Object user5 = json.opt("user_defined_5");
    if (GoCoin.hasValue(user5)) { this.userDefined5 = user5.toString(); }

    Object user6 = json.opt("user_defined_6");
    if (GoCoin.hasValue(user6)) { this.userDefined6 = user6.toString(); }

    Object user7 = json.opt("user_defined_7");
    if (GoCoin.hasValue(user7)) { this.userDefined7 = user7.toString(); }

    Object user8 = json.opt("user_defined_8");
    if (GoCoin.hasValue(user8)) { this.userDefined8 = user8.toString(); }

    Object data = json.opt("data");
    if (GoCoin.hasValue(data)) { this.data = data; }
  }

  public Invoice(String id, String merchantId, String status, String paymentAddress,
    String price, String priceCurrency, String basePrice, String basePriceCurrency,
    String createTs, String updateTs, String expireTs, String serverTs)
  {
    this.id = id;
    this.merchantId = merchantId;
    this.status = status;
    this.paymentAddress = paymentAddress;
    this.price = price;
    this.priceCurrency = priceCurrency;
    this.basePrice = basePrice;
    this.basePriceCurrency = basePriceCurrency;
    setCreateTs(createTs);
    setUpdateTs(updateTs);
    setExpireTs(expireTs);
    setServerTs(serverTs);
  }

  public String getId() { return this.id; }
  public void setId(String s) { this.id = s; }

  public String getMerchantId() { return this.merchantId; }
  public void setMerchantId(String s) { this.merchantId = s; }

  public String getStatus() { return this.status; }
  public void setStatus(String s) { this.status = s; }

  public String getPaymentAddress() { return this.paymentAddress; }
  public void setPaymentAddress(String s) { this.paymentAddress = s; }

  public String getPrice() { return this.price; }
  public void setPrice(String s) { this.price = s; }

  public String getPriceCurrency() { return this.priceCurrency; }
  public void setPriceCurrency(String s) { this.priceCurrency = s; }

  public String getBasePrice() { return this.basePrice; }
  public void setBasePrice(String s) { this.basePrice = s; }

  public String getBasePriceCurrency() { return this.basePriceCurrency; }
  public void setBasePriceCurrency(String s) { this.basePriceCurrency = s; }

  public String getBtcSpotRate() { return this.btcSpotRate; }
  public void setBtcSpotRate(String s) { this.btcSpotRate = s; }

  public String getUsdSpotRate() { return this.usdSpotRate; }
  public void setUsdSpotRate(String s) { this.usdSpotRate = s; }

  public int getConfirmationsRequired() { return this.confirmationsRequired; }
  public void setConfirmationsRequired(int i) { this.confirmationsRequired = i; }

  public String getNotificationLevel() { return this.notificationLevel; }
  public void setNotificationLevel(String s) { this.notificationLevel = s; }

  public String getCallbackUrl() { return this.callbackUrl; }
  public void setCallbackUrl(String s) { this.callbackUrl = s; }

  public String getRedirectUrl() { return this.redirectUrl; }
  public void setRedirectUrl(String s) { this.redirectUrl = s; }

  public String getOrderId() { return this.orderId; }
  public void setOrderId(String s) { this.orderId = s; }

  public String getItemName() { return this.itemName; }
  public void setItemName(String s) { this.itemName = s; }

  public String getItemSku() { return this.itemSku; }
  public void setItemSku(String s) { this.itemSku = s; }

  public String getItemDescription() { return this.itemDescription; }
  public void setItemDescription(String s) { this.itemDescription = s; }

  public String getPhysical() { return this.physical; }
  public void setPhysical(String s) { this.physical = s; }

  public String getCustomerName() { return this.customerName; }
  public void setCustomerName(String s) { this.customerName = s; }

  public String getCustomerAddress1() { return this.customerAddress1; }
  public void setCustomerAddress1(String s) { this.customerAddress1 = s; }

  public String getCustomerAddress2() { return this.customerAddress2; }
  public void setCustomerAddress2(String s) { this.customerAddress2 = s; }

  public String getCustomerCity() { return this.customerCity; }
  public void setCustomerCity(String s) { this.customerCity = s; }

  public String getCustomerRegion() { return this.customerRegion; }
  public void setCustomerRegion(String s) { this.customerRegion = s; }

  public String getCustomerCountry() { return this.customerCountry; }
  public void setCustomerCountry(String s) { this.customerCountry = s; }

  public String getCustomerPostalCode() { return this.customerPostalCode; }
  public void setCustomerPostalCode(String s) { this.customerPostalCode = s; }

  public String getCustomerEmail() { return this.customerEmail; }
  public void setCustomerEmail(String s) { this.customerEmail = s; }

  public String getCustomerPhone() { return this.customerPhone; }
  public void setCustomerPhone(String s) { this.customerPhone = s; }

  public String getUserDefined1() { return this.userDefined1; }
  public void setUserDefined1(String s) { this.userDefined1 = s; }

  public String getUserDefined2() { return this.userDefined2; }
  public void setUserDefined2(String s) { this.userDefined2 = s; }

  public String getUserDefined3() { return this.userDefined3; }
  public void setUserDefined3(String s) { this.userDefined3 = s; }

  public String getUserDefined4() { return this.userDefined4; }
  public void setUserDefined4(String s) { this.userDefined4 = s; }

  public String getUserDefined5() { return this.userDefined5; }
  public void setUserDefined5(String s) { this.userDefined5 = s; }

  public String getUserDefined6() { return this.userDefined6; }
  public void setUserDefined6(String s) { this.userDefined6 = s; }

  public String getUserDefined7() { return this.userDefined7; }
  public void setUserDefined7(String s) { this.userDefined7 = s; }

  public String getUserDefined8() { return this.userDefined8; }
  public void setUserDefined8(String s) { this.userDefined8 = s; }

  public Object getData() { return this.data; }
  public void setData(Object o) { this.data = o; }

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

  public Calendar getExpireTs() { return this.expireTs; }
  public String getExpireTsString()
  {
    if (GoCoin.hasValue(this.createTs)) { return GoCoin.formatTimestamp(this.expireTs); }
    return "";
  }
  public void setExpireTs(Calendar c) { this.expireTs = c; }
  public void setExpireTs(String s)
  {
    this.expireTs = GoCoin.parseTimestamp(s);
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
    this.serverTs = GoCoin.parseTimestamp(s,GoCoin.TIMESTAMP_PATTERN_CUT_SERVER);
  }

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Invoice [");
    sb.append(getId());
    sb.append(",merchantId=").append(getMerchantId());
    sb.append(",status=").append(getStatus());
    sb.append(",paymentAddress=").append(getPaymentAddress());
    sb.append(",price=").append(getPrice());
    sb.append(",priceCurrency=").append(getPriceCurrency());
    sb.append(",basePrice=").append(getBasePrice());
    sb.append(",basePriceCurrency=").append(getBasePriceCurrency());
    sb.append(",btcSpotRate=").append(getBtcSpotRate());
    sb.append(",usdSpotRate=").append(getUsdSpotRate());
    sb.append(",confirmationsRequired=").append(getConfirmationsRequired());
    sb.append(",notificationLevel=").append(getNotificationLevel());
    sb.append(",callbackUrl=").append(getCallbackUrl());
    sb.append(",redirectUrl=").append(getRedirectUrl());
    sb.append(",orderId=").append(getOrderId());
    sb.append(",itemName=").append(getItemName());
    sb.append(",itemSku=").append(getItemSku());
    sb.append(",itemDescription=").append(getItemDescription());
    sb.append(",physical=").append(getPhysical());
    sb.append(",customerName=").append(getCustomerName());
    sb.append(",customerAddress1=").append(getCustomerAddress1());
    sb.append(",customerAddress2=").append(getCustomerAddress2());
    sb.append(",customerCity=").append(getCustomerCity());
    sb.append(",customerRegion=").append(getCustomerRegion());
    sb.append(",customerCountry=").append(getCustomerCountry());
    sb.append(",customerPostalCode=").append(getCustomerPostalCode());
    sb.append(",customerEmail=").append(getCustomerEmail());
    sb.append(",customerPhone=").append(getCustomerPhone());
    sb.append(",userDefined1=").append(getUserDefined1());
    sb.append(",userDefined2=").append(getUserDefined2());
    sb.append(",userDefined3=").append(getUserDefined3());
    sb.append(",userDefined4=").append(getUserDefined4());
    sb.append(",userDefined5=").append(getUserDefined5());
    sb.append(",userDefined6=").append(getUserDefined6());
    sb.append(",userDefined7=").append(getUserDefined7());
    sb.append(",userDefined8=").append(getUserDefined8());
    sb.append(",data=").append(getData());
    sb.append(",createTs=").append(getCreateTsString());
    sb.append(",updateTs=").append(getUpdateTsString());
    sb.append(",expireTs=").append(getExpireTsString());
    sb.append(",serverTs=").append(getServerTsString());
    sb.append("]");
    return sb.toString();
  }

  public Map<String,Object> getJSONMap()
  {
    Map<String,Object> parameters = new LinkedHashMap<String,Object>();
    if (GoCoin.hasValue(getId()))
    {
      parameters.put("id",getId());
    }
    if (GoCoin.hasValue(getMerchantId()))
    {
      parameters.put("merchant_id",getMerchantId());
    }
    if (GoCoin.hasValue(getStatus()))
    {
      parameters.put("status",getStatus());
    }
    if (GoCoin.hasValue(getPaymentAddress()))
    {
      parameters.put("payment_address",getPaymentAddress());
    }
    if (GoCoin.hasValue(getPrice()))
    {
      parameters.put("price",getPrice());
    }
    if (GoCoin.hasValue(getPriceCurrency()))
    {
      parameters.put("price_currency",getPriceCurrency());
    }
    if (GoCoin.hasValue(getBasePrice()))
    {
      parameters.put("base_price",getBasePrice());
    }
    if (GoCoin.hasValue(getBasePriceCurrency()))
    {
      parameters.put("base_price_currency",getBasePriceCurrency());
    }
    if (GoCoin.hasValue(getBtcSpotRate()))
    {
      parameters.put("btc_spot_rate",getBtcSpotRate());
    }
    if (GoCoin.hasValue(getUsdSpotRate()))
    {
      parameters.put("usd_spot_rate",getUsdSpotRate());
    }
    if (getConfirmationsRequired() > 0)
    {
      parameters.put("confirmations_required",getConfirmationsRequired());
    }
    if (GoCoin.hasValue(getNotificationLevel()))
    {
      parameters.put("notification_level",getNotificationLevel());
    }
    if (GoCoin.hasValue(getCallbackUrl()))
    {
      parameters.put("callback_url",getCallbackUrl());
    }
    if (GoCoin.hasValue(getRedirectUrl()))
    {
      parameters.put("redirect_url",getRedirectUrl());
    }
    if (GoCoin.hasValue(getOrderId()))
    {
      parameters.put("order_id",getOrderId());
    }
    if (GoCoin.hasValue(getItemName()))
    {
      parameters.put("item_name",getItemName());
    }
    if (GoCoin.hasValue(getItemSku()))
    {
      parameters.put("item_sku",getItemSku());
    }
    if (GoCoin.hasValue(getItemDescription()))
    {
      parameters.put("item_description",getItemDescription());
    }
    if (GoCoin.hasValue(getPhysical()))
    {
      parameters.put("physical",getPhysical());
    }
    if (GoCoin.hasValue(getCustomerName()))
    {
      parameters.put("customer_name",getCustomerName());
    }
    if (GoCoin.hasValue(getCustomerAddress1()))
    {
      parameters.put("customer_address_1",getCustomerAddress1());
    }
    if (GoCoin.hasValue(getCustomerAddress2()))
    {
      parameters.put("customer_address_2",getCustomerAddress2());
    }
    if (GoCoin.hasValue(getCustomerCity()))
    {
      parameters.put("customer_city",getCustomerCity());
    }
    if (GoCoin.hasValue(getCustomerRegion()))
    {
      parameters.put("customer_region",getCustomerRegion());
    }
    if (GoCoin.hasValue(getCustomerCountry()))
    {
      parameters.put("customer_country",getCustomerCountry());
    }
    if (GoCoin.hasValue(getCustomerPostalCode()))
    {
      parameters.put("customer_postal_code",getCustomerPostalCode());
    }
    if (GoCoin.hasValue(getCustomerEmail()))
    {
      parameters.put("customer_email",getCustomerEmail());
    }
    if (GoCoin.hasValue(getCustomerPhone()))
    {
      parameters.put("customer_phone",getCustomerPhone());
    }
    if (GoCoin.hasValue(getUserDefined1()))
    {
      parameters.put("user_defined_1",getUserDefined1());
    }
    if (GoCoin.hasValue(getUserDefined2()))
    {
      parameters.put("user_defined_2",getUserDefined2());
    }
    if (GoCoin.hasValue(getUserDefined3()))
    {
      parameters.put("user_defined_3",getUserDefined3());
    }
    if (GoCoin.hasValue(getUserDefined4()))
    {
      parameters.put("user_defined_4",getUserDefined4());
    }
    if (GoCoin.hasValue(getUserDefined5()))
    {
      parameters.put("user_defined_5",getUserDefined5());
    }
    if (GoCoin.hasValue(getUserDefined6()))
    {
      parameters.put("user_defined_6",getUserDefined6());
    }
    if (GoCoin.hasValue(getUserDefined7()))
    {
      parameters.put("user_defined_7",getUserDefined7());
    }
    if (GoCoin.hasValue(getUserDefined8()))
    {
      parameters.put("user_defined_8",getUserDefined8());
    }
    if (GoCoin.hasValue(getData()))
    {
      parameters.put("data",getData());
    }
    return parameters;
  }

  public String toJSON()
  {
    return GoCoin.toJSON(getJSONMap());
  }
}