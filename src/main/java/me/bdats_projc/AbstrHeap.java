package me.bdats_projc;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class AbstrHeap<T>
{
    public enum Priority
    {NAME, POPULATION}

    private final ArrayList<T> heap;
    private Comparator<T> comparator;

    public AbstrHeap(Comparator<T> comparator)
    {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    public boolean isEmpty()
    {
        return heap.isEmpty();
    }

    public void destroy()
    {
        this.heap.clear();
    }

    public T removeMax()
    {
        if (this.isEmpty()) throw new IllegalStateException("Heap is empty");
        T max = this.heap.getFirst();
        T last = this.heap.removeLast();
        if (!this.isEmpty())
        {
            this.heap.set(0, last);
            heapifyDown();
        }
        return max;
    }

    public T getMax()
    {
        if (this.isEmpty()) throw new IllegalStateException("Heap is empty");
        return this.heap.getFirst();
    }

    public void add(T item)
    {
        this.heap.add(item);
        heapifyUP(this.heap.size()-1);
    }

    public void build(ArrayList<T> items)
    {
        for(T item : items)
            this.add(item);
    }

    public void setPriority(Comparator<T> newComparator)
    {
        this.comparator = newComparator;
        rebuildHeap();
    }

    public void rebuildHeap()
    {
        ArrayList<T> temp = new ArrayList<>(heap);
        this.heap.clear();
        for (T item : temp)
        {
            this.add(item);
        }
    }

    private void heapifyUP(int index)
    {
        int parent = (index - 1) / 2;
        while (index > 0 && comparator.compare(this.heap.get(index), this.heap.get(parent)) < 0)
        {
            swap(index, parent);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    private void heapifyDown() {
        int index = 0;
        int leftChild, rightChild, smallerChild;

        while ((leftChild = 2 * index + 1) < this.heap.size()) {
            rightChild = leftChild + 1;
            smallerChild = (rightChild < this.heap.size() &&
                    comparator.compare(this.heap.get(rightChild), this.heap.get(leftChild)) < 0)
                    ? rightChild : leftChild;

            if (comparator.compare(this.heap.get(index), this.heap.get(smallerChild)) <= 0) {
                break;
            }

            swap(index, smallerChild);
            index = smallerChild;
        }
    }

    private void swap(int index1, int index2)
    {
        T temp = this.heap.get(index1);
        this.heap.set(index1, this.heap.get(index2));
        this.heap.set(index2, temp);
    }

    public ArrayList<T> getAllItems() {
        return new ArrayList<>(heap);
    }
}
