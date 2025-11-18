package io.github.leobeaumont.PetriNET;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import io.github.leobeaumont.Edges.Edge;
import io.github.leobeaumont.Edges.EdgeEmpty;
import io.github.leobeaumont.Edges.EdgeIn;
import io.github.leobeaumont.Edges.EdgeOut;
import io.github.leobeaumont.Edges.EdgeZero;
import io.github.leobeaumont.Edges.WeightedEdgeIn;
import io.github.leobeaumont.Edges.WeightedEdgeOut;
import io.github.leobeaumont.Nodes.Node;
import io.github.leobeaumont.Nodes.Place;
import io.github.leobeaumont.Nodes.Transition;


/**
 * Represents a Petri net system composed of places, transitions, and edges.
 * A Petri net models a distributed system where tokens move between places
 * through transitions according to defined rules.
 *
 * <p>This class implements {@link IPretriNet} and provides functionality to:
 * <ul>
 *   <li>Add or remove places, transitions, and edges</li>
 *   <li>Set and modify weights and token counts</li>
 *   <li>Validate the structure of the Petri net</li>
 *   <li>Execute and simulate transitions</li>
 * </ul>
 *
 * <p>Supported edge types include {@link WeightedEdgeIn}, {@link WeightedEdgeOut},
 * {@link EdgeEmpty}, and {@link EdgeZero}.</p>
 * 
 * @author
 *     leobeaumont
 */
public class PetriNet1 implements IPretriNet {  // This version allows users to add duplicate edges and will notify him at the simulation that the Petri net is not valid.

    private List<Edge> edges;
    private List<Place> places;
    private List<Transition> transitions;

    /**
     * Constructs an empty Petri net.
     * <p>Uses {@link ArrayList} for internal collections since it provides
     * efficient iteration and insertion at the end, which suits typical Petri net usage.</p>
     */
    public PetriNet1() {
        /* ArrayList is faster/memory effficient in this case because:
        - We won't remove elements often from the middle of the list.
        - Most of the time we iterate through the list.
        - When we add an element we can put it at the end of the list. */
        this.edges = new ArrayList<Edge>();
        this.places = new ArrayList<Place>();
        this.transitions = new ArrayList<Transition>();
    }

    /**
     * Executes a single simulation step by firing the given transition.
     *
     * @param transition the transition to fire
     * @throws IllegalArgumentException if the transition cannot currently be drawn
     */
    public void stepSimulation(Transition transition) throws IllegalArgumentException {
        if (transition.isDrawable()) {
            transition.draw();
        } else {
            throw new IllegalArgumentException(
                "PetriNet.stepSimulation(transition) -- The argument transition can't be drawn.");
        }
    }

    /**
     * Adds a new place with the specified number of tokens.
     *
     * @param nbTokens the initial number of tokens in the new place
     */
    public void addPlace(int nbTokens) {
        Place place = new Place(nbTokens);
        this.places.add(place);
    }

    /**
     * Adds a weighted input edge (from a place to a transition) with the specified weight.
     *
     * @param weight  the edge weight
     * @param origin  the place from which the edge originates
     * @param arrival the transition to which the edge connects
     */
    public void addEdge(int weight, Place origin, Transition arrival) {
        WeightedEdgeIn edge = new WeightedEdgeIn(origin, arrival, weight);
        this.edges.add(edge);
    }

    /**
     * Adds a weighted output edge (from a transition to a place) with the specified weight.
     *
     * @param weight  the edge weight
     * @param origin  the transition from which the edge originates
     * @param arrival the place to which the edge connects
     */
    public void addEdge(int weight, Transition origin, Place arrival) {
        WeightedEdgeOut edge = new WeightedEdgeOut(origin, arrival, weight);
        this.edges.add(edge);
    }

    /**
     * Adds an unconnected weighted input edge.
     *
     * @param weight the weight of the edge
     */
    public void addWeightedEdgeIn(int weight) {
        WeightedEdgeIn edge = new WeightedEdgeIn(weight);
        this.edges.add(edge);
    }

    /**
     * Adds an unconnected weighted output edge.
     *
     * @param weight the weight of the edge
     */
    public void addWeightedEdgeOut(int weight) {
        WeightedEdgeOut edge = new WeightedEdgeOut(weight);
        this.edges.add(edge);
    }

    /**
     * Adds an empty (inhibitor) edge from a place to a transition.
     *
     * @param origin  the origin place
     * @param arrival the destination transition
     */
    public void addEdgeEmpty(Place origin, Transition arrival) {
        EdgeEmpty edge = new EdgeEmpty(origin, arrival);
        this.edges.add(edge);
    }

