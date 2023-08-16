package termination;

import org.w3c.dom.Element;

public class Termination {

    private Integer ticks;
    private Integer seconds;

    public static Termination parse(Element terminationElement) {
        Element ticksElement = (Element) terminationElement.getElementsByTagName("PRD-by-ticks").item(0);
        Integer ticks = ticksElement != null ? Integer.parseInt(ticksElement.getAttribute("count")) : -1;

        Element secondsElement = (Element) terminationElement.getElementsByTagName("PRD-by-second").item(0);
        Integer seconds = secondsElement != null ? Integer.parseInt(secondsElement.getAttribute("count")) : -1;

        return new Termination(ticks, seconds);
    }

    public Termination(Integer ticks, Integer seconds) {
        this.ticks = ticks;
        this.seconds = seconds;
    }

    boolean shouldTerminate(Integer currentTicks, Integer currentSeconds) {
        return (ticks > 0 && currentTicks >= ticks) || (seconds > 0 && currentSeconds >= seconds);
    }
}
