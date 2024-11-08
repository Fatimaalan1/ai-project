import java.util.ArrayList;

public class Constr {

    public static boolean gameMaxCheck(ArrayList<Schedule> s, ArrayList<Slot> slots) {

        for (Schedule sched : s) {
            if (sched.slot != null) {
                if (gameTotal(s, sched.slot) > sched.slot.maxAllocations) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int gameTotal(ArrayList<Schedule> s, Slot slot) {
        int totalGames = 0;
        for (Schedule sched : s) {
            // look for the current slot
            if (sched.slot != null && slot.equals(sched.slot) && sched.allocation.type == Type.GAME) {
                totalGames++;
            }
        }
        return totalGames;
    }

    public static boolean practiceMaxCheck(ArrayList<Schedule> s, ArrayList<Slot> slots) {
        for (Schedule sched : s) {
            if (sched.slot != null) {
                if (practiceTotal(s, sched.slot) > sched.slot.maxAllocations) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int practiceTotal(ArrayList<Schedule> s, Slot slot) {
        int totalPractices = 0;
        for (Schedule sched : s) {
            // look for the current slot
            if (sched.slot != null && slot.equals(sched.slot) && sched.allocation.type == Type.GAME) {
                totalPractices++;
            }
        }
        return totalPractices;
    }

    public static boolean compatableCheck(ArrayList<Schedule> s, ArrayList<NotCompatible> nc) {

        for (NotCompatible notCompatible : nc) {
            // get the current slot allocation of the two non compatible allocations
            Slot allocation1Slot = currSlot(s, notCompatible.notCompatibleOne);
            Slot allocation2Slot = currSlot(s, notCompatible.notCompatibleTwo);

            // return false if they share the same slot
            if (allocation1Slot != null && allocation2Slot != null && allocation1Slot.equals(allocation2Slot)) {
                return false;
            }
        }
        return true;
    }

    public static Slot currSlot(ArrayList<Schedule> s, Allocation allocation) {
        // look for a specific allocation in the schedule and return the slot
        for (Schedule schedule : s) {
            if (schedule.allocation.equals(allocation)) {
                return schedule.slot;
            }
        }
        return null;
    }

    public static boolean partAssignCheck(ArrayList<Schedule> s, ArrayList<PartialAssignment> pa) {
        for (Schedule schedule : s) {
            if (schedule.slot != null) {
                for (PartialAssignment partialAssignment : pa) {
                    // check every slot in schedule and return false if a certain game/practice is
                    // allocated another slot than what is in the part assign table
                    if (schedule.allocation.equals(partialAssignment.allocation)
                            && partialAssignment.slot.equals(schedule.slot) == false) {
                        return false;
                    }
                }
            }

        }
        return true;
    }

    public static boolean unwantedCheck(ArrayList<Schedule> s, ArrayList<Unwanted> u) {
        for (Schedule schedule : s) {
            if (schedule.slot != null) {
                for (Unwanted unwanted : u) {
                    // check every slot in schedule and return false if a certain game/practice is
                    // allocated a slot in the unwanted list
                    if (schedule.allocation.equals(unwanted.allocation)
                            && schedule.slot.equals(unwanted.unwantedSlot)) {
                        return false;
                    }
                }
            }

        }
        return true;
    }

    public static boolean div9Check(ArrayList<Schedule> s) {
        for (Schedule schedule : s) {
            // TO-DO: actually check the evening slots no clue how tbh
            if (schedule.slot != null && schedule.allocation.identifier.contains("DIV 09") && schedule.slot.intTime>=1800) {
                return false;
            }
        }
        return true;
    }

    public static boolean u15_u19Check(ArrayList<Schedule> s) {

        for (Schedule schedule : s) {
            // looks for an u15-u19 game that has been assigned a slot
            if (schedule.slot != null && (schedule.allocation.identifier.contains("U15")
                    || schedule.allocation.identifier.contains("U16") || schedule.allocation.identifier.contains("U17")
                    || schedule.allocation.identifier.contains("U18")
                    || schedule.allocation.identifier.contains("U19")) && schedule.allocation.type == Type.GAME) {
                // Oh no

                for (Schedule schedule2 : s) {
                    // looks for another u15-u19 game and checks if the original game is assigned to
                    // the same slot
                    if (schedule2.slot != null && (schedule2.allocation.identifier.contains("U15")
                            || schedule2.allocation.identifier.contains("U16")
                            || schedule2.allocation.identifier.contains("U17")
                            || schedule2.allocation.identifier.contains("U18")
                            || schedule2.allocation.identifier.contains("U19"))
                            && schedule2.allocation.type == Type.GAME && schedule.slot.equals(schedule2.slot)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean specialBookingCheck(ArrayList<Schedule> s) {
        for (Schedule schedule : s) {
            if (schedule.slot != null) {
                // check if a special game is given the right slot
                if ((schedule.allocation.identifier.contains("U13T1S")
                        || schedule.allocation.identifier.contains("U12T1S"))
                        && (!(schedule.slot.date.contains("TU") || schedule.slot.date.contains("TR"))
                                || !(schedule.slot.time.contains("18:00")))) {
                    return false;
                }
                // if a non special game is given the tuesday or thursday slot
                if ((schedule.allocation.identifier.contains("U13T1")
                        || schedule.allocation.identifier.contains("U12T1"))
                        && (schedule.slot.date.contains("TU") || schedule.slot.date.contains("TR"))
                        && (schedule.slot.time.contains("18:00"))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean constr(ArrayList<Schedule> s, ArrayList<Slot> slots, ArrayList<NotCompatible> nc,
            ArrayList<PartialAssignment> pa, ArrayList<Unwanted> u) {

        return gameMaxCheck(s, slots) && practiceMaxCheck(s, slots) && compatableCheck(s, nc) && partAssignCheck(s, pa)
                && unwantedCheck(s, u) && div9Check(s) && u15_u19Check(s) && specialBookingCheck(s);
    }

    

}