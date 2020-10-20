import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;

public class XClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> c = null;
        try {
            File f = new File("./Hello.xlass");
            byte[] ba = Files.readAllBytes(f.toPath());
            for (int i = 0; i < ba.length; i++) {
                ba[i] = (byte) (255 - ba[i]);
            }
            c = defineClass(name, ba, 0, ba.length);
            return c;
        }catch (Exception e) {
            e.printStackTrace();
            throw new ClassNotFoundException(name);
        }
    }

    public static void main(String[] args) {
        XClassLoader cl = new XClassLoader();
        try {

            Class helloc = cl.loadClass("Hello");
            Object helloO = helloc.newInstance();
            Method m = helloc.getMethod("hello");
            m.invoke(helloO);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
