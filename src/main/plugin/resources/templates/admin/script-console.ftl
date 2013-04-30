<html>
    <head>
        <#assign pageTitle=action.getText('plugin.script-console.admin.console.name') />
        <title>${pageTitle}</title>
        <content tag="pagetitle">${pageTitle}</content>
        <content tag="pageID">system-script-console</content>
    </head>
    <body>
        <#include "/template/global/include/form-message.ftl" />

        <@s.form theme="simple" action="scripting-console-execute">
          <table>
            <tr>
              <td colspan="2"><@s.text name="plugin.script-console.admin.console.scriptcontent" /></td>
            </tr>
            <tr>
              <td colspan="2"><@s.textarea name="scriptContent" cols="100" rows="20" required="true"/></td>
            </tr>
            <tr>
              <td><@s.select name="scriptLanguage" list="languages" required="true"/>
              &nbsp;
              <@s.submit value="${action.getText('plugin.script-console.admin.console.submit')}" disabled="${action.isRunning().toString()}"/></td>
            </tr>
            <@s.if test="running">
              <tr><td colspan="2"><@s.text name="plugin.script-console.admin.console.text.running" /></td></tr>
            </@s.if>
            <tr><td>&nbsp;</td></tr>
            <tr><td colspan="2"><@s.text name="plugin.script-console.admin.console.text.scriptOutput" /></td></tr>
            <tr>
              <td colspan="2">
                <pre>
                <@s.property value="result"/>
                </pre>
              </td>
            </tr>
          </table>
        </@s.form>
    </body>
</html>