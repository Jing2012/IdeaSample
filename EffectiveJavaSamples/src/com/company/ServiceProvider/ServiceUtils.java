package com.company.ServiceProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jingjing.hu on 2017/11/12.
 */
public class ServiceUtils {

    private ServiceUtils() {
    }

    private static final String DEFAULT_PROVIDER_NAME = "<def>";

    private static final Map<String, Provider> providers = new ConcurrentHashMap<>();


    public static void registerProvider(String name, Provider p) {
        providers.put(name, p);
    }

    public static void registerDefaultProvider(Provider p) {
        registerProvider(DEFAULT_PROVIDER_NAME, p);
    }


    public static Service newInstance(String name) {
        Provider provider = providers.get(name);
        if (provider == null) {
            throw new IllegalArgumentException("No provider registered with name:" + name);
        }
        return provider.newService();
    }

    public static Service newInstance() {
        return newInstance(DEFAULT_PROVIDER_NAME);
    }

}
