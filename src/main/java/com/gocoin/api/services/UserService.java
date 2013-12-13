/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/08/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api.services;

import java.util.Collection;
import java.util.Map;

import com.gocoin.api.pojo.Application;
import com.gocoin.api.pojo.Token;
import com.gocoin.api.pojo.User;

/**
 * a service that knows how to perform operations for users
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public interface UserService
{
  /**
   * @return the User pojo after making an API lookup call for resource owner
   */
  public User getResourceOwner(Token t);

  /**
   * @return the User pojo after making an API lookup call on the given user id
   */
  public User getUser(Token t, String userId);

  /**
   * @return a java.util.Collection of users
   */
  public Collection<User> listUsers(Token t, Map<String,String> parameters);

  /**
   * @return the User pojo after creating it via the API
   */
  public User createUser(Token t, User u, Map<String,String> parameters);

  /**
   * @return the User pojo after updating it via the API
   */
  public User updateUser(Token t, User u, Map<String,String> parameters);

  /**
   * @return a boolean representing whether or not the User was deleted
   */
  public boolean deleteUser(Token t, User u, Map<String,String> parameters);

  /**
   * @return a boolean representing whether or not the password was updated
   */
  public boolean updatePassword(Token t, User u, Map<String,String> parameters);

  /**
   * @return a boolean representing whether or not the password was reset
   */
  public boolean resetPassword(Token t, User u, Map<String,String> parameters);

  /**
   * @return a boolean representing whether or not the password was reset
   */
  public boolean resetPasswordWithToken(Token t, User u, Map<String,String> parameters);

  /**
   * @return a boolean representing whether or not the confirmation email was sent
   */
  public boolean requestConfirmationEmail(Token t, User u, Map<String,String> parameters);

  /**
   * @return a boolean representing whether or not the user was confirmed
   */
  public boolean confirmUser(Token t, User u, Map<String,String> parameters);

  /**
   * @return a java.util.Collection of applications given a user
   */
  public Collection<Application> getUserApplications(Token t, User u);
}
