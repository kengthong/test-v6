package org.example;

import java.util.Iterator;

public class AVL<T extends Comparable<T>> {

    // Root node of AVL Tree
    public Node root;

    public boolean contains(T value) {

    }

    public boolean insert(T value) {

    }

    public boolean remove(T value) {

    }

    public class Node {
        public int bf;
        public T value;
        public int height;
        public Node left, right;
        public Node(T value) {
            this.value = value;
        }
    }
}
