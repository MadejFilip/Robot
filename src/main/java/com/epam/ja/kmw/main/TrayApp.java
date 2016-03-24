package com.epam.ja.kmw.main;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.ja.kmw.viewer.FreeBookViewer;

public class TrayApp {
	public static final Logger LOGGER = LogManager.getLogger(TrayApp.class);
	private final static SystemTray tray = SystemTray.getSystemTray();
	private PopupMenu popup = new PopupMenu();
	private TrayIcon trayIcon;
	private String[] args;
	private static boolean downloadingFlag = false;
	
	public TrayApp(String[] args) {
		this.args = args;
	}

	public void initializeTray() {
		if (!SystemTray.isSupported()) {
			LOGGER.error("SystemTray is not supported");
			System.exit(0);
		}
		trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().createImage("./src/main/resources/icon.png"),
				"BookStoreRobot", popup);
		trayIcon.setImageAutoSize(true);
		
		MenuItem openItem = new MenuItem("Open");
		openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!downloadingFlag){
				new Thread(new Runnable(){
		            @Override
		            public void run() {
		                new FreeBookViewer().open(args);
		            }
		        }).start();
				}else{
					JOptionPane.showMessageDialog(null, "BookStoreRobot is curently downloading data... \nPlease be patient");
				}
			}
		});

		MenuItem closeItem = new MenuItem("Close");
		closeItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		popup.add(openItem);
		popup.add(closeItem);

		try {
			tray.add(trayIcon);
		} catch (AWTException awtException) {
			LOGGER.error(awtException.getMessage());
		}
	}
	
	public static void changeOpeningStatus() {
		downloadingFlag = !downloadingFlag;
	}
}
