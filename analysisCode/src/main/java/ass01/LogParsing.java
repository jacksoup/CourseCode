package ass01;

import com.google.common.collect.Maps;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogParsing {

    public static void main(String[] args) throws Exception {
        Map<String, Integer> classesCount = Maps.newHashMap();
        Map<String, Integer> methodsCount = Maps.newHashMap();

        InputStreamReader read = new InputStreamReader(new FileInputStream(new File("logged.log")));
        BufferedReader bufferedReader = new BufferedReader(read);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            String[] arr = line.split(" ");
            String clazz = arr[0];
            String method = arr[1];
            String depth = arr[2];

            if (classesCount.containsKey(clazz)) {
                classesCount.put(clazz, classesCount.get(clazz) + 1);
            } else {
                classesCount.put(clazz, 1);
            }
            String key = clazz + "." + method;
            if (methodsCount.containsKey(key)) {
                methodsCount.put(key, methodsCount.get(key) + 1);
            } else {
                methodsCount.put(key, 1);
            }
        }
        read.close();

        List<Map.Entry<String, Integer>> list1 = new ArrayList<>(classesCount.entrySet());
        list1.sort((o1, o2) -> o2.getValue() - o1.getValue());
        System.out.println("==== Most frequented class: " + list1.get(0).getKey() + ", count:" + list1.get(0).getValue());

        List<Map.Entry<String, Integer>> list2 = new ArrayList<>(methodsCount.entrySet());
        list2.sort((o1, o2) -> o2.getValue() - o1.getValue());
        System.out.println("==== Most frequented method: " + list2.get(0).getKey() + ", count:" + list2.get(0).getValue());

        System.out.println("==== Top 15 frequented classes:");
        for (int i = 0; i < 15; i++) {
            System.out.println(list1.get(i).getKey() + "," + list1.get(i).getValue());
        }
        System.out.println("==== Top 15 frequented methods:");
        for (int i = 0; i < 15; i++) {
            System.out.println(list2.get(i).getKey() + "," + list2.get(i).getValue());
        }

    }
}
