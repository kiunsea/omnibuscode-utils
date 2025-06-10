package com.omnibuscode.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author KIUNSEA 
 * Refactored to use Log4j 2.x instead of Log4j 1.x for security
 * reasons Supports both Log4j 2.x and fallback to commons-logging
 */
public class LogUtil {

	/**
	 * Log4j 2.x를 우선 사용하고, 없으면 commons-logging으로 fallback Websphere(WAS) 등에서 호환성을 위해
	 * Log4j 2를 직접 사용
	 * 
	 * @param clazz 로거를 생성할 클래스
	 * @return Log4j 2 Logger를 감싼 Commons Logging Log 인터페이스
	 */
	public static Log getLog(Class<?> clazz) {
		Log log = null;

		try {
			// Log4j 2.x 사용 가능한지 확인
			Class.forName("org.apache.logging.log4j.LogManager");
			Logger log4j2Logger = LogManager.getLogger(clazz);
			log = new Log4j2LogAdapter(log4j2Logger);

		} catch (ClassNotFoundException ex) {
			// Log4j 2가 없으면 commons-logging 사용
			log = LogFactory.getLog(clazz);
		}

		return log;
	}

	/**
	 * Log4j 2 Logger를 직접 반환하는 메서드 (새로 추가) Log4j 2의 모든 기능을 사용하고 싶을 때 사용
	 * 
	 * @param clazz 로거를 생성할 클래스
	 * @return Log4j 2 Logger
	 */
	public static Logger getLog4j2Logger(Class<?> clazz) {
		return LogManager.getLogger(clazz);
	}

	/**
	 * Log4j 2 Logger를 Commons Logging Log 인터페이스로 감싸는 어댑터 클래스
	 */
	private static class Log4j2LogAdapter implements Log {

		private final Logger logger;

		public Log4j2LogAdapter(Logger logger) {
			this.logger = logger;
		}

		@Override
		public boolean isDebugEnabled() {
			return logger.isDebugEnabled();
		}

		@Override
		public boolean isErrorEnabled() {
			return logger.isErrorEnabled();
		}

		@Override
		public boolean isFatalEnabled() {
			return logger.isFatalEnabled();
		}

		@Override
		public boolean isInfoEnabled() {
			return logger.isInfoEnabled();
		}

		@Override
		public boolean isTraceEnabled() {
			return logger.isTraceEnabled();
		}

		@Override
		public boolean isWarnEnabled() {
			return logger.isWarnEnabled();
		}

		@Override
		public void trace(Object message) {
			logger.trace(message);
		}

		@Override
		public void trace(Object message, Throwable t) {
			logger.trace(message, t);
		}

		@Override
		public void debug(Object message) {
			logger.debug(message);
		}

		@Override
		public void debug(Object message, Throwable t) {
			logger.debug(message, t);
		}

		@Override
		public void info(Object message) {
			logger.info(message);
		}

		@Override
		public void info(Object message, Throwable t) {
			logger.info(message, t);
		}

		@Override
		public void warn(Object message) {
			logger.warn(message);
		}

		@Override
		public void warn(Object message, Throwable t) {
			logger.warn(message, t);
		}

		@Override
		public void error(Object message) {
			logger.error(message);
		}

		@Override
		public void error(Object message, Throwable t) {
			logger.error(message, t);
		}

		@Override
		public void fatal(Object message) {
			logger.fatal(message);
		}

		@Override
		public void fatal(Object message, Throwable t) {
			logger.fatal(message, t);
		}
	}
}