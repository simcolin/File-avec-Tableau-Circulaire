package implemFile;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class FileTableauCirculaire implements Queue<Integer> {

    final int MAX = 1000;
    final int[] tab = new int[MAX];
    int tete = 0;
    int longueur = 0;

    @Override
    public int size() {
        return longueur;
    }

    @Override
    public boolean isEmpty() { return longueur == 0; }

    @Override
    public boolean contains(Object o) {
        if(o == null)
            throw new NullPointerException();
        if(o instanceof Integer)
        {

            int i = tete;
            boolean trouve = false;
            while(i < tete + longueur && !trouve){
                if(tab[i%MAX] == (int)o)
                    trouve = true;
                i++;
            }
            return trouve;
        }
        else
            throw new ClassCastException();
    }

    @Override
    public Iterator<Integer> iterator() {
        Iterator<Integer> it = new Iterator<Integer>() {
            int pos = tete;
            @Override
            public boolean hasNext() {
                return (pos%MAX < (tete+longueur)%MAX);
            }

            @Override
            public Integer next() {
                int temp = tab[pos%MAX];
                pos++;
                return temp;
            }
        };
        return it;
    }

    @Override
    public Object[] toArray() {
        final Object[] temp = new Object[longueur];
        for(int i = tete ; i < tete+longueur ; i++)
            temp[i-tete] = tab[i%MAX];
        return temp;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if(a == null)
            throw new NullPointerException();
        if(!( a instanceof Integer[]))
            throw new ArrayStoreException();
        Object[] temp = this.toArray();
        Object[] result = new Object[temp.length];
        if (a.length < longueur)
            return (T[])temp;
        else
            for(int i = 0 ; i < temp.length ; i++) {
                if (i > longueur)
                    result[i] = null;
                else
                    result[i] = tab[(i + tete) % MAX];
            }
            return (T[])result;

    }

    @Override
    public boolean remove(Object o) {
        if(o == null)
            throw new NullPointerException();
        if(o instanceof Integer)
        {
            int i = tete;
            boolean trouve = false;
            while(i < tete + longueur && !trouve){
                if(tab[i] == (int)o)
                    trouve = true;
                i++;

                if(trouve) {
                }
                if(isEmpty()){
                    throw new NoSuchElementException();
                }
                else
                {
                    for(int j = i ; j < tete+longueur ; j++) {
                        tab[j%MAX] = tab[(j+1)%MAX];
                    }
                    longueur--;
                }
                return true;
            }
            return false;
        }
        else
            throw new ClassCastException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean trouve = true;
        Object[] o = c.toArray();
        int i = 0;

        while(trouve && i < o.length) {
            if(o[i] == null)
                throw new NullPointerException();
            else if(o[i] instanceof Integer) {
                if(!this.contains(o[i]))
                    trouve = false;
                i++;
            }
            else
                throw new ClassCastException();
        }
        return trouve;
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        boolean trouve = true;
        Object[] o = c.toArray();
        int i = 0;

        while(trouve && i < o.length) {
            if(o[i] == null)
                throw new NullPointerException();
            else if(o[i] instanceof Integer) {
                if(!this.add((Integer) o[i]))
                    trouve = false;
                i++;
            }
            else
                throw new ClassCastException();
        }
        return trouve;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean done = true;
        int buffer = 0;
        Object[] o = c.toArray();
        int i = 0;

        while(done && i < o.length) {
            buffer = 0;
            if(o[i] == null)
                throw new NullPointerException();
            else if(o[i] instanceof Integer) {
                for( int j = tete ; j < tete+longueur ; j++ ) {
                    if (tab[(j + buffer)%MAX] == (Integer) o[i]) {
                        buffer++;
                    }
                    if (buffer != 0)
                        tab[j%MAX] = tab[(j+buffer)%MAX];
                }
                i++;
            }
            else
                throw new ClassCastException();
            longueur -= buffer;
        }
        return done;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for(Object temp : c) {
            if(temp == null)
                throw new NullPointerException();
            if (!(temp instanceof Integer))
                throw new ClassCastException();
        }

        boolean done = true;
        int buffer = 0;
        Object[] o = c.toArray();
        int j = tete;

        while( j+buffer < tete+longueur ) {
            boolean trouve = false;
            int i = 0;
            while( !trouve && i < o.length ) {
                if (tab[(j + buffer)%MAX] == (Integer)o[i])
                    trouve = true;
                i++;
            }
            if(!trouve)
                buffer++;
            else
                j++;

            if (buffer != 0)
                tab[j%MAX] = tab[(j+buffer)%MAX];
        }
        longueur -= buffer;
        return done;
    }

    @Override
    public void clear() {
        tete = (tete + longueur)%MAX;
        longueur = 0;
    }

    @Override
    public boolean add(Integer e) {
        if(e == null)
            throw new NullPointerException();
        if(longueur == MAX){
            throw new IllegalStateException();
        }
        else{
            tab[(tete + longueur)%MAX] = e;
            longueur++;
            return true;
        }
    }

    @Override
    public boolean offer(Integer e) {
        if(e == null)
            throw new NullPointerException();
        if(longueur == MAX){
            throw new IllegalStateException();
        }
        else{
            tab[(tete + longueur)%MAX] = e;
            longueur++;
            return true;
        }
    }

    @Override
    public Integer remove() {
        int temp = 0;
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        else
        {
            temp = tab[tete];
            tete = (tete + 1)%MAX;
            longueur--;
        }
        return temp;
    }

    @Override
    public Integer poll() {
        int temp;
        if(isEmpty())
            return null;
        else{
            temp = tab[tete];
            tete++;
        }
        return temp;
    }

    @Override
    public Integer element() {
        if(isEmpty())
            throw new NoSuchElementException();
        else
            return tab[tete];
    }

    @Override
    public Integer peek() {
        if(isEmpty())
            return null;
        else
            return tab[tete];
    }

}
