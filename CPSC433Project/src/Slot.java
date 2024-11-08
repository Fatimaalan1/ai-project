public class Slot {
    Type type;
    String date;
    String time;
    int maxAllocations;
    int minAllocations;

    public Slot(Type inType, String inDate, String inTime, int inMaxAlloc, int inMinAlloc)
    {
        type = inType;
        date = inDate;
        time = inTime;
        maxAllocations = inMaxAlloc;
        minAllocations = inMinAlloc;
    }
}
