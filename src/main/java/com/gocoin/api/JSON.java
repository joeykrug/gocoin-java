/**
 * Project: Go Coin API
 * Author:  Aaron LaBella
 * Date:    12/12/2013
 * Copyright: (c) 2013
 */
package com.gocoin.api;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

/**
 * this class is needed because the darn keys in JSONObject
 * aren't sorted, and its annoying when you're trying to debug/read the JSON
 * @author <a href="mailto:aaronlabella@gmail.com">Aaron LaBella</a>
 */
public final class JSON extends JSONObject
{
  private Set<?> keys = null;

  /**
   * make the default constructor private
   */
  @SuppressWarnings("unused")
  private JSON()
  {
  }

  public JSON(Map<String,?> map)
  {
    super(map);
    this.keys = map.keySet();
  }

  /**
   * Get an enumeration of the keys of the JSONObject.
   *
   * @return An iterator of the keys.
   */
  @SuppressWarnings("all")
  public Iterator keys()
  {
    return this.keys.iterator();
  }

  /**
   * Get a set of keys of the JSONObject.
   *
   * @return A keySet.
   */
  @SuppressWarnings("all")
  public Set keySet()
  {
    return this.keys;
  }
}