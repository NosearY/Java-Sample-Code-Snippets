import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Main {

    public static void main(String[] args) {

        CSVData csvD = new CSVData();
        csvToJson(new CSVData[]{csvD});

    }

    public static <T extends CSVInterface<T, E>, E> void csvToJson(T[] data) {
        Class<E> formatterClazz = data[0].getFormatterClazz();
        Class<T> dataClazz = data[0].getDataClazz();
        System.out.println(formatterClazz.toString());
        System.out.println(dataClazz);
    }

}

class CSVData extends CSVInterface<CSVData, CSVFormatter> {
}

class CSVFormatter {
}

abstract class CSVInterface<DataType, FormatterType> {
    public Class<DataType> getDataClazz() {
        Type t = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            return (Class<DataType>) Class.forName(t.getTypeName());
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public Class<FormatterType> getFormatterClazz() {
        Type t = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        try {
            return (Class<FormatterType>) Class.forName(t.getTypeName());
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
