
public class List<T> {
    private final T[] elements;
    private int size;
    private int cursor;

    @SuppressWarnings("unchecked")
    public List (int capacity){
        if (capacity <= 0){
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.elements = (T[]) new Object[capacity];
        this.size = 0;
        this.cursor = 0;
    }

    //method to check the list is empty 
    public boolean isEmpty(){
        return size == 0;
    }

    //method to check the list is full
    public boolean isFull(){
        return size == elements.length;
    }

    //method to insert a value at the end of the list 
    public void insertEnd(T value){
        if (isFull()){
            throw new IllegalStateException("List is full.");
        }
        elements[size] = value;
        size++;
    }

    //method to insert value at the beginning of the list 
    public void insertBeginning(T value){
        if (isFull()){
            throw new IllegalStateException("List is full.");
        }

        for(int i = size; i > 0; i--){
            elements[i] = elements[i - 1];
        }
        elements[0] = value;
        size++;
    }

    //method to delete first value 

    public boolean delete(T value){
        for (int i = 0; i < size; i++){
            if ((elements[i] == null && value == null) || (elements[i] != null && elements[i].equals(value))){
                for (int j = i; j < size - 1; j++){
                    elements[j] = elements[j + 1];
                }

                elements[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    //method to delete all occurences of value 
    public void deleteAll (T value ){
        int i = 0;
        while (i < size){
            if ((elements[i] == null && value == null) || (elements[i] != null && elements[i].equals(value))) {

                for (int j = i; j < size - 1; j++) {
                    elements[j] = elements[j + 1];
                }

                elements[size - 1] = null;
                size--;
            }else {
                i++;
            }
        }
    }

    //method to reset the list 
    public void reset(){
        for (int i = 0; i < size; i++){
            elements[i] = null;

        }
        size = 0;
        cursor = 0;
    }
    
}
