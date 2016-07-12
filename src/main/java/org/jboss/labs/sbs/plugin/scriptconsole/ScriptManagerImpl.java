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

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

/**
 * @author Libor Krzyzanek (lkrzyzan)
 */
public class ScriptManagerImpl implements ScriptManager {

	private String language = "JavaScript";

	private String script =
			"/* Run the script. ctx is com.jivesoftware.community.JiveContext class (spring context), out is PrintWriter */\n"
			+ "function run(ctx, out) {\n" + "  out.append(\"DONE\");\n" + "}";

	private ScriptEngineManager scriptEngineMgr;

	private List<String> languages;

	private ScriptExecutor executor = null;

	public ScriptManagerImpl() {
		this.scriptEngineMgr = new ScriptEngineManager();

		this.languages = new ArrayList<>();

		List<ScriptEngineFactory> factories = scriptEngineMgr.getEngineFactories();

		for (int i = 0; i < factories.size(); i++) {
			ScriptEngineFactory factory = factories.get(i);
			languages.addAll(factory.getNames());
		}
	}

	@Override
	public void execute(String script, String language) {
		this.language = language;
		this.script = script;

		ScriptEngine engine = scriptEngineMgr.getEngineByName(language);
		executor = new ScriptExecutor(script, engine);
		executor.start();
	}

	@Override
	public boolean isRunning() {
		if (executor == null) {
			return false;
		}
		return executor.isRunning();
	}

	@Override
	public String getScript() {
		return script;
	}

	@Override
	public String getLanguage() {
		return language;
	}

	@Override
	public String getResult() {
		if (executor == null) {
			return "Insert script content, choose scripting language and then press the Execute button";
		}
		return executor.getResult();
	}

	@Override
	public List<String> getLanguages() {
		return languages;
	}

}
