import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Main {
    public static String fileCSV = "data.csv";
    public static String fileJSON = "data.json";
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        List<Employee> list = parseCSV(columnMapping, fileCSV);
        String json = listToJson(list);
        if (writeString(json, fileJSON)) {
            System.out.println("Файл " + fileJSON + " успешно записан.");
        } else {
            System.out.println("Ошибка записи файла " + fileJSON + " !!!");
        }
    }
    public static List<Employee> parseCSV(String[] map, String file) {
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee. class);
            strategy.setColumnMapping(map);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            return csv.parse();
        } catch (IOException err) {
            err.printStackTrace();
        }
        return null;
    }
    public static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {}.getType(); // ???
        return gson.toJson(list, listType);
    }
    public static boolean writeString(String json, String fileName) {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(json);
            file.flush();
            return true;
        } catch (IOException err) {
            err.printStackTrace();
        }
        return false;
    }
}
