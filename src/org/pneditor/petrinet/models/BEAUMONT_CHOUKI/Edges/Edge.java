package io.github.leobeaumont.Edges;

/**
 * Represents a directed connection between a {@code Place} and a {@code Transition}.
 * <p>
 * This abstract class defines the basic structure of an edge within a Petri net.
 * Concrete subclasses should specify how the edge behaves when activated.
 * </p>
 *
 * <p>
 * Since an {@code Edge} alone cannot participate directly in a simulation,
 * subclasses such as {@code WeightedEdgeIn} or {@code WeightedEdgeOut}
 * must provide specific implementations.
 * </p>
 */
public abstract class Edge {

    /**
     * Activates this edge.
     * <p>
     * The concrete implementation defines the effect of the activation,
     * typically involving the transfer of tokens between connected nodes.
     * </p>
     */
   
    public abstract void activate();
}
