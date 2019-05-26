package dataProviderClasses;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import dataProviderClasses.dataObjects.MethodParam;

import javax.annotation.Nonnull;
import javax.xml.ws.Provider;
import java.util.List;
import java.util.Map;

public class ClassTypeProvider implements Provider<MethodParam>{

        private static final List<String> CLASS_NAMES = ImmutableList.of("String", "Integer", "Boolean");
        private static final Map<String, Provider<MethodParam>> PROVIDERS;

        static
        {
            final ImmutableMap.Builder<String, Provider<MethodParam>> imb = ImmutableMap.builder();
            for (final String cn : CLASS_NAMES) {
                switch (cn) {
                    case "String":
                        imb.put(cn, new Provider<MethodParam>() {
                            @Override
                            public MethodParam<String> get() {
                                return new MethodParam<String>() {
                                };
                            }
                        });
                        break;
                    case "Integer":
                        imb.put(cn, new Provider<MethodParam>() {
                            @Override
                            public MethodParam<Integer> get() {
                                return new MethodParam<Integer>() {
                                };
                            }
                        });
                        break;
                    case "Boolean":
                        imb.put(cn, new Provider<MethodParam>() {
                            @Override
                            public MethodParam<Boolean> get() {
                                return new MethodParam<Boolean>() {
                                };
                            }
                        });
                        break;
                    default:
                        throw new IllegalArgumentException(String.format("%s is not a supported type %s", cn, Joiner.on(",").join(CLASS_NAMES)));
                }
            }
            PROVIDERS = imb.build();
        }

    @Override
    public MethodParam invoke(MethodParam request) {
        return null;
    }
}

