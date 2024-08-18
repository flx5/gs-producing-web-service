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
