Feature: Verify SVT website

  Scenario: Check the title of the website
    Given User is on the homepage
    Then User should see the correct website title "SVT Play"

  Scenario: Check the visibility of the website logo
    Given User is on the homepage
    Then User should see the website logo

  Scenario: Check the names of the three main menu links
    Given User is on the homepage
    Then User should see the links "START, PROGRAM, KANALER" in the main menu

  Scenario: Check the visibility and text of the link for Availability in SVT Play
    Given User is on the homepage
    Then User should see the link "Tillgänglighet i SVT Play" with correct link text

  Scenario: Check the main heading on the Availability in SVT Play page
    Given User is on the homepage
    When User clicks the link Tillgänglighet i SVT Play
    Then User should see the main heading "Så arbetar SVT med tillgänglighet"

  Scenario: Check the number of categories listed on the Programs page
    Given User is on the homepage
    When User clicks the Programs link
    Then User should see 17 categories listed

  Scenario: Check the visibility and text of the "KONTAKT" link in the footer
    Given User is on the homepage
    Then User should see the "KONTAKT" link in the footer with correct link text

  Scenario: Check the Uppenbarelsen from FilmTips section
    Given User is on the homepage
    Then User should see "Uppenbarelsen" movie in the FilmTips section

  Scenario: Check the DragRaceSverige program
    Given User is on the homepage
    Then User should see the "Drag Race Sverige" program

  Scenario: Check number of items in the recommendation rail
    Given User is on the homepage
    Then User should see 6 items in recommendation rail

  Scenario: Check search form
    Given User is on the homepage
    When User inputs "Agenda" on the search form and submits
    Then User should see "Agenda" on the search result