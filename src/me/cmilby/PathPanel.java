package me.cmilby;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PathPanel extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	
	private List<Node> nodes;
    private List<Edge> edges;

    private int lastNode;
    private int startNode;
    private int endNode;

    private int nextNode;
    private int nextEdge;

    private int tool;

    private boolean edgeNamesOn;
    private boolean nodeNamesOn;

    private JFrame toolBoxFrame;
    private JButton bNode;
    private JButton bEdge;
    private JButton bStart;
    private JButton bEnd;
    private JButton bErase;
    private JButton bRun;
    private JButton bNodeOn;
    private JButton bEdgeOn;

    public PathPanel() {
        addMouseListener(this);

        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();

        startNode = 0;
        endNode = 1;

        nextNode = 0;
        nextEdge = 0;

        edgeNamesOn = true;
        nodeNamesOn = true;

        setFocusable(true);
        requestFocus();

        toolBoxFrame = new JFrame("Tool Box");

        toolBoxFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        toolBoxFrame.setResizable(false);

        toolBoxFrame.getContentPane().setPreferredSize(new Dimension(200, 400));

		Box box = new Box(BoxLayout.Y_AXIS);

        bNode = new JButton("Node Creator");
        bNode.setMaximumSize(new Dimension(200, 50));
       	bNode.setMinimumSize(new Dimension(200, 50));
        bNode.setPreferredSize(new Dimension(200, 50));
        bNode.addActionListener(this);
        box.add(bNode);

        bEdge = new JButton("Edge Creator");
       	bEdge.setMaximumSize(new Dimension(200, 50));
        bEdge.setMinimumSize(new Dimension(200, 50));
        bEdge.setPreferredSize(new Dimension(200, 50));
        bEdge.addActionListener(this);
        box.add(bEdge);

        bStart = new JButton("Start Point Creator");
        bStart.setMaximumSize(new Dimension(200, 50));
        bStart.setMinimumSize(new Dimension(200, 50));
        bStart.setPreferredSize(new Dimension(200, 50));
        bStart.addActionListener(this);
        box.add(bStart);

        bEnd = new JButton("End Point Creator");
        bEnd.setMaximumSize(new Dimension(200, 50));
        bEnd.setMinimumSize(new Dimension(200, 50));
        bEnd.setPreferredSize(new Dimension(200, 50));
        bEnd.addActionListener(this);
        box.add(bEnd);

        bErase = new JButton("Eraser");
        bErase.setMaximumSize(new Dimension(200, 50));
        bErase.setMinimumSize(new Dimension(200, 50));
        bErase.setPreferredSize(new Dimension(200, 50));
        bErase.addActionListener(this);
        box.add(bErase);

        bRun = new JButton("Run");
        bRun.setMaximumSize(new Dimension(200, 50));
        bRun.setMinimumSize(new Dimension(200, 50));
        bRun.setPreferredSize(new Dimension(200, 50));
        bRun.addActionListener(this);
        box.add(bRun);

        bNodeOn = new JButton("Node Names On");
        bNodeOn.setMaximumSize(new Dimension(200, 50));
        bNodeOn.setMinimumSize(new Dimension(200, 50));
        bNodeOn.setPreferredSize(new Dimension(200, 50));
        bNodeOn.addActionListener(this);
        box.add(bNodeOn);

        bEdgeOn = new JButton("Edge Names On");
        bEdgeOn.setMaximumSize(new Dimension(200, 50));
        bEdgeOn.setMinimumSize(new Dimension(200, 50));
        bEdgeOn.setPreferredSize(new Dimension(200, 50));
        bEdgeOn.addActionListener(this);
        box.add(bEdgeOn);

		toolBoxFrame.add(box);

        toolBoxFrame.pack();
        toolBoxFrame.setLocation(300, 100);



        toolBoxFrame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);

        for (Edge e : edges) {
        	if (e.isHighlighted)
        		g.setColor(Color.GRAY);
        	else
        		g.setColor(Color.BLACK);
        	g.drawLine(e.source.x, e.source.y, e.destination.x, e.destination.y);
            if(edgeNamesOn) {
                Point mid = midpoint(e.source.x, e.source.y, e.destination.x, e.destination.y);
                g.drawString(e.id + ", Weight: " + e.weight, mid.x, mid.y);
            }
        }

        for (Node v : nodes) {
        	if(v.isHighlighted)
                g.setColor(Color.RED);
            else
                g.setColor(Color.BLACK);
            g.fillOval(v.x - 5, v.y - 5, 10, 10);
            if(nodeNamesOn)
                g.drawString(v.id + ", (" + v.x + ", " + v.y + ")", v.x - 30, v.y - 7);
        }

        g.setColor(Color.BLACK);

        if(nodes.size() >= 2) {
            g.drawString("Start: " + nodes.get(startNode).id + ", (" + nodes.get(startNode).x + ", " + nodes.get(startNode).y + ")", 10, 20);
            g.drawString("End: " + nodes.get(endNode).id + ", (" + nodes.get(endNode).x  +", " + nodes.get(endNode).y + ")", 10, 40);
        }

        if(tool == 0) {
            g.drawString("Tool: Node Creator", 10, 60);
        } else if(tool == 1) {
            g.drawString("Tool: Edge Creator", 10, 60);
            if(lastNode != Integer.MAX_VALUE)
                g.drawString("Vertex: " + nodes.get(lastNode).id + ", (" + nodes.get(lastNode).x + ", " + nodes.get(lastNode).y + ")", 10, 80);
        } else if(tool == 2) {
            g.drawString("Tool: Start Point Creator", 10, 60);
        } else if(tool == 3) {
            g.drawString("Tool: End Point Creator", 10, 60);
        } else if(tool == 4) {
        	g.drawString("Tool: Eraser", 10, 60);
        }
    }

    private void addLane(String laneId, int sourceLocNo, int destLocNo, int duration) {
        edges.add(new Edge(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration));
    }

    private int getNodeIndex(Node v) {
        for(int i = 0; i < nodes.size(); i++)
            if((nodes.get(i)).equals(v))
                return i;
        return -1;
    }

    private Node getClosestNode(int x, int y) {
        Node closest = nodes.get(0);
        for (Node node : nodes) {
        	if(distance(x, y, node.x, node.y) < distance(x, y, closest.x, closest.y))
                closest = node;
        }
        return closest;
    }

    private int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private Point midpoint(int x1, int y1, int x2, int y2) {
        return new Point((x1 + x2) / 2, (y1 + y2) / 2);
    }

    public void mouseClicked(MouseEvent e) {
        switch(e.getButton()) {
        case 1:
            switch(tool) {
            case 0:
                nodes.add(new Node("Node_" + nextNode, e.getX(), e.getY()));
                nextNode++;
                break;
            case 1:
                int newNode = getNodeIndex(getClosestNode(e.getX(), e.getY()));
                if(lastNode != Integer.MAX_VALUE && newNode != lastNode) {
                    addLane("Edge_" + nextEdge, lastNode, newNode, Integer.valueOf(JOptionPane.showInputDialog(this, "Input weight.", Integer.valueOf(10))));
                    nextEdge++;
                    nodes.get(lastNode).isHighlighted = false;
                    lastNode = Integer.MAX_VALUE;
                } else if(lastNode == Integer.MAX_VALUE) {
                    lastNode = getNodeIndex(getClosestNode(e.getX(), e.getY()));
                    nodes.get(lastNode).isHighlighted = true;
                }
                break;
            case 2:
                int start = getNodeIndex(getClosestNode(e.getX(), e.getY()));
                if(start != endNode)
                    startNode = start;
                break;
            case 3:
                int end = getNodeIndex(getClosestNode(e.getX(), e.getY()));
                if(end != startNode)
                    endNode = end;
                break;
            case 4:
            	int remove = getNodeIndex(getClosestNode(e.getX(), e.getY()));
            	for (int i = 0; i < edges.size(); i++) {
            		if (edges.get(i).source.equals(nodes.get(remove)))
            			edges.remove(i);
            		if (edges.get(i).destination.equals(nodes.get(remove)))
            			edges.remove(i);
            	}
            	nodes.remove(remove);
            	break;
        	}
            break;
        case 2:
            if(tool == 1 && lastNode != Integer.MAX_VALUE)  {
                nodes.get(lastNode).isHighlighted = false;
                lastNode = Integer.MAX_VALUE;
            }
            break;
        }
        repaint();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(bNode)) {
			tool = 0;
		} else if (e.getSource().equals(bEdge)) {
			tool = 1;
		} else if (e.getSource().equals(bStart)) {
			tool = 2;
		} else if (e.getSource().equals(bEnd)) {
			tool = 3;
		} else if (e.getSource().equals(bErase)) {
			tool = 4;
		} else if (e.getSource().equals(bRun)) {
			Dijkstra d = new Dijkstra(nodes, edges);
            List<Node> path = d.getShortestPath(nodes.get(startNode), nodes.get(endNode));
       		for (Node n : path)
            	System.out.println(n);
		} else if (e.getSource().equals(bNodeOn)) {
			if(nodeNamesOn) {
                nodeNamesOn = false;
                bNodeOn.setText("Node Names Off");
			} else {
                nodeNamesOn = true;
                bNodeOn.setText("Node Names On");
			}
		} else if (e.getSource().equals(bEdgeOn)) {
			if(edgeNamesOn) {
                edgeNamesOn = false;
                bEdgeOn.setText("Edge Names Off");
			} else {
                edgeNamesOn = true;
                bEdgeOn.setText("Edge Names On");
			}
		}
		if(lastNode != Integer.MAX_VALUE && nodes.size() > 0
			)
            nodes.get(lastNode).isHighlighted = false;
        lastNode = Integer.MAX_VALUE;
		repaint();
     }
}