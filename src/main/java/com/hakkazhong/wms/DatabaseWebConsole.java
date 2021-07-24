package com.hakkazhong.wms;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Component
public class DatabaseWebConsole implements SmartLifecycle {

	private Server webServer;

	private boolean isRunning = false;

	public DatabaseWebConsole(@Value("${spring.h2.console.port:8082}") int portNo) {
		try {
			this.webServer = Server.createWebServer("-webPort", String.valueOf(portNo));
		}
		catch (SQLException ex) {
			throw new IllegalArgumentException("failed to create web server at port: " + portNo, ex);
		}
	}

	@Override
	public void start() {
		try {
			this.webServer.start();
		}
		catch (SQLException ex) {
			throw new IllegalArgumentException("failed to start web server" + this.webServer, ex);
		}
	}

	@Override
	public void stop() {
		this.webServer.stop();
	}

	@Override
	public boolean isRunning() {
		return this.isRunning;
	}

}
