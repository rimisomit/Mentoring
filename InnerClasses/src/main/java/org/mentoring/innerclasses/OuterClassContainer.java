package org.mentoring.innerclasses;

/**
 * Created by user on 1/8/15.
 */
public class OuterClassContainer {

    Runnable runnable = new Runnable() {
        int a;
        @Override
        public void run() {

        }
    };
    private int aaaa;
    //=========================<\Member>===================

    public static void main(String[] args) {
        //=========================<Member>===================
        //Error. Cannot be crated w/o outer class
        //InnerMember innerMember1 = new InnerMember();
        OuterClassContainer outerClassContainer = new OuterClassContainer();
        InnerMember innerMember1 = outerClassContainer.new InnerMember();
        //OuterClassContainer.InnerMember innerMember2 = new OuterClassContainer.InnerMember();
        //Error
        //OuterClassContainer innerMember2 = outerClassContainer.new InnerMember();
        //access to private methods
        innerMember1.printText();
        System.out.println(innerMember1.a);
        //=========================<\Member>===================

        //=========================<Static>===================
        InnerStatic innerStatic = new InnerStatic();


        //OuterClassContainer.InnerStatic innerStatic1 = new OuterClassContainer().InnerStatic();
        //=========================<\Static>===================

        //=========================<Inner local>===================
    }

    public void methodForInner(final int param) {
        final int a_out = 500;
        //=========================<Inner local>===================
        class InnerMethod {
            public int b_in = 400;
            private int a_in = 300;

            public void printText() {
                System.out.println("Inner local param = " + param + " a_in = " + a_in + " a_out = " + a_out);
            }

        }
        //=========================<\Inner local>===================

    }

    public void methodForInner2(final int param) {
        final int a_out = 500;
        //=========================<Inner local>===================
        class InnerMethod {
            public int b_in = 400;
            private int a_in = 300;

            public void printText() {
                System.out.println("Inner local param = " + param + " a_in = " + a_in + " a_out = " + a_out);
            }

        }
        //=========================<\Inner local>===================

    }

    public void methodForAnon() {
        //=========================<Anon>===================
        AnonClass anonClass = new AnonClass(){
            //no constructor
            public void display() {
                System.out.println("Anon Class");
            }
        };
        AnonClass anonClass1 = new AnonClass(){
            //no constructor
            public void display() {
                System.out.println("Anon Class");
            }
        };
        Runnable r = new Runnable() {
            @Override
            public void run() {

            }
        };
        Comparable cr = new Comparable() {
            @Override
            public int compareTo(Object o) {
                return 0;
            }
        };
    }

    //=========================<Static>===================
    private static class InnerStatic {
        public static int b_stat = 300;
        private int a_stat = 200;
        // Constructor
        private InnerStatic(int a, int b) {
            this.a_stat = a;
            this.b_stat = b;
        }
        private InnerStatic() {
        }

        public static void main(String[] args) {

        }

        private void printText() {
            System.out.println("Static a = " + a_stat + " b = " + b_stat);
        }
    }

    //=========================<\Static>===================
    public static class InnerStaticPublic {
        public static int b_stat = 300;
        public int a_pub = 2001;
        private int a_stat = 200;
        // Constructor
        public InnerStaticPublic(int a, int b) {
            this.a_stat = a;
            this.b_stat = b;
        }
        public InnerStaticPublic() {
        }

        public static void main(String[] args) {

        }

        private void printText() {
            System.out.println("Static a = " + a_stat + " b = " + b_stat);
        }
    }

    //=========================<Member>===================
    private class InnerMember {
        private int a = 100;
        // Error. Cant have static
        //private static int b = 200;

        // Constructor
        private InnerMember(int a) {
            this.a = a;
        }
        private InnerMember() {
        }

        private void printText() {
            System.out.println("InnerMember: a= " + a);
        }

        /* Error. Cant have static
        public static void main(String[] args) {

        }
        */
    }

    public class InnerMemberPublic {
        public int b = 100;
        private int a = 100;
        // Error. Cant have static
        //private static int b = 200;

        // Constructor
        public InnerMemberPublic(int a) {
            this.a = a;
        }
        public InnerMemberPublic() {
            System.out.println(OuterClassContainer.this.aaaa);
        }

        private void printText() {
            System.out.println("InnerMember: a= " + a);
        }



        /* Error. Cant have static
        public static void main(String[] args) {

        }
        */
    }

}
