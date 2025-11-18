package org.pneditor.petrinet.models.BEAUMONT_CHOUKI;

import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Nodes.Place;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.Nodes.Transition;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.PetriNET.PetriNet1;
import org.pneditor.petrinet.models.BEAUMONT_CHOUKI.PetriNET.PetriNet2;    

/**
 * Entry point for simulating a simple Petri net.
 * <p>
 * The {@code Main} class demonstrates the creation and simulation of a basic Petri net
 * consisting of {@link Place} and {@link Transition} objects connected via edges.
 * It shows how tokens move through places according to transition activations.
 * </p>
 * <p>
 * This example performs the following steps:
 * <ol>
 *   <li>Creates a new {@link PetriNet1} instance.</li>
 *   <li>Adds {@link Place} objects with initial tokens.</li>
 *   <li>Adds {@link Transition} objects.</li>
 *   <li>Connects places and transitions using edges.</li>
 *   <li>Displays the initial state of all places.</li>
 *   <li>Simulates a sequence of transition activations and prints the state after each step.</li>
 * </ol>
 * </p>
 * <p>
 * This class serves as a simple demonstration and does not implement interactive or complex
 * Petri net scenarios. Subclasses or alternative entry points can be created for more
 * advanced simulations.
 * </p>
 */
public final class Main {

    private Main() {

    }

    /**
     * The main entry point of the application.
     * <p>
     * This method initializes a simple Petri net with two places and one transition,
     * connects them with edges, and simulates three steps, printing the number of tokens
     * in each place after each step. It also checks whether the transition can fire before
     * each simulation step.
     * </p>
     *
     * @param args command-line arguments (not used in this example)
     */
    public static void main(String[] args) {
        final int simulationSteps = 10;

        // Create a new PetriNet
        PetriNet2 net = new PetriNet2();
        PetriNet1 net2 = new PetriNet1();

        // Add places with initial tokens
        Place p1 = new Place(2);  // Place with 2 tokens
        Place p2 = new Place(0);  // Place empty initially

        Place p3 = new Place(0);  // Place empty initially
        Place p4 = new Place(3);  // Place empty initially

        net.getPlaces().add(p1);
        net.getPlaces().add(p2);

        net2.getPlaces().add(p3);
        net2.getPlaces().add(p4);

        // Add a transition
        Transition t1 = new Transition();
        Transition t2 = new Transition();

        Transition t3 = new Transition();
        Transition t4 = new Transition();
        
        net.getTransitions().add(t1);
        //net.getTransitions().add(t2);

        net2.getTransitions().add(t3);
        net2.getTransitions().add(t4);

        // Connect places and transition with edges
        net.addEdge(1, p1, t1); // consumes 1 token from p1
        net.addEdge(1, t1, p2); // produces 1 token to p2
        //net.addEdgeEmpty(p1, t1); // inhibitor edge from p1 to t1
        
        net2.addEdge(1, p4, t3); // consumes 1 token from p4
        net2.addEdge(1, t3, p3); // produces 1 token
        net2.addEdgeZero(p3, t4); // zero-type edge from p1 to t1

        // Display initial state
        System.out.println("Initial tokens:");
        System.out.println("Place p1: " + p1.getNbTokens());
        System.out.println("Place p2: " + p2.getNbTokens());

        // Simulate 3 steps
        
        for (int i = 1; i <= simulationSteps; ++i) {
           if (t1.isDrawable()) {
                net.stepSimulation(t1);
                System.out.println("\nAfter step " + i + ":");
                System.out.println("Place p1: " + p1.getNbTokens());
                System.out.println("Place p2: " + p2.getNbTokens());
            } else {
                System.out.println("\nStep " + i + ": Transition cannot draw.");
                break;
           }
        }
        
        net.launchSimulation(simulationSteps);
        System.out.println("Place p1: " + p1.getNbTokens());
        System.out.println("Place p2: " + p2.getNbTokens());

        System.out.println(net2.drawable().size());

        net2.launchSimulation(simulationSteps);
        System.out.println("Place p3: " + p3.getNbTokens());
        System.out.println("Place p4: " + p4.getNbTokens());
    }
}
