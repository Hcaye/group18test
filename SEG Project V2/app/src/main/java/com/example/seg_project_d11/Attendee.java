package com.example.seg_project_d11;

import java.util.ArrayList;

public class Attendee extends User{
    //constructor without list of events
    public Attendee(String firstName, String lastName, String email, String password, String phoneNumber, String address, String status, String userRole) {
        super(firstName, lastName, email, password, phoneNumber, address, status, userRole);
    }
    //constructor with list of events
    public Attendee(String firstName, String lastName, String email, String password, String phoneNumber, String address, String status, String userRole, ArrayList<Event> events) {
        super(firstName, lastName, email, password, phoneNumber, address, status, userRole, events);
    }
}
