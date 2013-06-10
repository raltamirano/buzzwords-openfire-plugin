package com.raltamirano.openfire.plugins.buzzwords;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.interceptor.InterceptorManager;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;

public class Buzzwords implements Plugin, PacketInterceptor {

	private static final Logger logger = Logger.getLogger(Buzzwords.class.getName());
	
	public void initializePlugin(PluginManager pluginManager, File pluginDirectory) {
		try {
			InterceptorManager.getInstance().addInterceptor(this);
			logger.info("Buzzwords plugin initialized");
		} catch (Throwable t) {
			logger.log(Level.SEVERE, "Error initializing plugin", t);
			throw new RuntimeException(t);
		}
	}

	public void destroyPlugin() {
		try {
			InterceptorManager.getInstance().removeInterceptor(this);
			logger.info("Buzzwords plugin destroyed");
		} catch (Throwable t) {
			logger.log(Level.SEVERE, "Error destroying plugin", t);
			throw new RuntimeException(t);
		}			
	}

	public void interceptPacket(Packet packet, Session session, boolean incoming,
			boolean processed) throws PacketRejectedException {
		try {			
			if (processed) {
				return;
			}
	
			if (packet instanceof org.xmpp.packet.Message) {
				String messageBody = ((Message) packet).getBody();
				if (messageBody != null && !"".equals(messageBody)) {
					WordManager.getInstance().processText(messageBody);
					// TODO: testing!
					WordManager.getInstance().printTopStats();
				}
			}
		} catch (Throwable t) {
			logger.log(Level.SEVERE, "Error intercepting packet", t);
			throw new RuntimeException(t);
		}
	}
}
