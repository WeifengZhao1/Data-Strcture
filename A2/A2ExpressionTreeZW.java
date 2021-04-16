import java.util.*;
/**
 *  * Student: Weifeng Zhao
 *    Re-Submit
 *    Late One Day
 *   */

public class A2ExpressionTreeZW{
    public static void main(String argc[]){
        ExprTree et = new ExprTree();
        et.inToTree("(3+(4*5))");
        et.print(); //print above
        et.inToTree("((1-(2+3))+(4*5))");
        et.print(); //print above
        et.inToTree("(3(4+5))");
        et.print(); //no output
        et.inToTree("((4+5)*2+1)");
        et.print(); //no output
        et.inToTree("((4+5)*3))");
        et.print(); //no output
        et.postToTree("34+5");
        et.print(); //no output

        System.out.println("Enter an algebraic expression: "); //Enter a Fully Parenthesized Expression
        Scanner s = new Scanner(System.in);
        String alg =  s.nextLine();
        s.close();
        et = new ExprTree(alg);
        et.print();


        Tree t1 = new Tree();
        Tree t2 = new Tree();
        t1.makeTree();
        t1.levelOrder();	//0 1 2 3 4 5 6 13 14 15 7 8 9 10 11 12
        t2.makeTree2();
        t2.levelOrder();	//0 1 2 3


        System.out.println("sub tree t1 and t1 " + t1.isSubTree(t1.getRoot()));
        System.out.println("sub tree t1 and t2 " + t1.isSubTree(t2.getRoot())); //t2 is not a subTree of t1
        Tree t3 = new Tree();
        t3.makeTree();
        t3.levelOrder();
        System.out.println("sub tree t1 and t3 " + t1.isSubTree(t3.getRoot())); //t3 is a subTree of t1
        Tree t4 = new Tree();
        t4.makeTree();
        t4.levelOrder();
        System.out.println("sub tree t1 and t4 " + t1.isSubTree(t4.getRoot())); //t4 is a subTree of t1
    }
}

class ExprTree{
    private static class BTNode{
        char value;
        BTNode parent, left, right;
        int index;
        public BTNode(char e){
            this(e, null, null, null);
        }
        public BTNode(char e, BTNode p, BTNode l, BTNode r){
            value = e;
            parent = p;
            left = l;
            right = r;
        }
    }
    BTNode root;
    public ExprTree(){
        root = null;
    }

    public ExprTree(String alg) {
        ExprTree et = new ExprTree();
        et.inToTree(alg);
        et.print();
    }

    public boolean isEmpty(){
        return root == null;
    }

    public void postToTree(String expression){
    }

    public void inToTree(String expression){
        root = parseInToTree(expression);
    }
    public BTNode parseInToTree(String e){
        int index = -1;
        String op = "+-*/";

        if (e.length() == 1)
            if (Character.isDigit(e.charAt(0))) {
                return new BTNode(e.charAt(0));
            }
            else {
                return null;
            }

        if (e.length() < 3 || e.charAt(0) != '(' && e.charAt(e.length() - 1) != ')') {
            return null;
        }
        e = e.substring(1, e.length() - 1);

        Deque < Character > stack = new ArrayDeque < Character > ();

        for (int i = 0; i < e.length(); i++) {
            char c = e.charAt(i);
            if (c == '(') stack.addLast(c);
            else if (c == ')') {
                if (stack.isEmpty()) {
                    return null;
                }
                else {
                    stack.removeLast();
                }
            }

            else if (stack.isEmpty() && op.contains(Character.toString(c))) {
                if (index == -1) {
                    index = i;
                }
                else {
                    return null;
                }
            }
        }

        if (index <= 0 || index == e.length() - 1) {
            return null;
        }

        BTNode l = parseInToTree(e.substring(0, index));
        BTNode r = parseInToTree(e.substring(index + 1));
        if (l == null || r == null) return null;

        BTNode result = new BTNode(e.charAt(index),null,l,r);
        l.parent = r.parent;
        l.parent = result;
        r.parent = result;
        return result;
    }

    public boolean isLeaf(BTNode n){
        if (n == null) return false;
        return n.left == null && n.right == null;
    }

    public void print(){
        infix(root);
        System.out.println();
    }

    public void infix(BTNode bt){
        if (bt == null) return;
        if (isLeaf(bt)){
            System.out.print(bt.value);
            return;
        }
        System.out.print("(");
        infix(bt.left);
        System.out.print(bt.value);
        infix(bt.right);
        System.out.print(")");
    }
}


class Tree {
    private static class TNode {
        private int value;
        private TNode parent, left, right,t;
        private List < TNode > children;

        public TNode() {
            this(0, null);
        }

        public TNode(int e) {
            this(e, null);
        }

        public TNode(int e, TNode p) {
            value = e;
            parent = p;
            children = new ArrayList < TNode > ();
        }

        public int getValue() {
            return value;
        }
        public TNode getParent() {
            return parent;
        }
        public TNode getLeft() {
            return left;
        }
        public TNode getRight() {
            return right;
        }
        public List < TNode > getChildren() {
            return children;
        }
    }

    private TNode root;
    private int size;

    Tree() {
        root = null;
        size = 0;
    }

    public TNode createNode(int e, TNode p) {
        return new TNode(e, p);
    }
    public TNode addChild(TNode n, int e) {
        TNode temp = createNode(e, n);
        n.children.add(temp);
        size++;
        return temp;
    }
    public TNode addRoot(int e) throws IllegalArgumentException {
        if (root != null) throw new IllegalArgumentException("Root is full");
        root = createNode(e, null);
        size++;
        return root;
    }
    public void levelOrder() {
        List < TNode > queue = new LinkedList < > ();
        queue.add(root);
        levelOrderPrint(queue);
        System.out.println();
    }
    private void levelOrderPrint(List < TNode > l) {
        if (l.isEmpty()) {
            return;
        }
        TNode child = l.remove(0);
        System.out.print(child.getValue() + " ");
        l.addAll(child.getChildren());
        levelOrderPrint(l);
    }

    public void makeTree() {
        TNode rt = addRoot(0);
        buildTree(rt, 3);
    }
    public void makeTree2() {
        TNode rt = addRoot(0);
        buildTree(rt, 1);
    }

    public TNode getRoot() {
        return root;
    }

    public boolean isSubTree(TNode s) {
        if (subTreeHelper(root, s)) {
            return true;
        }
        return false;
    }

    private boolean subTreeHelper(TNode T, TNode S) {
        if (S == null) return true;

        if (T == null) return false;
        if (areSame(T, S)) return true;
        return subTreeHelper(T.left, S) || subTreeHelper(T.right, S);
    }

    private boolean areSame(TNode root1, TNode root2) {
        if (root1 == null && root2 == null) return true;
        if (root1 == null || root2 == null) return false;
        return (root1.value == root2.value && areSame(root1.left, root2.left) && areSame(root1.right, root2.right));
    }

    private void buildTree(TNode n, int i) {
        if (i <= 0) return;
        TNode fc = addChild(n, size);
        TNode sc = addChild(n, size);
        TNode tc = addChild(n, size);
        buildTree(fc, i - 1);
        buildTree(sc, i - 2);
        if (i % 2 == 0)
            buildTree(tc, i - 1);
    }
} 


