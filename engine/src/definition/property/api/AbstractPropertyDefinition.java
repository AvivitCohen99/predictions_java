package definition.property.api;

import definition.value.generator.api.ValueGenerator;

public abstract class AbstractPropertyDefinition<T> implements PropertyDefinition<T> {

    private final String name;
    private final PropertyType propertyType;
    private final ValueGenerator<T> valueGenerator;
    private T value;

    public AbstractPropertyDefinition(String name, PropertyType propertyType, ValueGenerator<T> valueGenerator) {
        this.name = name;
        this.propertyType = propertyType;
        this.valueGenerator = valueGenerator;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PropertyType getType() {
        return propertyType;
    }

    @Override
    public T generateValue() {
        return valueGenerator.generateValue();
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setValue(T value){
        this.value = value;
    }

}