package me.lightfall.BotBot.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import me.lightfall.BotBot.audio.AudioManager;
import me.lightfall.BotBot.utils.CleanGuild;
import me.lightfall.BotBot.utils.CleanVoiceChannel;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;

public class MainWindow {

	private JFrame frame;
	private DefaultTreeModel voiceChannelModel;
	private DefaultTreeModel musicModel;
	private JLabel voiceChannelSelectInfoLabel;
	private JLabel footerLabel;
	private JLabel voiceChannelBodyLabel;
	private JProgressBar progressBar;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	public DefaultTreeModel getListModel() { return voiceChannelModel; }
	public DefaultTreeModel getMusicModel() { return musicModel; }
	protected void setVisible(boolean b) { frame.setVisible(b); }
	public void setFooter(String string) { footerLabel.setText(string); }
	public void setProgrssBar(int value) {progressBar.setValue(value);}
	
	private void setVoiceChannelPlayerList(List<User> users) {
		String text = "<html><table style=\"width:100%\">";
		for(User user : users) {
			text += "<tr>";
				text += "<td><b>" + user.getUsername() + "</b>&emsp</td>";
				text += "<td>" + user.getOnlineStatus().toString().toLowerCase() + "&emsp</td>";
				text += "<td>" + (user.getCurrentGame() != null ? "Playing " + user.getCurrentGame() : "")+ "</td>";
			text += "</tr>";
		}
		text += "</p></html>";
		voiceChannelBodyLabel.setText(text);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		// Clear out the icons for the trees
		Icon empty = new Icon() {
			private static final int SIZE = 0;
		    public int getIconWidth() {return SIZE;}
		    public int getIconHeight() {return SIZE;}
		    public void paintIcon(Component c, Graphics g, int x, int y) {}
		};
        UIManager.put("Tree.closedIcon", empty);
        UIManager.put("Tree.openIcon", empty);
        UIManager.put("Tree.collapsedIcon", empty);
        UIManager.put("Tree.expandedIcon", empty);
//      UIManager.put("Tree.leafIcon", empty);
		
		voiceChannelModel = new DefaultTreeModel(new DefaultMutableTreeNode(""));
		musicModel = new DefaultTreeModel(new DefaultMutableTreeNode(""));
		
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(750, 500));
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[] {50, 400, 25};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel headerPanel = new JPanel();
		GridBagConstraints gbc_headerPanel = new GridBagConstraints();
		gbc_headerPanel.insets = new Insets(0, 0, 0, 0);
		gbc_headerPanel.fill = GridBagConstraints.BOTH;
		gbc_headerPanel.gridx = 0;
		gbc_headerPanel.gridy = 0;
		frame.getContentPane().add(headerPanel, gbc_headerPanel);
		
		JLabel lblNewLabel = new JLabel("New label");
		headerPanel.add(lblNewLabel);
		
		JPanel bodyPanel = new JPanel();
		GridBagConstraints gbc_bodyPanel = new GridBagConstraints();
		gbc_bodyPanel.fill = GridBagConstraints.BOTH;
		gbc_bodyPanel.gridx = 0;
		gbc_bodyPanel.gridy = 1;
		frame.getContentPane().add(bodyPanel, gbc_bodyPanel);
		bodyPanel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane mainTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		mainTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		mainTabbedPane.setBorder(null);
		bodyPanel.add(mainTabbedPane);
		
		JPanel voiceChannelPane = new JPanel();
		mainTabbedPane.addTab("Voice Channels", null, voiceChannelPane, null);
		GridBagLayout gbl_voiceChannelPane = new GridBagLayout();
		gbl_voiceChannelPane.columnWidths = new int[] {200, 0};
		gbl_voiceChannelPane.rowHeights = new int[]{0, 0};
		gbl_voiceChannelPane.columnWeights = new double[]{0.0, 1.0};
		gbl_voiceChannelPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		voiceChannelPane.setLayout(gbl_voiceChannelPane);
		
		JPanel voiceChannelListPane = new JPanel();
		GridBagConstraints gbc_voiceChannelListPane = new GridBagConstraints();
		gbc_voiceChannelListPane.insets = new Insets(0, 0, 0, 5);
		gbc_voiceChannelListPane.fill = GridBagConstraints.BOTH;
		gbc_voiceChannelListPane.gridx = 0;
		gbc_voiceChannelListPane.gridy = 0;
		voiceChannelPane.add(voiceChannelListPane, gbc_voiceChannelListPane);
		voiceChannelListPane.setLayout(new BorderLayout(0, 0));
		