    /**
     * Adds an unconnected empty (inhibitor) edge.
     */
    public void addEdgeEmpty() {
        EdgeEmpty edge = new EdgeEmpty();
        this.edges.add(edge);
    }

    /**
     * Adds a zero-type (test) edge from a place to a transition.
     *
     * @param origin  the origin place
     * @param arrival the destination transition
     */
    public void addEdgeZero(Place origin, Transition arrival) {
        EdgeZero edge = new EdgeZero(origin, arrival);
        this.edges.add(edge);
    }

    /**
     * Adds an unconnected zero-type (test) edge.
     */
    public void addEdgeZero() {
        EdgeZero edge = new EdgeZero();
        this.edges.add(edge);
    }

    /**
     * Adds a new transition to the Petri net.
     */
    public void addTransition() {
        Transition transition = new Transition();
        this.transitions.add(transition);
    }

    /**
     * Removes the specified place from the Petri net.
     *
     * @param place the place to remove
     */
    public void removePlace(Place place) {
        this.places.remove(place);
    }

    /**
     * Removes the specified edge from the Petri net.
     *
     * @param edge the edge to remove
     */
    public void removeEdge(Edge edge) {
        this.edges.remove(edge);
    }

    /**
     * Removes the specified transition from the Petri net.
     *
     * @param transition the transition to remove
     */
    public void removeTransition(Transition transition) {
        this.transitions.remove(transition);
    }

    /**
     * Sets the number of tokens for a given place.
     *
     * @param place    the place whose token count is being modified
     * @param nbTokens the new number of tokens
     */
    public void setTokens(Place place, int nbTokens) {
        place.setNbTokens(nbTokens);
    }

    /**
     * Sets the weight of a given weighted edge.
     *
     * @param edge   the edge to modify
     * @param weight the new weight
     * @throws IllegalArgumentException if the edge is not a {@link WeightedEdgeIn} or {@link WeightedEdgeOut}
     */
    public void setWeight(Edge edge, int weight) throws IllegalArgumentException {
        if (edge instanceof WeightedEdgeIn) {
            WeightedEdgeIn weightedEdge = (WeightedEdgeIn) edge;
            this.setWeight(weightedEdge, weight);
        } else if (edge instanceof WeightedEdgeOut) {
            WeightedEdgeOut weightedEdge = (WeightedEdgeOut) edge;
            this.setWeight(weightedEdge, weight);
        } else {
            throw new IllegalArgumentException(String.format(
                "PetriNet.setWeight(edge, %d) -- The argument edge (%s) is not a weighted edge.",
                weight, edge.getClass().getSimpleName()));
        }
    }

    /**
     * Sets the weight of a weighted input edge.
     *
     * @param edge   the weighted input edge
     * @param weight the new weight
     */
    public void setWeight(WeightedEdgeIn edge, int weight) {
        edge.setWeight(weight);
    }

    /**
     * Sets the weight of a weighted output edge.
     *
     * @param edge   the weighted output edge
     * @param weight the new weight
     */
    public void setWeight(WeightedEdgeOut edge, int weight) {
        edge.setWeight(weight);
    }

    /**
     * Validates the Petri net structure.
     * <ul>
     *   <li>Each transition must have at least one incoming or outgoing edge.</li>
     *   <li>Each edge must have both an origin and an arrival node.</li>
     * </ul>
     *
     * @return {@code true} if the Petri net is valid, {@code false} otherwise
     */
    public boolean isValid() {
        // Two edges can't have the same origin and arrival
        if (this.hasDuplicateEdges()) {
            return false;
        }

        // A transition must have at least one edge connected
        Iterator<Transition> iterTransitions = this.transitions.iterator();
        while (iterTransitions.hasNext()) {
            Transition transition = iterTransitions.next();

            List<EdgeIn> edgesIn = transition.getEdgesIn();
            List<EdgeOut> edgesOut = transition.getEdgesOut();

            if (edgesIn.isEmpty() && edgesOut.isEmpty()) {
                return false;
            }
        }

        // An edge must have an origin and an arrival
        Iterator<Edge> iterEdges = this.edges.iterator();
        while (iterEdges.hasNext()) {
            Edge edge = iterEdges.next();

            boolean hasOrigin = false;
            boolean hasArrival = false;

            if (edge instanceof EdgeIn) {
                EdgeIn eIn = (EdgeIn) edge;
                hasOrigin = eIn.getOrigin() != null;
                hasArrival = eIn.getArrival() != null;
            } else if (edge instanceof EdgeOut) {
                EdgeOut eOut = (EdgeOut) edge;
                hasOrigin = eOut.getOrigin() != null;
                hasArrival = eOut.getArrival() != null;
            } else {
                return false;
            }

            if (!hasOrigin || !hasArrival) {
                return false;
            }
        }

        // If all checks pass
        return true;
    }

