package io.github.leobeaumont;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a transition in a Petri net, responsible for moving tokens
 * from input places to output places via edges.
 */
public class Transition {

    private List<EdgeIn> edgesIn;
    private List<EdgeOut> edgesOut;

    /**
     * Creates a new {@code Transition} with empty input and output edge lists.
     */
    public Transition() {
        // ArrayList is the best choice for iteration speed and memory usage
        edgesIn = new ArrayList<EdgeIn>();
        edgesOut = new ArrayList<EdgeOut>();
    }

    /**
     * Determines whether this transition can be drawn (activated).
     * <p>
     * A transition can be drawn if all its incoming edges are activable.
     * </p>
     *
     * @return {@code true} if the transition can be drawn; {@code false} otherwise
     */
    public boolean isDrawable() {
        // Create an iterator for incoming edges
        List<EdgeIn> edgesToTest = this.getEdgesIn();
        Iterator<EdgeIn> edgeIterator = edgesToTest.iterator();

        // If any incoming edge can't be activated, the transition can't be drawn
        while (edgeIterator.hasNext()) {
            EdgeIn edge = edgeIterator.next();
            if (!edge.isActivable()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Draws (activates) the transition.
     * <p>
     * This method calls {@link Edge#activate()} on all connected incoming and outgoing edges.
     * </p>
     */
    public void draw() {
        // Create iterators for all edges
        Iterator<EdgeIn> edgesInIterator = this.getEdgesIn().iterator();
        Iterator<EdgeOut> edgesOutIterator = this.getEdgesOut().iterator();

        // Activate all edges
        while (edgesInIterator.hasNext()) {
            EdgeIn edge = edgesInIterator.next();
            edge.activate();
        }

        while (edgesOutIterator.hasNext()) {
            EdgeOut edge = edgesOutIterator.next();
            edge.activate();
        }
    }

    /**
     * Adds an incoming edge to this transition.
     *
     * @param edge the {@link EdgeIn} to add
     */
    public void newEdgeIn(EdgeIn edge) {
        this.getEdgesIn().add(edge);
    }

    /**
     * Removes an incoming edge from this transition.
     *
     * @param edge the {@link EdgeIn} to remove
     */
    public void removeEdgeIn(EdgeIn edge) {
        this.getEdgesIn().remove(edge);
    }

    /**
     * Adds an outgoing edge to this transition.
     *
     * @param edge the {@link EdgeOut} to add
     */
    public void newEdgeOut(EdgeOut edge) {
        this.getEdgesOut().add(edge);
    }

    /**
     * Removes an outgoing edge from this transition.
     *
     * @param edge the {@link EdgeOut} to remove
     */
    public void removeEdgeOut(EdgeOut edge) {
        this.getEdgesOut().remove(edge);
    }

    /**
     * Returns the list of incoming edges.
     *
     * @return the list of {@link EdgeIn}
     */
    public List<EdgeIn> getEdgesIn() {
        return edgesIn;
    }

    /**
     * Sets the list of incoming edges.
     *
     * @param edgesIn the list of {@link EdgeIn} to set
     */
    public void setEdgesIn(List<EdgeIn> edgesIn) {
        this.edgesIn = edgesIn;
    }

    /**
     * Returns the list of outgoing edges.
     *
     * @return the list of {@link EdgeOut}
     */
    public List<EdgeOut> getEdgesOut() {
        return edgesOut;
    }

    /**
     * Sets the list of outgoing edges.
     *
     * @param edgesOut the list of {@link EdgeOut} to set
     */
    public void setEdgesOut(List<EdgeOut> edgesOut) {
        this.edgesOut = edgesOut;
    }
}
