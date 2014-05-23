Feature: accountManagement

@eng564 @viewuser @acceptance
Scenario: View account users

Given I am logged in as a user with user.manage permission
When I navigae to users index page
Then I can view the users list
And a user has a "username, status, avatar"
And I see an option to search for a user
And I see an option to filter users
And I see an option to sort user list
And I see an option to invite a user

@eng564 @manageuser @usersearch @acceptance
Scenario: search users by name

Given I am logged in as a user with user.manage permission
And I am on the users index page
And a user "Mark" exists for the account
When I search for "Mark"
Then all search results matching the search term are shown

@eng564 @manageuser @usersearch @acceptance
Scenario: search users by email

Given I am logged in as a user with user.manage permission
And I am on the users index page
And a user exists with an email "mark@rightster.com" for the account
When I search for "mark@rightster.com"
Then all search results matching the search term are shown

@eng564 @manageuser @filter
Scenario Outline: filter users

Given I am logged in as a user with user.manage permission
And I am on the users index page
And following users exist in the system:
|name 	|status 	|
|Mark	|active		|
|Muj	|inactive	|
|Mahak	|deactivated|
When I filter the users <filter_criteria>
Then <user_name> user should be present in the results

Examples:
|filter_criteria | user_name |
|active			 | Mark 	 |
|inactive		 | Muj		 |
|deactivated	 | Mahak	 |


@eng564 @manageuser @manual @acceptance
Scenario Outline: sort users
Given I am logged in as a user with user.manage permission
And I am on the users index page
And following users exist in the system:
|name 	|last_active |
|Aaron	| an year ago|
|Ben	| Yesterday	 |
|Carl	| Today		 |
When I sort the users <sort_criteria> 
Then users should be present in following order
|sort_criteria |order	 		|
|Alphabetical  |Aaron, Ben, carl|
|Last active   |Carl, Ben, Aaron|


@eng564 @eng566 @manageuser @inviteusers @acceptance
Scenario Outline: invite users
Given I am logged in as a user with user.manage permission
And I am on the users index page
And I have clicked on invite user button
When I enter an <user_email_type> email
And I select a permission template
And I send the invite 
Then user should be sent an <invite_type> invite <outcome>

Examples:
|user_email_type 	 		 						|invite_type 	|outcome 		|
|valid and unregistered user	 					|registration	|successfully	|
|valid and registered to a different account user	|join account 	|successfully	|
|valid and registered to a same account user		|join account	|unsuccessfully |
|valid and invited to a different account user		|join account	|successfully	|
|valid and invited to same account user				|join account	|unsuccessfully	|
|valid and inactive user of different account		|join account	|successfully	|
|valid and inactive user of same account			|join account	|unsuccessfully	|
|valid and removed user of different account		|join account	|successfully	|
|valid and removed user of same account				|join account	|successfully	|


@eng566 @manageuser @userpermissions @permissiontemplates @acceptance
Scenario: new apply user permissions template

Given I am logged in as a user with user.manage permission
And I have invited a user
And given him following permissions at the time of invite:
|permissions |
|invite user |
|view content|
|upload video|
When user accepts the invitation
And signin to the site
Then user has following permissions on the account:
|permissions |
|invite user |
|view content|
|upload video|



@eng566 @manageuser @acceptance
Scenario Outline: new apply user permissions template

Given I am logged in as a user with user.manage permission
And I have invited a user
And the user has <invitation_acceptance_status> the invitation yet
When I go to the users index for the account
Then user status is <user_status>

Examples:
|invitation_acceptance_status |user_status |
|accepted					  |active	   |
|not accepted yet			  |invited	   |

@eng559 @accountinvite @registration @acceptance
Scenario Outline: accept account invite
#not including specifications uncovered as a result of these questions https://docs.google.com/a/rightster.com/document/d/1BpGfIQSrmLLZ1OYbyH8AEiQuaH6KF1nNRkUlqT1b8sA/edit

Given I am a <registration_status> user on my.rightster.com
And I am not a user of rightster account
When I am invited to join the Rightster account
Then I am sent an <invite_type> invite as <invitation_notification_type>
And I have an option to reject an invite

Examples:
|registration_status |invite_type  |invitation_notification_type|
|registered			 |join account |notification in My accounts |
|non-registered		 |registration |invitation link in an email	|

@eng562 @manageaccountinfo @acceptance
Scenario Outline: Manage account info

Given I am logged in as a user with account.manage permission
And I am on the account info page
When I edit the account name to <new_account_name>
And I enter account categories details
And I enter account website
And I enter account enquiries email
And I enter account telephone number
And I enter account address
And I enter account contacts
And I save the form
Then account information is saved <outcome>
And a <prompt> message is shown

Examples:
|new_account_name 	   |outcome 	   |prompt |
|existing account name |unsuccessfully |