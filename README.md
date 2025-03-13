# calendoo

![calendarScreen.png](screenshots%2FcalendarScreen.png)

## What is calendoo?
calendoo is a calendar application combining planning of events with a to-do list and a habit tracker – all in one place!
calendoo offers you four different views: one for each (calendar, to-dos, habits) and one focussed view for everything you need to know today.

## How to start this program?
**To start the program simply execute `Calendoo`.** `Calendoo` can be found here:
`calendar/src/main/java/de/hsh/larry/calendar/Calendoo.java`. This project was built using this
[JDK](https://bell-sw.com/pages/downloads/#jdk-11-lts) (package: **FULL JDK**).

---

This project was developed over a period of nine weeks by three students ([Farina](https://github.com/frinnana), [Felix](https://github.com/7daysnosleep), [Laura](https://github.com/xllaurax)) as part of the third-semester module "Programming Project" in the Bachelor's program Media Design Computing at Hanover University of Applied Sciences and Arts, Germany.

## Project Idea Description
In our project, we aimed to develop a calendar application that combines appointments, to-dos, and habits (recurring to-dos). The application is divided into four views:

- The overview page, displaying all upcoming appointments, to-dos, and habits for today
- The calendar view, showing a weekly overview of appointments, to-dos, and habits
- The to-do view, listing all tasks within a specified period
- The habits view, showing all habits along with their rhythm, streak, and status for the last 14 days

Users are able to create, categorize, edit, and delete appointments, to-dos, and habits.

Challenge: Calendar entries were to be synchronized with Google Calendar via an API.

## Similar Applications
- Similar all-in-one applications: Reclaim, Google Calendar + Google Tasks
- Similar calendar applications: Apple Calendar
- Similar to-do applications: Apple Reminders, Notion, Todoist, Trello
- Similar habit applications: Daylio, Done, Habit, Confetti

## Feature List

The key feature of the calendar is that it combines appointments, to-dos, and habits in one place, giving users a clear overview of their tasks. Appointments have start and end times, while to-dos can be marked as not started, in progress, or completed. Habits function like recurring to-dos with a streak system to encourage consistency.

Users can create, edit, and delete all entries, categorize them into different calendars (e.g., "Personal," "Work," "Studies"), and view more details by clicking on an entry. The calendar also supports recurring events and color-coded organization.

### Core Features
- vier Ansichten: Home, Kalender, To-Dos und Habits
- Wechsel zwischen diesen Ansichten mit einer Navigation

#### Home
- Brief greeting and information about the current weekday and date
- Overview of all upcoming appointments, to-dos, and habits for today

#### Calendar
- Weekly calendar view with navigation between weeks
- Toggle specific Calendars on and off to show or hide their events
- Toggle appointments, to-dos, and habits on and off
- Create, edit, and delete appointments
- Click on an entry to view details
  - In the detail view, users can delete the selected entry
  - From the detail view, users can switch to the editor mode to edit an entry

#### To-Dos
- Displays to-dos for today
- Update the status of a to-do
  - Completed to-dos remain visible but grayed out and crossed out for the day
- Toggle Calendars on and off to show or hide their to-dos
- Create, edit, and delete to-dos
- Click on a to-do to view details
  - In the detail view, users can delete the selected to-do
  - From the detail view, users can switch to editor mode to edit a to-do

#### Habits
- Displays all habits, their rhythm, their streak, and their status for the last 14 days
- Clicking on today's status allows users to update it (status of past days cannot be changed)
- Create, edit, and delete habits
- Click on a habit to view details
  - In the detail view, users can delete the selected habit
  - From the detail view, users can switch to editor mode to edit a habit

### Nice-To-Have

These features are considered useful additions once the core features are implemented.

  - General: User notifications for due appointments and to-dos
  - General: Improve visual design using CSS
  - Calendar: Additional views (primarily a monthly view)
  - To-Dos: Support for subtasks
  - Settings: Customizable color palette