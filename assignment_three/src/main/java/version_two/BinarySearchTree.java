/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package version_two;

/**
 *
 * @author max
 */
public class BinarySearchTree<E extends Comparable<E>> {
    //Fields
    protected Node<E> nil;
    protected Node<E> root;
    private LinkedStack<Node<E>> stack; //Top of stack is parent node
    
    
    //Constructor
    public BinarySearchTree() {
        setNil(new Node<>(null));
        this.root = nil;
        this.stack = new LinkedStack<>();
    }

    
    //Methods
    /** SEARCHING NODE. */
    public Node<E> search(E element) {
        if (this.root == nil) {
            return nil;
        } else {
            startTraversal(); //Stetting up traversal stack
            return searchNode(element, root);
        }
    }

    protected Node<E> searchNode(E element, Node<E> rootNode) {
        int compareValue = element.compareTo(rootNode.element);

        if (compareValue < 0) {
            if (rootNode.leftChild == nil) {
                return nil;
            } else {
                this.nodeTraversed(rootNode); //Adding parent to stack
                return searchNode(element, rootNode.leftChild);
            }
        } else if (0 < compareValue) {
            if (rootNode.rightChild == nil) {
                return nil;
            } else {
                this.nodeTraversed(rootNode); //Adding parent to stack
                return searchNode(element, rootNode.rightChild);
            }
        }
        return rootNode ; //Found
    }
    
    /** ADDING NODE. */
    //Returns new root of tree
    public Node<E> add(E element) {
        Node<E> nodeToAdd = new Node<>(element);
        if(this.root != nil) {
            startTraversal(); //Stetting up traversal stack
            return addNode(nodeToAdd, this.root);
        } else {
            this.root = nodeToAdd;
            return nodeToAdd;
        }
    }
    
