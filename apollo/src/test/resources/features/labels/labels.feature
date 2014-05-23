@library 
Feature: Library Labels

Background:
    Given I am on the Labels page

@include 
Scenario Outline: Create new label
    Given I am on the Labels page
    When I click on the New Label button
    And enter a name for label of '<type>'
    And I click on create button 
    Then label gets created '<status>'
Examples:
    | type        | status        |
    | valid     | successfully        |
    | numeric    | successfully        |
    | long        | unsuccessfully    |
    | empty        | unsuccessfully    |
    | special_characters    | unsuccessfully|
    
@include 
Scenario Outline: Cancel new label creation
    Given I am on the Labels page
    When I click on the New Label button
    And enter a name for label of '<type>'
    And I click on cancel button 
    Then label gets created '<status>'
    
    Examples:
    | type        | status        |
    | valid     | unsuccessfully    |
    | Numeric    | unsuccessfully    |
    | long        | unsuccessfully    |
    | empty        | unsuccessfully    |
    | special_characters    | unsuccessfully|
    
@exclude 
Scenario Outline: Edit label
    Given I am on the Labels page
    When I click on an existing label
    And edit label name with '<type>' label name
    And I click on create button 
    Then label gets created '<status>'
    
    Examples:
    | type        | status        |
    | valid     | unsuccessfully    |
    | Numeric    | unsuccessfully    |
    | long        | unsuccessfully    |
    | empty        | unsuccessfully    |
    | special characters    | unsuccessfully|

@exclude 
Scenario: Delete label
    Given I am on the Labels page
    When I click on an delete icon for a label 
    Then label gets deleted
 