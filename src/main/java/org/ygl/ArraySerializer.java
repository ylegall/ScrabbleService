package org.ygl;

import java.util.Collection;
import java.util.Iterator;

/**
 * Serializes a collection of strings to a json array format
 */
public class ArraySerializer {

    String toJsonArray(Collection<String> sequence) {
        StringBuilder builder = new StringBuilder();
        builder.append('[');

        Iterator<String> iterator = sequence.iterator();
        if (iterator.hasNext()) {
            builder.append("\"")
                    .append(iterator.next())
                    .append("\"");
        }

        while (iterator.hasNext()) {
            builder.append(",\"")
                    .append(iterator.next())
                    .append("\"");
        }

        builder.append(']');
        return builder.toString();
    }
}
