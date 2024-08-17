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
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        /*
        Caused by: java.lang.IllegalStateException: Could not load 'class path resource [org/springframework/ws/transport/http/MessageDispatcherServlet.properties]': class path resource [org/springframework/ws/transport/http/MessageDispatcherServlet.properties] cannot be opened because it does not exist
        at org.springframework.ws.support.DefaultStrategiesHelper.<init>(DefaultStrategiesHelper.java:78) ~[na:na]
        at org.springframework.ws.support.DefaultStrategiesHelper.<init>(DefaultStrategiesHelper.java:88) ~[na:na]
        at org.springframework.ws.transport.http.MessageDispatcherServlet.<init>(MessageDispatcherServlet.java:175) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]

         */
        hints.resources().registerResource(new ClassPathResource("org/springframework/ws/transport/http/MessageDispatcherServlet.properties"));


        /*
         java.lang.IllegalArgumentException: xsd 'class path resource [countries.xsd]' does not exist
        at org.springframework.util.Assert.isTrue(Assert.java:111) ~[na:na]
        at org.springframework.xml.xsd.SimpleXsdSchema.afterPropertiesSet(SimpleXsdSchema.java:119) ~[na:na]

         */
        hints.resources().registerResource(new ClassPathResource("countries.xsd"));


        /*
        Caused by: javax.wsdl.WSDLException: WSDLException: faultCode=CONFIGURATION_ERROR: Problem instantiating factory implementation.: java.lang.ClassNotFoundException: com.ibm.wsdl.factory.WSDLFactoryImpl
        at javax.wsdl.factory.WSDLFactory.newInstance(WSDLFactory.java:95) ~[na:na]
        at javax.wsdl.factory.WSDLFactory.newInstance(WSDLFactory.java:62) ~[na:na]
        at org.springframework.ws.wsdl.wsdl11.ProviderBasedWsdl4jDefinition.afterPropertiesSet(ProviderBasedWsdl4jDefinition.java:232) ~[na:na]
        at org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition.afterPropertiesSet(DefaultWsdl11Definition.java:185) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1822) ~[com.example.producingwebservice.ProducingWebServiceApplication:6.1.1]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1771) ~[com.example.producingwebservice.ProducingWebServiceApplication:6.1.1]
        ... 16 common frames omitted
Caused by: java.lang.ClassNotFoundException: com.ibm.wsdl.factory.WSDLFactoryImpl

         */
        try {
            hints.reflection().registerConstructor(com.ibm.wsdl.factory.WSDLFactoryImpl.class.getDeclaredConstructor(), ExecutableMode.INVOKE);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        /*
        Caused by: javax.wsdl.WSDLException: WSDLException: faultCode=CONFIGURATION_ERROR: Problem instantiating Java extensionType 'com.ibm.wsdl.extensions.schema.SchemaImpl'.: java.lang.InstantiationException: com.ibm.wsdl.extensions.schema.SchemaImpl
        at javax.wsdl.extensions.ExtensionRegistry.createExtension(ExtensionRegistry.java:383) ~[com.example.producingwebservice.ProducingWebServiceApplication:1.6.3]
        at org.springframework.ws.wsdl.wsdl11.provider.InliningXsdSchemaTypesProvider.addTypes(InliningXsdSchemaTypesProvider.java:100) ~[na:na]
        at org.springframework.ws.wsdl.wsdl11.ProviderBasedWsdl4jDefinition.afterPropertiesSet(ProviderBasedWsdl4jDefinition.java:240) ~[na:na]
        at org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition.afterPropertiesSet(DefaultWsdl11Definition.java:185) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1822) ~[com.example.producingwebservice.ProducingWebServiceApplication:6.1.1]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1771) ~[com.example.producingwebservice.ProducingWebServiceApplication:6.1.1]
        ... 16 common frames omitted
Caused by: java.lang.InstantiationException: com.ibm.wsdl.extensions.schema.SchemaImpl
        at java.base@17.0.11/java.lang.Class.newInstance(DynamicHub.java:639) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]
        at javax.wsdl.extensions.ExtensionRegistry.createExtension(ExtensionRegistry.java:367) ~[com.example.producingwebservice.ProducingWebServiceApplication:1.6.3]
        ... 21 common frames omitted
Caused by: java.lang.NoSuchMethodException: com.ibm.wsdl.extensions.schema.SchemaImpl.<init>()
        at java.base@17.0.11/java.lang.Class.checkMethod(DynamicHub.java:1044) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]
        at java.base@17.0.11/java.lang.Class.getConstructor0(DynamicHub.java:1210) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]
        at java.base@17.0.11/java.lang.Class.newInstance(DynamicHub.java:626) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]
        ... 22 common frames omitted

         */

        try {
            hints.reflection().registerConstructor(com.ibm.wsdl.extensions.schema.SchemaImpl.class.getDeclaredConstructor(), ExecutableMode.INVOKE);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        /*
        Caused by: javax.wsdl.WSDLException: WSDLException: faultCode=CONFIGURATION_ERROR: Problem instantiating Java extensionType 'com.ibm.wsdl.extensions.soap.SOAPBindingImpl'.: java.lang.InstantiationException: com.ibm.wsdl.extensions.soap.SOAPBindingImpl
        at javax.wsdl.extensions.ExtensionRegistry.createExtension(ExtensionRegistry.java:383) ~[com.example.producingwebservice.ProducingWebServiceApplication:1.6.3]
        at org.springframework.ws.wsdl.wsdl11.provider.Soap11Provider.createSoapExtension(Soap11Provider.java:352) ~[na:na]
        at org.springframework.ws.wsdl.wsdl11.provider.Soap11Provider.populateBinding(Soap11Provider.java:137) ~[na:na]
        at org.springframework.ws.wsdl.wsdl11.provider.DefaultConcretePartProvider.addBindings(DefaultConcretePartProvider.java:92) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]
        at org.springframework.ws.wsdl.wsdl11.provider.SoapProvider.addBindings(SoapProvider.java:99) ~[na:na]
        at org.springframework.ws.wsdl.wsdl11.ProviderBasedWsdl4jDefinition.afterPropertiesSet(ProviderBasedWsdl4jDefinition.java:249) ~[na:na]
        at org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition.afterPropertiesSet(DefaultWsdl11Definition.java:185) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1822) ~[com.example.producingwebservice.ProducingWebServiceApplication:6.1.1]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1771) ~[com.example.producingwebservice.ProducingWebServiceApplication:6.1.1]
        ... 16 common frames omitted

         */

        try {
            hints.reflection().registerConstructor(com.ibm.wsdl.extensions.soap.SOAPBindingImpl.class.getDeclaredConstructor(), ExecutableMode.INVOKE);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        /*
        Caused by: javax.wsdl.WSDLException: WSDLException: faultCode=CONFIGURATION_ERROR: Problem instantiating Java extensionType 'com.ibm.wsdl.extensions.soap.SOAPOperationImpl'.: java.lang.InstantiationException: com.ibm.wsdl.extensions.soap.SOAPOperationImpl
        at javax.wsdl.extensions.ExtensionRegistry.createExtension(ExtensionRegistry.java:383) ~[com.example.producingwebservice.ProducingWebServiceApplication:1.6.3]
        at org.springframework.ws.wsdl.wsdl11.provider.Soap11Provider.createSoapExtension(Soap11Provider.java:352) ~[na:na]
        at org.springframework.ws.wsdl.wsdl11.provider.Soap11Provider.populateBindingOperation(Soap11Provider.java:253) ~[na:na]
        at org.springframework.ws.wsdl.wsdl11.provider.DefaultConcretePartProvider.createBindingOperations(DefaultConcretePartProvider.java:139) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]
        at org.springframework.ws.wsdl.wsdl11.provider.DefaultConcretePartProvider.addBindings(DefaultConcretePartProvider.java:93) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]
        at org.springframework.ws.wsdl.wsdl11.provider.SoapProvider.addBindings(SoapProvider.java:99) ~[na:na]
        at org.springframework.ws.wsdl.wsdl11.ProviderBasedWsdl4jDefinition.afterPropertiesSet(ProviderBasedWsdl4jDefinition.java:249) ~[na:na]
        at org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition.afterPropertiesSet(DefaultWsdl11Definition.java:185) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1822) ~[com.example.producingwebservice.ProducingWebServiceApplication:6.1.1]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1771) ~[com.example.producingwebservice.ProducingWebServiceApplication:6.1.1]
        ... 16 common frames omitted

         */

        // TODO evtl. Hints in Klasse generieren? at org.springframework.ws.wsdl.wsdl11.provider.Soap11Provider.populateBindingInput(Soap11Provider.java:217) ~[na:na]
        try {
            for (var clazz : List.of(com.ibm.wsdl.extensions.soap.SOAPOperationImpl.class, com.ibm.wsdl.extensions.soap.SOAPAddressImpl.class,
                    com.ibm.wsdl.extensions.soap.SOAPBindingImpl.class, com.ibm.wsdl.extensions.soap.SOAPBodyImpl.class, com.ibm.wsdl.extensions.soap.SOAPFaultImpl.class,
                    com.ibm.wsdl.extensions.soap.SOAPHeaderFaultImpl.class)) {
                hints.reflection().registerConstructor(clazz.getDeclaredConstructor(), ExecutableMode.INVOKE);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // TODO Test with WSSE as well!
    }
}
