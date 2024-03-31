package hw6.bst;

public class MyAvlTreeMapDriver {

    public static void main(String[] args) {
        AvlTreeMap<Integer, String> myTree = new AvlTreeMap<>();
        myTree.insert(1, "a");
        myTree.insert(2, "b");
        myTree.insert(3, "c");
        System.out.println(myTree.toString());
        System.out.println(myTree.size());
    }

}
