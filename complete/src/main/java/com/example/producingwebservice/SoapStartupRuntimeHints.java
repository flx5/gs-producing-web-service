package com.example.producingwebservice;

import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

/**
 * These hints should be enough for the application to start.
 */
public class SoapStartupRuntimeHints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {

        /*
        java.xml@17.0.11/com.sun.org.apache.xpath.internal.compiler.FunctionTable.getFunction(FunctionTable.java:353)  uses reflection.
        https://github.com/openjdk/jdk/blob/master/src/java.xml/share/classes/com/sun/org/apache/xpath/internal/compiler/FunctionTable.java
         */
        try {
            hints.reflection().registerConstructor(Class.forName("com.sun.org.apache.xpath.internal.functions.FuncNormalizeSpace").getConstructor(), ExecutableMode.INVOKE);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        /*
         java.lang.IllegalArgumentException: xsd 'class path resource [countries.xsd]' does not exist
        at org.springframework.util.Assert.isTrue(Assert.java:111) ~[na:na]
        at org.springframework.xml.xsd.SimpleXsdSchema.afterPropertiesSet(SimpleXsdSchema.java:119) ~[na:na]

         */
        hints.resources().registerResource(new ClassPathResource("countries.xsd"));

        // TODO Test with WSSE as well!
    }
}
