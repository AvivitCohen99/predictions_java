package definition.value.generator.random.impl.numeric;

public class RandomFloatGenerator extends AbstractNumericRandomGenerator<Float> {

    public RandomFloatGenerator(Float from, Float to) {
        super(from, to);
    }

    @Override
    public Float generateValue() {
        if(from == -1 || to == -1){
            return random.nextFloat();
        }
        return from + random.nextFloat() * (to - from);
    }
}