package org.example;

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
        // Since its a set, we can assume no duplicates
        if (value == null) return false;
        if (contains(value, root)) return false;
        root = insert(value, root);
        return true;
    }

    public void printInOrder(Node node) {
        StringBuilder sb = new StringBuilder();
        formInOrderString(node, sb);
        String s = sb.toString();
        s = sb.substring(0, Math.max(s.length() -1, 0)); // remove last comma
        System.out.printf("[%s]", s);
    }
    private void formInOrderString(Node node, StringBuilder sb) {
        if (node == null) {
            return;
        }

        formInOrderString(node.left, sb);
        sb.append(String.format("%s,", node.value));
        formInOrderString(node.right, sb);
    }

    /**
     * Recursively calls the private insert method to insert the node into the left or right of the node, depending on compare value
     * @param value
     * @return
     */
    private Node insert(T value, Node node) {
        if (node == null) { // inserts here
            return new Node(value);
        }

        int cmp = value.compareTo(node.value);
        if (cmp < 0) { // value is less than node.value, hence insert left
            node.left = insert(value, node.left);
        } else { // value is greater than node.value, hence insert right
            node.right = insert(value, node.right);
        }

        update(node); // update bf and height values
        return balance(node);
    }

    /**
     * Rebalance the tree starting from node
     * @param node
     * @return
     */
    private Node balance(Node node) {
        int bf = node.bf;

        // cmp can be -2, 2 -> we balance. -1,0,1 -> we do nothing
        if (bf == -1 || bf == 0 || bf == 1) return node;

        if (bf == -2) {
            if (node.left.bf <= 0) { // left left case
                return rightRotation(node);
            } else { // left right case
                return leftRight(node);
            }
        } else if (bf == 2) {
            if (node.right.bf <= 0) { // right left
                return rightLeft(node);
            } else { // right right
                return leftRotation(node);
            }
        }// right cases

        // bf == -1 / 0 / -1
        return node;
    }

    /**
     * Update the node's height and balance factor
     * @param node
     */
    private void update(Node node) {
        int rightHeight = (node.right == null) ? -1 : node.right.height;
        int leftHeight = (node.left == null) ? -1 : node.left.height;

        node.height = Math.max(rightHeight, leftHeight) + 1;
        node.bf = rightHeight - leftHeight;
    }

    /**
     * x                x
     *  \              / \
     *   x     to     x   x
     *    \
     *     x
     */
    private Node leftRotation(Node node) {
        Node newParent = node.right;
        node.right = node.right.left;
        newParent.left = node;
        update(node);
        update(newParent);
        return newParent;
    }

    /**
     *     x               x
     *    /               / \
     *   x       to      x   x
     *  /
     * x
     */
    private Node rightRotation(Node node) {
        Node newParent = node.left;
        node.left = node.left.right;
        newParent.right = node;
        update(node);
        update(newParent);
        return newParent;
    }

    /**
     *     x               x              x
     *    /               /              / \
     *   x       to      x      to      x   x
     *    \             /
     *     x           x
     * Done before right rotation
     */
    private Node leftRight(Node node) {
        node.left = leftRotation(node.left);
        return rightRotation(node);
    }

    /**
     *     x               x                x
     *      \               \              / \
     *       x       to      x     to     x   x
     *      /                 \
     *     x                   x
     * Done before right rotation
     */
    private Node rightLeft(Node node) {
        node.right = rightRotation(node.right);
        return leftRotation(node);
    }

    public class Node { // max 8 + 16 + 8 + 8*2 = 48 bytes, 10 million objects is 480mb
        public int bf;
        public T value;
        public int height;
        public Node left, right;
        public Node(T value) {
            this.value = value;
        }
    }
}
