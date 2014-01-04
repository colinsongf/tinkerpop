package com.tinkerpop.gremlin.pipes.util;

import com.tinkerpop.gremlin.pipes.Pipe;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class Path {

    protected ArrayList<String> names = new ArrayList<>();
    protected ArrayList<Object> objects = new ArrayList<>();

    public Path(final Object... namesObjects) {
        // TODO: modulo 2
        for (int i = 0; i < namesObjects.length; i = i + 2) {
            names.add((String) namesObjects[i]);
            objects.add(namesObjects[i + 1]);
        }
    }


    public int size() {
        return this.objects.size();
    }

    public void add(final String name, final Object object) {
        this.names.add(name);
        this.objects.add(object);
    }

    public void add(final Path path) {
        this.names.addAll(path.names);
        this.objects.addAll(path.objects);
    }

    public <T> T get(final String name) {
        for (int i = 0; i < this.names.size(); i++) {
            if (this.names.get(i).equals(name))
                return (T) this.objects.get(i);
        }
        throw new IllegalArgumentException("The named step does not exist: " + name);
    }

    public List<String> getNamedSteps() {
        return this.names.stream().filter(s -> !s.equals(Pipe.NONE)).collect(Collectors.toList());
    }

    public boolean isSimple() {
        return new LinkedHashSet<>(this.objects).size() == this.objects.size();
    }

    public void forEach(final BiConsumer<String, Object> consumer) {
        for (int i = 0; i < this.size(); i++) {
            consumer.accept(this.names.get(i), this.objects.get(i));
        }
    }

    // TODO: iterator() -> Iterator<Pair<String,Object>

    public String toString() {
        return this.objects.toString();
    }
}
