package map.ua.ithillel;


public class Main {
    public static void main(String[] args) {
        MyMap<String, Integer> myMap = new MyMap<>();
        myMap.put("vasia", 2);
        myMap.put("petia", 5);
        myMap.put("oleh", 3);
        myMap.put("petia", 1);
        System.out.println(myMap.containsKey("oleh"));
        System.out.println(myMap.get("petia"));
        System.out.println(myMap.keySet());

        for (MyMap.Node<String, Integer> i : myMap.entrySet) {
            System.out.println(i);
        }

    }


}
