package jp.gihyo.projava.tasklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaskListDao {
    private final static String TABLE_NAME = "tasklist";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    TaskListDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int add(HomeController.TaskItem item){
        SqlParameterSource param = new BeanPropertySqlParameterSource(item);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName(TABLE_NAME);
        return insert.execute(param);
    }

    public <LIst> List<HomeController.TaskItem> findAll(){
        String query = "SELECT * FROM " + TABLE_NAME;
        List<Map<String, Object>> result = this.jdbcTemplate.queryForList(query);
        List<HomeController.TaskItem> list = result.stream().map(
                (Map<String, Object> row) -> new HomeController.TaskItem(
                        row.get("id").toString(),
                        row.get("task").toString(),
                        row.get("deadline").toString(),
                        row.get("memo").toString(),
                        (Boolean)row.get("done")

                )).toList();
        return list;
    }

    public int delete(String id) {
        int number = jdbcTemplate.update("DELETE FROM tasklist WHERE id = ?", id);
        return number;
    }

    public int update(HomeController.TaskItem taskItem){
        int number2 = jdbcTemplate.update("update tasklist set task=?, deadline=?, done=?, memo=? where id = ?",
                taskItem.task(),
                taskItem.deadline(),
                taskItem.done(),
                taskItem.memo(),
                taskItem.id());
        return number2;
    }
    public  <LIst> List<HomeController.TaskItem> searchMonth(String match_type,String month,String checkedDone,String search_task){

        String query;
//            query = "SELECT * FROM " + TABLE_NAME + " WHERE deadline like '" + month + "%'" +
//                    "AND done " + (checkedDone.equals("on") ? "='未'":"like '%'")+ "AND task like '" + search_task + "%'";
        if (match_type.equals("prefix_match")) {
            query = "SELECT * FROM " + TABLE_NAME + " WHERE deadline like '" + month + "%'" +
                    "AND done " + (checkedDone.equals("on") ? "='未'":"like '%'")+ "AND task like '" + search_task + "%'";
        } else if (match_type.equals("Partial_Match")) {
            query = "SELECT * FROM " + TABLE_NAME + " WHERE deadline like '" + month + "%'" +
                    "AND done " + (checkedDone.equals("on") ? "='未'":"like '%'")+ "AND task like '%" + search_task + "%'";
        } else if (match_type.equals("Backward_match")) {
            query = "SELECT * FROM " + TABLE_NAME + " WHERE deadline like '" + month + "%'" +
                    "AND done " + (checkedDone.equals("on") ? "='未'":"like '%'")+ "AND task like '%" + search_task + "'";
        }else {
            query = "SELECT * FROM " + TABLE_NAME + " WHERE deadline like '" + month + "%'" +
                    "AND done " + (checkedDone.equals("on") ? "='未'":"like '%'")+ "AND task like '" + search_task + "'";
        }
        List<Map<String, Object>> result = this.jdbcTemplate.queryForList(query);
        List<HomeController.TaskItem> list = result.stream().map(
                (Map<String, Object> row) -> new HomeController.TaskItem(
                        row.get("id").toString(),
                        row.get("task").toString(),
                        row.get("deadline").toString(),
                        row.get("memo").toString(),
                        (Boolean)row.get("done")

                )).toList();
        return list;
    }
}
