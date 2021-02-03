import java.time.Duration;
import java.time.LocalDate;
import java.util.BitSet;

public class Cache {
    public LocalDate start, end;
    public BitSet bitSet;
    public Cache(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
        bitSet = new BitSet(offset(start, end));
    }

    public void setDay(LocalDate day) {
        bitSet.set(offset(start, day));
    }

    public void getDay(LocalDate day) {
        bitSet.get(offset(start, day));
    }

    private int offset(LocalDate start, LocalDate end) {
        return (int)Duration.between(start.atStartOfDay(), end.atStartOfDay()).toDays();
    }
}
