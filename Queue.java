public class Queue {
    private int[] queue;
    private int front;
    private int rear;
    private int size;

    public Queue(int capacity) {
        queue = new int[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public void enqueue(int value) {
        if (size < queue.length) {
            rear = (rear + 1) % queue.length;
            queue[rear] = value;
            size++;
        } else {
            throw new RuntimeException("Queue overflow");
        }
    }

    public int dequeue() {
        if (size > 0) {
            int value = queue[front];
            front = (front + 1) % queue.length;
            size--;
            return value;
        } else {
            throw new RuntimeException("Queue underflow");
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
