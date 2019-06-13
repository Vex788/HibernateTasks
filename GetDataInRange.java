import java.lang.annotation.Annotation;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class GetDataInRange {
    private static int castToInt(Object obj) {
        String str = obj.toString();
        return Integer.parseInt(str);
    }
    public static List<menu_aurora_class.Menu> get(List<menu_aurora_class.Menu> menu, Class<? extends Annotation> a, int x, int xu) throws IllegalAccessException {
        List<menu_aurora_class.Menu> result = new LinkedList<>();
        int weight = 0;

        for (menu_aurora_class.Menu m : menu) {
            for (Field field : m.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(a)) {
                    field.setAccessible(true); // for close modificator
                    switch (field.getName()) {
                        case "cost":
                            if (x <= field.getInt(m) && field.getInt(m) >= xu) { // if cost in range add dish to a menu
                                result.add(m);
                            }
                            break;
                        case "weight":
                            if (weight + field.getInt(m) <= x) { // if weight <= X add dish to a menu
                                weight += field.getInt(m);
                                result.add(m);
                            }
                            break;
                        case "discount":
                            System.out.println(field.getName() + "   " + (int)field.get(m));
                            if (field.getInt(m) <= x) { // if discount <= X add dish to a menu
                                result.add(m);
                            }
                            break;
                        default:
                            continue;
                    }
                }
            }
        }
        return result;
    }
}
