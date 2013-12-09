/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/09/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.services;

import java.util.Collection;

import com.gocoin.api.pojo.Token;
import com.gocoin.api.pojo.User;

/**
 * a service that knows how to perform operations for merchant users
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public interface MerchantUserService
{
  /**
   * @return a java.util.Collection of users for a given merchant id
   */
  public Collection<User> getMerchantUsers(Token t, String merchantId);

  /**
   * @return a boolean representing whether or not the merchant user was added
   */
  public boolean addMerchantUser(Token t, String merchantId, User u);

  /**
   * @return a boolean representing whether or not the merchant user was deleted
   */
  public boolean deleteMerchantUser(Token t, String merchantId, User u);
}
