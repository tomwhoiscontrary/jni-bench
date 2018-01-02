package li.earth.urchin.twic;

public class Squarer {

    static {
        System.loadLibrary("jni-bench");
    }

    public static int squareInJava(int x) {
        return x * x;
    }

    public static native int squareNatively(int x);

}
