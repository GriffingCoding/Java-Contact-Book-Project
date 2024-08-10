package com.example.project_contact_book;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
    private ContactBook contactBook = new ContactBook();
    private ListView<String> contactListView = new ListView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Contact Book Application");

        // UI Elements
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label phoneLabel = new Label("Phone Number:");
        TextField phoneField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label notesLabel = new Label("Notes:");
        TextField notesField = new TextField();

        Button addButton = new Button("Add Contact");
        Button viewButton = new Button("View All Contacts");
        Button searchButton = new Button("Search Contact");
        Button removeButton = new Button("Remove Contact");

        // Layout
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);

        gridPane.add(phoneLabel, 0, 1);
        gridPane.add(phoneField, 1, 1);

        gridPane.add(emailLabel, 0, 2);
        gridPane.add(emailField, 1, 2);

        gridPane.add(notesLabel, 0, 3);
        gridPane.add(notesField, 1, 3);

        HBox buttonBox = new HBox(10, addButton, viewButton, searchButton, removeButton);

        VBox vbox = new VBox(10, gridPane, buttonBox, contactListView);

        Scene scene = new Scene(vbox, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Button Actions
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String phoneNumber = phoneField.getText();
            String email = emailField.getText();
            String notes = notesField.getText();
            Contact contact = new Contact(name, phoneNumber, email, notes);
            contactBook.addContact(contact);
            nameField.clear();
            phoneField.clear();
            emailField.clear();
            notesField.clear();
            updateContactListView();
        });

        viewButton.setOnAction(e -> updateContactListView());

        searchButton.setOnAction(e -> {
            String name = nameField.getText();
            Contact contact = contactBook.searchContactByName(name);
            if (contact != null) {
                showAlert(Alert.AlertType.INFORMATION, "Contact Found", contact.toString());
            } else {
                showAlert(Alert.AlertType.ERROR, "Contact Not Found", "No contact found with the name: " + name);
            }
        });

        removeButton.setOnAction(e -> {
            String name = nameField.getText();
            boolean removed = contactBook.removeContactByName(name);
            if (removed) {
                showAlert(Alert.AlertType.INFORMATION, "Contact Removed", "Contact with name: " + name + " removed.");
                updateContactListView();
            } else {
                showAlert(Alert.AlertType.ERROR, "Contact Not Found", "No contact found with the name: " + name);
            }
        });
    }

    private void updateContactListView() {
        contactListView.getItems().clear();
        for (Contact contact : contactBook.getContacts()) {
            contactListView.getItems().add(contact.getName());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
