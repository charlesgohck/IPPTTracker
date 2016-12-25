/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ippttracker.model;

import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author charlesgoh
 */
public class Result {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final IntegerProperty pushupScore;
    private final IntegerProperty situpScore;
    private final IntegerProperty runningScore;
    private final IntegerProperty totalScore;
    private final IntegerProperty age;
    private final BooleanProperty isSF;
    private final ObjectProperty <LocalDate> ipptDate;
    
    /**
     * Default constructor
     */
    public Result() {
        this(null, null, null);
    }
    
    /*
     * Constructor with some initial data.
     * @param firstName
     * @param lastName
     * @param age
     */
    @SuppressWarnings("Convert2Diamond")
    public Result(String firstName, String lastName, LocalDate ipptDate) {
        if (firstName == null && lastName == null && ipptDate == null) {
            this.firstName = new SimpleStringProperty("");
            this.lastName = new SimpleStringProperty("");
            //LocalDate dateToday = LocalDate.now();
            this.ipptDate = new SimpleObjectProperty <LocalDate> (LocalDate.now());
        } else {
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
            this.ipptDate = new SimpleObjectProperty <LocalDate> (ipptDate);
        }
        this.pushupScore = new SimpleIntegerProperty(0);
        this.situpScore = new SimpleIntegerProperty(0);
        this.runningScore = new SimpleIntegerProperty(0);
        this.totalScore = new SimpleIntegerProperty(0);
        this.age = new SimpleIntegerProperty(18);
        this.isSF = new SimpleBooleanProperty(false);
    }
    
    //The concept is this: getter, setter, property retrieval for all data types
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }
    
    public Integer getTotalScore() {
        return totalScore.get();
    }
    
    public void setTotalScore(Integer totalScore) {
        this.totalScore.set(totalScore);
    }
    
    public IntegerProperty totalScoreProperty() {
        return totalScore;
    }
    
    public Integer refreshTotalScore() {
        return this.getPushup() + this.getSitup() + this.getRunning();
    }
    
    public Integer getPushup() {
        return pushupScore.get();
    }
    
    public void setPushup(Integer pushupScore) {
        this.pushupScore.set(pushupScore);
    }
    
    public IntegerProperty pushupProperty() {
        return pushupScore;
    }
    
    public Integer getSitup() {
        return situpScore.get();
    }
    
    public void setSitup(Integer situpScore) {
        this.situpScore.set(situpScore);
    }
    
    public IntegerProperty situpProperty() {
        return situpScore;
    }
    
    public Integer getRunning() {
        return runningScore.get();
    }
    
    public void setRunning(Integer runningScore) {
        this.runningScore.set(runningScore);
    }
    
    public IntegerProperty runningProperty() {
        return runningScore;
    }
    
    public Integer getAge() {
        return age.get();
    }
    
    public void setAge(Integer age) {
        this.age.set(age);
    }
    
    public IntegerProperty ageProperty() {
        return age;
    }
    
    public Boolean getIsSF() {
        return isSF.get();
    }
    
    public void setIsSF(Boolean isSF) {
        this.isSF.set(isSF);
    }
    
    public BooleanProperty isSFProperty() {
        return isSF;
    }
    
    public LocalDate getIPPTDate() {
        return ipptDate.get();
    }
    
    public void setIPPTDate(LocalDate ipptDate) {
        this.ipptDate.set(ipptDate);
    }
    
    public ObjectProperty <LocalDate> ipptDateProperty() {
        return ipptDate;
    }
    
}
