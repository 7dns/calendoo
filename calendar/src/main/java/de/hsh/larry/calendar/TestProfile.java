package de.hsh.larry.calendar;

import de.hsh.larry.calendar.models.*;
import javafx.scene.paint.Color;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The TestProfile class is for demonstrating the functionalities of calendoo without creating a new profile.
 * It contains multiple exemplary events (timed and all day), habits and to-dos.
 *
 * @author Felix
 */
public class TestProfile {

    /**
     * Creates a new TestProfile with multiple Entries.
     *
     * @return  The TestProfile.
     */
    public static Profile createProfile() {
        Profile testProfile = new Profile("Test");

        Calendar uni = new Calendar("Uni", Color.MEDIUMSLATEBLUE);
        Calendar work = new Calendar("Work", Color.LIGHTSKYBLUE);
        Calendar personal = new Calendar("Personal", Color.LIGHTGREEN);

        testProfile.addCalendar(uni);
        testProfile.addCalendar(work);
        testProfile.addCalendar(personal);

        LocalDate today = LocalDate.now();
        LocalDate monday = today;

        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }

        LocalDate tuesday = monday.plusDays(1);
        LocalDate wednesday = monday.plusDays(2);
        LocalDate thursday = monday.plusDays(3);
        LocalDate friday = monday.plusDays(4);
        LocalDate saturday = monday.plusDays(5);
        LocalDate sunday = monday.plusDays(6);

        // MONDAY
        Habit dailyHabitReading = new Habit(personal, "Reading", LocalDate.of(2025, 1, 1), LocalTime.of(7, 0), Rhythm.DAILY);
        dailyHabitReading.setColor(Color.DARKSEAGREEN);
        dailyHabitReading.setIcon("/de/hsh/larry/calendar/views/habits/icons/book.png");
        Event mondayWorkshop01 = new Event(uni, "Concept Design Workshop", monday, LocalTime.of(9, 0), LocalTime.of(12, 0));
        mondayWorkshop01.setRhythm(Rhythm.WEEKLY);
        mondayWorkshop01.setColor(Color.LIGHTSALMON);
        mondayWorkshop01.setDescription("Develop a workbook filled with creative tasks and exercises.");
        Event mondayWorkshop02 = new Event(uni, "Project Design Workshop", monday, LocalTime.of(13, 0), LocalTime.of(16, 0));
        mondayWorkshop02.setRhythm(Rhythm.WEEKLY);
        mondayWorkshop02.setColor(Color.LIGHTSALMON);
        mondayWorkshop02.setDescription("Create a game using Unity from concept to implementation.");
        Event mondayVolleyball = new Event(personal, "Volleyball", monday, LocalTime.of(19, 0), LocalTime.of(21, 0));
        mondayVolleyball.setRhythm(Rhythm.WEEKLY);
        mondayVolleyball.setColor(Color.MEDIUMSEAGREEN);

        // TUESDAY
        Event tuesdayWork = new Event(work, "Work", tuesday, LocalTime.of(9, 0), LocalTime.of(17, 0));
        tuesdayWork.setRhythm(Rhythm.WEEKLY);
        tuesdayWork.setColor(Color.CORNFLOWERBLUE);
        ToDo tuesdayBathroom = new ToDo(personal, "Clean the bathroom", tuesday, LocalTime.of(19, 0));
        tuesdayBathroom.setRhythm(Rhythm.WEEKLY);
        tuesdayBathroom.setColor(Color.LIGHTSTEELBLUE);

        // WEDNESDAY
        Event wednesdayBirthdayFinn = new Event(personal, "Finn's Birthday", wednesday);
        wednesdayBirthdayFinn.setColor(Color.CADETBLUE);
        Event wednesdayMeeting = new Event(uni, "Meeting Coding Project", wednesday, LocalTime.of(10, 45), LocalTime.of(11, 15));
        wednesdayMeeting.setRhythm(Rhythm.WEEKLY);
        wednesdayMeeting.setColor(Color.LIGHTCORAL);
        wednesdayMeeting.setDescription("Discuss progress, address challenges, and align on next steps for the project.");
        wednesdayMeeting.setLocation("University of Applied Sciences Hanover");
        Event wednesdayLanguageCourse = new Event(uni, "Language Course", wednesday, LocalTime.of(15, 0), LocalTime.of(18, 15));
        wednesdayLanguageCourse.setRhythm(Rhythm.WEEKLY);
        wednesdayLanguageCourse.setColor(Color.MEDIUMPURPLE);
        wednesdayLanguageCourse.setDescription("Spanish lessons at a B1 proficiency level.");
        Event wednesdayCinema = new Event(personal, "Cinema with Marcel", wednesday, LocalTime.of(20, 0), LocalTime.of(22, 30));
        wednesdayCinema.setColor(Color.MEDIUMSEAGREEN);

