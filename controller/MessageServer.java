package controller;

import model.Message;

import java.util.*;
import java.util.function.Consumer;

//This is simulated message server

public class MessageServer implements Iterable<Message>{

    private Map<Integer,List<Message>> messages;
    private List<Message> selected;
    public MessageServer() {
        selected = new ArrayList<Message>();
        messages = new TreeMap<Integer,List<Message>>();

        List<Message> list = new ArrayList<Message>();
        list.add(new Message("The cat is missing", "Can you see Feelex anywhere?"));
        list.add(new Message("See you later", "Are we still meeting in the pub"));
        messages.put(0,list);

        list = new ArrayList<Message>();
        list.add(new Message("How about dinner later?", "Are you doing anything later for you?"));
        messages.put(1,list);

    }
    public void setSelectedServers(Set<Integer> isd){

        selected.clear();;

        for (Integer id: isd) {
            if(messages.containsKey(id)){
                selected.addAll(messages.get(id));
            }
        }
    }
    public int getMessageCount(){
        return selected.size();
    }

    @Override
    public Iterator<Message> iterator() {
        return new MessageItarator(selected);
    }

    @Override
    public void forEach(Consumer<? super Message> action) {

    }

    @Override
    public Spliterator<Message> spliterator() {
        return null;
    }
}

class MessageItarator implements Iterator {

    private Iterator<Message> iterator;
    public MessageItarator(List<Message> messages){
            iterator = messages.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Object next() {
        return iterator.next();
    }

    @Override
    public void remove() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        iterator.remove();
    }

    @Override
    public void forEachRemaining(Consumer action) {

    }
}