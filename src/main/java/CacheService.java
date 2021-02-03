import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    private Cache cache=new Cache(LocalDate.now(), LocalDate.now().minusDays(1000));

    public void setDay(LocalDate day) {
        cache.setDay(day);
    }

    public void getDay(LocalDate day) {
        cache.setDay(day);
    }
}
