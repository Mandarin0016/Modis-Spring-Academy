package model;

public class Main {
    public static void main(String[] args) {
        Person firstPerson = new Person(2L, "Viktor", "Aleksandrov", 21);
        System.out.println(firstPerson);
        Person secondPerson = new Person(2L, "Ivanka", "Ivanov", 32);
        System.out.println(secondPerson);
        System.out.println("Are these equal: " + firstPerson.equals(secondPerson));

        System.out.println(System.identityHashCode("tensada"));
        System.out.println(System.identityHashCode("friabili"));
        System.out.println("tensada".hashCode());
        System.out.println("friabili".hashCode());
        String a = "tensada";
        String b = "friabili";
        System.out.println(a.equals(b));
        System.out.println(a.equals(b));
        System.out.println(a.equals(b));
        System.out.println(a.equals(b));
    }
}
