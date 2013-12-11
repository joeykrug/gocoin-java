/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/10/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.services;

import java.util.Collection;

import com.gocoin.api.pojo.Merchant;
import com.gocoin.api.pojo.Token;

/**
 * a service that knows how to perform operations for merchants
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public interface MerchantService
{
  /**
   * NOTE: admin only
   * @return a collection of merchants
   */
  public Collection<Merchant> getMerchants(Token t);

  /**
   * @return a merchant by looking it up by id
   */
  public Merchant getMerchant(Token t, String merchantId);

  /**
   * NOTE: admin only
   * @return add a new merchant
   */
  public Merchant addMerchant(Token t, Merchant m);

  /**
   * @return an updated merchant
   */
  public Merchant updateMerchant(Token t, Merchant m);

  /**
   * NOTE: admin only
   * @return a boolean representing whether or not the merchant was deleted
   */
  public boolean deleteMerchant(Token t, Merchant m);
}
