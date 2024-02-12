import java.lang.reflect.Array;
import java.util.List;

public class App {
    private String e;

    public static void main(String[] argv) {
        String[] strings = App.convert(String.class, List.of("Val1", "Val2", "Val3"));
        System.out.println(java.util.Arrays.toString(strings));
    }

    public static <T> T[] convert(Class<T> clazz, List<T> values) {
        T[] elements = (T[]) Array.newInstance(clazz, values.size());
        for(int i = 0; i < values.size(); i++){
            elements[i] = values.get(i);
        }
        return elements;
    }
}