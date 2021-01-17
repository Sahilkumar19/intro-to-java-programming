package ch_17;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.naming.SizeLimitExceededException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ***17.9 (Address book) Write a program that stores, retrieves, adds, and updates addresses
 * as shown in Figure 17.20. Use a fixed-length string for storing each attribute in the
 * address. Use random access file for reading and writing an address. Assume that
 * the size of name, street, city, state, and zip is 32, 32, 20, 2, 5 bytes, respectively.
 * FIGURE 17.20 The application can store, retrieve, and update addresses from a file.
 *
 * @author Harry Dulaney
 */
public class E1709 extends Application {
    final static File storage = new File("src" + File.separator + E1709.class.getPackage().getName() + File.separator +
            "address_store.dat");
    static int numAddresses;
    static long filePointer;
    static final int NAME_SIZE = 32;
    static final int STREET_SIZE = 32;
    static final int CITY_SIZE = 20;
    static final int STATE_SIZE = 2;
    static final int ZIP_SIZE = 5;
    static final int TOTAL = 91;

    TextField nameField;
    TextField streetField;
    TextField cityField;
    TextField stateField;
    TextField zipField;

    @Override
    public void start(Stage primaryStage) {
        retrieve(storage);
        VBox rootBox = initUserInterface();
        Scene scene = new Scene(rootBox, 500, 170);
        primaryStage.setTitle("Exercise-17.09");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox initUserInterface() {
        VBox showBox = new VBox(5.0);

        Label name = new Label("Name");
        nameField = new TextField();

        nameField.setMinWidth(400);
        HBox topBox = new HBox(name, nameField);
        topBox.setSpacing(10);
        topBox.setPadding(new Insets(5, 5, 5, 5));

        showBox.getChildren().add(topBox);

        Label street = new Label("Street");
        streetField = new TextField();

        streetField.setMinWidth(400);
        HBox midBox = new HBox(street, streetField);
        midBox.setSpacing(10);
        midBox.setPadding(new Insets(5, 5, 5, 5));

        showBox.getChildren().add(midBox);

        Label city = new Label("City");
        cityField = new TextField();

        HBox h1 = new HBox(city, cityField);
        h1.setPadding(new Insets(2, 2, 2, 2));
        h1.setSpacing(10);

        Label state = new Label("State");
        stateField = new TextField();

        stateField.setMaxWidth(45);

        HBox h2 = new HBox(state, stateField);
        h2.setPadding(new Insets(2, 2, 2, 2));
        h2.setSpacing(10);

        Label zip = new Label("Zip");
        zipField = new TextField();

        zipField.setMaxWidth(65);
        HBox h3 = new HBox(zip, zipField);
        h3.setPadding(new Insets(2, 2, 2, 2));
        h3.setSpacing(10);

        HBox bottomBox = new HBox(h1, h2, h3);
        bottomBox.setAlignment(Pos.CENTER);

        showBox.getChildren().add(bottomBox);

        Button addButton = new Button("Add");
//        addButton.setOnAction(event -> add(new Address(streetField.getText(),
//                nameField.getText(), cityField.getText(), stateField.getText(),
//                zipField.getText())));

        Button firstButton = new Button("First");
        firstButton.setOnAction(event -> first());

        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> next());

        Button previousButton = new Button("Previous");
        previousButton.setOnAction(event -> previous());

        Button lastButton = new Button("Last");
        lastButton.setOnAction(event -> last());

        Button updateButton = new Button("Update");
//        updateButton.setOnAction(event -> update(pointer, streetField.getText(),
//                nameField.getText(), cityField.getText(), stateField.getText(),
//                zipField.getText())));

        HBox buttonBox = new HBox(addButton, firstButton, nextButton, previousButton, lastButton, updateButton);
        buttonBox.setSpacing(10.0);
        buttonBox.setAlignment(Pos.CENTER);

        showBox.getChildren().add(buttonBox);

        return showBox;
    }

    static void displayError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(error);
        alert.show();
    }


    private void add() {
        try {
            RandomAccessFile raf = new RandomAccessFile(storage, "rw");
            raf.seek(raf.length() - 1);
            String currentAddress = getAddressString();
            raf.write(currentAddress.getBytes());

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void first() {


    }

    private void next() {

    }

    private void previous() {

    }


    private void last() {
        try {
            RandomAccessFile raf = new RandomAccessFile(storage, "rw");
            if (raf.length() < 2) {
                return;
            }
            raf.seek(raf.length() - 92);
            String s = raf.readLine();
            setCurrentAddress(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setCurrentAddress(String s) {
        nameField.setText(s.substring(0, 32));
        streetField.setText(s.substring(32, 64));
        cityField.setText(s.substring(64, 84));
        stateField.setText(s.substring(84, 86));
        zipField.setText(s.substring(86));

    }

    private void update(int pointer, String[] addresses) {
        displayInfo("Updated the address!");

    }

    private void displayInfo(String updated) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(updated);
        alert.show();
    }

    private void store(File f, String s) {
        if (!f.exists()) {
            try {
                boolean b = f.createNewFile();

            } catch (Exception e) {
                e.printStackTrace();
                displayError(e.getMessage());
            }
        }

        try {
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            raf.writeBytes(s);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void retrieve(File file) {
        String addressString = "";
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "r");

        } catch (IOException ioException) {
            displayError(ioException.getMessage());
        }
    }

    String getAddressString() {
        String address = "";
        String name = nameField.getText();

        for (int i = name.length(); i < NAME_SIZE; i++) {
            name += " ";
        }
        address += name;

        String street = streetField.getText();
        for (int i = street.length(); i < STREET_SIZE; i++) {
            street += " ";
        }
        address += street;

        String city = cityField.getText();
        for (int i = city.length(); i < CITY_SIZE; i++) {
            city += " ";
        }
        address += city;

        String state = stateField.getText();
        for (int i = state.length(); i < STATE_SIZE; i++) {
            state += " ";
        }
        address += state;

        String zip = zipField.getText();

        for (int i = zip.length(); i < ZIP_SIZE; i++) {
            zip += " ";
        }
        address += zip;

        return address;

    }

}
