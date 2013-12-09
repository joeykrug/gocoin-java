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

import org.json.JSONObject;

import com.gocoin.api.GoCoin;

/**
 * a simple POJO class to represent a user
 *
 * {
 *   "id":63,
 *   "name":"AaronApp",
 *   "uid":"661b989eda4e39e65456646f3a214e35039a8823666916dac717f746afa34018",
 *   "secret":"977691490acff424973dfcf3fa32ba5161c7cda673af7b69a82c232e943f668b",
 *   "redirect_uri":"http://www.google.com",
 *   "created_at":"2013-12-05T12:23:12.776Z",
 *   "updated_at":"2013-12-05T12:23:12.776Z",
 *   "owner_id":"9127be09-24d0-4989-bc45-eb143c65506d",
 *   "owner_type":null,
 *   "protected":false,
 *   "allow_grant_type_password":false,
 *   "allow_grant_type_authorization_code":true,
 *   "allow_grant_type_client_credentials":false,
 *   "allow_grant_type_implicit":false
 * }
 *
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class Application
{
  private int id = 0;
  private String name = null;
  private String uid = null;
  private String secret = null;
  private String redirectUri = null;
  private Calendar createTs = null;
  private Calendar updateTs = null;
  private String ownerId = null;
  private String ownerType = null;
  private boolean protectedFlag = false;
  private boolean allowGrantTypePassword = false;
  private boolean allowGrantTypeAuthCode = false;
  private boolean allowGrantTypeClientCredentials = false;
  private boolean allowGrantTypeImplicit = false;

  public Application(String jsonText)
  {
    this(new JSONObject(jsonText));
  }

  public Application(JSONObject json)
  {
    this(
      json.getInt("id"), json.getString("name"), json.getString("uid"),
      json.getString("secret"), json.getString("redirect_uri"),
      json.getString("created_at"), json.getString("updated_at"),
      json.getString("owner_id"), null,
      json.getBoolean("protected"),
      json.getBoolean("allow_grant_type_password"),
      json.getBoolean("allow_grant_type_authorization_code"),
      json.getBoolean("allow_grant_type_client_credentials"),
      json.getBoolean("allow_grant_type_implicit")
    );
    Object ownerType = json.get("owner_type");
    if (GoCoin.hasValue(ownerType))
    {
      this.ownerType = ownerType.toString();
    }
  }

  public Application(int id, String name, String uid, String secret, String redirectUri,
      String createTs, String updateTs, String ownerId, String ownerType,
      boolean protectedFlag, boolean allowGrantTypePassword, boolean allowGrantTypeAuthCode,
      boolean allowGrantTypeClientCredentials, boolean allowGrantTypeImplicit
    )
  {
    this.id = id;
    this.name = name;
    this.uid = uid;
    this.secret = secret;
    this.redirectUri = redirectUri;
    setCreateTs(createTs);
    setUpdateTs(updateTs);
    this.ownerId = ownerId;
    this.ownerType = ownerType;
    this.protectedFlag = protectedFlag;
    this.allowGrantTypePassword = allowGrantTypePassword;
    this.allowGrantTypeAuthCode = allowGrantTypeAuthCode;
    this.allowGrantTypeClientCredentials = allowGrantTypeClientCredentials;
    this.allowGrantTypeImplicit = allowGrantTypeImplicit;
  }

  public int getId() { return this.id; }
  public void setId(int i) { this.id = i; }

  public String getName() { return this.name; }
  public void setName(String s) { this.name = s; }

  public String getUid() { return this.uid; }
  public void setUid(String s) { this.uid = s; }

  public String getSecret() { return this.secret; }
  public void setSecret(String s) { this.secret = s; }

  public String getRedirectUri() { return this.redirectUri; }
  public void setRedirectUri(String s) { this.redirectUri = s; }

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

  public String getOwnerId() { return this.ownerId; }
  public void setOwnerId(String s) { this.ownerId = s; }

  public String getOwnerType() { return this.ownerType; }
  public void setOwnerType(String s) { this.ownerType = s; }

  public boolean getProtectedFlag() { return this.protectedFlag; }
  public void setProtectedFlag(boolean tf) { this.protectedFlag = tf; }

  public boolean getAllowGrantTypePassword() { return this.allowGrantTypePassword; }
  public void setAllowGrantTypePassword(boolean tf) { this.allowGrantTypePassword = tf; }

  public boolean getAllowGrantTypeAuthCode() { return this.allowGrantTypeAuthCode; }
  public void setAllowGrantTypeAuthCode(boolean tf) { this.allowGrantTypeAuthCode = tf; }

  public boolean getAllowGrantTypeClientCredentials() { return this.allowGrantTypeClientCredentials; }
  public void setAllowGrantTypeClientCredentials(boolean tf) { this.allowGrantTypeClientCredentials = tf; }

  public boolean getAllowGrantTypeImplicit() { return this.allowGrantTypeImplicit; }
  public void setAllowGrantTypeImplicit(boolean tf) { this.allowGrantTypeImplicit = tf; }

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Application [");
    sb.append(getId()).append(",name=").append(getName());
    sb.append(",uid=").append(getUid());
    sb.append(",secret=").append(getSecret());
    sb.append(",redirectUri=").append(getRedirectUri());
    sb.append(",createTs=").append(getCreateTsString()).append(",updateTs=").append(getUpdateTsString());
    sb.append(",ownerId=").append(getOwnerId());
    sb.append(",ownerType=").append(getOwnerType());
    sb.append(",protected=").append(getProtectedFlag());
    sb.append(",allowGrantTypePassword=").append(getAllowGrantTypePassword());
    sb.append(",allowGrantTypeAuthCode=").append(getAllowGrantTypeAuthCode());
    sb.append(",allowGrantTypeClientCredentials=").append(getAllowGrantTypeClientCredentials());
    sb.append(",allowGrantTypeImplicit=").append(getAllowGrantTypeImplicit());
    sb.append("]");
    return sb.toString();
  }

  public String toJSON()
  {
    Map<String,Object> parameters = new LinkedHashMap<String,Object>();
    parameters.put("id",Integer.valueOf(getId()));
    if (GoCoin.hasValue(getName()))
    {
      parameters.put("name",getName());
    }
    if (GoCoin.hasValue(getUid()))
    {
      parameters.put("uid",getUid());
    }
    if (GoCoin.hasValue(getSecret()))
    {
      parameters.put("secret",getSecret());
    }
    if (GoCoin.hasValue(getRedirectUri()))
    {
      parameters.put("redirect_uri",getRedirectUri());
    }
    if (GoCoin.hasValue(getOwnerId()))
    {
      parameters.put("owner_id",getOwnerId());
    }
    if (GoCoin.hasValue(getOwnerType()))
    {
      parameters.put("owner_type",getOwnerType());
    }
    parameters.put("protected",String.valueOf(getProtectedFlag()));
    parameters.put("allow_grant_type_password",String.valueOf(getAllowGrantTypePassword()));
    parameters.put("allow_grant_type_authorization_code",String.valueOf(getAllowGrantTypeAuthCode()));
    parameters.put("allow_grant_type_client_credentials",String.valueOf(getAllowGrantTypeClientCredentials()));
    parameters.put("allow_grant_type_implicit",String.valueOf(getAllowGrantTypeImplicit()));

    return GoCoin.toJSON(parameters);
  }
}