    protected Node<E> addNode(Node<E> nodeToAdd, Node<E> nodePtr) {
        int compareValue = nodeToAdd.compareTo(nodePtr);
        if(compareValue < 0) {
            if(nodePtr.leftChild == nil) {
                this.nodeTraversed(nodePtr); //Adding parent to stack
                nodePtr.leftChild = nodeToAdd;
            } else {
                this.nodeTraversed(nodePtr); //Adding parent to stack
                return addNode(nodeToAdd, nodePtr.leftChild);
            }
        } else if(0 < compareValue) {
            if(nodePtr.rightChild == nil) {
                this.nodeTraversed(nodePtr); //Adding parent to stack
                nodePtr.rightChild = nodeToAdd;
            } else {
                this.nodeTraversed(nodePtr); //Adding parent to stack
                return addNode(nodeToAdd, nodePtr.rightChild);
            }
        }
        
        //Node already exists
        return nodePtr;
    }
    
    
    /** REMOVING NODE. */
    //Returns new root node of tree
    public Node<E> remove(E element) {
        if(this.root != nil) {
            Node<E> nodeToRemove = this.search(element);
            if(nodeToRemove == nil) {
                //Node not found
                return this.root;
            } else {
                //Node was found
                this.removeNode(nodeToRemove);
                
            }
        }
        return nil;
    }
    
    
    //Returns replacement node
    protected Node<E> removeNode(Node<E> nodeToRemove) {
        //Parent of node to remove is top of stack
        
        //Cases:
        //1- Both Children nil, swap parent link with nil
        //2- One child nil, swap parent link with not nil child
        //3- Both Children not nil, swap parent link with successor
        
        //CASE 1
        if(nodeToRemove.leftChild == nil && nodeToRemove.rightChild == nil) {
            Node<E> parentNode = this.getParent(nodeToRemove);
            
            //Checking if parent node is root
            if(parentNode != nil) {
                int compareValue = nodeToRemove.compareTo(parentNode);
                if (compareValue < 0) {
                    parentNode.leftChild = nil;
                    return nil;
                } else if (0 < compareValue) {
                    parentNode.rightChild = nil;
                    return nil;
                } else {
                    System.out.println("Invalid remove parent is same as removal node");
                    System.exit(1);
                    return nil;
                }
            } else {
                this.root = nil;
                return nil;
            }
        } else {
            //CASE 2
            if(nodeToRemove.leftChild == nil && nodeToRemove.rightChild != nil) {
                Node<E> parentNode = this.getParent(nodeToRemove);
                Node<E> replacementNode = nodeToRemove.rightChild;
                
                //Checking if node to remove is the root node
                if(parentNode != nil) {
                    //Finding what child nodeToRemove is 
                    if (parentNode.leftChild != nil) {
                        if (parentNode.leftChild.compareTo(nodeToRemove) == 0) {
                            //nodeToRemove == LEFT CHILD

                            //inserting replacement node
                            parentNode.leftChild = replacementNode;
                            return replacementNode;
                        }
                    } else if (parentNode.rightChild != nil) {
                        if (parentNode.rightChild.compareTo(nodeToRemove) == 0) {
                            //nodeToRemove == RIGHT CHILD

                            //inserting replacement node
                            parentNode.rightChild = replacementNode;
                            return replacementNode;
                        }
                    } else {
                        System.out.println("INVALID STACK");
                        System.exit(1);
                        return nil;
                    }
                } else {
                    //Node to remove is root node
                    this.root = replacementNode;
                    return replacementNode;
                }
            } else if(nodeToRemove.rightChild == nil && nodeToRemove.leftChild != nil) {
                Node<E> parentNode = this.getParent(nodeToRemove);
                Node<E> replacementNode = nodeToRemove.leftChild;
                
                //Checking if node to remove is the root node
                if(parentNode != nil) {
                    //Finding what child nodeToRemove is 
                    if (parentNode.leftChild != nil) {
                        if (parentNode.leftChild.compareTo(nodeToRemove) == 0) {
                            //nodeToRemove == LEFT CHILD

                            //inserting replacement node
                            parentNode.leftChild = replacementNode;
                            return replacementNode;
                        }
                    } else if (parentNode.rightChild != nil) {
                        if (parentNode.rightChild.compareTo(nodeToRemove) == 0) {
                            //nodeToRemove == RIGHT CHILD

                            //inserting replacement node
                            parentNode.rightChild = replacementNode;
                            return replacementNode;
                        }
                    } else {
                        System.out.println("INVALID STACK");
                        System.exit(1);
                        return nil;
                    }
                } else {
                    //Node to remove is root node
                    this.root = replacementNode;
                    return replacementNode;
                }
            } else {
                //CASE 3 - both children are !nil
                Node<E> successor = this.findSuccessor(nodeToRemove);
                
                //At this point the stack should point towards the parent of the successor
                Node<E> successorParent = this.getParent(successor);
                
                //REMOVING successor node
                //Replacing successor parent left child with right child of the successor
                if(successor.rightChild != nil) {
                    successorParent.rightChild = successor.rightChild;
                } else {
                    successorParent.rightChild = nil;
                }
                
                
                //Now we traverse back up the stack to the nodeToRemove
                Node<E> nodePtr = successor;
                Node<E> parentPtr = successorParent;
                //Traversal while nodePtr != nodeToRemove OR while nodePtr != rootNode
                while(nodePtr.compareTo(nodeToRemove) != 0 && nodePtr != this.root) {
                    nodePtr = traverseToParent(nodePtr); //Moving parent of parent into temp
                    parentPtr = getParent(nodePtr);
                }
                
                //Removing successor from successor parent
                successorParent.leftChild = nil;
                
                //NodePtr is now nodeToRemove
                //Top of stack is parent of nodePtr
                this.copyIntoNode(successor, nodeToRemove);      
                
                return nodeToRemove;
            }
        }
        System.out.println("IDK WHY HERE");
        System.exit(1);
        return nil;
    }
    
    protected void copyIntoNode(Node<E> copy, Node<E> copyTo) {
        //Check if copy into nil because we don't want to override nil
        if(copyTo == nil) {
            return; 
        } 
        
        copyTo.element = copy.element;
    }
    
    @Override
    public String toString() {
        if (this.root == nil) {
            return "()";
        }
        String tempString = nodeToString(this.root);

        return tempString;
    }

    protected String nodeToString(Node<E> currentNode) {
        //Inorder traversal of tree using recursion
        String tempString = "";

        //Left 
        if (currentNode.leftChild != this.nil) {
            String retString = nodeToString(currentNode.leftChild);
            tempString += "(" + retString;
        } else {
            tempString += "(";
        }
        //Me
        tempString += currentNode.toString();

        //Right
        if (currentNode.rightChild != this.nil) {
            String retString = nodeToString(currentNode.rightChild);
            tempString += retString + ")";
        } else {
            tempString += ")";
        }
        return tempString;
    }
    
    

    protected void setNil(Node<E> node) {
            this.nil = node;
            nil.leftChild = nil;
            nil.rightChild = nil;

    } 
    
    /** HELPER METHODS. */
    //Finds successor of given node
    private Node<E> findSuccessor(Node<E> root) {
        if(root.rightChild == nil) {
            return nil;
        } 
        
        this.nodeTraversed(root);
        return this.findTreeMinimum(root.rightChild);
    }
    
