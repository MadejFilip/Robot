package com.epam.ja.kmw.controller;

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

import com.epam.ja.kmw.Main;
import com.epam.ja.kmw.viewer.FreeBookViewer;

import javafx.application.Platform;

/**
 * @author filipm Initializes icon which allows user to open main window or shut
 *         down application.
 */
public class InicializateTrayAppControler {
	public static final Logger LOGGER = LogManager.getLogger(InicializateTrayAppControler.class);
	private final static SystemTray tray = SystemTray.getSystemTray();
	private PopupMenu popup = new PopupMenu();
	private TrayIcon trayIcon;
	private static boolean downloadingFlag = false;

	/**
	 * Initialize TrayIcon object. There are two text fields with
	 * functionalities: open and close. If user clicks on field 'open' main
	 * bookstore robot window will be open. If user clicks on field 'close'
	 * application will be close. Method adds those functionalities to
	 * SystemTray object. If this operation fails it caught an AWTException.
	 * 
	 * @param freeBookViewer
	 *            stores informations about free books downloaded from
	 *            bookstores.
	 */
	public void initializeTray(FreeBookViewer freeBookViewer) {
		if (!SystemTray.isSupported()) {
			LOGGER.error("SystemTray is not supported");
			System.exit(0);
		}
		trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/icon.png")),
				"BookStoreRobot", popup);
		trayIcon.setImageAutoSize(true);

		MenuItem openItem = new MenuItem("Open");
		openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!downloadingFlag) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							freeBookViewer.showMainUserLayout();
						}
					});
				} else {
					JOptionPane.showMessageDialog(null,
							"BookStoreRobot is curently downloading data... \nPlease be patient");
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
		Platform.setImplicitExit(false);
	}

	/**
	 * Change status of a flag which shows if books are downloading or not.
	 */
	public static void changeOpeningStatus() {
		downloadingFlag = !downloadingFlag;
	}
}
