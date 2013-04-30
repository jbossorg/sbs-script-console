/*
 * JBoss.org http://jboss.org/
 *
 * Copyright (c) 2010  Red Hat Middleware, LLC. All rights reserved.
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
package org.jboss.labs.sbs.plugin.scriptconsole.struts;

import java.util.List;

import org.jboss.labs.sbs.plugin.scriptconsole.ScriptManager;

import com.jivesoftware.community.action.JiveActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;

/**
 * Main class for scripting console
 */
@Validation
public class AdminConsoleAction extends JiveActionSupport implements Preparable {

	private static final long serialVersionUID = -7477924670457819035L;

	private String scriptLanguage;

	private String scriptContent;

	private ScriptManager scriptManager;

	@Override
	public void prepare() throws Exception {
		scriptLanguage = scriptManager.getLanguage();
		scriptContent = scriptManager.getScript();
	}

	@Override
	public String execute() {
		if (scriptManager.isRunning()) {
			addActionError(getText("plugin.script-console.admin.console.text.error.alreadyRunning"));
			return INPUT;
		}

		scriptManager.execute(scriptContent, scriptLanguage);

		addActionMessage(getText("plugin.script-console.admin.console.text.sucess"));

		return SUCCESS;
	}

	public String getResult() {
		return scriptManager.getResult();
	}

	@RequiredStringValidator(message = "Choose scripting language")
	public void setScriptLanguage(String scriptLanguage) {
		this.scriptLanguage = scriptLanguage;
	}

	public String getScriptLanguage() {
		return scriptLanguage;
	}

	@RequiredStringValidator(message = "Script content is empty")
	public void setScriptContent(String scriptContent) {
		this.scriptContent = scriptContent;
	}

	public String getScriptContent() {
		return scriptContent;
	}

	public List<String> getLanguages() {
		return scriptManager.getLanguages();
	}

	public boolean isRunning() {
		return scriptManager.isRunning();
	}

	public void setScriptManager(ScriptManager scriptManager) {
		this.scriptManager = scriptManager;
	}

}
