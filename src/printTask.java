
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class printTask implements Callable  {

    Future Fstring;
    String prefix;

    printTask(String string, Future err) {
        Fstring = err;
        prefix = string;
    }

    public Object call() throws Exception {
        System.out.println(prefix + Fstring.get());
        return null;
    }

}
