package com.example.frontpage;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TicketCounter {

    private final int[] count = new int[44];   // 11 days × 4 slots
    private final String[] slots = new String[4]; // s1,s2,s3,s4 from DB

    public void countTickets() {

        DatabaseReference slotRef = FirebaseDatabase.getInstance().getReference("slot");

        // 🔹 STEP 1: Load slots first
        slotRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot slotSnapshot) {

                for (int i = 1; i <= 4; i++) {
                    slots[i - 1] = slotSnapshot.child("s" + i).getValue(String.class);
                }

                loadDatesAndTickets();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Slot load error: " + error.getMessage());
            }
        });
    }

    private void loadDatesAndTickets() {

        DatabaseReference ticketsRef = FirebaseDatabase.getInstance().getReference("tickets");
        DatabaseReference countRef = FirebaseDatabase.getInstance().getReference("slot_count");
        DatabaseReference dateRef = FirebaseDatabase.getInstance().getReference("date");

        // 🔹 STEP 2: Load dates
        dateRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dateSnapshot) {

                String[] storedDates = new String[11];
                for (int i = 1; i <= 11; i++) {
                    storedDates[i - 1] = dateSnapshot.child("d" + i).getValue(String.class);
                }

                // 🔹 STEP 3: Load tickets
                ticketsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot ticketSnapshot) {

                        // reset count
                        for (int i = 0; i < 44; i++) count[i] = 0;

                        for (DataSnapshot ticket : ticketSnapshot.getChildren()) {

                            String ticketDate = ticket.child("date").getValue(String.class);
                            String ticketTime = ticket.child("time").getValue(String.class);
                            Object peopleObj = ticket.child("people").getValue();

                            if (ticketDate == null || ticketTime == null || peopleObj == null)
                                continue;

                            int peopleCount;
                            try {
                                peopleCount = Integer.parseInt(peopleObj.toString());
                            } catch (Exception e) {
                                continue;
                            }

                            // 🔹 find day index
                            int dayIndex = -1;
                            for (int i = 0; i < storedDates.length; i++) {
                                if (storedDates[i] != null && storedDates[i].equals(ticketDate)) {
                                    dayIndex = i;
                                    break;
                                }
                            }
                            if (dayIndex == -1) continue;

                            // 🔹 find slot index from DB slots
                            int slotIndex = -1;
                            for (int i = 0; i < slots.length; i++) {
                                if (slots[i] != null &&
                                        slots[i].equalsIgnoreCase(ticketTime.trim())) {
                                    slotIndex = i;
                                    break;
                                }
                            }
                            if (slotIndex == -1) continue;

                            int index = dayIndex * 4 + slotIndex;
                            count[index] += peopleCount;
                        }

                        // 🔹 STEP 4: Save to slot_count
                        for (int d = 0; d < storedDates.length; d++) {
                            if (storedDates[d] == null) continue;

                            String dateKey = storedDates[d].replace("/", "-");
                            DatabaseReference dateNode = countRef.child(dateKey);

                            int dateTotal = 0;

                            for (int s = 0; s < 4; s++) {
                                int index = d * 4 + s;
                                int slotValue = count[index];

                                dateNode.child("s" + (s + 1)).setValue(slotValue);
                                dateTotal += slotValue; // add to total
                            }

                            // save total booked tickets for that date
                            dateNode.child("total").setValue(dateTotal);
                        }

                        System.out.println("Ticket counts updated");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Ticket load error: " + error.getMessage());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Date load error: " + error.getMessage());
            }
        });
    }
}
