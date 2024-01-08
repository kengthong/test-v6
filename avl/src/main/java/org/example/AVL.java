package org.example;

import java.util.Iterator;

public class AVL<T extends Comparable<T>> {

    // Root node of AVL Tree
    public Node root;

    // Additional properties: size, height

    /**
     * Public facing contains method
     * @param value
     * @return
     */
    public boolean contains(T value) {
        return contains(value, root);
    }

    /**
     * Recursively calls the private contains method onto its current, left or right nodes depending on the compare value
     * @param value
     * @param node
     * @return
     */
    private boolean contains(T value, Node node) {
    }

    /**
     * Public facing insert method
     * @param value
     * @return
     */
    public boolean insert(T value) {
        return insert(value, root);
    }

    /**
     * Recursively calls the private insert method to insert the node into the left or right of the node, depending on compare value
     * @param value
     * @return
     */
    private boolean insert(T value, Node node) {

    }

    /**
     * Public facing remove method
     * @param value
     * @return
     */
    public boolean remove(T value) {
        return remove(value, root);
    }

    /**
     * Recursively calls the private remove method on the current, left or right of node depending on compare value
     * @param value
     * @param node
     * @return
     */
    public boolean remove(T value, Node node) {

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
