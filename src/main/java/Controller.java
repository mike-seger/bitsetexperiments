import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class Controller {
    public boolean getBoolena() {
        return (System.currentTimeMillis()&1)==1;
    }
}
