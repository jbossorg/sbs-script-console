/*
 * JBoss Community http://jboss.org/
 *
 * Copyright (c) 2010 Red Hat Middleware, LLC. All rights reserved.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT A WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License, v.2.1 along with this distribution; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 *
 * Red Hat Author(s): Libor Krzyzanek
 */
package org.jboss.labs.sbs.plugin.scriptconsole;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.script.Invocable;
import javax.script.ScriptEngine;

import org.apache.log4j.Logger;

import com.jivesoftware.community.lifecycle.JiveApplication;

/**
 * Executor for admin console.
 *
 * @author Libor Krzyzanek (lkrzyzan)
 */
public class ScriptExecutor extends Thread {

	private static final Logger log = Logger.getLogger(ScriptExecutor.class);

	private boolean running = false;

	private String script;

	private StringWriter result = new StringWriter();

	private ScriptEngine engine;

	public ScriptExecutor(String script, ScriptEngine engine) {
		super("ScriptingConsole-Executor");
		this.script = script;
		this.engine = engine;
	}

	@Override
	public void run() {
		running = true;

		try (PrintWriter r = new PrintWriter(result, true)) {
			try {
				engine.eval(script);
				Invocable invocableEngine = (Invocable) engine;
				Object output = invocableEngine.invokeFunction("run", JiveApplication.getContext(), r);
				if (output != null) {
					r.println("Output from script:");
					r.print(output.toString());
				}

			} catch (Exception e) {
				e.printStackTrace(r);
				log.error("Exception occurred during script execution", e);
			}
		} finally {
			running = false;
		}
	}

	public boolean isRunning() {
		return running;
	}

	/**
	 * Get current value of result
	 *
	 * @return
	 */
	public String getResult() {
		return result.toString();
	}

}
