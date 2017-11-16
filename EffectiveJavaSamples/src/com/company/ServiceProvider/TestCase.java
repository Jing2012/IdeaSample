package com.company.ServiceProvider;

/**
 * Created by jingjing.hu on 2017/11/12.
 */
public class TestCase {

    public static void main(String[] args) {

        // Providers would execute these lines
        ServiceUtils.registerDefaultProvider(DEFAULT_PROVIDER);
        ServiceUtils.registerProvider("comp", COMP_PROVIDER);
        ServiceUtils.registerProvider("armed", ARMED_PROVIDER);


        // Clients would execute these lines
        Service s1 = ServiceUtils.newInstance();
        Service s2 = ServiceUtils.newInstance("comp");
        Service s3 = ServiceUtils.newInstance("armed");
        System.out.printf("%s, %s, %s%n", s1, s2, s3);
    }


    private static Provider DEFAULT_PROVIDER = new Provider() {
        @Override
        public Service newService() {
            return new Service() {
                @Override
                public String toString() {
                    return "Default Service";
                }
            };
        }
    };

    private static Provider COMP_PROVIDER = new Provider() {
        public Service newService() {
            return new Service() {
                @Override
                public String toString() {
                    return "Complementary service";
                }
            };
        }
    };

    private static Provider ARMED_PROVIDER = new Provider() {
        public Service newService() {
            return new Service() {
                @Override
                public String toString() {
                    return "Armed service";
                }
            };
        }
    };

}
