module de.hsh.larry.calendar {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.xml.dom;
    requires java.desktop;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires google.api.services.calendar.v3.rev411;

    opens de.hsh.larry.calendar to javafx.fxml;
    exports de.hsh.larry.calendar;

        opens de.hsh.larry.calendar.logic to javafx.fxml;
        exports de.hsh.larry.calendar.logic;

        exports de.hsh.larry.calendar.models;
        opens de.hsh.larry.calendar.models to javafx.fxml;

        exports de.hsh.larry.calendar.views.models;
        opens de.hsh.larry.calendar.views.models to javafx.fxml;

            exports de.hsh.larry.calendar.views.application;
            opens de.hsh.larry.calendar.views.application to javafx.fxml;
            exports de.hsh.larry.calendar.views.calendar;
            opens de.hsh.larry.calendar.views.calendar to javafx.fxml;
            exports de.hsh.larry.calendar.views.detailedViews;
            opens de.hsh.larry.calendar.views.detailedViews to javafx.fxml;
            exports de.hsh.larry.calendar.views.dialogues;
            opens de.hsh.larry.calendar.views.dialogues to javafx.fxml;
            exports de.hsh.larry.calendar.views.editorViews;
            opens de.hsh.larry.calendar.views.editorViews to javafx.fxml;
            exports de.hsh.larry.calendar.views.habits;
            opens de.hsh.larry.calendar.views.habits to javafx.fxml;
            exports de.hsh.larry.calendar.views.screens;
            opens de.hsh.larry.calendar.views.screens to javafx.fxml;
            exports de.hsh.larry.calendar.views.todos;
            opens de.hsh.larry.calendar.views.todos to javafx.fxml;
}