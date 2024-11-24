package com.capgemini.wsb.fitnesstracker.training.internal;


/**
 * Enum representing the different types of activiies: RUNNING, CYCLING, WALKING, SWIMMING,
 * TENNIS. These activities can be performed by user during training session.
 *
 */
public enum ActivityType {


    /**
     * Represents a running activity
     */
    RUNNING("Running"),

    /**
     * Represents a cycling activity
     */
    CYCLING("Cycling"),

    /**
     * Represents a walking activity
     */
    WALKING("Walking"),

    /**
     * Represents a swimming activity
     */
    SWIMMING("Swimming"),

    /**
     * Represents a tennis activity
     */
    TENNIS("Tenis");

    private final String displayName;


    /**
     * Constructs a new ActivityType with a specified displayName
     *
     * @param displayName readable name for the activity type
     */
    ActivityType(String displayName) {
        this.displayName = displayName;
    }


    /**
     * Returns the displayName of the activity type.
     *
     * @return displayName of the activity type
     */
    public String getDisplayName() {
        return displayName;
    }

}
