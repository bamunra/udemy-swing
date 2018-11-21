package gui;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;


class ServerInfo{
    private String name;
    private int id;

    public ServerInfo(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class MessagePanel extends JPanel {

    private JTree serverTree;

    public MessagePanel() {
        serverTree = new JTree(createTree());
        serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setLayout(new BorderLayout());
        add(new JScrollPane(serverTree), BorderLayout.CENTER);

        serverTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) serverTree.getLastSelectedPathComponent();
                Object userObject = node.getUserObject();
//                if (userObject instanceof ServerInfo){
//                    System.out.println("get userobject with id " + ((ServerInfo) userObject).getId()    );
//                }

                System.out.println(userObject);
            }
        });
    }

    private DefaultMutableTreeNode createTree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");
        DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("Belarus");
        DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("Russia");
        DefaultMutableTreeNode branch3 = new DefaultMutableTreeNode("Ukraine");

        DefaultMutableTreeNode leaf1 = new DefaultMutableTreeNode(new ServerInfo("Minsk",1));
        DefaultMutableTreeNode leaf2 = new DefaultMutableTreeNode(new ServerInfo("Baranovichi",2));
        DefaultMutableTreeNode leaf3 = new DefaultMutableTreeNode(new ServerInfo("Moscow",3));
        DefaultMutableTreeNode leaf4 = new DefaultMutableTreeNode(new ServerInfo("Smolensk",4));
        DefaultMutableTreeNode leaf5 = new DefaultMutableTreeNode(new ServerInfo("Kiev",5));
        DefaultMutableTreeNode leaf6 = new DefaultMutableTreeNode(new ServerInfo("Odessa",6));

        branch1.add(leaf1);
        branch1.add(leaf2);
        branch2.add(leaf3);
        branch2.add(leaf4);
        branch3.add(leaf5);
        branch3.add(leaf6);

        top.add(branch1);
        top.add(branch2);
        top.add(branch3);
        return top;
    }
}
