package rule;

import java.util.Random;

public class ActivationByProbability implements Activation{
    private float probability;

    public ActivationByProbability(float probability){
        this.probability = probability;
    }

    @Override
    public boolean isActive(int tickNumber) {
        Random random = new Random();
        return random.nextFloat() < this.probability;
    }
}