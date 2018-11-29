package gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.net.URL;

public class ServerTreeCellRenderer implements TreeCellRenderer {

    private JCheckBox leafRenderer;
    private DefaultTreeCellRenderer nonleafRenderer;
    private Color textForeground;
    private Color textBackground;
    private Color selectionForeground;
    private Color selectionBackground;

    public ServerTreeCellRenderer() {
        leafRenderer = new JCheckBox();
        nonleafRenderer = new DefaultTreeCellRenderer();


        nonleafRenderer.setLeafIcon(createIcon("/icons/general/Add16.gif"));
        nonleafRenderer.setOpenIcon(createIcon("/icons/general/About16.gif"));
        nonleafRenderer.setClosedIcon(createIcon("/icons/general/Cut16.gif"));

        textForeground = UIManager.getColor("Tree.textForeground");
        textBackground = UIManager.getColor("Tree.textBackground");
        selectionForeground = UIManager.getColor("Tree.selectionForeground");
        selectionBackground = UIManager.getColor("Tree.selectionBackground");


    }

    private ImageIcon createIcon(String path){
        URL url =  getClass().getResource(path);
        if (url == null){
            System.err.println("Unable to load resourse " + path);
        }
        ImageIcon imageIcon = new ImageIcon(url);
        return imageIcon;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (leaf){
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            ServerInfo nodeInfo = (ServerInfo)node.getUserObject();
            leafRenderer.setText(nodeInfo.toString());
            leafRenderer.setSelected(nodeInfo.isChecked());

            if (selected){
                leafRenderer.setBackground(selectionBackground);
                leafRenderer.setForeground(selectionForeground);
            } else {
                leafRenderer.setBackground(textBackground);
                leafRenderer.setForeground(textForeground);
            }

            return leafRenderer;
        } else {
            return nonleafRenderer.getTreeCellRendererComponent( tree, value, selected, expanded, leaf,row, hasFocus);
        }
    }
}
