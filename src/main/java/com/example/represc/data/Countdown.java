package com.example.represc.data;

import java.time.Duration;
import java.util.Optional;

/**
 * @author Schmetz Arnaud
 * @version 1.0
 *
 * @overview A Countdown is a timer under which a task or the escape game must be completed.
 *
 * @specfield name: String // The name of the Countdown as a String. Must be neither, null, empty, nor blank.
 * @specfield duration: Duration // The duration of the Countdown. Must be not null and positive.
 *
 * // facultative
 * @specfield description: String // The description of the Countdown as a String.
 *
 * @invariant {@code duration} must be positive and not null.
 * @invariant {code name} must neither, null, empty, nor blank.
 * **/
public class Countdown {
    private String name;
    private String description; //facultative
    private Duration duration;

    /**
     * @precondition {@code duration != null && !duration.isNegative() && !name.isBlank() && !name.isEmpty() && name != null}.
     * @effects initialises this with the given name, and duration.
     * **/
    public Countdown(String name, Duration duration){
        assert name != null : "Countdown: trying to create a Countdown with a null String name.";
        assert !name.isEmpty() : "Countdown: trying to create a Countdown with an empty String name.";
        assert !name.isBlank() : "Countdown: trying to create a Countdown with a blank String name.";
        assert duration != null : "Countdown: trying to create a Countdown with a duration value being null.";
        assert !duration.isNegative() : "Countdown: Trying to create a countdown with a negative duration.";

        this.name = name;
        this.duration = duration;
    }
    /**
     * @precondition {@code duration != null && !duration.isNegative() && !name.isBlank() && !name.isEmpty() && name != null}
     * @effects initialises this with the given name, description and duration.
     * **/
    public Countdown(String name, Duration duration, String description){
        this(name, duration);
        assert description != null : "Countdown: Trying to create a Countdown with a description, but description value " +
                "is null.";

        this.description = description;
    }

    /**
     * @return Returns the name of the Countdown as a String.
     * **/
    public String getName() {
        return name;
    }
    /**
     * @precondition {@code name != null && !name.isBlank() && !name.isEmpty()}
     * @postcondition Sets the name of the Countdown to be the String {@code name}
     * **/
    public void setName(String name) {
        assert name != null : "setName: Trying to set the name of a countdown with a null string.";
        assert !name.isEmpty() : "Countdown: Trying to set the name of a Countdown with an empty String name.";
        assert !name.isBlank() : "Countdown: Trying to set the name of a Countdown with a blank String name.";

        this.name = name;
    }

    /**
     * @return Returns the description of the Countdown as an Optional, containing a String, or being empty if
     * {@code description} is null.
     * **/
    public Optional<String> getDescription() {
        if (this.description!=null){
            return Optional.of(this.description);
        }
        return Optional.empty();
    }
    /**
     * @precondition {@code description != null}
     * @postcondition Sets the description of the Countdown to be the String {@code description}.
     * **/
    public void setDescription(String description) {
        assert description != null : "setDescription: trying to set the description of the Countdown, but parameter " +
                "description is null.";
        this.description = description;
    }
    /**
     * @effects Sets the description of the Countdown to be {@code null}
     * **/
    public void deleteDescription(){
        this.description = null;
    }

    /**
     * @return Returns the duration of the Countdown as a Duration.
     */
    public Duration getDuration() {
        return duration;
    }
    /**
     * @precondition {@code  duration != null && !duration.isNegative()}.
     * @effects Sets the duration of the Countdown to be the Duration {@code duration}.
     */
    public void setDuration(Duration duration) {
        assert duration != null : "setDuration: Tries to set the duration of a Countdown with a value which is null.";
        assert !duration.isNegative() : "setDuration: The duration of the countdown must be positive";

        this.duration = duration;
    }

    public String toString(){
        StringBuilder s = new StringBuilder("State : {\n\t");
        s.append("name: ").append(name);
        if (description != null){
            s.append(",\n\tdescription: ").append(description);
        }
        s.append(",\n\tduration: ").append(duration);
        s.append(" }");
        return s.toString();
    }
    public boolean repOK(){
        if(name == null || name.isEmpty() || name.isBlank()) return false;
        if(duration == null) return false;
        if(duration.isNegative()) return false;

        return true;
    }
}