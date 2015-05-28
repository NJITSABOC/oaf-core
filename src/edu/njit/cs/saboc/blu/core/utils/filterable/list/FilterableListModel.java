package edu.njit.cs.saboc.blu.core.utils.filterable.list;

import SnomedShared.Concept;
import java.util.ListIterator;
import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/** 
 * An extention of {@link Vector}&lt;{@link Concept}> that implements {@link
 * javax.swing.ListModel}.  The class can be filtered in such a way that
 * only those Concepts that match the filter will be accessible through the
 * ListModel interface, but all the Concepts will always be accessible through
 * the Vector methods.
 */
public class FilterableListModel extends MonitoredVector<Filterable> implements ListModel {
    protected Vector<Filterable> modelledVector = this;

    private class DelegateListModel extends AbstractListModel {
        @Override
        public int getSize() {
            return FilterableListModel.this.getSize();
        }

        @Override
        public Object getElementAt(int index) {
            return FilterableListModel.this.getElementAt(index);
        }

        @Override
        public void fireContentsChanged(Object source, int index0, int index1) {
            super.fireContentsChanged(source, index0, index1);
        }

        @Override
        public void fireIntervalAdded(Object source, int index0, int index1) {
            super.fireIntervalAdded(source, index0, index1);
        }

        @Override
        public void fireIntervalRemoved(Object source, int index0, int index1) {
            super.fireIntervalRemoved(source, index0, index1);
        }
    }

    private String filter = "";
    
    private DelegateListModel delegateLM = new DelegateListModel();

    public FilterableListModel() {

    }

    @Override
    public int getSize() {
        return modelledVector.size();
    }

    @Override
    public Object getElementAt(int index) {
        return modelledVector.get(index).getFilterText(filter);
    }

    public Filterable getFilterableAtModelIndex(int index) {
        return modelledVector.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        delegateLM.addListDataListener(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        delegateLM.removeListDataListener(l);
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    protected void elementsInserted(int index0, int index1) {
        if(filter.isEmpty()) {
            delegateLM.fireIntervalAdded(this, index0, index1);
        }
        else {
            updateFilter();
        }
    }

    @Override
    protected void elementsRemoved(int index0, int index1) {
        if(filter.isEmpty()) {
            delegateLM.fireIntervalRemoved(this, index0, index1);
        }
        else {
            updateFilter();
        }
    }

    @Override
    protected void elementsUpdated(int index0, int index1) {
        if(filter.isEmpty()) {
            delegateLM.fireContentsChanged(this, index0, index1);
        }
        else {
            updateFilter();
        }
    }

    public void updateFilter() {
        
        if(!filter.isEmpty()) {
            assert modelledVector != this;
            int size = modelledVector.size();
            modelledVector.clear();

            if(size > 0) {
                delegateLM.fireIntervalRemoved(this, 0, size - 1);
            }

            for(Filterable f : this) {
                if(f.containsFilter(filter.toLowerCase())) {
                    modelledVector.add(f);
                }
            }
            
            if(!modelledVector.isEmpty()) {
                delegateLM.fireIntervalAdded(this, 0, modelledVector.size() - 1);
            }
        }
    }

    public void changeFilter(String newFilter) {
        newFilter = newFilter.toLowerCase();
        
        if(newFilter.equals(filter)) {
            return;
        }
        else if(newFilter.isEmpty()) {
            modelledVector = this;
            filter = newFilter;
            delegateLM.fireContentsChanged(this, 0, modelledVector.size() - 1);
        }
        else if(filter.isEmpty() || !newFilter.contains(filter)) {
            modelledVector = new Vector<Filterable>(this);
            filter = newFilter;
            updateFilter();
        }
        else {
            filter = newFilter;
            ListIterator<Filterable> iter = modelledVector.listIterator();
            
            while(iter.hasNext()) {
                String text = iter.next().getInitialText().toLowerCase();
                
                if(!text.contains(filter)) {
                    int index = iter.nextIndex() - 1;
                    iter.remove();
                    delegateLM.fireIntervalRemoved(this, index, index);
                }
                
            }
            
            delegateLM.fireContentsChanged(this, 0, modelledVector.size() - 1);
        }
    }
}
