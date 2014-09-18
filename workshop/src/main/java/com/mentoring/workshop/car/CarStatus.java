package com.mentoring.workshop.car;

public enum CarStatus {
    NEVER_RECEIVED("Client"),
    WAITING_FOR_REPAIR("Parking"),
    REPAIRING("Garage"),
    REPAIR_COMPLETE("Parking"),
    GAVE_AWAY("Parking"),
    PARKED("Parking");

    private String location;

    private CarStatus(final String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
