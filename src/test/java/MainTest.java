import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class MainTest {
    @Test
    public void printStream() throws FileNotFoundException {
        System.out.println("aaa");
        PrintStream out = System.out;
        out.println("kk");
        System.setOut(new PrintStream(new File("aa")));
    }
}