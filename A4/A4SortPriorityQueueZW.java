import java.util.Random;

public class A4SortPriorityQueue {
    public static void heapSort(int[] a) { 
        int k = a.length;

        for (int i = k - 1; i > 0; i--) {

            int temp = a[i];
            a[i] = a[i];
            a[i] = temp;

            swap(a, i, 0);
        }

        for (int i = k / 2 - 1; i >= 0; i--)
            swap(a, k, i);
    }

    public static void swap(int[] a, int n, int i) {
        int j = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && a[left] > a[j])
            j = left;

        if (right < n && a[right] > a[j])
            j = right;

        if (j != i) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;

            swap(a, i, j);
        }
    }

    /*Extra Credit:
   private static void quickSort(int[] a){
      quickSort(a, 0, a.length - 1);
   }
   private static void insertionSort(int[] a, int l, int h){
      for (int i = l + 1; i <= h; i++){
         int j = i;
         int v = a[i];
         while (j > l && v < a[j - 1]){
            a[j] = a[j - 1];
            j--;
         }
         a[j] = v;
      }
   }
   private static void quickSort(int[] a, int l, int h){ //Update this method to be more efficient
      if (l >= h) return;
      int pivotIdx = partition(a, l, h);
      quickSort(a, l, pivotIdx - 1); 
      quickSort(a, pivotIdx + 1, h);
   }
   public static int partition(int[] a, int l, int h){
      int len = (h - l) + 1;
      Random r = new Random();
      int idx = r.nextInt(len) + l;
      swap(a, idx, h);
      int pivot = a[h];
      int last = h;
      int first = l;
      h--;
      while (l < h){
         while(a[l] < pivot) l++;
         while(h > first && a[h] >= pivot) h--;
         if (l < h) swap(a, l, h);
	     else swap(a, l, last);
      }
      return l;
   }
   */
    public static void main(String[] args) {
        long time, nextTime;
        int[] a = new int[60]; //heapsort
        System.out.println("heapSort: ");
        Random r = new Random();
        for (int i = 0; i < a.length; i++) a[i] = r.nextInt(100);
        for (int s = 0; s < 3; s++) {
            /*
        if (s == 2){
            Extra Credit:
      		System.out.println("quickSort: ");
      		time = System.nanoTime();
      		quickSort(a);
      		nextTime = System.nanoTime();
      			
      	}
      	else */
            {
                time = System.nanoTime();
                heapSort(a);
                nextTime = System.nanoTime();
            }
            System.out.println("\tTime used: " + (nextTime - time) + " nseconds");
            for (int i = 0; i < a.length; i++) System.out.print(a[i] + ",");
            System.out.println();
            for (int i = 0; i < a.length; i++) a[i] = r.nextInt(100);
        }

        MinHeapPriorityQueue < Integer > minHeap = new MinHeapPriorityQueue < > (a.length + 1);
        for (int i = 0; i < a.length; ++i) {
            minHeap.add(a[i]);
        }
        System.out.println();
        System.out.println(minHeap.removeThirdMin() + ".");

        minHeap.print();
    }

}
class MinHeapPriorityQueue < E extends Comparable < ? super E >> {
    private E data[];
    private int size;
    public MinHeapPriorityQueue() {
        this(20);
    }
    @SuppressWarnings("unchecked")
    public MinHeapPriorityQueue(int cap) {
        size = 0;
        data = (E[]) new Comparable[cap];
    }

    public boolean add(E d) {
        if (size >= data.length - 1) return false;
        data[++size] = d;
        bubbleUp(size);
        return true;
    }

    public E remove() {
        if (size <= 0) return null;
        E ans = data[1];
        data[1] = data[size--];
        bubbleDown(1);
        return ans;
    }
    private void swap(int i, int j) {
        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
    private void bubbleUp(int i) {
        if (i <= 1) return;
        if (data[i].compareTo(data[i / 2]) >= 0) return;
        swap(i, i / 2);
        bubbleUp(i / 2);
    }
    private void bubbleDown(int i) {
        if ((i * 2) > size) return;
        int min_i = i * 2;
        if (min_i + 1 <= size) {
            if (data[min_i].compareTo(data[min_i + 1]) > 0)
                min_i += 1;
        }
        if (data[i].compareTo(data[min_i]) <= 0) return;
        swap(i, min_i);
        bubbleDown(min_i);
    }
    
    public E removeThirdMin() {
    	 E Third = data[3];
         for(int i=3;i<=size-1;i++){
             data[i]=data[i+1];
         }
         size--;
         return Third ;
    }
    public void print() {
        for (int i = 1; i <= size; i++)
            System.out.print(data[i] + ",");
        System.out.println();
    }

}