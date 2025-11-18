package io.github.leobeaumont.Edges;

import io.github.leobeaumont.Nodes.Place;
import io.github.leobeaumont.Nodes.Transition;

/**
 * Represents an abstract incoming edge connecting a {@link Place} to a {@link Transition}.
 * <p>
 * {@code EdgeIn} defines the behavior common to all edges directed from a place to
 * a transition in a Petri net. It is abstract because subclasses must specify how
 * the edge interacts with tokens when activated.
 * </p>
 */
public abstract class EdgeIn extends Edge {

    private Place origin;
    private Transition arrival;

    /**
     * Constructs a new {@code EdgeIn} connecting a {@link Place} to a {@link Transition}.
     *
     * @param origin  the origin {@link Place} of the edge
     * @param arrival the arrival {@link Transition} of the edge
     */
    public EdgeIn(Place origin, Transition arrival) {
        this.setOrigin(origin);
        this.setArrival(arrival);
        
    }

    /**
     * Creates an uninitialized {@code EdgeIn} instance.
     */
    public EdgeIn() {
    }

    /**
     * Determines whether this edge can be activated.
     * <p>
     * Concrete subclasses must define the conditions under which the edge
     * can trigger its connected {@link Transition}.
     * </p>
     *
     * @return {@code true} if the edge can be activated; {@code false} otherwise
     */
    public abstract boolean isActivable();

    /**
     * Returns the origin {@link Place} of this edge.
     *
     * @return the origin {@link Place}
     */
    public Place getOrigin() {
        return this.origin;
    }

    /**
     * Sets the origin {@link Place} of this edge.
     *
     * @param origin the {@link Place} to set as the origin
     */
    public void setOrigin(Place origin) {
        this.origin = origin;
    }

    /**
     * Returns the arrival {@link Transition} of this edge.
     *
     * @return the arrival {@link Transition}
     */
    public Transition getArrival() {
        return this.arrival;
    }

    /**
     * Sets the arrival {@link Transition} of this edge.
     * <p>
     * This method automatically updates the connections of both the
     * previous and new {@link Transition} instances to maintain consistency.
     * </p>
     *
     * @param arrival the {@link Transition} to set as the arrival
     */
    public void setArrival(Transition arrival) {
        // Inform previous arrival it lost an EdgeIn
        if (this.arrival != null) {
            this.arrival.removeEdgeIn(this);
        }

        this.arrival = arrival;

        // Inform new arrival it has a new EdgeIn
        arrival.newEdgeIn(this);
    }
}
