/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment_three;

import java.util.HashMap;

/**
 *
 * @author max
 */
public class PersistentDynamicTree<E extends Comparable> extends BinarySearchTree<E> {

    //Fields
    private static final int INITIAL_CAPACITY = 5;
    protected HashMap<Integer, Node<E>> rootNodes;
    protected int versionCount;

    //Constructor
    public PersistentDynamicTree() {
        super();
        this.versionCount = 0;
        this.rootNodes = new HashMap<>(INITIAL_CAPACITY);
    }

    //Methods
    @Override
    protected void buildTree() {        
        if (this.stack.peek() != nil) {
            Node<E> childNode = this.stack.pop();
            while (stack.peek() != nil) {
                Node<E> parentNode = this.stack.pop();
                int compare = childNode.compareTo(parentNode);

                if (compare == 0) {
                    System.out.println("SOMETHING WENT WRONG!");
                } else if (compare < 0) {
                    parentNode.leftChild = childNode;
                } else {
                    parentNode.rightChild = childNode;
                }
                childNode = parentNode;
            }            
            this.createNewTree(childNode);
        } else {
            this.createNewTree(nil);
        }
    }

    protected void buildTree(Node<E> replacement, Node<E> removed) {
        if (this.stack.peek() != nil) {
            Node<E> childNode = this.stack.pop();
            while (stack.peek() != nil) {
                Node<E> parentNode = this.stack.pop();
                int compare = childNode.compareTo(parentNode);

                if (compare == 0) {
                    System.out.println("SOMETHING WENT WRONG!");
                } else if (compare < 0) {
                    parentNode.leftChild = childNode;
                } else {
                    parentNode.rightChild = childNode;
                }
                childNode = parentNode;
            }
            Node<E> temp = this.rootNode;
            this.rootNode = childNode;
            childNode = temp;
            this.createNewTree(childNode);
        } else {
            this.createNewTree(this.duplicateNode(removed));
        }
    }

    protected void createNewTree(Node<E> rootOfNewTree) {
        this.rootNodes.put(this.versionCount, rootOfNewTree);
        this.versionCount++;
        this.rootNodes.put(this.versionCount, this.duplicateNode(this.rootNode));
    }


    @Override
    protected void nodeVisited(Node<E> node) {
        super.nodeVisited(this.duplicateNode(node)); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public String toString() {
        String tempString = "";

        for (int i = 0; i <= this.versionCount; i++) {
            tempString += "Version " + i + ":\n";

            tempString += this.nodeToString(this.rootNodes.get(i));

            tempString += "\n";
        }

        return tempString;
    }

    //Main Method
    public static void main(String[] args) {
        BinarySearchTree<String> tree = new PersistentDynamicTree<>();

        tree.add("Ant");
        tree.add("Bee");
        tree.add("Cat");
        tree.add("Dog");
        tree.remove("Dog");
        tree.remove("Bee");

        System.out.println(tree.toString());
    }
}
