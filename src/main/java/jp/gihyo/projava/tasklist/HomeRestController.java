package jp.gihyo.projava.tasklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class HomeRestController {
    private final TaskListDao dao;
    private final TaskListDao searchdao;
    @Autowired
    HomeRestController(TaskListDao dao){
        this.dao = dao;
        this.searchdao = this.dao;
    }

    @PostMapping("/rest_add")
    List<HomeController.TaskItem> addItem(
                    @RequestParam("task") String task,
                    @RequestParam("deadline") String deadLine,
                    @RequestParam("memo") String memo/*,
                                          @RequestParam("done") boolean done*/){
        String id = UUID.randomUUID().toString().substring(0, 8);
        HomeController.TaskItem item = new HomeController.TaskItem(id, task, deadLine, memo,false);
        this.dao.add(item);
        return this.dao.findAll();
    }



//    private List<TaskItem> taskItems = new ArrayList<>();

//    @RequestMapping(value = "/resthello")
//    String hello() {
//        return """
//                Hello.
//                It works!
//                現在時刻は%sです。
//                """.formatted(LocalDateTime.now());
//    }

//    @GetMapping("/restadd")
//    String addItem(@RequestParam("task") String task,
//                   @RequestParam("deadline") String deadline) {
//        String id = UUID.randomUUID().toString().substring(0, 8);
//        TaskItem item = new TaskItem(id, task, deadline, false);
//        taskItems.add(item);
//
//        return "タスクを追加しました";
//    }

//    @GetMapping("/restlist")
//    String listItems() {
//        String result = taskItems.stream()
//                .map(TaskItem::toString)
//                .collect(Collectors.joining(", "));
//        return result;
//    }
}
