package dynamicAnalysis.agent;

import com.google.common.collect.Maps;
import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Transformer implements ClassFileTransformer {

    private static Map<String, Integer> classesCount = Maps.newHashMap();
    private static Map<String, Integer> methodsCount = Maps.newHashMap();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
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
        }));
    }

    protected String packagePrefix;

    public Transformer(String args) {
        this.packagePrefix = args;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cl = null;
        try {
            cl = pool.makeClass(new java.io.ByteArrayInputStream(classfileBuffer));

            CtBehavior[] methods = cl.getDeclaredBehaviors();
            for (int i = 0; i < methods.length; i++) {
                if (!cl.getPackageName().startsWith(packagePrefix))
                    continue;
                if (!cl.isPrimitive() && !cl.isInterface() && cl.getDeclaringClass() == null && cl.getEnclosingBehavior() == null) {
                    if (!methods[i].isEmpty()) {
                        changeMethod(methods[i], cl.getName());
                    }
                }
            }
            classfileBuffer = cl.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return classfileBuffer;
    }

    private void changeMethod(CtBehavior method, String className) throws NotFoundException, CannotCompileException {
        if (!Modifier.isNative(method.getModifiers()) && !Modifier.isAbstract(method.getModifiers())) {
            String insertString = "java.util.logging.Logger.getLogger(\"" + className + "\")" +
                    ".info(Integer.toString(Thread.currentThread().getStackTrace().length));";
            method.insertBefore(insertString);
//            System.out.println(className + ":" + method.getName());

            if (classesCount.containsKey(className)) {
                classesCount.put(className, classesCount.get(className) + 1);
            } else {
                classesCount.put(className, 1);
            }
            String key = className + "." + method.getName();
            if (methodsCount.containsKey(key)) {
                methodsCount.put(key, methodsCount.get(key) + 1);
            } else {
                methodsCount.put(key, 1);
            }
        }
    }

}
