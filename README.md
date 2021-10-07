# OnePageCRM Java API Wrapper
This project is a comprehensive java API wrapper aimed to abstract some of the difficulties associated with getting started interacting with external APIs, providing you quick and easy access to API resources in useful formats.

The project uses Gradle, an advanced, general purpose build management system.  This allows for streamlined functionality, such as automatically including jars in the build path or running unit tests much more efficiently.

### What can it do?
This project communicates using the OnePageCRM API.  It can, for example, authenticate users, obtain details about their account, and perform a range of actions such as adding calls and creating contacts.


List available gradle commands:
```
./gradlew tasks
```
Run all unit tests:
```
./gradlew test
```

Create jar file for use in projects:
```
./gradlew jar
```

## Getting started

- Clone the repository.

- Import the project into your IDE.

- Create a config.properties file containing your OnePageCRM user_id and auth_key.

## Example
The following is an example of a method which will:
- Authenticate a user.
- Display details about the user and their account.
- Get their Action Stream.
- Get their a-to-z list of Contacts.
- Get their list of Deals.
- Pick the first Contact. 
- And add a new Call for that Contact.
- Create a new Contact.

```java

  public static void main(String[] args) {

    // init 
    OnePageCRM.init(Request.APP_US_SERVER, "user_id", "api_key");

    // Display all the details about the user / account.
    User currentUser = Account.getCurrentUser();
    LOG.info("Current User : " + currentUser);
    LOG.info("User's Team : " + currentUser.getAccount().team);
    LOG.info("User's Settings : " + Account.settings);
    LOG.info("User's Statuses : " + currentUser.getAccount().statuses);
    LOG.info("User's Lead Sources : " + currentUser.getAccount().leadSources);
    LOG.info("User's Custom Fields : " + currentUser.getAccount().customFields);
    LOG.info("User's Company Fields : " + currentUser.getAccount().companyFields);
    LOG.info("User's Call Results : " + currentUser.getAccount().callResults);
    LOG.info("User's Filters : " + currentUser.getAccount().filters);
    LOG.info("User's ContactsCounts : " + currentUser.getAccount().contactsCount);
    LOG.info("User's StreamCount : " + currentUser.getAccount().streamCount);
    LOG.info("User's Predefined Actions : " + currentUser.getAccount().predefinedActions);
    LOG.info("User's Contact Titles : " + currentUser.getAccount().contactTitles);
    LOG.info("User's Account Rights : " + currentUser.getAccountRights());

    // Get user's Action Stream
    ContactList stream = currentUser.actionStream();

    // Get user's list of contacts in alphabetical order
    ContactList contacts = currentUser.contacts();

    // Get user's list of deals (pipeline)
    DealList pipeline = currentUser.pipeline();

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
            .save();

      Contact newContact = new Contact()
            .setLastName("Myles")
            .setCompanyName("Myles Inc.")
            .setFirstName("Cillian");
            .save();
        }
  }
```

## Example
The following is an example of a method which will:
- Authenticate a user.
- Pick their first contact.
- Add a new deal for that contact.

```java
    public static void main(String[] args) throws OnePageException {

        //init
        OnePageCRM.init(Request.APP_US_SERVER, "user_id", "api_key");
        User currentUser = Account.getCurrentUser();

        //Pick the first contact from the Action Stream
        Contact first = currentUser.actionStream().get(0);

        //Create a new deal
        new Deal()
             .setStage(10)
             .setStatus("pending")
             .setContactId(first.getId())
             .setAmount(33.33d)
             .setDate(LocalDate.now())
             .setText("Java Wrapper Deal Text")
             .setName("Java Wrapper Deal Name")
             .save();
    }
```