		JScrollPane voiceChannelScrollPane = new JScrollPane();
		voiceChannelListPane.add(voiceChannelScrollPane, BorderLayout.CENTER);
		
		JTree voiceChannelList = new JTree(voiceChannelModel);
		voiceChannelList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					GUI.joinSelected();
				}
			}
		});
		voiceChannelList.setRootVisible(false);
		voiceChannelScrollPane.setViewportView(voiceChannelList);
		voiceChannelList.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				if(node.isLeaf()) {
					CleanVoiceChannel ch = (CleanVoiceChannel) node.getUserObject();
					voiceChannelSelectInfoLabel.setText("<html>" + 
														ch.getName() + "<br>" +
														ch.getGuild().getName() + "<br>" +
														ch.getId() + "</html>"
														);
					GUI.selectedVoiceChannel = ch;
					setVoiceChannelPlayerList(ch.getUsers());
				} else {
					// Assume it's a server
					CleanGuild guild = (CleanGuild) node.getUserObject();
					List<User> users = guild.getOnlineUsers();
					users.sort(new Comparator<User>() {
						@Override
						public int compare(User u1, User u2) {
							return u1.getUsername().toLowerCase().compareTo(u2.getUsername().toLowerCase());
						}
					});
					setVoiceChannelPlayerList(users);
				}
			}
		});
		
		JPanel voiceChannelBodyPane = new JPanel();
		voiceChannelBodyPane.setBorder(null);
		GridBagConstraints gbc_voiceChannelBodyPane = new GridBagConstraints();
		gbc_voiceChannelBodyPane.fill = GridBagConstraints.BOTH;
		gbc_voiceChannelBodyPane.gridx = 1;
		gbc_voiceChannelBodyPane.gridy = 0;
		voiceChannelPane.add(voiceChannelBodyPane, gbc_voiceChannelBodyPane);
		GridBagLayout gbl_voiceChannelBodyPane = new GridBagLayout();
		gbl_voiceChannelBodyPane.columnWidths = new int[]{0, 0};
		gbl_voiceChannelBodyPane.rowHeights = new int[] {50, 0, 0};
		gbl_voiceChannelBodyPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_voiceChannelBodyPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		voiceChannelBodyPane.setLayout(gbl_voiceChannelBodyPane);
		
		JPanel voiceChannelBodyHeaderPane = new JPanel();
		voiceChannelBodyHeaderPane.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		GridBagConstraints gbc_voiceChannelBodyHeaderPane = new GridBagConstraints();
		gbc_voiceChannelBodyHeaderPane.fill = GridBagConstraints.BOTH;
		gbc_voiceChannelBodyHeaderPane.gridx = 0;
		gbc_voiceChannelBodyHeaderPane.gridy = 0;
		voiceChannelBodyPane.add(voiceChannelBodyHeaderPane, gbc_voiceChannelBodyHeaderPane);
		GridBagLayout gbl_voiceChannelBodyHeaderPane = new GridBagLayout();
		gbl_voiceChannelBodyHeaderPane.columnWidths = new int[] {0, 0, 0, 0};
		gbl_voiceChannelBodyHeaderPane.rowHeights = new int[] {0};
		gbl_voiceChannelBodyHeaderPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0};
		gbl_voiceChannelBodyHeaderPane.rowWeights = new double[]{1.0};
		voiceChannelBodyHeaderPane.setLayout(gbl_voiceChannelBodyHeaderPane);
		
		JLabel voiceChannelSelectInfoTitlesLabel = new JLabel("<html>\r\n<p align=\"right\">\r\n<b>Channel Name</b><br>\r\n<b>Server Name</b><br>\r\n<b>ID</b><br>\r\n</p>\r\n</html>");
		GridBagConstraints gbc_voiceChannelSelectInfoTitlesLabel = new GridBagConstraints();
		gbc_voiceChannelSelectInfoTitlesLabel.insets = new Insets(0, 5, 0, 5);
		gbc_voiceChannelSelectInfoTitlesLabel.anchor = GridBagConstraints.BELOW_BASELINE;
		gbc_voiceChannelSelectInfoTitlesLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_voiceChannelSelectInfoTitlesLabel.gridx = 0;
		gbc_voiceChannelSelectInfoTitlesLabel.gridy = 0;
		voiceChannelBodyHeaderPane.add(voiceChannelSelectInfoTitlesLabel, gbc_voiceChannelSelectInfoTitlesLabel);
		
		voiceChannelSelectInfoLabel = new JLabel("<html>\r\nChannel Name<br>\r\nServer Name <br>\r\nID <br>\r\n</html>");
		GridBagConstraints gbc_voiceChannelSelectInfoLabel = new GridBagConstraints();
		gbc_voiceChannelSelectInfoLabel.insets = new Insets(0, 0, 0, 5);
		gbc_voiceChannelSelectInfoLabel.fill = GridBagConstraints.BOTH;
		gbc_voiceChannelSelectInfoLabel.gridx = 1;
		gbc_voiceChannelSelectInfoLabel.gridy = 0;
		voiceChannelBodyHeaderPane.add(voiceChannelSelectInfoLabel, gbc_voiceChannelSelectInfoLabel);
		
		JButton voiceChannelJoinButton = new JButton("Join Channel");
		voiceChannelJoinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI.joinSelected();
			}
		});
		GridBagConstraints gbc_voiceChannelJoinButton = new GridBagConstraints();
		gbc_voiceChannelJoinButton.insets = new Insets(0, 0, 0, 5);
		gbc_voiceChannelJoinButton.gridx = 2;
		gbc_voiceChannelJoinButton.gridy = 0;
		voiceChannelBodyHeaderPane.add(voiceChannelJoinButton, gbc_voiceChannelJoinButton);
		
		JButton voiceChannelLeaveButton = new JButton("Leave Channel");
		voiceChannelLeaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AudioManager.leave();
			}
		});
		GridBagConstraints gbc_voiceChannelLeaveButton = new GridBagConstraints();
		gbc_voiceChannelLeaveButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_voiceChannelLeaveButton.gridx = 3;
		gbc_voiceChannelLeaveButton.gridy = 0;
		voiceChannelBodyHeaderPane.add(voiceChannelLeaveButton, gbc_voiceChannelLeaveButton);
		
		JPanel voiceChannelBodyBodyPane = new JPanel();
		GridBagConstraints gbc_voiceChannelBodyBodyPane = new GridBagConstraints();
		gbc_voiceChannelBodyBodyPane.fill = GridBagConstraints.BOTH;
		gbc_voiceChannelBodyBodyPane.gridx = 0;
		gbc_voiceChannelBodyBodyPane.gridy = 1;
		voiceChannelBodyPane.add(voiceChannelBodyBodyPane, gbc_voiceChannelBodyBodyPane);
		voiceChannelBodyBodyPane.setLayout(new BorderLayout(0, 0));
		
		voiceChannelBodyLabel = new JLabel();
		voiceChannelBodyLabel.setVerticalTextPosition(SwingConstants.TOP);
		voiceChannelBodyLabel.setVerticalAlignment(SwingConstants.TOP);
		voiceChannelBodyLabel.setBorder(null);
		voiceChannelBodyLabel.setOpaque(false);
		
		JScrollPane voiceChannelBodyLabelScrollPane = new JScrollPane(voiceChannelBodyLabel);
		voiceChannelBodyLabelScrollPane.setBorder(null);
		voiceChannelBodyBodyPane.add(voiceChannelBodyLabelScrollPane, BorderLayout.CENTER);
		
		JPanel musicPane = new JPanel();
		mainTabbedPane.addTab("Music", null, musicPane, null);
		GridBagLayout gbl_musicPane = new GridBagLayout();
		gbl_musicPane.columnWidths = new int[] {200, 300};
		gbl_musicPane.rowHeights = new int[]{0, 0};
		gbl_musicPane.columnWeights = new double[]{1.0, 2.0};
		gbl_musicPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		musicPane.setLayout(gbl_musicPane);
		
		JPanel musicListPane = new JPanel();
		GridBagConstraints gbc_musicListPane = new GridBagConstraints();
		gbc_musicListPane.weightx = 0.2;
		gbc_musicListPane.insets = new Insets(0, 0, 0, 5);
		gbc_musicListPane.fill = GridBagConstraints.BOTH;
		gbc_musicListPane.gridx = 0;
		gbc_musicListPane.gridy = 0;
		musicPane.add(musicListPane, gbc_musicListPane);
		musicListPane.setLayout(new BorderLayout(0, 0));
		
		JScrollPane musicScrollPane = new JScrollPane();
		musicListPane.add(musicScrollPane, BorderLayout.CENTER);
		
		JTree musicList = new JTree(musicModel);
		musicList.setDragEnabled(true);
		musicList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2)
					GUI.playSong();
			}
		});
		musicList.setRootVisible(false);
		musicList.setRowHeight(-1);
		musicScrollPane.setViewportView(musicList);
		musicList.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				String song = (String) node.getUserObject();
				GUI.selectedSong = song;
			}
		});
		
		JPanel musicBodyPane = new JPanel();
		GridBagConstraints gbc_musicBodyPane = new GridBagConstraints();
		gbc_musicBodyPane.weightx = 1.0;
		gbc_musicBodyPane.fill = GridBagConstraints.BOTH;
		gbc_musicBodyPane.gridx = 1;
		gbc_musicBodyPane.gridy = 0;
		musicPane.add(musicBodyPane, gbc_musicBodyPane);
		GridBagLayout gbl_musicBodyPane = new GridBagLayout();
		gbl_musicBodyPane.columnWidths = new int[]{0, 0};
		gbl_musicBodyPane.rowHeights = new int[]{50, 0, 0};
		gbl_musicBodyPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_musicBodyPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		musicBodyPane.setLayout(gbl_musicBodyPane);
		
		JPanel musicBodyHeaderPane = new JPanel();
		GridBagConstraints gbc_musicBodyHeaderPane = new GridBagConstraints();
		gbc_musicBodyHeaderPane.insets = new Insets(0, 0, 5, 0);
		gbc_musicBodyHeaderPane.fill = GridBagConstraints.BOTH;
		gbc_musicBodyHeaderPane.gridx = 0;
		gbc_musicBodyHeaderPane.gridy = 0;
		musicBodyPane.add(musicBodyHeaderPane, gbc_musicBodyHeaderPane);
		GridBagLayout gbl_musicBodyHeaderPane = new GridBagLayout();
		gbl_musicBodyHeaderPane.columnWidths = new int[]{0, 0, 0};
		gbl_musicBodyHeaderPane.rowHeights = new int[]{0, 0};
		gbl_musicBodyHeaderPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_musicBodyHeaderPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		musicBodyHeaderPane.setLayout(gbl_musicBodyHeaderPane);
		
		JButton musicPlayButton = new JButton("Play");
		musicPlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI.playSong();
			}
		});
		GridBagConstraints gbc_musicPlayButton = new GridBagConstraints();
		gbc_musicPlayButton.insets = new Insets(0, 0, 0, 5);
		gbc_musicPlayButton.gridx = 0;
		gbc_musicPlayButton.gridy = 0;
		musicBodyHeaderPane.add(musicPlayButton, gbc_musicPlayButton);
		
		JButton musicPauseButton = new JButton("Pause");
		musicPauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AudioManager.pause();
			}
		});
		GridBagConstraints gbc_musicPauseButton = new GridBagConstraints();
		gbc_musicPauseButton.gridx = 1;
		gbc_musicPauseButton.gridy = 0;
		musicBodyHeaderPane.add(musicPauseButton, gbc_musicPauseButton);
		
		JPanel musicBodyBodyPane = new JPanel();
		GridBagConstraints gbc_musicBodyBodyPane = new GridBagConstraints();
		gbc_musicBodyBodyPane.fill = GridBagConstraints.BOTH;
		gbc_musicBodyBodyPane.gridx = 0;
		gbc_musicBodyBodyPane.gridy = 1;
		musicBodyPane.add(musicBodyBodyPane, gbc_musicBodyBodyPane);
		musicBodyBodyPane.setLayout(new BorderLayout(0, 0));
		
		JPanel footerPanel = new JPanel();
		GridBagConstraints gbc_footerPanel = new GridBagConstraints();
		gbc_footerPanel.fill = GridBagConstraints.BOTH;
		gbc_footerPanel.gridx = 0;
		gbc_footerPanel.gridy = 2;
		frame.getContentPane().add(footerPanel, gbc_footerPanel);
		GridBagLayout gbl_footerPanel = new GridBagLayout();
		gbl_footerPanel.columnWidths = new int[]{150, 0, 0};
		gbl_footerPanel.rowHeights = new int[]{0, 0};
		gbl_footerPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_footerPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		footerPanel.setLayout(gbl_footerPanel);
		
		progressBar = new JProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.weighty = 1.0;
		gbc_progressBar.insets = new Insets(0, 0, 0, 5);
		gbc_progressBar.fill = GridBagConstraints.BOTH;
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 0;
		footerPanel.add(progressBar, gbc_progressBar);
		
		footerLabel = new JLabel("Footer Text");
		footerLabel.setHorizontalAlignment(SwingConstants.LEFT);
		footerLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		GridBagConstraints gbc_footerLabel = new GridBagConstraints();
		gbc_footerLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_footerLabel.gridx = 1;
		gbc_footerLabel.gridy = 0;
		footerPanel.add(footerLabel, gbc_footerLabel);
	}
}
