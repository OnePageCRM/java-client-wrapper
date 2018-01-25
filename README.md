# OnePageCRM Java API Wrapper
This project is a comprehensive java API wrapper aimed to abstract some of the difficulties associated with getting started interacting with external APIs, providing you quick and easy access to API resources in useful formats.

So far, it only contains a moderately-sized subsection of calls and functions available using the API, though more are currently being added.

## Getting started

- Clone the repository.

- Import the project into your IDE.



## Example
The following is an example of a class which will:
- Log in a user.
- Get their Action Stream.
- Get their a-to-z list of Contacts.
- Get their list of Deals.
- Pick the first Contact. 
- And add a new Call for that Contact.
- Create a new Contact.

```java
package com.onepagecrm;

import com.onepagecrm.models.Call;
import com.onepagecrm.models.Contact;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.User;

public class Driver {

  private static final Logger LOG = Logger.getLogger(Driver.class.getName());

  public static void main(String[] args) {

    // Login 
    User loggedInUser = User.login("username", "password");

    // Display all the details about the user / account.
    LOG.info("Logged in User : " + loggedInUser);
    LOG.info("User's Team : " + loggedInUser.getAccount().team);
    LOG.info("User's Statuses : " + loggedInUser.getAccount().statuses);
    LOG.info("User's Lead Sources : " + loggedInUser.getAccount().leadSources);
    LOG.info("User's Custom Fields : " + loggedInUser.getAccount().customFields);
    LOG.info("User's Call Results : " + loggedInUser.getAccount().callResults);
    LOG.info("User's Filters : " + loggedInUser.getAccount().filters);

    // Get user's Action Stream
    ContactList stream = loggedInUser.actionStream();

    // Get user's list of contacts in alphabetical order
    ContactList contacts = loggedInUser.contacts();

    // Get user's list of deals (pipeline)
    DealList pipeline = loggedInUser.pipeline();

    // Pick the first contact from the Action Stream
    Contact contact = stream.get(0);

    if (contact.isValid()) {

      // Get the list of Actions associated with that contact
      List<Action> actions = contact.getActions();

      // Get the Next Action specifically
      Action nextAction = contact.getNextAction();

      // Create a new Call resource
      Call newCall = new Call()
        .setCallResult(new CallResult()
        .setId("interested")
        .setText("From Java Wrapper..."));
      newCall.save();

      Contact newContact = new Contact()
        .setLastName("Myles")
        .setCompanyName("Myles Inc.")
        .setFirstName("Cillian");
      newContact.save();
    }
  }
}
```
