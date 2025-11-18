package io.github.leobeaumont;

/**
 * Represents an abstract outgoing edge connecting a {@link Transition} to a {@link Place}.
 * <p>
 * {@code EdgeOut} defines the behavior common to all edges directed from a transition
 * to a place in a Petri net. It is abstract because subclasses must specify how
 * the edge interacts with tokens when activated.
 * </p>
 */
public abstract class EdgeOut extends Edge {

    private Transition origin;
    private Place arrival;

    /**
     * Constructs a new {@code EdgeOut} connecting a {@link Transition} to a {@link Place}.
     *
     * @param origin  the origin {@link Transition} of the edge
     * @param arrival the arrival {@link Place} of the edge
     */
    public EdgeOut(Transition origin, Place arrival) {
        this.setOrigin(origin);
        this.setArrival(arrival);
    }

    /**
     * Creates an uninitialized {@code EdgeOut} instance.
     */
    public EdgeOut() {
    }

    /**
     * Returns the origin {@link Transition} of this edge.
     *
     * @return the origin {@link Transition}
     */
    public Transition getOrigin() {
        return this.origin;
    }

    /**
     * Sets the origin {@link Transition} of this edge.
     * <p>
     * This method automatically updates the connections of both the
     * previous and new {@link Transition} instances to maintain consistency.
     * </p>
     *
     * @param origin the {@link Transition} to set as the origin
     */
    public void setOrigin(Transition origin) {
        // Inform previous origin it lost an EdgeOut
        if (this.origin != null) {
            this.origin.removeEdgeOut(this);
        }

        this.origin = origin;

        // Inform new origin it has a new EdgeOut
        origin.newEdgeOut(this);
    }

    /**
     * Returns the arrival {@link Place} of this edge.
     *
     * @return the arrival {@link Place}
     */
    public Place getArrival() {
        return this.arrival;
    }

    /**
     * Sets the arrival {@link Place} of this edge.
     *
     * @param arrival the {@link Place} to set as the arrival
     */
    public void setArrival(Place arrival) {
        this.arrival = arrival;
    }
}
