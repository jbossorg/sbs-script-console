Jive SBS Plugin: Script Console
===============================

Run your scripts in Jive SBS administration console.
Scripts can access JiveContext (Spring context) and is very powerful tool.

Just go to Jive Admin > System > Management > Scripting Console and run your script.
Script itself is executed in separate thread so you need to refresh to get actual script's output.


How to write a script
---------------------

Script must contains one function called run with two parameters (parameters are optional).
Engine call this function and pass [JiveContext](http://docs.jivesoftware.com/jive_sbs/5.0.0/javadoc/api/com/jivesoftware/community/JiveContext.html) (spring context) as the first parameter and [PrintWriter](http://download.oracle.com/javase/6/docs/api/java/io/PrintWriter.html) as the second parameter.
PrintWriter is used as script output. If function returns any object then it is passed to the output after execution.

Examples
--------

Simple output

		/* Run the script.
		   ctx is com.jivesoftware.community.JiveContext class (spring context)
		   result is java.io.PrintWriter */
		function run(ctx, result) {
		 	result.println("DONE");

			// Optional:
			return "Script Finished";
		}

Access DB

        var Collections = new JavaImporter(
            java.util,
            org.springframework.jdbc.core);

        /* Run the script.
           ctx is com.jivesoftware.community.JiveContext class (spring context)
           result is java.io.PrintWriter */
        function run(ctx, result) {
            SEPARATOR = ";";

            with (Collections) {
                dataSourceFactory = ctx.getBean(com.jivesoftware.base.database.dao.DataSourceFactory.class);
                jdbcTemplate = new JdbcTemplate(dataSourceFactory.dataSource);

                data = jdbcTemplate.queryForList("select 1 + 1 'Value of 1+1'");

                result.println("Result form SQL query:");

                line = data.get(0).keySet().iterator();
                while(line.hasNext()) {
                    column = line.next();
                    result.append(column + SEPARATOR);
                }
                result.append("\n");

                for (i = 0; i < data.size(); i++) {
                    line = data.get(i).values().iterator();
                    while(line.hasNext()) {
                        column = line.next();
                        result.append(column + SEPARATOR);
                    }
                    result.append("\n");
                }
            }
        }

