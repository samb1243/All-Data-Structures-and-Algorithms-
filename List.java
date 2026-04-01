import java.util.NoSuchElementException;

public class List<T> {
    private final T[] elements;
    private int size;
    private int cursor;

    @SuppressWarnings("unchecked")
    public List(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0.");
        }
        this.elements = (T[]) new Object[capacity];
        this.size = 0;
        this.cursor = 0;
    }

    // isEmpty(): returns true if the list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // isFull(): returns true if the list is full
    public boolean isFull() {
        return size == elements.length;
    }

    // insertEnd(v): insert value at the end
    public void insertEnd(T value) {
        if (isFull()) {
            throw new IllegalStateException("List is full.");
        }
        elements[size] = value;
        size++;
    }

    // insertBeginning(v): insert value at the beginning
    public void insertBeginning(T value) {
        if (isFull()) {
            throw new IllegalStateException("List is full.");
        }

        for (int i = size; i > 0; i--) {
            elements[i] = elements[i - 1];
        }

        elements[0] = value;
        size++;
    }

    // delete(v): deletes first occurrence of value
    public boolean delete(T value) {
        for (int i = 0; i < size; i++) {
            if ((elements[i] == null && value == null) ||
                (elements[i] != null && elements[i].equals(value))) {

                for (int j = i; j < size - 1; j++) {
                    elements[j] = elements[j + 1];
                }

                elements[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    // deleteAll(v): deletes all occurrences of value
    public void deleteAll(T value) {
        int i = 0;
        while (i < size) {
            if ((elements[i] == null && value == null) ||
                (elements[i] != null && elements[i].equals(value))) {

                for (int j = i; j < size - 1; j++) {
                    elements[j] = elements[j + 1];
                }

                elements[size - 1] = null;
                size--;
            } else {
                i++;
            }
        }
    }

    // reset(): deletes all elements in the list
    public void reset() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
        cursor = 0;
    }

    // Cursor operations for sequential traversal
    public void resetCursor() {
        cursor = 0;
    }

    public boolean hasNext() {
        return cursor < size;
    }

    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in the list.");
        }
        return elements[cursor++];
    }

    // Optional helper
    public int size() {
        return size;
    }

    // Optional helper for printing
    public void printList() {
        System.out.print("[");
        for (int i = 0; i < size; i++) {
            System.out.print(elements[i]);
            if (i < size - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    // Example usage
    public static void main(String[] args) {
        List<Integer> list = new List<>(10);

        list.insertEnd(5);
        list.insertEnd(10);
        list.insertBeginning(1);
        list.insertEnd(5);

        System.out.print("List: ");
        list.printList();

        list.delete(5);
        System.out.print("After delete(5): ");
        list.printList();

        list.deleteAll(5);
        System.out.print("After deleteAll(5): ");
        list.printList();

        System.out.println("isEmpty: " + list.isEmpty());
        System.out.println("isFull: " + list.isFull());

        System.out.print("Traverse using cursor: ");
        list.resetCursor();
        while (list.hasNext()) {
            System.out.print(list.next() + " ");
        }
        System.out.println();

        list.reset();
        System.out.print("After reset(): ");
        list.printList();
    }
}