package jp.gihyo.projava.tasklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class HomeController {
    private final TaskListDao dao;

    @Autowired
    public HomeController(TaskListDao dao) {
        this.dao = dao;
    }

    record TaskItem(String id, String task, String deadline, String memo, boolean done) {

    }

//    private List<TaskItem> taskItems = new ArrayList<>();

    @RequestMapping(value = "/hello")
    String hello(Model model) {
        model.addAttribute("time", LocalDateTime.now());
        return "hello";
    }

    @GetMapping("/list")
    String listItems(Model model) {
        List<TaskItem> taskItems = this.dao.findAll();
        model.addAttribute("taskList", taskItems);
        return "home";
    }

    @GetMapping("/add")
    String addItem(@RequestParam("task") String task,
                   @RequestParam("deadline") String deadline,
                   @RequestParam("memo") String memo){
        String id = UUID.randomUUID().toString().substring(0, 8);
        TaskItem item = new TaskItem(id, task, deadline,memo, false);
        this.dao.add(item);

        return "redirect:/list";
    }

    @GetMapping("/delete")
    String deleteItem(@RequestParam("id") String id) {
        this.dao.delete(id);
        return "redirect:/list";
    }

    @GetMapping("/update")
    String updateItem(@RequestParam("id") String id,
                      @RequestParam("task") String task,
                      @RequestParam("deadline") String deadline,
                      @RequestParam("memo") String memo,
                      @RequestParam("done") boolean done) {
        TaskItem taskItem = new TaskItem(id, task, deadline, memo,done);
        this.dao.update(taskItem);
        return "redirect:/list";
    }

    @GetMapping("/search_month")
    String searchMonth(Model model,
                       @RequestParam("month") String month,
                       @RequestParam("checkedDone") String checked) {
        List<TaskItem> taskItems = null;
            taskItems = this.dao.searchMonth(month,checked);

        model.addAttribute("taskList", taskItems);
        return "home";
    }
}
