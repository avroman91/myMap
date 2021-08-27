package map.ua.ithillel;

import java.util.Iterator;
import java.util.Map;

class MyMap<K, V> implements Iterable<Pair<K, V>> {
    @Override
    public Iterator<Pair<K, V>> iterator() {
        return new Iterator<>() {
            int index;
            Node<K, V> current;

            {
                index = getNextHeadIndex(-1);
                if (index != -1) current = buckets[index];
            }

            private int getNextHeadIndex(int currentHead) {
                for (int i = currentHead + 1; i < buckets.length; i++) {
                    if (buckets[i] != null) return i;
                }
                return -1;
            }

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Pair<K, V> next() {
                Pair<K, V> value = (Pair<K, V>) current.pair;
                current = current.next;
                if (current == null) {
                    index = getNextHeadIndex(index);
                    if (index != -1) current = buckets[index];
                }
                return value;
            }

        };
    }

    private static class Node<K, V> {
        Map.Entry<K, V> pair;
        Node<K, V> next;
    }

    private Node<K, V>[] buckets = new Node[16];


    private int getIndex(K key) {
        return key.hashCode() % buckets.length;
    }

    public void put(K key, V value) {

        int index = getIndex(key);

        Node<K, V> node = new Node<K, V>();
        node.pair = new Pair<K, V>(key, value);

        if (buckets[index] == null) buckets[index] = node;
        else {
            Node<K, V> current = buckets[index];
            while (current != null) {
                if (current.pair.getKey().equals(key)) {
                    current.pair = node.pair;
                    break;
                }
                if (current.next == null) {
                    current.next = node;
                    break;
                }
                current = current.next;
            }
        }
    }


    public boolean containsKey(K key) {
        for (Node<K,V> bucket : buckets) {
            Node<K,V> current = bucket;
            while (current != null) {
                if (current.pair.getKey().equals(key)) {
                    return true;
                } else {
                    current = current.next;
                }
            }
        }
        return false;
    }

    public V get(K key) {
        int index = key.hashCode() % 16;
        Node<K,V> current = buckets[index];
        while (current != null) {
            if (current.pair.getKey().equals(key)) {
                return current.pair.getValue();
            } else {
                current = current.next;
            }
        }
        return null;
    }

    public String keySet() {
        StringBuilder st = new StringBuilder();
        for (Node<K,V> bucket : buckets) {
            Node<K,V> current = bucket;
            while (current != null) {
                st.append(current.pair.getKey()).append(", ");
                current = current.next;
            }
        }
        st.delete(st.length()-2,st.length()).append(".");
        return st.toString();
    }

}
