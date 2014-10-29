/*******************************************************************************
 * $Id: DefaultListModelEx.java,v 1.1 2012/06/15 21:01:11 uid57051 Exp $
 */
package edu.njit.cs.saboc.blu.core.utils.filterable.list;

import javax.swing.DefaultListModel;
import java.util.List;
import java.util.AbstractList;
import java.util.Collection;
import java.util.ListIterator;
import java.util.Iterator;

/**
 * A simple extention to the DefaultListModel.  Very handy.
 * @see <a href>http://www.javalobby.org/java/forums/t94074.html</a>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class DefaultListModelEx extends DefaultListModel implements List {
    private Delegate m_delegate = new Delegate();

    public DefaultListModelEx() {
        super();
    }

    public DefaultListModelEx(Collection c) {
        this();
        addAll(c);
    }

    @Override
    public boolean add(Object o) {
        return m_delegate.add(o);
    }

    @Override
    public boolean remove(Object o) {
        return m_delegate.remove(o);
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return m_delegate.addAll(index, c);
    }

    @Override
    public boolean addAll(Collection c) {
        return m_delegate.addAll(c);
    }

    @Override
    public boolean containsAll(Collection c) {
        return m_delegate.containsAll(c);
    }

    @Override
    public boolean removeAll(Collection c) {
        return m_delegate.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection c) {
        return m_delegate.retainAll(c);
    }

    @Override
    public Iterator iterator() {
        return m_delegate.iterator();
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return m_delegate.subList(fromIndex, toIndex);
    }

    @Override
    public ListIterator listIterator() {
        return m_delegate.listIterator();
    }

    @Override
    public ListIterator listIterator(int index) {
        return m_delegate.listIterator(index);
    }

    @Override
    public Object[] toArray(Object a[]) {
        return m_delegate.toArray(a);
    }

    /**
     * This class extends AbstractList so we get all the functionality of iterators and such for free.
     */
    private class Delegate extends AbstractList {
        public Delegate() {
            super();
        }

        @Override
        public Object get(int index) {
            return DefaultListModelEx.super.get(index);
        }

        @Override
        public int size() {
            return DefaultListModelEx.super.size();
        }

        @Override
        public Object set(int index, Object element) {
            return DefaultListModelEx.super.set(index, element);
        }

        @Override
        public void add(int index, Object element) {
            DefaultListModelEx.super.add(index, element);
        }

        @Override
        public Object remove(int index) {
            return DefaultListModelEx.super.remove(index);
        }
    }
}
