package io.github.leobeaumont;

import java.util.List;

/**
 * Defines the contract for a Petri net simulation.
 * <p>
 * This interface specifies all necessary methods for managing and simulating
 * the behavior of a Petri net, including manipulation of places, transitions, and edges.
 * </p>
 */
public interface IPretriNet {

    /**
     * Executes one simulation step for the specified {@link Transition}.
     *
     * @param transition the transition to activate during the simulation step.
     */
    void stepSimulation(Transition transition);

    /**
     * Adds a new {@link Place} to the Petri net with a specified number of tokens.
     *
     * @param nbTokens the initial number of tokens to assign to the place.
     */
    void addPlace(int nbTokens);

    /**
     * Adds a new incoming {@link Edge} from a {@link Place} to a {@link Transition}.
     *
     * @param weight  the weight of the edge (number of tokens transferred).
     * @param origin  the origin place.
     * @param arrival the arrival transition.
     */
    void addEdge(int weight, Place origin, Transition arrival);

    /**
     * Adds a new outgoing {@link Edge} from a {@link Transition} to a {@link Place}.
     *
     * @param weight  the weight of the edge (number of tokens transferred).
     * @param origin  the origin transition.
     * @param arrival the arrival place.
     */
    void addEdge(int weight, Transition origin, Place arrival);

    /**
     * Adds a new {@link Transition} to the Petri net.
     */
    void addTransition();

    /**
     * Removes a {@link Place} from the Petri net.
     *
     * @param place the place to remove.
     */
    void removePlace(Place place);

    /**
     * Removes an {@link Edge} from the Petri net.
     *
     * @param edge the edge to remove.
     */
    void removeEdge(Edge edge);

    /**
     * Removes a {@link Transition} from the Petri net.
     *
     * @param transition the transition to remove.
     */
    void removeTransition(Transition transition);

    /**
     * Sets the number of tokens in a specific {@link Place}.
     *
     * @param place    the place whose tokens will be updated.
     * @param nbTokens the new number of tokens to assign.
     */
    void setTokens(Place place, int nbTokens);

    /**
     * Sets the weight of a specific {@link Edge}.
     *
     * @param edge   the edge whose weight will be updated.
     * @param weight the new weight to assign.
     */
    void setWeight(Edge edge, int weight);

    /**
     * Returns the list of {@link Transition}s that can currently fire.
     *
     * @return a list of firable transitions.
     */
    List<Transition> drawable();

    /**
     * Launches the Petri net simulation for a given number of steps.
     *
     * @param steps the number of simulation steps to execute.
     */
    void launchSimulation(int steps);
}
