package io.joyoungc.data.configuration;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;

import java.util.Locale;

/***
 * Created by Aiden Jeong on 2022.04.03
 */
public class P6SpySqlFormatter implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
                                String sql, String url) {
        sql = formatSql(category, sql);
        //return now + "|" + elapsed + "ms|" + category + "|connection " + connectionId + "|" + P6Util.singleLine(prepared) + sql;
        return now + "|" + elapsed + "ms|" + category + "|connection " + connectionId + "|" + sql;
    }

    private String formatSql(String category, String sql) {
        if (sql == null || sql.trim().equals("")) return sql;

        // Only format Statement, distinguish DDL And DML
        if (Category.STATEMENT.getName().equals(category)) {
            String tempSql = sql.trim().toLowerCase(Locale.ROOT);
            if (tempSql.startsWith("create") || tempSql.startsWith("alter") || tempSql.startsWith("comment")) {
                sql = FormatStyle.DDL.getFormatter().format(sql);
            } else {
                sql = FormatStyle.BASIC.getFormatter().format(sql);
            }
            sql = "|\nFormatSql(P6Spy sql,Hibernate format):" + sql;
        }

        return sql;
    }
}
