/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package version_two;

/**
 *
 * @author maxthemus
 */
public class RedBlackTree<E extends Comparable<E>> extends BinarySearchTree<E> {
    //Fields
    
    
    //Constructor
    public RedBlackTree() {
        super();
    }
    

    //Methods
    @Override
    protected void removeFinished(Node<E> node) {
        RedBlackNode<E> nodePtr = (RedBlackNode<E>)node;
        RedBlackNode<E> parentNode = (RedBlackNode<E>) this.getParent(nodePtr);
        
        while(nodePtr.compareTo(this.root) != 0 && nodePtr.colour == NodeColour.BLACK) {
            //Checking if nodePtr is left child
            int compareValue = nodePtr.compareTo(parentNode);
            if(compareValue < 0) {
                //Left child
                RedBlackNode<E> siblingNode = (RedBlackNode<E>) parentNode.rightChild;
                if(siblingNode != nil && siblingNode.colour == NodeColour.RED)  {
                    //CASE ONE
                    siblingNode.colour = NodeColour.BLACK;
                    parentNode.colour = NodeColour.RED;
                    this.leftRotateNode(parentNode); //Old sibling is on top of stack at this point
                    siblingNode = (RedBlackNode<E>) parentNode.rightChild; //Because parent is still parent of nodePtr
                }
                if(siblingNode == nil || 
                        ((siblingNode.leftChild == nil || ((RedBlackNode<E>)siblingNode.leftChild).colour == NodeColour.BLACK)
                        &&  ((siblingNode.rightChild == nil) || ((RedBlackNode<E>)siblingNode.rightChild).colour == NodeColour.BLACK))) {
                    //CASE TWO
                    if(siblingNode != nil) {
                        siblingNode.colour = NodeColour.RED;

                    }
                    nodePtr = (RedBlackNode<E>)this.search(parentNode.element); //Moving to parent node. top of stack should be parent of nodePtr now
                    
                    parentNode = (RedBlackNode<E>) (this.getParent(nodePtr)); //Getting parent node
                } else {
                    if(siblingNode.rightChild == nil || ((RedBlackNode<E>)siblingNode.rightChild).colour == NodeColour.BLACK) {
                        //CASE THREE
                        ((RedBlackNode<E>)siblingNode.leftChild).colour = NodeColour.BLACK;
                        siblingNode.colour = NodeColour.RED;
                        //Pushing parent onto stack because we need parent to be ontop
                        this.nodeTraversed((Node<E>)parentNode);
                        this.rightRotateNode(siblingNode);

                        this.traverseToParent(siblingNode); //Just removing random node of stack
                        this.traverseToParent(parentNode); //Removing parent of stack to clean up iswell

                        siblingNode = (RedBlackNode<E>) parentNode.rightChild;
                    }
                    //CASE 4
                    siblingNode.colour = parentNode.colour;
                    parentNode.colour = NodeColour.BLACK;
                    this.leftRotateNode(parentNode);
                    nodePtr = (RedBlackNode<E>)this.root;
                } 
            } else if(0 < compareValue) {
                //Right child
                RedBlackNode<E> siblingNode = (RedBlackNode<E>) parentNode.leftChild;
                if(siblingNode != nil && siblingNode.colour == NodeColour.RED)  {
                    //CASE ONE
                    siblingNode.colour = NodeColour.BLACK;
                    parentNode.colour = NodeColour.RED;
                    this.rightRotateNode(parentNode); //Old sibling is on top of stack at this point
                    siblingNode = (RedBlackNode<E>) parentNode.leftChild; //Because parent is still parent of nodePtr
                }
                if(siblingNode == nil || 
                        ((siblingNode.rightChild == nil || ((RedBlackNode<E>)siblingNode.rightChild).colour == NodeColour.BLACK)
                        &&  ((siblingNode.leftChild == nil) || ((RedBlackNode<E>)siblingNode.leftChild).colour == NodeColour.BLACK))) {
                    //CASE TWO
                    if(siblingNode != nil) {
                        siblingNode.colour = NodeColour.RED;

                    }
                    nodePtr = parentNode; //Moving to parent node. top of stack should be parent of nodePtr now
                    parentNode = (RedBlackNode<E>) (this.getParent(nodePtr)); //Re assigning parent node
                } else {
                    if(siblingNode.leftChild == nil || ((RedBlackNode<E>)siblingNode.leftChild).colour == NodeColour.BLACK) {
                        //CASE THREE
                        ((RedBlackNode<E>)siblingNode.rightChild).colour = NodeColour.BLACK;
                        siblingNode.colour = NodeColour.RED;
                        //Pushing parent onto stack because we need parent to be ontop
                        this.nodeTraversed((Node<E>)parentNode);
                        this.leftRotateNode(siblingNode);

                        this.traverseToParent(siblingNode); //Just removing random node of stack
                        this.traverseToParent(parentNode); //Removing parent of stack to clean up iswell

                        siblingNode = (RedBlackNode<E>) parentNode.rightChild;
                    }
                    //CASE 4
                    siblingNode.colour = parentNode.colour;
                    parentNode.colour = NodeColour.BLACK;
                    this.rightRotateNode(parentNode);
                    nodePtr = (RedBlackNode<E>)this.root;
                } 
            } else {
                System.out.println("Parent node is same as child node");
                System.exit(1);
            }
        }
//        nodePtr.colour = NodeColour.BLACK;
        nodePtr.colour = NodeColour.BLACK; //Setting root node to black
    }

