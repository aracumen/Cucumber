Feature: Content Ingest

  Background: 
    Given I am logged in as Rightster admin
    And I am on the ingest and delivery tab
    And I am creating a new ingest profile
	

  @ingest @acceptance @smoke @ENG-175
  Scenario: create MRSS ingest
    When I click on New ingest button
    And I enter a valid profile name
    And I select the status to be Active
    And I enter a valid unauthenticated feed url
    And I select Yahoo MRSS 2.0 Translation translation
    And I select 25 historical items for ingest
    And I select default status as Active
    And I save the form
    Then new igest profile should be created
    And 25 historical items should get ingested to the library

  @ingest @regression @new @ENG-175
  Scenario Outline: create new ingest profile
    When I click on New ingest button
    And I enter "<name_type>" profile name
    And I select  "<state>" profile status
    And I enter "<URL_type>" feed URL
    And I select "<translation_type>" translation
    And I select "<item_number>" historical items
    And I select "<default_video_status>"  status for ingested videos
    And I save the form
    Then new ingest profile should get created "<outcome>"
    And "<number of items ingested>" videos should be ingested in the library
    And ingested items should have status "<vid_status>"

    Examples: 
      | name_type     | state    | URL_type | translation_type           | item_number | default_video_status | outcome | number of items ingested | vid_status |
      | long          | Active   | valid    | Yahoo MRSS 2.0 Translation | 5           | Active               | success | 5                        | Active     |
      | numeric       | Active   | valid    | Yahoo MRSS 2.0 Translation | 5           | Active               | success | 5                        | Active     |
      | special_chars | Active   | valid    | Yahoo MRSS 2.0 Translation | 5           | Active               | success | 5                        | Active     |
      | empty         | Active   | valid    | Yahoo MRSS 2.0 Translation | 5           | Active               | failure | 0                        | Draft      |
      # Ingest inactive on firefox is not working ENG-988
      | valid         | Inactive | valid    | Yahoo MRSS 2.0 Translation | 5           | Active               | success | 0                        | Draft      |
      | valid         | Active   | valid    | Yahoo MRSS 2.0 Translation | 5           | Active               | success | 5                        | Active     |
      | valid         | Active   | invalid  | Yahoo MRSS 2.0 Translation | 5           | Active               | success | 5                        | Active     |
      | valid         | Active   | long     | Yahoo MRSS 2.0 Translation | 5           | Active               | success | 5                        | Active     |
      | valid         | Active   | empty    | Yahoo MRSS 2.0 Translation | 5           | Active               | success | 5                        | Active     |
      | valid         | Active   | valid    | Yahoo MRSS 2.0 Translation | 10          | Active               | success | 10                       | Active     |
      | valid         | Active   | valid    | Yahoo MRSS 2.0 Translation | 25          | Active               | success | 15                       | Active     |
      | valid         | Active   | valid    | Yahoo MRSS 2.0 Translation | 50          | Active               | success | 50                       | Active     |
      | valid         | Active   | valid    | Yahoo MRSS 2.0 Translation | 5           | Draft                | success | 5                        | Draft      |
