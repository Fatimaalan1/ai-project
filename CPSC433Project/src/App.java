public class App {
    public static void main(String[] args) throws Exception {
        for(String inputLine : args)
        {
            System.out.println(inputLine);
        }
        //Parser testing (you can delete this)
        Parser parser = new Parser(args);
        System.out.println("Not Compatible");

        for(NotCompatible temp : parser.notCompatibleList)
        {
            System.out.println(temp.notCompatibleOne.identifier + " :: " + temp.notCompatibleTwo.identifier);
        }
        System.out.println("Unwanted");
        for(Unwanted temp : parser.unwantedList)
        {
            System.out.println(temp.allocation.identifier + " :: " + temp.unwantedSlot.date + temp.unwantedSlot.time + " " + temp.unwantedSlot.type);
        }
        System.out.println("Preferences");
        for(Preferences temp : parser.preferencesList)
        {
            System.out.printf("%s :: %s Value: %d\n", temp.prefSlot.date + temp.prefSlot.time + " " + temp.prefSlot.type, temp.allocation.identifier, temp.prefValue);
        }
        System.out.println("Pairs");
        for(Pair temp : parser.pairList)
        {
            System.out.printf("%s :: %s\n", temp.pairOne.identifier, temp.pairTwo.identifier);
        }
        System.out.println("Partial Assignment");
        for(PartialAssignment temp : parser.partialAssignmentList)
        {
            System.out.printf("%s :: %s\n", temp.allocation.identifier, temp.slot.date + temp.slot.time);
        }
    }
}