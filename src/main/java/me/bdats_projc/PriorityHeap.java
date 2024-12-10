package me.bdats_projc;

import java.util.Comparator;


public class PriorityHeap extends AbstrHeap<Obec>
{
    public static final Comparator<Obec> BY_NAME = Comparator.comparing(Obec::getName);

    public static final Comparator<Obec> BY_POPULATION = Comparator.comparing(Obec::getPopulation);



    public PriorityHeap(Priority priority, Comparator<Obec> initialComparator)
    {
        super(initialComparator);
    }

    public void setPriority(Priority priority)
    {
        Comparator<Obec> newComparator;
        switch (priority)
        {
            case NAME -> newComparator = BY_NAME;
            case POPULATION -> newComparator = BY_POPULATION;
            default -> throw new IllegalArgumentException("Invalid priority: " + priority);
        }
        super.setPriority(newComparator);
    }

}
