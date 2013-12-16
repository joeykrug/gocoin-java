gocoin-java
===========

A Java client library for the GoCoin API.<br>
Current Version: <br>
1.0.0-SNAPSHOT 
[Download](http://gocoin-developers.s3.amazonaws.com/downloads/gocoin-java-1.0.0-SNAPSHOT.zip)


## Usage

```java
  ...
  //get a scope or set of scopes, ie: Scope.getAllScopes()
  String scope = Scope.getScope(
    Scope.USER_READ_WRITE, Scope.INVOICE_READ_WRITE
  );

  //get an authorization url
  URL auth_url  = GoCoin.getAuthURL(
    clientId,     //client/appplication id
    redirectId,   //redirect uri
    scope,        //scope
    "optional"    //state
  );

  //get a new oauth token
  Token token = GoCoin.getAuthToken(
    code,         //code
    clientId,     //client/appplication id
    clientSecret, //client secret
    redirectUri   //redirect uri
  );

  //call a service method, ie: get the invoice by id
  Invoice i = GoCoin.getInvoiceService().getInvoice(t,invoiceId);
  ...
```

## Services and Methods

### GoCoin

###### static ExchangeRates getExchangeRates()
###### static URL getAuthURL(String clientId, String redirectUri, String scope, String state)
###### static Token getAuthToken(String code, String clientId, String clientSecret, String redirectUri)
###### static Token getAuthToken(AuthCode authcode)
###### static AccountService getAccountService() 
###### static DepositAddressService getDepositAddressService()
###### static InvoiceService getInvoiceService()
###### static MerchantService getMerchantService()
###### static MerchantUserService getMerchantUserService()
###### static UserService getUserService()
###### static Calendar parseTimestamp(String s)
###### static String formatTimestamp(Calendar c)

### UserService

###### boolean confirmUser(Token t, User u, Map<String,String> parameters)
###### User createUser(Token t, User u, Map<String,String> parameters)
###### boolean deleteUser(Token t, User u, Map<String,String> parameters)
###### User getResourceOwner(Token t)
###### User getUser(Token t, String userId)
###### Collection<Application> getUserApplications(Token t, User u)
###### Collection<User> listUsers(Token t, Map<String,String> parameters)
###### boolean requestConfirmationEmail(Token t, User u)
###### boolean resetPassword(Token t, User u)
###### boolean resetPasswordWithToken(Token t, User u, Map<String,String> parameters)
###### boolean updatePassword(Token t, User u, Map<String,String> parameters)
###### User updateUser(Token t, User u, Map<String,String> parameters)

### MerchantService

###### Merchant addMerchant(Token t, Merchant m)           NOTE: admin only
###### boolean deleteMerchant(Token t, Merchant m)         NOTE: admin only
###### Merchant getMerchant(Token t, String merchantId)
###### Collection<Merchant> getMerchants(Token t)          NOTE: admin only
###### Merchant updateMerchant(Token t, Merchant m)

### MerchantUserService

###### boolean addMerchantUser(Token t, String merchantId, User u)
###### boolean deleteMerchantUser(Token t, String merchantId, User u)
###### Collection<User> getMerchantUsers(Token t, String merchantId)

### AccountService

##### Collection<Account> getAccounts(Token t, String merchantId)

### DepositAddressService

###### boolean generateDepositAddress(Token t, String accountId)

### InvoiceService

###### Invoice createInvoice(Token t, String merchantId, Invoice i)
###### Invoice getInvoice(Token t, String invoiceId)
###### InvoiceSearchResult searchInvoices(Token t, SearchCriteria criteria)

### License

Copyright 2013 GoCoin Pte. Ltd.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
