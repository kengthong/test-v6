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
        if (node == null) return false;

        int cmp = value.compareTo(node.value);
        if (cmp < 0) { // value might exist in the left
            return contains(value, node.left);
        }

        if (cmp > 0) { // value might exist on the right
            return contains(value, node.right);
        }

        return true;
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
    private Node insert(T value, Node node) {

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

    /**
     * Rebalance the tree starting from node
     * @param node
     * @return
     */
    private Node balance(Node node) {}

    /**
     * Update the node's height and balance factor
     * @param node
     */
    private void update(Node node) {
    }

    // Left rotation, right rotation, leftleft, leftRight, rightRight, rightLeft

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
