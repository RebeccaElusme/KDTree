import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class KdTree {
    private static class Node{
        private double[] diagnosisAttribute;
        public int height;
        public Node left, right;
        public Node(double[] attribute){
            this.diagnosisAttribute = attribute;
            this.left = null;
            this.right = null;
            height = 0;
        }
        public Node(double[] attribute, int height){
            this(attribute);
            this.height = height;
        }
        public boolean isMalignant  (){
            return this.diagnosisAttribute[0]==1.0;
        }

        public double distanceToOtherNode(Node otherNode, int k){
            double SumOfSquares = 0.0;
            for(int i=1; i<=k; i++){
                double difference = this.diagnosisAttribute[i] - otherNode.diagnosisAttribute[i];;
                SumOfSquares += difference * difference;
            }
            return Math.sqrt(SumOfSquares);
        }
    }
    private Node root;
    int dimension;
    public KdTree(int k){
        this.dimension = k;
        this.root = null;
    }
    public Node addNode(double[] value){
        Node newNode = new Node(value);
        if(root==null)
            root = newNode;
        else
            newNode = this.recursiveAddNode(root, value, 0);
        return newNode;
    }
    private Node recursiveAddNode(Node currNode, double[] value, int height){
        if (currNode == null)
            return new Node(value, height+1);
        int currDimension = currNode.height%this.dimension+1;
        if (value[currDimension] < currNode.diagnosisAttribute[currDimension]) {
            currNode.left = recursiveAddNode(currNode.left, value, currNode.height);
        } else {
            currNode.right = recursiveAddNode(currNode.right, value, currNode.height);
        }

        return currNode;
    }
    // Reading the csv file
    private static double[][] readCSVFile(){
        double[][] data = new double[569][11];
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("data.csv"))) {
            // Here is the array structure by index
            // 0 diagnosis	1 radius_mean	2 texture_mean	3 perimeter_mean	4 area_mean
            // 5 smoothness_mean	6 compactness_mean	7 concavity_mean	8 concave points_mean	9 symmetry_mean
            // 10 fractal_dimension_mean

            // Read the first line of the csv, but we don't use it
            String[] row = br.readLine().split(",");
            int index = 0;
            while ((line = br.readLine()) != null) {
                row = line.split(",");
                double[] rowDouble = new double[11];
                rowDouble[0] = row[1].equals("M")? 1.0:0.0;
                for(int i=1; i< 11; i++)
                    rowDouble[i] = Double.parseDouble(row[i+1]);
                data[index++] = rowDouble;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static void main(String[] args) {
        double[][] data = readCSVFile();
    KdTree tree = new KdTree(3);
    tree.addNode(data[0]);
    tree.addNode(data[1]);
    tree.addNode(data[2]);
    tree.addNode(data[3]);
    tree.addNode(data[4]);
    tree.addNode(data[5]);
    tree.addNode(data[6]);
    tree.addNode(data[7]);
    tree.addNode(data[8]);
    tree.addNode(data[9]);
    tree.addNode(data[10]);
    tree.addNode(data[11]);
    tree.addNode(data[12]);
    tree.addNode(data[13]);
    }
}
