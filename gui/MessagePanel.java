package gui;

import controller.MessageServer;
import model.Message;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;


class ServerInfo {
    private String name;
    private int id;
    private boolean checked;

    public ServerInfo(String name, int id, boolean checked) {
        this.name = name;
        this.id = id;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class MessagePanel extends JPanel {

    private JTree serverTree;
    private ServerTreeCellRenderer treeCellRenderer;
    private TreeCellEditor treeCellEditor;
    private ProgressDialog progressDialog;

    private Set<Integer> selectedServers;
    private MessageServer messageServer;

    public MessagePanel() {


        progressDialog = new ProgressDialog((Window) getParent());
        messageServer = new MessageServer();
        selectedServers = new TreeSet<Integer>();
        selectedServers.add(0);
        selectedServers.add(1);
        selectedServers.add(4);
        treeCellRenderer = new ServerTreeCellRenderer();
        treeCellEditor = new ServerTreeCellEditor();


        serverTree = new JTree(createTree());
        serverTree.setCellRenderer(treeCellRenderer);
        serverTree.setCellEditor(treeCellEditor);
        serverTree.setEditable(true);

        serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setLayout(new BorderLayout());
        add(new JScrollPane(serverTree), BorderLayout.CENTER);

//        serverTree.addTreeSelectionListener(new TreeSelectionListener() {
//            @Override
//            public void valueChanged(TreeSelectionEvent e) {
//                DefaultMutableTreeNode node = (DefaultMutableTreeNode) serverTree.getLastSelectedPathComponent();
//                Object userObject = node.getUserObject();
////                if (userObject instanceof ServerInfo){
////                    System.out.println("get userobject with id " + ((ServerInfo) userObject).getId()    );
////                }
//
//                System.out.println(userObject);
//            }
//        });

        treeCellEditor.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                ServerInfo info = (ServerInfo) treeCellEditor.getCellEditorValue();
                System.out.println(info + ": " + info.getId() + ": " + info.isChecked());
                int serverId = info.getId();
                if (info.isChecked()) {
                    selectedServers.add(serverId);
                } else {
                    selectedServers.remove(serverId);
                }

                messageServer.setSelectedServers(selectedServers);

                retrieveMessages();


            }

            @Override
            public void editingCanceled(ChangeEvent e) {

            }
        });


    }

    private void retrieveMessages() {
        System.out.println("Message waiting" + messageServer.getMessageCount());

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("Showing modal dialog");
                progressDialog.setVisible(true);
                System.out.println("Finish showing modal dialog");
            }
        });





        SwingWorker<List<Message>, Integer> worker = new SwingWorker<List<Message>, Integer>() {
            @Override
            protected List<Message> doInBackground() throws Exception {

                List<Message> retrievedMessages = new ArrayList<Message>();

                int count = 0;
                for (Message message : messageServer) {
                    System.out.println(message.getTittle());
                    retrievedMessages.add(message);
                    count++;
                    publish(count);
                }
                return retrievedMessages;
            }

            @Override
            protected void process(List<Integer> counts) {

                int retrieved = counts.get(counts.size()-1);

                System.out.println("Got " + retrieved + " messages.");


            }

            @Override
            protected void done() {

                try {

                    List<Message> retrievedMessages = get();
                    System.out.println("Retrieved " + retrievedMessages.size() + " messages.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                progressDialog.setVisible(false);
            }


        };

        worker.execute();

    }

    private ImageIcon createIcon(String path) {
        URL url = getClass().getResource(path);
        if (url == null) {
            System.err.println("Unable to load resourse " + path);
        }
        ImageIcon imageIcon = new ImageIcon(url);
        return imageIcon;
    }

    private DefaultMutableTreeNode createTree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");
        DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("Belarus");
        DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("Russia");
        DefaultMutableTreeNode branch3 = new DefaultMutableTreeNode("Ukraine");

        DefaultMutableTreeNode leaf1 = new DefaultMutableTreeNode(new ServerInfo("Minsk", 1, selectedServers.contains(0)));
        DefaultMutableTreeNode leaf2 = new DefaultMutableTreeNode(new ServerInfo("Baranovichi", 2, selectedServers.contains(1)));
        DefaultMutableTreeNode leaf3 = new DefaultMutableTreeNode(new ServerInfo("Moscow", 3, selectedServers.contains(2)));
        DefaultMutableTreeNode leaf4 = new DefaultMutableTreeNode(new ServerInfo("Smolensk", 4, selectedServers.contains(3)));
        DefaultMutableTreeNode leaf5 = new DefaultMutableTreeNode(new ServerInfo("Kiev", 5, selectedServers.contains(4)));
        DefaultMutableTreeNode leaf6 = new DefaultMutableTreeNode(new ServerInfo("Odessa", 6, selectedServers.contains(5)));

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
