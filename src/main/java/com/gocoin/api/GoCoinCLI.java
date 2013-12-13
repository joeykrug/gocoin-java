/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/11/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api;

import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.URL;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Properties;

import com.gocoin.api.GoCoin;
import com.gocoin.api.pojo.Account;
import com.gocoin.api.pojo.Application;
import com.gocoin.api.pojo.AuthCode;
import com.gocoin.api.pojo.ExchangeRates;
import com.gocoin.api.pojo.Invoice;
import com.gocoin.api.pojo.InvoiceSearchResult;
import com.gocoin.api.pojo.Merchant;
import com.gocoin.api.pojo.SearchCriteria;
import com.gocoin.api.pojo.Token;
import com.gocoin.api.pojo.User;

/**
 * a simple command line interface for the GoCoin client
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public class GoCoinCLI
{
//TODO: get the version and artifact from the maven pom file
  /** version */
  public static final String VERSION = "1.0.0-SNAPSHOT";

  /** jar file name */
  public static final String JARFILE_NAME = "gocoin-api-"+VERSION+".jar";

  /** method / api operation */
  protected String apiMethod = "";

  /** token */
  protected Token token = null;

  /** auth code */
  protected AuthCode authcode = null;

  /** token file */
  protected String tokenFile = null;

  /** a map of parameters */
  protected Map<String,Object> params = new LinkedHashMap<String,Object>();

  //the api operations without the -- prefix
  public static final String API_METHOD_VERSION             = "version";
  public static final String API_METHOD_GET_TOKEN           = "get-token";
  public static final String API_METHOD_GET_INVOICE         = "get-invoice";
  public static final String API_METHOD_GET_ACCOUNTS        = "get-accounts";
  public static final String API_METHOD_GET_MERCHANT        = "get-merchant";
  public static final String API_METHOD_GET_MERCHANT_USERS  = "get-merchant-users";
  public static final String API_METHOD_GET_USER            = "get-user";
  public static final String API_METHOD_GET_USER_APPS       = "get-user-apps";
  public static final String API_METHOD_GET_RESOURCE_OWNER  = "get-resource-owner";
  public static final String API_METHOD_CREATE_INVOICE      = "create-invoice";
  public static final String API_METHOD_SEARCH_INVOICES     = "search-invoices";
  public static final String API_METHOD_GET_EXCHANGE        = "get-exchange";

  //collections that help us determine valid cli args
  public static Collection<String> apiMethods = null;
  public static Collection<String> searchOptions = new LinkedList<String>();

  static
  {
    //the allowed api methods
    String[] methods = new String[] {
      API_METHOD_VERSION,
      API_METHOD_GET_EXCHANGE, API_METHOD_GET_TOKEN,
      API_METHOD_GET_ACCOUNTS,
      API_METHOD_GET_INVOICE, API_METHOD_CREATE_INVOICE, API_METHOD_SEARCH_INVOICES,
      API_METHOD_GET_MERCHANT,
      API_METHOD_GET_MERCHANT_USERS,
      API_METHOD_GET_USER, API_METHOD_GET_USER_APPS,
      API_METHOD_GET_RESOURCE_OWNER,
    };
    //convert to collection so we can use contains
    apiMethods = Arrays.asList(methods);
    //add the allowed search options
    searchOptions.add("-merchant");
    searchOptions.add("-status");
    searchOptions.add("-start");
    searchOptions.add("-end");
    searchOptions.add("-page");
    searchOptions.add("-per");
  }

  /**
   * Kick off the program
   */
  public static void main(String[] args) throws Throwable
  {
    //construct and run
    GoCoinCLI cli = new GoCoinCLI();

    //parse command line arguments
    Properties props = cli.initArgs(args);
    if (GoCoin.VERBOSE) { System.out.println("PROPERTIES:\n"+props); }

    //run the program
    try 
    { 
      cli.runProgram(props); 
    }
    catch (Throwable t)
    {
      t.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Default constructor -- run the program
   */
  public GoCoinCLI()
  {
  }

  public Token getToken() throws Exception
  {
    if (this.token == null)
    {
      this.token = getTokenFromFile(this.tokenFile);
    }
    return this.token;
  }

  /**
   * Run the program
   */
  public void runProgram(Properties props) throws Exception
  {
    if (API_METHOD_VERSION.equalsIgnoreCase(apiMethod))
    {
      System.out.println("GoCoin Java API, version: "+VERSION);
      System.exit(1);
    }
    //everything else needs to have a token
    if (!GoCoin.hasValue(getToken()))
    {
      throw new Exception("Token must be passed in order to use the GoCoin CLI!");
    }
    if (API_METHOD_GET_EXCHANGE.equalsIgnoreCase(apiMethod))
    {
      ExchangeRates rates = GoCoin.getExchangeRates();
      GoCoin.print(rates.toJSON());
    }
    else if (API_METHOD_GET_TOKEN.equalsIgnoreCase(apiMethod))
    {
      Token t = GoCoin.getAuthToken((AuthCode)params.get("authcode"));
      GoCoin.print(t.toJSON());
    }
    else if (API_METHOD_GET_ACCOUNTS.equalsIgnoreCase(apiMethod))
    {
      Collection<Account> accounts = GoCoin.getAccountService().getAccounts(getToken(),(String)params.get("merchantId"));
      //NOTE: this approach uses the get methods
      //as the fields of the json, which works out okay for now in this case
      GoCoin.print(GoCoin.toJSON(accounts));
    }
    else if (API_METHOD_GET_INVOICE.equalsIgnoreCase(apiMethod))
    {
      Invoice i = GoCoin.getInvoiceService().getInvoice(getToken(),(String)params.get("invoiceId"));
      GoCoin.print(i.toJSON());
    }
    else if (API_METHOD_GET_MERCHANT.equalsIgnoreCase(apiMethod))
    {
      Merchant m = GoCoin.getMerchantService().getMerchant(getToken(),(String)params.get("merchantId"));
      GoCoin.print(m.toJSON());
    }
    else if (API_METHOD_GET_MERCHANT_USERS.equalsIgnoreCase(apiMethod))
    {
      Collection<User> users = GoCoin.getMerchantUserService().getMerchantUsers(getToken(),(String)params.get("merchantId"));
      //manually print our json because the json array approach doesn't
      //work that great in this case
      GoCoin.print("[");
      if (users != null)
      {
        for(User u : users)
        {
          GoCoin.print(u.toJSON());
        }
      }
      GoCoin.print("]");
    }
    else if (API_METHOD_GET_USER.equalsIgnoreCase(apiMethod))
    {
      User u = GoCoin.getUserService().getUser(getToken(),(String)params.get("userId"));
      GoCoin.print(u.toJSON());
    }
    else if (API_METHOD_GET_USER_APPS.equalsIgnoreCase(apiMethod))
    {
      User u = new User();
      u.setId((String)params.get("userId"));
      Collection<Application> apps = GoCoin.getUserService().getUserApplications(getToken(),u);
      //manually print our json because the json array approach doesn't
      //work that great in this case
      GoCoin.print("[");
      if (apps != null)
      {
        for(Application a : apps)
        {
          GoCoin.print(a.toJSON());
        }
      }
      GoCoin.print("]");
    }
    else if (API_METHOD_GET_RESOURCE_OWNER.equalsIgnoreCase(apiMethod))
    {
      User u = GoCoin.getUserService().getResourceOwner(getToken());
      GoCoin.print(u.toJSON());
    }
    else if (API_METHOD_CREATE_INVOICE.equalsIgnoreCase(apiMethod))
    {
      //System.out.println("INVOICE HERE:\n"+((Invoice)params.get("invoice")).toJSON());
      Invoice i = GoCoin.getInvoiceService().createInvoice(
        getToken(),(String)params.get("merchantId"),(Invoice)params.get("invoice")
      );
      GoCoin.print(i.toJSON());
    }
    else if (API_METHOD_SEARCH_INVOICES.equalsIgnoreCase(apiMethod))
    {
      InvoiceSearchResult result = GoCoin.getInvoiceService().searchInvoices(
        getToken(),(SearchCriteria)params.get("criteria")
      );
      GoCoin.print(result.toJSON());
    }
    if (GoCoin.DEBUG) { GoCoin.print("[DEBUG]: Program COMPLETE!"); }
  }

  /**
   * Initialize any program arguments
   */
  public Properties initArgs(String[] args) throws Exception
  {
    int size = args.length;

    int i=0;

    Properties props = null;

    try
    {
      //apply arguments
      for(; i<size; i++)
      {
        if ("-?".equals(args[i]) || "-h".equals(args[i]))
        {
          //print usage and exit
          usage(props);
        }
        //debug
        else if ("-d".equals(args[i]))
        {
          GoCoin.DEBUG = true; 
        }
        //verbose
        else if ("-v".equals(args[i]))
        {
          GoCoin.DEBUG = true; 
          GoCoin.VERBOSE = true; 
        }
        //token
        else if ("--token-file".equals(args[i]))
        {
          this.tokenFile = args[++i];
        }
        else if (args[i].startsWith("--"))
        {
          String arg = args[i].substring(2);
          if (apiMethods.contains(arg))
          {
            apiMethod = arg;
            if (API_METHOD_VERSION.equals(arg))
            {
              //no params needed
            }
            else if (API_METHOD_GET_EXCHANGE.equals(arg))
            {
              //no params needed
            }
            else if (API_METHOD_GET_TOKEN.equals(arg))
            {
              String json = getFileContents(args[++i]);
              AuthCode ac = new AuthCode(json);
              //potentially use the default client id and secret from the properties file
              if (this.authcode != null)
              {
                if (!GoCoin.hasValue(ac.getClientId()))     { ac.setClientId(this.authcode.getClientId()); }
                if (!GoCoin.hasValue(ac.getClientSecret())) { ac.setClientSecret(this.authcode.getClientSecret()); }
              }
              params.put("authcode",ac);
            }
            else if (API_METHOD_GET_ACCOUNTS.equals(arg))
            {
              params.put("merchantId",args[++i]);
            }
            else if (API_METHOD_GET_MERCHANT.equals(arg))
            {
              params.put("merchantId",args[++i]);
            }
            else if (API_METHOD_GET_MERCHANT_USERS.equals(arg))
            {
              params.put("merchantId",args[++i]);
            }
            else if (API_METHOD_GET_USER.equals(arg))
            {
              params.put("userId",args[++i]);
            }
            else if (API_METHOD_GET_USER_APPS.equals(arg))
            {
              params.put("userId",args[++i]);
            }
            else if (API_METHOD_GET_INVOICE.equals(arg))
            {
              params.put("invoiceId",args[++i]);
            }
            else if (API_METHOD_CREATE_INVOICE.equals(arg))
            {
              params.put("merchantId",args[++i]);
              String json = getFileContents(args[++i]);
              params.put("invoice",new Invoice(json));
            }
            else if (API_METHOD_SEARCH_INVOICES.equals(arg))
            {
              SearchCriteria criteria = new SearchCriteria();
              if (i+1 != size)
              {
                int j = i+1;
                while(j<size && args[j].startsWith("-") && searchOptions.contains(args[j]))
                {
                  String option = args[j];
                  String value = args[++j];
                  //System.out.println("FOUND SEARCH OPTION ["+option+"]=["+value+"]");
                  if ("-merchant".equalsIgnoreCase(option)) { criteria.setMerchantId(value); }
                  if ("-status".equalsIgnoreCase(option)) { criteria.setStatus(value); }
                  if ("-start".equalsIgnoreCase(option)) { criteria.setStartTs(value); }
                  if ("-end".equalsIgnoreCase(option)) { criteria.setEndTs(value); }
                  if ("-page".equalsIgnoreCase(option)) { criteria.setPageNumber(value); }
                  if ("-per".equalsIgnoreCase(option)) { criteria.setPerPage(value); }
                  j++;
                }
                //put our index to i since we've iterated through it
                i = j;
              }
              params.put("criteria",criteria);
            }
          }
          else
          {
            if (GoCoin.VERBOSE)
            {
              System.out.println("[WARNING]: Unknown api method ["+arg+"]");
            }
          }
        }
        //unknown, abort
        else
        {
          System.err.println("Unknown parameter ["+args[i]+"]");
          //print usage and exit
          usage(props);
        }
      }
    }
    catch (ArrayIndexOutOfBoundsException aiobe)
    {
      System.err.printf("\n"+"Invalid program argument %s",args[i-1]);
      usage(props);
    }

    if (props == null)
    {
      props = getDefaultProperties();
    }

    return props;
  }

  /**
   * @return the default properties via the class loader
   */
  public Properties getDefaultProperties()
  {
    final String DEFAULT_PROPERTIES_FILE = "gocoin.properties";

    //try to find the default properties file via the class loader
    URL u = this.getClass().getClassLoader().getResource(DEFAULT_PROPERTIES_FILE);
    if (u == null)
    {
      try
      {
        String propFile = System.getProperty(DEFAULT_PROPERTIES_FILE);
        File f = null;
        //use the given file via the system property
        //otherwise, look for the properties file in the current directory
        if (GoCoin.hasValue(propFile)) { f = new File(propFile); }
        else                           { f = new File(DEFAULT_PROPERTIES_FILE); }
        //use the url from the file if it exists
        if (f != null && f.exists()) { u = f.toURI().toURL(); }
      }
      catch (Exception e)
      {
        if (GoCoin.VERBOSE)
        {
          GoCoin.log(e);
        }
      }
    }
    if (u == null) { return null; }
    if (GoCoin.VERBOSE) { GoCoin.printf("[WARNING]: Using default properties [%s]",u); }
    Properties p = new Properties();
    InputStream is = null;
    try
    {
      is = u.openStream();
      p.load(is);
    }
    catch (Exception e)
    {
      GoCoin.log(e);
    }
    finally
    {
      //we always clean up after ourselves in a finally block for input streams
      if (is != null)
      {
        try { is.close(); }
        catch (IOException e) { }
      }
    }
    //set the token, but only if someone didn't give us a token file to use
    if (!GoCoin.hasValue(this.tokenFile) && p.containsKey("token"))
    {
      String t = p.getProperty("token");
      if (GoCoin.hasValue(t))
      {
        if (GoCoin.VERBOSE) { GoCoin.printf("[WARNING]: Using token from properties file"); }
        this.token = new Token(t,"","");
      }
    }
    //set the auth code
    if (p.containsKey("client.id") && p.containsKey("client.secret"))
    {
      String id = p.getProperty("client.id");
      String secret = p.getProperty("client.secret");
      if (GoCoin.hasValue(id) && GoCoin.hasValue(secret))
      {
        if (GoCoin.VERBOSE) { GoCoin.printf("[WARNING]: Using client id and secret from properties file"); }
        this.authcode = new AuthCode(null,null,id,secret,null);
      }

    }

    return p;
  }

  /**
   * @return the token by reading it from the file for a more secure CLI
   */
  public static final Token getTokenFromFile(String file) throws Exception
  {
    if (!GoCoin.hasValue(file)) { throw new FileNotFoundException("Token file must be passed!"); }
    String token = GoCoinCLI.getFileContents(file);
    if (GoCoin.hasValue(token))
    {
      token = token.replaceAll("\n","").trim();
      return new Token(token,"","");
    }
    return null;
  }

  /**
   * @return the token by reading it from the file for a more secure CLI
   */
  public static final String getFileContents(String file) throws Exception
  {
    InputStream is = null;
    if (!GoCoin.hasValue(file)) { throw new FileNotFoundException("File must be passed!"); }
    try
    {
      File f = new File(file);
      if (!f.exists()) { throw new FileNotFoundException("File ["+file+"] not found!"); }
      is = new FileInputStream(f);
      BufferedReader in = new BufferedReader(new InputStreamReader(is));
      String line = "";
      StringBuilder sb = new StringBuilder();
      //if for some crazy reason there are multiple lines, the last line wins
      while ((line = in.readLine()) != null)
      {
        sb.append(line).append("\n");
      }
      return sb.toString();
    }
    catch (Exception e)
    {
      throw e;
    }
    finally
    {
      if (is != null)
      {
        try { is.close(); }
        catch (IOException e) { }
      }
    }
  }

  /**
   * get the usage
   */
  public static final void usage(Properties props)
  {
    StringBuilder sb = new StringBuilder("\n");
    sb.append("Usage: java -jar ").append(JARFILE_NAME).append(" [program arguments] [options]");
    sb.append("\n\n");
    sb.append("    --version\n");
    sb.append("    --token-file [token file]\n");
    sb.append("    --get-exchange\n");
    sb.append("    --get-token [exchange code json file]\n");
    sb.append("    --get-accounts [merchant id]\n");
    sb.append("    --get-invoice [invoice id]\n");
    sb.append("    --get-merchant [merchant id]\n");
    sb.append("    --get-merchant-users [merchant id]\n");
    sb.append("    --get-user [user id]\n");
    sb.append("    --get-user-apps [user id]\n");
    sb.append("    --get-resource-owner\n");
    sb.append("    --create-invoice [merchant id] [invoice json file]\n");
    sb.append("    --search-invoices [search options]\n");
    sb.append("        search options:\n");
    sb.append("            -merchant [merchant id]\n");
    sb.append("            -status [status]\n");
    sb.append("            -start [start time, format: "+GoCoin.TIMESTAMP_PATTERN_CUT+"]\n");
    sb.append("            -end [end time, format: "+GoCoin.TIMESTAMP_PATTERN_CUT+"]\n");
    sb.append("            -page [page number]\n");
    sb.append("            -per [per page]\n");
    sb.append("\n");
    sb.append("    options:\n");
    sb.append("        -h|-?  print help/usage\n");
    sb.append("        -d     debug\n");
    sb.append("        -v     verbose\n");
    sb.append("\n");
    sb.append("NOTE: storing client id, secret, and token in gocoin.properties is also allowed\n");
    sb.append("\n");
    sb.append("ie: java -jar ").append(JARFILE_NAME);
    sb.append(" --token-file your.token.file --get-resource-owner\n");
    System.out.println(sb.toString());
    System.exit(1);
  }
}
