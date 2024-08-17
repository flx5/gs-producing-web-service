package com.example.producingwebservice;

import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.Currency;
import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;
import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

/**
 * These hints are necessary to process requests.
 */
public class SoapRequestTimeHints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {

        // Required because glassfish does not have hints for these itself yet: https://github.com/eclipse-ee4j/jaxb-ri/pull/1802
        String[] jaxbProperties = new String[] { "SingleElementLeafProperty", "ArrayElementLeafProperty", "SingleElementNodeProperty",
        "SingleReferenceNodeProperty", "SingleMapNodeProperty", "ArrayElementNodeProperty", "ArrayReferenceNodeProperty"};

        for (String jaxbProperty : jaxbProperties) {
            TypeReference type = TypeReference.of("org.glassfish.jaxb.runtime.v2.runtime.property." + jaxbProperty);
            hints.reflection().registerType(type, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
        }



        // TODO Make sure native test fails without this!

        /*
        Caused by: org.springframework.beans.factory.BeanInitializationException: Could not find default strategy class for interface [org.springframework.ws.WebServiceMessageFactory]
        at org.springframework.ws.support.DefaultStrategiesHelper.getDefaultStrategies(DefaultStrategiesHelper.java:141) ~[na:na]
        at org.springframework.ws.support.DefaultStrategiesHelper.getDefaultStrategy(DefaultStrategiesHelper.java:212) ~[na:na]
        at org.springframework.ws.transport.http.MessageDispatcherServlet.initWebServiceMessageFactory(MessageDispatcherServlet.java:389) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]
        at org.springframework.ws.transport.http.MessageDispatcherServlet.initMessageReceiverHandlerAdapter(MessageDispatcherServlet.java:377) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]
        ... 27 common frames omitted
Caused by: java.lang.ClassNotFoundException: org.springframework.ws.soap.saaj.SaajSoapMessageFactory

         */
        try {
            hints.reflection().registerConstructor( org.springframework.ws.soap.saaj.SaajSoapMessageFactory.class.getDeclaredConstructor(), ExecutableMode.INVOKE);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        /*
        org.springframework.beans.factory.BeanInitializationException: Could not find default strategy class for interface [org.springframework.ws.transport.WebServiceMessageReceiver]
        at org.springframework.ws.support.DefaultStrategiesHelper.getDefaultStrategies(DefaultStrategiesHelper.java:141) ~[na:na]
        at org.springframework.ws.support.DefaultStrategiesHelper.getDefaultStrategy(DefaultStrategiesHelper.java:212) ~[na:na]
        at org.springframework.ws.transport.http.MessageDispatcherServlet.initMessageReceiver(MessageDispatcherServlet.java:433) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]
        at org.springframework.ws.transport.http.MessageDispatcherServlet.initStrategies(MessageDispatcherServlet.java:364) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]
        at org.springframework.ws.transport.http.MessageDispatcherServlet.onRefresh(MessageDispatcherServlet.java:296) ~[com.example.producingwebservice.ProducingWebServiceApplication:na]
        at org.springframework.web.servlet.FrameworkServlet.initWebApplicationContext(FrameworkServlet.java:603) ~[com.example.producingwebservice.ProducingWebServiceApplication:6.1.1]

        This is interesting. onRefresh is only called on the first request!
         */
        try {
            hints.reflection().registerConstructor( org.springframework.ws.soap.server.SoapMessageDispatcher.class.getDeclaredConstructor(), ExecutableMode.INVOKE);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        hints.resources().registerResource(new ClassPathResource("org/springframework/ws/soap/server/SoapMessageDispatcher.properties"));


        /*
        2024-06-07T09:39:48.319Z DEBUG 1 --- [nio-8080-exec-1] o.s.w.soap.server.SoapMessageDispatcher  : Testing endpoint adapter [org.springframework.ws.server.endpoint.adapter.DefaultMethodEndpointAdapter@3dac81f2]
2024-06-07T09:39:48.319Z TRACE 1 --- [nio-8080-exec-1] o.s.w.s.e.a.DefaultMethodEndpointAdapter : Testing if argument resolver [org.springframework.ws.server.endpoint.adapter.method.dom.DomPayloadMethodProcessor@4eb8db9a] supports [class io.spring.guides.gs_producing_web_service.GetCountryRequest]
2024-06-07T09:39:48.319Z TRACE 1 --- [nio-8080-exec-1] o.s.w.s.e.a.DefaultMethodEndpointAdapter : Testing if argument resolver [org.springframework.ws.server.endpoint.adapter.method.MessageContextMethodArgumentResolver@4f94a109] supports [class io.spring.guides.gs_producing_web_service.GetCountryRequest]
2024-06-07T09:39:48.319Z TRACE 1 --- [nio-8080-exec-1] o.s.w.s.e.a.DefaultMethodEndpointAdapter : Testing if argument resolver [org.springframework.ws.server.endpoint.adapter.method.SourcePayloadMethodProcessor@7c2dcdce] supports [class io.spring.guides.gs_producing_web_service.GetCountryRequest]
2024-06-07T09:39:48.319Z TRACE 1 --- [nio-8080-exec-1] o.s.w.s.e.a.DefaultMethodEndpointAdapter : Testing if argument resolver [org.springframework.ws.server.endpoint.adapter.method.XPathParamMethodArgumentResolver@154e0f59] supports [class io.spring.guides.gs_producing_web_service.GetCountryRequest]
2024-06-07T09:39:48.319Z TRACE 1 --- [nio-8080-exec-1] o.s.w.s.e.a.DefaultMethodEndpointAdapter : Testing if argument resolver [org.springframework.ws.server.endpoint.adapter.method.StaxPayloadMethodArgumentResolver@2e594b7d] supports [class io.spring.guides.gs_producing_web_service.GetCountryRequest]
2024-06-07T09:39:48.319Z DEBUG 1 --- [nio-8080-exec-1] s.e.SoapFaultAnnotationExceptionResolver : Resolving exception from endpoint [public io.spring.guides.gs_producing_web_service.GetCountryResponse com.example.producingwebservice.
         */

        // Ohne Native: XmlRootElementPayloadMethodProcessor
        // Aus DefaultMethodEndpointAdapter in Resolvers INit:
        /*
        			if (isPresent(JAXB2_CLASS_NAME)) {
				methodArgumentResolvers.add(new XmlRootElementPayloadMethodProcessor());
				methodArgumentResolvers.add(new JaxbElementPayloadMethodProcessor());
			}
         */

        // TODO Validate category
        hints.reflection().registerType(jakarta.xml.bind.Binder.class, MemberCategory.DECLARED_CLASSES);

        // TODO Register generated Classes for reflection (ctor call)
        for (var clazz : List.of(GetCountryRequest.class, GetCountryResponse.class, Country.class)) {
            try {
                hints.reflection().registerConstructor(clazz.getDeclaredConstructor(), ExecutableMode.INVOKE);
                // TODO Might change depending on @XmlAccessorType(XmlAccessType.FIELD)
                // TODO XmlAccessType Field seems to need declared fields and the getter/setter method. Instead of blindly registering fields it might make sense to only register the used fields?
                hints.reflection().registerType(TypeReference.of(clazz),
                        MemberCategory.DECLARED_FIELDS,
                        MemberCategory.INVOKE_DECLARED_METHODS);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            hints.reflection().registerMethod(CountryEndpoint.class.getMethod("getCountry", GetCountryRequest.class), ExecutableMode.INVOKE);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // TODO Extend this example to use an enum in the request as well as in the response to ensure de-serialization works on the client as well...
        hints.reflection().registerType(Currency.class, MemberCategory.DECLARED_FIELDS);
    }
}
