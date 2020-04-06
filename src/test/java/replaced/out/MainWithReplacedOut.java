package replaced.out;

import main.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MainWithReplacedOut extends Main {
    // hides System variable of type System
    public static MockSystem System = new MockSystem(java.lang.System.out);

    private ByteArrayOutputStream byteArrayOutputStream;

    public MainWithReplacedOut() {
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        System.out = new PrintStream(byteArrayOutputStream);
    }

    /****************************************************************************************************************************************************
     * If created using this constructor getByteArrayOutputStream() will throw exception                                                                *
     * it is not being initiated because it is impossible to create PrintStream without previously creating ByteArrayOutputStream object                *
     * this indicated that ByteArrayOutputStream object is already entangled with given PrintStream                                                     *
     ****************************************************************************************************************************************************/
    public MainWithReplacedOut(PrintStream out) {
        this.byteArrayOutputStream = null;
        System = new MockSystem(out);
    }

    public MainWithReplacedOut(PrintStream out, ByteArrayOutputStream byteArrayOutputStream) {
        this.byteArrayOutputStream = byteArrayOutputStream;
        System = new MockSystem(out);
    }

    public ByteArrayOutputStream getByteArrayOutputStream() {
        if(this.byteArrayOutputStream == null) throw new NullPointerException("this.byteArrayOutputStream == null");
        return byteArrayOutputStream;
    }
}
