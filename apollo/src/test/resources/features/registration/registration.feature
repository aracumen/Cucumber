Feature: User registration

@eng554 @eng557 @registration @acceptance
Scenario Outline: User registration new account

Given I am on the registration page
When I enter <email_type> unique email address
And I enter <username_type> user name
And I enter valid unique account name 
And I enter a <password_input> as password
And I enter the same string to confirm password
And I submit the registration form
Then I am shown <email_name_outcome> prompt for email name
And I am shown <password_strength_outcome> prompt for password
And verification email sent to the mentioned email address <verification_email_sent>

Examples:
|email_type |username_type | password_input |email_name_outcome 	|password_strength_outcome  |verification_email_sent |
|valid		|valid 		   | strong	  		|valid email address	|STRONG password			|successfully    	   	 |
|invalid	|valid		   | strong	  		|invalid email address	|STRONG password		    |unsuccessfully			 |
|existing	|valid		   | strong	  		|email not available  	|STRONG password			|unsuccessfully			 |
|valid		|Empty 		   | strong	  		|valid email address	|STRONG password			|successfully    	   	 |
|valid		|long		   | strong	  		|valid email address	|STRONG password			|successfully 	 		 |
|valid		|long		   | weak	  		|valid email address 	|WEAK password				|successfully 	 		 |



@eng555 @registration @acceptance
Scenario Outline: User registration exisiting account

Given I am on the registration page
When I enter a valid unique email address
And I enter a valid user name
And I enter <account_visibility> existing account name
And I enter a string as password
And I enter the same string to confirm password
And I submit the registration form
Then I am shown <account_availability> prompt
And I am shown <account_name_suggestion_outcome> as account name suggestions
And I am given <account_access_option> access option

Examples:
|account_visibility |account_availability  |account_name_suggestion_outcome |account_access_option 		|
|discoverable		|account not available |nothing						    |request access to account  |
|non-discoverable	|account not available |similar unique account names    |none						|

@eng555 @registration @acceptance
Scenario: request access to an account

Given I have submitted a user registration form for an exisiting account
And I am on the request access screen
When I enter a user name
And I enter a valid unique email ID
And I send request 
Then I am sent a verification email on the provided email ID

@eng554 @registration @acceptance
Scenario Outline: account validity acessement

Given I am on the registration page
And I have entered name of an exisiting account
When I edit the account name firled to enter a <account_name_type> account name
Then the account name is reassessed for availability
And <appropriate_result> is shown

Examples:
|account_name_type 		  |appropriate_result    	       |
|unique			   		  |account name available	       |
|existing and discoverable|request access to account	   |
|exisitng and hidden	  |similar account name suggestions|

@eng557 @registration @acceptance
Scenario: User verifies email

Given I have been sent a verification email
When I click on the verification link 
Then I am shown a screen with verification completion message
And I am taken to the sign-in page

@eng558 @registration @termsofuse @acceptance
Scenario Outline: Terms of use

Given I have verified my email address
And I try to sign-in for the first time
When I am prompted to <action> terms of use
Then I am able to signin <sign_in_outcome>

Examples:
|action |sign_in_outcome |
|accept | successfully   |
|deny	| unsuccessfully |

@eng560 @signin @acceptance
Scenario Outline: Sign-in

Given I am on the sign-in page
When I enter email of a <user_type> user
And the user belongs to an account with <account_site_access> access to the site
And the site has <site_access_type> access
And I press sign-in button
Then I am able to signin <outcome>

Examples:
|user_type 		|account_site_access |site_access_type |outcome 	  |
|verified 		|valid				 |open			   |successfully  |
|unverified 	|valid				 |open			   |unsuccessfully|
|verified 		|invalid			 |open			   |successfully  |
|verified 		|invalid			 |closed		   |unsuccessfully|
|former			|valid				 |closed		   |unsuccessfully|
|former 		|valid				 |open			   |successfully  |

@eng561 @forgotpassowrd @managepassword @signin @acceptance
Scenario Outline: Forgot password

Given I have clicked on forgot password link on the sign-in page
And  I am on the forgot password screen
When I enter <email_type> account email
And I press submit
Then I am sent password reset link on the mentioned email <outcome>

Examples:
|email_type 	|outcome 		|
|registered 	|successfully	|
|unregistered	|unsuccessfully |
|invalid		|unsuccessfully |

@eng561 @resetpassword @managepassword @signin @acceptance
Scenario Outline: Reset password

Given I have been sent email with reset password instructions
When I click on the reset password link
And I enter a <password_type> password
And I enter <confirm_password> password in confirm password link
Then I am able to signin with new password <outcome>
And the old password is <old_password_status>

Examples:
|password_type |confirm_password |outcome 	    |old_password_status |
|strong		   |same			 |successfully  |disabled			 |
|weak		   |same			 |successfully	|disabled			 |
|strong		   |different		 |unsuccessfully|still enabled		 |

