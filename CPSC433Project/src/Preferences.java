public class Preferences {
    Allocation allocation;
    Slot prefSlot;
    int prefValue;

    public Preferences(Allocation inAllocation, Slot inSlot, int inPrefVal)
    {
        allocation = inAllocation;
        prefSlot = inSlot;
        prefValue = inPrefVal;
    }
}