    private Node<E> findPredessesor(Node<E> root) {
        
        return nil;
    }
    
    private Node<E> findTreeMaximum(Node<E> root) {
        while(root.rightChild.compareTo(nil) != 0) {
            this.nodeTraversed(root);
            return findTreeMaximum(root.rightChild);
        }
        return root;
    }
    
    private Node<E> findTreeMinimum(Node<E> root) {
        while(root.leftChild != nil) {
            this.nodeTraversed(root);
            return findTreeMinimum(root.leftChild);
        }
        return root;
    }
    
    
    /** STACK METHODS. */
    public void printStack() {
        System.out.println(this.stack.toString());
    }
    
    public Node<E> getParent(Node<E> node) {
        try {
            Node<E> parent = this.stack.peek();
            
            //If parent is nil then we check again the root node
            if(parent == nil) {
                if(this.root.compareTo(node) == 0) {
                    return this.stack.peek();
                } else {
                    throw new Exception("Parent is not valid ROOT");
                }
            }
            
            int compareValue = node.compareTo(parent);
            if(compareValue < 0) {
                if(parent.leftChild.compareTo(node) == 0) {
                    return parent;
                } else {
                    throw new Exception("Parent is not valid");
                }
            } else if(0 < compareValue) {
                if(parent.rightChild.compareTo(node) == 0) {
                    return parent;
                } else {
                    throw new Exception("Parent is not valid");
                }
            } else {
                throw new Exception("Top of stack is node");
            } 
        } catch(Exception err) {
            System.out.println("Stack pointing wrong parent");
            System.out.println(err);
            System.exit(1);
            return nil;
        }
    }
    
    //Removes parent from stack
    //Given correct child node will return parentOfChild
    //POPS parent off stack
    public Node<E> traverseToParent(Node<E> node) {
        try {
            Node<E> parent = this.stack.peek();
            
            //If parent is nil then we check again the root node
            if(parent == nil) {
                if(this.root.compareTo(node) == 0) {
                    return this.stack.pop();
                } else {
                    throw new Exception("Parent is not valid ROOT");
                }
            }
            
            int compareValue = node.compareTo(parent);
            if(compareValue < 0) {
                if(parent.leftChild.compareTo(node) == 0) {
                    return this.stack.pop();
                } else {
                    throw new Exception("Parent is not valid");
                }
            } else if(0 < compareValue) {
                if(parent.rightChild.compareTo(node) == 0) {
                    return this.stack.pop();
                } else {
                    throw new Exception("Parent is not valid");
                }
            } else {
                throw new Exception("Top of stack is node");
            } 
        } catch(Exception err) {
            System.out.println("Stack pointing wrong parent");
            System.out.println(err);
            System.exit(1);
            return nil;
        }
    }
    
    
    /** HOOK METHODS */
    private void startTraversal() {
        this.stack.clear(); //Making sure that stack is clear
        
        this.stack.push(nil);
    }
                
    
    private void nodeTraversed(Node<E> node) {
        this.stack.push(node);
    }
    
    private void nodeFinished(Node<E> node) {
        
    }
    
    
    
    
    //Protected inner class
    protected class Node<E extends Comparable<E>> implements Comparable<Node<E>> {
        //Fields for Node
        protected E element;
        protected Node<E> leftChild;
        protected Node<E> rightChild;
        
        //Constructors for Node
        public Node(E element) {
            this.element = element;
            this.leftChild = (Node<E>)nil;
            this.rightChild = (Node<E>)nil;
        }
        
        
        //Methods for Node
        @Override
        public int compareTo(Node<E> t) {
            if(t.element == null && this.element == null) {
                return 0;
            } else {
                return this.element.compareTo(t.element);
            }
        }

        @Override
        public String toString() {
            if(this.element == null) {
                return "nil";
            } else {
                return this.element.toString();
            }
        }
    }
    
    //Main Method
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        
        tree.add(10);
        tree.add(15);
        tree.add(14);
        tree.add(20);
        tree.add(19);
        tree.add(18);
        System.out.println(tree);
        
        System.out.println("+----------------+");
        
        tree.remove(15);
        System.out.println(tree);
        tree.printStack();
        
        System.out.println("+----------------+");
        
        tree.remove(10);
        System.out.println(tree);
        tree.printStack();
        
        System.out.println("+----------------+");
        
        tree.remove(18);
        System.out.println(tree);
        tree.printStack();
        
        System.out.println("+----------------+");
        
        tree.remove(19);
        System.out.println(tree);
        tree.printStack();

    }
    
}