    @Override
    protected void addFinished(Node<E> node) {
        //At this point top of stack should be on parent of node
        RedBlackNode<E> insertNode = (RedBlackNode<E>)node;
        insertNode.colour = NodeColour.RED; //Making newly inserted node red
        RedBlackNode<E> parentNode = (RedBlackNode<E>) this.traverseToParent(insertNode);
        RedBlackNode<E> nodePtr = insertNode;
        
        
        while(parentNode != nil && parentNode.colour == NodeColour.RED) {
            int compareValue = parentNode.compareTo(this.getParentStack(parentNode));
            if(compareValue < 0) {
                //Parent is left child
                RedBlackNode<E> uncle = (RedBlackNode<E>) this.getParentStack(parentNode).rightChild;
                if(uncle != nil && uncle.colour == NodeColour.RED) {
                    //CASE ONE
                    parentNode.colour = NodeColour.BLACK;
                    uncle.colour = NodeColour.BLACK;
                    ((RedBlackNode<E>)this.getParentStack(parentNode)).colour = NodeColour.RED; //Making colour of great parent RED
                    
                    //Now we need to make nodePtr into gparent which is top of stack
                    nodePtr = (RedBlackNode<E>) this.traverseToParent(parentNode);
                    parentNode = (RedBlackNode<E>) this.traverseToParent(nodePtr);
                } else {
                    //Right child
                    if(0 < nodePtr.compareTo(parentNode)) {
                        //CASE TWO
                        nodePtr = parentNode;
//                        parentNode = (RedBlackNode<E>) this.traverseToParent(nodePtr); // <-- Might not need to pop parent off stack
                        //LEFT ROTATE
                        this.leftRotateNode(nodePtr);
                        parentNode = (RedBlackNode<E>) this.traverseToParent(nodePtr);
                    }
                    //CASE THREE
                    parentNode.colour = NodeColour.BLACK;
                    RedBlackNode<E> grandParent = (RedBlackNode<E>) this.traverseToParent(parentNode);
                    grandParent.colour = NodeColour.RED;
                    this.rightRotateNode(grandParent);
                }
            } else if(0 < compareValue) {
                //Parent is right child
                RedBlackNode<E> uncle = (RedBlackNode<E>) this.getParentStack(parentNode).leftChild;
                if(uncle != nil && uncle.colour == NodeColour.RED) {
                    //CASE ONE
                    parentNode.colour = NodeColour.BLACK;
                    uncle.colour = NodeColour.BLACK;
                    ((RedBlackNode<E>)this.getParentStack(parentNode)).colour = NodeColour.RED; //Making colour of great parent RED
                    
                    //Now we need to make nodePtr into gparent which is top of stack
                    nodePtr = (RedBlackNode<E>) this.traverseToParent(parentNode);
                    parentNode = (RedBlackNode<E>) this.traverseToParent(nodePtr);
                } else {
                    //Checking left child
                    if(nodePtr.compareTo(parentNode) < 0) {
                        //CASE TWO
                        nodePtr = parentNode;
//                        parentNode = (RedBlackNode<E>) this.traverseToParent(nodePtr); // <-- Might not need to pop parent off stack
                        //LEFT ROTATE
                        this.rightRotateNode(nodePtr);
                        parentNode = (RedBlackNode<E>) this.traverseToParent(nodePtr);
                    }
                    //CASE THREE
                    parentNode.colour = NodeColour.BLACK;
                    RedBlackNode<E> grandParent = (RedBlackNode<E>) this.traverseToParent(parentNode);
                    grandParent.colour = NodeColour.RED;
                    this.leftRotateNode(grandParent);
                }
            } else {
                System.out.println("Parent is same as child");
                System.exit(1);
                return;
            }
        }
        ((RedBlackNode<E>)this.root).colour = NodeColour.BLACK; //Setting root node to black
    }

    
    @Override
    protected void setNil(Node<E> node) {
        RedBlackNode<E> nilNode = new RedBlackNode<E>(node);
        nilNode.colour = NodeColour.BLACK;
        this.nil = nilNode;
    }

    @Override
    protected Node<E> createNode(E element) {
        return new RedBlackNode<>(element);
    }
    
    
    //Private inner class
    protected class RedBlackNode<E extends Comparable<E>> extends Node<E> {
        //Fields
        public NodeColour colour;
        
        
        //Constructor
        public RedBlackNode(E element) {
            super(element);
            this.colour = NodeColour.BLACK;
        }
        
        public RedBlackNode(Node<E> node) {
            super(node.element);
            this.colour = NodeColour.RED; //New nodes are RED by default.
        }
        
        
        //Methods

        @Override
        public String toString() {
            if(this.colour == NodeColour.RED) {
                return super.toString() + "R";
            } else {
                return super.toString() + "B";
            }
        }
        
    }
    
    //Private inner enum
    private enum NodeColour {
        RED,
        BLACK
    };
    
    
    //Main Method
    public static void main(String[] args) {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        
        
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        tree.add(6);
        tree.add(7);
        tree.add(8);        
        System.out.println(tree);
        
        System.out.println("+----------------+");
   
       
        tree.remove(8);
        System.out.println(tree);
        
        System.out.println("+----------------+");
        
        tree.remove(7);
        System.out.println(tree);
        
        System.out.println("+----------------+");
        
        tree.remove(6);
        System.out.println(tree);
        
        System.out.println("+----------------+");
        
        tree.remove(1);
        System.out.println(tree);
    }
}
