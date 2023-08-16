package rule;

public class ActivationByTicks implements Activation{
    private int ticksCycle;

    public ActivationByTicks(int ticksToActivate){
        this.ticksCycle = ticksToActivate;
    }

    @Override
    public boolean isActive(int currentTick) {
        return currentTick % this.ticksCycle == 0;
    }
}
