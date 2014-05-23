Feature: Upload a file

Background: 
Given I am logged in as Rightster account Admin
And I am on the content tab

@include 
@upload @library @smoke @acceptance
Scenario: Upload single video

When I click on create new video
And upload a video of valid format
Then upload success prompt message is shown
And video is uploaded to library

@include 
@upload @library @regression
Scenario Outline: Upload files of different format

When I click on create new video
And I upload a ‘<format_type>’ file
Then file is uploaded ‘<outcome>’

Examples:
|     format_type        | outcome    |
| MPEG_4_AVC_BASELINE    | successfully    |
| MPEG_4_AVC_MAIN         | successfully    |
| MPEG_4_AVC_HIGH         | successfully    |
| MPEG_4_XVID         | successfully  |
| MPEG_4_MPEG4SP         | successfully  |
| MPEG_4_H263         | successfully  |
| QUICKTIME_AVC_MAIN     | successfully  |
| QUICKTIME_XVID         | successfully  |
| QUICKTIME_H263         | successfully  |
| AVI                 | successfully  |
| FLV                 | successfully  |
| WMV                 | successfully  |
| 3GPP                 | successfully  |
| WEBM             | successfully  |
#| TXT                | unsuccessfully |
#| PDF                | unsuccessfully |
