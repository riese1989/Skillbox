package double_linked_list;

import java.util.Objects;

public class DoubleLinkedList<T> {
    private ListItem<T> head;
    private ListItem<T> tail;
    private int size;

    public ListItem<T> popHeadElement() {
        ListItem<T> current = head;
        removeHeadElement();
        // TODO
        return current;
    }

    public ListItem<T> popTailElement() {
        // TODO
        ListItem<T> current = tail;
        removeTailElement();
        return current;
    }

    public void removeHeadElement() {
        head = head.next;
        size--;
        // TODO
    }

    public void removeTailElement() {
        tail = tail.prev;
        size--;
        // TODO
    }

    public void addToHead(T data) {
        ListItem<T> current = new ListItem<>(data);
        if (head != null) {
            head.prev = current;
            current.next = head;
        }
        if (tail == null)   {
            tail = current;
        }
        head = current;
        size++;
        // TODO
    }

    public void addToTail(T data) {
        ListItem<T> current = new ListItem<>(data);
        if (tail !=null) {
            tail.next = current;
            current.prev = tail;
        }   else    {
            head = current;
        }
        tail = current;
        size++;
        // TODO
    }

    public int getSize() {
        return size;
    }

    public ListItem<T> getHeadElement() {
        return head;
    }

    public ListItem<T> getTailElement() {
        return tail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleLinkedList<T> that = (DoubleLinkedList<T>) o;
        return Objects.equals(head, that.head) && Objects.equals(tail, that.tail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, tail);
    }

    @Override
    public String toString() {
        if (head == null) {
            return "DoubleLinkedList is empty size = " + size;
        }

        StringBuilder stringBuilder = new StringBuilder(head.toString());
        ListItem<T> item = head;
        while (item.next != null) {
            if (item.next.prev == item) {
                stringBuilder.append("<-");
            }

            stringBuilder.append(" -> ").append(item.next);
            item = item.next;
        }

        return "DoubleLinkedList{size=" + size + "\n" + stringBuilder.toString() + "}";
    }
}