    /**
     * Returns a list of all drawable (firable) transitions in the Petri net.
     *
     * @return a list of transitions that can currently be drawn
     */
    public List<Transition> drawable() {
        List<Transition> drawable = new ArrayList<Transition>();
        Iterator<Transition> iterTransitions = this.transitions.iterator();

        while (iterTransitions.hasNext()) {
            Transition transition = iterTransitions.next();
            if (transition.isDrawable()) {
                drawable.add(transition);
            }
        }

        return drawable;
    }

    /**
     * Executes a simulation for a specified number of steps.
     * <p>At each step, a random drawable transition is selected and drawn.</p>
     *
     * @param steps the number of steps to simulate
     */
    public void launchSimulation(int steps) {
        if (!isValid()) {
            throw new IllegalStateException("PetriNet.launchSimulation(steps) -- The Petri net is not valid.");
        }
        for (int i = 0; i < steps; ++i) {
            // Get every drawable transition
            List<Transition> drawable = this.drawable();

            // If no transition can be fired, stop the simulation
            if (drawable.isEmpty()) {
                System.out.println("No drawable transitions available. Stopping simulation.");
                return;
            }

            // Select a drawable transition at random
            Random random = new Random();
            Transition randomTransition = drawable.get(random.nextInt(drawable.size()));

            // Step the simulation
            this.stepSimulation(randomTransition);
        }
    }

    /**
     * Checks whether there exist duplicate edges among input and output edges:
     * <ul>
     *   <li>Two {@link EdgeIn} edges with the same origin {@link Place} and the same arrival {@link Transition}</li>
     *   <li>Two {@link EdgeOut} edges with the same origin {@link Transition} and the same arrival {@link Place}</li>
     * </ul>
     *
     * @return {@code true} if at least one duplicate input or output edge exists,
     *         {@code false} otherwise
     */
    public boolean hasDuplicateEdges() {
        java.util.HashSet<EdgeKey> seenInputs = new java.util.HashSet<>();
        java.util.HashSet<EdgeKey> seenOutputs = new java.util.HashSet<>();

        for (Edge edge : edges) {
            if (edge instanceof EdgeIn) {
                EdgeIn eIn = (EdgeIn) edge;
                EdgeKey key = new EdgeKey(eIn.getOrigin(), eIn.getArrival());

                // If add() returns false, this (origin, arrival) pair already exists for an input edge
                if (!seenInputs.add(key)) {
                    return true;
                }

            } else if (edge instanceof EdgeOut) {
                EdgeOut eOut = (EdgeOut) edge;
                EdgeKey key = new EdgeKey(eOut.getOrigin(), eOut.getArrival());

                // If add() returns false, this (origin, arrival) pair already exists for an output edge
                if (!seenOutputs.add(key)) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * Returns the list of edges in the Petri net.
     *
     * @return a list of {@link Edge} objects representing all edges in the net
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Sets the list of edges in the Petri net.
     *
     * @param edges the new list of {@link Edge} objects to assign
     */
    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    /**
     * Returns the list of places in the Petri net.
     *
     * @return a list of {@link Place} objects representing all places in the net
     */
    public List<Place> getPlaces() {
        return places;
    }

    /**
     * Sets the list of places in the Petri net.
     *
     * @param places the new list of {@link Place} objects to assign
     */
    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    /**
     * Returns the list of transitions in the Petri net.
     *
     * @return a list of {@link Transition} objects representing all transitions in the net
     */
    public List<Transition> getTransitions() {
        return transitions;
    }

    /**
     * Sets the list of transitions in the Petri net.
     *
     * @param transitions the new list of {@link Transition} objects to assign
     */
    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

     /**
     * Internal key representing a pair (origin, arrival) of nodes.
     * <p>
     * This class is used to detect duplicate edges efficiently by storing
     * keys in a {@link HashSet}. Two {@code EdgeKey} instances are considered
     * equal if and only if both their origin and arrival nodes are equal.
     * </p>
     */
    private static final class EdgeKey {

        private final Node origin;
        private final Node arrival;

        /**
         * Creates a new edge key.
         *
         * @param origin  the origin node of the edge
         * @param arrival the arrival node of the edge
         */
        EdgeKey(Node origin, Node arrival) {
            this.origin = origin;
            this.arrival = arrival;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof EdgeKey)) {
                return false;
            }
            EdgeKey k = (EdgeKey) o;
            return origin.equals(k.origin) && arrival.equals(k.arrival);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(origin, arrival);
        }
    }
}
