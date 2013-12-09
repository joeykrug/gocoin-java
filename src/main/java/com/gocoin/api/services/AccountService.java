/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/09/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.services;

import java.util.Collection;

import com.gocoin.api.pojo.Account;
import com.gocoin.api.pojo.Token;

/**
 * a service that knows how to perform operations for accounts
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public interface AccountService
{
  /**
   * @return a java.util.Collection of accounts for a given merchant id
   */
  public Collection<Account> getAccounts(Token t, String merchantId);
}
