package tproject.postservice.util;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Generic collection to manage and retrieve entities by multiple fields with high performance
 * @param <T> Entity type
 * @param <ID> Primary ID type (usually Long, Integer, String)
 */
public class EntityCollection<T, ID> {
    private final List<T> allEntities;
    private final Map<ID, T> entitiesById;
    private final Map<String, Map<Object, List<T>>> indexedFields = new HashMap<>();
    private final Function<T, ID> idExtractor;

    /**
     * Initialize EntityCollection with a list of entities and an ID extractor function
     *
     * @param entities List of entities
     * @param idExtractor Function to extract ID from entity
     */
    public EntityCollection(List<T> entities, Function<T, ID> idExtractor) {
        this.allEntities = new ArrayList<>(entities);
        this.idExtractor = idExtractor;

        // Create index by ID
        this.entitiesById = entities.stream()
                .collect(Collectors.toMap(idExtractor, entity -> entity));
    }

    /**
     * Add index for a specific field
     *
     * @param fieldName Name of the field to index
     * @param fieldExtractor Function to extract field value from entity
     * @param <F> Data type of the field
     * @return this (for method chaining)
     */
    public <F> EntityCollection<T, ID> addIndex(String fieldName, Function<T, F> fieldExtractor) {
        Map<Object, List<T>> fieldIndex = allEntities.stream()
                .collect(Collectors.groupingBy(entity -> fieldExtractor.apply(entity)));

        indexedFields.put(fieldName, fieldIndex);
        return this;
    }

    /**
     * Get entity by ID
     *
     * @param id ID of the entity to find
     * @return Entity or null if not found
     */
    public T getById(ID id) {
        return entitiesById.get(id);
    }

    /**
     * Get list of entities by value of an indexed field
     *
     * @param fieldName Field name
     * @param value Value to search for
     * @return List of entities or empty list if not found
     */
    public List<T> getByField(String fieldName, Object value) {
        Map<Object, List<T>> fieldIndex = indexedFields.get(fieldName);
        if (fieldIndex == null) {
            throw new IllegalArgumentException("Field '" + fieldName + "' is not indexed. Call addIndex first.");
        }

        return fieldIndex.getOrDefault(value, Collections.emptyList());
    }

    /**
     * Get list of entities that satisfy a custom condition
     *
     * @param condition Condition to be satisfied
     * @return List of entities that satisfy the condition
     */
    public List<T> getByCondition(Predicate<T> condition) {
        return allEntities.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    /**
     * Get all entities
     *
     * @return List of all entities
     */
    public List<T> getAll() {
        return new ArrayList<>(allEntities);
    }

    /**
     * Check if a field has been indexed
     *
     * @param fieldName Field name to check
     * @return true if the field has been indexed
     */
    public boolean isFieldIndexed(String fieldName) {
        return indexedFields.containsKey(fieldName);
    }

    /**
     * Return the number of entities in the collection
     *
     * @return Number of entities
     */
    public int size() {
        return allEntities.size();
    }
}
