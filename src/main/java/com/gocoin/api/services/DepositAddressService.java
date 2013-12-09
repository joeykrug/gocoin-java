/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/09/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.services;

import com.gocoin.api.pojo.Token;

/**
 * a service that knows how to perform operations for deposit addresses
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public interface DepositAddressService
{
  /**
   * @return a boolean representing whether or not the deposit address was generated
   */
  public boolean generateDepositAddress(Token t, String accountId);
}
