package definition.property.api;

public interface PropertyDefinition<T> {
    String getName();
    PropertyType getType();
    T generateValue();
    void setValue(T value);
    T getValue();
}
