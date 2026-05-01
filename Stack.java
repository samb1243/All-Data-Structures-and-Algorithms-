public class Stack {
    private int[] stack;
    private int top;

    public Stack(int capacity) {
        stack = new int[capacity];
        top = -1;
    }

    public void push(int value) {
        if (top < stack.length - 1) {
            stack[++top] = value;
        } else {
            throw new RuntimeException("Stack overflow");
        }
    }

    public int pop() {
        if (top >= 0) {
            return stack[top--];
        } else {
            throw new RuntimeException("Stack underflow");
        }
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }
}
