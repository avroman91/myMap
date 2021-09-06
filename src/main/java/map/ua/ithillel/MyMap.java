package map.ua.ithillel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class MyMap<K, V> {

    static class Node<K, V> {
        Map.Entry<K, V> pair;
        Node<K, V> next;

        public Map.Entry<K, V> getPair() {
            return pair;
        }

        public void setPair(Map.Entry<K, V> pair) {
            this.pair = pair;
        }

        @Override
        public String toString() {
            return pair.toString();
        }
    }

    private final Node<K, V>[] buckets = new Node[16];
    public Set<Node<K, V>> entrySet = new HashSet<>();


    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public void put(K key, V value) {

        int index = getIndex(key);

        Node<K, V> node = new Node<>();
        node.pair = new Pair<>(key, value);

        if (buckets[index] == null) {
            buckets[index] = node;
            entrySet.add(node);
        } else {
            Node<K, V> current = buckets[index];
            while (current != null) {
                if (current.pair.getKey().equals(key)) {
                    current.pair = node.pair;
                    for (Node<K, V> kvNode : entrySet) {
                        if (kvNode.getPair().getKey() == current.pair.getKey()) {
                            kvNode.setPair(node.pair);
                        }
                    }
                    break;
                }
                if (current.next == null) {
                    current.next = node;
                    entrySet.add(node);
                    break;
                }
                current = current.next;
            }
        }
    }


    public boolean containsKey(K key) {
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
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
        Node<K, V> current = buckets[index];
        while (current != null) {
            if (current.pair.getKey().equals(key)) {
                return current.pair.getValue();
            } else {
                current = current.next;
            }
        }
        return null;
    }

    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                keySet.add(current.pair.getKey());
                current = current.next;
            }
        }
        return keySet;
    }
}