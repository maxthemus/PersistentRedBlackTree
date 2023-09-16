/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package version_two;

import java.util.HashMap;

/**
 *
 * @author maxthemus
 */
public class PersistentTree<E extends Comparable<E>> extends RedBlackTree<E>{
    //Fields
    private RedBlackNode<E> tempRoot;
    private RedBlackNode<E> tempPointer;
    private HashMap<Integer, RedBlackNode<E>> roots;
    private int rootCount;
    
    //Constructor
    public PersistentTree() {
        super();
        this.rootCount = 0;
        this.roots = new HashMap<>();
    }

    //Methods
    @Override
    protected Node<E> copyTree() {
        //Looks at stack to rebuild tree
        System.out.println(this.treeToString(tempRoot));
        this.roots.put(this.rootCount, tempRoot);
        this.rootCount++;
        return this.tempRoot;
    }

    @Override
    protected void nodeForCopy(Node<E> node) {
        RedBlackNode<E> copyOfNode = this.copyNode((RedBlackNode<E>)node);
        
        if(this.tempRoot == nil) {
            this.tempRoot = copyOfNode;
        } else {
            int compareValue = copyOfNode.compareTo(this.tempPointer);
            if(compareValue < 0) {
                //Left child
                this.tempPointer.leftChild = copyOfNode;
            } else {
                //Right child
                this.tempPointer.rightChild = copyOfNode;
            }
        }
        this.tempPointer = copyOfNode;
    }
    
    private RedBlackNode<E> copyNode(RedBlackNode<E> nodeToCopy) {
        RedBlackNode<E> newNode = new RedBlackNode<>(nodeToCopy);
        return newNode; 
    }

    @Override
    protected void startCopyStack() {
        this.tempRoot = (RedBlackNode<E>)nil;
        this.tempPointer = (RedBlackNode<E>)nil;
    }

    @Override
    public String toString() {
        String tempString = "Tree:\n";
        for(int i = 0; i < this.rootCount; i++) {
            Node<E> treeRoot = this.roots.get(i);
            tempString += i + " : " + this.treeToString(treeRoot);
            tempString += "\n";
        }
        tempString += "current: " + this.treeToString(this.root);
        return tempString;
    }
    
    
    
    


    //Main Method
    public static void main(String[] args) {
        PersistentTree<Integer> tree = new PersistentTree<>();
        
        tree.add(1);
        tree.add(2);
        tree.add(3);
        
        System.out.println(tree);
    }
}
