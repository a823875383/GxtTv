package com.jsqix.gxt.tv.utils;

import android.os.Environment;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class SDLogUtils {
	public static void init() {
		LogConfigurator logConfigurator = new LogConfigurator();
		logConfigurator.setMaxFileSize(1024 * 1024 * 1024);
		logConfigurator.setFileName(Environment.getExternalStorageDirectory()
				+ "/dianwo/logs/tv/dianwo.txt");
		logConfigurator.setRootLevel(Level.ALL);
		logConfigurator.setLevel("org.apache", Level.ALL);
		logConfigurator.configure();
	}

	public static Logger getLogger(String name) {
		Logger logger = Logger.getLogger(name);
		FileAppender fileAppender = new FileAppender();
		fileAppender.setEncoding("utf-8");
		logger.addAppender(fileAppender);
		return logger;
	}

}