        // THURSDAY
        Event thursdayDentist = new Event(personal, "Dentist Appointment", thursday, LocalTime.of(10, 0));
        thursdayDentist.setColor(Color.GOLD);
        Event thursdayStudying = new Event(uni, "Study for PR3", thursday, LocalTime.of(13, 0), LocalTime.of(17, 0));
        thursdayStudying.setColor(Color.LIGHTCORAL);
        thursdayStudying.setDescription("Practice and learn programming in C and C++.");
        ToDo thursdayMail = new ToDo(work, "Reply to Ayla's email", thursday, LocalTime.of(18, 0));
        thursdayMail.setColor(Color.LIGHTSTEELBLUE);

        // FRIDAY
        Event fridayExam = new Event(uni, "BSN1 exam", friday, LocalTime.of(8, 0), LocalTime.of(9, 30));
        fridayExam.setColor(Color.INDIANRED);
        Event fridayProjectWork = new Event(uni, "Coding Project", friday, LocalTime.of(10, 0), LocalTime.of(15, 0));
        fridayProjectWork.setRhythm(Rhythm.WEEKLY);
        fridayProjectWork.setColor(Color.LIGHTCORAL);
        Event fridayConcert = new Event(personal, "Concert", friday, LocalTime.of(20, 0), LocalTime.of(22, 30));
        fridayConcert.setColor(Color.MEDIUMSEAGREEN);

        ToDo todo01 = new ToDo(work, "Write email to Eva", today);
        ToDo todo02 = new ToDo(uni, "Return books to the library", today, LocalTime.of(6, 0));
        ToDo todo03 = new ToDo(personal, "Schedule an appointment with the technician", today, LocalTime.of(9, 30));
        ToDo todo04 = new ToDo(personal, "Change the bedsheets", today, LocalTime.of(15, 30));

        todo01.setColor(Color.LIGHTSTEELBLUE);
        todo02.setColor(Color.LIGHTSTEELBLUE);
        todo03.setColor(Color.LIGHTSTEELBLUE);
        todo04.setColor(Color.LIGHTSTEELBLUE);

        Habit habit01 = new Habit(personal, "Meditation", LocalDate.of(2025, 1, 3), LocalTime.of(17, 0), Rhythm.WEEKLY);
        habit01.setColor(Color.PLUM);
        habit01.setIcon("/de/hsh/larry/calendar/views/habits/icons/morning.png");

        LocalDate current = LocalDate.of(2025, 1, 1);
        while (current.isBefore(today)) {
            dailyHabitReading.setStreak(current, true);
            habit01.setStreak(current, current.getDayOfWeek() == DayOfWeek.FRIDAY);
            current = current.plusDays(1);
        }

        // SATURDAY
        Event saturdayBirthdayParty = new Event(personal, "Finn's Birthday Party", saturday, LocalTime.of(20, 0), LocalTime.of(23, 30));
        saturdayBirthdayParty.setColor(Color.MEDIUMSEAGREEN);

        // SUNDAY
        ToDo sundayPlants = new ToDo(personal, "Water the plants", sunday, LocalTime.of(9, 0));
        sundayPlants.setColor(Color.LIGHTSTEELBLUE);
        sundayPlants.setRhythm(Rhythm.WEEKLY);
        Event sundayBrunch = new Event(personal, "Brunch", sunday, LocalTime.of(10, 0), LocalTime.of(13, 0));
        sundayBrunch.setColor(Color.MEDIUMSEAGREEN);
        sundayBrunch.setDescription("Celebrate aunt Angelica's birthday with a cozy meal.");
        Event sundayProjectWork = new Event(uni, "Design Project", sunday, LocalTime.of(13, 0), LocalTime.of(17, 0));
        sundayProjectWork.setRhythm(Rhythm.WEEKLY);
        sundayProjectWork.setColor(Color.LIGHTSALMON);

        return testProfile;
    }
}
